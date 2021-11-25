CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
drop table if exists authorities;
drop table if exists users;
drop table if exists game;
drop index if exists ix_auth_username;

create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
create table game(
    id uuid default uuid_generate_v4 () not null primary key,
    name varchar(50) constraint uq_game_name unique,
    created_by varchar(50) not null,
    second_player varchar(50) null ,
    status varchar(10) not null,
    field_size int not null,
    constraint fk_user_name foreign key(created_by) references users(username)
);