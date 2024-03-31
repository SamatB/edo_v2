INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       1,
       1 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 1);
----------------------------------------------------------------------------------------------
INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       1,
       2 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 2);
----------------------------------------------------------------------------------------------
INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       1,
       3 WHERE EXISTS (SELECT 1 FROM resolution WHERE id = 3);