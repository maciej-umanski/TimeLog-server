DROP schema public cascade;

CREATE schema timeLog;
GRANT ALL ON SCHEMA timeLog TO "admin";
ALTER ROLE "admin" IN DATABASE "timelog" SET search_path TO timeLog;

CREATE TABLE timeLog.USERS (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO timeLog.USERS VALUES (1, 'admin', 'admin', 'owner');
INSERT INTO timeLog.USERS VALUES (2, 'user', 'user', 'user');
INSERT INTO timelog.USERS VALUES (3, 'maciej', 'maciej', 'team_leader');
INSERT INTO timelog.USERS VALUES (4, 'jakub', 'jakub', 'team_leader');
