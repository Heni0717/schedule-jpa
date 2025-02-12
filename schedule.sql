
create table schedule
(
    id         bigint auto_increment primary key,
    title      varchar(255) not null,
    content    text         not null,
    created_at datetime ,
    updated_at datetime ,
    user_id bigint not null,
    foreign key (user_id) references user_info(id) on delete cascade
);

create table user_info
(
    id         bigint auto_increment primary key,
    user_name  varchar(100) not null,
    email      varchar(100) not null,
    password   varchar(100) not null,
    created_at datetime,
    updated_at datetime
);

create table comment
(
    id          bigint auto_increment primary key ,
    content     text not null ,
    user_id     bigint not null ,
    schedule_id bigint not null ,
    created_at  datetime,
    updated_at  datetime,
    foreign key (user_id) references user_info(id) on delete cascade ,
    foreign key (schedule_id) references schedule(id) on delete cascade
);