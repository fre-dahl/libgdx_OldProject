package ui.hud.hud2;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ui.UI.SelectableUnit;

public class HUD {

    public static final int NONE = 0;
    public static final int ITEM = 1;
    public static final int ABILITY = 2;
    public static int activeHudElement;

    private Rectangle tempContainer;
    private Rectangle hoveredMainContainer;

    private Ability activeAbility;
    private SelectableUnit activeUnit;
    private Array<Rectangle> onScreenHudElements;

    private GameUI2 userInterface;

    public HUD(GameUI2 userInterface) {
        this.userInterface = userInterface;
    }

    public boolean mouseOnHud(Vector2 pos) {
        for (Rectangle container : onScreenHudElements) {
            if (container.contains(pos)) {
                hoveredMainContainer = container;
                return true;
            }
        }
        hoveredMainContainer = null;
        return false;
    }

    public void setActiveUnit(SelectableUnit unit){
        // here we can set/reset all info and abilities
        // depending on whether unit = null || !
        activeUnit = unit;
    }

    public void hover(Vector2 pos){
        //hoveredMainContainer.hover(pos);
    }

    public void leftClick(Vector2 pos) {
        //hoveredMainContainer.leftClick(pos);
    }
}
