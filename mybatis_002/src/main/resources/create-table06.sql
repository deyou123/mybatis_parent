DROP TABLE IF EXISTS `user`;
create table user (
                      `id` int NOT NULL AUTO_INCREMENT,
                      create_time varchar(20) ,
                      name varchar(20),
                      password varchar(36),
                      phone varchar(20),
                      nick_name varchar(20),
                      primary key (`id`)

);
