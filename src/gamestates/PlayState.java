package gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import components.GameWorld;
import input.adapters.GameControl;
import input.GameKeys;
import camera.FocusPoint;
import assets.Assets;
import managers.GameStateManager;

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

        // init player, world etc
    }

    @Override
    public void loadAssets() {
        Assets.instance.init(new AssetManager());

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        world.update(dt);
        //System.out.println(Mouse.hoverWorldPosition);


        if(!paused) {
            FocusPoint.follow(dt);
        }

    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.SHIFT)) FocusPoint.inControl = !FocusPoint.inControl;
        if (GameKeys.isPressed(GameKeys.ESCAPE)) gsm.setState(GameStateManager.MENU);
        if (GameKeys.isPressed(GameKeys.SPACE)) world.newArea();
        if (GameKeys.isPressed(GameKeys.PAUSE)) paused =! paused;

    }

    @Override
    public void dispose() {
        // Save
        // move to gameworld
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
