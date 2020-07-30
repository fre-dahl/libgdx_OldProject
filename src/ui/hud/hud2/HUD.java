package ui.hud.hud2;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import main.Settings;
import ui.UI.SelectableUnit;

public class HUD {

    public static int activeHudElement;
    public static final int NONE = 0;
    public static final int ITEM = 1;
    public static final int ABILITY = 2;

    public static int displaying;
    public static final int NULL = 0;
    public static final int FRIENDLY = 1;
    public static final int NEUTRAL = 2;
    public static final int HOSTILE = 3;
    public static final int GROUP_FRIENDLY = 4;

    private Ability activeAbility;
    private SelectableUnit activeUnit;
    private Rectangle mainContainer; //tmp
    private Array<SelectableUnit> activeUnits;
    private Array<Rectangle> onScreenHudElements; //tmp

    private GameUI userInterface;

    public HUD(GameUI userInterface) {
        this.userInterface = userInterface;
        onScreenHudElements = new Array<>();
        mainContainer = new Rectangle(0,0, Settings.SCREEN_W,110);
        onScreenHudElements.add(mainContainer);
        activeHudElement = NONE;
        displaying = NULL;
    }

    public boolean mouseOnHud(Vector2 pos) {
        for (Rectangle container : onScreenHudElements) {
            if (container.contains(pos)) {
                mainContainer = container;
                return true;
            }
        }
        mainContainer = null;
        return false;
    }

    public void setActiveUnit(SelectableUnit unit){
        // here we can set/reset all info and abilities and unit can be null
        activeUnit = unit;
        if (activeUnit == null) displaying = NULL;
        else {
            switch (activeUnit.getDisposition()) {
                case FRIENDLY: displaying = FRIENDLY; break;
                case NEUTRAL: displaying = NEUTRAL; break;
                case HOSTILE: displaying = HOSTILE; break;
            }
        } // tmp
        System.out.println("HUD display mode (displaying) : " + displaying);
    }

    public void setActiveUnits(Array<SelectableUnit> units) {
        // here we can set/reset all info and abilities
        // tests for size/null in unitManager. If this get's called units != null
        activeUnits = units;
        activeUnit = null;
        displaying = GROUP_FRIENDLY;
        System.out.println("HUD display mode (displaying) : " + displaying);
    }

    public void hover(Vector2 pos){
        //hoveredMainContainer.hover(pos);
    }

    public void leftClick(Vector2 pos) {
        //hoveredMainContainer.leftClick(pos);
    }
}
