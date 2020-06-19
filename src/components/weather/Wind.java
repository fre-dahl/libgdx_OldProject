package components.weather;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Wind {

    private static final int MEASUREPOINT = 60;
    public Direction direction;
    private Vector2 velocity;
    private float timer = 0;
    private int strength;


    public Wind(int strength, Direction direction) {
        velocity = new Vector2();
        this.direction = direction;
        this.strength = strength;
    }

    enum Direction {
        N(90f),
        NNE(67.5f),
        NE(45f),
        ENE(22.5f),
        E(0f),
        ESE(337.5f),
        SE(315f),
        SSE(292.5f),
        S(270f),
        SSW(247.5f),
        SW(225f),
        WSW(202.5f),
        W(180f),
        WNW(157.5f),
        NW(135f),
        NNW(112.5f);

        Vector2 vector = new Vector2(1,0);
        float degrees;

        Direction(float degrees) {
            this.degrees = degrees;
            vector.setAngle(degrees);
        }

        public Direction getDirection(float d) {
            if (d<0) return ESE;
            if (d>360) return ENE;
            for (Direction dir : Direction.values()) {
                if (dir.degrees == d) {
                    return dir;
                }
            }
            return E;
        }

    }

    public void update(float dt) {
        timer += dt;
        if (timer > MEASUREPOINT) {
            timer = 0;
            int i = MathUtils.random(2) -1;
            if (i!=0) direction = direction.getDirection(i*22.5f+ direction.degrees);
        }
    }

    public Vector2 getVelocity() {
        float x = direction.vector.x * strength;
        float y = direction.vector.y * strength;
        velocity.set(x,y);
        return velocity;
    }

    public void increaseWind(int amount) {
        strength+=amount;
    }

}
