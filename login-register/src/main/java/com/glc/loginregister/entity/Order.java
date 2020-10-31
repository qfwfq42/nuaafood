package com.glc.loginregister.entity;

import java.util.Date;

public class Order {
    private Integer orderID;
    private Integer userID;
    private Integer timeLimit;
    private Integer peopleLimit;
    private String publishPlace;
    private String orderState;
    private Date publishTime;
    public Integer getOrderID() { return orderID; }

    public void setOrderID(Integer orderID) { this.orderID = orderID; }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public Date getPublishTime() { return publishTime; }

    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }

    public Integer getTimeLimit() { return timeLimit; }

    public void setTimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }

    public String getPublishPlace() { return publishPlace; }

    public void setPublishPlace(String publishPlace) { this.publishPlace = publishPlace; }

    public String getOrderState() { return orderState; }

    public void setOrderState(String orderState) { this.orderState = orderState; }

    public Integer getPeopleLimit() { return peopleLimit; }

    public void setPeopleLimit(Integer peopleLimit) { this.peopleLimit = peopleLimit; }

    @Override
    public String toString() {
        return "Item{" +
                "orderID=" + orderID +
                ", userID='" + userID + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", peopleLimit='" + peopleLimit + '\'' +
                ", publishPlace='" + publishPlace + '\'' +
                ", orderState='" + orderState + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
