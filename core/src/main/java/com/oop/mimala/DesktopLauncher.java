package com.oop.mimala;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mimala Game");
        config.setWindowedMode(800, 600);



        config.useVsync(true);
        config.setForegroundFPS(60);

        // Set assets directory (optional for debugging)
        System.setProperty("user.dir", "core/assets");

        new Lwjgl3Application(new Mimala(), config);
    }
}
