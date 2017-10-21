/*1) Сколько у меня домов в аренде, а сколько на продажу и сколько я уже продал?

      Под количеством домов на продажу в запросе подразумевается общее количество непроданных достроенных домов

 */

SELECT COUNT(*) FROM jack.houses WHERE RENT IS TRUE;

SELECT COUNT(*) FROM jack.houses WHERE SALED_DATE IS NULL AND BUILDING_DATE IS NOT NULL;

SELECT COUNT(*) FROM jack.houses WHERE SALED_DATE IS NOT NULL;

/*2) Сколько у меня арендаторов и кто, когда и сколько платит?

    Если срок окончания аренды не указан, договор считается бессрочным

 */

SELECT COUNT(*) FROM jack.renters WHERE RENTED_DATE IS NOT NULL AND CURDATE() <= IFNULL(EXPIRE_DATE, CURDATE());

SELECT NAME, DAYLY_RENT, RATE FROM jack.renters
  WHERE RENTED_DATE IS NOT NULL AND CURDATE() <= IFNULL(EXPIRE_DATE, CURDATE());

/*3) Сколько стоит все мое недвижимое имущество?

   Запрос выдает продажную стоимость всех, ранее не проданых и достроенных домов

*/

SELECT SUM(PRICE) FROM jack.houses WHERE SALED_DATE IS NULL AND BUILDING_DATE IS NOT NULL ;

/*4. Когда были построены мои дома на Бейкер стрит и Малхолланд Драйв?*/

SELECT BUILDING_DATE FROM jack.houses WHERE ADRESS LIKE "%Бейкер стрит%";
SELECT BUILDING_DATE FROM jack.houses WHERE ADRESS LIKE "%Малхолланд Драйв%";

/*5. Сколько арендаторов не заплатили за последние 3 месяца?

      Отчет только по действующим договорам, выводим имя должника, для наглядности
      Подразумевается, что аренда должна вноситься в первый день расчетного периода.
      Принимаем три месяца  = 91 день

 */

SELECT r.NAME FROM jack.renters AS r
  WHERE r.RENTED_DATE IS NOT NULL AND CURDATE() <= IFNULL(r.EXPIRE_DATE, CURDATE())
  AND (SELECT SUM(p.SUMM) FROM jack.payments AS p
    WHERE p.HOUSE_ID = r.HOUSE_ID AND (TO_DAYS(CURDATE()) - TO_DAYS(p.DATE)) <= 91) < r.RATE *
      CASE
         WHEN (TO_DAYS(CURDATE()) - TO_DAYS(r.RENTED_DATE)) < 91
            THEN (TO_DAYS(CURDATE()) - TO_DAYS(r.RENTED_DATE))
            ELSE 91
      END;








