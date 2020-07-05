package main.gamestates;

import main.gamestates.abstr.GameState;

public class GameStateManager {

    // current gamestate
    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 1;

    public GameStateManager() {
        //setState(MENU);
        setState(PLAY);
    }

    public void setState(int state) {

        if (gameState != null) gameState.dispose();
        if(state == MENU) {
            gameState = new MenuState(this);
        }
        if(state == PLAY) {
            gameState = new PlayState(this);
        }
    }

    public void delegate(float dt) {
        gameState.update(dt);
        gameState.draw();
    }

}
