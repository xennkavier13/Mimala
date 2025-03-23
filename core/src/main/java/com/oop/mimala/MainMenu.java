package com.oop.mimala;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainMenu {
    private JFrame frame;
    private JButton exitGameButton;
    private JButton startGameButton;
    private JPanel panel1;
    private Clip musicClip;

    public MainMenu() {
        showIntroScreen();
    }

    private void showIntroScreen() {
        frame = new JFrame("Intro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.BLACK); // Ensure background stays black

        ImageIcon introGif = new ImageIcon("assets/MainMenuIntro.gif");
        JLabel introLabel = new JLabel(introGif) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Scale the image to fit the label
                g.drawImage(introGif.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        introLabel.setLayout(new BorderLayout());

        JPanel clickPanel = new JPanel(new GridBagLayout());
        clickPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(450, 0, 0, 0);

        clickPanel.add(new JLabel(), gbc);
        introLabel.add(clickPanel, BorderLayout.CENTER);

        frame.add(introLabel);

        introLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("assets/sounds/enter_mainmenu.wav");
                showMainMenu();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    showMainMenu();
                }
            }
        });

        frame.setVisible(true);

        playBackgroundMusic("assets/sounds/mimala_music.wav");
    }

    private void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setTitle("Main Menu");
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
        background.setLayout(new GridBagLayout());

        // Button Panel
        panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.setLayout(new GridLayout(0, 1, 10, -15)); // Reduced space between buttons

        // Start Game Button
        startGameButton = createImageButton(
            "assets/Start/StartGame_Off.png",
            "assets/Start/StartGame_Hover.png",
            "assets/Start/StartGame_Hover.png"
        );
        startGameButton.setPreferredSize(new Dimension(1000, 100));
        startGameButton.addActionListener(this::startGame);

        // Exit Game Button
        exitGameButton = createImageButton(
            "assets/End/EndGame_Off.png",
            "assets/End/EndGame_Hover.png",
            "assets/End/EndGame_Hover.png"
        );

        exitGameButton.setPreferredSize(new Dimension(1000, 100));
        exitGameButton.addActionListener(e -> System.exit(0));

        // Add buttons to panel
        panel1.add(startGameButton);
        panel1.add(exitGameButton);

        // Add panel to background (Lower the buttons using GridBagConstraints)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 200, 0);

        background.add(panel1, gbc);
        frame.add(background, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private void startGame(ActionEvent e) {
        stopBackgroundMusic();
        frame.setVisible(false);

        new CutsceneAct1(() -> {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Mimala Game");
            config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
            config.useVsync(true);

            new Lwjgl3Application(new Mimala() {
                @Override
                public void dispose() {
                    super.dispose();
                    SwingUtilities.invokeLater(() -> {
                        frame.setVisible(true);
                        playBackgroundMusic("assets/sounds/mimala_music.wav");
                    });
                }
            }, config);
        });
    }

    private void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);

            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);

            float volumeReduction = -5.0f; // Reduce volume by 10 decibels (adjust as needed)
            gainControl.setValue(volumeReduction);

            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
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

    private void playSelectSound() {
        try {
            File audioFile = new File("assets/sounds/select_menu.wav");
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: assets/select_menu.wav");
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private JButton createImageButton(String normalPath, String hoverPath, String pressedPath) {
        JButton button = new JButton(new ImageIcon(normalPath));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setIcon(new ImageIcon(hoverPath));
                playSelectSound();
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

    private Font loadTrajanProFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/TrajanPro-Regular.ttf")).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int) size);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
