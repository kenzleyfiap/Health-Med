INSERT INTO usuarios (login, password, role)
VALUES
('usuario1', '$2a$10$S1wJ1hL7cM1eRt8fI8xpi.5THXyI.JUlg4dCRgxTMOw.aqXlKs4/2', 'ADMIN'),
('usuario2', '$2a$10$ZmQ4OdM2MeTqZ1Z5qXl/qOys0FxDdQD7hFZqeqOY/C4Viwaqf5w/e', 'MEDICO'),
('usuario3', '$2a$10$pA/bO5xR0SaJ6iL5e8J.i.JMzk1kpO3nJIV3AaD2LTUylg58t2TiS', 'PACIENTE'),
('usuario4', '$2y$10$risT.UuqhdO5Kjrvt8b.G.i0bduCNNnGEuM3qm/vat1gmOt.h5/Te', 'PACIENTE');;

INSERT INTO pacientes(nome, email, cpf, ativo) VALUES
('João da Silva', 'joao.silva@example.com', '12345678900', 1),
('Maria Oliveira', 'maria.oliveira@example.com', '98765432100', 1),
('Carlos Pereira', 'carlos.pereira@example.com', '11122233344', 0);

INSERT INTO medicos (nome, cpf, crm, email, especialidade, ativo) VALUES
('Alessandra Alana Mariana Rocha', '31462526942', '44444', 'alessandra@mail.com', 'ORTOPEDIA', 1),
('Carlos Silva', '12345678901', '55555', 'carlos@mail.com', 'CARDIOLOGIA', 1),
('Fernanda Oliveira', '98765432100', '66666', 'fernanda@mail.com', 'ORTOPEDIA', 1),
('Júlio César', '45678912301', '77777', 'julio@mail.com', 'DERMATOLOGIA', 1);

INSERT INTO horario_disponivel (dia, hora_inicio, hora_fim, medico_id) VALUES
('MONDAY', '08:00:00', '12:00:00', 1),  -- Alessandra Rocha
('MONDAY', '14:00:00', '18:00:00', 1),  -- Alessandra Rocha
('TUESDAY', '09:00:00', '13:00:00', 1), -- Alessandra Rocha
('WEDNESDAY', '08:00:00', '12:00:00', 1), -- Alessandra Rocha
('THURSDAY', '14:00:00', '18:00:00', 1), -- Alessandra Rocha
('FRIDAY', '08:00:00', '12:00:00', 1), -- Alessandra Rocha

('MONDAY', '09:00:00', '13:00:00', 2),  -- Carlos Silva
('TUESDAY', '14:00:00', '18:00:00', 2), -- Carlos Silva
('WEDNESDAY', '08:00:00', '12:00:00', 2), -- Carlos Silva
('THURSDAY', '10:00:00', '14:00:00', 2), -- Carlos Silva
('FRIDAY', '09:00:00', '13:00:00', 2), -- Carlos Silva

('MONDAY', '08:00:00', '12:00:00', 3),  -- Fernanda Oliveira
('WEDNESDAY', '14:00:00', '18:00:00', 3), -- Fernanda Oliveira
('THURSDAY', '09:00:00', '13:00:00', 3), -- Fernanda Oliveira
('FRIDAY', '08:00:00', '12:00:00', 3), -- Fernanda Oliveira

('TUESDAY', '10:00:00', '14:00:00', 4), -- Júlio César
('WEDNESDAY', '08:00:00', '12:00:00', 4), -- Júlio César
('THURSDAY', '14:00:00', '18:00:00', 4), -- Júlio César
('FRIDAY', '09:00:00', '13:00:00', 4); -- Júlio César

