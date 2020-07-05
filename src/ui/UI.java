package ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import input.Mouse.MouseEventListener;

public abstract class UI implements MouseEventListener {

    public void l_highlightBox_S(Rectangle box) {}
    public void l_highlightBox_W(Rectangle box) {}
    public void r_highlightBox_S(Rectangle box) {}
    public void r_highlightBox_W(Rectangle box) {}
    public void l_selectBox_S(Rectangle box) {}
    public void l_selectBox_W(Rectangle box) {}
    public void r_selectBox_S(Rectangle box) {}
    public void r_selectBox_W(Rectangle box) {}
    public void rightclick_S(Vector2 pos) {}
    public void rightclick_W(Vector2 pos) {}
    public void leftclick_S(Vector2 pos) {}
    public void leftclick_W(Vector2 pos) {}
    public void hover_S(Vector2 pos) {}
    public void hover_W(Vector2 pos) {}

    public interface Selectable {
        void selected();
        void hovered();
    }
}
