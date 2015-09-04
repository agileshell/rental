package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.FlatRenter;

public interface FlatRenterDao {

    List<FlatRenter> listFlatRentersByFlatId(int flatId);

    List<FlatRenter> listFlatRentersByRenterId(int renterId);

    int create(FlatRenter flatRenter);

    void update(FlatRenter flatRenter);

    FlatRenter getById(int flatRenterId);

    void unRent(int flatRenterId);

    int countFlatRenter(int renterId);
}
