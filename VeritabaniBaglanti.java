import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;

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
//        System.out.println("Çalıştırılan sorgu : " +sql);
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

    public static ArrayList<String> veriOku(Connection conn) {
        ArrayList<String> skorListesi = new ArrayList<>();
        if (conn == null) {
            System.out.println("Bağlantı nesnesi null! Veritabanına bağlanmayı kontrol et.");
            skorListesi.add("Bağlantı Hatası");
            return skorListesi;
        }

        try {
            Statement stmt = conn.createStatement();
            //stmt bağlandıktan sonra query  ver; -> bütün veriyi çektik;
        ResultSet rs=stmt.executeQuery("SELECT * FROM Skor_Table");
        //kaç kayıt olduğunu -> meta info;
        ResultSetMetaData rsmd = rs.getMetaData();
            skorListesi.add(String.format("%-25s %18s %18s", "      Tarih", "PcSkor", "KullanıcıSkor"));
            skorListesi.add("----------------------------------------------------------------------------");
        //veritabanındaki columnları getirdim:
        while (rs.next()) {
            String satir=(String.format("%-30s %10d %15d",
                    rs.getString(1), rs.getInt(2), rs.getInt(3)));
            skorListesi.add(satir);
        }
        } catch (Exception e) {
            e.printStackTrace();
            skorListesi.add("Veri okunamadı!");
        }
        finally {
            baglantiKapat(conn);
        }return skorListesi;
    }
public static void TabloOluştur(Connection conn) {
        if(conn == null) {
            System.out.println("Bağlantı Hatası...");
            return;
        }
    Statement stmt = null;
    try{
        stmt=conn.createStatement();
        String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Skor_Table') " +
                "BEGIN " +
                "CREATE TABLE Skor_Table (" +
                "Tarih DATETIME NULL, " +
                "PcSkor INT NOT NULL, " +
                "KullanıcıSkor INT NOT NULL)" +
                "END;";
        stmt.executeUpdate(sql);
        System.out.println("Tablo kontrol edildi, yoksa oluşturuluyor...");

    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
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

