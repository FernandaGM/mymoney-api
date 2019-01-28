CREATE table if not exists entries (
  id int not null AUTO_INCREMENT PRIMARY key,
  user_id bigint(20) not null,
  description varchar(40),
  `value` float,
  created_at timestamp,
  is_income varchar(1),
  foreign key (user_id) references users(id)
);

create table if not exists entries_categories (
  id int not null AUTO_INCREMENT PRIMARY key,
  categories_id int not null,
  entry_id int not null,
  foreign key (categories_id) references categories(id),
  foreign key (entry_id) references entries(id)
);
