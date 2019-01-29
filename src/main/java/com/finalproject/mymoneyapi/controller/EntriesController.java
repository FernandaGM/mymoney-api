package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.entities.Entry;
import com.finalproject.mymoneyapi.model.Response;
import com.finalproject.mymoneyapi.entities.User;
import com.finalproject.mymoneyapi.repository.EntryRepository;
import com.finalproject.mymoneyapi.repository.UserRepository;
import com.finalproject.mymoneyapi.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/entry")
public class EntriesController {
    
    @Autowired
    EntryRepository entryRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/expense/getbyusername/{username}")
    public Response getExpenses(@PathVariable(name = "username") String username) {
        Response resp = new Response<Entry>();

        List<Entry> entries = entryRepository.findExpensesByUser_Username(username);

        if(entries != null) {

            resp.setBody(entries);
            resp.setStatus(200);

        } else {
            resp.setStatus(404);
        }

        return resp;
    }

    @GetMapping("/expense/getbyusername/{username}/{month}/{year}/{page}")
    public List<Entry> getMonthExpenses(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "page") int page) {

        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        List<Entry> entries = entryRepository
                .findByUser_UsernameAndIsIncomeAndCreatedAtBetweenOrderByCreatedAtDesc(
                        username,
                        "N",
                        dates.getFirst(),
                        dates.getSecond(),
                        PageRequest.of(page, 10));

        return entries;
    }

    @GetMapping("/expense/gettotalbyusername/{username}/{month}/{year}")
    public int getTotalMonthExpenses(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year) {
        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        System.out.println("Dados de total");

        return entryRepository.countByUser_UsernameAndIsIncomeAndCreatedAtBetween(
                username,
                "N",
                dates.getFirst(),
                dates.getSecond()
        );
    }

    @PostMapping("/add/{username}")
    public Response addExpense(@PathVariable(name = "username") String username, @RequestBody Entry entry) {
        Response resp = new Response();

        User user = userRepository.findByUsername(username);

        entry.setUser(user);

        try {
            entryRepository.save(entry);

            resp.setStatus(200);
            resp.setMessage("Despesa salva com sucesso");
        } catch (DataAccessException err) {
            resp.setStatus(400);
            resp.setMessage(err.getRootCause().getMessage());
        }

        return resp;
    }

    @PutMapping("/update/{id}")
    public Response updateExpense(@PathVariable(name = "id") Long id, @RequestBody Entry updatedEntry) {

        Response resp = new Response();

        Entry entry = entryRepository.findById(id).get();

        if(entry != null) {
            try {
                // Update entry

                entryRepository.save(entry);

                resp.setStatus(200);
                resp.setMessage("Despesa atualizada com sucesso");
            } catch (DataAccessException err) {
                resp.setStatus(400);
                resp.setMessage(err.getRootCause().getMessage());
            }
        } else {
            resp.setMessage("Não foi possivel atualizar, despesa não encontrada");
            resp.setStatus(404);
        }

        return resp;
    }
}
