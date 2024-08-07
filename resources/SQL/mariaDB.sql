drop table if exists user;
create table user (
  id int not null auto_increment,
  user_name varchar(50) not null,
  email varchar(100) not null,
  password varchar(100) not null,
  phone_number varchar(20),
  version int not null default 0,
  created_at timestamp null default current_timestamp(),
  updated_at timestamp null default current_timestamp(),
  primary key (id),
  unique (email)
);