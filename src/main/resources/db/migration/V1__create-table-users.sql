create table users(
    id bigint not null auto_increment,
    name varchar(20) not null,
    email varchar(20) not null,
    password varchar(8) not null,

    primary key(id)
);
