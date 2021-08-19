package com.github.dmagare.families.service;
import com.github.dmagare.families.budget.Expense;
import com.github.dmagare.families.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class ExpenseService {
    private static ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Flux<Expense> getAll() {
        return expenseRepository.getAll();
    }

    public Mono<Expense> get(String expense_id){
        return expenseRepository.get(Integer.parseInt(expense_id));
    }

    public static Expense create(Expense expense){
        return expenseRepository.create(expense);
    }
   // Public Expenses updateExpense(Expenses expense) {return expenseRepository.updateExpenses(expenses); }

}
