-- Добавление колонки email
ALTER TABLE employee ADD COLUMN email VARCHAR(100);

-- Добавление колонки к email уникальности
ALTER TABLE employee ADD CONSTRAINT unique_email UNIQUE (email);

-- добавление тестовых данных
UPDATE employee SET email = 'example1@example.com' WHERE id = 1;
UPDATE employee SET email = 'example2@example.com' WHERE id = 2;
UPDATE employee SET email = 'example3@example.com' WHERE id = 3;

