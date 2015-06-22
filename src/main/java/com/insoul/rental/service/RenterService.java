package com.insoul.rental.service;

import java.util.List;

import com.insoul.rental.vo.CurrentPage;
import com.insoul.rental.vo.RenterDetailVO;
import com.insoul.rental.vo.RenterListVO;
import com.insoul.rental.vo.request.RenterCreateRequest;
import com.insoul.rental.vo.request.RenterListRequest;

public interface RenterService extends BaseService {

    int createRenter(RenterCreateRequest renterCreateRequest);

    CurrentPage<RenterListVO> listRenters(RenterListRequest renterListRequest);

    RenterDetailVO getRenterDetail(int renterId);

    void editRenter(int renterId, RenterCreateRequest renterCreateRequest);

    int deleteRenter(int renterId);

    List<RenterListVO> listRenters();
}
