-- =========================
-- Table: lockers (櫃子檔)
-- =========================
INSERT INTO lockers (locker_no, is_active, create_time, update_time) VALUES
('L001', FALSE, CURRENT_TIME, null),
('L002', TRUE, CURRENT_TIME, null),
('L003', TRUE, CURRENT_TIME, null);

-- =========================
-- Table: packages (包裹檔)
-- =========================
INSERT INTO packages (package_no, phone, locker_no, is_pickup, create_time, update_time) VALUES
('P001', '0912345678', 'L001', FALSE, CURRENT_TIME, null),
('P002', '0987654321', 'L002', TRUE, CURRENT_TIME, null),
('P003', '0955123456', 'L003', TRUE, CURRENT_TIME, null);
