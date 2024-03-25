CREATE TABLE IF NOT EXISTS appeal_questions
(
    appeal_id   BIGSERIAL,
    question_id BIGSERIAL,
    FOREIGN KEY (appeal_id) REFERENCES appeal (id),
    FOREIGN KEY (question_id) REFERENCES question (id)
);

INSERT INTO question (id, creation_date, summary, appeal_id, status)
VALUES (1, '2024-03-24 01:26:24.000000', 'test a1 q1', 1, 'REGISTERED'),
       (2, '2024-03-24 01:27:26.000000', 'test a2 q1', 2, 'REGISTERED'),
       (3, '2024-03-24 01:27:56.000000', 'test a1 q2', 1, 'REGISTERED'),
       (4, '2024-03-24 01:28:55.000000', 'test a2 q2', 2, 'REGISTERED'),
       (5, '2024-03-24 01:29:03.000000', 'test a1 q3', 1, 'REGISTERED');

INSERT INTO appeal_questions (appeal_id, question_id)
VALUES (1, 1),
       (1, 3),
       (1, 5),
       (2, 2),
       (2, 4);