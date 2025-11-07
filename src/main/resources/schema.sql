DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS lockers;
DROP TABLE IF EXISTS sys_code;

-- =========================
-- Table: orders (訂單檔)
-- =========================
CREATE TABLE orders (
    order_no VARCHAR(20) PRIMARY KEY,
    send_phone VARCHAR(10) NOT NULL,
    send_name VARCHAR(20) NOT NULL,
    receive_phone VARCHAR(10) NOT NULL,
    receive_name VARCHAR(20) NOT NULL,
    item VARCHAR(1) NOT NULL,
    lockers_id INT8 NOT NULL,
    is_pickup BOOLEAN NOT NULL,
    payment INT NOT NULL,
    status VARCHAR(2) NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Table: lockers (櫃子檔)
-- =========================
CREATE TABLE lockers (
	id INT8 PRIMARY KEY,
    locker_no VARCHAR(10) NOT NULL,
    location VARCHAR(2) NOT NULL,
    is_active BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 為 (locker_no, location) 建立複合 unique key
ALTER TABLE lockers
ADD CONSTRAINT lockers_locker_no_location_uk
UNIQUE (locker_no, location);

-- =========================
-- Table: sys_code (系統管理檔)
-- =========================
CREATE TABLE sys_code (
	id INT8 PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    sys_key VARCHAR(100) NOT NULL,
    sys_value VARCHAR(100) NOT NULL,
    memo VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);