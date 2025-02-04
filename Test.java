import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Test extends javax.swing.JFrame {
    private JButton btnBasla;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
    private JLabel lblsecilenOzellik;
    private JLabel contentPanel;
    private JPanel middlePanel;
    private JLabel playerSelectedCard;
    private JLabel computerSelectedCard;
    private List<JButton> computerButtons;
    private Kullanıcı kullanıcı;
    private Bilgisayar bilgisayar;
    private boolean OyuncuSırasıMı = true;

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



    public Test() {
        kullanıcı = new Kullanıcı("1", "Kullancı", 0);
        bilgisayar = new Bilgisayar("1", "Bilgisayar", 0);
        oyunBaslat();

    }

    //kartları oluşturma ve dağıtma işlemi;
    public void kartlarıOluştur() {
        List<Sporcu> futbolcular = new ArrayList<>();
        futbolcular.add(new Futbolcu("Ronaldo", "Manchester United", 95, 90, 92));
        futbolcular.add(new Futbolcu("Messi", "PSG", 90, 95, 94));
        futbolcular.add(new Futbolcu("Neymar", "PSG", 88, 89, 90));
        futbolcular.add(new Futbolcu("Mbappe", "PSG", 87, 85, 93));
        futbolcular.add(new Futbolcu("Lewandowski", "Dortmund", 95, 100, 87));
        futbolcular.add(new Futbolcu("De Bruyne", "Man City", 82, 90, 85));
        futbolcular.add(new Futbolcu("Salah", "Liverpool", 80, 95, 90));
        futbolcular.add(new Futbolcu("Dijk", "Liverpool", 87, 87, 87));

        List<Sporcu> basketbolcular = new ArrayList<>();
        basketbolcular.add(new Basketbolcu(90, 85, 88, "LeBron James", "Lakers"));
        basketbolcular.add(new Basketbolcu(88, 90, 85, "Curry", "Warriors"));
        basketbolcular.add(new Basketbolcu(87, 86, 89, "Durant", "Nets"));
        basketbolcular.add(new Basketbolcu(85, 87, 86, "Giannis", "Bucks"));
        basketbolcular.add(new Basketbolcu(80, 84, 98, "James Harden", "76ers"));
        basketbolcular.add(new Basketbolcu(78, 90, 95, "Kobe Bryant", "Lakers"));
        basketbolcular.add(new Basketbolcu(97, 88, 79, "Michael Jordan", "Bulls"));
        basketbolcular.add(new Basketbolcu(95, 82, 80, "Shaquille O'Neal", "Lakers"));


        Collections.shuffle(futbolcular);
        Collections.shuffle(basketbolcular);
        //kartları dağıtma
        for (int i = 0; i < 4; i++) { //-> kullanıcın ve bilgisayarın kartları 4 futbolcu 4 basketçi
            kullanıcı.getKartListesi().add(futbolcular.get(i));
            kullanıcı.getKartListesi().add(basketbolcular.get(i));
            bilgisayar.getKartListesi().add(futbolcular.get(i + 4));
            bilgisayar.getKartListesi().add(basketbolcular.get(i + 4));
        }


    }

    public void oyunBaslat() {
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
        btnBasla.setBackground(new Color(200, 200, 200)); // Hafif gri tonu

        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");
        lblsecilenOzellik = new JLabel("Seçilen Özellik: ?");

        controlPanel.add(btnBasla);
        controlPanel.add(lblKullaniciSkor);
        controlPanel.add(lblBilgisayarSkor);
        controlPanel.add(lblsecilenOzellik);

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
        controlPanel.add(lblsecilenOzellik);
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        JPanel gamePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel computerPanel = KartPaneliOluştur("Bilgisayar Kartları", bilgisayar.getKartListesi(), true);
        gamePanel.add(computerPanel);

        middlePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Seçilen Kartlar"));

        playerSelectedCard = new JLabel();
        computerSelectedCard = new JLabel();
        playerSelectedCard.setPreferredSize(new Dimension(100, 150));
        computerSelectedCard.setPreferredSize(new Dimension(100, 150));

        middlePanel.add(playerSelectedCard);
        middlePanel.add(computerSelectedCard);
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

    private JButton KartButonuOluştur(Sporcu card, boolean isComputer) {
        JButton button = new JButton();
        Image resizedImage = resizeImage(card.getImagePath());

        if (resizedImage != null) {
            button.setIcon(new ImageIcon(resizedImage));
        } else {
            StringBuilder cardText = new StringBuilder(card.getSporcuIsim() + " - " + card.getSporcuTakim());

            if (card instanceof Futbolcu) {
                Futbolcu futbolcu = (Futbolcu) card;
                cardText.append("<br><br>1: ").append(futbolcu.getPenaltı())
                        .append(",<br>2: ").append(futbolcu.getSerbestAtis())
                        .append(",<br>3: ").append(futbolcu.getKaleciKarsiKarsiya());
            } else if (card instanceof Basketbolcu) {
                Basketbolcu basketbolcu = (Basketbolcu) card;
                cardText.append("<br><br>1: ").append(basketbolcu.getIkilik())
                        .append(",<br>2: ").append(basketbolcu.getUcluk())
                        .append(",<br>3: ").append(basketbolcu.getSerbestAtis());
            }

            button.setText("<html>" + cardText.toString() + "</html>");
        }

        button.setPreferredSize(new Dimension(100, 150));

        if (!isComputer) {
            button.addActionListener(e -> OyuncuKartSeç(button, card));
        }

        return button;
    }

    private void OyuncuKartSeç(JButton button, Sporcu card) {
        if (!OyuncuSırasıMı) return;

        playerSelectedCard.setIcon(button.getIcon());
        button.setEnabled(false);

        // Remove the card from the player's list
        kullanıcı.getKartListesi().remove(card);

        computerTurn(card);
    }

    private void computerTurn(Sporcu playerCard) {
        // Uygun kartları elle filtreleyerek bir liste oluşturuyoruz
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
            bilgisayar.getKartListesi().remove(computerCard); // Seçilen kartı listeden çıkar

            // Bilgisayarın seçilen karta karşılık gelen butonunu bul
            JButton selectedButton = null;
            for (JButton b : computerButtons) {
                if (b.isEnabled() && b.getText().contains(computerCard.getSporcuIsim())) {
                    selectedButton = b;
                    break; // İlk eşleşmeyi bulunca döngüden çık
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

    // Yeni değişken ekliyoruz.
//    private boolean futbolcuSırasıMı = true;
//
//    private void OyuncuKartSeç(JButton button, Sporcu card) {
//        if (!OyuncuSırasıMı) return;
//
//        // Seçilen kartın türünü kontrol et
//        boolean kartFutbolcuMu = card instanceof Futbolcu;
//        if (kartFutbolcuMu != futbolcuSırasıMı) {
//            JOptionPane.showMessageDialog(this, "Sıradaki kart türü uygun değil!", "Hata", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        playerSelectedCard.setIcon(button.getIcon());
//        button.setEnabled(false);
//        kullanıcı.getKartListesi().remove(card);
//
//        // Sıradaki kartın türünü değiştir
//        futbolcuSırasıMı = !futbolcuSırasıMı;
//
//        computerTurn(card);
//    }
//
//    private void computerTurn(Sporcu playerCard) {
//        // Bilgisayarın da uygun sıraya göre seçim yapmasını sağlıyoruz
//        List<Sporcu> uygunKartlar = bilgisayar.getKartListesi().stream()
//                .filter(kart -> (kart instanceof Futbolcu) == futbolcuSırasıMı)
//                .collect(Collectors.toList());
//
//        if (!uygunKartlar.isEmpty()) {
//            Sporcu bilgisayarKartı = uygunKartlar.get(new Random().nextInt(uygunKartlar.size()));
//            bilgisayar.getKartListesi().remove(bilgisayarKartı);
//
//            JButton secilenButon = null;
//            for (JButton b : computerButtons) {
//                if (b.isEnabled() && b.getText().contains(bilgisayarKartı.getSporcuIsim())) {
//                    secilenButon = b;
//                    break;
//                }
//            }
//
//            if (secilenButon != null) {
//                computerSelectedCard.setIcon(secilenButon.getIcon());
//                secilenButon.setEnabled(false);
//                kartlarıKarşılaştır(playerCard, bilgisayarKartı);
//            }
//        }
//
//        OyuncuSırasıMı = true;
//        checkGameOver();
//    }


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
                    System.out.println("Oyuncunun Kartı : " +OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (Penaltı) : " +seçilenÖzellik);


                }
                case 1 -> {
                    oyuncuPuan = OyuncuFutbolKart.getSerbestAtis();
                    bilgisayarPuan = BilgisayarFutbolcuKart.getSerbestAtis();
                    seçilenÖzellik = "2";
                    System.out.println("Oyuncunun Kartı : " +OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (Serbest Atış) : " +seçilenÖzellik);


                }
                case 2 -> { // Burada yanlışlıkla 3 yazılmış, 2 olmalı
                    oyuncuPuan = OyuncuFutbolKart.getKaleciKarsiKarsiya();
                    bilgisayarPuan = BilgisayarFutbolcuKart.getKaleciKarsiKarsiya();
                    seçilenÖzellik = "3";
                    System.out.println("Oyuncunun Kartı : " +OyuncuFutbolKart);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarFutbolcuKart);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (KaleciKarşıKarşıya) : " +seçilenÖzellik);

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
                    System.out.println("Oyuncunun Kartı : " +OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (İkilik) : " +seçilenÖzellik);

                }
                case 1 -> {
                    oyuncuPuan = OyuncuBasketbolcu.getUcluk();
                    bilgisayarPuan = BilgisayarBasketbolcu.getUcluk();
                    seçilenÖzellik = "2";
                    System.out.println("Oyuncunun Kartı : " +OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (Üçlük) : " +seçilenÖzellik);

                }
                case 2 -> {
                    oyuncuPuan = OyuncuBasketbolcu.getSerbestAtis();
                    bilgisayarPuan = BilgisayarBasketbolcu.getSerbestAtis();
                    seçilenÖzellik = "3";
                    System.out.println("Oyuncunun Kartı : " +OyuncuBasketbolcu);
                    System.out.println("Bilgisayarın  Kartı : " +BilgisayarBasketbolcu);
                    System.out.println("---------------------------------------------");
                    System.out.println("Seçilen Özellik (Serbest Atış) : " +seçilenÖzellik);

                }
            }
        }

        // Skor hesaplama
        if (oyuncuPuan > bilgisayarPuan) {
            kullanıcı.setSkor(kullanıcı.getSkor() + 10);
            System.out.println("Turu kazanan : Kullanıcı");
            System.out.println("---------------------------------------------");
        } else if (oyuncuPuan < bilgisayarPuan) {
            bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            System.out.println("Turu kazanan : Bilgisayar");
            System.out.println("---------------------------------------------");
        }
        lblsecilenOzellik.setText("Seçilen Özellik : " + seçilenÖzellik);
        skorGüncelle();
    }


    private Image resizeImage(String imagePath) {
        try {
            URL imageUrl = getClass().getResource("/" + imagePath);
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
        boolean KartlarKullanildiMi= bilgisayar.getKartListesi().isEmpty() && kullanıcı.getKartListesi().isEmpty();
        if (KartlarKullanildiMi) {
            String kazanan;
            if (kullanıcı.getSkor() > bilgisayar.getSkor()) {
                kazanan = "Kullanıcı";
            } else if (kullanıcı.getSkor() < bilgisayar.getSkor()) {
                kazanan = "Bilgisayar";
            } else {
                kazanan = "Berabere";
            }

            JOptionPane.showMessageDialog(this,
                    "Oyun bitti!\nKazanan: " + kazanan + "\nKullanıcı Skor: " + kullanıcı.getSkor() +
                            "\nBilgisayar Skor: " + bilgisayar.getSkor(),
                    "Oyun Sonucu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test game = new Test();
            game.setVisible(true);
        });

    }
    }



