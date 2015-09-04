package com.insoul.rental.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insoul.rental.constant.ResponseStatus;
import com.insoul.rental.constant.SystemSettingPath;
import com.insoul.rental.dao.StallDao;
import com.insoul.rental.dao.StallPaymentHistoryDao;
import com.insoul.rental.dao.StallUtilitiesPaymentHistoryDao;
import com.insoul.rental.dao.SubareaDao;
import com.insoul.rental.model.Stall;
import com.insoul.rental.model.StallPaymentHistory;
import com.insoul.rental.model.StallUtilitiesPaymentHistory;
import com.insoul.rental.model.Subarea;
import com.insoul.rental.service.RenterService;
import com.insoul.rental.service.StallService;
import com.insoul.rental.service.SubareaService;
import com.insoul.rental.service.SystemSettingService;
import com.insoul.rental.util.NumberToCN;
import com.insoul.rental.util.Printer;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.StallDetailVO;
import com.insoul.rental.vo.StallListVO;
import com.insoul.rental.vo.StallPaymentHistoryListVO;
import com.insoul.rental.vo.StallUtilitiesPaymentHistoryListVO;
import com.insoul.rental.vo.request.PaginationRequest;
import com.insoul.rental.vo.request.RentPayment;
import com.insoul.rental.vo.request.StallCreateRequest;
import com.insoul.rental.vo.request.StallListRequest;
import com.insoul.rental.vo.request.UtilitiesPayment;

@Controller
@RequestMapping(value = "/stall")
public class StallController extends BaseController {

    @Autowired
    private StallService stallService;

    @Autowired
    private RenterService renterService;

    @Autowired
    protected SystemSettingService systemSettingService;

    @Autowired
    private StallDao stallDao;

    @Autowired
    private StallPaymentHistoryDao stallPaymentHistoryDao;

    @Autowired
    private StallUtilitiesPaymentHistoryDao stallUtilitiesPaymentHistoryDao;

    @Autowired
    private SubareaService subareaService;

    @Autowired
    private SubareaDao subareaDao;

    @RequestMapping("/list")
    public String listStall(@ModelAttribute StallListRequest stallListRequest, Model model) {
        CurrentPage<StallListVO> result = stallService.listStalls(stallListRequest);
        model.addAttribute("request", stallListRequest);

        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotEmpty(stallListRequest.getName())) {
            condition.append("&name=").append(stallListRequest.getName());
        }
        if (null != stallListRequest.getIsRented()) {
            condition.append("&isRented=").append(stallListRequest.getIsRented());
        }
        if (null != stallListRequest.getSubareaId()) {
            condition.append("&subareaId=").append(stallListRequest.getSubareaId());
        }
        if (null != stallListRequest.getRenterId()) {
            condition.append("&renterId=").append(stallListRequest.getRenterId());
        }
        model.addAttribute("condition", condition.toString());

        int curn = result.getCurn();
        int ps = stallListRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("stalls", result.getItems());
        model.addAttribute("ps", ps);

        model.addAttribute("subareas", subareaService.getAllSubareas());

