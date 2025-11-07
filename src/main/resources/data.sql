-- =========================
-- Table: orders (訂單檔)
-- =========================
INSERT INTO orders (order_no, send_phone, send_name, receive_phone, receive_name, item, lockers_id, is_pickup, payment, status, create_time, update_time) VALUES
('OR202511042123320001', '0912345678', '王小明', '0984149387', '張大仁', '1', 1, FALSE, 50, '1', CURRENT_TIME, null),
('OR202511042123320002', '0987654321', '陳中明', '0935095184', '鄭小王', '2', 2, TRUE, 50, '3', CURRENT_TIME, null),
('OR202511042123320003', '0955123456', '林大明', '0958901348', '李中平', '1', 3, TRUE, 50, '5', CURRENT_TIME, null);

-- =========================
-- Table: lockers (櫃子檔)
-- =========================
INSERT INTO lockers (id, locker_no, location, is_active, create_time, update_time) VALUES
(1, 'L0001', '1', FALSE, CURRENT_TIME, null),
(2, 'L0001', '2', TRUE, CURRENT_TIME, null),
(3, 'L0002', '1', TRUE, CURRENT_TIME, null);

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