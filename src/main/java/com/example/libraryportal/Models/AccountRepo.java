package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Boolean existsAccountByStudentIdAndPassword(String username, String Password);
    Account findAccountByStudentId(String username);
    Account findAccountByAccountID(Long ID);

    List<Account>findAllByStudentId(String name);

}
