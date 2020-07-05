package graphics.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import components.entity.Entity.State;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {

    public static final String TAG = AnimationSet.class.getName();
    private Map<State, Animations> animations;
    private Animations currentAnimations;
    private State currentState;

    public AnimationSet() {
        animations = new HashMap<>();
    }

    public void newAnimations(State state, Array<Array<TextureRegion>> grid, float frameDuration) {
        if (animations.containsKey(state)) { Gdx.app.log(TAG, "State already initiated"); }
        else animations.put(state, new Animations(grid, frameDuration));
    }

    public TextureRegion getKeyFrame(State state, float deg, float dt) {
        if (state != currentState) {
            currentAnimations.reset();
            currentAnimations = animations.get(state);
            currentState = state;
        }
        return currentAnimations.getKeyFrame(deg,dt);
    }

    public TextureRegion getInitial(State state) {
        return animations.get(state).getKeyFrame(270,0);
    }

    public void init(State state) {
        currentState = state;
        currentAnimations = animations.get(currentState);
    }
}
