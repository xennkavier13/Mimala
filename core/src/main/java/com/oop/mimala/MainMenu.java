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
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this::startGame);

        frame.add(startGameButton, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private void startGame(ActionEvent e) {

        frame.setVisible(false); // Hide the main menu instead of closing it

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mimala Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);

        new Lwjgl3Application(new Mimala() {
            @Override
            public void dispose() {
                super.dispose();
                SwingUtilities.invokeLater(() -> frame.setVisible(true)); // Show menu when the game is closed
            }
        }, config);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
