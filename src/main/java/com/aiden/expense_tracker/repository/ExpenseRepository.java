package com.aiden.expense_tracker.repository;

import com.aiden.expense_tracker.model.Expense;
import com.aiden.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, String category);
    Optional<Expense> findByIdAndUser(Long id, User user);
}
