package com.oop.mimala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainMenu {
    private JFrame frame;

    public MainMenu() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true); // Remove window borders
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        frame.setLayout(new BorderLayout());

        // Load Background GIF (Scaling)
        ImageIcon gifIcon = new ImageIcon("assets/MainMenu.gif");
        JLabel background = new JLabel(gifIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setLayout(new GridBagLayout()); // Center the button

        // Create Start Button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(200, 50));
        startGameButton.addActionListener(this::startGame);

        // Add Components
        background.add(startGameButton);
        frame.add(background, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startGame(ActionEvent e) {
        frame.setVisible(false); // Hide menu instead of closing it

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mimala Game");
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.useVsync(true);

        new Lwjgl3Application(new Mimala() {
            @Override
            public void dispose() {
                super.dispose();
                SwingUtilities.invokeLater(() -> frame.setVisible(true)); // Show menu when game closes
            }
        }, config);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
