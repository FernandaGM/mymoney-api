package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.model.Category;
import com.finalproject.mymoneyapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/getall")
    public List<Category> getCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories;
    }

}
