INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       executor.id,
       resolution.id
FROM (SELECT 1, 1 AS id) AS dummy
         INNER JOIN employee AS executor ON executor.id = 1
         INNER JOIN resolution ON resolution.id = 1;
----------------------------------------------------------------------------------------------
INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       executor.id,
       resolution.id
FROM (SELECT 2, 2 AS id) AS dummy
         INNER JOIN employee AS executor ON executor.id = 2
         INNER JOIN resolution ON resolution.id = 2;
----------------------------------------------------------------------------------------------
INSERT INTO report (creation_date, result, executor_id, resolution_id)
SELECT now(),
       false,
       executor.id,
       resolution.id
FROM (SELECT 3, 3 AS id) AS dummy
         INNER JOIN employee AS executor ON executor.id = 3
         INNER JOIN resolution ON resolution.id = 3;