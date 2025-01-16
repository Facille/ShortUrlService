## Сервис коротких ссылок

Сервис коротких ссылок позволяет пользователям создавать короткие URL-адреса, которые перенаправляют на оригинальные URL. Сервис отслеживает количество кликов и устанавливает лимиты на количество переходов по коротким ссылкам.

## 🔥 Функциональные возможности

- Создание короткой ссылки с оригинальным URL и лимитом кликов.
- Перенаправление по короткой ссылке на оригинальный URL.
- Увеличение счетчика кликов при каждом переходе.
- Установка срока действия для короткой ссылки.
- Удаление ссылки по идентификатору.
- Получение всех ссылок, созданных пользователем.

## 🤘 Технологии
- Java
- Spring Boot
- Hibernate
- PostgreSQL
- Maven
- Postman(или любой другой сервис)

## 💬 Установка
Убедитесь, что у вас установлен JDK 23 и Maven.
Настройте базу данных PostgreSQL:

Создайте новую базу данных. Обновите файл src/main/resources/application.properties с вашими данными для подключения к базе данных: properties

spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name

spring.datasource.username=your_username

spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

Соберите проект: mvn clean install

## 🙈 Какие команды поддерживаются
- POST /api/links: Создание короткой ссылки.
- GET /api/links/{shortUrl}: Перенаправление на оригинальный URL.
- GET /api/links/user/{userId}: Получение всех ссылок пользователя.
- DELETE /api/links/{id}: Удаление ссылки по идентификатору.
- PUT /api/links/{id}/clickLimit: Обновление лимита кликов для ссылки.

## 👽 Использование
Запустите приложение: ShortUrlServiceApplication bash

- mvn spring-boot:run

1.Создание короткой ссылки

Отправьте POST запрос на /api/links с параметрами:

- originalUrl: оригинальный URL, который вы хотите сократить.
- clickLimit: максимальное количество кликов.

Пример запроса с использованием Postman:

- URL: http://localhost:8081/api/links
- Метод: POST
- Тело (формат x-www-form-urlencoded):
- originalUrl: http://example.com
- clickLimit: 10

1.1 Перенаправление по короткой ссылке Отправьте GET запрос на /api/links/{shortUrl}, заменив {shortUrl} на сгенерированную короткую ссылку.

Пример запроса с использованием Postman:

- URL: http://localhost:8081/api/links/0a83a0f2
- Метод: GET
  
1.2 Получение всех ссылок пользователя Отправьте GET запрос на /api/links/user/{userId}, заменив {userId} на UUID пользователя.

Пример запроса с использованием Postman:

- URL: http://localhost:8081/api/links/user/679bf939-ce23-471f-95b6-5df4786bb723
- Метод: GET
  
1.3 Удаление ссылки Отправьте DELETE запрос на /api/links/{id}, заменив {id} на идентификатор ссылки.

Пример запроса с использованием Postman:

- URL: http://localhost:8081/api/links/1
- Метод: DELETE

1.4 Обновление лимита кликов Отправьте PUT запрос на /api/links/{id}/clickLimit, заменив {id} на идентификатор ссылки и передав новый лимит кликов в теле запроса.

Пример запроса с использованием Postman:

- URL: http://localhost:8081/api/links/1/clickLimit
- Метод: PUT
- Тело (формат x-www-form-urlencoded):
- newClickLimit: 20
  
## Проверка базы данных:
После выполнения запросов проверьте состояние вашей базы данных, чтобы убедиться, что данные сохраняются и обновляются корректно. Вы можете использовать инструменты, такие как pgAdmin, для работы с PostgreSQL.
