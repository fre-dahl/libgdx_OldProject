package ui.hud.hud2;

import com.badlogic.gdx.math.Vector2;
import ui.UI;

public class GameUI2 extends UI {

    public static boolean mouseOnHud;

    private HUD hud;
    private UnitManager unitManager;

    public GameUI2() {
        unitManager = new UnitManager(this);
        hud = new HUD(this);
    }

    public HUD getHud() {
        return hud;
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    @Override
    public void hover_S(Vector2 pos) {
        mouseOnHud = hud.mouseOnHud(pos);
        if (mouseOnHud) hud.hover(pos);
    }

    @Override
    public void hover_W(Vector2 pos) {
        if (!mouseOnHud) unitManager.hover(pos);
    }

    @Override
    public void leftclick_S(Vector2 pos) {
        if (mouseOnHud) hud.leftClick(pos);
    }

    @Override
    public void leftclick_W(Vector2 pos) {
        if (!mouseOnHud) unitManager.leftClick(pos);
    }
}
