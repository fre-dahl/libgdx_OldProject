package input;

import camera.Cam;
import camera.FocusPoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import main.Settings;
import culling.Dimensions.MAP;

public class Mouse2 {


    public static Vector2 rightClickScreenPosition = new Vector2();
    public static Vector2 rightClickWorldPosition = new Vector2();
    public static Vector2 leftClickScreenPosition = new Vector2();
    public static Vector2 leftClickWorldPosition = new Vector2();
    public static Vector2 hoverScreenPosition = new Vector2();
    public static Vector2 hoverWorldPosition = new Vector2();
    private static Vector2 tmpVector2 = new Vector2();

    public static GridPoint2 sectionCordinates = new GridPoint2();
    public static GridPoint2 tileCordinates = new GridPoint2();
    public static GridPoint2 zoneCordinates = new GridPoint2();

    private static int sectionSize = MAP.STANDARD.sectionSize();
    private static int zoneSize = MAP.STANDARD.zoneSize();
    private static int tilesize = Settings.TILE_SIZE_M;
    private static int scale = Settings.SCALE;

    public static final int RIGHT_CLICK = 0;
    public static final int LEFT_CLICK = 1;
    public static int click;

    public static final int MENU_CONTROL = 0;
    public static final int GAME_CONTROL = 1;
    public static int currentAdapter;

    public static boolean leftButtonDown;
    public static boolean rightbuttonDown;


    public static void hoverUpdate(int x, int y) {
        hoverScreenPosition.set(x, Gdx.graphics.getHeight() - y);
        if (currentAdapter == GAME_CONTROL) {
            setWorldPosition(x,y);
        }
    }

    public static void rightClick(int x, int y) {
        click = RIGHT_CLICK;
        setClickScreenPosition(x,y);
        if (currentAdapter == GAME_CONTROL){
            setClickWorldPosition(x,y);
        }

    }

    public static void leftClick(int x, int y) {
        click = LEFT_CLICK;
        setClickScreenPosition(x,y);
        if (currentAdapter == GAME_CONTROL) {
            setClickWorldPosition(x,y);
        }
    }

    private static void setWorldPosition(int x, int y) {
        tmpVector2.set(x,y);
        hoverWorldPosition.set(Cam.adjustToWorldCords(tmpVector2));
        updateGridpoints(hoverWorldPosition);

    }

    private static void setClickScreenPosition(int x, int y) {
        if (click == LEFT_CLICK) {
            leftClickScreenPosition.set(x, Gdx.graphics.getHeight() - y);
        }
        else { rightClickScreenPosition.set(x, Gdx.graphics.getHeight() - y); }
    }

    private static void setClickWorldPosition(int x, int y) {
        if (click == LEFT_CLICK) {
            tmpVector2.set(x,y);
            leftClickWorldPosition.set(Cam.adjustToWorldCords(tmpVector2));
            FocusPoint.position.set(leftClickWorldPosition);
        }
        else {
            tmpVector2.set(x,y);
            rightClickWorldPosition.set(Cam.adjustToWorldCords(tmpVector2));
        }
    }

    public static void setAdapter(int adapter) {
        currentAdapter = adapter;
    }

    private static void updateGridpoints(Vector2 worldPosition) {
        tileCordinates.set((int)(worldPosition.x/tilesize/scale),(int)(worldPosition.y/tilesize/scale));
        sectionCordinates.set(tileCordinates.x/sectionSize, tileCordinates.y/sectionSize);
        zoneCordinates.set(tileCordinates.x/zoneSize, tileCordinates.y/zoneSize);
        //System.out.println(tileCordinates);

    }
}
