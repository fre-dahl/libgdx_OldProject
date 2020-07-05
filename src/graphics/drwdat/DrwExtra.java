package graphics.drwdat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import enums.Enums.COLORATION;

public class DrwExtra extends DrwRegion{
    // Don't overuse. Can be expensive

    private Color tint;
    protected boolean flip;     // Y-flip

    public DrwExtra(TextureRegion region, float x, float y, float w, float h, byte z, boolean flip) {
        super(region, x, y, w, h, z);
        tint = new Color(1f,1f,1f,1f);
        this.flip = flip;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (render) {
            if (regionIsSet) {
                if (!isSectionSet()) {
                    batch.setColor(tint);
                    if (flip){
                        region.flip(false, true);
                        batch.draw(region,x,y,w,h);
                        region.flip(false, true);
                    }
                    else {
                        batch.draw(region,x,y,w,h);
                    }
                    batch.setColor(COLORATION.DEFAULT.getColoring());
                }
                else  {
                    if (getSection().currentlyInView()) {
                        batch.setColor(tint);
                        if (flip){
                            region.flip(false, true);
                            batch.draw(region,x,y,w,h);
                            region.flip(false, true);
                        }
                        else batch.draw(region,x,y,w,h);
                        batch.setColor(COLORATION.DEFAULT.getColoring());
                    }
                }
            }
        }
    }

    public void setTint(Color t){tint = t;}

    public void setTint(float r, float g, float b, float a){
        tint.set(r,g,b,a);
    }
    public float getAlpha() {return tint.a;}

    public void setAlpha(float alpha) {tint.a = alpha;}

    public void adjustAlpha(float amount) { tint.a+=amount; }



}
