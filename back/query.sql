drop schema if exists `daero`;

create schema `daero`;

use `daero`;

CREATE TABLE `users`
(
    `users_seq`          int auto_increment,
    `user_email`         nvarchar(50),
    `password`           nvarchar(512),
    `nickname`           nvarchar(50),
    `del_yn`             varchar(1) DEFAULT 'n',
    `email_verified_yn`  varchar(1) DEFAULT 'n',
    `password_reset_yn`  varchar(1) DEFAULT 'y',
    `suspended_yn`       varchar(1) DEFAULT 'n',
    `profile_image_link` text          NULL,
    `reported_count`     int        DEFAULT 0,
    `created_at`         nvarchar(50)  NULL,
    `fcm_token`          nvarchar(512) NULL,
    constraint primary key (`users_seq`),
    constraint unique (`user_email`),
    constraint unique (`nickname`)
);

CREATE TABLE `user_favor`
(
    `users_seq` int NOT NULL,
    `score`     smallint default 0,
    `tag_seq`   int NOT NULL,
    constraint foreign key (`users_seq`) references `users` (`users_seq`)
);

CREATE TABLE `email_verification_keys`
(
    `email_verification_seq` int auto_increment,
    `user_seq`               int           NOT NULL,
    `verification_key`       nvarchar(512) NOT NULL,
    `expiration`             datetime   default date_add(now(), interval 30 minute),
    `expired_yn`             varchar(1) default 'n',
    constraint primary key (`email_verification_seq`),
    constraint foreign key (`user_seq`) references `users` (`users_seq`)
);

CREATE TABLE `password_reset_keys`
(
    `password_reset_seq` int auto_increment,
    `user_seq`           int           NOT NULL,
    `reset_key`          nvarchar(512) NOT NULL,
    `expiration`         datetime   default date_add(now(), interval 30 minute),
    `expired_yn`         varchar(1) default 'n',
    constraint primary key (`password_reset_seq`),
    constraint foreign key (`user_seq`) references `users` (`users_seq`)
);

CREATE TABLE `region_tags`
(
    `region_seq`  tinyint auto_increment,
    `region_name` nvarchar(20) unique,
    constraint primary key (`region_seq`),
    constraint unique (`region_name`)
);

CREATE TABLE `trip_places`
(
    `trip_places_seq` int auto_increment,
    `place_name`      nvarchar(20)  not NULL,
    `address`         nvarchar(100) not NULL,
    `latitude`        double        not NULL,
    `longitude`       double        not NULL,
    `description`     text          NULL,
    `region_seq`      tinyint       NOT NULL,
    `image_url`       text          NULL,
    constraint primary key (`trip_places_seq`),
    constraint foreign key (`region_seq`) references `region_tags` (`region_seq`)
);

CREATE TABLE `trips`
(
    `trips_seq`     int auto_increment,
    `users_seq`     int      NOT NULL,
    `trip_comment`  text     NULL,
    `trip_expenses` tinytext NULL,
    `trip_rating`   int      NULL,
    constraint primary key (`trips_seq`),
    constraint foreign key (`users_seq`) references `users` (`users_seq`)
);

CREATE TABLE `trip_days`
(
    `trip_days_seq` int auto_increment,
    `trip`          int          NOT NULL,
    `date`          nvarchar(30) not NULL,
    `day_comment`   tinytext     NULL,
    constraint primary key (`trip_days_seq`),
    constraint foreign key (`trip`) references `trips` (`trips_seq`)
);

CREATE TABLE `trip_stamps`
(
    `trip_stamps_seq`       int auto_increment,
    `trip_seq`              int        not null,
    `trip_days_seq`         int        NOT NULL,
    `trip_places_seq`       int        NOT NULL,
    `image_url`             text       NULL,
    `place_satisfaction_YN` varchar(1) NULL,
    constraint primary key (`trip_stamps_seq`),
    constraint foreign key (`trip_days_seq`) references `trip_days` (`trip_days_seq`),
    constraint foreign key (`trip_seq`) references `trips` (`trips_seq`)
);

