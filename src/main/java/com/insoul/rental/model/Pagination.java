package com.insoul.rental.model;

import java.util.ArrayList;
import java.util.List;

public class Pagination<E> {

    private int count;

    private List<E> items = new ArrayList<E>();

    public Pagination(int count, List<E> items) {
        this.count = count;
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

}
