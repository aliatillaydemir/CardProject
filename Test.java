import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    // Constructor
    public Test() {
        System.out.println("Game Started!");
        setTitle("Futbolcu ve Basketbolcu Kart Oyunu");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Freeform layout

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        // Load and resize background image
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
            }
        });

        // Add components to contentPanel
        contentPanel.add(btnBasla);
        contentPanel.add(lblKullaniciSkor);
        contentPanel.add(lblBilgisayarSkor);

        // Set contentPanel as the frame's content pane
        setContentPane(contentPanel);

        // Set frame size
        setSize(width, height);
    }

    // Start the game
    public void oyunBaslat() {
        btnBasla.setEnabled(false); // Disable the start button after clicking

        // Create cards
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
        }

        // Create buttons for the cards
        kartlar = new JButton[16]; // 16 player cards
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 4)); // 4x4 grid for cards
        for (int i = 0; i < 16; i++) {
            kartlar[i] = new JButton();
            ImageIcon icon = new ImageIcon(kartResimleri[i]);
            kartlar[i].setIcon(icon); // Set card image
            kartlar[i].setPreferredSize(new Dimension(100, 150));
            jPanel.add(kartlar[i]);
        }

        // Set the position of the cards panel
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        jPanel.setBounds(width / 2 - 200, height / 2 - 200, 400, 400);
        contentPanel.add(jPanel); // Add the card panel to the content panel
        contentPanel.setComponentZOrder(jPanel, 1); // Bring cards to the front
        contentPanel.repaint();
        contentPanel.revalidate();
    }

    public void kartSec() {
        if (kullanıcı.getKartListesi().isEmpty() || bilgisayar.getKartListesi().isEmpty()) {
            System.out.println("Oyun Bitti");
            return;
        }
        System.out.println(kullanıcı.getKartListesi().size());
        System.out.println(bilgisayar.getKartListesi().size());
    }

    public static void main(String[] args) {
        Test frame = new Test();
        frame.setVisible(true);
    }
}
