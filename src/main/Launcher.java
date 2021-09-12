package main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

    public static void main(String[] args) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Graphics.DisplayMode desktopMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
        System.out.println(desktopMode.height + " " + desktopMode.width + " " + desktopMode.refreshRate);

        config.useGL30 = false;
        config.pauseWhenMinimized = true;
        config.pauseWhenBackground = true;
        config.width = Settings.SCREEN_W; // 1920
        config.height = Settings.SCREEN_H; // 1080
        //config.addIcon();
        config.resizable = true;

        config.vSyncEnabled = true;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;

        Application app = new LwjglApplication(new GameClass(), config);
        if(Gdx.graphics.supportsDisplayModeChange()) {
            System.out.println("yep");
            System.out.println("yep");
        }
        Gdx.app = app;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

    }

}
