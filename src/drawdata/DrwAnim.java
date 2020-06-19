package drawdata;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import drawdata.drwabstract.DrwCulling;

public class DrwAnim extends DrwCulling {

    private Animation<TextureRegion> anim;

    public DrwAnim() {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    public void setAnim(Animation<TextureRegion> anim) {
        this.anim = anim;
    }

    public void updateAnim() {
    }
}
