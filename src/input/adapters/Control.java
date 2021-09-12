package input.adapters;


import com.badlogic.gdx.InputAdapter;
import input.GameKeys;
import input.Mouse;

public abstract class Control extends InputAdapter {

    // input listeners are called each frame before (aplicationListeners render())
    // altså rett før render() i Gameclass.java

    protected Control() {
        GameKeys.resetKeys();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            Mouse.instance.leftClick(screenX,screenY);
        } else if (pointer == 0 && button == 1){
            Mouse.instance.rightClick(screenX,screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            Mouse.instance.leftReleased(screenX,screenY);
        } else if (pointer == 0 && button == 1){
            Mouse.instance.rightReleased(screenX,screenY);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Mouse.instance.hover(screenX,screenY);
        return true;
    }

}