CREATE TABLE `articles`
(
    `articles_seq`  int auto_increment,
    `title`         nvarchar(20) not null,
    `trips_seq`     int          NOT NULL,
    `created_at`    nvarchar(30) NOT NULL,
    `updated_at`    nvarchar(30) NOT NULL,
    `like_count`    int        DEFAULT 0,
    `reply_count`   int        DEFAULT 0,
    `open_yn`       varchar(1) DEFAULT 'n',
    `thumbnail_url` text         NOT NULL,
    constraint primary key (`articles_seq`),
    constraint foreign key (`trips_seq`) references `trips` (`trips_seq`)
);

CREATE TABLE `likes`
(
    `likes_seq`    int auto_increment,
    `users_seq`    int          NOT NULL,
    `articles_seq` int          NOT NULL,
    `liked_at`     nvarchar(30) not NULL,
    constraint primary key (`likes_seq`),
    constraint foreign key (`users_seq`) references `users` (`users_seq`),
    constraint foreign key (`articles_seq`) references `articles` (`articles_seq`)
        on delete cascade on update cascade
);

CREATE TABLE `user_achievements`
(
    `user_achievements_seq` int auto_increment,
    `users_seq`             int     NOT NULL,
    `region_seq`            tinyint NOT NULL,
    `count`                 int     NULL,
    constraint primary key (`user_achievements_seq`),
    constraint foreign key (`users_seq`) references `users` (`users_seq`),
    constraint foreign key (`region_seq`) references `region_tags` (`region_seq`)
);

CREATE TABLE `place_tags`
(
    `place_tag_seq` tinyint auto_increment,
    `tag`           nvarchar(15) not null,
    constraint primary key (`place_tag_seq`),
    constraint unique (`tag`)
);

CREATE TABLE `article_tags`
(
    `article_tags_seq` int auto_increment,
    `articles_seq`     int     NOT NULL,
    `place_tag_seq`    tinyint NOT NULL,
    constraint primary key (`article_tags_seq`),
    constraint foreign key (`articles_seq`) references `articles` (`articles_seq`)
        on delete cascade on update cascade
);

CREATE TABLE `tag_trip_places`
(
    `tag_trip_places_seq` int auto_increment,
    `place_tag_seq`       tinyint NOT NULL,
    `trip_places_seq`     int     NOT NULL,
    constraint primary key (`tag_trip_places_seq`),
    constraint foreign key (`place_tag_seq`) references `place_tags` (`place_tag_seq`),
    constraint foreign key (`trip_places_seq`) references `trip_places` (`trip_places_seq`)
);

CREATE TABLE `admin`
(
    `admin_seq`  int auto_increment,
    `admin_name` nvarchar(50),
    `created_at` nvarchar(30) NOT NULL,
    constraint primary key (`admin_seq`),
    constraint unique (`admin_name`)
);

CREATE TABLE `faq`
(
    `faq_seq`    int auto_increment,
    `admin_seq`  int          NOT NULL,
    `title`      nvarchar(30) NOT NULL,
    `content`    text         NOT NULL,
    `created_at` nvarchar(30) NOT NULL,
    `updated_at` nvarchar(30) NOT NULL,
    constraint primary key (`faq_seq`),
    foreign key (`admin_seq`) references `admin` (`admin_seq`)
);

CREATE TABLE `notice`
(
    `notice_seq` int auto_increment,
    `admin_seq`  int          NOT NULL,
    `title`      nvarchar(50) NOT NULL,
    `content`    nvarchar(50) NOT NULL,
    `created_at` nvarchar(30) NOT NULL,
    `updated_at` nvarchar(30) NOT NULL,
    constraint primary key (`notice_seq`),
    foreign key (`admin_seq`) references `admin` (`admin_seq`)
);

