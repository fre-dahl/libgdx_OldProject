package main.gamestates.abstr;


import input.adapters.Control;
import input.GameKeys;
import main.gamestates.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Control control;


    protected GameState (GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public abstract void init();
    public abstract void loadAssets();
    public void update(float dt) {
        handleInput();
        GameKeys.update();
    };
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
}
