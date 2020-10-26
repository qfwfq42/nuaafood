package com.glc.itbook.bean;

import java.util.Date;
import java.util.List;

public class Order {
    /*
    private List<OrdersBean> items;

    public List<OrdersBean> getOrders() {
        return items;
    }

    public void setOrders(List<OrdersBean> items) {
        this.items = items;
    }

    public class OrdersBean {
    */

        private Integer orderID;
        private Integer userID;
        private Integer timeLimit;
        private Integer peopleLimit;
        private String publishPlace;
        private String orderState;
        private Date publishTime;
        public Integer getorderID() { return orderID; }

        public void setorderID(Integer orderID) { this.orderID = orderID; }

        public Integer getuserID() { return userID; }

        public void setuserID(Integer userID) { this.userID = userID; }

        public Date getpublishTime() { return publishTime; }

        public void setpublishTime(Date publishTime) { this.publishTime = publishTime; }

        public Integer gettimeLimit() { return timeLimit; }

        public void settimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }

        public String getpublishPlace() { return publishPlace; }

        public void setpublishPlace(String publishPlace) { this.publishPlace = publishPlace; }

        public String getorderState() { return orderState; }

        public void setorderState(String orderState) { this.orderState = orderState; }

        public Integer getpeopleLimit() { return peopleLimit; }

        public void setpeopleLimit(Integer peopleLimit) { this.peopleLimit = peopleLimit; }

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
       // }
    }
}
