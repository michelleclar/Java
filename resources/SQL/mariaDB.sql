drop table if exists user;
create table user (
  id int not null auto_increment,
  user_name varchar(50) not null,
  created_at timestamp null default current_timestamp(),
  updated_at timestamp null default current_timestamp(),
  primary key (id)
);
