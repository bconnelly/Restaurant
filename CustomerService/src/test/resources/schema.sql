create table customers
(
    id           int auto_increment
        primary key,
    first_name   varchar(50) null,
    table_number int         null,
    cash         float       null,
    address      varchar(50) null
);