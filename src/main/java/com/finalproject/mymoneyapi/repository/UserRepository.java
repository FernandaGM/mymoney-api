package com.finalproject.mymoneyapi.repository;

import com.finalproject.mymoneyapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

//    @Query("select u from User u left join u.expenses left join u.incomes where u.username = :username order by ")
//    User findByUsernameWithExpensesAndIncomes(@Param("username") String username);

}
