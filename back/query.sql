drop schema if exists `daero`;

create schema `daero`;

use `daero`;

CREATE TABLE `users`
(
    `users_seq`          int PRIMARY KEY AUTO_INCREMENT,
    `user_email`         nvarchar(50) UNIQUE,
    `password`           nvarchar(50),
    `nickname`           nvarchar(50) UNIQUE,
    `del_yn`             varchar(1) DEFAULT 'n',
    `email_verified_yn`  varchar(1) DEFAULT 'n',
    `password_reset_yn`  varchar(1) DEFAULT 'y',
    `suspended_yn`       varchar(1) DEFAULT 'n',
    `profile_image_link` text          NULL,
    `reported_count`     int        DEFAULT 0,
    `created_at`         nvarchar(50)  NULL,
    `fcm_token`          nvarchar(512) NULL
);

CREATE TABLE `user_favor`
(
    `users_seq` int NOT NULL,
    `score`     smallint default 0,
    `tag_seq`   int NOT NULL
);

CREATE TABLE `email_verification_keys`
(
    `email_verification_seq` int           primary key auto_increment,
    `user_seq`               int           NOT NULL,
    `verification_key`       nvarchar(512) NOT NULL,
    `expiration`             datetime      default date_add(now(), interval 30 minute),
    `expired_yn`             varchar(1) default 'n'
);

CREATE TABLE `password_reset_keys`
(
    `password_reset_seq` int           primary key auto_increment,
    `user_seq`           int           NOT NULL,
    `reset_key`          nvarchar(512) NOT NULL,
    `expiration`         datetime      default date_add(now(), interval 30 minute),
    `expired_yn`         varchar(1) default 'n'
);

CREATE TABLE `trip_places`
(
    `trip_places_seq` int primary key auto_increment,
    `place_name`      nvarchar(20)  not NULL,
    `address`         nvarchar(100) not NULL,
    `latitude`        double        not NULL,
    `longitude`       double        not NULL,
    `description`     text          NULL,
    `region_seq`      tinyint       NOT NULL,
    `image_url`       text          NULL
);

CREATE TABLE `trips`
(
    `trips_seq`     int primary key auto_increment,
    `users_seq`     int      NOT NULL,
    `trip_comment`  text     NULL,
    `trip_expenses` tinytext NULL,
    `trip_rating`   int      NULL
);


CREATE TABLE `trip_days`
(
    `trip_days_seq` int primary key auto_increment,
    `trip`          int          NOT NULL,
    `date`          nvarchar(30) not NULL,
    `day_comment`   tinytext     NULL
);

CREATE TABLE `trip_stamps`
(
    `trip_stamps_seq`       int primary key auto_increment,
    `trip_days_seq`         int          NOT NULL,
    `trip_places_seq`       int          NOT NULL,
    `image_url`             nvarchar(50) NULL,
    `place_satisfaction_YN` varchar(1)   NULL
);

CREATE TABLE `likes`
(
    `likes_seq`    int primary key auto_increment,
    `users_seq`    int          NOT NULL,
    `articles_seq` int          NOT NULL,
    `liked_at`     nvarchar(30) not NULL
);

CREATE TABLE `user_achievements`
(
    `user_achievements_seq` int not null,
    `users_seq`  int     NOT NULL,
    `region_seq` tinyint NOT NULL,
    `count`      int     NULL
);

CREATE TABLE `place_tags`
(
    `place_tag_seq` int primary key auto_increment,
    `tag`           nvarchar(15) unique not null
);

CREATE TABLE `article_tags`
(
    `article_tags_seq` int primary key auto_increment,
    `articles_seq`     int NOT NULL,
    `place_tag_seq`    int NOT NULL
);

CREATE TABLE `region_tags`
(
    `region_seq`  tinyint primary key auto_increment,
    `region_name` nvarchar(20) unique
);

