package com.finalproject.mymoneyapi.repository;

import com.finalproject.mymoneyapi.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findByUser_Username(String username);
}
