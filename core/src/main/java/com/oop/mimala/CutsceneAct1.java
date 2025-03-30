package com.oop.mimala;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class CutsceneAct1 {
    private JFrame frame;
    private JLabel cutsceneLabel;
    private String cutscene = "assets/Cutscenes/Cutscene(Act1).gif";
    private Runnable onComplete;
    private Clip musicClip;

    public CutsceneAct1(Runnable onComplete) {
        this.onComplete = onComplete;
        frame = new JFrame("Cutscenes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        cutsceneLabel = new JLabel();
        cutsceneLabel.setLayout(new BorderLayout());
        cutsceneLabel.setOpaque(true);
        cutsceneLabel.setBackground(Color.BLACK); // Prevents white flash

        frame.add(cutsceneLabel);
        frame.setVisible(true);

        showCutscene();
    }

    private void showCutscene() {
        ImageIcon cutsceneImage = new ImageIcon(cutscene);
        cutsceneLabel.setIcon(cutsceneImage);

        //plays music
        playBackgroundMusic("assets/sounds/scene1_music.wav");

        // Smooth rendering
        cutsceneLabel.repaint();

        // Automatically proceed after GIF duration (40 seconds)
        new Timer(47000, e -> {
            frame.dispose();
            onComplete.run();
        }).start();

        // Skip cutscene if clicked
        cutsceneLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                }
                onComplete.run();
            }
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

            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
