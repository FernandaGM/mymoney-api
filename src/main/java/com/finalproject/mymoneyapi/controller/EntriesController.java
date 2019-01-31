package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.entities.Entry;
import com.finalproject.mymoneyapi.model.Response;
import com.finalproject.mymoneyapi.entities.User;
import com.finalproject.mymoneyapi.model.SimpleEntry;
import com.finalproject.mymoneyapi.repository.EntryRepository;
import com.finalproject.mymoneyapi.repository.UserRepository;
import com.finalproject.mymoneyapi.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<SimpleEntry> getMonthExpenses(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "page") int page) {

        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        List<Entry> entries = entryRepository
                .findByUser_UsernameAndIsIncomeAndDataBetweenOrderByDataDesc(
                        username,
                        "N",
                        dates.getFirst(),
                        dates.getSecond(),
                        PageRequest.of(page, 10));

        return transformEntries(entries);
    }

    @GetMapping("/expense/gettotalbyusername/{username}/{month}/{year}")
    public int getTotalMonthExpenses(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year) {
        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        System.out.println("Dados de total");

        return entryRepository.countByUser_UsernameAndIsIncomeAndDataBetween(
                username,
                "N",
                dates.getFirst(),
                dates.getSecond()
        );
    }

    @GetMapping("/income/getbyusername/{username}/{month}/{year}/{page}")
    public List<SimpleEntry> getMonthIncomes(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "page") int page) {

        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        List<Entry> entries = entryRepository
                .findByUser_UsernameAndIsIncomeAndDataBetweenOrderByDataDesc(
                        username,
                        "S",
                        dates.getFirst(),
                        dates.getSecond(),
                        PageRequest.of(page, 10));

        return transformEntries(entries);
    }

    @GetMapping("/income/gettotalbyusername/{username}/{month}/{year}")
    public int getTotalMonthIncomes(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year) {
        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        System.out.println("Dados de total");

        return entryRepository.countByUser_UsernameAndIsIncomeAndDataBetween(
                username,
                "S",
                dates.getFirst(),
                dates.getSecond()
        );
    }

    @PostMapping("/add/{username}")
    public boolean addEntry(@PathVariable(name = "username") String username, @RequestBody Entry entry) {

        User user = userRepository.findByUsername(username);

        entry.setUser(user);

        try {
            entryRepository.save(entry);

            return true;
        } catch (DataAccessException err) {
            System.out.println(err.getRootCause().getMessage());
        }

        return false;
    }

    @PutMapping("/update/{id}")
    public boolean updateEntry(@PathVariable(name = "id") Long id, @RequestBody Entry updatedEntry) {

        Entry entry = entryRepository.findById(id).get();

        if(entry != null) {
            try {
                // Update entry
                entry.setCategories(updatedEntry.getCategories());
                entry.setData(updatedEntry.getData());
                entry.setDescription(updatedEntry.getDescription());
                entry.setIsIncome(updatedEntry.getIsIncome());
                entry.setValue(updatedEntry.getValue());

                entryRepository.save(entry);

                return true;
            } catch (DataAccessException err) {
                System.out.println(err.getRootCause().getMessage());
            }
        }

        return false;
    }

    @GetMapping("/get/{id}")
    public SimpleEntry getEntry(@PathVariable("id") Long id) {

        Entry entry = entryRepository.findById(id).get();

        if (entry != null) {
            return new SimpleEntry(entry);
        }

        return null;
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeEntry(@PathVariable("id") Long id) {
        try {
            entryRepository.deleteById(id);
            return true;
        } catch (DataAccessException err) {
            System.out.println(err.getRootCause().getMessage());
        }
        return false;
    }

    public List<SimpleEntry> transformEntries(List<Entry> entries) {
        ArrayList<SimpleEntry> simpleEntries = new ArrayList<SimpleEntry>();

        for (Entry entry: entries) {
            simpleEntries.add(new SimpleEntry(entry));
        }

        return simpleEntries;
    }
}
