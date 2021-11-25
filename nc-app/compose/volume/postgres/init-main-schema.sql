\c "nc-db"
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table if not exists game(
    id uuid default uuid_generate_v4 () not null primary key,
    name varchar(50) constraint uq_game_name unique,
    createdBy varchar(50),
    fieldSize int,
    constraint fk_user_name foreign key(createdBy) references users(username)
);
create table if not exists users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table if not exists authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index if not exists ix_auth_username on authorities (username,authority);