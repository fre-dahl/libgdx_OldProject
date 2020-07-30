package ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ButtonArea extends Container {

    private int numButtons;
    private int innerPadding; // selector area between buttons
    private int rows;
    private int cols;

    private Container parent;
    private Sprite selector;
    private Color selectorColor;
    private Array<Button> buttons;


    public ButtonArea(Vector2 position, Container parent, int rows, int cols, int bMargin, int bBorder, int bPadding, int bWidth, int bHeight, int innerPadding, int scale) {
        super(position, 0, 0, 0, scale);
        contentHeight = rows * bHeight * scale + (innerPadding * (rows - 1) * scale);
        contentWidth = cols * bWidth * scale + (innerPadding * (cols - 1) * scale);
        this.innerPadding = innerPadding * scale;
        buttons = new Array<>();
        this.parent = parent;
        this.rows = rows;
        this.cols = cols;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button button = new Button(bWidth,bHeight,bMargin,bBorder,bPadding,scale);
                button.position.set(position.x + col * button.width + col * innerPadding, position.y + row * button.height + row * innerPadding);
                button.gridPosition = new GridPoint2(col,row);
                buttons.add(button);
            }
        }
    }

    public void testfunc(){}

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
    public Rectangle getBox() { return scope; }

}
