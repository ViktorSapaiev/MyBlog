CREATE TABLE roles (
    role_id   INTEGER      NOT NULL ,
    role VARCHAR(128) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE SEQUENCE hibernate_sequence START WITH 10;

CREATE TABLE users (
    user_id   INTEGER  NOT NULL,
    username VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    PRIMARY KEY (user_id),
);

CREATE TABLE user_roles
(
    user_id integer NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id )
);

CREATE TABLE articles (
    id   INTEGER      NOT NULL,
    user_id VARCHAR(128) NOT NULL,
    date TIMESTAMP without time zone NOT NULL,
    header VARCHAR(300) NOT NULL,
    text TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (user_id )
);

