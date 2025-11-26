CREATE TABLE carts
(
	id binary(16) default (UUID_to_bin(UUID())) not null primary key,
    date_created date default (curdate()) not null
);
create table cart_items
(
	id bigint auto_increment primary key,
    cart_id binary(16) not null ,
    product_id bigint not null ,
    quantity int default 1 not null,


    constraint cart_items_carts_fk
    foreign key (cart_id)
    references carts(id)
    on delete cascade ,

    constraint cart_items_product_fk
    foreign key (product_id)
    references products(id)
    on delete cascade
);