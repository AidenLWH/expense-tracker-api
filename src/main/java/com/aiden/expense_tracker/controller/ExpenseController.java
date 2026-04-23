package com.aiden.expense_tracker.controller;

import com.aiden.expense_tracker.model.Expense;
import com.aiden.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")   
public class ExpenseController {
    
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // GET all expenses
    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }

    // POST - add new expense
    @PostMapping
    public ResponseEntity<Expense> add(@Valid @RequestBody Expense expense) {
        return ResponseEntity.ok(service.addExpense(expense));
    }

    // PUT - update existing expense
    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        return ResponseEntity.ok(service.updateExpense(id, expense));
    }

    // DELETE - remove expense by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    // GET expenses by category
    @GetMapping("/category/{category}")
    public List<Expense> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    // GET total expenses
    @GetMapping("/total")
    public Double getTotal() {
        return service.getTotal();
    }
}
