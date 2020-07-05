package graphics.drwdat.abstr;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import graphics.DrwHandler;

public abstract class DrwDat implements Comparable<DrwDat>, Disposable {

    protected boolean render;   // draw
    protected float x;          // x-pos
    protected float y;          // y-pos
    protected float w;          // width
    protected float h;          // height
    protected byte  z;          // layer

    public abstract void draw(SpriteBatch batch);

    public void add() {
        render = true;
        DrwHandler.instance.add(this);
    }

    public void dispose() {
        render = false; // for DrwPoolable
        DrwHandler.instance.remove(this);
    }

    public void translateX(float amount) {x+=amount;}

    public void translateY(float amount) {y+=amount;}

    public void scale(float amount) {
        if(amount == 0 || amount == 1) return;
        float c = x + (w/2);
        w *= amount;
        x = c - (w/2);
        c = y + (h/2);
        h *= amount;
        y = c - (h/2);
    }

    public void scale(float amount, float cX, float cY) {
        if(amount == 0 || amount == 1) return;
        w *= amount;
        x = cX - (w/2);
        h *= amount;
        y = cY - (h/2);
    }

    public void transfer(int z) {
        if (z != this.z){
            DrwHandler.instance.transfer(this, z);
            this.z = (byte)z; }
    }

    public void setRender(boolean r) { render = r; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setPosition(float x, float y) {this.x = x; this.y = y;}

    public void translate(float x, float y) {this.x += x; this.y += y;}

    public boolean rendering() { return render; }

    public float getX() { return x; }

    public float getY() { return y; }

    public byte getZ() { return z; }

    public float getW() { return w; }

    public float getH() { return h; }

    public int compareTo(DrwDat d) {
        return (d == null) ? 0: Float.compare(d.getY(), y);
    } //todo Only actively compare entities and only in view

}
