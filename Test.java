import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test extends javax.swing.JFrame {
    private JButton btnBasla;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
    private JButton[] kartlar;
    private String[] kartResimleri = {
            "images/futbolcu1.jpg",
            "images/futbolcu2.jpg",
            "images/futbolcu3.jpg",
            "images/futbolcu4.jpg",
            "images/futbolcu5.jpg",
            "images/futbolcu6.jpg",
            "images/futbolcu7.jpg",
            "images/futbolcu8.jpg",
            "images/basketbolcu1.jpg",
            "images/basketbolcu2.jpg",
            "images/basketbolcu3.jpg",
            "images/basketbolcu4.jpg",
            "images/basketbolcu5.jpg",
            "images/basketbolcu6.jpg",
            "images/basketbolcu7.jpg",
            "images/basketbolcu8.jpg"
    };
    private List<Sporcu> deste = new ArrayList<>();
    private Kullanıcı kullanıcı;
    private Bilgisayar bilgisayar;
    private int i = 0;
    private Image backgroundImage; // Background image for the whole frame
    private JPanel contentPanel; // Main content panel
    private static final Logger logger = Logger.getLogger(Test.class.getName());

    // Constructor
    public Test() {
        logger.info("Game Started!");
        setTitle("Futbolcu ve Basketbolcu Kart Oyunu");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Freeform layout

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        try{
            ImageIcon originalIcon = new ImageIcon("C:\\Users\\Dell\\Desktop\\KartProject\\images\\stat.jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Set background image on JLabel
            JLabel backgroundLabel = new JLabel(scaledIcon);
            backgroundLabel.setBounds(0, 0, width, height);

            // Create contentPanel and set layout
            contentPanel = new JPanel();
            contentPanel.setLayout(null);
            contentPanel.add(backgroundLabel); // Add background image to the panel
        }catch (Exception e){
            logger.log(Level.SEVERE, "Arka plan resmi yüklenirken hata oluştu!", e);
        }


        // Create buttons and labels
        btnBasla = new JButton("Oyunu Başlat");
        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");

        // Set positions of components
        btnBasla.setBounds(width / 2 - 100, height / 2 - 50, 200, 50);
        lblKullaniciSkor.setBounds(10, 10, 200, 30);
        lblBilgisayarSkor.setBounds(10, 50, 200, 30);
        btnBasla.setFocusPainted(false);

        // Add event listeners to buttons
        btnBasla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    oyunBaslat();
                    JPanel grayPanel = new JPanel();
                    grayPanel.setLayout(null);
                    grayPanel.setBounds(750, 350, 400, 200);
                    grayPanel.setBackground(Color.BLACK);
                    contentPanel.add(grayPanel);
                    contentPanel.setComponentZOrder(grayPanel, 0); // Bring grayPanel to the front
                    contentPanel.repaint();
                    contentPanel.revalidate();
                    btnBasla.setVisible(false); // Hide the start button
                }catch (Exception ex){
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
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            return new ImageIcon(img);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Resim yeniden boyutlandırılırken hata oluştu: " + imagePath, e);
            return null;
        }
    }

    // Start the game
    public void oyunBaslat() {


        btnBasla.setEnabled(false); // Disable the start button after clicking

        // Create cards
        try {
            deste.add(new Futbolcu("Ronaldo", "Juventus", 95, 90, 90));
            deste.add(new Futbolcu("Lionel Messi", "Barça", 100, 75, 90));
            deste.add(new Futbolcu("Neymar", "Barça", 90, 95, 95));
            deste.add(new Futbolcu("Robert Lewandowski", "Dortmunt", 95, 100, 95));
            deste.add(new Futbolcu("Kevin De Bruyne", "Man City", 88, 90, 85));
            deste.add(new Futbolcu("Mohamed Salah", "Liverpool", 85, 88, 90));
            deste.add(new Futbolcu("Virgil van Dijk", "Liverpool", 80, 85, 88));
            deste.add(new Futbolcu("Kylian Mbappe", "PSG", 92, 80, 90));

            deste.add(new Basketbolcu(85, 85, 85, "LeBron James", "Lakers"));
            deste.add(new Basketbolcu(90, 90, 90, "Stephen Curry", "Warriors"));
            deste.add(new Basketbolcu(88, 85, 89, "Kevin Durant", "Nets"));
            deste.add(new Basketbolcu(84, 80, 82, "James Harden", "76ers"));
            deste.add(new Basketbolcu(82, 78, 80, "Kobe Bryant", "Lakers"));
            deste.add(new Basketbolcu(90, 88, 85, "Michael Jordan", "Bulls"));
            deste.add(new Basketbolcu(85, 80, 83, "Giannis Antetokounmpo", "Bucks"));
            deste.add(new Basketbolcu(88, 86, 87, "Shaquille O'Neal", "Lakers"));

            Collections.shuffle(deste); // Shuffle the deck

            // Create user and computer players
            kullanıcı = new Kullanıcı("0", "Ati PC", 0);
            bilgisayar = new Bilgisayar("1", "Furki", 0);

            // Distribute cards
            for (; i < deste.size(); i++) {
                if (i % 2 == 0) {
                    kullanıcı.getKartListesi().add(deste.get(i));
                } else {
                    bilgisayar.getKartListesi().add(deste.get(i));
                }
            } logger.info("Kartlar başarıyla dağıtıldı.");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Kartları dağıtırken bir hata oluştu!",e);
        }





        int kartGenislik = 150;
        int kartYukseklik = 200;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;


// Üst panel (bilgisayarın kartları)
        JPanel ustPanel = new JPanel(new GridLayout(1, 8, 50, 10)); // 1 sıra, 8 kart
        ustPanel.setBounds(140, 50, 1600, 200);
        ustPanel.setOpaque(false); //-> opaklığı ayarlarız

// Alt panel (oyuncunun kartları)
        JPanel altPanel = new JPanel(new GridLayout(1, 8, 50, 10)); // 1 sıra, 8 kart
        altPanel.setBounds(140, height - 300, 1600, 200);
        altPanel.setOpaque(false);

// Kapalı kart görseli (bilgisayarın kartları için)
        ImageIcon kapaliKart = resizeImage("images/kart_arka.jpg", kartGenislik, kartYukseklik);

        kartlar = new JButton[16];

        for (int i = 0; i < 16; i++) {
            kartlar[i] = new JButton();
            kartlar[i].setPreferredSize(new Dimension(kartGenislik, kartYukseklik));

            if (i < 4) {
                // Bilgisayarın futbol kartları (kapalı)
                kartlar[i].setIcon(kapaliKart);
                ustPanel.add(kartlar[i]);
            } else if (i < 8) {
                // Oyuncunun futbol kartları (açık)
                ImageIcon icon = resizeImage(kartResimleri[i - 4], kartGenislik, kartYukseklik);
                kartlar[i].setIcon(icon);
                altPanel.add(kartlar[i]);
            } else if (i < 12) {
                // Bilgisayarın basketbol kartları (kapalı)
                kartlar[i].setIcon(kapaliKart);
                ustPanel.add(kartlar[i]);
            } else {
                // Oyuncunun basketbol kartları (açık)
                ImageIcon icon = resizeImage(kartResimleri[i - 4], kartGenislik, kartYukseklik);
                kartlar[i].setIcon(icon);
                altPanel.add(kartlar[i]);
            }
        }

// Ana panelleri içeriğe ekle ve güncelle
        contentPanel.add(ustPanel);
        contentPanel.add(altPanel);
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    public Sporcu bilgisayarKartSec(boolean futbolcuMu) {
        for (Sporcu kart : bilgisayar.getKartListesi()) {
            if (futbolcuMu && kart instanceof Futbolcu) {
                return kart;
            } else if (!futbolcuMu && kart instanceof Basketbolcu) {
                return kart;
            }
        }
        return null; // Eğer uygun kart yoksa
    }




    //Eksik sırasıyla bi kullanıcı bir bilgisayar kart seçecek
    //bilgisayar otomatik atayacak kullanıcı seçtikten sonra;
    public void kartSec(JButton secilenKartButon, int kartIndex) {
        try {
            if (kullanıcı.getKartListesi().isEmpty() || bilgisayar.getKartListesi().isEmpty()) {
                logger.info("Oyun Bitti.");
                return;
            }

            // Kullanıcının seçtiği kart
            Sporcu kullanıcıKart = kullanıcı.getKartListesi().get(kartIndex);

            // Bilgisayarın aynı kategoriden kart seçmesi
            Sporcu bilgisayarKart = bilgisayarKartSec(kullanıcıKart instanceof Futbolcu);

            if (bilgisayarKart == null) {
                logger.warning("Bilgisayar uygun kart bulamadı!");
                return;
            }

            // Seçilen kartları orta alana ekle
            JPanel ortaPanel = new JPanel(new GridLayout(1, 2, 20, 10));
            ortaPanel.setBounds(700, 400, 300, 200);
            ortaPanel.setOpaque(false);

            JButton kullaniciKartGoruntu = new JButton(resizeImage(kullanıcıKart.getImagePath(), 150, 200));
            JButton bilgisayarKartGoruntu = new JButton(resizeImage("images/kart_arka.jpg", 150, 200));

            ortaPanel.add(kullaniciKartGoruntu);
            ortaPanel.add(bilgisayarKartGoruntu);

            contentPanel.add(ortaPanel);
            contentPanel.revalidate();
            contentPanel.repaint();

            Timer timer = new Timer(1000, event -> {
                bilgisayarKartGoruntu.setIcon(resizeImage(bilgisayarKart.getImagePath(), 150, 200));
                contentPanel.repaint();

                // Kazananı belirle
                kazananBelirle(kullanıcıKart, bilgisayarKart);

                // Kartları listeden çıkar
                kullanıcı.getKartListesi().remove(kartIndex);
                bilgisayar.getKartListesi().remove(bilgisayarKart);

                logger.info("Kart seçimi tamamlandı.");
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Kart seçerken hata oluştu!", e);
        }
    }


    public void kazananBelirle(Sporcu kullaniciKart, Sporcu bilgisayarKart) {
        logger.info("Kazanan belirleniyor...");

        if (kullaniciKart instanceof Futbolcu && bilgisayarKart instanceof Futbolcu) {
            Futbolcu kKart = (Futbolcu) kullaniciKart;
            Futbolcu bKart = (Futbolcu) bilgisayarKart;

            if (kKart.getPenaltı() > bKart.getPenaltı() & kKart.getKaleciKarsiKarsiya()> bKart.getKaleciKarsiKarsiya() & kKart.getSerbestAtis()>bKart.getSerbestAtis()) {
                logger.info("Kullanıcı kazandı!");
                kullanıcı.setSkor(kullanıcı.getSkor() + 10);
            } else {
                logger.info("Bilgisayar kazandı!");
                bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            }
        } else if (kullaniciKart instanceof Basketbolcu && bilgisayarKart instanceof Basketbolcu) {
            Basketbolcu kKart = (Basketbolcu) kullaniciKart;
            Basketbolcu bKart = (Basketbolcu) bilgisayarKart;

            if (kKart.getSerbestAtis() > bKart.getSerbestAtis() & kKart.getUcluk()>bKart.getUcluk() & kKart.getIkilik()>bKart.getIkilik()) {
                logger.info("Kullanıcı kazandı!");
                kullanıcı.setSkor(kullanıcı.getSkor() + 10);
            } else {
                logger.info("Bilgisayar kazandı!");
                bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            }
        } else {
            logger.info("Hatalı eşleşme! Futbolcu ile basketbolcu karşılaştı.");
        }

        lblKullaniciSkor.setText("Kullanıcı Skor: " + kullanıcı.getSkor());
        lblBilgisayarSkor.setText("Bilgisayar Skor: " + bilgisayar.getSkor());
    }

    public static void main(String[] args) {
        Test frame = new Test();
        frame.setVisible(true);
    }
}