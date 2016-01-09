CREATE DATABASE netstore;
USE netstore;
CREATE TABLE categorys(
	id VARCHAR(100) PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(255)
);

create table books(
	id varchar(100) primary key,
	name varchar(100) not null unique,
	author varchar(100),
	price float(8,2),
	path varchar(100),
	photoFileName varchar(100),
	description varchar(255),
	categoryId varchar(100),
	constraint category_id_fk foreign key (categoryId) references categorys(id)
);
