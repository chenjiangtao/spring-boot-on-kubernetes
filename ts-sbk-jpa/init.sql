create database report char set utf8;

CREATE TABLE `report_config` (
                                 `id` int unsigned NOT NULL AUTO_INCREMENT,
                                 `class_name` varchar(20) NOT NULL,
                                 `status` smallint NOT NULL,
                                 `create_date` datetime NOT NULL,
                                 `update_date` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;