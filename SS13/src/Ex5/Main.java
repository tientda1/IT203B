// 1. Phân tích Rủi ro & Bẫy lỗi (Edge Cases)
//  Để hệ thống không bị "crash" hay sinh ra rác dữ liệu, chúng ta phải lường trước và giăng bẫy 3 kịch bản sau:
//  Bẫy 1 - Lỗi Nhập liệu (Input Mismatch): Lễ tân đang vội nên gõ chữ "Năm trăm" vào ô nhập số tiền, hoặc nhập tuổi là chữ. Nếu dùng scanner.nextInt() thông thường, chương trình sẽ ném InputMismatchException và văng (crash) ngay lập tức.
//  Khắc phục: Đọc input dưới dạng chuỗi (nextLine()) và dùng try-catch với Integer.parseInt() / Double.parseDouble() để ép kiểu. Nếu lỗi, yêu cầu nhập lại.
//  Bẫy 2 - Xung đột tài nguyên (Race Condition / Logical Error): Lễ tân chọn mã giường "G01", nhưng giường này không tồn tại trong DB, hoặc ngay khoảnh khắc đó đã có một y tá khác xếp người vào "G01" rồi.
//  Khắc phục: Khi chạy lệnh UPDATE Beds SET status = 'OCCUPIED' WHERE bed_id = ? AND status = 'AVAILABLE', bắt buộc phải kiểm tra Row Affected. Nếu trả về 0, nghĩa là giường không tồn tại hoặc không còn trống -> Chủ động ném Exception để Rollback toàn bộ tiến trình.
//  Bẫy 3 - Khóa ngoại (Foreign Key) và mất kết nối: Lệnh số 3 (Thêm phiếu thu tiền) cần biết Mã bệnh nhân vừa được tạo ở Lệnh số 1. Nếu hardcode hoặc mạng giật giữa chừng, phiếu thu sẽ bị mồ côi (không biết của ai).
//  Khắc phục: Sử dụng cờ Statement.RETURN_GENERATED_KEYS trong JDBC để lấy ngay ID bệnh nhân vừa Insert. Bọc toàn bộ trong conn.rollback() để đảm bảo an toàn.
//2. Thiết kế Kiến trúc (Architecture & Data Flow)
//  Chúng ta sẽ áp dụng Kiến trúc 3 lớp (3-Tier Architecture) để tách biệt giao diện, logic và kết nối database:
//  View (ReceptionView): Quản lý vòng lặp Menu Console, nhận input từ bàn phím, validate (chống nhập sai kiểu) và in ra thông báo.
//  Controller (AdmissionController): Nhận dữ liệu sạch từ View, mở Connection, điều phối 3 câu lệnh SQL trong 1 Transaction, xử lý commit hoặc rollback.
//  Database (DatabaseHelper): Class tĩnh (static) chuyên cung cấp Connection.
//  Cấu trúc Database (Lược đồ):
//  Bảng Patients: patient_id (INT, Auto_Increment, PK), full_name (VARCHAR), age (INT).
//  Bảng Beds: bed_id (VARCHAR, PK), status (VARCHAR - 'AVAILABLE' / 'OCCUPIED').
//  Bảng Financial_Records: record_id (INT, Auto_Increment, PK), patient_id (INT, FK), amount (DOUBLE).
//  Luồng dữ liệu Tiếp nhận (Core Feature):
//  Mở Connection -> setAutoCommit(false).
//  INSERT vào Patients -> Lấy patient_id tự sinh ra.
//  UPDATE bảng Beds -> Check Row Affected == 1.
//  INSERT vào Financial_Records với patient_id và amount.
//  Commit() nếu tất cả thành công -> Trả về true cho View.
