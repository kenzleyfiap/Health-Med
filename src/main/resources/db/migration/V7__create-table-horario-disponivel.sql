CREATE TABLE horario_disponivel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dia VARCHAR(10) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    medico_id BIGINT,
    CONSTRAINT fk_medico_id FOREIGN KEY (medico_id) REFERENCES medicos(id)
);
