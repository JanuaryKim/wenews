ALTER TABLE member ADD(member_picture VARCHAR(500));
ALTER TABLE member DROP member_password;
ALTER TABLE member DROP member_age;
ALTER TABLE member MODIFY member_id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE news MODIFY member_id BIGINT NOT NULL;
ALTER TABLE member ADD(member_name VARCHAR(100));
