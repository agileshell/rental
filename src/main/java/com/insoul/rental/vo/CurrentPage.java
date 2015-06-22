package com.insoul.rental.vo;

import java.util.ArrayList;
import java.util.List;

public class CurrentPage<E> {

    private int curn;

    private int totaln;

    private List<E> items = new ArrayList<E>();

    public CurrentPage(int curn, int count, int pageSize, List<E> items) {
        this.totaln = calculatePageCount(count, pageSize);
        this.curn = (0 == curn) ? 1 : (curn > totaln ? totaln : curn);
        this.items = items;
    }

    public int getCurn() {
        return curn;
    }

    public void setCurn(int curn) {
        this.curn = curn;
    }

    public int getTotaln() {
        return totaln;
    }

    public void setTotaln(int totaln) {
        this.totaln = totaln;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int calculatePageCount(int rowCount, int pageSize) {
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        return pageCount;
    }
}
