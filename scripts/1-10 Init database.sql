BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS currency (id integer not null, currency_code varchar(255), flag varchar(255), primary key (id));
INSERT INTO currency VALUES(0,'USD','🇺🇸️');
INSERT INTO currency VALUES(1,'EUR','🇪🇺️');
INSERT INTO currency VALUES(2,'HUF','🇭🇺️');
INSERT INTO currency VALUES(5,'PLN','🇵🇱️');
INSERT INTO currency VALUES(6,'CZK','🇨🇿️');
INSERT INTO currency VALUES(8,'RON','🇷🇴️');
INSERT INTO currency VALUES(9,'GBP','🇬🇧️');
INSERT INTO currency VALUES(14,'CHF','🇨🇭️');
CREATE TABLE IF NOT EXISTS region (id integer not null, name varchar(255), primary key (id));
INSERT INTO region VALUES(3,'Мукачево');
CREATE TABLE IF NOT EXISTS subscriber (id bigint not null, region_id integer, primary key (id));
CREATE TABLE IF NOT EXISTS subscriber_currencies (subscriber_id bigint not null, currency_id integer not null);
CREATE TABLE IF NOT EXISTS "rate"
(
    id          integer not null
    primary key,
    buy_rate    float,
    currency_id integer,
    region_id   integer,
    sell_rate   float
);
COMMIT;

