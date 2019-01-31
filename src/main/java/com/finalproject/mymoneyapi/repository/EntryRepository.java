package com.finalproject.mymoneyapi.repository;

import com.finalproject.mymoneyapi.entities.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query(value = "select u.entries from User u join u.entries e where u.username = ?1 and e.isIncome = 'N'")
    List<Entry> findExpensesByUser_Username(String username);

    @Query(value = "select u.entries from User u join u.entries e where u.username = ?1 and e.isIncome = 'S'")
    List<Entry> findIncomesByUser_Username(String username);

    List<Entry> findByUser_Username(String username);

    List<Entry> findByUser_UsernameAndIsIncomeAndDataBetweenOrderByDataDesc(String username, String isIncome, Date inicio, Date fim, Pageable pageable);

    List<Entry> findByUser_UsernameAndDataBetweenOrderByDataDesc(String username, Date inicio, Date fim);

    Long countByUser_UsernameAndDataBetween(String username, Date inicio, Date fim);

    int countByUser_UsernameAndIsIncome(String username, String isIncome);

    @Query("select coalesce(sum(e.value), 0) from User u join u.entries e where u.username = ?1 and e.isIncome = ?2")
    double totalByUser_UsernameAndIsIncome(String username, String isIncome);

    int countByUser_UsernameAndIsIncomeAndDataBetween(String username, String isIncome, Date inicio, Date fim);
}
