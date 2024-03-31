INSERT INTO resolution_executor (resolution_id, employee_id)
SELECT resolution.id,
       employee.id
FROM (SELECT 1, 1 AS id) AS dummy
         INNER JOIN resolution ON resolution.id = 1
         INNER JOIN employee ON employee.id = 1;
----------------------------------------------------------------------------------------------
INSERT INTO resolution_executor (resolution_id, employee_id)
SELECT resolution.id,
       employee.id
FROM (SELECT 2, 2 AS id) AS dummy
         INNER JOIN resolution ON resolution.id = 2
         INNER JOIN employee ON employee.id = 2;
----------------------------------------------------------------------------------------------
INSERT INTO resolution_executor (resolution_id, employee_id)
SELECT resolution.id,
       employee.id
FROM (SELECT 3, 3 AS id) AS dummy
         INNER JOIN resolution ON resolution.id = 3
         INNER JOIN employee ON employee.id = 3;
