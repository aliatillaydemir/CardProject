import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test extends javax.swing.JFrame {
    private JButton btnBasla;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
    private  JButton[] kartlar;
   private String[] kartResimleri= {
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu1.jpg" , "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu2.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu3.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu4.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu5.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu6.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu7.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\futbolcu8.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu1.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu2.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu3.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu4.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu5.jpg", "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu6.jpg",
           "C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu7.jpg","C:\\Users\\Dell\\Desktop\\KartProject\\images\\basketbolcu8.jpg"
    };
    private List<Sporcu> deste = new ArrayList<>();
    private Kullanıcı kullanıcı;
    private Bilgisayar bilgisayar;
    private int i = 0;
    private Image backgroundImage; // büyük arayüz bg si
    private JPanel contentPanel; //büyük ana arayüz

    //constructer
    public Test() {
        System.out.println("Hello ati");
        // JFrame özelliklerini ayarlıyoruz
        setTitle("Futbolcu ve Basketbolcu Kart Oyunu");
        setExtendedState(JFrame.MAXIMIZED_BOTH); //full screen yaptım!
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // null layout kullanarak özgürce yerleştirme yapılabilir

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        // Resmi yükle ve yeniden boyutlandır
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Dell\\Desktop\\KartProject\\images\\stat.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // JLabel ile arka plan olarak ekle
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, width, height);

        // JPanel'i oluşturuyoruz ve üzerine arka planı ve diğer bileşenleri ekliyoruz
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // null layout kullanarak özgürce yerleştirme yapılabilir
        contentPanel.add(backgroundLabel); // Arka planı ekle


        // Bileşenleri oluşturuyoruz
        btnBasla = new JButton("Oyunu Başlat");

        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");

        // Konumları belirliyoruz
        btnBasla.setBounds(width / 2 - 100, height / 2 - 50, 200, 50);
        lblKullaniciSkor.setBounds(10, 10, 200, 30);
        lblBilgisayarSkor.setBounds(10, 50, 200, 30);
        btnBasla.setFocusPainted(false);


        // Düğmelere tıklama olayları ekliyoruz
        btnBasla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oyunBaslat();
                JPanel grayPanel = new JPanel();
                grayPanel.setLayout(null);
                grayPanel.setBounds(750, 350, 400, 200);
                grayPanel.setBackground(Color.BLACK);
                contentPanel.add(grayPanel);
                contentPanel.setComponentZOrder(grayPanel, 0); // grayPanel'i en üste getir
                contentPanel.repaint();
                contentPanel.revalidate();
               btnBasla.setVisible(false);
            }
        });



        //contentPanel bizim ana containerımız!
        // Bileşenleri contentPanel'e ekliyoruz
        contentPanel.add(btnBasla);

        contentPanel.add(lblKullaniciSkor);
        contentPanel.add(lblBilgisayarSkor);



        // contentPanel'i JFrame'e set ediyoruz
        setContentPane(contentPanel);

        // Boyutu ayarlıyoruz
        setSize(width, height);

    }
    // Burada oyun başlatılacak ve gerekli işlemler yapılacak
    public void oyunBaslat() {
        btnBasla.setEnabled(false); // Oyunu başlattıktan sonra "Başlat" butonunu devre dışı bırak
        btnBasla.setVisible(false);

        // Kartları oluşturma
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

        Collections.shuffle(deste);

        // Kullanıcı ve bilgisayar oluşturma
        kullanıcı = new Kullanıcı("0", "Ati PC", 0);
        bilgisayar = new Bilgisayar("1", "Furki", 0);

        // Kartları dağıtma
        for (; i < deste.size(); i++) {
            if (i % 2 == 0) {
                kullanıcı.getKartListesi().add(deste.get(i));
            } else {
                bilgisayar.getKartListesi().add(deste.get(i));
            }
        }

        // Oyun başlatıldı
        System.out.println("Oyun Başladı!");
        kartlar= new JButton[16]; //16 adet sporcu kartımız var ->16 adet buton oluşturduk👍;
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new GridLayout(4, 4)); //kartları 4 futbol 4 basker ten hem pc hem oyuncu için
        for(int i=0;i<16;i++){
            kartlar[i]=new JButton(); //kartları butonlara yerleştirdimm
            //görselleri butonlara yerleştirme;
            ImageIcon icon= new ImageIcon(kartResimleri[i]);
            kartlar[i].setIcon(icon);//butonlara görsel ekleme
            kartlar[i].setPreferredSize(new Dimension(100, 150));
            jPanel.add(kartlar[i]);
            // Oyun başlatıldı kısmına aşağıdaki kodu ekleyin
            int width = Toolkit.getDefaultToolkit().getScreenSize().width;
            int height = Toolkit.getDefaultToolkit().getScreenSize().height;
            jPanel.setBounds(width / 2 - 200, height / 2 - 200, 400, 400); // Kartları yerleştireceğiniz alana uygun boyutlar verin
            contentPanel.add(jPanel); // jPanel'i contentPanel'e ekleyin
            contentPanel.setComponentZOrder(jPanel, 1); // jPanel'i içerik panelinde birinci sıraya koy
            contentPanel.repaint(); // Ekranı yeniden çiz
            contentPanel.revalidate();
        }
    }

    public void kartSec() {
        if (kullanıcı.getKartListesi().isEmpty() || bilgisayar.getKartListesi().isEmpty()) {
            System.out.println("Oyun bitti!");
            return;
        }
        Sporcu kullanıcıKart = kullanıcı.kartSec();
        Sporcu bilgisayarKart = bilgisayar.kartSec();

        System.out.println("Kullanıcı kartı: " + kullanıcıKart);
        System.out.println("Bilgisayar kartı: " + bilgisayarKart);

        int kullanıcıPuan = 0;
        int bilgisayarPuan = 0;

        if (kullanıcıKart instanceof Futbolcu && bilgisayarKart instanceof Futbolcu) {
            Futbolcu kullanıcıFutbolcu = (Futbolcu) kullanıcıKart;
            Futbolcu bilgisayarFutbolcu = (Futbolcu) bilgisayarKart;
            kullanıcıPuan = kullanıcıFutbolcu.getPenaltı() + kullanıcıFutbolcu.getSerbestAtis() + kullanıcıFutbolcu.getKaleciKarsiKarsiya();
            bilgisayarPuan = bilgisayarFutbolcu.getPenaltı() + bilgisayarFutbolcu.getSerbestAtis() + bilgisayarFutbolcu.getKaleciKarsiKarsiya();
        } else if (kullanıcıKart instanceof Basketbolcu && bilgisayarKart instanceof Basketbolcu) {
            Basketbolcu kullanıcıBasketbolcu = (Basketbolcu) kullanıcıKart;
            Basketbolcu bilgisayarBasketbolcu = (Basketbolcu) bilgisayarKart;
            kullanıcıPuan = kullanıcıBasketbolcu.getIkilik() + kullanıcıBasketbolcu.getSerbestAtis() + kullanıcıBasketbolcu.getUcluk();
            bilgisayarPuan = bilgisayarBasketbolcu.getIkilik() + bilgisayarBasketbolcu.getSerbestAtis() + bilgisayarBasketbolcu.getUcluk();
        }

        // Skor güncellemeleri
        if (kullanıcıPuan > bilgisayarPuan) {
            kullanıcı.setSkor(kullanıcı.getSkor() + 10);
            System.out.println("Turu kazanan: Kullanıcı!");
        } else if (bilgisayarPuan > kullanıcıPuan) {
            bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            System.out.println("Turu kazanan: Bilgisayar!");
        } else {
            System.out.println("Bu tur berabere!");
        }

        lblKullaniciSkor.setText("Kullanıcı Skor: " + kullanıcı.getSkor());
        lblBilgisayarSkor.setText("Bilgisayar Skor: " + bilgisayar.getSkor());

    }



    //Karmaşık yapı -> atiye sor!
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test().setVisible(true);
            }

        });
    }
}
