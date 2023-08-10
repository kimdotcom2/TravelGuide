CREATE TABLE `Member` (
	`email`	VARCHAR(64)	NOT NULL,
	`password`	VARCHAR(255)	NOT NULL,
	`name`	VARCHAR(32)	NOT NULL,
	`nickname`	VARCHAR(32)	NOT NULL,
	`social_login` ENUM('Y', 'N')	NOT NULL
);

CREATE TABLE `planner` (
	`planner_id`	INT	NOT NULL,
	`email`	VARCHAR(64)	NOT NULL	COMMENT 'foreign key',
	`title`	VARCHAR(64)	NOT NULL,
	`first_date`	DATE	NOT NULL,
	`last_date`	DATE	NOT NULL,
	`comment`	TEXT	NULL
);

CREATE TABLE `schedule` (
	`schedule_id`	INT	NOT NULL,
	`planner_id`	INT	NOT NULL	COMMENT 'foreign key',
	`content_id`	INT	NOT NULL,
	`content_type`	INT	NOT NULL,
	`address`	VARCHAR(255)	NOT NULL,
	`place`	VARCHAR(32)	NOT NULL,
	`mapx`	DOUBLE	NOT NULL,
	`mapy`	DOUBLE	NOT NULL,
	`date`	DATE	NOT NULL,
	`arrive_time`	DATETIME	NULL,
	`via_time`	TIME	NULL,
	`start_time`	DATETIME	NULL,
	`thumbnail_location`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `board` (
	`post_id`	INT	NOT NULL,
	`board_id`	INT	NOT NULL,
	`email`	VARCHAR(64)	NOT NULL	COMMENT 'foreign key',
	`title`	VARCHAR(64)	NOT NULL,
	`content`	TEXT	NOT NULL,
	`view_count`	INT	NOT NULL	DEFAULT 0,
	`date`	DATETIME	NOT NULL,
	`view_role`	INT	NOT NULL	COMMENT '0 : 관리자와 작성자만
1 : 모두'
);

CREATE TABLE `comment` (
	`comment_id`	INT	NOT NULL,
	`post_id`	INT	NOT NULL	COMMENT 'foreign key',
	`email`	VARCHAR(64)	NOT NULL	COMMENT 'foreign key',
	`comment`	TEXT	NOT NULL,
	`date`	DATETIME	NOT NULL
);

CREATE TABLE `file` (
	`file_id`	INT	NOT NULL,
	`file_origin`	VARCHAR(64)	NOT NULL,
	`file_internal`	VARCHAR(64)	NOT NULL,
	`size`	VARCHAR(8)	NOT NULL,
	`type`	enum('jpg', 'jpeg', 'png', 'gif', 'webp', 'heic', 'heif')	NULL
);

ALTER TABLE `Member` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
	`email`
);

ALTER TABLE `planner` ADD CONSTRAINT `PK_PLANNER` PRIMARY KEY (
	`planner_id`
);

ALTER TABLE `schedule` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
	`schedule_id`
);

ALTER TABLE `board` ADD CONSTRAINT `PK_BOARD` PRIMARY KEY (
	`post_id`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`comment_id`
);

ALTER TABLE `file` ADD CONSTRAINT `PK_FILE` PRIMARY KEY (
	`file_id`
);

ALTER TABLE Planner MODIFY planner_id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE schedule MODIFY schedule_id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE board MODIFY post_id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE comment MODIFY comment_id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE file MODIFY file_id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `planner` ADD FOREIGN KEY `Planner`(`email`) REFERENCES `Member`(`email`);

ALTER TABLE `schedule` ADD FOREIGN KEY `schedule`(`planner_id`) REFERENCES `Planner`(`planner_id`);

ALTER TABLE `board` ADD FOREIGN KEY `board`(`email`) REFERENCES `Member`(`email`);

ALTER TABLE `comment` ADD FOREIGN KEY `comment`(`post_id`) REFERENCES `board`(`post_id`);

ALTER TABLE `comment` ADD FOREIGN KEY `comment2`(`email`) REFERENCES `Member`(`email`);

