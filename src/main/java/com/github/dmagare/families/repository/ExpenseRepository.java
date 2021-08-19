package com.github.dmagare.families.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.dmagare.families.budget.Expense;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

@Repository
public class ExpenseRepository {
    //logger for the class
    private static final Logger log=
        (Logger) LoggerFactory.getLogger("cass.expense.repo") ;
    private final CqlSession session;
    public ExpenseRepository(CqlSession session) {
        this.session = session;
    }

    public Expense create(Expense expense){
        SimpleStatement stmt = SimpleStatement.builder("INSERT INTO families.expenses (expense_id, expense_name, expense_amount) values(?,?,?)")
                .addPositionalValues(expense.getExpense_id(), expense.getExpense_name(), expense.getExpense_amount())
                .build();
        Flux.from(session.executeReactive( stmt)).subscribe();
        return expense;
    }

    public Flux<Expense> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM families.expenses"))
                .map(row -> new Expense(row.getInt("expense_id"), row.getString("expense_name"), row.getDouble("expense_amount")));
    }

    public Mono<Expense> get(int expense_id) {
        return Mono.from(session.executeReactive("SELECT * FROM families.expenses WHERE expense_id =" + expense_id))
                .map(row -> new Expense(row.getInt("expense_id"), row.getString("expense_name"), row.getDouble("expense_amount")));
    }
}




