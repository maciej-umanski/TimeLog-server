DROP schema public cascade;

CREATE schema timeLog;
GRANT ALL ON SCHEMA timeLog TO "admin";
ALTER ROLE "admin" IN DATABASE "timelog" SET search_path TO timeLog;

CREATE TABLE timeLog.USERS (
    id INTEGER PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    surname VARCHAR(50),
    mail VARCHAR(50),
    role INTEGER NOT NULL
);

CREATE TABLE timeLog.TASKS (
    id INTEGER PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    creation_date DATE,
    due_to_date DATE,
    creator INTEGER REFERENCES timeLog.USERS(id),
    assigned_to INTEGER REFERENCES timeLog.USERS(id)
);

CREATE SEQUENCE timeLog.user_seq INCREMENT BY 1 MINVALUE 0;

INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'admin', 'admin', 'admin', 'admin', 'admin@admin.com', 0);
INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'user', 'user', 'user', 'user', 'user@user.com', 2);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'maciej', 'maciej', 'maciej', 'maciej', 'maciej@maciej.com', 1);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'jakub', 'jakub', 'jakub', 'jakub', 'jakub@jakub.com', 1);
