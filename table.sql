Drop database if exists schooldb;
create database if not exists schooldb;
use schooldb;

create table if not exists client(
id int primary key auto_increment,
name varchar(45),
address varchar(45),
email varchar(45),
phoneNumber varchar(45));

create table if not exists product(
id int primary key auto_increment,
name varchar(45),
price double,
stock int);

create table if not exists orders(
id int primary key auto_increment,
idClient int,
idProduct int,
quantity int,
index(idClient),
index(idProduct),
foreign key (idClient) references client(id),
foreign key (idProduct) references product(id)
);

