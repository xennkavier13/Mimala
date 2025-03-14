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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this::startGame);

        frame.add(startGameButton, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startGame(ActionEvent e) {

        frame.setVisible(false);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mimala Game");
        config.setMaximized(true);
        config.useVsync(true);

        new Lwjgl3Application(new Mimala() {
            @Override
            public void dispose() {
                super.dispose();
                SwingUtilities.invokeLater(() -> frame.setVisible(true));
            }
        }, config);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
