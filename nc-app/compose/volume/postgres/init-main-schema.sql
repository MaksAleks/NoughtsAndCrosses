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
     next_move varchar(50) not null check ( next_move = created_by OR next_move = second_player ),
     status varchar(10) not null,
     left_moves_count bigint not null check ( left_moves_count >= 0 ),
     version bigint default 0,
     field_size int not null check ( field_size >= 3 )
);
create table move(
    id uuid default uuid_generate_v4 () not null primary key,
    x_pos int not null check ( x_pos > 0 ),
    y_pos int not null check ( y_pos > 0),
    game_name varchar(50) not null ,
    user_name varchar(50) not null ,
    created_time timestamp not null ,
    constraint fk_move_game_name foreign key (game_name) references game(name),
    constraint move_pos_for_game_unq unique (x_pos, y_pos, game_name)
)