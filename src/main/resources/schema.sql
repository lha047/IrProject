 CREATE TABLE users (
id BIGINT NOT NULL ,
screen_name VARCHAR( 15 ) NOT NULL ,
name VARCHAR( 20 ) NOT NULL ,
url VARCHAR( 300 ) NOT NULL ,
profile_image_url VARCHAR( 300 ) NOT NULL ,
description VARCHAR( 160 ) NOT NULL ,
location VARCHAR( 100 ) NOT NULL ,
date DATE NOT NULL ,
favorites_count INT NOT NULL ,
followers_count INT NOT NULL ,
friends_count INT NOT NULL ,
language VARCHAR( 100 ) NOT NULL ,
profile_url VARCHAR( 300 ) NOT NULL ,
statuses_count INT NOT NULL ,
fitness_score FLOAT NOT NULL ,
last_updated DATE NOT NULL ,
PRIMARY KEY (id)
);

