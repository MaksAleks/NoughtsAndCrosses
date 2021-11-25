# Noughts and Crosses

## Описание
- Поддерживаются квадратные доски произвольного размера
- Поддержка множества игровых сессий
- Хранение состояния текущих и архивных игр в реляционной БД

Сервис имеет возможность горизонтального масштабирования, то есть запуска нескольких экземпляров приложения, подключающихся к одной БД.

## Реализация
- Основной фреймворк - **spring-boot**
- База данных - **PostgreSQL**
- Система сборки - **gradle**


 Для поддержки множества открытых сессий используется
 spring-session-jdbc модуль. Информация о сессиях хранится в БД.
 Таким образом, если будут запущены несколько экземпляров приложения,  
 не будет иметь значения с каким экземпляром взаимодействует пользователь. Это так же позволяет обеспечить отказоустойчивость - если какой-то из экземпляров будет недоступен, можно переключить пользователя на другой инстанс используя load-balancer и service-discovery (например consul).
 

## Запуск
- Для локального запуска используется [docker-compose](/nc-app/compose/docker-compose.yaml)

- Для создания контейнера есть [Dockerfile](nc-app/docker-image/Dockerfile)

- Для инициализации БД используются два скрипта:
  - [Основная база](nc-app/compose/volume/postgres/init-main-schema.sql)
  - [База для spring-session-jdbc хранения сессий](nc-app/compose/volume/postgres/init-session-schema.sql)

Чтобы запустить приложение через docker-compose у вас должен быть установлен и запущен docker.

Шаги для запуска:

 1) нужно произвести сборку артефакта командой:  

        ./gradlew clean build

В директории [nc-app/docker-image](/nc-app/docker-image) будет создана директория `build` и в ней будет лежать .jar файл с приложением: `build/libs/nc-app-0.0.1-SNAPSHOT.jar`

2) Перейти в директорию с [docker-compose](/nc-app/compose/) и выполнить команду:

        docker-compose up -d postgres nc-app

После запуска будет доступен [swagger-ui](http://localhost:8080/swagger-ui.html) с описанием API

Все ссылки ниже будут рабочие только после запуска приложения и ведут к endpoint-ам на [http://localhost:8080/swagger-ui.html/*](http://localhost:8080/swagger-ui.html)

Для начала зарегистрируем пользователя через endpoint [/register](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/register-api/login)
Нажимаем на кнопочку [**Try it out**] в правом верхнем углу и в теле запроса вводим имя и пароль:

    {
        "name": "Max",
        "password": "123"
    }

Далее открываем вкладку incognito в браузере, чтобы сменить сессию, и точно так же регистрируем второго пользователя 

Теперь можно сыграть.

Но для начала надо авторизоваться в системе.
spring-security предоставляет прекрасную [форму с вводом логина и пароля](http://localhost:8080/login):

После введения корректных учетных данных браузер вернет код 404, так как UI к сервису, пока что, не прилагается. Но нам и не надо. 

Возвращаемся в swagger-ui и создадим игру через POST запрос на endpoint [/game](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/createGame)

Обязательными параметрами для создания игры являются:
- **name** - название игры
- **fieldSize** - размер поля  
Так как поле квадратное, то при введении fieldSize = 3 размер поля будет 3x3
    

    {
        "name": "G1",
        "fieldSize": 3 
    }

После этого будет создана игра от имени того пользователя, из-под которого вы выполнили запрос.

Теперь нужно присоединиться вторым игроком к игре.
Для этого есть endpoint POST [/game/join/{gameName}](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/joinGame)

Где в качестве обязательного параметра вы должны ввести название игры.
Вводим: G1

Если сделать это из-под создателя игры, запрос выполниться успешно, но в этом случае вы сможете продолжить игру только с самим собой =(

Второй игрок не сможет присоединиться к игре. 
В качестве `//TODO` надо реализовать endpoint отсоединения.

Чтобы начать игру, создатель игры должен вызвать метод POST [/game/start/{gameName}](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/startGame)

Успешно запустить игру может только ее создатель.
Запустить он ее может только после присоединения второго игрока к игре.

Теперь, когда игра начата, можно делать ходы с помощью запроса
POST [/game/move/](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/move-api/makeAMove)

    {
        "gameName": "G1",
        "xPos": 1,
        "yPos": 2
    }

P.S. По какой-то причине swagger генерирует названия свойств `xPos` и `yPos` в этом запросе не в camel case, а прописными (маленькими) буквами, так что лучше скопируйте из примера выше

`xPos` и `yPos` - это координаты клетки на поле

В качестве `xPos` и `yPos` могут быть значения от `1` до `fieldSize` включительно. Значения `< = 0` - невалидные.

Когда вы закончите игру, статус игры будет переведен в `FINISHED`

Информация о ходах игры записывается в БД.

- [Получение всех игр](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/getAllGames)
- [Получение всех игр созданных пользователем](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/getGameByUser)
- [Получение игры по названию](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/game-api/getGameByName)
- [Получение информации обо всех ходах конкретной игры](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/move-api/getMovesForGame)