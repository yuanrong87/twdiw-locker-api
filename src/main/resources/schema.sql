DROP TABLE IF EXISTS lockers;
DROP TABLE IF EXISTS packages;

CREATE TABLE lockers (
    locker_no VARCHAR(10) PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP
);

CREATE TABLE packages (
    package_no VARCHAR(20) PRIMARY KEY,
    phone VARCHAR(10) NOT NULL,
    locker_no VARCHAR(10) NOT NULL,
    is_pickup BOOLEAN NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP,
    FOREIGN KEY (locker_no) REFERENCES lockers(locker_no)
);