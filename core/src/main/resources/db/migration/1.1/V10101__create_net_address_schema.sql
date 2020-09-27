create table net_address (
  id int AUTO_INCREMENT  primary key,
  name varchar(250) not null,
  description varchar(250) not null,
  mac varchar(250) default null,
  address varchar(250) default null,
  hostname varchar(250) default null,
  cidr varchar(250) default null,
  broadcast_address varchar(250) default null,
  comment varchar(250) default null,
  active boolean default false
);

create table nic_adaptor (
  id int AUTO_INCREMENT  primary key,
  name varchar(250) not null,
  display_name varchar(250) not null,
  hostname varchar(250) default null,
  mac varchar(250) default null,
  broadcast varchar(250) default null,
  netmask varchar(250) default null,
  ipv4Address varchar(250) default null,
  ipv6Address varchar(250) default null,
  index int default 0,
  mtu int default 0
);