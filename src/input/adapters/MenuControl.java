package input.adapters;

import com.badlogic.gdx.Input.Keys;
import input.GameKeys;


public class MenuControl extends Control {


    public MenuControl() { super(); }

    public boolean keyDown(int k) {
        switch (k) {
            case Keys.UP:
            case Keys.W:
                GameKeys.setKey(GameKeys.UP, true);
                break;
            case Keys.RIGHT:
            case Keys.D:
                GameKeys.setKey(GameKeys.RIGHT, true);
                break;
            case Keys.DOWN:
            case Keys.S:
                GameKeys.setKey(GameKeys.DOWN, true);
                break;
            case Keys.LEFT:
            case Keys.A:
                GameKeys.setKey(GameKeys.LEFT, true);
                break;
            case Keys.ENTER:
                GameKeys.setKey(GameKeys.ENTER, true);
                break;
            case Keys.ESCAPE:
                GameKeys.setKey(GameKeys.ESCAPE, true);
                break;
        }
        return true;
    }

    public boolean keyUp(int k) {
        switch (k) {
            case Keys.UP:
            case Keys.W:
                GameKeys.setKey(GameKeys.UP, false);
                break;
            case Keys.RIGHT:
            case Keys.D:
                GameKeys.setKey(GameKeys.RIGHT, false);
                break;
            case Keys.DOWN:
            case Keys.S:
                GameKeys.setKey(GameKeys.DOWN, false);
                break;
            case Keys.LEFT:
            case Keys.A:
                GameKeys.setKey(GameKeys.LEFT, false);
                break;
            case Keys.ENTER:
                GameKeys.setKey(GameKeys.ENTER, false);
                break;
            case Keys.ESCAPE:
                GameKeys.setKey(GameKeys.ESCAPE, false);
                break;
        }
        return true;
    }


}
