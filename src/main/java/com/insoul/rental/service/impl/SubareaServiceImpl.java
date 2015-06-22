package com.insoul.rental.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.insoul.rental.model.Subarea;
import com.insoul.rental.service.SubareaService;
import com.insoul.rental.vo.SubareaVO;
import com.insoul.rental.vo.request.SubareaCreateRequest;

@Service
public class SubareaServiceImpl extends BaseServiceImpl implements SubareaService {

    @Override
    public List<SubareaVO> getAllSubareas() {
        List<Subarea> subareas = subareaDao.listSubareas();

        List<SubareaVO> subareaVOs = new ArrayList<SubareaVO>();
        for (Subarea subarea : subareas) {
            SubareaVO subareaVO = new SubareaVO();
            subareaVO.setSubareaId(subarea.getSubareaId());
            subareaVO.setName(subarea.getName());
            subareaVO.setComment(subarea.getComment());

            subareaVOs.add(subareaVO);
        }

        return subareaVOs;
    }

    @Override
    public void createSubarea(SubareaCreateRequest subareaCreateRequest) {
        Subarea subarea = new Subarea();
        subarea.setName(subareaCreateRequest.getName());
        subarea.setComment(subareaCreateRequest.getComment());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        subarea.setCreated(now);
        subarea.setUpdated(now);

        subareaDao.create(subarea);
    }

    @Override
    public SubareaVO getSubareaDetail(int subareaId) {
        Subarea subarea = subareaDao.getById(subareaId);

        SubareaVO subareaVO = new SubareaVO();
        subareaVO.setSubareaId(subarea.getSubareaId());
        subareaVO.setName(subarea.getName());
        subareaVO.setComment(subarea.getComment());

        return subareaVO;
    }

    @Override
    public void editSubarea(int subareaId, SubareaCreateRequest subareaCreateRequest) {
        Subarea subarea = subareaDao.getById(subareaId);
        if (null == subarea || subarea.getSubareaId() <= 0) {
            return;
        }

        subarea.setSubareaId(subareaId);
        subarea.setName(subareaCreateRequest.getName());
        subarea.setComment(subareaCreateRequest.getComment());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        subarea.setUpdated(now);

        subareaDao.update(subarea);
    }

}
