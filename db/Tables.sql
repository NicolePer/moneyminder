CREATE TABLE users (
 id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
 username VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE,
 password_hash VARCHAR(255) NOT NULL
 );


