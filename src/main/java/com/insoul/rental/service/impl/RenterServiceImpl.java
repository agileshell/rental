package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.insoul.rental.criteria.RenterCriteria;
import com.insoul.rental.model.Pagination;
import com.insoul.rental.model.Renter;
import com.insoul.rental.service.RenterService;
import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.RenterDetailVO;
import com.insoul.rental.vo.RenterListVO;
import com.insoul.rental.vo.request.RenterCreateRequest;
import com.insoul.rental.vo.request.RenterListRequest;

@Service
public class RenterServiceImpl extends BaseServiceImpl implements RenterService {

    @Override
    public int createRenter(RenterCreateRequest renterCreateRequest) {
        Renter renter = new Renter();
        renter.setName(renterCreateRequest.getName());
        renter.setMobile(renterCreateRequest.getMobile());
        renter.setIdCard(renterCreateRequest.getIdCard());
        renter.setComment(renterCreateRequest.getComment());
        renter.setCreated(new Timestamp(System.currentTimeMillis()));

        int renterId = renterDao.create(renter);

        return renterId;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CurrentPage<RenterListVO> listRenters(RenterListRequest renterListRequest) {
        RenterCriteria criteria = new RenterCriteria();
        criteria.setName(renterListRequest.getName());

        int curn = renterListRequest.getCurn() > 0 ? renterListRequest.getCurn() : 1;
        int pageSize = renterListRequest.getPs();
        criteria.setOffset((curn - 1) * pageSize);
        criteria.setLimit(pageSize);

        Pagination<Renter> renters = renterDao.listRenters(criteria);

        return new CurrentPage(curn, renters.getCount(), pageSize, formatRenters(renters.getItems()));
    }

    @Override
    public RenterDetailVO getRenterDetail(int renterId) {
        RenterDetailVO vo = new RenterDetailVO();

        Renter renter = renterDao.getById(renterId);
        if (null != renter) {
            vo.setRenterId(renter.getRenterId());
            vo.setName(renter.getName());
            vo.setMobile(renter.getMobile());
            vo.setIdCard(renter.getIdCard());
            vo.setComment(renter.getComment());
        }

        return vo;
    }

    @Override
    public void editRenter(int renterId, RenterCreateRequest renterCreateRequest) {
        Renter renter = renterDao.getById(renterId);
        if (null == renter) {
            return;
        }

        renter.setName(renterCreateRequest.getName());
        renter.setMobile(renterCreateRequest.getMobile());
        renter.setIdCard(renterCreateRequest.getIdCard());
        renter.setComment(renterCreateRequest.getComment());
        renter.setUpdated(new Timestamp(System.currentTimeMillis()));

        renterDao.update(renter);
    }

    @Override
    public int deleteRenter(int renterId) {
        Renter renter = renterDao.getById(renterId);
        if (null == renter) {
            return -1;
        }

        if (stallRenterDao.countStallRenter(renterId) > 0) {
            return -2;
        }
        if (flatRenterDao.countFlatRenter(renterId) > 0) {
            return -3;
        }

        renterDao.delete(renterId);

        return 1;
    }

    private List<RenterListVO> formatRenters(List<Renter> renters) {
        List<RenterListVO> renterVOs = new ArrayList<RenterListVO>();
        if (null == renters || renters.isEmpty()) {
            return renterVOs;
        }

        for (Renter renter : renters) {
            RenterListVO vo = new RenterListVO();
            vo.setRenterId(renter.getRenterId());
            vo.setName(renter.getName());
            vo.setMobile(renter.getMobile());
            vo.setIdCard(renter.getIdCard());
            vo.setCreated(renter.getCreated());

            renterVOs.add(vo);
        }

        return renterVOs;
    }

    @Override
    public List<RenterListVO> listRenters() {
        List<Renter> renters = renterDao.listRenters();

        return formatRenters(renters);
    }
}
