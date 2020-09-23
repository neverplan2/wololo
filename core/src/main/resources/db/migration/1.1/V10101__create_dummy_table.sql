create table billionaires (
  id int AUTO_INCREMENT  primary key,
  first_name varchar(250) not null,
  last_name varchar(250) not null,
  career varchar(250) default null
);