CREATE TABLE `inquires`
(
    `inquires_seq` int auto_increment,
    `users_seq`    int           NOT NULL,
    `admin_seq`    int           NOT NULL,
    `title`        nvarchar(30)  NOT NULL,
    `content`      nvarchar(100) NOT NULL,
    `created_at`   nvarchar(30)  NOT NULL,
    `answer_yn`    varchar(1) DEFAULT 'n',
    `answer`       text          NULL,
    `answer_at`    nvarchar(30)  NULL,
    constraint primary key (`inquires_seq`),
    constraint foreign key (`users_seq`) references `users` (`users_seq`),
    constraint foreign key (`admin_seq`) references `admin` (`admin_seq`)
);

CREATE TABLE `reports`
(
    `reports_seq`           int auto_increment,
    `reporter_users_seq`    int          NOT NULL,
    `reported_users_seq`    int          NOT NULL,
    `reported_at`           nvarchar(30) NOT NULL,
    `report_url`            nvarchar(50) NOT NULL,
    `report_categories_seq` int          NOT NULL,
    `handled_yn`            varchar(1) DEFAULT 'n',
    `article_type`          nvarchar(50) NOT NULL,
    constraint primary key (`reports_seq`),
    constraint foreign key (`reporter_users_seq`) references `users` (`users_seq`),
    constraint foreign key (`reported_users_seq`) references `users` (`users_seq`)
);

CREATE TABLE `report_categories`
(
    `report_categories_seq` int auto_increment,
    `name`                  nvarchar(30),
    constraint primary key (`report_categories_seq`),
    constraint unique (`name`)
);

CREATE TABLE `blocks`
(
    `blocks_seq`        int auto_increment,
    `blocker_users_seq` int NOT NULL,
    `blocked_users_seq` int NOT NULL,
    constraint primary key (`blocks_seq`),
    constraint foreign key (`blocked_users_seq`) references `users` (`users_seq`),
    constraint foreign key (`blocker_users_seq`) references `users` (`users_seq`)
);

CREATE TABLE `follows`
(
    `follows_seq`         int auto_increment,
    `following_users_seq` int NOT NULL,
    `follower_users_seq`  int NOT NULL,
    constraint primary key (`follows_seq`),
    constraint foreign key (`following_users_seq`) references `users` (`users_seq`),
    constraint foreign key (`follower_users_seq`) references `users` (`users_seq`)
);

CREATE TABLE `replies`
(
    `replies_seq`    int auto_increment,
    `articles_seq`   int          NOT NULL,
    `users_seq`      int          NOT NULL,
    `content`        nvarchar(50) NOT NULL,
    `created_at`     nvarchar(30) NOT NULL,
    `updated_at`     nvarchar(30) NOT NULL,
    `rereply_count`  int DEFAULT 0,
    `rereply_parent` int          NULL,
    constraint primary key (`replies_seq`),
    constraint foreign key (`articles_seq`) references `articles` (`articles_seq`)
        on delete cascade on update cascade,
    constraint foreign key (`users_seq`) references `users` (`users_seq`)
);

create view `tag_region_place` as
select `p`.`trip_places_seq`, `p`.`place_name`, `t`.`place_tag_seq`, `p`.`region_seq`
from `tag_trip_places` as `t`
         left join `trip_places` as `p`
                   on `t`.`trip_places_seq` = `p`.`trip_places_seq`;

create view `v_users_trips_articles` as
select `a`.`articles_seq`,
       `u`.`nickname`,
       `t`.`users_seq`,
       `u`.`profile_image_link`,
       `a`.`created_at`,
       `a`.`thumbnail_url`,
       `t`.`trip_comment`,
       `a`.`title`,
       (select min(`date`) from `trip_days` where `trip` = `t`.`trips_seq`) as `start_date`,
       (select max(`date`) from `trip_days` where `trip` = `t`.`trips_seq`) as `end_date`,
       `a`.`like_count`,
       `a`.`reply_count`,
       `a`.`open_yn`
from `daero`.`articles` as `a`
         left join `daero`.`trips` `t` on `t`.`trips_seq` = `a`.`trips_seq`
         left join `daero`.`users` `u` on `t`.`users_seq` = `u`.`users_seq`;