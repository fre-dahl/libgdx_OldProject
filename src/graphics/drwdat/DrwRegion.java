package graphics.drwdat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import graphics.drwdat.abstr.DrwCulling;

public class DrwRegion extends DrwCulling {

    public static int numDraw = 0;
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
                    //numDraw++;
                }
                else  {
                    if (getSection().currentlyInView()) {
                        //numDraw++;
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
