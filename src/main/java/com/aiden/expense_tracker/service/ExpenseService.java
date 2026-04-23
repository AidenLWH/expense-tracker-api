package com.aiden.expense_tracker.service;

import com.aiden.expense_tracker.model.Expense;
import com.aiden.expense_tracker.model.User;
import com.aiden.expense_tracker.repository.ExpenseRepository;
import com.aiden.expense_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findByUser(getCurrentUser());
    }

    public Expense addExpense(Expense expense) {
        expense.setUser(getCurrentUser());
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updated) {
        User currentUser = getCurrentUser();
        Expense existing = expenseRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new RuntimeException("Expense not found or not owned by user"));
        existing.setCategory(updated.getCategory());
        existing.setDescription(updated.getDescription());
        existing.setAmount(updated.getAmount());
        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        User currentUser = getCurrentUser();
        Expense existing = expenseRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new RuntimeException("Expense not found or not owned by user"));
        expenseRepository.delete(existing);
    }

    public List<Expense> getByCategory(String category) {
        return expenseRepository.findByUserAndCategory(getCurrentUser(), category);
    }

    public Double getTotal() {
        return expenseRepository.findByUser(getCurrentUser()).stream()
            .mapToDouble(Expense::getAmount)
            .sum();
    }
}
