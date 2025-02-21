# CryptoBot
## Как запустить
При открытии директории через IntelliJ IDEA проект должен автоматически распознаться. У вас должна появится run configuration CryptoBotApplication (зеленый треугольник), если не появился, можно пройти в класс CryptoBotApplication и оттуда напрямую вызвать main метод.

## О проекте
База данных
Для удобной работы с postgreSql используется докер-образ, вам не нужно самостоятельно устанавливать postgreSql. Выполнив docker-compose up, вы запустите postgreSql с предустановленными настройками (их посмотреть можно в файле docker-compose.yaml). Для запуска докер образа вам понадобится установить docker и docker-compose

## Spring проект
В данном проекте используется Spring, gradle используется в качестве системы сборки. Основные зависимости уже указаны, но никто вам не запрещает добавлять новые.

В application.yml находятся конфигурируемые параметры нашего приложения (в частности, там указывается токен вашего бота)
