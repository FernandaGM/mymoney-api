package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.entities.Entry;
import com.finalproject.mymoneyapi.entities.User;
import com.finalproject.mymoneyapi.model.*;
import com.finalproject.mymoneyapi.repository.EntryRepository;
import com.finalproject.mymoneyapi.repository.UserRepository;
import com.finalproject.mymoneyapi.util.DataUtils;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntryRepository entryRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/new")
    public Response signUp(@RequestBody User user) {
        Response resp = new Response<User>();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);

            resp.setMessage("Usuário criado com sucesso");
            resp.setStatus(200);
            resp.addToBody(user);
        } catch (DataAccessException err) {
            resp.setStatus(400);
            resp.setMessage(err.getRootCause().getMessage());
        }

        return resp;

    }

    @GetMapping("/getuser/{username}")
    public SimpleUser getUser(@PathVariable(name = "username") String username) {

        SimpleUser user = this.transformUser(userRepository.findByUsername(username));

        return user;
    }

    @PutMapping("/update/{username}")
    public Response update(@PathVariable(name = "username") String username, @RequestBody User updatedUser) {
        Response resp = new Response();

        User user = userRepository.findByUsername(username);;

        if (user != null) {

            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setPhone(updatedUser.getPhone());

            try {
                userRepository.save(user);

                resp.setMessage("Usuário atualizado com sucesso");
                resp.setStatus(200);

            } catch (DataAccessException err) {
                resp.setStatus(400);
                resp.setMessage(err.getRootCause().getMessage());
            }

        } else {
            resp.setStatus(404);
            resp.setMessage("Não foi possivel atualizar o cadastro, usuário não encontrado");
        }

        return resp;

    }

    @GetMapping("/exists/{username}")
    public boolean userExists(@PathVariable(name = "username") String username) {
        if(userRepository.existsByUsername(username)) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/dashboards/{username}/{month}/{year}")
    public ArrayList getDashboardData(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year) {
        Pair<Date, Date> dates = DataUtils.getDateRangeFromYearMonth(month, year);

        System.out.println("Dashboard");

        if(entryRepository.countByUser_UsernameAndCreatedAtBetween(username, dates.getFirst(), dates.getSecond()) > 0) {
            // Get data
            List<Entry> entries = entryRepository.findByUser_UsernameAndCreatedAtBetweenOrderByCreatedAtDesc(
                    username, dates.getFirst(), dates.getSecond());

            double totalReceitas = 0.0;
            double totalDespesas = 0.0;

            Entry[] lastEntries = new Entry[4];

            int x = 0;

            for (Entry entry: entries) {
                if(entry.getIsIncome().equals('S')) {
                    totalReceitas += entry.getValue();
                } else {
                    totalDespesas += entry.getValue();
                }
                if(x < 4) {
                    lastEntries[x] = entry;
                    x++;
                }
            }

            // Calculos para o dash

            ArrayList array = new ArrayList();

            DataSet[] dados = new DataSet[2];

            double[] valores = new double[1];

            valores[0] = totalReceitas;

            double[] valores2 = new double[1];

            valores2[0] = totalDespesas;

            dados[0] = new DataSet("Incomes", "#13f558", "#00e50a", valores);
            dados[1] = new DataSet("Expenses", "#f53f27", "#e5001d", valores2);

            array.add(new DashboardData(new String[0], dados));

            array.add(lastEntries);

            array.add(totalReceitas > totalDespesas ? totalReceitas : totalDespesas);

            return array;
        }

        return new ArrayList();
    }

    @PutMapping("/reset-password/{username}")
    public boolean resetPassword(@PathVariable(name = "username") String username,
                                  @RequestBody String password) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(bCryptPasswordEncoder.encode(password));

            try {
                userRepository.save(user);
                return true;
            } catch (DataAccessException ex) {
                System.out.println(ex.getRootCause().getMessage());
                return false;
            }

        }

        return false;
    }

    @GetMapping("/getuserdata/{username}")
    public UserData getUserData(@PathVariable(name = "username") String username) {

        SimpleUser user = this.transformUser(userRepository.findByUsername(username));

        double[] totais = new double[2];
        int[] qtd = new int[2];

        totais[0] = entryRepository.totalByUser_UsernameAndIsIncome(username, "N");
        totais[1] = entryRepository.totalByUser_UsernameAndIsIncome(username, "S");

        qtd[0] = entryRepository.countByUser_UsernameAndIsIncome(username, "N");
        qtd[1] = entryRepository.countByUser_UsernameAndIsIncome(username, "S");

        return new UserData(user, totais, qtd);
    }

    private SimpleUser transformUser(User user) {

        if (user != null) {

            SimpleUser sUser = new SimpleUser();

            sUser.setEmail(user.getEmail());
            sUser.setUsername(user.getUsername());
            sUser.setFirstName(user.getFirstName());
            sUser.setLastName(user.getLastName());
            sUser.setEmail(user.getEmail());

            return sUser;
        }

        return null;
    }
}
