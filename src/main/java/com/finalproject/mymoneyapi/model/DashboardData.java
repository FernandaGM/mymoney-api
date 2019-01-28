package com.finalproject.mymoneyapi.model;

public class DashboardData {

    private String[] label;

    private DataSet[] dataset;

    public DashboardData(String[] label, DataSet[] data) {
        this.label = label;
        this.dataset = data;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public DataSet[] getDataset() {
        return dataset;
    }

    public void setDataset(DataSet[] dataset) {
        this.dataset = dataset;
    }
}
