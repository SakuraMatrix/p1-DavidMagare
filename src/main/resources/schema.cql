CREATE KEYSPACE IF NOT EXISTS families WITH replication ={'class': 'SimpleStrategy','replication_factor': 1}

USE families;

CREATE TABLE IF NOT EXISTS expense (
    expense_id int,
    expense_name text,
    expense_amount double,
    PRIMARY KEY(expense_id, expense_name)

);