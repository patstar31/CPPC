package org.GUI.Page;

import org.GUI.Components.GradientPanel;
import org.GUI.Components.RoundedPanel;
import org.GUI.Components.ShadowPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllSongsPanel extends JPanel {

    public AllSongsPanel(JPanel cardPanel) {
        // Colors
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");
        Color softGray = new Color(220, 220, 220, 100);

        // Phone container
        RoundedPanel phonePanel = new RoundedPanel(35, Color.BLACK);
        phonePanel.setLayout(new BorderLayout());
        phonePanel.setPreferredSize(new Dimension(400, 800));

        GradientPanel gradientPanel = new GradientPanel(deepBlue, mutedPurple);
        gradientPanel.setLayout(new BorderLayout());
        phonePanel.add(gradientPanel, BorderLayout.CENTER);

        // Top bar
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel backButton = new JLabel("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 28));
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        JLabel titleLabel = new JLabel("All Songs");
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topBarPanel.add(backButton, BorderLayout.WEST);
        topBarPanel.add(titleLabel, BorderLayout.CENTER);

        gradientPanel.add(topBarPanel, BorderLayout.NORTH);

        // Song list
        JPanel songsListPanel = new JPanel();
        songsListPanel.setOpaque(false);
        songsListPanel.setLayout(new BoxLayout(songsListPanel, BoxLayout.Y_AXIS));

        addSongItem(songsListPanel, "Decode", "Paramore", "/decode_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Televised", "HUNNY", "/televised_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Complicated", "Avril Lavigne", "/complicated_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Still into you", "Paramore", "/still_into_you_album_art.jpg", softGray);

        JScrollPane scrollPane = new JScrollPane(songsListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        gradientPanel.add(scrollPane, BorderLayout.CENTER);

        // Updated nav bar
        gradientPanel.add(createBottomNavBar(mauvePink, Color.WHITE, cardPanel), BorderLayout.SOUTH);

        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.add(shadowPanel);
    }

    private void addSongItem(JPanel parentPanel, String title, String artist, String imagePath, Color bgColor) {
        RoundedPanel songContainerPanel = new RoundedPanel(15, bgColor);
        songContainerPanel.setPreferredSize(new Dimension(350, 80));
        songContainerPanel.setLayout(new BorderLayout(15, 0));
        songContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel placeholder = new JLabel("♪");
        placeholder.setFont(getFont("SansSerif", Font.PLAIN, 40));
        placeholder.setForeground(Color.WHITE);
        songContainerPanel.add(placeholder, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 16));

        JLabel artistLabel = new JLabel(artist);
        artistLabel.setForeground(Color.LIGHT_GRAY);
        artistLabel.setFont(getFont("Montserrat", Font.PLAIN, 12));

        textPanel.add(titleLabel);
        textPanel.add(artistLabel);

        songContainerPanel.add(textPanel, BorderLayout.CENTER);

        parentPanel.add(songContainerPanel);
        parentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private Font getFont(String name, int style, int size) {
        try {
            return new Font(name, style, size);
        } catch (Exception e) {
            return new Font("SansSerif", style, size);
        }
    }

    private JPanel createBottomNavBar(Color activeColor, Color inactiveColor, JPanel cardPanel) {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setOpaque(false);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        JPanel homeItem = createNavItem("⌂", "Home", inactiveColor, false);
        homeItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        JPanel libraryItem = createNavItem("🎵", "Library", inactiveColor, false);
        libraryItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Library");
            }
        });

        JPanel accountItem = createNavItem("👤", "Account", inactiveColor, false);
        accountItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Account");
            }
        });

        navBar.add(homeItem);
        navBar.add(libraryItem);
        navBar.add(accountItem);

        return navBar;
    }

    private JPanel createNavItem(String iconText, String labelText, Color color, boolean isActive) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(getFont("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(color);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(getFont("Montserrat", isActive ? Font.BOLD : Font.PLAIN, 12));
        textLabel.setForeground(color);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        itemPanel.add(iconLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        itemPanel.add(textLabel);
        return itemPanel;
    }
}
