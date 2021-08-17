package com.github.dmagare.families.budget;
import java.util.Objects;

public class Expense {
    private int expense_id;
    private String expense_name;
    private double expense_amount;

    public Expense() {
    }

    public Expense(int expense_id, String expense_name, double amount) {
        this.expense_id = expense_id;
        this.expense_name = expense_name;
        this.expense_amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expense_id=" + expense_id +
                ", expense_name='" + expense_name + '\'' +
                ", expense_amount=" + expense_amount +
                '}';
    }
@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return expense_id == expense.expense_id && Double.compare(expense.expense_amount, expense_amount) == 0 && Objects.equals(expense_name, expense.expense_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expense_id, expense_name, expense_amount);
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    public double getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(double expense_amount) {
        this.expense_amount = expense_amount;
    }

}

