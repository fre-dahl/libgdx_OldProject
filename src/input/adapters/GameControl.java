package input.adapters;

import camera.Cam;
import com.badlogic.gdx.Input;
import input.GameKeys;


public class GameControl extends Control {


    public GameControl() { super(); }

    public boolean keyDown(int k) {
        switch (k) {
            case Input.Keys.UP:
            case Input.Keys.W:
                GameKeys.setKey(GameKeys.UP, true);
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                GameKeys.setKey(GameKeys.RIGHT, true);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                GameKeys.setKey(GameKeys.DOWN, true);
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                GameKeys.setKey(GameKeys.LEFT, true);
                break;
            case Input.Keys.ENTER:
                GameKeys.setKey(GameKeys.ENTER, true);
                break;
            case Input.Keys.ESCAPE:
                GameKeys.setKey(GameKeys.ESCAPE, true);
                break;
            case Input.Keys.SPACE:
                GameKeys.setKey(GameKeys.SPACE, true);
                break;
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                GameKeys.setKey(GameKeys.SHIFT, true);
                break;
            case Input.Keys.P:
                GameKeys.setKey(GameKeys.PAUSE, true);
                break;
        }
        return true;
    }

    public boolean keyUp(int k) {
        switch (k) {
            case Input.Keys.UP:
            case Input.Keys.W:
                GameKeys.setKey(GameKeys.UP, false);
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                GameKeys.setKey(GameKeys.RIGHT, false);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                GameKeys.setKey(GameKeys.DOWN, false);
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                GameKeys.setKey(GameKeys.LEFT, false);
                break;
            case Input.Keys.ENTER:
                GameKeys.setKey(GameKeys.ENTER, false);
                break;
            case Input.Keys.ESCAPE:
                GameKeys.setKey(GameKeys.ESCAPE, false);
                break;
            case Input.Keys.SPACE:
                GameKeys.setKey(GameKeys.SPACE, false);
                break;
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                GameKeys.setKey(GameKeys.SHIFT, false);
                break;
            case Input.Keys.P:
                GameKeys.setKey(GameKeys.PAUSE, false);
                break;
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        Cam.instance.zoom(amount);
        return true;
    }

}
