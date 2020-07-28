package components;

import camera.Cam;
import camera.FocusPoint;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import components.entity.Entity;
import components.entity.Knight;
import components.map.TileMap;
import graphics.Assets;
import com.badlogic.gdx.utils.Disposable;
import components.effects.Effects;
import components.weather.Weather;
import input.Mouse;
import main.Settings;
import graphics.DrwHandler;
import components.map.tilecoding.process.Setup;
import ui.GameUI;


public class GameWorld implements Disposable {

    private Array<Knight> knights;
    private Biome biome;
    public TileMap map;
    private Weather weather;
    public GameUI gameUI;

    public static Rectangle bounds;

    public void newArea() {

        Mouse.instance.setMouseEventListener(gameUI = new GameUI());
        DrwHandler.instance.clear();
        if (weather!=null) weather.dispose();
        if (knights!=null) {
            for (Knight knight : knights) {
                knight.dispose();
            }
            knights.clear();
        }
        DrwHandler.instance.set(1,true);
        DrwHandler.instance.set(2,true);
        DrwHandler.instance.set(3,true);
        DrwHandler.instance.set(4,true);
        DrwHandler.instance.set(5,true);
        DrwHandler.instance.set(6,true);
        DrwHandler.instance.set(7,true);
        DrwHandler.instance.sort(5,true);
        biome = new Biome(Biome.BIOME.TUNDRA);
        Assets.instance.loadBiome(biome);
        map = Setup.init(800,800, biome);
        bounds = map.boundary;
        weather = new Weather();
        Cam.instance.reset();
        FocusPoint.position.set(map.getCentre());
        knights = new Array<>();
        Effects.instance.newInstance();
        
        for (int i = 0; i < 50; i++) {
            Knight knight = new Knight(Entity.Type.KNIGHT, this, FocusPoint.position.x + i*32*Settings.SCALE,FocusPoint.position.y);
            knights.add(knight);
        }

        Cam.instance.lockOnTarget(FocusPoint.position);
        Cam.instance.SetZoom(Settings.SCALE);
    }


    public void update(float dt) {

        for (Knight knight : knights) {
            knight.update(dt);
        }
        map.rendercheck();
        weather.update(dt);
        Effects.instance.update(dt);
    }


    @Override
    public void dispose() {
        weather.dispose();
        Effects.instance.dispose();
        DrwHandler.instance.clear();
        for (Knight knight : knights) {
            knight.dispose();
        }
    }
}
