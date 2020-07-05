package components.weather;

import graphics.Assets;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import components.GameWorld;
import graphics.drwdat.abstr.DrwPool;
import main.Settings;

public class Weather implements Disposable {
    public CloudPool cloudPool;
    public Array<Cloud> clouds;
    public Wind wind;

    public Weather() {
        clouds = new Array<>();
        cloudPool = new CloudPool();
        wind = new Wind(25, Wind.Direction.ENE);
        for (int i = 0; i < 75 ; i++) spawnCloud();
    }

    public void update(float dt) {
        wind.update(dt);
        for (Cloud cloud : clouds) {
            cloud.update(dt);
        }
    }

    public void spawnCloud() { clouds.add(cloudPool.obtain()); }

    @Override
    public void dispose() { cloudPool.clear(); }


    public class CloudPool extends DrwPool<Cloud> {

        TextureRegion region;
        int minY = (int)GameWorld.bounds.y;
        int maxY = (int)GameWorld.bounds.height;
        int minX = (int)GameWorld.bounds.x;
        int maxX = (int)GameWorld.bounds.width;

        @Override
        protected Cloud newObject() {
            region = Assets.instance.assetClouds.getCloud();
            int y = MathUtils.random(minY,maxY)-region.getRegionHeight()*Settings.SCALE;
            int x = MathUtils.random(minX,maxX)-region.getRegionWidth()*Settings.SCALE;
            return new Cloud(Weather.this, region, x, y);
        }
    }
}
