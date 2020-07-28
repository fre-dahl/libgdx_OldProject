package components.effects;

import graphics.Assets;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import components.effects.Effect.Type;
import com.badlogic.gdx.utils.Array;
import graphics.drwdat.abstr.DrwPool;

public class Effects implements Disposable {

    // a effect don't need to check components.map.culling. The object instancing it will.
    public static Effects instance = new Effects();
    public Array<Effect> current;
    public Factory factory;

    private Effects() {
        current = new Array<>();
        factory = new Factory();
    }

    public void newInstance() {
        current = new Array<>();
        factory = new Factory();
    }

    public void update(float dt) {
        for (Effect effect : current) {
            effect.update(dt);
        }
    }

    public void create(Type type, float x, float y) {
        Effect effect = factory.create(type);
        effect.setPos(x,y);
        current.add(effect);
    }

    public void create(Type type, Vector2 pos) {
        Effect effect = factory.create(type);
        effect.setPos(pos.x,pos.y);
        current.add(effect);
    }


    public void free(Effect effect) {
        current.removeValue(effect,true);
        factory.free(effect);
    }

    @Override
    public void dispose() {
        factory.bloodPool.clear();
        factory.splatterPool.clear();
        factory.waterRipplePool.clear();
        factory.knightExplodePool.clear();
    }


    private static class Factory {

        public Effect create(Type type) {
            switch (type) {
                case BLOOD: return bloodPool.obtain();
                case WATER_RIPPLES: return waterRipplePool.obtain();
                case BLOOD_SPLATTER: return splatterPool.obtain();
                case KNIGHT_EXPLODE: return knightExplodePool.obtain();
                default: return null;
            }
        }

        public void free(Effect effect) {
            switch (effect.type) {
                case BLOOD: bloodPool.free(effect); break;
                case BLOOD_SPLATTER: splatterPool.free(effect); break;
                case WATER_RIPPLES: waterRipplePool.free(effect); break;
                case KNIGHT_EXPLODE: knightExplodePool.free(effect); break;
                default:
            }
        }

        private final DrwPool<Effect> bloodPool = new DrwPool<Effect>() {
            @Override
            protected Effect newObject() {
                return new Effect(Type.BLOOD,Assets.instance.assetRipples.anim);
            }
        };
        private final DrwPool<Effect> splatterPool = new DrwPool<Effect>() {
            @Override
            protected Effect newObject() {
                return new Effect(Type.BLOOD_SPLATTER,Assets.instance.assetRipples.anim);
            }
        };
        private final DrwPool<Effect> waterRipplePool = new DrwPool<Effect>() {
            @Override
            protected Effect newObject() {
                return new Effect(Type.WATER_RIPPLES,Assets.instance.assetRipples.anim);
            }
        };
        private final DrwPool<Effect> knightExplodePool = new DrwPool<Effect>() {
            @Override
            protected Effect newObject() {
                return new Effect(Type.KNIGHT_EXPLODE,Assets.instance.assetKnight.knight_explode);
            }
        };
    }
}
