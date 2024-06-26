/*
 добавление тестовых данных в author
 */
INSERT INTO author (id, first_name, last_name, middle_name, address, snils, mobile_phone, email, employment, fio_dative,
                    fio_genitive, fio_nominative)
VALUES (1, 'Александр', 'Петров', 'Иванович', 'г. Москва, ул. Тверская, д. 15, кв. 322', '112-233-445 99',
        '+79123456789', 'a.petrov@example.com', 'WORKER', 'Петрову Александру Ивановичу',
        'Петрова Александра Ивановича', 'Петров Александр Иванович'),
       (2, 'Мария', 'Иванова', 'Сергеевна', 'г. Санкт-Петербург, ул. Невская, д. 10, кв. 25', '555-123-456 78',
        '+79012345678', 'm.ivanova@example.com', 'STUDENT', 'Ивановой Марии Сергеевне',
        'Ивановой Марии Сергеевны', 'Иванова Мария Сергеевна'),
       (3, 'Андрей', 'Смирнов', 'Васильевич', 'г. Екатеринбург, ул. Ленина, д. 25, кв. 10', '777-555-444 33',
        '+79876543210', 'a.smirnov@example.com', 'UNEMPLOYED', 'Смирнову Андрею Васильевичу',
        'Смирнова Андрея Васильевича', 'Смирнов Андрей Васильевич'),
       (4, 'Иван', 'Сидоров', 'Петрович', 'г. Санкт-Петербург, ул. Невский проспект, д. 25, кв. 512', '123-456-789 00',
        '+79872984338', 'i.sidorov@example.com', 'WORKER', 'Сидорову Ивану Петровичу',
        'Сидорова Ивана Петровича', 'Сидоров Иван Петрович'),
       (5, 'Екатерина', 'Никитина', 'Александровна', 'г. Новосибирск, ул. Ленинградская, д. 5, кв. 101',
        '987-654-321 11',
        '+79018435738', 'e.nikitina@example.com', 'STUDENT', 'Никитиной Екатерине Александровне',
        'Никитиной Екатерины Александровны', 'Никитина Екатерина Александровна'),
       (6, 'Андрей', 'Иванов', 'Петрович', 'г. Якутск, ул. Чернышевского, д. 8, кв. 87',
        '957-123-321 11',
        '+79028543039', 'a.ivanov@example.com', 'UNEMPLOYED', 'Иванову Андрею Петровичу',
        'Иванова Андрея Петровича', 'Иванов Андрей Петрович');
