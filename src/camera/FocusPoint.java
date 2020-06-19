package camera;

import com.badlogic.gdx.math.Vector2;
import input.GameKeys;

public class FocusPoint {

    public static boolean inControl = true;
    public static float velocity = 1000f;
    public static Vector2 position =    new Vector2(0,0);
    private static Vector2 direction =  new Vector2(0,0);

    // todo Mouseposition does not translate with focuspoint if mouse is still

    public static void follow(float dt) {
        if (inControl){
            direction.set(0,0);
            if (GameKeys.isDown(GameKeys.UP))     { direction.y = 1; }
            if (GameKeys.isDown(GameKeys.RIGHT))  { direction.x = 1; }
            if (GameKeys.isDown(GameKeys.DOWN))   { direction.y = -1; }
            if (GameKeys.isDown(GameKeys.LEFT))   { direction.x = -1; }
            if (GameKeys.isDown(GameKeys.UP) && GameKeys.isDown(GameKeys.DOWN))     { direction.y = 0; }
            if (GameKeys.isDown(GameKeys.RIGHT) && GameKeys.isDown(GameKeys.LEFT))  { direction.x = 0; }
            if (direction.x != 0) position.x += direction.nor().x * velocity * dt;
            if (direction.y != 0) position.y += direction.nor().y * velocity * dt;
            Cam.instance.lerpToTarget(position);
        }
    }
}
