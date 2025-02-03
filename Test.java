import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Test extends JFrame {
    private JButton btnBasla;
    private JLabel lblKullaniciSkor;
    private JLabel lblBilgisayarSkor;
    private JLabel lblSelectedFeature;
    private JPanel contentPanel;
    private JPanel middlePanel;
    private JLabel playerSelectedCard;
    private JLabel computerSelectedCard;
    private Kullanıcı kullanici;
    private Bilgisayar bilgisayar;
    private List<JButton> computerButtons;
    private boolean isPlayerTurn = true;

    public Test() {
        kullanici = new Kullanıcı("1", "Kullanıcı", 0);
        bilgisayar = new Bilgisayar("2", "Bilgisayar", 0);
        initializeUI();
    }

    private void initializeCards() {
        List<Sporcu> footballers = Arrays.asList(
            new Futbolcu("Ronaldo", "Manchester United", 95, 90, 92),
            new Futbolcu("Messi", "PSG", 90, 95, 94),
            new Futbolcu("Neymar", "PSG", 88, 89, 90),
            new Futbolcu("Mbappe", "PSG", 87, 85, 93),
            new Futbolcu("Lewandowski", "Dortmund", 95, 100, 87),
            new Futbolcu("De Bruyne", "Man City", 82, 90, 85),
            new Futbolcu("Salah", "Liverpool", 80, 95, 90),
            new Futbolcu("Dijk", "Liverpool", 87, 87, 87)
        );

        List<Sporcu> basketballPlayers = Arrays.asList(
            new Basketbolcu(90, 85, 88, "LeBron James", "Lakers"),
            new Basketbolcu(88, 90, 85, "Curry", "Warriors"),
            new Basketbolcu(87, 86, 89, "Durant", "Nets"),
            new Basketbolcu(85, 87, 86, "Giannis", "Bucks"),
            new Basketbolcu(80, 84, 98, "James Harden", "76ers"),
            new Basketbolcu(78, 90, 95, "Kobe Bryant", "Lakers"),
            new Basketbolcu(97, 88, 79, "Michael Jordan", "Bulls"),
            new Basketbolcu(95, 82, 80, "Shaquille O'Neal", "Lakers")
        );

        Collections.shuffle(footballers);
        Collections.shuffle(basketballPlayers);

        for (int i = 0; i < 4; i++) {
            kullanici.getKartListesi().add(footballers.get(i));
            kullanici.getKartListesi().add(basketballPlayers.get(i));
            bilgisayar.getKartListesi().add(footballers.get(i + 4));
            bilgisayar.getKartListesi().add(basketballPlayers.get(i + 4));
        }
    }

    private void initializeUI() {
        setTitle("Kart Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);

        JPanel controlPanel = new JPanel();
        btnBasla = new JButton("Oyunu Başlat");
        lblKullaniciSkor = new JLabel("Kullanıcı Skor: 0");
        lblBilgisayarSkor = new JLabel("Bilgisayar Skor: 0");
        lblSelectedFeature = new JLabel("Seçilen Özellik: ?");

        controlPanel.add(btnBasla);
        controlPanel.add(lblKullaniciSkor);
        controlPanel.add(lblBilgisayarSkor);
        controlPanel.add(lblSelectedFeature);

        contentPanel.add(controlPanel, BorderLayout.NORTH);

        btnBasla.addActionListener(e -> startGame());
    }

    private void startGame() {
        kullanici.setSkor(0);
        bilgisayar.setSkor(0);
        isPlayerTurn = true;
        updateScores();
        initializeCards();
        setupGameUI();
    }

    private void updateScores() {
        lblKullaniciSkor.setText("Kullanıcı Skor: " + kullanici.getSkor());
        lblBilgisayarSkor.setText("Bilgisayar Skor: " + bilgisayar.getSkor());
    }

    private void setupGameUI() {
        contentPanel.removeAll();

        JPanel controlPanel = new JPanel();
        controlPanel.add(btnBasla);
        controlPanel.add(lblKullaniciSkor);
        controlPanel.add(lblBilgisayarSkor);
        controlPanel.add(lblSelectedFeature);
        contentPanel.add(controlPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        
        JPanel computerPanel = createCardPanel("Bilgisayar Kartları", bilgisayar.getKartListesi(), true);
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

        JPanel playerPanel = createCardPanel("Kullanıcı Kartları", kullanici.getKartListesi(), false);
        gamePanel.add(playerPanel);

        contentPanel.add(gamePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createCardPanel(String title, List<Sporcu> cards, boolean isComputer) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new FlowLayout());

        if (isComputer) {
            computerButtons = new ArrayList<>();
        }

        for (Sporcu card : cards) {
            JButton cardButton = createCardButton(card, isComputer);
            if (isComputer) {
                computerButtons.add(cardButton);
            }
            panel.add(cardButton);
        }

        return panel;
    }

    private JButton createCardButton(Sporcu card, boolean isComputer) {
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
            button.addActionListener(e -> handlePlayerCardClick(button, card));
        }
        
        return button;
    }

    private void handlePlayerCardClick(JButton button, Sporcu card) {
        if (!isPlayerTurn) return;

        playerSelectedCard.setIcon(button.getIcon());
        button.setEnabled(false);

        // Remove the card from the player's list
        kullanici.getKartListesi().remove(card);

        computerTurn(card);
    }

    private void computerTurn(Sporcu playerCard) {
        List<Sporcu> availableCards = bilgisayar.getKartListesi().stream()
            .filter(card -> (playerCard instanceof Futbolcu && card instanceof Futbolcu) ||
                            (playerCard instanceof Basketbolcu && card instanceof Basketbolcu))
            .collect(Collectors.toList());

        if (!availableCards.isEmpty()) {
            Sporcu computerCard = availableCards.get(new Random().nextInt(availableCards.size()));
            bilgisayar.getKartListesi().remove(computerCard);

            JButton selectedButton = computerButtons.stream()
                .filter(b -> b.isEnabled() && b.getText().contains(computerCard.getSporcuIsim()))
                .findFirst()
                .orElse(null);

            if (selectedButton != null) {
                computerSelectedCard.setIcon(selectedButton.getIcon());
                selectedButton.setEnabled(false);
                compareCards(playerCard, computerCard);
            }
        }

        isPlayerTurn = true;
        checkGameOver();
    }

    private void compareCards(Sporcu playerCard, Sporcu computerCard) {
        String selectedFeature = "";

        if (playerCard instanceof Futbolcu && computerCard instanceof Futbolcu) {
            Futbolcu pF = (Futbolcu) playerCard;
            Futbolcu cF = (Futbolcu) computerCard;

            // Randomly select a feature to compare
            int featureIndex = new Random().nextInt(3);
            int playerValue = 0, computerValue = 0;

            switch (featureIndex) {
                case 0:
                    playerValue = pF.getPenaltı();
                    computerValue = cF.getPenaltı();
                    selectedFeature = "1";
                    break;
                case 1:
                    playerValue = pF.getSerbestAtis();
                    computerValue = cF.getSerbestAtis();
                    selectedFeature = "2";
                    break;
                case 2:
                    playerValue = pF.getKaleciKarsiKarsiya();
                    computerValue = cF.getKaleciKarsiKarsiya();
                    selectedFeature = "3";
                    break;
            }

            if (playerValue > computerValue) {
                kullanici.setSkor(kullanici.getSkor() + 10);
            } else if (computerValue > playerValue) {
                bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            }
        } else if (playerCard instanceof Basketbolcu && computerCard instanceof Basketbolcu) {
            Basketbolcu pB = (Basketbolcu) playerCard;
            Basketbolcu cB = (Basketbolcu) computerCard;

            // Randomly select a feature to compare
            int featureIndex = new Random().nextInt(3);
            int playerValue = 0, computerValue = 0;

            switch (featureIndex) {
                case 0:
                    playerValue = pB.getIkilik();
                    computerValue = cB.getIkilik();
                    selectedFeature = "1";
                    break;
                case 1:
                    playerValue = pB.getUcluk();
                    computerValue = cB.getUcluk();
                    selectedFeature = "2";
                    break;
                case 2:
                    playerValue = pB.getSerbestAtis();
                    computerValue = cB.getSerbestAtis();
                    selectedFeature = "3";
                    break;
            }

            if (playerValue > computerValue) {
                kullanici.setSkor(kullanici.getSkor() + 10);
            } else if (computerValue > playerValue) {
                bilgisayar.setSkor(bilgisayar.getSkor() + 10);
            }
        }

        lblSelectedFeature.setText("Seçilen Özellik: " + selectedFeature);
        updateScores();
    }

    private void checkGameOver() {
        System.out.println("Kullanıcı kart listesi boyutu: " + kullanici.getKartListesi().size());
        System.out.println("Bilgisayar kart listesi boyutu: " + bilgisayar.getKartListesi().size());

        boolean allCardsPlayed = bilgisayar.getKartListesi().isEmpty() && kullanici.getKartListesi().isEmpty();
        System.out.println("Checking game over: " + allCardsPlayed); // Debugging line
        if (allCardsPlayed) {
            String winner;
            if (kullanici.getSkor() > bilgisayar.getSkor()) {
                winner = "Kullanıcı";
            } else if (kullanici.getSkor() < bilgisayar.getSkor()) {
                winner = "Bilgisayar";
            } else {
                winner = "Berabere";
            }
            
            JOptionPane.showMessageDialog(this, 
                "Oyun bitti!\nSonuç: " + winner + "\nKullanıcı Skor: " + kullanici.getSkor() + 
                "\nBilgisayar Skor: " + bilgisayar.getSkor(),
                "Oyun Sonucu", JOptionPane.INFORMATION_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test game = new Test();
            game.setVisible(true);
        });
    }
}