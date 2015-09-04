package com.insoul.rental.service;

import java.util.List;

import com.insoul.rental.vo.SubareaVO;
import com.insoul.rental.vo.request.SubareaCreateRequest;

public interface SubareaService extends BaseService {

    List<SubareaVO> getAllSubareas();

    void createSubarea(SubareaCreateRequest subareaCreateRequest);

    SubareaVO getSubareaDetail(int subareaId);

    void editSubarea(int subareaId, SubareaCreateRequest subareaCreateRequest);
}
