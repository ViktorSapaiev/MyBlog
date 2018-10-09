CREATE TABLE roles (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    role VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    username VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    role_id INTEGER NOT NULL,
    password VARCHAR(128) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);


CREATE TABLE articles (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(128) NOT NULL,
    date TIMESTAMP without time zone NOT NULL,
    header VARCHAR(300) NOT NULL,
    text TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

