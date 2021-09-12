package ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import components.entity.Entity.Disposition;
import input.Mouse.MouseEventListener;

public abstract class UI implements MouseEventListener {

    // S happens before W
    public void l_highlightBox_S(Rectangle box) {}
    public void l_highlightBox_W(Rectangle box) {}
    public void r_highlightBox_S(Rectangle box) {}
    public void r_highlightBox_W(Rectangle box) {}
    public void l_selectBox_S(Rectangle box) {}
    public void l_selectBox_W(Rectangle box) {}
    public void r_selectBox_S(Rectangle box) {}
    public void r_selectBox_W(Rectangle box) {}
    public void l_dragRelease_S(Vector2 vec) {}
    public void l_dragRelease_W(Vector2 vec) {}
    public void r_dragRelease_S(Vector2 vec) {}
    public void r_dragRelease_W(Vector2 vec) {}
    public void l_dragVector_S(Vector2 vec) {}
    public void l_dragVector_W(Vector2 vec) {}
    public void r_dragVector_S(Vector2 vec) {}
    public void r_dragVector_W(Vector2 vec) {}
    public void rightclick_S(Vector2 pos) {}
    public void rightclick_W(Vector2 pos) {}
    public void leftclick_S(Vector2 pos) {}
    public void leftclick_W(Vector2 pos) {}
    public void hover_S(Vector2 pos) {}
    public void hover_W(Vector2 pos) {}

    public interface Selectable {
        void selected();
        void deSelected();
        void hovered();
        void deHovered();
        Rectangle getBox();
    }

    public interface SelectableUnit<Entity> extends Selectable {
        // Might want to merge Interactable in Selectable.
        // That means changing the worldobject target in Action-class to selectable
        // this is more logical.
        // Action Commands
        Disposition getDisposition();
        void moveTo(Vector2 pos);
        void moveTo(Vector2 pos, boolean clearQueue);
        //void useAbilitySelf(Ability ability)
        //void useAbilityTarget(Ability ability, WorldObject target)
        //void useAbility
        // getInventory
        // getAbilities
    }

    public interface SelectableHUD extends Selectable {
        void move(Vector2 pos);
        void resize(Vector2 pos);
    }
}