CREATE TABLE `tag_trip_places`
(
    `place_tag_seq`   int NOT NULL,
    `trip_places_seq` int NOT NULL
);

CREATE TABLE `faq`
(
    `faq_seq`    int primary key auto_increment,
    `admin_seq`  int          NOT NULL,
    `title`      nvarchar(30) NOT NULL,
    `content`    text         NOT NULL,
    `created_at` nvarchar(30) NOT NULL,
    `updated_at` nvarchar(30) NOT NULL
);

CREATE TABLE `admin`
(
    `admin_seq`  int primary key auto_increment,
    `admin_name` nvarchar(50) UNIQUE,
    `created_at` nvarchar(30) NOT NULL
);

CREATE TABLE `notice`
(
    `notice_seq` int primary key auto_increment,
    `admin_seq`  int          NOT NULL,
    `title`      nvarchar(50) NOT NULL,
    `content`    nvarchar(50) NOT NULL,
    `created_at` nvarchar(30) NOT NULL,
    `updated_at` nvarchar(30) NOT NULL
);

CREATE TABLE `inquires`
(
    `inquires_seq` int primary key auto_increment,
    `users_seq`    int           NOT NULL,
    `admin_seq`    int           NOT NULL,
    `title`        nvarchar(30)  NOT NULL,
    `content`      nvarchar(100) NOT NULL,
    `created_at`   nvarchar(30)  NOT NULL,
    `answer_yn`    varchar(1) DEFAULT 'n',
    `answer`       text          NULL,
    `answer_at`    nvarchar(30)  NULL
);

CREATE TABLE `reports`
(
    `reports_seq`           int primary key auto_increment,
    `reporter_users_seq`    int          NOT NULL,
    `reported_users_seq`    int          NOT NULL,
    `reported_at`           nvarchar(30) NOT NULL,
    `report_url`            nvarchar(50) NOT NULL,
    `report_categories_seq` int          NOT NULL,
    `handled_yn`            varchar(1) DEFAULT 'n',
    `article_type`          nvarchar(50) NOT NULL
);

CREATE TABLE `report_categories`
(
    `report_categories_seq` int primary key auto_increment,
    `name`                  nvarchar(30) unique
);

CREATE TABLE `blocks`
(
    `blocks_seq`        int primary key auto_increment,
    `blocker_users_seq` int NOT NULL,
    `blocked_users_seq` int NOT NULL
);

CREATE TABLE `follows`
(
    `follows_seq`         int primary key auto_increment,
    `following_users_seq` int NOT NULL,
    `follower_users_seq`  int NOT NULL
);

CREATE TABLE `articles`
(
    `articles_seq`  int primary key auto_increment,
    `trips_seq`     int          NOT NULL,
    `created_at`    nvarchar(30) NOT NULL,
    `updated_at`    nvarchar(30) NOT NULL,
    `like_count`    int        DEFAULT 0,
    `reply_count`   int        DEFAULT 0,
    `open_yn`       varchar(1) DEFAULT 'n',
    `thumbnail_url` text         NOT NULL
);

CREATE TABLE `replies`
(
    `replies_seq`    int primary key auto_increment,
    `articles_seq`   int          NOT NULL,
    `users_seq`      int          NOT NULL,
    `content`        nvarchar(50) NOT NULL,
    `created_at`     nvarchar(30) NOT NULL,
    `updated_at`     nvarchar(30) NOT NULL,
    `rereply_count`  int DEFAULT 0,
    `rereply_parent` int          NULL
);

# ALTER TABLE `user_favor`
#     ADD CONSTRAINT `PK_USER_FAVOR` PRIMARY KEY (
#                                                 `users_seq`
#         );

ALTER TABLE `user_achievements`
    ADD CONSTRAINT `PK_USER_ACHIEVEMENTS` PRIMARY KEY (
                                                       `user_achievements_seq`
        );

