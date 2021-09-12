package graphics.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import components.entity.Entity;
import components.entity.Entity.AnimationState;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {

    public static final String TAG = AnimationSet.class.getName();
    private Map<Entity.AnimationState, Animations> animations;
    private Animations currentAnimations;
    private Entity.AnimationState currentAnimationState;

    public AnimationSet() {
        animations = new HashMap<>();
    }

    public void newAnimations(AnimationState animationState, Array<Array<TextureRegion>> grid, float frameDuration) {
        if (animations.containsKey(animationState)) { Gdx.app.log(TAG, "State already initiated"); }
        else animations.put(animationState, new Animations(grid, frameDuration));
    }

    public TextureRegion getKeyFrame(Entity.AnimationState animationState, float deg, float dt) {
        if (animationState != currentAnimationState) {
            currentAnimations.reset();
            currentAnimations = animations.get(animationState);
            currentAnimationState = animationState;
        }
        return currentAnimations.getKeyFrame(deg,dt);
    }

    public TextureRegion getInitial(AnimationState animationState) {
        return animations.get(animationState).getKeyFrame(270,0);
    }

    public void init(Entity.AnimationState animationState) {
        currentAnimationState = animationState;
        currentAnimations = animations.get(currentAnimationState);
    }
}
