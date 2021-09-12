package main;

import camera.Cam;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import components.effects.Effects;
import components.map.tilecoding.logic.BitmaskLogic;
import main.gamestates.GameStateManager;
import input.Mouse;
import graphics.DrwHandler;

public class GameClass extends ApplicationAdapter {

    private GameStateManager gsm;
    private Pixmap cursor;

    @Override
    public void create() {
        gsm = new GameStateManager();
        cursor = new Pixmap(Gdx.files.internal("res/testing/cursor/cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor,cursor.getWidth()/2-2 , cursor.getHeight()/2-2));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        Gdx.graphics.setTitle( "fps: " + Gdx.graphics.getFramesPerSecond());
        Cam.instance.update(dt);
        Mouse.instance.update(dt);
        gsm.delegate(dt);
        // Mouse must be updated after gsm: ("justPressed/justReleased") ??
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        cursor.dispose();
        DrwHandler.instance.dispose();
    }
}
