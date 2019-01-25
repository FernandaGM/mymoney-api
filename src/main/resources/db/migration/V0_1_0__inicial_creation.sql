CREATE TABLE if not exists  users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) DEFAULT NULL,
  email varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE table if not exists categories (
  id int not null AUTO_INCREMENT PRIMARY key,
  cat_name varchar(20),
  description varchar(40)
);