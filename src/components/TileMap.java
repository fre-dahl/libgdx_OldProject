package components;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import culling.Section;
import culling.Zone;
import main.Settings;

import java.util.HashMap;

public class TileMap {

    public int rows, cols;
    public Rectangle boundary;
    public HashMap<GridPoint2, Zone> zones;
    public HashMap<GridPoint2, Section> sections;
    public HashMap<GridPoint2, Tile> tiles;



    public TileMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        zones = new HashMap<>();
        sections = new HashMap<>();
        tiles = new HashMap<>();
    }


    public void rendercheck() {
        for (HashMap.Entry<GridPoint2,Zone> entry : zones.entrySet()) {
            entry.getValue().renderCheck();
        }
    }


    public Vector2 getCentre() {
        Vector2 centre;
        float width =  Settings.SCALE * Settings.TILE_SIZE_M * cols;
        float height = Settings.SCALE * Settings.TILE_SIZE_M * rows;
        centre = new Vector2(width/2, height/2);
        return centre;
    }

}
