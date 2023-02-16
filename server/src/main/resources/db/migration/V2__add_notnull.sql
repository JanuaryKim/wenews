ALTER TABLE member modify COLUMN `member_nickname` VARCHAR(10) NOT NULL;
ALTER TABLE member modify COLUMN `member_password` VARCHAR(12) NOT NULL;
ALTER TABLE member modify COLUMN `member_email` VARCHAR(100) NOT NULL;
ALTER TABLE member modify COLUMN `member_age` TINYINT NOT NULL;


ALTER TABLE news modify COLUMN `member_id` BIGINT NOT NULL;
ALTER TABLE news modify COLUMN `news_title` VARCHAR(20) NOT NULL;
ALTER TABLE news modify COLUMN `news_content` TEXT NOT NULL;
ALTER TABLE news modify COLUMN `news_tag` VARCHAR(100);
ALTER TABLE news modify COLUMN `created_at` DATETIME NOT NULL;
ALTER TABLE news modify COLUMN `modified_at` DATETIME NOT NULL;