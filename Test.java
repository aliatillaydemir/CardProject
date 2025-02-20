import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Test extends javax.swing.JFrame {
    private JButton btnBasla;
    private JButton btnBitir;
    private JButton btnSkorlariGoster;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
//    private JLabel lblsecilenOzellik;
    private JLabel contentPanel;
    private JPanel middlePanel;
    private JLabel playerSelectedCard;
    private JLabel computerSelectedCard;
    private List<JButton> computerButtons;
    private Kullanıcı kullanıcı;
    private Bilgisayar bilgisayar;
    private boolean OyuncuSırasıMı = true;
    private JLabel lblAciklama;
    private JButton BtnAnamenu;
    private StringBuilder cardText;



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
            "images/Shaquille.jpg",
            "images/Michael Jordan.jpg",
            "images/Kobe Bryant.jpg",
            "images/James Harden.jpg",
            "images/Antetokounmpo.jpg",
            "images/Kevin Durant.jpg"
    };


    public Test() {
        kullanıcı = new Kullanıcı("1", "Kullancı", 0);
        bilgisayar = new Bilgisayar("1", "Bilgisayar", 0);
        oyunBaslat();

    }

    //kartları oluşturma ve dağıtma işlemi;
    public void kartlarıOluştur() {
        List<Sporcu> futbolcular = new ArrayList<>();
        futbolcular.add(new Futbolcu("Ronaldo", "Manchester United", 95, 90, 92, FutbolcuResimleri[0]));
        futbolcular.add(new Futbolcu("Lionel Messi", "PSG", 90, 95, 94, FutbolcuResimleri[5]));
        futbolcular.add(new Futbolcu("Neymar", "PSG", 88, 89, 90, FutbolcuResimleri[2]));
        futbolcular.add(new Futbolcu("Kylian Mbappe", "PSG", 87, 85, 93, FutbolcuResimleri[3]));
        futbolcular.add(new Futbolcu("Robert Lewandowski", "Dortmund", 95, 100, 87, FutbolcuResimleri[7]));
        futbolcular.add(new Futbolcu("Kevin De Bruyne", "Man City", 82, 90, 85, FutbolcuResimleri[1]));
        futbolcular.add(new Futbolcu("Mohamed Salah", "Liverpool", 80, 95, 90, FutbolcuResimleri[6]));
        futbolcular.add(new Futbolcu("Virgil van Dijk", "Liverpool", 87, 87, 87, FutbolcuResimleri[4]));

        List<Sporcu> basketbolcular = new ArrayList<>();
        basketbolcular.add(new Basketbolcu(90, 85, 88, "LeBron James", "Lakers", BasketbolcuResimleri[0]));
        basketbolcular.add(new Basketbolcu(88, 90, 85, "Stephen Curry", "Warriors", BasketbolcuResimleri[1]));
        basketbolcular.add(new Basketbolcu(87, 86, 89, "Kevin Durant", "Nets", BasketbolcuResimleri[7]));
        basketbolcular.add(new Basketbolcu(85, 87, 86, "Antetokounmpo", "Bucks", BasketbolcuResimleri[6]));
        basketbolcular.add(new Basketbolcu(80, 84, 98, "James Harden", "76ers", BasketbolcuResimleri[5]));
        basketbolcular.add(new Basketbolcu(78, 90, 95, "Kobe Bryant", "Lakers", BasketbolcuResimleri[4]));
        basketbolcular.add(new Basketbolcu(97, 88, 79, "Michael Jordan", "Bulls", BasketbolcuResimleri[3]));
        basketbolcular.add(new Basketbolcu(95, 82, 80, "Shaquille", "Lakers", BasketbolcuResimleri[2]));


        Collections.shuffle(futbolcular);
        Collections.shuffle(basketbolcular);
        //kartları dağıtma
        for (int i = 0; i < 4; i++) { //-> kullanıcın ve bilgisayarın kartları 4 futbolcu 4 basketçi
            kullanıcı.getKartListesi().add(futbolcular.get(i));
            kullanıcı.getKartListesi().add(basketbolcular.get(i));
            bilgisayar.getKartListesi().add(futbolcular.get(i + 4));
            bilgisayar.getKartListesi().add(basketbolcular.get(i + 4));

        }
        for (Sporcu kart : bilgisayar.getKartListesi()) {
            kart.setImagePath("images/black.jpg");

        }


    }

    //başlangıçta bilgisayar kartları yazılar gizlidir;
    private void BilgisayarKartlariniGizle(List<JButton> computerButtons) {
        for (JButton button : computerButtons) {
            button.setText("");
        }
    }

    public void oyunBaslat() {
        System.out.println("Kartlar Kullanıcıya ve Bilgisayara 8 er adet olmak üzere dağıtıldı!");
        setTitle("Kart Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Orijinal arka plan resmini yükle
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/stat.jpg"));

        // İlk olarak belirlenen boyutta resmi ölçekle
        Image img = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        contentPanel = new JLabel(new ImageIcon(img));

        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Skor panellerini oluştur
        JPanel controlPanel = new JPanel();
        btnBasla = new JButton("Oyunu Başlat");
        btnBasla.setBorderPainted(false);
        btnBasla.setFocusPainted(false);
        btnBasla.setBackground(new Color(200, 200, 200));
        btnBitir = new JButton("Oyunu Bitir");
        BtnAnamenu=new JButton("Ana Menü");
        btnSkorlariGoster = new JButton("Skor Geçmişi");
        btnBitir.setBorderPainted(false);
        btnBitir.setFocusPainted(false);
        btnBitir.setBackground(new Color(200, 200, 200));
        BtnAnamenu.setBorderPainted(false);
        BtnAnamenu.setFocusPainted(false);
        BtnAnamenu.setBackground(new Color(200, 200, 200));
//        BtnAnamenu.setVisible(false);
        btnSkorlariGoster.setBorderPainted(false);
        btnSkorlariGoster.setFocusPainted(false);
        btnSkorlariGoster.setBackground(new Color(200, 200, 200));

        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");
//        lblsecilenOzellik = new JLabel("Seçilen Özellik: ?");

        controlPanel.add(btnBasla);
        controlPanel.add(btnBitir);
        controlPanel.add(BtnAnamenu);
        controlPanel.add(btnSkorlariGoster);
        controlPanel.add(lblKullaniciSkor);
        controlPanel.add(lblBilgisayarSkor);
//        controlPanel.add(lblsecilenOzellik);

//        btnBasla.addActionListener(e -> {
//            BtnAnamenu.setVisible(true);
//        });
        contentPanel.add(controlPanel, BorderLayout.NORTH);


        // Pencere boyutu değiştikçe arka planı güncelleriz;
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBackground(backgroundIcon);
            }
        });


        // lambda function;
        btnBasla.addActionListener(e -> oyunuBaşlat());


        btnBitir.addActionListener(e -> oyunuBitir());
    }

    // Arka planı pencerenin yeni boyutuna göre ölçeklendir
    private void resizeBackground(ImageIcon backgroundIcon) {
        int width = getWidth();
        int height = getHeight();
        Image scaledImage = backgroundIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        contentPanel.setIcon(new ImageIcon(scaledImage));
    }


    private void oyunuBaşlat() {
        btnBasla.setVisible(false); // Başlat butonunu gizle
        kullanıcı.setSkor(0);
        bilgisayar.setSkor(0); // Skorları sıfırla
        skorGüncelle();
        kartlarıOluştur();
        oyunKur();
//        BilgisayarKartlariniGizle(computerButtons);

    }

    private void oyunuBitir() {
        System.out.println("Oyun Kapatılıyor...");
        JOptionPane optionPane = new JOptionPane("UYARI:\nOyun Bitti!\nOyun Kapatılıyor...",
        JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("UYARI");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(600, 400);
        dialog.setVisible(true);
        Timer timer = new Timer(1000,e->System.exit(0));
        timer.setRepeats(false);
        timer.start();
    }

    private void skorGüncelle() {
        lblKullaniciSkor.setText("Kullanıcı Skor: " + kullanıcı.getSkor());
        lblBilgisayarSkor.setText("Bilgisayar Skor: " + bilgisayar.getSkor());
    }


    private void oyunKur() {
        contentPanel.removeAll();
        JPanel controlPanel = new JPanel();
        controlPanel.add(btnBasla);
        controlPanel.add(lblKullaniciSkor);
        controlPanel.add(lblBilgisayarSkor);
        BtnAnamenu.setBounds(10, -5, 150, 30);
        contentPanel.add(BtnAnamenu); //anamenüüye dön butonu ekledim!
//        controlPanel.add(lblsecilenOzellik);
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        JPanel gamePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel computerPanel = KartPaneliOluştur("Bilgisayar Kartları", bilgisayar.getKartListesi(), true);
        gamePanel.add(computerPanel);


        middlePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Seçilen Kartlar"));
        lblAciklama = new JLabel("Seçilen Özellik :");
        lblAciklama.setFont(new Font("Arial", Font.PLAIN, 20));
        lblAciklama.setForeground(Color.RED);
        middlePanel.add(lblAciklama);

        playerSelectedCard = new JLabel();
        computerSelectedCard = new JLabel();
        playerSelectedCard.setOpaque(true);
        playerSelectedCard.setBackground(Color.LIGHT_GRAY);
        playerSelectedCard.setPreferredSize(new Dimension(100, 150));
        computerSelectedCard.setOpaque(true);
        computerSelectedCard.setBackground(Color.LIGHT_GRAY);


        //-> kullanıcının seçtiği kartları middlePanel e ekledik
        computerSelectedCard.setPreferredSize(new Dimension(100, 150));//-> bilgisayarın seçtiği kartları middlePanel e ekledik

        middlePanel.add(playerSelectedCard);
        middlePanel.add(computerSelectedCard);


        //------------------middle panel -----------------------------------------
        gamePanel.add(middlePanel);

        JPanel playerPanel = KartPaneliOluştur("Kullanıcı Kartları", kullanıcı.getKartListesi(), false);
        gamePanel.add(playerPanel);

        contentPanel.add(gamePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private JPanel KartPaneliOluştur(String title, List<Sporcu> cards, boolean isComputer) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new FlowLayout());

        if (isComputer) {
            computerButtons = new ArrayList<>();
        }

        for (Sporcu card : cards) {
            JButton cardButton = KartButonuOluştur(card, isComputer);
            if (isComputer) {
                computerButtons.add(cardButton);
            }
            panel.add(cardButton);
        }

        return panel;
    }



    //kartlar açıldığında--->>>> pc nin kartları güncellenir ve kartların değerleri açılıp orta panale atılınca gösterilir;
    private void BilgisayarKartGuncelle(Sporcu secilenKart, JButton computerButton) {
        String resimYolu = "";
        if (secilenKart instanceof Futbolcu) {
            for (String resim : FutbolcuResimleri) {
                if (resim.equals("images/" + secilenKart.getSporcuIsim() + ".jpg")) {
                    resimYolu = resim;
                    break;
                }
            }
        } else if (secilenKart instanceof Basketbolcu) {
            for (String resim : BasketbolcuResimleri) {
                if (resim.equals("images/" + secilenKart.getSporcuIsim() + ".jpg")) {
                    resimYolu = resim;
                    break;
                }
            }
        }
        if (resimYolu != null) {
            secilenKart.setImagePath(resimYolu);//->set ettik
            // Resmi boyutlandırma işlemi
            Image resizedImage = resizeImage(resimYolu);
            if (resizedImage != null) {
                // Yeniden boyutlandırılmış resmi ImageIcon olarak set et
                computerButton.setIcon(new ImageIcon(resizedImage));
                StringBuilder cardText = new StringBuilder("<html><center>");
                cardText.append(secilenKart.getSporcuIsim()).append("<br>");
                if (secilenKart instanceof Futbolcu) {
                    Futbolcu futbolcu = (Futbolcu) secilenKart;
                    cardText.append(futbolcu.getPenaltı()).append("<br>");
                    cardText.append(futbolcu.getSerbestAtis()).append("<br>");
                    cardText.append(futbolcu.getKaleciKarsiKarsiya()).append("<br>");
                } else if (secilenKart instanceof Basketbolcu) {
                    Basketbolcu basketbolcu = (Basketbolcu) secilenKart;
                    cardText.append(basketbolcu.getIkilik()).append("<br>");
                    cardText.append(basketbolcu.getUcluk()).append("<br>");
                    cardText.append(basketbolcu.getSerbestAtis()).append("<br>");
                }
                cardText.append("</html>");
                computerButton.setText(cardText.toString());
                computerButton.repaint(); // Ekranı yeniden çiz
            } else {
                System.err.println("Resim boyutlandırma başarısız: " + resimYolu);
            }
        } else {
            System.out.println("Resim bulunamadı... " + secilenKart.getSporcuIsim());
        }

        // Kartı seçtikten sonra butonu devre dışı bırakıyoruz
        computerButton.setEnabled(false);
    }


    private JButton KartButonuOluştur(Sporcu card, boolean isComputer) {
        JButton button = new JButton(); // Butonu oluşturduk
        Image resizedImage = resizeImage(card.getImagePath());


        StringBuilder cardText = new StringBuilder("<html><center>");
        cardText.append(card.getSporcuIsim()).append("<br>");

        //eksik olan kısım !iscomputer diiynce algoritma çalışması lazım ama çalışmıyor!
        //İF !İSCOMPUTER

        if (card instanceof Futbolcu) {
            Futbolcu futbolcu = (Futbolcu) card;
            cardText.append(futbolcu.getPenaltı()).append("<br>");
            cardText.append(futbolcu.getSerbestAtis()).append("<br>");
            cardText.append(futbolcu.getKaleciKarsiKarsiya());
        } else if (card instanceof Basketbolcu) {
            Basketbolcu basketbolcu = (Basketbolcu) card;
            cardText.append(basketbolcu.getIkilik()).append("<br>");
            cardText.append(basketbolcu.getUcluk()).append("<br>");
            cardText.append(basketbolcu.getSerbestAtis());
        }

        cardText.append("</center></html>");
        button.setText(cardText.toString());


        // Resmi arka plan olarak ayarlıyoruz
        if (resizedImage != null) {
            button.setIcon(new ImageIcon(resizedImage));
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
        }

        button.setPreferredSize(new Dimension(100, 150));
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setContentAreaFilled(false); // İçeriğin alanı doldurulmasın (resmi göstermek için)
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        if (!isComputer) {
            button.addActionListener(e -> OyuncuKartSeç(button, card));
        }

        return button;
    }


    private void OyuncuKartSeç(JButton button, Sporcu card) {
        if (!OyuncuSırasıMı) return;

        playerSelectedCard.setIcon(button.getIcon());



        kullanıcı.getKartListesi().remove(card);
        button.setEnabled(false);
        computerTurn(card);
    }


    private void computerTurn(Sporcu playerCard) {
        List<Sporcu> availableCards = new ArrayList<>();

        for (Sporcu card : bilgisayar.getKartListesi()) {
            if ((playerCard instanceof Futbolcu && card instanceof Futbolcu) ||
                    (playerCard instanceof Basketbolcu && card instanceof Basketbolcu)) {
                availableCards.add(card);

            }
        }

        if (!availableCards.isEmpty()) {
            // Rastgele bir kart seç
            Sporcu computerCard = availableCards.get(new Random().nextInt(availableCards.size()));


            bilgisayar.getKartListesi().remove(computerCard);


            // Bilgisayarın seçilen karta karşılık gelen butonunu bul
            JButton selectedButton = null;
            for (JButton b : computerButtons) {
                if (b.isEnabled() && b.getText().contains(computerCard.getSporcuIsim())) {
                    selectedButton = b;

                    BilgisayarKartGuncelle(computerCard, b);
                    break;
                }
            }

            if (selectedButton != null) {
                computerSelectedCard.setIcon(selectedButton.getIcon());
                selectedButton.setEnabled(false);
                kartlarıKarşılaştır(playerCard, computerCard);
            }
        }

        OyuncuSırasıMı = true;
        checkGameOver();
    }


    //(buradaki oyuncu->kullanıcı oluyor);
    private void kartlarıKarşılaştır(Sporcu oyuncuKart, Sporcu bilgisayarKart) {
        String seçilenÖzellik = "";
        int oyuncuPuan = 0, bilgisayarPuan = 0;

        if (oyuncuKart instanceof Futbolcu && bilgisayarKart instanceof Futbolcu) {
            Futbolcu OyuncuFutbolKart = (Futbolcu) oyuncuKart;
            Futbolcu BilgisayarFutbolcuKart = (Futbolcu) bilgisayarKart;
            int secilenOzellikIndx = new Random().nextInt(3); // 3 özellikten biri seçilir.

            // Seçilen özelliklere göre karşılaştırma yapıyoruz
            switch (secilenOzellikIndx) {
                case 0 -> {
                    oyuncuPuan = OyuncuFutbolKart.getPenaltı();
                    bilgisayarPuan = BilgisayarFutbolcuKart.getPenaltı();
                    seçilenÖzellik = "1";
                    System.out.println("Oyuncunun Kartı : " + OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [Penaltı] : " + seçilenÖzellik);


                }
                case 1 -> {
                    oyuncuPuan = OyuncuFutbolKart.getSerbestAtis();
                    bilgisayarPuan = BilgisayarFutbolcuKart.getSerbestAtis();
                    seçilenÖzellik = "2";
                    System.out.println("Oyuncunun Kartı : " + OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [Serbest Atış] : " + seçilenÖzellik);


                }
                case 2 -> {
                    oyuncuPuan = OyuncuFutbolKart.getKaleciKarsiKarsiya();
                    bilgisayarPuan = BilgisayarFutbolcuKart.getKaleciKarsiKarsiya();
                    seçilenÖzellik = "3";
                    System.out.println("Oyuncunun Kartı : " + OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [KaleciKarşıKarşıya] : " + seçilenÖzellik);

                }
            }
        } else if (oyuncuKart instanceof Basketbolcu && bilgisayarKart instanceof Basketbolcu) {
            Basketbolcu OyuncuBasketbolcu = (Basketbolcu) oyuncuKart;
            Basketbolcu BilgisayarBasketbolcu = (Basketbolcu) bilgisayarKart;
            int secilenOzellikIndx = new Random().nextInt(3); // 3 özellikten biri seçilir.

            // Seçilen özelliklere göre karşılaştırma yapıyoruz
            switch (secilenOzellikIndx) {
                case 0 -> {
                    oyuncuPuan = OyuncuBasketbolcu.getIkilik();
                    bilgisayarPuan = BilgisayarBasketbolcu.getIkilik();
                    seçilenÖzellik = "1";
                    System.out.println("Oyuncunun Kartı : " + OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [İkilik] : " + seçilenÖzellik);


                }
                case 1 -> {
                    oyuncuPuan = OyuncuBasketbolcu.getUcluk();
                    bilgisayarPuan = BilgisayarBasketbolcu.getUcluk();
                    seçilenÖzellik = "2";
                    System.out.println("Oyuncunun Kartı : " + OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [Üçlük] : " + seçilenÖzellik);

                }
                case 2 -> {
                    oyuncuPuan = OyuncuBasketbolcu.getSerbestAtis();
                    bilgisayarPuan = BilgisayarBasketbolcu.getSerbestAtis();
                    seçilenÖzellik = "3";
                    System.out.println("Oyuncunun Kartı : " + OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " + BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik [Serbest Atış] : " + seçilenÖzellik);

                }
            }
        }

        String sonucMetni = "<html><b>Seçilen Özellik: " + seçilenÖzellik + "</b><br>" +
                "Kullanıcı Puan: " + oyuncuPuan + "<br>" +
                "Bilgisayar Puan: " + bilgisayarPuan;

        // Kazananı belirleme
        if (oyuncuPuan > bilgisayarPuan) {
            sonucMetni += "<br><font color='green'><b> Turu Kullanıcı Kazandı!</b></font>";
            kullanıcı.setSkor(kullanıcı.getSkor() + 10);
        } else if (oyuncuPuan < bilgisayarPuan) {
            sonucMetni += "<br><font color='red'><b> Turu Bilgisayar Kazandı!</b></font>";
            bilgisayar.setSkor(bilgisayar.getSkor() + 10);
        } else {
            sonucMetni += "<br><b> Tur Berabere!</b>";
        }

        sonucMetni += "</html>";
        lblAciklama.setText(sonucMetni);
        lblAciklama.setHorizontalAlignment(SwingConstants.CENTER);
        skorGüncelle();
    }


    private Image resizeImage(String imagePath) {
        try {
            if (imagePath == null || imagePath.isEmpty()) {
                System.err.println("Image path is empty or null!");
                return null;
            }

            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl == null) {
                System.err.println("Cannot find image: " + imagePath);
                return null;
            }

            BufferedImage originalImage = ImageIO.read(imageUrl);
            if (originalImage == null) {
                System.err.println("Failed to read image: " + imagePath);
                return null;
            }

            return originalImage.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Error loading image " + imagePath + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    private void checkGameOver() {
//        System.out.println("Kullanıcı kart listesi boyutu: " + kullanıcı.getKartListesi().size());
//        System.out.println("Bilgisayar kart listesi boyutu: " + bilgisayar.getKartListesi().size());
        boolean KartlarKullanildiMi = bilgisayar.getKartListesi().isEmpty() && kullanıcı.getKartListesi().isEmpty();
        if (KartlarKullanildiMi) {
            String kazanan;
            int kullaniciSkor=kullanıcı.getSkor();
            int pcSkor=bilgisayar.getSkor();
            if (kullanıcı.getSkor() > bilgisayar.getSkor()) {
                kazanan = "Kullanıcı";
                System.out.println("Kazanan : " + kazanan + "\nKullanıcı Puan: " + kullanıcı.getSkor() + "\nBilgisayar Puan : " + bilgisayar.getSkor());
                ;
            } else if (kullanıcı.getSkor() < bilgisayar.getSkor()) {
                kazanan = "Bilgisayar";
                System.out.println("Kazanan : " + kazanan + "\nKullanıcı Puan: " + kullanıcı.getSkor() + "\nBilgisayar Puan : " + bilgisayar.getSkor());
            } else {
                kazanan = "Berabere";
                System.out.println("Oyun  : " + kazanan);
                System.out.println("Kullanıcı Puan: " + kullanıcı.getSkor() + "\nBilgisayar Puan : " + bilgisayar.getSkor());
            }
VeritabaniBaglanti.insertSkor(kullaniciSkor,pcSkor);
            JOptionPane.showMessageDialog(this,
                    "Oyun bitti!\nKazanan: " + kazanan + "\nKullanıcı Skor: " + kullanıcı.getSkor() +
                            "\nBilgisayar Skor: " + bilgisayar.getSkor(),
                    "Oyun Sonucu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {

        Connection conn= VeritabaniBaglanti.baglan();
        VeritabaniBaglanti.baglantiKapat(conn);
        SwingUtilities.invokeLater(() -> {

            Test game = new Test();
            game.setVisible(true);
        });
    }
}

