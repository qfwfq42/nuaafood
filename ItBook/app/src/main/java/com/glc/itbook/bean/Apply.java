package com.glc.itbook.bean;


public class Apply {
    private Integer applyID;
    private Integer userID;
    private Integer orderID;
    private Integer applyAmount;
    private String foodName;
    private String applyPlace;
    private String applyState;

    public Integer getApplyID() { return applyID; }

    public void setApplyID(Integer applyID) { this.applyID = applyID; }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public Integer getOrderID() { return orderID; }

    public void setOrderID(Integer orderID) { this.orderID = orderID; }

    public String getFoodName() { return foodName; }

    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Integer getApplyAmount() { return applyAmount; }

    public void setApplyAmount(Integer applyAmount) { this.applyAmount = applyAmount; }

    public String getApplyPlace() { return applyPlace; }

    public void setApplyPlace(String applyPlace) { this.applyPlace = applyPlace; }

    public String getApplyState() { return applyState; }

    public void setApplyState(String applyState) { this.applyState = applyState; }

    @Override
    public String toString() {
        return "Item{" +
                "applyID=" + applyID +
                ", userID='" + userID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", applyAmount='" + applyAmount + '\'' +
                ", foodName='" + foodName + '\'' +
                ", applyPlace='" + applyPlace + '\'' +
                ", applyState='" + applyState + '\'' +
                '}';
    }
}
