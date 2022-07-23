create schema vem_ser;

set schema 'vem_ser';

CREATE TABLE Pessoa (
  id_pessoa numeric NOT NULL,
  id_pet numeric NULL,
  nome text NULL,
  data_nascimento DATE,
  cpf text NOT NULL,
  email text NOT NULL,
  PRIMARY KEY(id_pessoa)
);

CREATE SEQUENCE seq_pessoa2
INCREMENT 1
START 1;

INSERT INTO PESSOA
(ID_PESSOA, NOME, DATA_NASCIMENTO, CPF, EMAIL)
VALUES(nextval('seq_pessoa2'), 'Maicon Machado Gerardi', TO_DATE('08-09-1991', 'dd-mm-yyyy'), '48863250090', 'maicon.gerardi@dbccompany.com.br');

INSERT INTO PESSOA
(ID_PESSOA, NOME, DATA_NASCIMENTO, CPF, EMAIL)
VALUES(nextval('seq_pessoa2'), 'Pedro Dantas', TO_DATE('08-09-1980', 'dd-mm-yyyy'), '29629873036', 'pedro@dbccompany.com.br');

INSERT INTO PESSOA
(ID_PESSOA, NOME, DATA_NASCIMENTO, CPF, EMAIL)
VALUES(nextval('seq_pessoa2'), 'Brenda Da Silva', TO_DATE('06-05-1992', 'dd-mm-yyyy'), '01468462067', 'brenda@dbccompany.com.br');

INSERT INTO PESSOA
(ID_PESSOA, NOME, DATA_NASCIMENTO, CPF, EMAIL)
VALUES(nextval('seq_pessoa2'), 'Fernanda Rosa', TO_DATE('06-05-1970', 'dd-mm-yyyy'), '01468462007', 'fernanda@dbccompany.com.br');


------ pet
CREATE TABLE PET (
  id_pet numeric NOT NULL,
  id_pessoa numeric NOT NULL,
  nome text NOT NULL,
  tipo numeric NOT NULL,
  PRIMARY KEY(id_pet),
  CONSTRAINT FK_PET_PESSOA FOREIGN KEY (id_pessoa) REFERENCES PESSOA (id_pessoa)
);

ALTER TABLE PESSOA 
   ADD CONSTRAINT FK_PESSOA_PET FOREIGN KEY (id_pet) REFERENCES PET (id_pet);
   
CREATE SEQUENCE seq_pet
INCREMENT 1
START 1;

  -- tipo 0 cachorro 1 gato 2 guaxinim
INSERT INTO PET
(id_pet, id_pessoa, nome, tipo)
VALUES(nextval('seq_pet'), 1, 'Athos', 0);

UPDATE PESSOA 
   SET id_pet = 1 
 WHERE ID_PESSOA = 1;

INSERT INTO PET
(id_pet, id_pessoa, nome, tipo)
VALUES(nextval('seq_pet'), 2, 'Caruzo', 2);

UPDATE PESSOA 
   SET id_pet = 2
 WHERE ID_PESSOA = 2;

INSERT INTO PET
(id_pet, id_pessoa, nome, tipo)
VALUES(nextval('seq_pet'), 4, 'Paçoca', 1);

UPDATE PESSOA 
   SET id_pet = 3
 WHERE ID_PESSOA = 4;


------ contato

CREATE TABLE Contato (
  id_contato numeric NOT NULL,
  id_pessoa numeric NOT NULL,
  tipo numeric,
  numero text,
  descricao text,
  PRIMARY KEY(id_contato),
  CONSTRAINT FK_CONTATO_PESSOA FOREIGN KEY (id_pessoa) REFERENCES Pessoa (id_pessoa)
);

CREATE sequence IF NOT EXISTS seq_contato
INCREMENT 1
START 1;

-- contatos do maicon
INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 1, 0, '51955565585', 'whatsapp');

INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 1, 1, '48335698566', 'trabalho');

-- contatos do pedro
INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 2, 0, '51955565585', 'celular');

INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 2, 1, '48335698566', 'casa');

-- contatos da Brenda
INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 3, 0, '51995866695', 'whatsapp');

-- contatos da Brenda
INSERT INTO CONTATO
(ID_CONTATO, ID_PESSOA, TIPO, NUMERO, DESCRICAO)
VALUES(nextval('seq_contato'), 3, 0, '51995866695', 'whatsapp');

-- contatos da fernanda



------- endere�o
CREATE TABLE Endereco_Pessoa (
  id_endereco numeric NOT NULL,
  tipo numeric NOT NULL,
  logradouro text NOT NULL,
  numero numeric NOT NULL,
  complemento text NULL,
  cep VARCHAR(8) NOT NULL,
  cidade text NOT NULL,
  estado text NOT NULL,
  pais text NOT NULL,
  PRIMARY KEY(id_endereco)
);



CREATE sequence IF NOT EXISTS seq_endereco_contato
INCREMENT 1
START 1;


INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 0, 'Rua José Dos Santos', 120, 'casa', '88080700', 'Florianópolis', 'SC', 'Brasil');

INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 1, 'Rua Pedro Canvas', 800, 'apto 2', '88805500', 'Porto Alegre', 'RS', 'Brasil');

INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 0, 'Rua Pedro Fantin', 800, 'apt 52', '88080700', 'Florianópolis', 'SC', 'Brasil');

INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 0, 'Rua Mario Quintana', 50, NULL, '88805800', 'Santos', 'SP', 'Brasil');

INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 1, 'Avenida Brasil', 1855, NULL, '88754566', 'Rio De Janeiro', 'RJ', 'Brasil');

INSERT INTO ENDERECO_PESSOA
(ID_ENDERECO, TIPO, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, CIDADE, ESTADO, PAIS)
VALUES(nextval('seq_endereco_contato'), 0, 'Avenida Luis Carvalho', 444, NULL, '88987788', 'Rio De Janeiro', 'RJ', 'Brasil');



CREATE TABLE Pessoa_X_Pessoa_Endereco (
  id_pessoa numeric NOT NULL,
  id_endereco numeric NOT NULL,
  PRIMARY KEY(id_pessoa, id_endereco),
  CONSTRAINT FK_CE_PESSOA FOREIGN KEY (id_pessoa) REFERENCES Pessoa (id_pessoa),
  CONSTRAINT FK_CE_ENDERECO_PESSOA FOREIGN KEY (id_endereco) REFERENCES Endereco_Pessoa (id_endereco)
);


-- enderecos maicon
INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(1, 1);

INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(1, 2);

INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(1, 3);

-- enderecos Brenda
INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(3, 3);

-- fernanda
INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(4, 4);

INSERT INTO PESSOA_X_PESSOA_ENDERECO
(ID_PESSOA, ID_ENDERECO)
VALUES(4, 5);