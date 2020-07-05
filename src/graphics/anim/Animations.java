package graphics.anim;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animations {

    // animation grid cordination
    private int frames;
    private int directions;
    private int lastDirection;
    private int lastFrame;

    // time calculation
    private float cycleTime;
    private float timeInCycle;
    private float frameDuration;
    private float wait;

    // 360 limits for direction
    private float[] lims;

    // animation grid
    private Array<Array<TextureRegion>> grid;


    public Animations(Array<Array<TextureRegion>> grid, float frameDuration) {
        this.frameDuration = frameDuration;
        this.grid = grid;
        frames = grid.size;
        directions = grid.get(0).size;
        lims = new float[directions];
        cycleTime = frames * frameDuration;
        float section = (float)360/directions;
        for (int i = 0; i < directions; i++) {
            float lim = section*i + section/2;
            if(lim > 360) lim -= 360;
            lims[i] = lim;
        }
    }

    public TextureRegion getKeyFrame(float deg, float dt) {
        timeInCycle += dt;
        if (timeInCycle >= cycleTime) timeInCycle = 0;
        int frame = (int) (timeInCycle /frameDuration);
        int dir = getDir(deg);
        lastFrame = frame;
        lastDirection = dir;
        return grid.get(frame).get(dir);
    }


    private int getDir(float deg) {
        int dir = 0;
        for (int i = 0; i < directions; i++) {
            if (deg < lims[i]) { dir = i; break; }
        }return dir;
    }

    public void reset() { timeInCycle = 0; }


}
