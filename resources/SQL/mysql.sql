drop table if exists user;
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- CREATE TABLE `user` (
--   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
--   `role` varchar(10) NOT NULL COMMENT '角色',
--   `username` varchar(20) NOT NULL COMMENT '用户名',
--   `password` varchar(100) NOT NULL COMMENT '密码',
--   `name` varchar(30) NOT NULL COMMENT '名称',
--   `phone` varchar(30) NOT NULL COMMENT '手机号',
--   `is_disable` tinyint(2) unsigned NOT NULL COMMENT '是否禁用',
--   `data_version` bigint(20) unsigned NOT NULL COMMENT '数据版本号（乐观锁）',
--   `created_at` datetime(6) NOT NULL COMMENT '创建时间',
--   `updated_at` datetime(6) COMMENT '修改时间',
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `username` (`username`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
