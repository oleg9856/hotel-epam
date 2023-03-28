create table if not exists hotel_schema.class
(
    class_id     int auto_increment
        primary key,
    class_name   varchar(45)                 not null,
    basic_rating decimal(10, 2) default 0.00 null,
    constraint class_name_UNIQUE
        unique (class_name)
);

create table if not exists hotel_schema.role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(20) not null,
    constraint name_UNIQUE
        unique (role_name)
);

create table if not exists hotel_schema.reservation_status
(
    status_id   mediumint auto_increment
        primary key,
    status_name varchar(45) not null,
    constraint status_name_UNIQUE
        unique (status_name)
);

create table if not exists hotel_schema.status_room
(
    status_id smallint auto_increment
        primary key,
    status    varchar(15) not null
);

create table if not exists hotel_schema.room
(
    id        bigint auto_increment comment 'Apartamemts in an hotel'
        primary key,
    class_id  int                         not null,
    status_id smallint                    not null,
    capacity  smallint       default 1    not null comment 'capacity',
    price     decimal(10, 2) default 0.00 not null,
    constraint fk_apartments_apartments_locale1
        foreign key (class_id) references hotel_schema.room_class (class_id),
    constraint fk_apartments_status_locale1
        foreign key (status_id) references hotel_schema.status_room (status_id)
);

create index fk_apartments_apartments_locale1_idx
    on hotel_schema.room (class_id);

create index fk_apartments_status_locale1_idx
    on hotel_schema.room (status);

create table if not exists hotel_schema.room_details
(
    id          bigint auto_increment
        primary key,
    description varchar(200) not null,
    room_number varchar(5)   not null comment 'room number',
    image       blob         not null comment 'image of room',
    constraint description_UNIQUE
        unique (description),
    constraint fk_table1_apartments1
        foreign key (id) references hotel_schema.room (id)
);

create table if not exists hotel_schema.user
(
    id          bigint                               not null,
    login       varchar(45)                          not null,
    name        varchar(45)                          not null,
    surname     varchar(45)                          not null,
    password    varchar(32)                          not null,
    email       varchar(255)                         not null,
    phonenumber varchar(12)                          not null,
    balance     decimal(15, 2) unsigned default 0.00 null,
    discount    decimal                 default 0    null comment 'discount for user',
    role_id     int                                  not null,
    primary key (id),
    constraint email_UNIQUE
        unique (email),
    constraint login_UNIQUE
        unique (login),
    constraint phonenumber_UNIQUE
        unique (phonenumber),
    constraint fk_users_roles1
        foreign key (role_id) references hotel_schema.role (role_id)
);

create table if not exists hotel_schema.comment
(
    comment_id          bigint auto_increment
        primary key,
    user_id            bigint       not null,
    room_id             bigint       not null,
    date_of_publication datetime     not null,
    description         varchar(150) not null,
    constraint fk_comments_apartments1
        foreign key (room_id) references hotel_schema.room (id),
    constraint fk_comments_users1
        foreign key (user_id) references hotel_schema.user (id)
);

create index fk_comments_apartments1_idx
    on hotel_schema.comment (room_id);

create index fk_comments_users1_idx
    on hotel_schema.comment (user_id);

create table if not exists hotel_schema.`reservation`
(
    reservation_id   bigint auto_increment
        primary key,
    user_id   bigint              not null,
    room_id    bigint              not null,
    price      decimal(10, 2)      not null,
    arrival_date datetime            not null,
    departure_date   datetime            not null,
    status_id  mediumint default 2 not null,
    constraint fk_orders_status_orders1
        foreign key (status_id) references hotel_schema.reservation_status (status_id),
    constraint fk_users_has_apartments_apartments1
        foreign key (room_id) references hotel_schema.room (id),
    constraint fk_users_has_apartments_users1
        foreign key (user_id) references hotel_schema.user (id)
);

create index fk_orders_status_orders1_idx
    on hotel_schema.reservation (status_id);

create index fk_users_has_apartments_apartments1_idx
    on hotel_schema.reservation (room_id);

create index fk_users_has_apartments_users1_idx
    on hotel_schema.reservation (user_id);

create index fk_users_roles1_idx
    on hotel_schema.user (role_id);

# insert data into table class
INSERT INTO hotel_schema.room_class (class_name, basic_rating)VALUES ('LUX', 5.3);
INSERT INTO hotel_schema.room_class (class_name, basic_rating)VALUES ('STANDARD', 4.3);
INSERT INTO hotel_schema.room_class (class_name, basic_rating)VALUES ('VIP', 6.1);

# insert data into table role
INSERT INTO hotel_schema.role (role_name) VALUES ('USER');
INSERT INTO hotel_schema.role (role_name) VALUES ('BLOCK');
INSERT INTO hotel_schema.role (role_name) VALUES ('ADMIN');

# insert data into table status room
INSERT INTO hotel_schema.status_room (status) VALUES ('FREE');
INSERT INTO hotel_schema.status_room (status) VALUES ('BOOKED');
INSERT INTO hotel_schema.status_room (status) VALUES ('BUSY');
INSERT INTO hotel_schema.status_room (status) VALUES ('UNAVAILABLE');

# insert data into table status reservation
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('WAITING');
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('APPROVED');
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('PAID');
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('CHECKED_IN');
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('CHECKED_OUT');
INSERT INTO hotel_schema.reservation_status(status_name) VALUES ('CANCELLED');

# insert data into table user
INSERT INTO hotel_schema.user(id, login, name, surname, password, email, phonenumber, balance, discount, role_id)
VALUES (0, 'jock', 'John', 'Smith', md5(1234), 'email', 089326, 34.6, 0 ,1);
INSERT INTO hotel_schema.user(id, login, name, surname, password, email, phonenumber, balance, discount, role_id)
VALUES (0, 'admin', 'Admin', 'Adminovych', md5('admin'), 'admin@gmail.com', 082436, 1000000, 110000 ,1);

INSERT INTO room (id, class_id, status, beds_amount, price)
VALUES (0,1,1,3,500)

ALTER DATABASE hotel_schema CHARACTER SET utf8 COLLATE utf8_general_ci