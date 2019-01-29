package com.finalproject.mymoneyapi.repository;

import com.finalproject.mymoneyapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
