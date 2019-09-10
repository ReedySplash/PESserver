create table roles (id int not null, name varchar(255), primary key (id));
create table users (email varchar(255) not null, creation_date timestamp, enabled boolean not null, modified_date timestamp, password varchar(255), username varchar(255), primary key (email));
create table users_roles (user_id varchar(255) not null, role_id int not null);
alter table roles add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles;
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users;
