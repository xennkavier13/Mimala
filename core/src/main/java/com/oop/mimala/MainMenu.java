package com.oop.mimala;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class MainMenu {
    private JFrame frame;
    private JButton exitGameButton;
    private Clip musicClip;


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
        background.setLayout(new GridBagLayout()); // Center the buttons

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Space between buttons

        // Start Game Button
        JButton startGameButton = createImageButton(
            "assets/Start/StartGame_Off.png",
            "assets/Start/StartGame_Hover.png",
            "assets/Start/StartGame_Hover.png" // Same as hover for pressed state
        );
        startGameButton.setPreferredSize(new Dimension(1000, 100));
        startGameButton.addActionListener(this::startGame);

        // Exit Game Button
        JButton exitGameButton = createImageButton(
            "assets/End/EndGame_Off.png",
            "assets/End/EndGame_Hover.png",
            "assets/End/EndGame_Hover.png"
        );

        exitGameButton.setPreferredSize(new Dimension(1000, 100));
        exitGameButton.addActionListener(e -> System.exit(0)); // Close program

        // Add buttons to panel
        buttonPanel.add(startGameButton);
        buttonPanel.add(exitGameButton);

        // Add panel to background
        background.add(buttonPanel);
        frame.add(background, BorderLayout.CENTER);

        frame.setVisible(true);

        playBackgroundMusic("assets/mimala_music.wav");
    }

    private void startGame(ActionEvent e) {
        stopBackgroundMusic(); // Stop the background music before launching the game
        frame.setVisible(false); // Hide menu instead of closing it

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mimala Game");
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.useVsync(true);

        new Lwjgl3Application(new Mimala() {
            @Override
            public void dispose() {
                super.dispose();
                SwingUtilities.invokeLater(() -> {
                    frame.setVisible(true); // Show menu when game closes
                    playBackgroundMusic("assets/mimala_music.wav"); // Restart music when returning to the menu
                });
            }
        }, config);
    }


    private void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    // Create a custom button with images
    private JButton createImageButton(String normalPath, String hoverPath, String pressedPath) {
        JButton button = new JButton(new ImageIcon(normalPath));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setIcon(new ImageIcon(hoverPath));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setIcon(new ImageIcon(normalPath));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setIcon(new ImageIcon(pressedPath));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setIcon(new ImageIcon(hoverPath));
            }
        });

        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }

}
