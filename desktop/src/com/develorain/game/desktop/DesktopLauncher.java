package com.develorain.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.develorain.game.Illumination;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Illumination.TITLE;
        config.width = Illumination.RESOLUTION_X;
        config.height = Illumination.RESOLUTION_Y;
        config.fullscreen = false;
        config.vSyncEnabled = false;
        config.resizable = true;
        config.foregroundFPS = 60;
        config.backgroundFPS = -1;
        new LwjglApplication(new Illumination(), config);
    }
}
