package com.finalproject.mymoneyapi.model;

import com.finalproject.mymoneyapi.entities.Category;
import com.finalproject.mymoneyapi.entities.Entry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SimpleEntry {

    private Long id;

    private String description;

    private BigDecimal value;

    private Date data;

    private String isIncome;

    private List<Category> categories;

    public SimpleEntry(Entry entry) {
        this.id = entry.getId();
        this.description = entry.getDescription();
        this.value = entry.getValue();
        this.data = entry.getData();
        this.isIncome = entry.getIsIncome();
        this.categories = entry.getCategories();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(String isIncome) {
        this.isIncome = isIncome;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
            this.categories = categories;
        }
}
