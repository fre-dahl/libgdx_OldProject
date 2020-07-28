package ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Menu extends Container {

    protected int maxContentWidth;
    protected int minContentWidth;
    protected int maxContentHeight;
    protected int minContentHeight;

    protected Sprite background;
    protected Sprite border;

    protected Color backgroundColor;
    protected Color borderColor;

    public Menu(Vector2 position, int margin, int border, int padding, int scale) {
        super(position, margin, border, padding, scale);
    }

    public Menu(Vector2 position, int width, int height, int margin, int border, int padding, int scale) {
        super(position, width, height, margin, border, padding, scale);
    }

    public interface Dynamic {

        void resize(Vector2 vec);
        void move(Vector2 vec);
    }
}
