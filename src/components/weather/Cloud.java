package components.weather;

import camera.Cam;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import components.GameWorld;
import drawdata.DrwExtra;
import drawdata.drwabstract.DrwPool.DrwPoolable;
import main.Settings;

public class Cloud implements DrwPoolable {


    private static final boolean spawnOnReset = true;
    private static final float fadeOutStart = 30;
    private static final float alphaTarget = 0.1f;
    private static final float alphaDelta = 0.02f;
    private static final byte reflectLayer = 2;
    private static final byte shadowtLayer = 6;
    private float fadeOutTimer = 30;
    private float velocityMod;
    private boolean fadeIn = true;
    private boolean fadeOut = false;
    private Rectangle boundary;
    private Weather weather;
    private DrwExtra cloudRegion;
    private Vector2 pos;



    public Cloud(Weather weather, TextureRegion region, float x, float y) {
        this.weather = weather;
        boundary = new Rectangle(x,y,region.getRegionWidth()*Settings.SCALE,region.getRegionHeight()*Settings.SCALE);
        cloudRegion = new DrwExtra(region,x,y,boundary.width,boundary.height, reflectLayer,true);
        pos = new Vector2(x,y);
        cloudRegion.setAlpha(0f);
        velocityMod = (float) (1+((Math.random()+0.01)/8));
    }


    public void update(float dt) {
        fadeOutTimer -= dt;
        if (fadeOutTimer < 0) {
            if (MathUtils.random(1) == 0) {
                fadeOut = true;
            }else fadeOutTimer = fadeOutStart;
        }
        pos.x += weather.wind.getVelocity().x * velocityMod * dt;
        pos.y += weather.wind.getVelocity().y * velocityMod * dt;
        cloudRegion.setPosition(pos.x,pos.y);
        boundary.setPosition(pos.x,pos.y);
        if (Cam.instance.getCullingWindow().overlaps(boundary)) cloudRegion.setRender(true);
        else cloudRegion.setRender(false);
        if (!GameWorld.bounds.overlaps(boundary)) {
            weather.clouds.removeValue(this,true);
            weather.cloudPool.free(this);
        }
        if (fadeIn)fadeIn(dt);
        if (fadeOut)fadeOut(dt);
    }

    private void fadeIn(float dt) {
        if (cloudRegion.getAlpha() < alphaTarget) {
            cloudRegion.adjustAlpha(alphaDelta *dt);
        }
        else {
            cloudRegion.setAlpha(alphaTarget);
            fadeIn = false;
        }
    }

    private void fadeOut(float dt) {
        if (cloudRegion.getAlpha() > 0) {
            cloudRegion.adjustAlpha(-alphaDelta *dt);
        }
        else {
            weather.clouds.removeValue(this,true);
            weather.cloudPool.free(this);
            fadeOut = false;
        }
    }

    @Override
    public void reset() {
        int minY = (int)GameWorld.bounds.y;
        int maxY = (int)GameWorld.bounds.height;
        int minX = (int)GameWorld.bounds.x;
        int maxX = (int)GameWorld.bounds.width;
        int y = (int)(MathUtils.random(minY,maxY)- cloudRegion.getH());
        int x = (int)(MathUtils.random(minX,maxX)- cloudRegion.getW());
        pos.set(x,y);
        cloudRegion.dispose();
        if (spawnOnReset) weather.spawnCloud();
    }

    @Override
    public void onClear() {
        cloudRegion.dispose();
    }

    @Override
    public void onSpawm() {
        fadeIn = true;
        fadeOutTimer = fadeOutStart;
        cloudRegion.setAlpha(0f);
        cloudRegion.add();
    }

}
