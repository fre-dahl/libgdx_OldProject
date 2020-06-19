package camera;

import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Rumble {

    private static float pow = 0;
    private static float time = 0;
    private static float currentTime = 0;
    private static Vector3 pos = new Vector3();
    private static Random random = new Random();

    public static void rumble(float power, float duration) {
        pow = power;
        time = duration;
        currentTime = 0;
    }

    public static Vector3 update(float dt) {
        if (currentTime <= time) {
            float currentPower = pow * ((time - currentTime) / time);
            pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;
            currentTime += dt;
        } else time = 0;
        return pos;
    }

    public static float getRumbleTimeLeft() { return time; }

    public static Vector3 getPos() { return pos; }
}

/*
    if (Rumble.getRumbleTimeLeft() > 0){
        Rumble.update(dt);
        camera.translate(Rumble.getPos());
     } else {
         camera.position.lerp(hero.pos, .2f);
    }
 */