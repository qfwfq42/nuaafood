package com.glc.loginregister.entity;

public class Food {
    private Integer foodID;
    private String foodName;
    private Integer foodPrice;
    private String foodIngredient;
    private Integer foodWeight;
    private String foodPicture;

    public Integer getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Integer foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodIngredient() {
        return foodIngredient;
    }

    public void setFoodIngredient(String foodIngredient) {
        this.foodIngredient = foodIngredient;
    }

    public Integer getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(Integer foodWeight) {
        this.foodWeight = foodWeight;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    @Override
    public String toString() {
        return "Item{" +
                "foodID=" + foodID +
                ", foodName='" + foodName + '\'' +
                ", foodPrice='" + foodPrice + '\'' +
                ", foodIngredient='" + foodIngredient + '\'' +
                ", foodWeight='" + foodWeight + '\'' +
                ", foodPicture='" + foodPicture + '\'' +
                '}';
    }
}
