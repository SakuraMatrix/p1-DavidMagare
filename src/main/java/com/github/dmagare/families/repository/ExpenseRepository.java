package com.github.dmagare.families.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.dmagare.families.budget.Expense;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public class ExpenseRepository {
    private CqlSession session;

    public ExpenseRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Expense> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM families.expense"))
                .map(row -> new Expense(row.getInt("expense_id"), row.getString("expense_name"), row.getDouble("amount")));
    }

    public Mono<Expense> get(int expense_id) {
        return Mono.from(session.executeReactive("SELECT * FROM families.expense WHERE expense_id =" + expense_id))
                .map(row -> new Expense(row.getInt("expense_id"), row.getString("expense_name"), row.getDouble("amount")));
    }

}




