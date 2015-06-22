package com.insoul.rental.dao;

import java.util.List;

import com.insoul.rental.model.StallRenter;

public interface StallRenterDao {

    List<StallRenter> listStallRentersByStallId(int stallId);

    List<StallRenter> listStallRentersByRenterId(int renterId);

    int create(StallRenter stallRenter);

    void update(StallRenter stallRenter);

    StallRenter getById(int stallRenterId);

    void unRent(int stallRenterId);

    int countStallRenter(int renterId);
}
