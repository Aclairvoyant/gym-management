USE `gym-management`;

DROP TABLE IF EXISTS gym_users;
CREATE TABLE gym_users (
                           user_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                           user_name VARCHAR(12) NOT NULL UNIQUE comment '用户名',
                           password VARCHAR(64) NOT NULL COMMENT '用户密码',
                           user_real_name VARCHAR(10) DEFAULT '' COMMENT '用户姓名',
                           user_phone VARCHAR(11) DEFAULT '' COMMENT '手机号',
                           date_birth DATE COMMENT '用户生日',
                           user_type VARCHAR(1) DEFAULT '1' COMMENT '用户类型',
                           avatar VARCHAR(255) DEFAULT '' COMMENT '头像地址',
                           sex CHAR(1) DEFAULT '0' COMMENT '性别，0 男， 1 女， 2未知',
                           create_time DATETIME COMMENT '创建时间',
                           update_time DATETIME COMMENT '更新时间',
                           PRIMARY KEY(user_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '用户信息表';

INSERT INTO gym_users (user_name, password, user_real_name, user_phone, date_birth, user_type, avatar, sex, create_time, update_time)
VALUES
    ('user1', '7c6a180b36896a0a8c02787eeafb0e4c', 'John Doe', '12345678901', '1990-01-01', '1', 'avatar1.jpg', '0', NOW(), NOW()),
    ('user2', 'password2', 'Jane Doe', '98765432101', '1995-05-15', '2', 'avatar2.jpg', '1', NOW(), NOW()),
    ('user3', 'password3', 'Bob Smith', '55555555555', '1985-11-30', '1', 'avatar3.jpg', '0', NOW(), NOW());


DROP TABLE IF EXISTS gym_member_cards;
CREATE TABLE gym_member_cards (
                                  member_card_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '会员卡号',
                                  user_id BIGINT(20) NOT NULL COMMENT '用户id',
                                  avatar VARCHAR(100) DEFAULT '' COMMENT '头像地址',
                                  member_fee DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '会员余额',
                                  member_card_status CHAR(1) DEFAULT '0' COMMENT '会员卡状态，0 未激活， 1 已激活， 2 已过期',
                                  activate_time DATE COMMENT '激活日期',
                                  expire_time DATE COMMENT '过期日期',
                                  create_time DATETIME COMMENT '创建时间',
                                  update_time DATETIME COMMENT '更新时间',
                                  PRIMARY KEY(member_card_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '会员表';

INSERT INTO gym_member_cards (user_id, avatar, member_fee, activate_time, expire_time, create_time, update_time)
VALUES
    (101, 'avatar1.jpg', 50.00, '2023-01-01', '2023-12-31', NOW(), NOW()),
    (102, 'avatar2.jpg', 75.50, '2023-02-15', '2023-11-30', NOW(), NOW()),
    (103, 'avatar3.jpg', 100.00, '2023-03-10', '2024-03-10', NOW(), NOW());


DROP TABLE IF EXISTS gym_coachs;
CREATE TABLE gym_coachs (
                            coach_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '教练id',
                            coach_sex CHAR(1) DEFAULT '0' COMMENT '性别，0 男， 1 女， 2未知',
                            coach_real_name VARCHAR(10) NOT NULL COMMENT '教练姓名',
                            coach_phone VARCHAR(11) DEFAULT '' COMMENT '手机号',
                            coach_remark VARCHAR(500) DEFAULT NULL COMMENT '教练专长或备注',
                            create_time DATETIME COMMENT '创建时间',
                            update_time DATETIME COMMENT '更新时间',
                            PRIMARY KEY(coach_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '教练信息表';

INSERT INTO gym_coachs (coach_sex, coach_real_name, coach_phone, coach_remark, create_time, update_time)
VALUES
    ('0', 'John Doe', '12345678901', 'Weightlifting expert', NOW(), NOW()),
    ('1', 'Jane Doe', '98765432101', 'Yoga and flexibility training', NOW(), NOW()),
    ('2', 'Alex Smith', NULL, 'Certified nutritionist and personal trainer', NOW(), now());


DROP TABLE IF EXISTS gym_courses;
CREATE TABLE gym_courses (
                             course_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '课程id',
                             course_name VARCHAR(100) NOT NULL COMMENT '课程名',
                             coach_id BIGINT(20) NOT NULL COMMENT '教练id',
                             schedule_start DATETIME NOT NULL COMMENT '课程开始时间',
                             schedule_end DATETIME NOT NULL COMMENT '课程结束时间',
                             course_fee DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '课程金额',
                             create_time DATETIME COMMENT '创建时间',
                             update_time DATETIME COMMENT '更新时间',
                             PRIMARY KEY(course_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '课程信息表';

INSERT INTO gym_courses (course_name, coach_id, schedule_start, schedule_end, course_fee, create_time, update_time)
VALUES
    ('Weightlifting Basics', 101, '2023-01-15 10:00:00', '2023-01-15 11:30:00', 30.00, NOW(), NOW()),
    ('Yoga for Beginners', 102, '2023-02-01 18:30:00', '2023-02-01 20:00:00', 25.50, NOW(), NOW()),
    ('Nutrition Workshop', 103, '2023-03-05 14:00:00', '2023-03-05 16:00:00', 50.00, NOW(), NOW());


DROP TABLE IF EXISTS gym_booking;
CREATE TABLE gym_booking (
                             booking_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '预定id',
                             user_id BIGINT(20) NOT NULL COMMENT '用户id',
                             course_id BIGINT(20) NOT NULL COMMENT '课程id',
                             booking_date DATETIME COMMENT '预定日期时间',
                             PRIMARY KEY(booking_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '预定表';

INSERT INTO gym_booking (user_id, course_id, booking_date)
VALUES
    (101, 101, '2023-01-10 09:00:00'),
    (102, 102, '2023-02-15 17:30:00'),
    (103, 103, '2023-03-20 13:45:00');


DROP TABLE IF EXISTS gym_payments;
CREATE TABLE gym_payments (
                              payment_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '支付id',
                              user_id BIGINT(20) NOT NULL COMMENT '用户id',
                              amount DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
                              payment_date DATETIME COMMENT '支付日期时间',
                              payment_type VARCHAR(20) COMMENT '支付方式',
                              payment_status CHAR(1) DEFAULT '0' COMMENT '支付状态，0 未支付， 1 已支付',
                              PRIMARY KEY(payment_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '支付表';

INSERT INTO gym_payments (user_id, amount, payment_date, payment_type, payment_status)
VALUES
    (101, 50.00, '2023-01-05 14:30:00', 'Credit Card', '1'),
    (102, 25.50, '2023-02-20 10:00:00', 'PayPal', '1'),
    (103, 100.00, '2023-03-15 18:45:00', 'Bank Transfer', '1');


DROP TABLE IF EXISTS gym_logs;
CREATE TABLE gym_logs (
                          log_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
                          user_id BIGINT(20) NOT NULL COMMENT '用户id',
                          action_type VARCHAR(20) COMMENT '操作类型',
                          action_date DATETIME COMMENT '操作日期时间',
                          PRIMARY KEY(log_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT '日志表';

INSERT INTO gym_logs (user_id, action_type, action_date)
VALUES
    (101, 'Login', '2023-01-10 09:30:00'),
    (102, 'Course Booking', '2023-02-15 17:45:00'),
    (103, 'Payment', '2023-03-20 14:00:00');
