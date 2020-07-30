package ui.hud.hud2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ui.UI;

public abstract class Container implements UI.Selectable {

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

    public Container(int margin, int border, int padding, int scale) {
        this.padding = padding * scale;
        this.border = border * scale;
        this.margin = margin * scale;
        this.scale = scale;
    }

    public Container(int contentWidth, int contentHeight, int margin, int border, int padding, int scale) {
        this.contentHeight = contentHeight * scale;
        this.contentWidth = contentWidth * scale;
        this.padding = padding * scale;
        this.border = border * scale;
        this.margin = margin * scale;
        this.scale = scale;

        width = this.contentWidth + this.margin*2 + this.border*2 + this.padding*2;
        height = this.contentHeight + this.margin*2 + this.border*2 + this.padding*2;
    }

    /*
    public Container(int width, int height, int margin, int border, int padding, int scale) {
        this.padding = padding * scale;
        this.border = border * scale;
        this.margin = margin * scale;
        this.height = height * scale;
        this.width = width * scale;
        this.scale = scale;

        contentWidth = this.width - this.margin*2 + this.border*2 + this.padding*2;
        contentHeight = this.height - this.margin*2 + this.border*2 + this.padding*2;
    }

     */

    public abstract void draw(SpriteBatch batch);

}
