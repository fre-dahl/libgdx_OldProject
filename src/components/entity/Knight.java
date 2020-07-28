package components.entity;

import camera.Cam;
import com.badlogic.gdx.math.Rectangle;
import components.GameWorld;
import components.effects.Effect;
import components.effects.Effects;
import graphics.Assets;
import graphics.anim.AnimationSet;
import graphics.drwdat.DrwExtra;
import graphics.drwdat.DrwRegion;
import main.Settings;

public class Knight extends Entity {

    AnimationSet animations;
    DrwRegion drwBody;
    DrwRegion drwShadow;
    DrwExtra drwReflection;
    float waterRippleTimer;


    public Knight(Type type, GameWorld world, float x, float y) {
        super(type,world,x,y);
        playerUnit = true;
        hostile = false;
        animations = new AnimationSet();
        animations.newAnimations(State.IDLE, Assets.instance.assetKnight.knight_idle,0.2f);
        animations.newAnimations(State.WALK,Assets.instance.assetKnight.knight_walk,0.1f);
        drwBody = new DrwRegion(animations.getInitial(State.IDLE),x-type.getW()/2,y,type.getW() * Settings.SCALE,type.getH() * Settings.SCALE,(byte)5);
        drwShadow = new DrwRegion(Assets.instance.assetKnight.knight_shadow,x,y,type.getW()*Settings.SCALE,8*Settings.SCALE,(byte)4);
        drwReflection = new DrwExtra(drwBody.getRegion(), drwBody.getX(), drwBody.getY(), drwBody.getW(), drwBody.getH(),(byte)1,true);
        drwReflection.setAlpha(0.5f);
        state = State.IDLE;
        animations.init(state);
        velocity.set(0,-1);
        moveSpeed = 50f;
    }

    @Override
    public void update(float dt) {
        inView = Cam.instance.getCullingWindow().overlaps(selectBox);
        if (actionQueue.notEmpty()) actionQueue.first().execute(dt);
        if (inView) {
            if (!inViewList) {
                world.gameUI.unitsInView.add(this);
                inViewList = true;
            }
            drwBody.setRender(true);
            drwShadow.setRender(true);
            drwBody.setPosition(position.x-(type.getW()/2*Settings.SCALE) , position.y);
            drwBody.setRegion(animations.getKeyFrame(state,velocity.angle(),dt));
            drwShadow.setPosition(drwBody.getX(),position.y - 4*Settings.SCALE);
            if (nearWater){
                drwReflection.setRegion(drwBody.getRegion());
                drwReflection.setPosition(drwBody.getX(), drwBody.getY()-type.getH()*Settings.SCALE);
                drwReflection.setRender(true);
                if (state == State.WALK) {
                    waterRippleTimer +=dt;
                    if (waterRippleTimer >0.1f) {
                        Effects.instance.create(Effect.Type.WATER_RIPPLES,position);
                        waterRippleTimer = 0;
                    }
                }
            }
            else drwReflection.setRender(false);
        }
        else {
            if (inViewList) {
                world.gameUI.unitsInView.removeValue(this,true);
                inViewList = false;
            }
            drwBody.setRender(false);
            drwShadow.setRender(false);
            drwReflection.setRender(false);
        }
        proximity.setPosition(position);
        selectBox.setPosition(position.x - type.getW()/2, position.y);
    }

    @Override
    public void dispose() {
        drwBody.dispose();
        drwReflection.dispose();
        drwShadow.dispose();
    }

    @Override
    public void selected() {
        drwShadow.setRegion(Assets.instance.assetKnight.knight_select);
        selected = true;
    }

    @Override
    public void deSelected() {
        drwShadow.setRegion(Assets.instance.assetKnight.knight_shadow);
        selected = false;
    }

    @Override
    public void hovered() {
        drwShadow.setRegion(Assets.instance.assetKnight.knight_select);
        hovered = true;
    }

    @Override
    public void deHovered() {
        drwShadow.setRegion(Assets.instance.assetKnight.knight_shadow);
        hovered = false;
    }

    @Override
    public Rectangle getBox() {
        return selectBox;
    }

    @Override
    public Disposition getDisposition() { return disposition; }
}
