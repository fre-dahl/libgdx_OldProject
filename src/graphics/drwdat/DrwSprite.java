package graphics.drwdat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drwdat.abstr.DrwDat;

public class DrwSprite extends DrwDat {

    private Sprite sprite;

    public DrwSprite(Sprite sprite, float x, float y, byte z){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.z = z;
        add();
    }

    //todo: Adjust for sprite

    @Override
    public void draw(SpriteBatch batch) {
        if (render) {
            if (sprite != null) sprite.draw(batch);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
