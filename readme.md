Задача 1 (package task1)

Создание базы данных initDB.sql, тестовые данные populateDB.sql, запросы query.sql
Допущения при запросах указаны в комментариях к ним

Структура базы данных:
1) Таблица ДОМ:
   - ID
   - стоимость посторойки
   - стоимость продажи (если уже была, то фактическая, если только планируется, то заявленная)
   - адрес
   - сдается ли дом (boolean)
   - дата постройки (если null, то дом еще строится)
   - дата продажи (если null, то дом еще в собственности)
2) Таблица АРЕНДАТОРЫ:
   - ID арендатора
   - ID дома (foreign key)
   - имя арендатора
   - дата начала аренды
   - дата окончания аренды
   - суточная ли аренда (boolean), если false, то месячная (в условии задачи только два вида аренды)
   - ставка аренды за сутки
3) Таблица ПЛАТЕЖИ:
   - ID дома (foreign key)
   - дата платежа
   - сумма платежа
   
Задача 2 (package task2, запуск метод main)   
  
  Ввиду простоты приложения, обработка ошибок реализована простым exit(1) с сообщением в консоли.