        return "stall/list";
    }

    @RequestMapping("/addPage")
    public String getAddStallPage(Model model) {
        model.addAttribute("stall", new StallDetailVO());
        model.addAttribute("renters", renterService.listRenters());
        model.addAttribute("subareas", subareaService.getAllSubareas());

        return "stall/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addStall(@ModelAttribute StallCreateRequest stallCreateRequest) {
        stallService.createStall(stallCreateRequest);

        return "redirect:/stall/list";
    }

    @RequestMapping("/editPage")
    public String getEditStallPage(int stallId, Model model) {
        StallDetailVO stall = stallService.getStallDetail(stallId);
        model.addAttribute("stall", stall);
        model.addAttribute("renters", renterService.listRenters());
        model.addAttribute("subareas", subareaService.getAllSubareas());

        return "stall/form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editStall(int stallId, @ModelAttribute StallCreateRequest stallCreateRequest) {
        stallService.editStall(stallId, stallCreateRequest);

        return "redirect:/stall/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> deleteStall(int stallId) {
        int status = stallService.deleteStall(stallId);

        Map<String, Object> result = new HashMap<String, Object>();
        if (status > 0) {
            result.put("status", ResponseStatus.SUCCEED.getValue());
        } else {
            result.put("status", ResponseStatus.FAILED.getValue());
            result.put("error_code", status);
        }

        return result;
    }

    @RequestMapping("/payRentPage")
    public String getPayRentPage(int stallId, Model model) {
        Stall stall = stallDao.getById(stallId);
        Subarea subarea = subareaDao.getById(stall.getSubareaId());
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", subarea.getName() + stall.getName());
        model.addAttribute("monthPrice", stall.getMonthPrice());

        Map<String, String> quarters = getQuarterStartEnd(new Date());
        model.addAttribute("startDate", quarters.get("startDate"));
        model.addAttribute("endDate", quarters.get("endDate"));

        return "stall/pay_rent";
    }

    @RequestMapping(value = "/payRent", method = RequestMethod.POST)
    public String payRent(int stallId, @ModelAttribute RentPayment rentPayment) {
        stallService.payRent(stallId, rentPayment);

        return "redirect:/stall/listRentpaymentHistory?stallId=" + stallId;
    }

    @RequestMapping("/payWatermeterPage")
    public String getPayWatermeterPage(int stallId, Model model) {
        StallDetailVO stall = stallService.getStallDetail(stallId);
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", stall.getSubarea().getName() + stall.getName());
        String lastWatermeter = "";
        if (null != stall.getStallRenter()) {
            lastWatermeter = stallService.getLastWatermeter(stall.getStallRenter().getStallRenterId());
            if (StringUtils.isEmpty(lastWatermeter)) {
                lastWatermeter = stall.getStallRenter().getInitWatermeter();
            }
        }
        model.addAttribute("lastWatermeter", lastWatermeter);

        Map<String, String> settings = systemSettingService.getSettings();
        String watermeter = settings.get(SystemSettingPath.WATERMETER);
        model.addAttribute("watermeter", watermeter);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        model.addAttribute("recordDate", date);

        return "stall/pay_watermeter";
    }

    @RequestMapping(value = "/payWatermeter", method = RequestMethod.POST)
    public String payWatermeter(int stallId, @ModelAttribute UtilitiesPayment utilitiesPayment) {
        stallService.payWatermeter(stallId, utilitiesPayment);

        return "redirect:/stall/listWaterpaymentHistory?stallId=" + stallId;
    }

    @RequestMapping("/payMeterPage")
    public String getPayMeterPage(int stallId, Model model) {
        StallDetailVO stall = stallService.getStallDetail(stallId);
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", stall.getSubarea().getName() + stall.getName());
        String lastMeter = "";
        if (null != stall.getStallRenter()) {
            lastMeter = stallService.getLastMeter(stall.getStallRenter().getStallRenterId());
            if (StringUtils.isEmpty(lastMeter)) {
                lastMeter = stall.getStallRenter().getInitMeter();
            }
        }
        model.addAttribute("lastMeter", lastMeter);

        Map<String, String> settings = systemSettingService.getSettings();
        String meter = settings.get(SystemSettingPath.METER);
        model.addAttribute("meter", meter);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        model.addAttribute("recordDate", date);

        return "stall/pay_meter";
    }

    @RequestMapping(value = "/payMeter", method = RequestMethod.POST)
    public String payMeter(int stallId, @ModelAttribute UtilitiesPayment utilitiesPayment) {
        stallService.payMeter(stallId, utilitiesPayment);

        return "redirect:/stall/listMeterpaymentHistory?stallId=" + stallId;
    }

    @RequestMapping(value = "/confirmPay")
    @ResponseBody
    public Map<String, Object> confirmPay(int statisticId, String type) {
        int status = stallService.confirmPay(statisticId, type);

        Map<String, Object> result = new HashMap<String, Object>();
        if (status > 0) {
            result.put("status", ResponseStatus.SUCCEED.getValue());
        } else {
            result.put("status", ResponseStatus.FAILED.getValue());
            result.put("error_code", status);
        }

        return result;
    }

    @RequestMapping("/listRentpaymentHistory")
    public String listRentPaymentHistory(int stallId, @ModelAttribute PaginationRequest paginationRequest, Model model) {
        Stall stall = stallDao.getById(stallId);
        Subarea subarea = subareaDao.getById(stall.getSubareaId());
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", subarea.getName() + stall.getName());

        CurrentPage<StallPaymentHistoryListVO> result = stallService
                .listStallPaymentHistory(stallId, paginationRequest);
        model.addAttribute("request", paginationRequest);
        model.addAttribute("condition", "");

        int curn = result.getCurn();
        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("histories", result.getItems());
        model.addAttribute("ps", ps);

        return "stall/list_rentpayment_history";
    }

    @RequestMapping("/listWaterpaymentHistory")
    public String listWaterPaymentHistory(int stallId, @ModelAttribute PaginationRequest paginationRequest, Model model) {
        Stall stall = stallDao.getById(stallId);
        Subarea subarea = subareaDao.getById(stall.getSubareaId());
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", subarea.getName() + stall.getName());

        CurrentPage<StallUtilitiesPaymentHistoryListVO> result = stallService.listStallUtilitiesPaymentHistory(stallId,
                1, paginationRequest);
        model.addAttribute("request", paginationRequest);
        model.addAttribute("condition", "");

        int curn = result.getCurn();
        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("histories", result.getItems());
        model.addAttribute("ps", ps);

        return "stall/list_waterpayment_history";
    }

    @RequestMapping("/listMeterpaymentHistory")
    public String listMeterPaymentHistory(int stallId, @ModelAttribute PaginationRequest paginationRequest, Model model) {
        Stall stall = stallDao.getById(stallId);
        Subarea subarea = subareaDao.getById(stall.getSubareaId());
        model.addAttribute("stallId", stall.getStallId());
        model.addAttribute("stallName", subarea.getName() + stall.getName());

        CurrentPage<StallUtilitiesPaymentHistoryListVO> result = stallService.listStallUtilitiesPaymentHistory(stallId,
                2, paginationRequest);
        model.addAttribute("request", paginationRequest);
        model.addAttribute("condition", "");

        int curn = result.getCurn();
        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("histories", result.getItems());
        model.addAttribute("ps", ps);

        return "stall/list_meterpayment_history";
    }

    @RequestMapping("/downloadRentpayment/{id}")
    public ModelAndView downloadRentpayment(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        StallPaymentHistory stallPaymentHistory = stallPaymentHistoryDao.getById(id);
        if (null == stallPaymentHistory) {
            return null;
        }

        try {
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

            File demoFile = new File(classpath + "/stall_rent_template.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$renterName$", stallPaymentHistory.getRenterName());
            Stall stall = stallDao.getById(stallPaymentHistory.getStallId());
            Subarea subarea = subareaDao.getById(stall.getSubareaId());
            map.put("$stallName$", subarea.getName() + stall.getName());
            map.put("$startDate$", dateFormat.format(stallPaymentHistory.getStartDate()));
            map.put("$endDate$", dateFormat.format(stallPaymentHistory.getEndDate()));
            map.put("$totalPrice$", stallPaymentHistory.getTotalPrice() + "");
            map.put("$totalCNYPrice$",
                    NumberToCN.number2CNMontrayUnit(new BigDecimal(stallPaymentHistory.getTotalPrice())));
            map.put("$created$", dateFormat.format(stallPaymentHistory.getCreated()));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }

            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis() + ".doc\"");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream();
            hdt.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }

    @RequestMapping("/printRentpayment/{id}")
    public String printRentpayment(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        StallPaymentHistory stallPaymentHistory = stallPaymentHistoryDao.getById(id);
        if (null == stallPaymentHistory) {
            return null;
        }

        String dir = request.getServletContext().getRealPath("/");
        String basedir = dir + "docs/stall_rent_payment";
        File filedir = new File(basedir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }

        String docPath = basedir + "/" + id + ".doc";
        File doc = new File(docPath);
        if (!doc.exists()) {
            try {
                String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

                File demoFile = new File(classpath + "/stall_rent_template.doc");
                FileInputStream in = new FileInputStream(demoFile);
                HWPFDocument hdt = new HWPFDocument(in);

                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

                Range range = hdt.getRange();
                Map<String, String> map = new HashMap<String, String>();
                map.put("$renterName$", stallPaymentHistory.getRenterName());
                Stall stall = stallDao.getById(stallPaymentHistory.getStallId());
                Subarea subarea = subareaDao.getById(stall.getSubareaId());
                map.put("$stallName$", subarea.getName() + stall.getName());
                map.put("$startDate$", dateFormat.format(stallPaymentHistory.getStartDate()));
                map.put("$endDate$", dateFormat.format(stallPaymentHistory.getEndDate()));
                map.put("$totalPrice$", stallPaymentHistory.getTotalPrice() + "");
                map.put("$totalCNYPrice$",
                        NumberToCN.number2CNMontrayUnit(new BigDecimal(stallPaymentHistory.getTotalPrice())));
                map.put("$created$", dateFormat.format(stallPaymentHistory.getCreated()));
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    range.replaceText(entry.getKey(), entry.getValue());
                }

                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                hdt.write(ostream);

                FileOutputStream fos = new FileOutputStream(doc);
                ostream.writeTo(fos);

                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

        Printer.printWord(docPath);

        return "redirect:/stall/listRentpaymentHistory?stallId=" + stallPaymentHistory.getStallId();
    }

    @RequestMapping("/downloadWaterpayment/{id}")
    public ModelAndView downloadWaterpayment(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory = stallUtilitiesPaymentHistoryDao.getById(id);
        if (null == stallUtilitiesPaymentHistory) {
            return null;
        }

        try {
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

            File demoFile = new File(classpath + "/stall_water_template.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$renterName$", stallUtilitiesPaymentHistory.getRenterName());
            Stall stall = stallDao.getById(stallUtilitiesPaymentHistory.getStallId());
            Subarea subarea = subareaDao.getById(stall.getSubareaId());
            map.put("$stallName$", subarea.getName() + stall.getName());
            map.put("$firstRecord$", stallUtilitiesPaymentHistory.getFirstRecord());
            map.put("$lastRecord$", stallUtilitiesPaymentHistory.getLastRecord());
            map.put("$qty$",
                    (Integer.valueOf(stallUtilitiesPaymentHistory.getLastRecord()) - Integer
                            .valueOf(stallUtilitiesPaymentHistory.getFirstRecord())) + "");
            map.put("$price$", stallUtilitiesPaymentHistory.getPrice() + "");
            map.put("$totalPrice$", stallUtilitiesPaymentHistory.getTotalPrice() + "");
            map.put("$totalCNYPrice$",
                    NumberToCN.number2CNMontrayUnit(new BigDecimal(stallUtilitiesPaymentHistory.getTotalPrice())));
            map.put("$recordDate$", dateFormat.format(stallUtilitiesPaymentHistory.getRecordDate()));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }

            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis() + ".doc\"");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream();
            hdt.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }

    @RequestMapping("/printWaterpayment/{id}")
    public String printWaterpayment(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory = stallUtilitiesPaymentHistoryDao.getById(id);
        if (null == stallUtilitiesPaymentHistory) {
            return null;
        }

        String dir = request.getServletContext().getRealPath("/");
        String basedir = dir + "docs/stall_watermeter_payment";
        File filedir = new File(basedir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }

        String docPath = basedir + "/" + id + ".doc";
        File doc = new File(docPath);
        if (!doc.exists()) {
            try {
                String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

                File demoFile = new File(classpath + "/stall_water_template.doc");
                FileInputStream in = new FileInputStream(demoFile);
                HWPFDocument hdt = new HWPFDocument(in);

                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

                Range range = hdt.getRange();
                Map<String, String> map = new HashMap<String, String>();
                map.put("$renterName$", stallUtilitiesPaymentHistory.getRenterName());
                Stall stall = stallDao.getById(stallUtilitiesPaymentHistory.getStallId());
                Subarea subarea = subareaDao.getById(stall.getSubareaId());
                map.put("$stallName$", subarea.getName() + stall.getName());
                map.put("$firstRecord$", stallUtilitiesPaymentHistory.getFirstRecord());
                map.put("$lastRecord$", stallUtilitiesPaymentHistory.getLastRecord());
                map.put("$qty$",
                        (Integer.valueOf(stallUtilitiesPaymentHistory.getLastRecord()) - Integer
                                .valueOf(stallUtilitiesPaymentHistory.getFirstRecord())) + "");
                map.put("$price$", stallUtilitiesPaymentHistory.getPrice() + "");
                map.put("$totalPrice$", stallUtilitiesPaymentHistory.getTotalPrice() + "");
                map.put("$totalCNYPrice$",
                        NumberToCN.number2CNMontrayUnit(new BigDecimal(stallUtilitiesPaymentHistory.getTotalPrice())));
                map.put("$recordDate$", dateFormat.format(stallUtilitiesPaymentHistory.getRecordDate()));
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    range.replaceText(entry.getKey(), entry.getValue());
                }

                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                hdt.write(ostream);

                FileOutputStream fos = new FileOutputStream(doc);
                ostream.writeTo(fos);

                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

        Printer.printWord(docPath);

        return "redirect:/stall/listWaterpaymentHistory?stallId=" + stallUtilitiesPaymentHistory.getStallId();
    }

    @RequestMapping("/downloadMeterpayment/{id}")
    public ModelAndView downloadMeterpayment(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory = stallUtilitiesPaymentHistoryDao.getById(id);
        if (null == stallUtilitiesPaymentHistory) {
            return null;
        }

        try {
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

            File demoFile = new File(classpath + "/stall_meter_template.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$renterName$", stallUtilitiesPaymentHistory.getRenterName());
            Stall stall = stallDao.getById(stallUtilitiesPaymentHistory.getStallId());
            Subarea subarea = subareaDao.getById(stall.getSubareaId());
            map.put("$stallName$", subarea.getName() + stall.getName());
            map.put("$firstRecord$", stallUtilitiesPaymentHistory.getFirstRecord());
            map.put("$lastRecord$", stallUtilitiesPaymentHistory.getLastRecord());
            map.put("$qty$",
                    (Integer.valueOf(stallUtilitiesPaymentHistory.getLastRecord()) - Integer
                            .valueOf(stallUtilitiesPaymentHistory.getFirstRecord())) + "");
            map.put("$price$", stallUtilitiesPaymentHistory.getPrice() + "");
            map.put("$totalPrice$", stallUtilitiesPaymentHistory.getTotalPrice() + "");
            map.put("$totalCNYPrice$",
                    NumberToCN.number2CNMontrayUnit(new BigDecimal(stallUtilitiesPaymentHistory.getTotalPrice())));
            map.put("$recordDate$", dateFormat.format(stallUtilitiesPaymentHistory.getRecordDate()));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }

            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis() + ".doc\"");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream();
            hdt.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }

    @RequestMapping("/printMeterpayment/{id}")
    public String printMeterpayment(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        StallUtilitiesPaymentHistory stallUtilitiesPaymentHistory = stallUtilitiesPaymentHistoryDao.getById(id);
        if (null == stallUtilitiesPaymentHistory) {
            return null;
        }

        String dir = request.getServletContext().getRealPath("/");
        String basedir = dir + "docs/stall_meter_payment";
        File filedir = new File(basedir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }

        String docPath = basedir + "/" + id + ".doc";
        File doc = new File(docPath);
        if (!doc.exists()) {
            try {
                String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

                File demoFile = new File(classpath + "/stall_meter_template.doc");
                FileInputStream in = new FileInputStream(demoFile);
                HWPFDocument hdt = new HWPFDocument(in);

                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

                Range range = hdt.getRange();
                Map<String, String> map = new HashMap<String, String>();
                map.put("$renterName$", stallUtilitiesPaymentHistory.getRenterName());
                Stall stall = stallDao.getById(stallUtilitiesPaymentHistory.getStallId());
                Subarea subarea = subareaDao.getById(stall.getSubareaId());
                map.put("$stallName$", subarea.getName() + stall.getName());
                map.put("$firstRecord$", stallUtilitiesPaymentHistory.getFirstRecord());
                map.put("$lastRecord$", stallUtilitiesPaymentHistory.getLastRecord());
                map.put("$qty$",
                        (Integer.valueOf(stallUtilitiesPaymentHistory.getLastRecord()) - Integer
                                .valueOf(stallUtilitiesPaymentHistory.getFirstRecord())) + "");
                map.put("$price$", stallUtilitiesPaymentHistory.getPrice() + "");
                map.put("$totalPrice$", stallUtilitiesPaymentHistory.getTotalPrice() + "");
                map.put("$totalCNYPrice$",
                        NumberToCN.number2CNMontrayUnit(new BigDecimal(stallUtilitiesPaymentHistory.getTotalPrice())));
                map.put("$recordDate$", dateFormat.format(stallUtilitiesPaymentHistory.getRecordDate()));
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    range.replaceText(entry.getKey(), entry.getValue());
                }

                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                hdt.write(ostream);

                FileOutputStream fos = new FileOutputStream(doc);
                ostream.writeTo(fos);

                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

        Printer.printWord(docPath);

        return "redirect:/stall/listMeterpaymentHistory?stallId=" + stallUtilitiesPaymentHistory.getStallId();
    }
}
