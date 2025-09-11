package org.GUI.Page;

import org.GUI.Components.GradientPanel;
import org.GUI.Components.RoundedPanel;
import org.GUI.Components.ShadowPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * LibraryPanel — mirrors the original PlaylistPanel sizing/behavior,
 * keeps the phone container consistent with Home and Account panels.
 */
public class LibraryPanel extends JPanel {
    private JPanel playlistsContainer; // container for dynamic playlists
    private java.util.List<String> playlists;

    public LibraryPanel(JPanel cardPanel) {
        playlists = new ArrayList<>();

        // Colors (kept consistent)
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");
        Color softGray = new Color(220, 220, 220, 100);
        Color playlistGray = new Color(100, 100, 100, 150);

        // ---------- Phone container (match Home & Account exactly) ----------
        RoundedPanel phonePanel = new RoundedPanel(35, Color.BLACK);
        phonePanel.setLayout(new BorderLayout());
        phonePanel.setPreferredSize(new Dimension(400, 800));
        phonePanel.setMaximumSize(new Dimension(400, 800));
        phonePanel.setMinimumSize(new Dimension(400, 800));
        phonePanel.setOpaque(false);

        // Gradient background inside phone
        GradientPanel gradientPanel = new GradientPanel(deepBlue, mutedPurple);
        gradientPanel.setLayout(new BorderLayout());
        phonePanel.add(gradientPanel, BorderLayout.CENTER);

        // ---------- Top bar ----------
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Library");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topBarPanel.add(titleLabel, BorderLayout.CENTER);
        gradientPanel.add(topBarPanel, BorderLayout.NORTH);

        // ---------- Content area ----------
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Liked Songs card (same size as original PlaylistPanel cards)
        RoundedPanel likedSongsPanel = new RoundedPanel(25, mauvePink);
        likedSongsPanel.setLayout(new GridBagLayout());
        likedSongsPanel.setPreferredSize(new Dimension(350, 120));
        likedSongsPanel.setMaximumSize(new Dimension(350, 120));
        likedSongsPanel.setMinimumSize(new Dimension(350, 120));

        JLabel likedSongsLabel = new JLabel("Liked Songs");
        likedSongsLabel.setForeground(Color.WHITE);
        likedSongsLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        likedSongsPanel.add(likedSongsLabel);

        likedSongsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Placeholder — preserve behavior: show message for now
                JOptionPane.showMessageDialog(LibraryPanel.this,
                        "Liked Songs clicked! (Hook to real liked songs later)");
            }
        });

        // small spacing between top cards (Spotify-like grouping)
        contentPanel.add(likedSongsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Create Playlist card (same size)
        RoundedPanel createPlaylistPanel = new RoundedPanel(25, softGray);
        createPlaylistPanel.setLayout(new GridBagLayout());
        createPlaylistPanel.setPreferredSize(new Dimension(350, 120));
        createPlaylistPanel.setMaximumSize(new Dimension(350, 120));
        createPlaylistPanel.setMinimumSize(new Dimension(350, 120));

        JLabel createPlaylistLabel = new JLabel("Create Playlist");
        createPlaylistLabel.setForeground(Color.WHITE);
        createPlaylistLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        createPlaylistPanel.add(createPlaylistLabel);

        // Clicking opens the same mini-frame playlist-creation dialog (original behavior)
        createPlaylistPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCreatePlaylistFrame();
            }
        });

        contentPanel.add(createPlaylistPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Playlists container (new playlists appended here, same card size)
        playlistsContainer = new JPanel();
        playlistsContainer.setOpaque(false);
        playlistsContainer.setLayout(new BoxLayout(playlistsContainer, BoxLayout.Y_AXIS));
        playlistsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the dynamic playlists container
        contentPanel.add(playlistsContainer);

        // Put content into gradient center
        gradientPanel.add(contentPanel, BorderLayout.CENTER);

        // ---------- Bottom navigation bar (Home | Library | Account) ----------
        gradientPanel.add(createBottomNavBar(mauvePink, Color.WHITE, cardPanel), BorderLayout.SOUTH);

        // ---------- Shadow wrapper and panel addition ----------
        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.add(shadowPanel);
    }

    /** Opens the same small frame to create a playlist (preserving original flow). */
    private void showCreatePlaylistFrame() {
        JFrame createPlaylistFrame = new JFrame("Create Playlist");
        createPlaylistFrame.setSize(300, 150);
        createPlaylistFrame.setLocationRelativeTo(this);
        createPlaylistFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createPlaylistFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.decode("#355C7D"));

        // Playlist name input (same as original)
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setOpaque(false);
        JTextField nameField = new JTextField(15);
        namePanel.add(new JLabel("Playlist name:"));
        namePanel.add(nameField);

        JButton createButton = new JButton("Create");
        createButton.setBackground(Color.decode("#C56C86"));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);

        createButton.addActionListener(e -> {
            String playlistName = nameField.getText().trim();
            if (playlistName.isEmpty()) {
                JOptionPane.showMessageDialog(createPlaylistFrame,
                        "Please enter a name for your playlist.");
            } else {
                addPlaylist(playlistName);
                createPlaylistFrame.dispose();
            }
        });

        panel.add(namePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createButton);

        createPlaylistFrame.add(panel);
        createPlaylistFrame.setVisible(true);
    }

    /** Adds a playlist card below Create Playlist (same size as original playlist cards). */
    private void addPlaylist(String name) {
        // store
        playlists.add(name);

        // create card
        RoundedPanel playlistPanel = new RoundedPanel(25, new Color(100, 100, 100, 150));
        playlistPanel.setLayout(new GridBagLayout());
        playlistPanel.setPreferredSize(new Dimension(350, 120));
        playlistPanel.setMaximumSize(new Dimension(350, 120));
        playlistPanel.setMinimumSize(new Dimension(350, 120));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        playlistPanel.add(nameLabel);

        // clicking a created playlist shows a message (preserved behavior)
        playlistPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LibraryPanel.this,
                        "You clicked on playlist: " + name);
            }
        });

        playlistsContainer.add(playlistPanel);
        playlistsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        playlistsContainer.revalidate();
        playlistsContainer.repaint();
    }

    /** Bottom navigation bar builder (unchanged behavior: Library active). */
    private JPanel createBottomNavBar(Color activeColor, Color inactiveColor, JPanel cardPanel) {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setOpaque(false);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // Home
        JPanel homeItem = createNavItem("⌂", "Home", inactiveColor, false);
        homeItem.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        // Library (active)
        JPanel libraryItem = createNavItem("🎵", "Library", activeColor, true);

        // Account
        JPanel accountItem = createNavItem("👤", "Account", inactiveColor, false);
        accountItem.addMouseListener(new MouseAdapter() {
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

    /** Single nav item helper. */
    private JPanel createNavItem(String iconText, String labelText, Color color, boolean isActive) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(color);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Montserrat", isActive ? Font.BOLD : Font.PLAIN, 12));
        textLabel.setForeground(color);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        itemPanel.add(iconLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        itemPanel.add(textLabel);

        return itemPanel;
    }
}
