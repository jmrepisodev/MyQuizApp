DROP DATABASE IF EXISTS bbdd_quizz;
CREATE DATABASE IF NOT EXISTS bbdd_quizz;
USE bbdd_quizz;

DROP TABLE IF EXISTS detalle_resultados;
DROP TABLE IF EXISTS resultados;
DROP TABLE IF EXISTS respuestas;
DROP TABLE IF EXISTS preguntas;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios;


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
    categoryname VARCHAR(100),
    descripcion VARCHAR(255)

);

CREATE TABLE levels (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    levelname VARCHAR(100)

);

CREATE TABLE questions (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question TEXT,
    option1 TEXT,
    option2 TEXT,
    option3 TEXT,
    option4 TEXT,
    answer INT,
    id_category INT,
    id_level INT,
    CONSTRAINT id_category_fk FOREIGN KEY (id_category) REFERENCES categorias (id) ON DELETE CASCADE,
    CONSTRAINT id_level_fk FOREIGN KEY (id_level) REFERENCES niveles (id) ON DELETE CASCADE
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

