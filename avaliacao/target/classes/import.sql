insert into contato(nome, telefone, email) values ('Jefferson', '4432633322', 'a@a.com.br'), ('Amanda', '4432633322', 'teste@validacao.com.br'), ('Clarinda', '4432633322', 'a@a.com.br');
insert into pessoa(nome, cpf, data_nascimento) values ('Jefferson', '53108328077', '1990-04-02'), ('Clarinda', '09910043025', '1954-09-22'), ('Amanda', '20530756005', '1990-05-24');
create table if not exists pessoa_contato(pessoa_id int,contato_id int);
insert into pessoa_contato(pessoa_id, contato_id) values (1, 2), (2, 1), (3, 3);

