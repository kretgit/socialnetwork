# Social Network

архитектура: ``rest api приложение``

бэкенд: ``spring boot framework ``

фронт: ``swagger-ui``

БД: ``MySql``

> Инструкция по запуску:
> 0. Перейти по адресу: https://SOME-HOST-URL/social/api/swagger-ui.html
> 1. Проверить работоспособность приложения, нажав на контроллер PING
> 2. Зарегистрироваться в приложении. Использовать метод REGISTER
> 3. Нажать кнопку **TRY IT OUT**, заплонить тело запроса при необходимости. Нажать кнопку **EXECUTE**.

Пример тела запроса для регистрации:
````
{
  "firstName": "Petr",
  "lastName": "Fedorov",
  "email": "fedorov@yandex.ru",
  "age": 34,
  "gender": "MALE",
  "hobbies": [
    "photo","video"
  ],
  "city": "Podolsk",
  "password": "password"
}
````