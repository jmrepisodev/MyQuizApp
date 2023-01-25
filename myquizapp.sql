DROP DATABASE IF EXISTS bbdd_quizz;
CREATE DATABASE IF NOT EXISTS bbdd_quizz;
USE bbdd_quizz;

DROP TABLE IF EXISTS detalle_resultados;
DROP TABLE IF EXISTS resultados;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS levels;
DROP TABLE IF EXISTS users;


-- Implementación en SQL del modelo de base de datos

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(50)

);

CREATE TABLE categories (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    image VARCHAR(255)

);

CREATE TABLE levels (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    level VARCHAR(100)

);

CREATE TABLE questions (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question TEXT,
    option1 TEXT,
    option2 TEXT,
    option3 TEXT,
    option4 TEXT,
    correcta INT,
    id_category INT,
    id_level INT,
    CONSTRAINT id_category_fk FOREIGN KEY (id_category) REFERENCES categories (id) ON DELETE CASCADE,
    CONSTRAINT id_level_fk FOREIGN KEY (id_level) REFERENCES levels (id) ON DELETE CASCADE
);


CREATE TABLE resultados (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    id_category INT,
    id_level INT,
    score INT,
    CONSTRAINT id_user_fk FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT id_category2_fk FOREIGN KEY (id_category) REFERENCES categorias (id) ON DELETE CASCADE,
    CONSTRAINT id_level2_fk FOREIGN KEY (id_level) REFERENCES niveles (id) ON DELETE CASCADE
);


CREATE TABLE detalle_resultados (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_resultado INT,
        id_pregunta INT,
	respuesta INT,
        es_correcta BOOLEAN,
	CONSTRAINT id_res_fk FOREIGN KEY (id_resultado) REFERENCES resultados (id) ON DELETE CASCADE,
        CONSTRAINT id_preg2_fk FOREIGN KEY (id_pregunta) REFERENCES questions (id) ON DELETE CASCADE
);