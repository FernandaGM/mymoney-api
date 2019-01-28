package com.finalproject.mymoneyapi.model;

public class DataSet {
    private String label;
    private String backgroundColor;
    private String borderColor;
    private double[] data;

    public DataSet(String label, String backgroundColor, String borderColor, double[] data) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }
}
