package components.tile;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import components.entity.Entity;
import graphics.culling.Dimensions.MAP;
import graphics.culling.Section;
import graphics.culling.Zone;
import main.Settings;

import java.util.HashMap;

public class TileMap {

    public int rows, cols;
    public Rectangle boundary;
    public HashMap<GridPoint2, Zone> zones;
    public HashMap<GridPoint2, Section> sections;
    public HashMap<GridPoint2, Tile> tiles;
    private GridPoint2 tmpGP2;

    private static int sectionSize = MAP.STANDARD.sectionSize();
    private static int tileSize = Settings.TILE_SIZE_M;
    private static int scale = Settings.SCALE;


    public TileMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        zones = new HashMap<>();
        sections = new HashMap<>();
        tiles = new HashMap<>();
        tmpGP2 = new GridPoint2();
    }


    public void rendercheck() {
        for (HashMap.Entry<GridPoint2,Zone> entry : zones.entrySet()) {
            entry.getValue().renderCheck();
        }
    }


    public Tile getTile(Vector2 worldPos) {
        int x = (int)worldPos.x/tileSize/scale;
        int y = (int)worldPos.y/tileSize/scale;
        tmpGP2.set(x,y);
        return tiles.get(tmpGP2);
    }

    public void setGridPositions(Entity entity) {
        int x = (int)entity.getPosition().x/tileSize/scale;
        int y = (int)entity.getPosition().y/tileSize/scale;
        tmpGP2.set(x,y);
        entity.setTile(tiles.get(tmpGP2));
        tmpGP2.set(x/sectionSize, y/sectionSize);
        entity.setSection(sections.get(tmpGP2));

    }

    public Vector2 getCentre() {
        Vector2 centre;
        float width =  scale * tileSize * cols;
        float height = scale * tileSize * rows;
        centre = new Vector2(width/2, height/2);
        return centre;
    }

}
