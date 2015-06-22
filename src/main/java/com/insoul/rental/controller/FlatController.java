package com.insoul.rental.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.insoul.rental.dao.FlatDao;
import com.insoul.rental.dao.FlatMeterPaymentHistoryDao;
import com.insoul.rental.dao.FlatPaymentHistoryDao;
import com.insoul.rental.model.Flat;
import com.insoul.rental.model.FlatMeterPaymentHistory;
import com.insoul.rental.model.FlatPaymentHistory;
import com.insoul.rental.service.FlatService;
import com.insoul.rental.service.RenterService;
import com.insoul.rental.service.SystemSettingService;
import com.insoul.rental.util.NumberToCN;
import com.insoul.rental.util.Printer;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.FlatDetailVO;
import com.insoul.rental.vo.FlatListVO;
import com.insoul.rental.vo.FlatMeterPaymentHistoryListVO;
import com.insoul.rental.vo.FlatPaymentHistoryListVO;
import com.insoul.rental.vo.request.FlatCreateRequest;
import com.insoul.rental.vo.request.FlatListRequest;
import com.insoul.rental.vo.request.PaginationRequest;
import com.insoul.rental.vo.request.RentPayment;
import com.insoul.rental.vo.request.UtilitiesPayment;

@Controller
@RequestMapping(value = "/flat")
public class FlatController extends BaseController {

    @Autowired
    private FlatService flatService;

    @Autowired
    private RenterService renterService;

    @Autowired
    private FlatDao flatDao;

    @Autowired
    private FlatPaymentHistoryDao flatPaymentHistoryDao;

    @Autowired
    private FlatMeterPaymentHistoryDao flatMeterPaymentHistoryDao;

    @Autowired
    private SystemSettingService systemSettingService;

    @RequestMapping("/list")
    public String listFlat(@ModelAttribute FlatListRequest flatListRequest, Model model) {
        CurrentPage<FlatListVO> result = flatService.listFlats(flatListRequest);
        model.addAttribute("request", flatListRequest);

        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotEmpty(flatListRequest.getName())) {
            condition.append("&name=").append(flatListRequest.getName());
        }
        if (null != flatListRequest.getIsRented()) {
            condition.append("&isRented=").append(flatListRequest.getIsRented());
        }
        if (null != flatListRequest.getRenterId()) {
            condition.append("&renterId=").append(flatListRequest.getRenterId());
        }
        model.addAttribute("condition", condition.toString());

        int curn = result.getCurn();
        int ps = flatListRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("flats", result.getItems());
        model.addAttribute("ps", ps);

