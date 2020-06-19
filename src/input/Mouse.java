package input;

import camera.Cam;
import camera.FocusPoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Mouse {

    public static Mouse instance = new Mouse();
    // Timing
    private static final float SELECTION_DELAY = 0.15f;
    private float r_buttonDown_timer = 0f;
    private float l_buttonDown_timer = 0f;
    // Booleans
    private boolean r_buttonDown, l_buttonDown;
    private boolean r_justPressed, l_justPressed;
    private boolean r_justReleased, l_justReleased;
    // Screen Cordinates:
    private Vector2 mousePos_S;
    private Vector2 r_clickPos_S, l_clickPos_S;
    private Vector2 r_releasedPos_S, l_releasedPos_S;
    private Rectangle r_highlightBox_S, l_highlightBox_S;
    private Rectangle r_selectedBox_S, l_selectedBox_S;
    // World Cordinates:
    private Vector2 mousePos_W;
    private Vector2 r_clickPos_W, l_clickPos_W;
    private Vector2 r_releasedPos_W, l_releasedPos_W;
    private Rectangle r_highlightBox_W, l_highlightBox_W;
    private Rectangle r_selectedBox_W, l_selectedBox_W;

    private Vector2 tmpVector;

    private Mouse() {
        mousePos_S = new Vector2();
        r_clickPos_S = new Vector2();
        l_clickPos_S = new Vector2();
        r_releasedPos_S = new Vector2();
        l_releasedPos_S = new Vector2();
        r_highlightBox_S = new Rectangle();
        l_highlightBox_S = new Rectangle();
        r_selectedBox_S = new Rectangle();
        l_selectedBox_S = new Rectangle();

        tmpVector = new Vector2();
        mousePos_W = new Vector2();
        r_clickPos_W = new Vector2();
        l_clickPos_W = new Vector2();
        r_releasedPos_W = new Vector2();
        l_releasedPos_W = new Vector2();
        r_highlightBox_W = new Rectangle();
        l_highlightBox_W = new Rectangle();
        r_selectedBox_W = new Rectangle();
        l_selectedBox_W = new Rectangle();
    }

    public void update(float dt) { // placed late in "update queues"

        if (r_buttonDown) {
            hover(Gdx.input.getX(),Gdx.input.getY());
            r_buttonDown_timer += dt;
            if (r_buttonDown_timer > SELECTION_DELAY) {
                float x_min = Math.min(r_clickPos_S.x, mousePos_S.x);
                float y_min = Math.min(r_clickPos_S.y, mousePos_S.y);
                float x_max = Math.max(r_clickPos_S.x, mousePos_S.x);
                float y_max = Math.max(r_clickPos_S.y, mousePos_S.y);
                r_highlightBox_S.set(x_min,y_min,x_max-x_min,y_max-y_min);
                x_min = Math.min(r_clickPos_W.x, mousePos_W.x);
                y_min = Math.min(r_clickPos_W.y, mousePos_W.y);
                x_max = Math.max(r_clickPos_W.x, mousePos_W.x);
                y_max = Math.max(r_clickPos_W.y, mousePos_W.y);
                r_highlightBox_W.set(x_min,y_min,x_max-x_min,y_max-y_min);
            }

        }
        if (l_buttonDown) {
            hover(Gdx.input.getX(),Gdx.input.getY());
            l_buttonDown_timer += dt;
            if (l_buttonDown_timer > SELECTION_DELAY) {
                float x_min = Math.min(l_clickPos_S.x, mousePos_S.x);
                float y_min = Math.min(l_clickPos_S.y, mousePos_S.y);
                float x_max = Math.max(l_clickPos_S.x, mousePos_S.x);
                float y_max = Math.max(l_clickPos_S.y, mousePos_S.y);
                l_highlightBox_S.set(x_min,y_min,x_max-x_min,y_max-y_min);
                x_min = Math.min(l_clickPos_W.x, mousePos_W.x);
                y_min = Math.min(l_clickPos_W.y, mousePos_W.y);
                x_max = Math.max(l_clickPos_W.x, mousePos_W.x);
                y_max = Math.max(l_clickPos_W.y, mousePos_W.y);
                l_highlightBox_W.set(x_min,y_min,x_max-x_min,y_max-y_min);
            }
        }

        r_justPressed = r_justReleased = false;
        l_justPressed = l_justReleased = false;

    }

    public void hover(int x, int y) {
        tmpVector.set(x,y);
        mousePos_S.set(x, Gdx.graphics.getHeight() - y);
        mousePos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
    }

    public void rightClick(int x, int y) {
        tmpVector.set(x,y);
        r_buttonDown = true;
        r_justPressed = true;
        r_clickPos_S.set(x, Gdx.graphics.getHeight() - y);
        r_clickPos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
    }

    public void rightReleased(int x, int y) {
        tmpVector.set(x,y);
        r_buttonDown = false;
        r_justReleased = true;
        r_buttonDown_timer = 0f;
        r_releasedPos_S.set(x, Gdx.graphics.getHeight() - y);
        r_selectedBox_S.set(r_highlightBox_S);
        r_highlightBox_S.set(0,0,0,0);
        r_releasedPos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
        r_selectedBox_W.set(r_highlightBox_W);
        r_highlightBox_W.set(0,0,0,0);
    }

    public void leftClick(int x, int y) {
        tmpVector.set(x,y);
        l_buttonDown = true;
        l_justPressed = true;
        l_clickPos_S.set(x, Gdx.graphics.getHeight() - y);
        l_clickPos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
        FocusPoint.position.set(l_clickPos_W);
    }

    public void leftReleased(int x, int y) {
        tmpVector.set(x,y);
        l_buttonDown = false;
        l_justReleased = true;
        l_buttonDown_timer = 0f;
        l_releasedPos_S.set(x, Gdx.graphics.getHeight() - y);
        l_selectedBox_S.set(l_highlightBox_S);
        l_highlightBox_S.set(0,0,0,0);
        l_releasedPos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
        l_selectedBox_W.set(l_highlightBox_W);
        l_highlightBox_W.set(0,0,0,0);
    }

}
