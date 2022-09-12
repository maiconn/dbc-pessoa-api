create table usuario(
    id_usuario NUMBER not null,
    login VARCHAR2(1024) unique not null,
    senha VARCHAR2(1024) not null,
    primary key(id_usuario)
);

create sequence seq_usuario
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

create table cargo (
    id_cargo NUMBER not null,
    nome VARCHAR2(1024) unique not null,
    primary key(id_cargo)
);

create sequence seq_cargo
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

insert into cargo (id_cargo, nome)
values (seq_cargo.nextval, 'ROLE_ADMIN'); -- 1

insert into cargo (id_cargo, nome)
values (seq_cargo.nextval, 'ROLE_USUARIO'); -- 2

insert into cargo (id_cargo, nome)
values (seq_cargo.nextval, 'ROLE_MARKETING'); -- 3

create table usuario_cargo (
    id_usuario NUMBER not null,
    id_cargo NUMBER not null,
    primary key(id_usuario, id_cargo),
    constraint fk_usuario_cargo_cargo foreign key (id_cargo) references cargo (id_cargo),
  	constraint fk_usuario_cargo_usuario foreign key (id_usuario) references usuario (id_usuario)
);

insert into usuario (id_usuario, login, senha)
     values (seq_usuario.nextval, 'maicon', '$2a$10$OSdKzw0K0LaLPyj1EqOWte8U.cpTftzycrK5eQ/Wgu2GfB4wgpWu6'); --1

insert into usuario (id_usuario, login, senha)
     values (seq_usuario.nextval, 'admin', '$2a$10$X.AxPaR2usWOKRyUT2ZX/.k5sdTQNkSUly3AlSibvxImFrNb9ulAu'); --2

insert into usuario (id_usuario, login, senha)
     values (seq_usuario.nextval, 'user', '$2a$10$X.AxPaR2usWOKRyUT2ZX/.k5sdTQNkSUly3AlSibvxImFrNb9ulAu'); --3

insert into usuario (id_usuario, login, senha)
     values (seq_usuario.nextval, 'marketing', '$2a$10$X.AxPaR2usWOKRyUT2ZX/.k5sdTQNkSUly3AlSibvxImFrNb9ulAu'); --4

insert into usuario_cargo (id_usuario, id_cargo)
values (1,1); --maicon / admin

insert into usuario_cargo (id_usuario, id_cargo)
values (1,2); --maicon / user

insert into usuario_cargo (id_usuario, id_cargo)
values (1,3); --maicon / marketing

insert into usuario_cargo (id_usuario, id_cargo)
values (2,1); --admin / admin

insert into usuario_cargo (id_usuario, id_cargo)
values (2,2); --admin / user

insert into usuario_cargo (id_usuario, id_cargo)
values (2,3); --admin / marketing

insert into usuario_cargo (id_usuario, id_cargo)
values (3,2); --user / usuario

insert into usuario_cargo (id_usuario, id_cargo)
values (4,3); --marketing / marketing


