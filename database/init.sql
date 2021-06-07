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
    creation_date TIMESTAMP,
    due_to_date TIMESTAMP,
    creator INTEGER REFERENCES timeLog.USERS(id)
);

CREATE SEQUENCE timeLog.user_seq INCREMENT BY 1 MINVALUE 0;
CREATE SEQUENCE timeLog.task_seq INCREMENT BY 1 MINVALUE 0;

INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'admin', 'admin', 'admin', 'admin', 'admin@admin.com', 0);
INSERT INTO timeLog.USERS VALUES (nextval('timeLog.user_seq'), 'user', 'user', 'user', 'user', 'user@user.com', 2);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'maciej', 'maciej', 'maciej', 'maciej', 'maciej@maciej.com', 1);
INSERT INTO timelog.USERS VALUES (nextval('timeLog.user_seq'), 'jakub', 'jakub', 'jakub', 'jakub', 'jakub@jakub.com', 1);

INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:02:53.093072', '2021-06-07 21:02:53.096194', '0');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:02:55.592125', '2021-06-07 19:02:55.592236', '1');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:02:56.740682', '2021-06-07 19:02:56.740804', '2');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:02:58.226785', '2021-06-07 19:02:58.226869', '2');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:02:59.754298', '2021-06-07 19:02:59.754432', '1');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:01.014946', '2021-06-07 19:03:01.015133', '0');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:01.936865', '2021-06-07 19:03:01.936957', '1');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:02.835043', '2021-06-07 19:03:02.8351', '0');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:03.691493', '2021-06-07 19:03:03.691631', '2');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:04.479835', '2021-06-07 19:03:04.479901', '2');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:05.313015', '2021-06-07 19:03:05.313106', '0');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:06.098954', '2021-06-07 19:03:06.09902', '1');
INSERT INTO timeLog.TASKS(id, title, description, creation_date, due_to_date, creator) VALUES (nextval('timeLog.task_seq'), 'Title', 'Description', '2021-06-07 19:03:06.928914', '2021-06-07 19:03:06.928992', '0');
