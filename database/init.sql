DROP schema public cascade;

CREATE schema timeLog;
GRANT ALL ON SCHEMA timeLog TO "admin";
ALTER ROLE "admin" IN DATABASE "timelog" SET search_path TO timeLog;

CREATE TABLE timeLog.USERS (
    id INTEGER PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role INTEGER NOT NULL
);

CREATE SEQUENCE timeLog.user_seq INCREMENT BY 1 MINVALUE 0;

INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'admin', 'admin', 0);
INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'user', 'user', 2);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'maciej', 'maciej', 1);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'jakub', 'jakub', 1);
