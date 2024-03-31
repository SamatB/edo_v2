INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       1,
       2,
       3,
       1,
       1 WHERE EXISTS (SELECT 1 FROM question where id = 1);
----------------------------------------------------------------------------------------------
INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       1,
       2,
       3,
       2,
       1 WHERE EXISTS (SELECT 1 FROM question where id = 1);
----------------------------------------------------------------------------------------------
INSERT INTO resolution (creation_date, last_action_date, type, creator_id, signer_id,
                        curator_id, serial_number, question_id)
SELECT now(),
       '2025-03-28 14:22:00.0',
       'RESOLUTION',
       1,
       2,
       3,
       3,
       1 WHERE EXISTS (SELECT 1 FROM question where id = 1);