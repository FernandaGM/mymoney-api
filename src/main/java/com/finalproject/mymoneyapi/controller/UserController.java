package com.finalproject.mymoneyapi.controller;

import com.finalproject.mymoneyapi.model.Response;
import com.finalproject.mymoneyapi.model.SimpleUser;
import com.finalproject.mymoneyapi.model.User;
import com.finalproject.mymoneyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

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
    public Response getUser(@PathVariable(name = "username") String username) {
        Response resp = new Response<SimpleUser>();

        SimpleUser user = this.transformUser(userRepository.findByUsername(username));

        if(user != null) {

            resp.addToBody(user);
            resp.setMessage("Usuário encontrado");
            resp.setStatus(200);

        } else {
            resp.setMessage("Usuário não foi encontrado");
            resp.setStatus(404);
        }

        return resp;
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
//        Response resp = new Response();

        if(userRepository.existsByUsername(username)) {
//            resp.setStatus(200);
            return true;
        } else {
//            resp.setStatus(404);
            return false;
        }

//        return resp;
    }

    @GetMapping("/dashboards/{username}")
    public Response getDashboardData(@PathVariable(name = "username") String username) {
        Response resp = new Response();

        // Calculos para o dash
        /*
        this.chartData = {
              labels: [],
              datasets: [
                {
                  label: "Incomes",
                  backgroundColor: "#13f558",
                  borderColor: "#00e50a",
                  data: [80]
                },
                {
                  label: "Expenses",
                  backgroundColor: "#f53f27",
                  borderColor: "#e5001d",
                  data: [65]
                }
              ]
            };

            this.lastEntries = [
              {value: 10, description: "Teste 1", isIncome: "N", categories: ["comida", "supermercado"]},
              {value: 1600, description: "Teste 2", isIncome: "S", categories: ["salario"]},
              {value: 10, description: "Teste 3", isIncome: "N", categories: ["comida", "supermercado"]},
              {value: 400, description: "Teste 4", isIncome: "N", categories: ["compra"]},
            ];
         */

        return resp;
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
