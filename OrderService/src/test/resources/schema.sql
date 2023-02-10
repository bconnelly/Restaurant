create table orders
(
    id integer               auto_increment
        primary key,
    first_name   varchar(50) null,
    dish         varchar(50) null,
    table_number int         null,
    bill         float       null
);