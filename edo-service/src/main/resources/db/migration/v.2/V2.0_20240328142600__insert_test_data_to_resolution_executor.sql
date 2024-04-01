INSERT INTO resolution_executor (employee_id, resolution_id)
SELECT 1,
       1 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 1);
----------------------------------------------------------------------------------------------
INSERT INTO resolution_executor (employee_id, resolution_id)
SELECT 2,
       2 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 2);
----------------------------------------------------------------------------------------------
INSERT INTO resolution_executor (employee_id, resolution_id)
SELECT 3,
       3 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 3);
