package camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import main.Settings;

public class Cam {

    public static Cam instance = new Cam();

    private boolean moved;
    private boolean zoomed;

    private Vector2 direction;
    private Vector2 tmpVector2;
    private Vector3 tmpVector3;

    private static Rectangle worldWindow;
    private static Rectangle cullingWindow;
    private static OrthographicCamera camera;

    private Cam() {
        direction = new Vector2();
        tmpVector2 = new Vector2();
        tmpVector3 = new Vector3();
        worldWindow = new Rectangle();
        cullingWindow = new Rectangle();
        camera = new OrthographicCamera(Settings.SCREEN_W, Settings.SCREEN_H);
    }

    public void update(float dt) {

        if (moved || zoomed) {
            updateWorldWindow();
            updateCullingWindow();
            camera.update();
            zoomed = false;
        }
    }

    public void zoom(int amount) {
        camera.zoom += amount;
        camera.update();
        zoomed = true;
    }


    public void lockOnTarget(Vector2 target) {
        setDirection(target);
        tmpVector3.set(camera.position);
        tmpVector3.x = target.x;
        tmpVector3.y = target.y;
        camera.position.set(tmpVector3);
    }

    public void lerpToTarget(Vector2 target) {
        // a + (b - a) * lerp factor
        setDirection(target);
        tmpVector3.set(camera.position);
        tmpVector3.x = camera.position.x + (target.x - camera.position.x) * .1f;
        tmpVector3.y = camera.position.y + (target.y - camera.position.y) * .1f;
        camera.position.set(tmpVector3);
    }

    public void lockAverageBetweenTargets(Vector2 targetA, Vector2 targetB) {
        tmpVector3.set(camera.position);
        float avgX = (targetA.x + targetB.x) / 2;
        float avgY = (targetA.y + targetB.y) / 2;
        tmpVector2.set(avgX,avgY);
        setDirection(tmpVector2);
        tmpVector3.set(avgX,avgY,0);
        camera.position.set(tmpVector3);
    }

    public void lerpAverageBetweenTargets(Vector2 targetA, Vector2 targetB) {
        float avgX = (targetA.x + targetB.x) / 2;
        float avgY = (targetA.y + targetB.y) / 2;
        tmpVector2.set(avgX,avgY);
        setDirection(tmpVector2);
        tmpVector3.set(camera.position);
        tmpVector3.x = camera.position.x + (avgX - camera.position.x) * .1f;
        tmpVector3.y = camera.position.y + (avgY - camera.position.y) * .1f;
        camera.position.set(tmpVector3);
    }

    public boolean searchFocalPoints(Array<Vector2> focalPoints, Vector2 target, float threshold) {
        for(Vector2 point : focalPoints) {
            if(target.dst(point) < threshold) {
                float newZoom = (target.dst(point) / threshold) + .2f;
                camera.zoom = camera.zoom + ((newZoom > 1? 1 : newZoom) - camera.zoom) * .1f;
                lerpToTarget(point);
                return true;
            }
        }
        return false;
    }

    public void shake(Vector2 displacement, float strength) {
        tmpVector3.set(camera.position);
        tmpVector3.x += displacement.x * strength;
        tmpVector3.y += displacement.y * strength;
        camera.position.set(tmpVector3);
    }

    public Vector2 adjustToWorldCords(Vector2 mousePosition) {
        tmpVector3.set(mousePosition.x, mousePosition.y,0);
        camera.unproject(tmpVector3);
        tmpVector2.x = tmpVector3.x;
        tmpVector2.y = tmpVector3.y;
        return tmpVector2;
    }


    private void setDirection(Vector2 target) {
        // called in various movement methods like lerp
        int targetX = (int)target.x;
        int targetY = (int)target.y;
        int oldX = (int)camera.position.x;
        int oldY = (int)camera.position.y;
        direction.set(targetX-oldX,targetY-oldY);
        moved = direction.len() > 1;
    }

    private void updateWorldWindow() {
        tmpVector3.set(0, Gdx.graphics.getHeight(),0);
        tmpVector3.set(camera.unproject(tmpVector3));
        tmpVector2.set(tmpVector3.x,tmpVector3.y);
        float w = Gdx.graphics.getWidth() * camera.zoom;
        float h = Gdx.graphics.getHeight() * camera.zoom;
        worldWindow.set(tmpVector2.x, tmpVector2.y, w, h);
        cullingWindow.set(worldWindow);
    }

    private void updateCullingWindow() {
        float x = cullingWindow.x;
        float y = cullingWindow.y;
        float w = cullingWindow.width;
        float h = cullingWindow.height;
        float xOff = Math.abs(direction.x);
        float yOff = Math.abs(direction.y);
        if (direction.x > 0) { w += xOff; }
        if (direction.y > 0) { h += yOff; }
        if (direction.x < 0) { w += xOff; x -= xOff; }
        if (direction.y < 0) { h += yOff; y -= yOff; }
        cullingWindow.set(x,y,w,h);
    }

    public void reset() {
        camera.zoom = 1;
        camera.position.x = 0f;
        camera.position.y = 0f;
        camera.update();
    }

    public OrthographicCamera getCamera() {return camera;}

    public Rectangle getWorldWindow() { return worldWindow; }

    public Rectangle getCullingWindow() { return cullingWindow; }
}
