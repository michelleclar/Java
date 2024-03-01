create database db;

drop table if exists "db"."public"."user";
CREATE TABLE "db"."public"."user" (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建一个函数，用于在更新前设置 updated_at 列为当前时间戳
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建一个触发器，当更新行时调用上面定义的函数
CREATE TRIGGER trigger_update_updated_at
BEFORE UPDATE ON "db"."public"."user"
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