        return "flat/list";
    }

    @RequestMapping("/addPage")
    public String getAddFlatPage(Model model) {
        model.addAttribute("flat", new FlatDetailVO());
        model.addAttribute("renters", renterService.listRenters());

        return "flat/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addFlat(@ModelAttribute FlatCreateRequest flatCreateRequest) {
        flatService.createFlat(flatCreateRequest);

        return "redirect:/flat/list";
    }

    @RequestMapping("/editPage")
    public String getEditFlatPage(int flatId, Model model) {
        FlatDetailVO flat = flatService.getFlatDetail(flatId);
        model.addAttribute("flat", flat);
        model.addAttribute("renters", renterService.listRenters());

        return "flat/form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editFlat(int flatId, @ModelAttribute FlatCreateRequest flatCreateRequest) {
        flatService.editFlat(flatId, flatCreateRequest);

        return "redirect:/flat/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> deleteFlat(int flatId) {
        int status = flatService.deleteFlat(flatId);

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
    public String getPayRentPage(int flatId, Model model) {
        Flat flat = flatDao.getById(flatId);
        model.addAttribute("flatId", flat.getFlatId());
        model.addAttribute("flatName", flat.getName());
        model.addAttribute("monthPrice", flat.getMonthPrice());

        return "flat/pay_rent";
    }

    @RequestMapping(value = "/payRent", method = RequestMethod.POST)
    public String payRent(int flatId, @ModelAttribute RentPayment rentPayment) {
        flatService.payRent(flatId, rentPayment);

        return "redirect:/flat/listRentpaymentHistory?flatId=" + flatId;
    }

    @RequestMapping("/payMeterPage")
    public String getPayMeterPage(int flatId, Model model) {
        FlatDetailVO flat = flatService.getFlatDetail(flatId);
        model.addAttribute("flatId", flat.getFlatId());
        model.addAttribute("flatName", flat.getName());
        String lastMeter = "";
        if (null != flat.getFlatRenter()) {
            lastMeter = flatService.getLastMeter(flat.getFlatRenter().getFlatRenterId());
            if (StringUtils.isEmpty(lastMeter)) {
                lastMeter = flat.getFlatRenter().getInitMeter();
            }
        }
        model.addAttribute("lastMeter", lastMeter);

        Map<String, String> settings = systemSettingService.getSettings();
        String meter = settings.get(SystemSettingPath.METER);
        model.addAttribute("meter", meter);

        return "flat/pay_meter";
    }

    @RequestMapping(value = "/payMeter", method = RequestMethod.POST)
    public String payMeter(int flatId, @ModelAttribute UtilitiesPayment utilitiesPayment) {
        flatService.payMeter(flatId, utilitiesPayment);

        return "redirect:/flat/listMeterpaymentHistory?flatId=" + flatId;
    }

    @RequestMapping("/listRentpaymentHistory")
    public String listRentPaymentHistory(int flatId, @ModelAttribute PaginationRequest paginationRequest, Model model) {
        Flat flat = flatDao.getById(flatId);
        model.addAttribute("flatId", flat.getFlatId());
        model.addAttribute("flatName", flat.getName());

        CurrentPage<FlatPaymentHistoryListVO> result = flatService.listFlatPaymentHistory(flatId, paginationRequest);
        model.addAttribute("request", paginationRequest);
        model.addAttribute("condition", "");

        int curn = result.getCurn();
        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("histories", result.getItems());
        model.addAttribute("ps", ps);

        return "flat/list_rentpayment_history";
    }

    @RequestMapping("/listMeterpaymentHistory")
    public String listMeterPaymentHistory(int flatId, @ModelAttribute PaginationRequest paginationRequest, Model model) {
        Flat flat = flatDao.getById(flatId);
        model.addAttribute("flatId", flat.getFlatId());
        model.addAttribute("flatName", flat.getName());

        CurrentPage<FlatMeterPaymentHistoryListVO> result = flatService.listFlatMeterPaymentHistories(flatId,
                paginationRequest);
        model.addAttribute("request", paginationRequest);
        model.addAttribute("condition", "");

        int curn = result.getCurn();
        int ps = paginationRequest.getPs();
        model.addAttribute("curn", curn);
        model.addAttribute("totaln", result.getTotaln());
        model.addAttribute("histories", result.getItems());
        model.addAttribute("ps", ps);

        return "flat/list_meterpayment_history";
    }

    @RequestMapping("/downloadRentpayment/{id}")
    public ModelAndView downloadRentpayment(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FlatPaymentHistory flatPaymentHistory = flatPaymentHistoryDao.getById(id);
        if (null == flatPaymentHistory) {
            return null;
        }

        try {
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

            File demoFile = new File(classpath + "/flat_rent_template.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$renterName$", flatPaymentHistory.getRenterName());
            Flat flat = flatDao.getById(flatPaymentHistory.getFlatId());
            map.put("$flatName$", flat.getName());
            map.put("$startDate$", dateFormat.format(flatPaymentHistory.getStartDate()));
            map.put("$endDate$", dateFormat.format(flatPaymentHistory.getEndDate()));
            map.put("$totalPrice$", flatPaymentHistory.getTotalPrice() + "");
            map.put("$totalCNYPrice$",
                    NumberToCN.number2CNMontrayUnit(new BigDecimal(flatPaymentHistory.getTotalPrice())));
            map.put("$created$", dateFormat.format(flatPaymentHistory.getCreated()));
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
        FlatPaymentHistory flatPaymentHistory = flatPaymentHistoryDao.getById(id);
        if (null == flatPaymentHistory) {
            return null;
        }

        String dir = request.getServletContext().getRealPath("/");
        String basedir = dir + "docs/flat_rent_payment";
        File filedir = new File(basedir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }

        String docPath = basedir + "/" + id + ".doc";
        File doc = new File(docPath);
        if (!doc.exists()) {
            try {
                String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

                File demoFile = new File(classpath + "/flat_rent_template.doc");
                FileInputStream in = new FileInputStream(demoFile);
                HWPFDocument hdt = new HWPFDocument(in);

                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

                Range range = hdt.getRange();
                Map<String, String> map = new HashMap<String, String>();
                map.put("$renterName$", flatPaymentHistory.getRenterName());
                Flat flat = flatDao.getById(flatPaymentHistory.getFlatId());
                map.put("$flatName$", flat.getName());
                map.put("$startDate$", dateFormat.format(flatPaymentHistory.getStartDate()));
                map.put("$endDate$", dateFormat.format(flatPaymentHistory.getEndDate()));
                map.put("$totalPrice$", flatPaymentHistory.getTotalPrice() + "");
                map.put("$totalCNYPrice$",
                        NumberToCN.number2CNMontrayUnit(new BigDecimal(flatPaymentHistory.getTotalPrice())));
                map.put("$created$", dateFormat.format(flatPaymentHistory.getCreated()));
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

        return "redirect:/flat/listRentpaymentHistory?flatId=" + flatPaymentHistory.getFlatId();
    }

    @RequestMapping("/downloadMeterpayment/{id}")
    public ModelAndView downloadMeterpayment(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FlatMeterPaymentHistory flatMeterPaymentHistory = flatMeterPaymentHistoryDao.getById(id);
        if (null == flatMeterPaymentHistory) {
            return null;
        }

        try {
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

            File demoFile = new File(classpath + "/flat_meter_template.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$renterName$", flatMeterPaymentHistory.getRenterName());
            Flat flat = flatDao.getById(flatMeterPaymentHistory.getFlatId());
            map.put("$flatName$", flat.getName());
            map.put("$firstRecord$", flatMeterPaymentHistory.getFirstRecord());
            map.put("$lastRecord$", flatMeterPaymentHistory.getLastRecord());
            map.put("$qty$",
                    (Integer.valueOf(flatMeterPaymentHistory.getLastRecord()) - Integer.valueOf(flatMeterPaymentHistory
                            .getFirstRecord())) + "");
            map.put("$price$", flatMeterPaymentHistory.getPrice() + "");
            map.put("$totalPrice$", flatMeterPaymentHistory.getTotalPrice() + "");
            map.put("$totalCNYPrice$",
                    NumberToCN.number2CNMontrayUnit(new BigDecimal(flatMeterPaymentHistory.getTotalPrice())));
            map.put("$recordDate$", dateFormat.format(flatMeterPaymentHistory.getRecordDate()));
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
        FlatMeterPaymentHistory flatMeterPaymentHistory = flatMeterPaymentHistoryDao.getById(id);
        if (null == flatMeterPaymentHistory) {
            return null;
        }

        String dir = request.getServletContext().getRealPath("/");
        String basedir = dir + "docs/flat_meter_payment";
        File filedir = new File(basedir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }

        String docPath = basedir + "/" + id + ".doc";
        File doc = new File(docPath);
        if (!doc.exists()) {
            try {
                String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

                File demoFile = new File(classpath + "/flat_meter_template.doc");
                FileInputStream in = new FileInputStream(demoFile);
                HWPFDocument hdt = new HWPFDocument(in);

                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

                Range range = hdt.getRange();
                Map<String, String> map = new HashMap<String, String>();
                map.put("$renterName$", flatMeterPaymentHistory.getRenterName());
                Flat flat = flatDao.getById(flatMeterPaymentHistory.getFlatId());
                map.put("$flatName$", flat.getName());
                map.put("$firstRecord$", flatMeterPaymentHistory.getFirstRecord());
                map.put("$lastRecord$", flatMeterPaymentHistory.getLastRecord());
                map.put("$qty$",
                        (Integer.valueOf(flatMeterPaymentHistory.getLastRecord()) - Integer
                                .valueOf(flatMeterPaymentHistory.getFirstRecord())) + "");
                map.put("$price$", flatMeterPaymentHistory.getPrice() + "");
                map.put("$totalPrice$", flatMeterPaymentHistory.getTotalPrice() + "");
                map.put("$totalCNYPrice$",
                        NumberToCN.number2CNMontrayUnit(new BigDecimal(flatMeterPaymentHistory.getTotalPrice())));
                map.put("$recordDate$", dateFormat.format(flatMeterPaymentHistory.getRecordDate()));
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

        return "redirect:/flat/listMeterpaymentHistory?stallId=" + flatMeterPaymentHistory.getFlatId();
    }
}
