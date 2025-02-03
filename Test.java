import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test extends javax.swing.JFrame {
    private JButton btnBasla;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
    private JButton[] kartlar;
    private String[] FutbolcuResimleri = {
            "images/Ronaldo.jpg",
            "images/Kevin De Bruyne.jpg",
            "images/Neymar.jpg",
            "images/Kylian Mbappe.jpg",
            "images/Virgil van Dijk.jpg",
            "images/Lionel Messi.jpg",
            "images/Mohamed Salah.jpg",
            "images/Robert Lewandowski.jpg",
    };
    private String[] BasketbolcuResimleri = {
            "images/LeBron James.jpg",
            "images/Stephen Curry.jpg",
            "images/ShaquilleONeal.jpg", // Düzeltildi
            "images/Michael Jordan.jpg",
            "images/Kobe Bryant.jpg",
            "images/James Harden.jpg",
            "images/Giannis Antetokounmpo.jpg",
            "images/Kevin Durant.jpg"
    };




    private List<Futbolcu> futbolKartlari = new ArrayList<>();
    private List<Basketbolcu> basketbolKartlari = new ArrayList<>();
    private Kullanıcı kullanıcı;
    private Bilgisayar bilgisayar;
    private JPanel contentPanel;
    private static final Logger logger = Logger.getLogger(Test.class.getName());

    public Test() {
        logger.info("Game Started!");
        setTitle("Futbolcu ve Basketbolcu Kart Oyunu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        try {
            ImageIcon originalIcon = new ImageIcon("images/stat.jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel backgroundLabel = new JLabel(scaledIcon);
            backgroundLabel.setBounds(0, 0, width, height);
            contentPanel.add(backgroundLabel);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Arka plan resmi yüklenirken hata oluştu!", e);
        }

        btnBasla = new JButton("Oyunu Başlat");
        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");

        btnBasla.setBounds(width / 2 - 100, height / 2 - 50, 200, 50);
        lblKullaniciSkor.setBounds(10, 10, 200, 30);
        lblBilgisayarSkor.setBounds(10, 50, 200, 30);
        btnBasla.setFocusPainted(false);

        btnBasla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    oyunBaslat();
                    btnBasla.setVisible(false);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Oyunu başlatırken hata oluştu!", ex);
                }
            }
        });

        contentPanel.add(btnBasla);
        contentPanel.add(lblKullaniciSkor);
        contentPanel.add(lblBilgisayarSkor);
        setContentPane(contentPanel);
        setSize(width, height);
    }
    public ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }


    public void oyunBaslat() {
        btnBasla.setEnabled(false);

        futbolKartlari.add(new Futbolcu("Ronaldo", "Juventus", 95, 90, 90));
        futbolKartlari.add(new Futbolcu("Lionel Messi", "Barça", 100, 75, 90));
        futbolKartlari.add(new Futbolcu("Neymar", "Barça", 90, 95, 95));
        futbolKartlari.add(new Futbolcu("Robert Lewandowski", "Dortmund", 95, 100, 95));
        futbolKartlari.add(new Futbolcu("Kevin De Bruyne", "Man City", 88, 90, 85));
        futbolKartlari.add(new Futbolcu("Mohamed Salah", "Liverpool", 85, 88, 90));
        futbolKartlari.add(new Futbolcu("Virgil van Dijk", "Liverpool", 80, 85, 88));
        futbolKartlari.add(new Futbolcu("Kylian Mbappe", "PSG", 92, 80, 90));

        basketbolKartlari.add(new Basketbolcu(85, 85, 85, "LeBron James", "Lakers"));
        basketbolKartlari.add(new Basketbolcu(90, 90, 90, "Stephen Curry", "Warriors"));
        basketbolKartlari.add(new Basketbolcu(88, 85, 89, "Kevin Durant", "Nets"));
        basketbolKartlari.add(new Basketbolcu(84, 80, 82, "James Harden", "76ers"));
        basketbolKartlari.add(new Basketbolcu(82, 78, 80, "Kobe Bryant", "Lakers"));
        basketbolKartlari.add(new Basketbolcu(90, 88, 85, "Michael Jordan", "Bulls"));
        basketbolKartlari.add(new Basketbolcu(85, 80, 83, "Giannis Antetokounmpo", "Bucks"));
        basketbolKartlari.add(new Basketbolcu(88, 86, 87, "Shaquille O'Neal", "Lakers"));

        Collections.shuffle(futbolKartlari);
        Collections.shuffle(basketbolKartlari);

        kartlariDagit();
        // Kart butonlarını oluştur
        kartlar = new JButton[16];

        int kartGenislik = 170;
        int kartYukseklik = 200;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

// Bilgisayar ve Oyuncu için 4 farklı panel oluştur
        JPanel ustFutbolPanel = new JPanel(new GridLayout(1, 4, 50, 10)); // Bilgisayarın futbol kartları
        JPanel ustBasketbolPanel = new JPanel(new GridLayout(1, 4, 50, 10)); // Bilgisayarın basketbol kartları
        JPanel altFutbolPanel = new JPanel(new GridLayout(1, 4, 50, 10)); // Oyuncunun futbol kartları
        JPanel altBasketbolPanel = new JPanel(new GridLayout(1, 4, 50, 10)); // Oyuncunun basketbol kartları

// Panelleri konumlandır
        ustFutbolPanel.setBounds(140, 50, 800, 200);
        ustBasketbolPanel.setBounds(140, 260, 800, 200);
        altFutbolPanel.setBounds(140, height - 500, 800, 200);
        altBasketbolPanel.setBounds(140, height - 300, 800, 200);

// Panelleri şeffaf yap
        ustFutbolPanel.setOpaque(false);
        ustBasketbolPanel.setOpaque(false);
        altFutbolPanel.setOpaque(false);
        altBasketbolPanel.setOpaque(false);

// Kapalı kart görseli (bilgisayarın kartları için)
        ImageIcon kapaliKart = resizeImage("images/black.jpg", kartGenislik, kartYukseklik);
        for (int i = 0; i < 16; i++) {
            kartlar[i] = new JButton();
            kartlar[i].setPreferredSize(new Dimension(kartGenislik, kartYukseklik));

            if (i < 4) {
                // Üstteki 4 futbolcu (bilgisayarın kartları kapalı)
                kartlar[i].setIcon(kapaliKart);
                ustFutbolPanel.add(kartlar[i]);

            } else if (i < 8) {
                // Alttaki 4 futbolcu (oyuncunun kartları açık)
                ImageIcon icon = resizeImage(FutbolcuResimleri[i - 4], kartGenislik, kartYukseklik);
                kartlar[i].setIcon(icon);
                altFutbolPanel.add(kartlar[i]);

            } else if (i < 12) {
                // Üstteki 4 basketbolcu (bilgisayarın kartları kapalı)
                kartlar[i].setIcon(kapaliKart);
                ustBasketbolPanel.add(kartlar[i]);

            } else {
                // Alttaki 4 basketbolcu (oyuncunun kartları açık)
                ImageIcon icon = resizeImage(BasketbolcuResimleri[i - 12], kartGenislik, kartYukseklik);
                kartlar[i].setIcon(icon);
                altBasketbolPanel.add(kartlar[i]);
            }
        }





// Panelleri içeriğe ekle
        contentPanel.add(ustFutbolPanel);
        contentPanel.add(ustBasketbolPanel);
        contentPanel.add(altFutbolPanel);
        contentPanel.add(altBasketbolPanel);

        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private void kartlariDagit() {
        try {
            kullanıcı = new Kullanıcı("0", "Ati PC", 0);
            bilgisayar = new Bilgisayar("1", "Furki", 0);

            if (futbolKartlari.size() < 8 || basketbolKartlari.size() < 8) {
                throw new IllegalStateException("Kart listeleri eksik! 8'er kart olması gerekiyor.");
            }

            for (int i = 0; i < 4; i++) {
                kullanıcı.getKartListesi().add(futbolKartlari.get(i));
                bilgisayar.getKartListesi().add(futbolKartlari.get(i + 4));
                kullanıcı.getKartListesi().add(basketbolKartlari.get(i));
                bilgisayar.getKartListesi().add(basketbolKartlari.get(i + 4));
            }

            logger.info("Kartlar başarıyla dağıtıldı.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Kartları dağıtırken bir hata oluştu!", e);
        }
    }

    public static void main(String[] args) {
        Test frame = new Test();
        frame.setVisible(true);
    }
}
