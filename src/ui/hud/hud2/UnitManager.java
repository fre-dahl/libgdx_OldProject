package ui.hud.hud2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ui.UI.SelectableUnit;

public class UnitManager {

    public static final int NONE = 0;

    public static final int SINGLE_UNIT = 1;
    public static final int UNIT_GROUP = 2;
    public static int activeUnits;

    public static final int FRIENDLY = 1;
    public static final int NEUTRAL = 2;
    public static final int HOSTILE = 3;
    public static int hoveredUnitDisposition;

    private Array<SelectableUnit> unitsInView;
    private Array<SelectableUnit> selectedUnits;
    private SelectableUnit hoveredUnit;

    private GameUI2 userInterface;

    public UnitManager(GameUI2 userInterface) {
        this.userInterface = userInterface;
        selectedUnits = new Array<>();
        unitsInView = new Array<>();
    }

    public Array<SelectableUnit> getUnitsInView() {
        return unitsInView;
    }

    public Array<SelectableUnit> getSelectedUnits() {
        return selectedUnits;
    }

    public void hover(Vector2 pos) {
        if (hoveredUnit != null) {
            if (!hoveredUnit.getBox().contains(pos)) {
                if (!selectedUnits.contains(hoveredUnit,true)) {
                    hoveredUnit.deHovered();
                }
                hoveredUnit = null;
            }
        }
        else {
            for (SelectableUnit unit : unitsInView) {
                if (unit.getBox().contains(pos)){
                    hoveredUnit = unit;
                    unit.hovered();
                    break;
                }
            }
        }
    }

    public void leftClick(Vector2 pos) {

        // can change the order of the if statements
        if (activeUnits == UNIT_GROUP) {
            if (hoveredUnit == null) {
                for (SelectableUnit unit : selectedUnits) {
                    unit.deSelected();
                }
                selectedUnits.clear();
                activeUnits = NONE;
            }
            else {
                boolean hoveredUnitInGroup = false;
                for (SelectableUnit unit : selectedUnits) {
                    if (hoveredUnit != unit) unit.deSelected();
                    else hoveredUnitInGroup = true;
                }
                selectedUnits.clear();
                selectedUnits.add(hoveredUnit);
                userInterface.getHud().setActiveUnit(hoveredUnit); // set active unit
                if (!hoveredUnitInGroup) hoveredUnit.selected();
                activeUnits = SINGLE_UNIT;
            }
        }
        else if (activeUnits == SINGLE_UNIT) {
            // presupposes selected units size = 1
            if (hoveredUnit == null) {
                selectedUnits.first().deSelected();
                userInterface.getHud().setActiveUnit(null); // set active unit = null
                selectedUnits.clear();
                activeUnits = NONE;
            }
            else {
                if (hoveredUnit != selectedUnits.first()) {
                    selectedUnits.first().deSelected();
                    selectedUnits.clear();
                    selectedUnits.add(hoveredUnit);
                    userInterface.getHud().setActiveUnit(hoveredUnit); // set active unit
                    hoveredUnit.selected();
                }
            }
        }
        else { // NONE
            if (hoveredUnit != null) {
                selectedUnits.add(hoveredUnit);
                hoveredUnit.selected();
                userInterface.getHud().setActiveUnit(hoveredUnit); // set active unit
                activeUnits = SINGLE_UNIT;
            }
        }
    }

    public void rightClick(Vector2 pos) {

        // presupposes activeUnits == SINGLE_UNIT;
        // as hud abilities are only availbble for single units.
        // if activeAbility is self-targeting. It will activate then turn inactive
        // passive abilities cannot be active by definition. and will activate like self-targeting.

        // ALSO NEED a build mode for selecting enviromental objects. MAYBE

        // Right click:  Move to / Move / Attack hostile / Use Ability/ Interact with interactable or friendly.
        // toggling build with b? could change move to worldObject to harvest wo.
        //
        // Left click: Selecting. IE. show info, set activeUnit etc

        // If group is selected. The ability bar fills up with unit icons with healthbars.
        // and when either hovering the world unit or the icon of the unit. Further info is shown in the info bar
        // else the overall healt % + num units / overall moral and average level is shown.
        // selecting a unit icon, selects the unit.


    }


}
