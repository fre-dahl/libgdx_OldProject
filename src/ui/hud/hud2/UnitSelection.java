package ui.hud.hud2;

import camera.FocusPoint;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import components.entity.Entity.Disposition;
import input.GameKeys;
import ui.UI.SelectableUnit;
import java.util.HashMap;
import java.util.Map;

public class UnitSelection {

    public static int activeUnits;
    public static final int NONE = 0;
    public static final int SINGLE_UNIT = 1;
    public static final int UNIT_GROUP = 2;

    private Map<Disposition,Array<SelectableUnit>> unitsInView;
    private Array<SelectableUnit> selectedUnits;
    private SelectableUnit hoveredUnit;

    private GameUI userInterface;

    // remember to change =! -> !equals()

    public UnitSelection(GameUI userInterface) {
        this.userInterface = userInterface;
        unitsInView = new HashMap<>();
        unitsInView.put(Disposition.FRIENDLY,new Array<>());
        unitsInView.put(Disposition.NEUTRAL,new Array<>());
        unitsInView.put(Disposition.HOSTILE,new Array<>());
        selectedUnits = new Array<>(); // Only "friendly" can have multiple selected
        activeUnits = NONE; // just for clarity
    }

    public Array<SelectableUnit> getSelectedUnits() {
        return selectedUnits;
    }

    public void addUnit(SelectableUnit unit) {
        // i don't want to check for null every time
        unitsInView.get(unit.getDisposition()).add(unit);
    }

    public void removeUnit(SelectableUnit unit) {
        // i don't want to check for null every time
        unitsInView.get(unit.getDisposition()).removeValue(unit,true);
    }

    public void hover(Vector2 pos) {
        if (hoveredUnit != null) {
            if (!hoveredUnit.getBox().contains(pos)) {
                hoveredUnit.deHovered();
                hoveredUnit = null;
            }
        }
        else {
            for (SelectableUnit unit : unitsInView.get(Disposition.FRIENDLY)) {
                if (unit.getBox().contains(pos)) {
                    hoveredUnit = unit;
                    unit.hovered();
                    return;
                }
            }
            for (SelectableUnit unit : unitsInView.get(Disposition.NEUTRAL)) {
                if (unit.getBox().contains(pos)) {
                    hoveredUnit = unit;
                    unit.hovered();
                    return;
                }
            }
            for (SelectableUnit unit : unitsInView.get(Disposition.HOSTILE)) {
                if (unit.getBox().contains(pos)) {
                    hoveredUnit = unit;
                    unit.hovered();
                    return;
                }
            }
        }
    }

    public void leftClick(Vector2 pos) {

        // can change the order of the if statements to NONE first
        if (activeUnits == UNIT_GROUP) {
            if (hoveredUnit == null) {
                for (SelectableUnit unit : selectedUnits) {
                    unit.deSelected();
                }
                userInterface.getHud().setActiveUnit(null); // set active unit = null
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

        if (activeUnits == NONE) FocusPoint.position.set(pos);

        else if (activeUnits == SINGLE_UNIT) {
            SelectableUnit unit = selectedUnits.first();
            if (unit.getDisposition() == Disposition.FRIENDLY) {
                boolean clearQueue = !GameKeys.isDown(GameKeys.CONTROL);
                unit.moveTo(pos,clearQueue);
            }
        }

        else {
            for (SelectableUnit unit : selectedUnits) {
                boolean clearQueue = !GameKeys.isDown(GameKeys.CONTROL);
                unit.moveTo(pos,clearQueue);
                //unit.useAbility(unit.activeAbility(), pos)
                //unit.useAbility(ability, target); check in unit method if ability is legal
            }
        }


    }

    public void selectBox(Rectangle box) {
        // selected units should be either 0 if selectbox click was on empty space
        // or 1 if clicked on Unit. But this unit could be moving. but never > 1
        if (activeUnits != NONE) {
            for (SelectableUnit unit : selectedUnits) unit.deSelected();
            selectedUnits.clear();
        }// selected units should be clear atp
        int unitsSelected = 0;
        for (SelectableUnit unit : unitsInView.get(Disposition.FRIENDLY)) {
            if (box.overlaps(unit.getBox())) {
                unit.selected();
                unitsSelected++;
                selectedUnits.add(unit);
            }
        }
        if (unitsSelected == 0) {
            if (activeUnits != NONE) {
                userInterface.getHud().setActiveUnit(null);
                activeUnits = NONE;
            }
        }
        else if (unitsSelected == 1) {
            // Gets checked in HUD if friendly, but that is redundant. Could fix this later.
            userInterface.getHud().setActiveUnit(selectedUnits.first());
            activeUnits = SINGLE_UNIT;
        }
        else { // unitsSelected > 1
            userInterface.getHud().setActiveUnits(selectedUnits);
            activeUnits = UNIT_GROUP;
        }
    }

    // TEMP

    public void removeUnit2(SelectableUnit unit) {
        if (activeUnits == SINGLE_UNIT) {
            activeUnits = NONE;
            selectedUnits.removeValue(unit,true);
        }
    }

}
