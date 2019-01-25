package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.model.Expenses;
import com.finalproject.mymoneyapi.model.Response;
import com.finalproject.mymoneyapi.model.User;
import com.finalproject.mymoneyapi.repository.ExpensesRepository;
import com.finalproject.mymoneyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpensesController {

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getbyusername/{username}")
    public Response userExists(@PathVariable(name = "username") String username) {
        Response resp = new Response<Expenses>();

        List<Expenses> expenses = expensesRepository.findByUser_Username(username);

        if(expenses != null) {

            resp.setBody(expenses);
            resp.setStatus(200);

        } else {
            resp.setStatus(404);
        }

        return resp;
    }

    @PostMapping("/add/{username}")
    public Response addExpense(@PathVariable(name = "username") String username, @RequestBody Expenses expense) {
        Response resp = new Response();

        User user = userRepository.findByUsername(username);

        expense.setUser(user);

        try {
            expensesRepository.save(expense);

            resp.setStatus(200);
            resp.setMessage("Despesa salva com sucesso");
        } catch (DataAccessException err) {
            resp.setStatus(400);
            resp.setMessage(err.getRootCause().getMessage());
        }

        return resp;
    }
}
