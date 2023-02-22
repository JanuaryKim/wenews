CREATE TABLE IF NOT EXISTS member(

    `member_id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_nickname` VARCHAR(10),
    `member_password` VARCHAR(12),
    `member_email` VARCHAR(100),
    `member_age` TINYINT,
    PRIMARY KEY (`member_id`));


CREATE TABLE IF NOT EXISTS news(

    `news_id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT,
    `news_title` VARCHAR(20),
    `news_content` TEXT,
    `news_tag` VARCHAR(100),
    `created_at` DATETIME,
    `modified_at` DATETIME,
    PRIMARY KEY (`news_id`));