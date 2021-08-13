package com.github.dmagare.families.service;
import com.github.dmagare.families.budget.Expense;
import com.github.dmagare.families.repository.ExpenseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Flux<Expense> getAll() {
        return expenseRepository.getAll();
    }

    public Mono<Expense> get(String expense_id){
        return expenseRepository.get(Integer.parseInt(expense_id));
    }
}
