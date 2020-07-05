package components.entity;

import components.effects.Effect;
import components.effects.Effects;
import components.tile.TileMap;
import graphics.Assets;
import graphics.anim.AnimationSet;
import graphics.drwdat.DrwExtra;
import graphics.drwdat.DrwRegion;
import main.Settings;

public class Knight extends Entity {

    AnimationSet animations;
    DrwRegion drwRegion;
    DrwRegion shadow;
    DrwExtra reflection;
    float effctInterval;

    public Knight(Type type, TileMap map, float x, float y) {
        super(type,map,x,y);
        animations = new AnimationSet();
        animations.newAnimations(State.IDLE, Assets.instance.assetKnight.knight_idle,0.2f);
        animations.newAnimations(State.WALK,Assets.instance.assetKnight.knight_walk,0.1f);
        drwRegion = new DrwRegion(animations.getInitial(State.IDLE),x-type.getW()/2,y,type.getW() * Settings.SCALE,type.getH() * Settings.SCALE,(byte)5);
        shadow = new DrwRegion(Assets.instance.assetKnight.knight_shadow,x,y,type.getW()*Settings.SCALE,8*Settings.SCALE,(byte)4);
        reflection = new DrwExtra(drwRegion.getRegion(),drwRegion.getX(),drwRegion.getY(),drwRegion.getW(),drwRegion.getH(),(byte)1,true);
        reflection.setAlpha(0.5f);
        animations.init(State.IDLE);
        velocity.set(0,-1);
        state = State.IDLE;
        moveSpeed = 50f;

    }
    @Override
    public void update(float dt) {
        if (actionQueue.size!=0) {
            actionQueue.get(0).execute(dt);
            if (nearWater) {
                effctInterval+=dt;
                if (effctInterval>0.1f) {
                    Effects.instance.create(Effect.Type.WATER_RIPPLES,position);
                    effctInterval = 0;
                }
            }
        }

        drwRegion.setPosition(position.x-(type.getW()/2*Settings.SCALE) , position.y);
        drwRegion.setRegion(animations.getKeyFrame(state,velocity.angle(),dt));
        shadow.setPosition(drwRegion.getX(),position.y - 4*Settings.SCALE);
        if (nearWater){
            reflection.setRegion(drwRegion.getRegion());
            reflection.setPosition(drwRegion.getX(),drwRegion.getY()-type.getH()*Settings.SCALE);
            reflection.setRender(true);
        }
        else reflection.setRender(false);


        proximity.setPosition(position);
    }

    @Override
    public void dispose() {
        drwRegion.dispose();
        reflection.dispose();
        shadow.dispose();
    }

    @Override
    public void selected() {

    }

    @Override
    public void hovered() {

    }
}
