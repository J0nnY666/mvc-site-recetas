INSERT INTO ingredientes(nome) VALUES('Óleo') ,('Cenoura') ,('Ovos') ,('Açucar') ,('Farinha de trigo') ,
('Fermento em pó') ,('Manteiga'), ('Chocolate em pó'), ('Leite');

INSERT INTO unidade_medida(nome) VALUES('Xícaras(chá)'), ('Colher(sopa)'), ('ML'), ('Gramas'), ('KG');

INSERT INTO receita(nome, tempo, tempo_preparo, modo_preparo) VALUES('Bolo de Cenoura','Minutos',40, 
'MASSA

* Em um liquidificador, adicione 3 cenouras, 4 ovos e  1/2 xícara(chá) de óleo, depois misture.  
* Acrescente 3 xícaras(chá) e bata novamente por 5 minutos.
* Em uma tigela ou na batedeira, adicione 2 e 1/2 xícaras (chá) de farinha de trigo e depois misture.
* Acrescente 1 colher(sopa) de fermento em pó e misture lentamente com uma colher.
* Asse em um forno preaquecido a 180ºC por aproximadamente 40 minutos.


			COBERTURA

* Despenje em uma tigela 1 colher(sopa) de manteiga, 3 colheres(sopa) de chocolate em pó, 1 xícara(chá) de açúcar e 1 
xícara(chá) de leite e misture.
* Leve a mistura ao fogo e continue misturando até obter uma consistência cremosa, depois despeje a calda por cima do bolo.
');

INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 1);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 2);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 3);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 4);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 5);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 6);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 7);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 8);
INSERT INTO lista_ingredientes (receita_id, ingredientes_id) VALUES (1, 9);

INSERT INTO receita_unidade_medida (receita_id, unidade_medida_id) VALUES (1, 1);
INSERT INTO receita_unidade_medida (receita_id, unidade_medida_id) VALUES (1, 2);

INSERT INTO usuario (id, login, nome, senha) VALUES ('1', 'admin@gft.com', 'Admin GFT', '$2a$10$p6GBdQRuaADGGAWzL9DCneMG5cswjfiOFOPlPvyMZWXtF3zw8ByMK');
INSERT INTO usuario (id, login, nome, senha) VALUES ('2', 'usuario@gft.com', 'Usuario GFT', '$2a$10$p6GBdQRuaADGGAWzL9DCneMG5cswjfiOFOPlPvyMZWXtF3zw8ByMK');

INSERT INTO `desafio`.`role` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `desafio`.`role` (`id`, `role`) VALUES ('2', 'USER');

INSERT INTO `desafio`.`usuario_role` (`usuario_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `desafio`.`usuario_role` (`usuario_id`, `role_id`) VALUES ('2', '2');