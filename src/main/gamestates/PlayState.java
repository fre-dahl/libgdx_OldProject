package main.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import components.GameWorld;
import components.effects.Effects;
import graphics.DrwHandler;
import input.adapters.GameControl;
import input.GameKeys;
import camera.FocusPoint;
import graphics.Assets;
import main.gamestates.abstr.GameState;
import utils.Screenshot;

public class PlayState extends GameState {

    private boolean paused;
    public GameWorld world;

    public PlayState (GameStateManager gsm) { super(gsm); }


    @Override
    public void init() {
        loadAssets();
        control = new GameControl();
        Gdx.input.setInputProcessor(control);
        world = new GameWorld();
        world.newArea();
    }

    @Override
    public void loadAssets() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        world.update(dt);
        FocusPoint.follow(dt);
    }

    @Override
    public void draw() {
        DrwHandler.instance.drawInGame();
    }

    @Override
    public void handleInput() {
        //if (GameKeys.isPressed(GameKeys.SHIFT)) FocusPoint.inControl = !FocusPoint.inControl;
        if (GameKeys.isPressed(GameKeys.ESCAPE)) {
            gsm.setState(GameStateManager.MENU);
        }

        if (GameKeys.isPressed(GameKeys.SPACE)) world.newArea();
        if (GameKeys.isPressed(GameKeys.PAUSE)) paused =! paused;
        if (GameKeys.isPressed(GameKeys.SHIFT)) Screenshot.snap();
    }

    @Override
    public void dispose() {
        // Save
        world.dispose();
        Assets.instance.dispose();
    }


    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }
}
