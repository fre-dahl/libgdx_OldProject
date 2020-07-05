package graphics.drwdat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import graphics.drwdat.abstr.DrwCulling;

public class DrwRegion extends DrwCulling {

    protected TextureRegion region;
    boolean regionIsSet;

    public DrwRegion(TextureRegion region, float x, float y, float w, float h, byte z){
        this.region = region;
        regionIsSet = true;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.z = z;
        add();
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (render) {
            if (regionIsSet) {
                if (!isSectionSet()) {
                    batch.draw(region,x,y,w,h);
                }
                else  {
                    if (getSection().currentlyInView()) {
                        batch.draw(region,x,y,w,h);
                    }
                }
            }
        }
    }


    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {return region; }


}
