CREATE TABLE IF NOT EXISTS persons
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    email      VARCHAR(100),
    phone      VARCHAR(15),
    job        VARCHAR(100)

);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    password  VARCHAR(100)
);