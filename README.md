КРАТКОЕ ОПИСАНИЕ
Сервис-хранилище представляет собой хранилище сообщений. 
По ключу типа String хранятся сообщения типа String. Приложение состоит из четырех пакетов: controller, model, repository, service. 
Пакет model хранит класс Message, реализующий основные операции над сообщением. 
Пакет repository хранит класс MessageRepository, отвечающий на хранение сообщений в структуре HashMap. 
Пакет service хранит интерфейс MessageService и реализацию интерфейса MessageServiceImpl. Этот слой отвечает за основную логику приложения (все операции над репозиторием прописаны в этом слое). 
Пакет controller хранит класс MessageController. Этот класс принимает запросы пользователя, преобразует и передаёт их на слой service.
Все операции и ошибки логируются в файл application.log, который создается при сборке проекта и находится в папке target.

СБОРКА И ЗАПУСК
В реализации исплользуется Java 8, Spring Boot 2.5.4, Apache Maven 3.8.2.
1) В корневой директории проекта storage через командную строку запустить сборку проекта при помощи команды
mvn package
В процессе сборки будут запущены написанные для сервиса тесты, результаты тестов будут выведены в консоль.
2) Переходим в автоматически созданную папку target. В ней появился файл storage-0.0.1-SNAPSHOT.jar. Запускаем его при помощи команды
java -jar storage-1.jar
Сервис запущен.

ЗАПРОСЫ НА СЕРВИС
Адрес IP хост-сервера: localhost. Порт:8080.
Post запрос c адресом /messages добавляет новое сообщение в хранилище, привязывая его к указанному ID. ttl указывается в секундах. Если ttl не указывается, то по умолчанию ttl ставится 60 секунд.  Тело запроса передается в формате JSON.
Пример запроса:
POST http://localhost:8080/messages
Тело запроса в формате JSON:
{
    "id": "M1",
    "message": "Hello!",
    "ttl": 50
}
Тело запроса без указания ttl (ttl по умолчанию станет 60 секунд):
{
    "id": "M1",
    "message": "Hello!"
}

Get запрос с адресом /messages вернет все данные, содержащиеся в хранилище.
Пример запроса:
GET http://localhost:8080/messages

Get запрос с адресом /messages с указанием ID вернет сообщение, привязанное к этому ID.  ID передается через URI.
Пример запроса:
GET http://localhost:8080/messages/M1

Delete запрос с адресом /messages с указанием ID удаляет и возвращает сообщение, привязанное к этому ID. ID передается через URI.
Пример запроса:
DELETE http://localhost:8080/messages/M1

Get запрос с адресом /dump сохраняет текущее состояние хранилища и возвращает его в виде загружаемого файла формата .txt.
Пример запроса:
GET http://localhost:8080/dump/test.txt

Post запрос с адресом /load загружает состояние хранилища из файла формата .txt. Имя файла передается через URI.
Пример запроса:
POST http://localhost:8080/load/test.txt





