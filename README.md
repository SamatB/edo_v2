<h2>Описание проекта "EDO"</h2>

- [Summary](#summary)
- [Stack](#stack)
- [MVP](#mvp)
- [Backlog](#backlog)
- [Структура проекта](#структура-проекта)
    - [Бэкенд](#бэкенд)
    - [Фронтенд](#фронтенд)
- [Работа на проекте](#работа-на-проекте)
    - [С чего начинать](#с-чего-начинать)
    - [О таскборде](#о-таскборде)
    - [Как выполнять задачи](#как-выполнять-задачи)
    - [Проверка задач](#проверка-задач)
    - [Требования к коду](#требования-к-коду)
    - [Созвоны по проекту](#созвоны-по-проекту)
- [Дополнительные материалы](#дополнительные-материалы)
    - [Spring Boot Dev Tools](#Spring-Boot-Dev-Tools)
    - [Flyway](#flyway)
    - [MinIO](#minio)

[//]: # (    - [Аутентификация]&#40;#аутентификация&#41;)

### Summary

Реализуем функционал обработки обращений граждан и обменом электронными документами между ведомствами.

Проект рассчитан на студентов, успешно завершивших этап Pre-Project в Kata Academy.

### Stack

Проект пишется на базе `Java 17`, `Spring Boot 3`, `Maven` и архитектуре REST.
Работаем с базой данных `PostgreSQL 12` через `Spring Data` и `Hibernate`.

Чтобы не писать boilerplate-код, используем на проекте [Lombok](https://projectlombok.org/features/all).

Все контроллеры и их методы нужно сразу описывать
аннотациями [Swagger](https://docs.swagger.io/swagger-core/v1.5.0/apidocs/allclasses-noframe.html).
Swagger UI при запущенном приложении крутится [здесь](http://localhost:8761/swagger-ui/index.html).

Таск-борд находится прямо на [Gitlab](https://gitlab.com/__-__/edo_v2/-/boards).

Dev-stand отсутствует, будем поднимать и разворачивать локально.

### MVP

[MVP](https://ru.wikipedia.org/wiki/%D0%9C%D0%B8%D0%BD%D0%B8%D0%BC%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE_%D0%B6%D0%B8%D0%B7%D0%BD%D0%B5%D1%81%D0%BF%D0%BE%D1%81%D0%BE%D0%B1%D0%BD%D1%8B%D0%B9_%D0%BF%D1%80%D0%BE%D0%B4%D1%83%D0%BA%D1%82) -
API (полностью описанное в Swagger), которое будет уметь создавать обращения вручную, получать по интеграционным каналам
и оповещать пользователей о новых документах.
Работать с таким API можно будет через веб-интерфейс Swagger и Postman.

### Backlog

Фичи:
<ul>
<li>Создание, редактирование, удаление обращений</li>
<li>Создание личного кабинета работника, добавление аутентификации через логин/пароль, Google, социальные сети</li>
<li>Интеграция с Keycloak</li>
<li>реализация функционала обратной связи с гражданами через e-mail и Telegram</li>
<li>создание ролевой модели для взаимодействия с API</li>
<li>внедрение взаимодействия с яндекс.картами для парсинга адресов</li>
<li>внедрение интеграции с внешними источниками данных для синхронизации данных пользователей</li>
</ul>

Импрувменты:

<ul>
<li>логирование через Slf4j + log4j2</li>
<li>юнит-тесты и интеграционные тесты</li>
<li>анализ качества кода через SonarQube</li>
</ul>

## Структура проекта

### Бэкенд

Проект основан на архетипе webapp.
Слои:
<ul>
<li><code>config</code> конфигурационные классы, в т.ч. Spring Security, инструменты аутентификации</li>
<li><code>entity</code> сущности базы данных</li>
<li><code>dto</code> специальные сущности для передачи/получения данных в/с апи</li>
<li><code>repository</code> dao-слой приложения, реализуем в виде интерфейсов Spring Data, имплементирующих JpaRepository</li>
<li><code>service</code> бизнес-логика приложения, реализуем в виде интерфейсов и имплементирующих их классов</li>
<li><code>controller</code> обычные и rest-контроллеры приложения</li>
<li><code>util</code> пакет для утилитных классов: валидаторов, шаблонов, хэндлеров, эксепшнов</li>
<li><code>feign</code> Feign клиенты для взаимодействия между микросервисами</li>
<li><code>listener</code> слушатели очередей для выборки данных</li>
<li><code>publisher</code> отправители в очереди</li>
</ul>

### Фронтенд

Фронтенд пишет команда фронтенда. Мы команда бэкенда.

## Работа на проекте

### С чего начинать

Доступы. Если ты читаешь это, значит доступ к проекту у тебя уже есть )
<ol>
<li>загрузи проект себе в среду разработки.</li>
<li>изучи весь проект - начни с pom, properties файлов и конфигурационных классов.</li>
<li>создай локальную базу данных PostgeSQL с названием <code>edo_db</code>. Можешь изменить параметры доступа (логин, пароль) в конфиге проекта под свои нужды, 
но не отправляй эти данные в Git.</li>
<li>добейся успешного запуска проекта. Первым запускается модуль edo-main. <a href="http://localhost:8761/"> Проверить</a>.</li>
<li>изучи <a href="https://gitlab.com/__-__/edo_v2/-/boards">таск-борд.</a>
</ol>

[//]: # (Для отправки почты используется Google аккаунт.<br/>)

[//]: # (Вход в него с именем "uxair1.kata" и паролем "Mail4@Uxair1" &#40;без кавыек&#41;.<br/>)

[//]: # (Важно учитывать, что для отправки почты использзуется отдельный пароль &#40;т.н пароль приложения&#41;,)

[//]: # (так как в случае наличи двухфакторной аутентификации использование пароля от эккаунта не позволит отправить почту.<br/>)

[//]: # (Именно данный пароль и указан в найстройке в .yml файле.)

[//]: # (При отправке почты всегда можно зайти в эккаунт через браузер и проверить, что она в папке Отправленные.)

### О таскборде

Таск-борд строится по принципу Kanban - он разделён на столбцы, каждый из которых соответствует определённому этапу
работы с задачей:
<ul>
<li><code>Backlog</code> задачи на <b>новый функционал</b>, корзина функционала приложения. Здесь можете создавать карточки на таски, которые считаете необходимыми</li>
<li><code>TODO</code> задачи, требующие выполнения</li>
<li><code>In Progress</code> выполняемые в данный момент задачи, обязательно должны иметь исполнителя</li>
<li><code>Cross-review </code> задачи на этапе перекрёстной проверки студентами</li>
<li><code>Final Review</code> задачи на проверке у техлида</li>
<li><code>Closed</code> выполненные задачи</li>
</ul>

У каждой задачи есть теги:
<ul>
<li><code>Feature/Refactor</code> - новый функционал или переработка существующего</li>
<li><code>Bug</code> - таска на исправление бага до или после тестирования</li>
<li><code>Reworking</code> - таска на исправлении замечаний после кросс или файнал ревью</li>

[//]: # (<li><code>InQA</code> - задача с такой меткой находится у тестировщика в работе </li>)
<li><code>Backlog, ToDo, InProgress, CrossReview, FinalReview</code> - этапы прохождения задачи по борде</li>
</ul>

### Как выполнять задачи

<ul>
<li>в графе <code>TODO</code> на таск-борде выбери карточку с задачей и назначь её себе для исполнения</li>
<li>загрузи себе последнюю версию ветки <code>develop</code></li>
<li>создай от <code>develop</code> свою собственную ветку для выполнения взятой задачи. Свою ветку назови так, чтобы было понятно, чему посвящена задача. В начале имени ветки проставь номер задачи с Gitlab. Например, <code>313_adding_new_html_pages</code></li>
<li>выполни задачу, обязательно сделай юнит-тесты на методы и, если всё ок, залей её в репозиторий проекта</li>
<li>создай на своей ветке merge request, в теле реквеста укажи <code><i>Closes #здесь-номер-таски"</i></code>. Например, <code>Closes #313</code></li>
<li>перенеси задачу в столбец <code>Cross-review</code></li>
</ul>

### Проверка задач

На этапе кросс-ревью студенты проверяют задачи, выполненные друг другом.
В случае, если к коду есть замечания, проверяющий пишет замечания в мердж реквесте и оставляет комментарий в карточке.
Если к коду претензий нет, проверяющий студент ставит к карточке лайк.

**Каждая карточка (студенческая задача) должна быть проверена как минимум 2 другими студентами и одобрена ими (т.е.
собрать не менее 2 лайков). Если студенты не посмотрели задачу за 3 дня, то задача отправляется в Final Review**

Только после этого карточку можно переносить в столбец `Final Review`.

Затем код проверяет техлид (ментор) и в случае обнаружения ошибок ставит тег `Reworking` и переносит её в
столбец `InProgress`.
Если всё ок - merge request принимается, ветка студента сливается с основной веткой проекта, а карточка переносится в
столбец `Closed`.

### Требования к коду

- сделайте себе понятные никнеймы (имя + фамилия) в Git. Не хочу гадать, кто, где и что писал.

#### Команды для гита (выполнять по очереди): <code>git config --global user.name "Фамилия Имя"<br> git config --global user.email "Email"</code>

- при использовании Stream, Optional, Builder необходимо писать каждый метод с новой строки.

#### Пример:

````java
Stream.of("a","b","c")
        .filter(string->string.equals("a"))
        .sorted()
        .collect(toSet());
````

- для каждого класса и (желательно) методов пишите комментарии в формате <b>Javadoc</b>:
    - над классом: что это за класс, зачем нужен. Описывайте поля.
    - над методом: что делает, какие параметры принимает (и что это такое), что возвращает.
- свободно создавайте собственные вспомогательные классы в пакете Util - типа утилиток для страховки от null и типа
  того. Модуль edo-common.
- в REST-контроллерах и DTO пользоваться аннотациями Swagger - причём как сами контроллеры в целом, так и их отдельные методы.
  Посмотрите пример в проекте.
- на полях сущностей можно и нужно расставлять констрейнты для проверки формата, длины введённых значений, проверки
  чисел на положительность и т.д.
- пишите Commit message как можно более подробно! На английском языке - пользуйтесь переводчиком при необходимости. Также указывайте номер задачи.
- при объявлении переменной используйте ключевое слово <code>var</code>.
- используйте STATIC импорт для статических методов и переменных(Alt+Enter в помощь).
- всегда пользуйтесь Ctrl+Alt+L.

### Требования к логированию работы контроллеров:

1. В каждый метод необходимо добавить логирование с описанием произведенной операции на уровне info.

2. Если объект не найден, вывести сообщение уровня warning ("not found" или "does not exist") с описанием произведенной
   операции.

### Созвоны по проекту

Созвоны проходят по понедельникам, средам в оговорённое время.
Регламент:

- длительность до 15 минут
- формат: доклады по 3 пунктам:
    - что сделано с прошлого созвона
    - какие были/есть трудности
    - что будешь делать до следующего созвона
- техлид (ментор) на созвонах код не ревьюит

Любые другие рабочие созвоны команда проводит без ограничений, т.е. в любое время без участия техлида.
Договаривайтесь сами :)

## Дополнительные материалы

### Spring Boot Dev Tools

Благодаря данной зависимости, разработчик получает возможность ускорить разработку проекта на Spring Boot в IDEA IDE и
сделать этот процесс более приятным и продуктивным. А не вручную перегружать сборку каждый раз, когда надо проверить
код. IDEA будет делать это за него.

####Как подключить

+ [mkyong.com](https://mkyong.com/spring-boot/intellij-idea-spring-boot-template-reload-is-not-working/)
+ [metakoder.com](https://www.metakoder.com/blog/spring-boot-devtools-on-intellij/)
+ [YouTube Video](https://youtu.be/XYTET4vSn6k)

####Полезные ссылки:

+ [baeldung.com](https://www.baeldung.com/spring-boot-devtools)
+ [habr.com](https://habr.com/ru/post/479382/)

[//]: # (### Аутентификация)

[//]: # ()

[//]: # (В проект подключена аутентификация с помощью keycloak, который поднимается в docker запускам файла)

[//]: # (keycloak-docker-compose.yml, в котором совместно с keycloak также поднимается база данных, и настраиваются пользователи.)

[//]: # (Для доступа к keycloak используется пользоватье с логином keycloak-admin и паролем admin.)

[//]: # (Так же создаётся реалм airline-realm, который используется для авторизации в приложении.)

[//]: # (Тестовые пользователи в нём admin и user с паролями admin и user соответственна, у админа назначена роль admin, у)

[//]: # (user - passenger.)

[//]: # ()

[//]: # (В контроллер AuthenticateController в метод authenticate передаётся DTO с именем и паролем в случае успешной)

[//]: # (аутентификации он возвращает токен который потом используется в запросах для)

[//]: # (доступа к эндпоинтам котрноллеров. Для того чтобы организавать аторизованный доступ к эндпоинтам контроллеров достаточно)

[//]: # (пометить метод контроллера аннотацией **_@PreAuthorize&#40;&#41;_** и в скобках указать роль например _**hasRole&#40;'admin'&#41;**_)

[//]: # (В keycloak настроены роли supeAdmin, admin, manager, passenger.)

[//]: # ()

[//]: # (#### Пример метода:)

[//]: # (````java)

[//]: # ( @GetMapping&#40;"/admin"&#41;)

[//]: # ( @PreAuthorize&#40;"hasRole&#40;'admin'&#41;"&#41;)

[//]: # ( public String getAdminInfo&#40;&#41; {)

[//]: # (   return "admin info";)

[//]: # ( } )

[//]: # (````)



### Flyway

#### Инструкция для разработчика по созданию скриптов миграции

1. все операции с БД происходят в модуле edo-service:

- сохранение, обновление, удаление происходит при получении данных по очереди или при необходимости через Feign Client.
- Получение данных через Feign клиенты.

2. При необходимости изменить настройки подключения к бд в файле application.yml.
3. В папке resources/db/migration/v.1 создать файл с именем по шаблону
   V1.0_год|месяц|число|час|минута|секунда__краткое_описание.sql.
4. Внутри файла написать инструкцию для выполнения.

- Всегда использовать <code>if not exists</code> при создании или обновлении таблицы

5. Если миграция некорректно накатилась, то необходимо руками удалить внесенные изменения и удалить запись в
   таблице <code>flyway_schema_history</code> после чего исправить скрипт

### MinIO

### Установка и разворачивание MinIO на локальном компьютере

1. Установите Docker на вашем компьютере, если он ещё не установлен.

2. Откройте терминал (можно прямо в IntelliJ IDEA) и выполните следующую команду, чтобы скачать образ MinIO из Docker
   Hub:

docker pull minio/minio:latest

3. Создайте Docker контейнер с помощью следующей команды:

docker run \
-p 9000:9000 \
-p 9090:9090 \
--name MinIOContainer \
-e "MINIO_ROOT_USER=username" \
-e "MINIO_ROOT_PASSWORD=password" \
minio/minio:latest server /data --console-address ":9090"

4. MinIO сервер должен быть успешно развёрнут и запущен на порту 9000 вашего локального компьютера.
   Вы можете получить доступ к административной панели MinIO, открыв веб-браузер и перейдя по адресу:
   http://localhost:9000
   Логин - username;
   Пароль - password.

5. Конфигурация MinIO описана в edo-file-storage > src > main > application.yml
   Обратите внимание, что бакет для хранения файлов (если его ещё не существует) создаётся автоматически при добавлении
   нового файла.