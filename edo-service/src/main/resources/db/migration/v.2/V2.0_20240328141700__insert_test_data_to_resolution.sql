INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       creator.id,
       signer.id,
       curator.id,
       1,
       question.id
FROM (SELECT 1, 2, 3, 1 AS id) AS dummy
         INNER JOIN employee AS creator ON creator.id = 1
         INNER JOIN employee AS signer ON signer.id = 2
         INNER JOIN employee AS curator ON curator.id = 3
         INNER JOIN employee AS question ON question.id = 1;
----------------------------------------------------------------------------------------------
INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       creator.id,
       signer.id,
       curator.id,
       2,
       question.id
FROM (SELECT 1, 2, 3, 1 AS id) AS dummy
         INNER JOIN employee AS creator ON creator.id = 1
         INNER JOIN employee AS signer ON signer.id = 2
         INNER JOIN employee AS curator ON curator.id = 3
         INNER JOIN employee AS question ON question.id = 1;
----------------------------------------------------------------------------------------------
INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       creator.id,
       signer.id,
       curator.id,
       3,
       question.id
FROM (SELECT 1, 2, 3, 1 AS id) AS dummy
         INNER JOIN employee AS creator ON creator.id = 1
         INNER JOIN employee AS signer ON signer.id = 2
         INNER JOIN employee AS curator ON curator.id = 3
         INNER JOIN employee AS question ON question.id = 1;