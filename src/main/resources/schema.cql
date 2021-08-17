CREATE KEYSPACE IF NOT EXISTS families WITH replication ={'class': 'SimpleStrategy','replication_factor': 1};

CREATE TABLE IF NOT EXISTS families.expenses (
    expense_id int,
    expense_name text,
    expense_amount double,
    PRIMARY KEY(expense_id, expense_name)

);
INSERT into families.expenses (expense_id, expense_name, expense_amount) values(1, 'mortgage', 2500);
INSERT into families.expenses (expense_id, expense_name, expense_amount) values(2, 'credit card', 300);
