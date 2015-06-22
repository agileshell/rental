package com.insoul.rental.service;

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

public interface FlatService {

    int createFlat(FlatCreateRequest flatCreateRequest);

    CurrentPage<FlatListVO> listFlats(FlatListRequest flatListRequest);

    FlatDetailVO getFlatDetail(int flatId);

    void editFlat(int flatId, FlatCreateRequest flatCreateRequest);

    void unrent(int flatId);

    int deleteFlat(int flatId);

    void payRent(int flatId, RentPayment rentPayment);

    CurrentPage<FlatPaymentHistoryListVO> listFlatPaymentHistory(int flatId, PaginationRequest paginationRequest);

    void payMeter(int flatId, UtilitiesPayment utilitiesPayment);

    String getLastMeter(int flatRenterId);

    CurrentPage<FlatMeterPaymentHistoryListVO> listFlatMeterPaymentHistories(int flatId,
            PaginationRequest paginationRequest);
}
