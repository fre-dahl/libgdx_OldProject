package ui.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button extends Container{

    protected TextureRegion icon;
    protected GridPoint2 gridPosition;

    public Button(Vector2 position, int margin, int border, int padding, int scale) {
        super(position, margin, border, padding, scale);
    }

    public Button(Vector2 position, int width, int height, int margin, int border, int padding, int scale) {
        super(position, width, height, margin, border, padding, scale);
    }

    public Button(int width, int height, int margin, int border, int padding, int scale) {
        super(new Vector2(0,0), width, height, margin, border, padding, scale);
    }

    public Button(TextureRegion icon, Vector2 position, int margin, int border, int padding, int scale) {
        super(position, margin, border, padding, scale);
        this.icon = icon;
        contentWidth = icon.getRegionWidth() * scale;
        contentHeight = icon.getRegionHeight() * scale;
        width = contentWidth + this.margin*2 + this.border*2 + this.padding*2;
        height = contentHeight + this.margin*2 + this.border*2 + this.padding*2;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void selected() {

    }

    @Override
    public void deSelected() {

    }

    @Override
    public void hovered() {

    }

    @Override
    public void deHovered() {

    }

    @Override
    public Rectangle getBox() {
        return null;
    }
}
