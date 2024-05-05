## Компилируем
javac -d bin -cp src/main/java src/main/java/*.java src/main/java/model/*.java src/main/java/view/*.java src/main/java/controller/*.java
## Запускаем
java -cp bin Main

## Теперь используем Maven поэтому:
mvn exec:java

## Сбилженный файл с зависимостями запускить:
\JavaLab\target> java -jar .\javalabapplication-1.0-SNAPSHOT-jar-with-dependencies.jar