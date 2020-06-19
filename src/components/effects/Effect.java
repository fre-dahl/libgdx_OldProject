package components.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import drawdata.DrwRegion;
import drawdata.drwabstract.DrwPool.DrwPoolable;
import main.Settings;

public class Effect implements DrwPoolable { // Make your own Animation class. it keeps checking playmodes each cycle (inefective)

    // Effect position exists as drwDat and is the center of the animation
    private Animation<TextureRegion> anim;
    public Type type;
    private DrwRegion drw;
    private float timer;

    public Effect(Type type, Animation<TextureRegion> anim) {
        this.anim = anim;
        this.type = type;
        TextureRegion r = anim.getKeyFrame(0);
        drw = new DrwRegion(r,0,0,r.getRegionWidth()* Settings.SCALE,r.getRegionHeight()*Settings.SCALE,type.layer);
    }

    public enum Type {

        BLOOD((byte)4),
        BLOOD_SPLATTER((byte)5),
        KNIGHT_EXPLODE((byte)5),
        WATER_RIPPLES((byte)2);

        byte layer;

        Type(byte layer) {
            this.layer = layer;
        }
    }

    public void update(float dt) {
        timer+=dt;
        drw.setRegion(anim.getKeyFrame(timer));
        if (anim.getAnimationDuration() < timer) {
            Effects.instance.free(this);
        }
    }

    public void setPos(float x, float y) {
        float x_center = x - drw.getW()/2;
        float y_center = y - drw.getH()/2;
        drw.setPosition(x_center,y_center);
    }

    @Override
    public void reset() { drw.dispose(); timer = 0; }

    @Override
    public void onClear() {
        drw.dispose();
    }

    @Override
    public void onSpawm() { drw.add(); }
}
