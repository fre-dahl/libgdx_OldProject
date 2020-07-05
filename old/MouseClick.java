package input;

import graphics.assets.Assets;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import graphics.drwdat.DrwExtra;
import main.Settings;

public class MouseClick {

    public Animation<TextureRegion> clickAnim;
    public DrwExtra region;
    public Vector2 pos;
    boolean playAnim;
    float timer;

    public MouseClick() {
        pos = new Vector2();
        clickAnim = Assets.instance.assetRipples.anim;
        region = new DrwExtra(clickAnim.getKeyFrame(0),0,0,32* Settings.SCALE,32*Settings.SCALE,(byte) 2,false);
        region.setRender(false);
    }

    public void update(float dt) {
        if (Mouse2.rightbuttonDown) {
            pos.set(Mouse2.rightClickWorldPosition.x-32, Mouse2.rightClickWorldPosition.y-32);
            region.setPosition(pos.x,pos.y);
            playAnim = true;
            timer = 0;
            region.setRender(true);
        }
        if (playAnim) playAnim(dt);
    }

    private void playAnim(float dt) {
        timer+=dt;
        region.setRegion(clickAnim.getKeyFrame(timer));
        if (clickAnim.getKeyFrameIndex((int)timer) == 10) {
            playAnim = false;
            timer = 0;
            region.setRender(false);
        }
    }


}