ALTER TABLE `user_favor`
    ADD CONSTRAINT `FK_users_TO_user_favor_1` FOREIGN KEY (
                                                           `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `email_verification_keys`
    ADD CONSTRAINT `FK_users_TO_email_verification_keys_1` FOREIGN KEY (
                                                                        `user_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `inquires`
    ADD CONSTRAINT `FK_users_TO_inquires_1` FOREIGN KEY (
                                                         `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `inquires`
    ADD CONSTRAINT `FK_admin_TO_inquires_1` FOREIGN KEY (
                                                         `admin_seq`
        )
        REFERENCES `admin` (
                            `admin_seq`
            );

ALTER TABLE `faq`
    ADD CONSTRAINT `FK_admin_TO_faq_1` FOREIGN KEY (
                                                    `admin_seq`
        )
        REFERENCES `admin` (
                            `admin_seq`
            );

ALTER TABLE `notice`
    ADD CONSTRAINT `FK_admin_TO_notice_1` FOREIGN KEY (
                                                       `admin_seq`
        )
        REFERENCES `admin` (
                            `admin_seq`
            );

ALTER TABLE `follows`
    ADD CONSTRAINT `FK_users_TO_follows_1` FOREIGN KEY (
                                                        `following_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `blocks`
    ADD CONSTRAINT `FK_users_TO_blocks_1` FOREIGN KEY (
                                                       `blocker_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `reports`
    ADD CONSTRAINT `FK_users_TO_reports_1` FOREIGN KEY (
                                                        `reporter_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `trips`
    ADD CONSTRAINT `FK_users_TO_trips_1` FOREIGN KEY (
                                                      `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `articles`
    ADD CONSTRAINT `FK_trips_TO_articles_1` FOREIGN KEY (
                                                         `trips_seq`
        )
        REFERENCES `trips` (
                            `trips_seq`
            );

ALTER TABLE `trip_days`
    ADD CONSTRAINT `FK_trips_TO_trip_days_1` FOREIGN KEY (
                                                          `trip`
        )
        REFERENCES `trips` (
                            `trips_seq`
            );

ALTER TABLE `trip_stamps`
    ADD CONSTRAINT `FK_trip_days_TO_trip_stamps_1` FOREIGN KEY (
                                                                `trip_days_seq`
        )
        REFERENCES `trip_days` (
                                `trip_days_seq`
            );

ALTER TABLE `replies`
    ADD CONSTRAINT `FK_articles_TO_replies_1` FOREIGN KEY (
                                                           `articles_seq`
        )
        REFERENCES `articles` (
                               `articles_seq`
            );

ALTER TABLE `replies`
    ADD CONSTRAINT `FK_users_TO_replies_1` FOREIGN KEY (
                                                        `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `likes`
    ADD CONSTRAINT `FK_users_TO_likes_1` FOREIGN KEY (
                                                      `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `likes`
    ADD CONSTRAINT `FK_articles_TO_likes_1` FOREIGN KEY (
                                                         `articles_seq`
        )
        REFERENCES `articles` (
                               `articles_seq`
            );

ALTER TABLE `user_achievements`
    ADD CONSTRAINT `FK_users_TO_user_achievements_1` FOREIGN KEY (
                                                                  `users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `article_tags`
    ADD CONSTRAINT `FK_articles_TO_article_tags_1` FOREIGN KEY (
                                                                `articles_seq`
        )
        REFERENCES `articles` (
                               `articles_seq`
            );

ALTER TABLE `reports`
    ADD CONSTRAINT `FK_users_TO_reports_2` FOREIGN KEY (
                                                        `reported_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `follows`
    ADD CONSTRAINT `FK_users_TO_follows_2` FOREIGN KEY (
                                                        `follower_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );

ALTER TABLE `blocks`
    ADD CONSTRAINT `FK_users_TO_blocks_2` FOREIGN KEY (
                                                       `blocked_users_seq`
        )
        REFERENCES `users` (
                            `users_seq`
            );