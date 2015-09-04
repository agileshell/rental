DROP TABLE IF EXISTS `renter`;

CREATE TABLE `renter` (
  `renter_id` INTEGER PRIMARY KEY AUTOINCREMENT, 
  `name` varchar(64) NOT NULL,
  `mobile` char(15) DEFAULT NULL,
  `id_card` varchar(30) NOT NULL,
  `comment` VARCHAR(255) NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `subarea`;

CREATE TABLE `subarea` (
  `subarea_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` varchar(64) NOT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `stall`;

CREATE TABLE `stall` (
  `stall_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `subarea_id` int(10) NOT NULL,
  `name` varchar(64) NOT NULL,
  `month_price` int(10) NOT NULL,
  `renter_id` int(10) NULL DEFAULT NULL,
  `stall_renter_id` int(10) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `stall_renter`;

CREATE TABLE `stall_renter` (
  `stall_renter_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `stall_id` int(10) NOT NULL,
  `renter_id` int(10) NOT NULL,
  `has_rented` tinyint(1) NOT NULL DEFAULT 1,
  `rent_date` DATE NOT NULL DEFAULT CURRENT_DATE,
  `unrent_date` DATE NULL DEFAULT NULL,
  `init_watermeter` VARCHAR(30) NULL DEFAULT NULL,
  `init_meter` VARCHAR(30) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `stall_payment_history`;

CREATE TABLE `stall_payment_history` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `stall_renter_id` int(10) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `quarter` tinyint(1) NOT NULL,
  `total_price` int(10) NOT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `stall_utilities_payment_history`;

CREATE TABLE `stall_utilities_payment_history` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `stall_renter_id` int(10) NOT NULL,
  `first_record` VARCHAR(30) NULL DEFAULT NULL,
  `last_record` VARCHAR(30) NULL DEFAULT NULL,
  `quarter` tinyint(1) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT 1, -- 1 water, 2 electricity
  `price` float NOT NULL,
  `total_price` int(10) NOT NULL,
  `record_date` DATE NOT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `stall_fee_statistics`;

CREATE TABLE `stall_fee_statistics` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `stall_renter_id` int(10) NOT NULL,
  `year` CHAR(4) NOT NULL,
  `quarter` tinyint(1) NOT NULL DEFAULT 1,
  `is_pay_rent` tinyint(1) NOT NULL DEFAULT 0,--0未填单，1未确认，2已确认
  `is_pay_meter` tinyint(1) NOT NULL DEFAULT 0,
  `is_pay_water` tinyint(1) NOT NULL DEFAULT 0,
  `is_pay_all` tinyint(1) NOT NULL DEFAULT 0,
  `rent_fee` float NOT NULL DEFAULT 0,
  `meter_fee` float NOT NULL DEFAULT 0,
  `water_fee` float NOT NULL DEFAULT 0,
  `total_fee` float NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `flat`;

CREATE TABLE `flat` (
  `flat_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `name` varchar(64) NOT NULL,
  `month_price` int(10) NOT NULL,
  `renter_id` int(10) NULL DEFAULT NULL,
  `flat_renter_id` int(10) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `flat_renter`;

CREATE TABLE `flat_renter` (
  `flat_renter_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `flat_id` int(10) NOT NULL,
  `renter_id` int(10) NOT NULL,
  `has_rented` tinyint(1) NOT NULL DEFAULT 1,
  `rent_date` DATE NOT NULL DEFAULT CURRENT_DATE,
  `unrent_date` DATE NULL DEFAULT NULL,
  `init_meter` VARCHAR(30) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `flat_payment_history`;

CREATE TABLE `flat_payment_history` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `flat_renter_id` int(10) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `quarter` tinyint(1) NOT NULL,
  `total_price` int(10) NOT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `flat_meter_payment_history`;

CREATE TABLE `flat_meter_payment_history` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `flat_renter_id` int(10) NOT NULL,
  `first_record` VARCHAR(30) NULL DEFAULT NULL,
  `last_record` VARCHAR(30) NULL DEFAULT NULL,
  `quarter` tinyint(1) NOT NULL,
  `price` float NOT NULL,
  `total_price` int(10) NOT NULL,
  `record_date` DATE NOT NULL,
  `comment` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `flat_fee_statistics`;

CREATE TABLE `flat_fee_statistics` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `flat_renter_id` int(10) NOT NULL,
  `year` CHAR(4) NOT NULL,
  `quarter` tinyint(1) NOT NULL DEFAULT 1,
  `is_pay_rent` tinyint(1) NOT NULL DEFAULT 0,--0未填单，1未确认，2已确认
  `is_pay_meter` tinyint(1) NOT NULL DEFAULT 0,
  `is_pay_all` tinyint(1) NOT NULL DEFAULT 0,
  `rent_fee` float NOT NULL DEFAULT 0,
  `meter_fee` float NOT NULL DEFAULT 0,
  `total_fee` float NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `fee_statistics`;

CREATE TABLE `fee_statistics` (
  `record_date` CHAR(10) PRIMARY KEY,
  `year` CHAR(4) NOT NULL,
  `month` CHAR(2) NOT NULL,
  `quarter` tinyint(1) NOT NULL DEFAULT 1,
  `stall_rent_fee` float NOT NULL DEFAULT 0,
  `stall_meter_fee` float NOT NULL DEFAULT 0,
  `stall_water_fee` float NOT NULL DEFAULT 0,
  `flat_rent_fee` float NOT NULL DEFAULT 0,
  `flat_meter_fee` float NOT NULL DEFAULT 0,
  `total_fee` float NOT NULL DEFAULT 0,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

DROP TABLE IF EXISTS `system_setting`;

CREATE TABLE `system_setting` (
  `system_setting_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `key` varchar(255) NOT NULL,
  `value` VARCHAR(255) NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL
);

INSERT INTO `system_setting`(`key`, `value`) VALUES ('watermeter', '1.0'), ('meter', '1.0'),
('first_quarter_start', '03-01'), ('first_quarter_end', '05-31'),
('second_quarter_start', '06-01'), ('second_quarter_end', '08-31'),
('third_quarter_start', '09-01'), ('third_quarter_end', '11-30'),
('fourth_quarter_start', '12-01'), ('fourth_quarter_end', '02-28');