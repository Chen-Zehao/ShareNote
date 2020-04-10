package com.scnu.model;

/**
 * Created by ChenZehao
 * on 2020/3/31
 */
public class PriceModel {
    private String price;
    private boolean isSelected;

    public PriceModel() {
        price = "";
        isSelected = false;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
