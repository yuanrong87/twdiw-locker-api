-- =========================
-- Table: lockers (櫃子檔)
-- =========================
INSERT INTO lockers (id, locker_no, location, is_active, create_time, update_time) VALUES
(1, 'L0001', '1', FALSE, CURRENT_TIME, null),
(2, 'L0002', '1', FALSE, CURRENT_TIME, null),
(3, 'L0003', '1', FALSE, CURRENT_TIME, null);

-- =========================
-- Table: sys_code (系統管理檔)
-- =========================
INSERT INTO sys_code (id, group_name, sys_key, sys_value, memo, is_active, create_time) VALUES
(1, 'LOCATION', '1', '台北車站', '據點', TRUE, CURRENT_TIME),
(2, 'LOCATION', '2', '板橋車站', '據點', TRUE, CURRENT_TIME),
(3, 'ORDER_STATUS', '1', '寄放中', '訂單狀態', TRUE, CURRENT_TIME),
(4, 'ORDER_STATUS', '2', '準備配送', '訂單狀態', TRUE, CURRENT_TIME),
(5, 'ORDER_STATUS', '3', '配送中', '訂單狀態', TRUE, CURRENT_TIME),
(6, 'ORDER_STATUS', '4', '已抵達', '訂單狀態', TRUE, CURRENT_TIME),
(7, 'ORDER_STATUS', '5', '已結案', '訂單狀態', TRUE, CURRENT_TIME);