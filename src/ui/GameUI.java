package ui;

import camera.FocusPoint;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameUI extends UI{

    private boolean mouseOnHudElement;
    public Array<SelectableUnit> unitsInView;
    private Array<Selectable> buidings;
    private Array<Selectable> terrainView;

    private Array<SelectableUnit> selectedUnits;
    private SelectableUnit hovered;

    public GameUI() {
        unitsInView = new Array<>();
        selectedUnits = new Array<>();
    }

    @Override
    public void hover_S(Vector2 pos) {
        /*
        if (hud.containsMouse(pos)) {
            mouseOnHudElement = true;
            hud.getActiveContainer(pos).hovered();
        }
        else mouseOnHudElement = false;

         */
    }

    @Override
    public void hover_W(Vector2 pos) {
        if (hovered != null) {
            if (!hovered.getBox().contains(pos)) {
                if (!selectedUnits.contains(hovered,true)) {
                    hovered.deHovered();
                }
                hovered = null;
            }
        }
        else {
            for (SelectableUnit unit : unitsInView) {
                if (unit.getBox().contains(pos)){
                    hovered = unit;
                    unit.hovered();
                    break;
                }
            }
        }
    }

    @Override
    public void leftclick_W(Vector2 pos) {
        for (SelectableUnit unit : selectedUnits) {
            unit.deSelected();
        }
        selectedUnits.clear();
        if (hovered != null) {
            hovered.selected();
            selectedUnits.add(hovered);
        }
    }

    @Override
    public void rightclick_W(Vector2 pos) {
        if (selectedUnits.isEmpty()) FocusPoint.position.set(pos);
        else {
            for (SelectableUnit unit : selectedUnits) {
                unit.moveTo(pos);
                //unit.useAbility(unit.activeAbility(), pos)
                //unit.useAbility(ability, target); check in unit method if ability is legal
            }
        }
    }

    @Override
    public void l_highlightBox_W(Rectangle box) {

    }

    @Override
    public void l_selectBox_W(Rectangle box) {
        for (SelectableUnit unit : selectedUnits) {
            unit.deSelected();
        }
        selectedUnits.clear();
        for (SelectableUnit unit : unitsInView) {
            if (box.overlaps(unit.getBox())) {
                unit.selected();
                selectedUnits.add(unit);
            }
        }

    }
}
