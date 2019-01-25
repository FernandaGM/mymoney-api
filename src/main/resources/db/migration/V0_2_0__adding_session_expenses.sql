CREATE table if not exists expenses (
  id int not null AUTO_INCREMENT PRIMARY key,
  user_id bigint(20) not null,
  description varchar(40),
  `value` float,
  created_at timestamp,
  foreign key (user_id) references users(id)
);

create table if not exists expenses_categories (
  id int not null AUTO_INCREMENT PRIMARY key,
  categories_id int not null,
  expenses_id int not null,
  foreign key (categories_id) references categories(id),
  foreign key (expenses_id) references expenses(id)
);
