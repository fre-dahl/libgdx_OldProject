package ui.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ui.UI.Selectable;

public abstract class Container implements Selectable {

    protected Rectangle scope;
    protected Vector2 position;

    protected int scale;
    protected int width;
    protected int height;

    protected int margin;
    protected int border;
    protected int padding;
    protected int contentWidth;
    protected int contentHeight;

    public Container(Vector2 position, int margin, int border, int padding, int scale) {
        this.padding = padding * scale;
        this.border = border * scale;
        this.margin = margin * scale;
        this.position = position;
        this.scale = scale;
    }

    public Container(Vector2 position, int width, int height, int margin, int border, int padding, int scale) {
        this.padding = padding * scale;
        this.border = border * scale;
        this.margin = margin * scale;
        this.height = height * scale;
        this.width = width * scale;
        this.position = position;
        this.scale = scale;

        contentWidth = this.width - this.margin*2 + this.border*2 + this.padding*2;
        contentHeight = this.height - this.margin*2 + this.border*2 + this.padding*2;
    }

    public abstract void draw(SpriteBatch batch);

}
