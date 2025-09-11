package org.example;

import org.GUI.Page.AccountPanel;
import org.GUI.Page.AllSongsPanel;
import org.GUI.Page.Home;
import org.GUI.Page.LibraryPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Music Player");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(420, 880);
            frame.setMinimumSize(new Dimension(420, 880));
            frame.setLocationRelativeTo(null);

            JPanel cardPanel = new JPanel(new CardLayout());

            Home homePanel = new Home(cardPanel);
            AllSongsPanel allSongsPanel = new AllSongsPanel(cardPanel);
            LibraryPanel libraryPanel = new LibraryPanel(cardPanel);
            AccountPanel accountPanel = new AccountPanel(cardPanel);

            cardPanel.add(homePanel, "Home");
            cardPanel.add(allSongsPanel, "AllSongs");
            cardPanel.add(libraryPanel, "Library");
            cardPanel.add(accountPanel, "Account");

            frame.setContentPane(cardPanel);
            frame.setVisible(true);
        });
    }
}

