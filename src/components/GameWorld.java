package components;

import camera.Cam;
import camera.FocusPoint;
import com.badlogic.gdx.math.Rectangle;
import assets.Assets;
import com.badlogic.gdx.utils.Disposable;
import components.effects.Effects;
import components.weather.Weather;
import managers.DrwHandler;
import tilecoding.process.Setup;


public class GameWorld implements Disposable {

    Biome biome;
    TileMap map;
    Weather weather;

    public static Rectangle bounds;

    public void newArea() {
        DrwHandler.instance.clear();
        if (weather!=null) weather.dispose();
        DrwHandler.instance.set(1,true);
        DrwHandler.instance.set(2,true);
        DrwHandler.instance.set(3,true);
        DrwHandler.instance.set(4,true);
        DrwHandler.instance.set(5,true);
        DrwHandler.instance.set(6,true);
        biome = new Biome(Biome.BIOME.TUNDRA);
        Assets.instance.loadBiome(biome);
        map = Setup.init(800,800, biome);
        bounds = map.boundary;
        weather = new Weather();
        Cam.instance.reset();
        FocusPoint.position.set(map.getCentre());
        Cam.instance.lockOnTarget(FocusPoint.position);
        Cam.instance.zoom(1);
    }


    public void update(float dt) {
        map.rendercheck();
        weather.update(dt);
        Effects.instance.update(dt);
    }


    @Override
    public void dispose() {
        Effects.instance.dispose();
        weather.dispose();
        DrwHandler.instance.clear();
    }
}
