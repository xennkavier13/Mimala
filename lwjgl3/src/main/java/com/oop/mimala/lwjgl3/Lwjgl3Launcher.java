package com.oop.mimala.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.oop.mimala.Mimala;

/** Launches the desktop (LWJGL3) application. */
//MIMALA LAUNCHER
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();

    }

    public static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Mimala(), getDefaultConfiguration());

    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Mimala");

        int screenWidth = Lwjgl3ApplicationConfiguration.getDisplayMode().width;
        int screenHeight = Lwjgl3ApplicationConfiguration.getDisplayMode().height - 80;

        configuration.setWindowedMode(screenWidth, screenHeight);
        configuration.setMaximized(true);
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        return configuration;
    }

}
