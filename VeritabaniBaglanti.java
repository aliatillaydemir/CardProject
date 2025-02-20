import java.sql.*;

public class VeritabaniBaglanti {
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-PIQ3E57;databaseName=SkorDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";


    public static Connection baglan() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Veritabanına başarıyla bağlandı!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Veritabanına bağlanırken bir hata oluştu!");
            e.printStackTrace();
            return null;
        }
    }


    public static void insertSkor(int kullaniciSkor, int pcSkor) {
        String sql = "INSERT INTO Skor_Table (Tarih, KullanıcıSkor,PcSkor) VALUES (GETDATE(), ?, ?)";
        //logger kontroll
        System.out.println("Çalıştırılan sorgu : " +sql);
        System.out.println("Kullanıcı Skor :" +kullaniciSkor +"\tBilgisayar Skor : "+pcSkor);
        Connection conn = baglan();

        //bağlantı yoksa sorunluysa uyarı;
        if (conn == null) {
            System.out.println("Bağlantı başarısız, skor eklenemedi!");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kullaniciSkor);
            pstmt.setInt(2, pcSkor);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Skor başarıyla eklendi!");
            }
        } catch (SQLException e) {
            System.out.println("Skor eklenirken hata oluştu.");
            e.printStackTrace();
        } finally {
            baglantiKapat(conn);
        }
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
