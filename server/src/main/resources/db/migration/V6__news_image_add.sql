CREATE TABLE IF NOT EXISTS news_image(

     `image_id` BIGINT NOT NULL AUTO_INCREMENT,
     `url` VARCHAR(500),
     `created_at` DATETIME,
     `modified_at` DATETIME,
     PRIMARY KEY (`image_id`));
