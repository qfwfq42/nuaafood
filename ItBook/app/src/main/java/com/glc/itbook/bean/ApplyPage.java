package com.glc.itbook.bean;

import java.util.List;

public class ApplyPage{
    private int currentPage;
    private int pageSize;
    private int totalNum;
    private int isMore;
    private int totalPage;
    private List<Apply> items;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getIsMore() {
        return isMore;
    }

    public void setIsMore(int isMore) {
        this.isMore = isMore;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Apply> getFoods() {
        return items;
    }

    public void setFoods(List<Apply> items) {
        this.items = items;
    }

}
