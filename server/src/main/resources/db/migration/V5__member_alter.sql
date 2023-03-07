ALTER TABLE member DROP member_nickname;
ALTER TABLE member MODIFY member_id varchar(30);
ALTER TABLE news MODIFY member_id varchar(30);
ALTER TABLE member modify COLUMN member_age TINYINT;