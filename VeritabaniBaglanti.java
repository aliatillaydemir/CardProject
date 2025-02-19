import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglanti {
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-PIQ3E57;databaseName=SkorDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    public static Connection baglan() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Veritabanına başarıyla bağlandı!");
        } catch (SQLException e) {
            System.out.println("Veritabanına bağlanırken bir hata oluştu!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void baglantiKapat(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Bağlantı kapatıldı.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
