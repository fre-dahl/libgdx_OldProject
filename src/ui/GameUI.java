package ui;

import com.badlogic.gdx.math.Rectangle;

public class GameUI extends UI{

    private HUD hud;

    public Rectangle box;
    public GameUI() {
        box = new Rectangle();
    }

    @Override
    public void l_highlightBox_W(Rectangle box) {
        this.box.set(box);
    }
}
