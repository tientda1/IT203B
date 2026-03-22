import java.sql.Connection;
public class DBConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc?createDatabaseIfNotExist=true";

    public static Connection openConnection(){
        try{
            Class.forName(DRIVER);
            return java.sql.DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void createTables() {
        Connection con = openConnection();
        if (con != null) {
            try {
                java.sql.Statement stmt = con.createStatement();

                // Tạo bảng students
                String createStudents = "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "email VARCHAR(100) UNIQUE, " +
                        "phone VARCHAR(20), " +
                        "address VARCHAR(255), " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                stmt.executeUpdate(createStudents);

                System.out.println("Kết nối thành công : Phùng Quang Tiến");
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        createTables();
    }


}