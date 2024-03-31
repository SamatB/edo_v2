INSERT INTO question (creation_date, status, summary, appeal_id)
SELECT now(),
       'REGISTERED',
       'test question',
       1 WHERE EXISTS (SELECT 1 FROM appeal WHERE id = 1);
