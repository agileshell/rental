package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.Subarea;

public interface SubareaDao {

    void create(Subarea subarea);

    void update(Subarea subarea);

    Subarea getById(int subareaId);

    List<Subarea> listSubareas();

    List<Subarea> getSubareasByIds(List<Integer> ids);
}
