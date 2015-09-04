package com.insoul.rental.service;

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

public interface StallService extends BaseService {

    int createStall(StallCreateRequest stallCreateRequest);

    CurrentPage<StallListVO> listStalls(StallListRequest stallListRequest);

    StallDetailVO getStallDetail(int stallId);

    void editStall(int stallId, StallCreateRequest stallCreateRequest);

    void unrent(int stallId);

    int deleteStall(int stallId);

    void payRent(int stallId, RentPayment rentPayment);

    void payWatermeter(int stallId, UtilitiesPayment utilitiesPayment);

    void payMeter(int stallId, UtilitiesPayment utilitiesPayment);

    int confirmPay(int statisticId, String type);

    String getLastWatermeter(int stallRenterId);

    String getLastMeter(int stallRenterId);

    CurrentPage<StallPaymentHistoryListVO> listStallPaymentHistory(int stallId, PaginationRequest paginationRequest);

    CurrentPage<StallUtilitiesPaymentHistoryListVO> listStallUtilitiesPaymentHistory(int stallId, int type,
            PaginationRequest paginationRequest);

    void generateStallFeeStatistics(String year, int quarter);

}
