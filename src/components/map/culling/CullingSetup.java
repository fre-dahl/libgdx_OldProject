package components.map.culling;

import components.map.Zone;
import components.map.culling.Dimensions.TILE;
import components.map.culling.Dimensions.MAP;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import components.map.Tile;
import components.map.TileMap;
import components.map.Section;
import main.Settings;

import java.util.ArrayList;

public class CullingSetup {


    private int mapSize;
    private int scale;

    private int sectionScaledSize;
    private int sectionCols;
    private int sectionRows;

    private int zoneScaledSize;
    private int zoneCols;
    private int zoneRows;

    //todo This must be modified for non-quadratical maps (not hard, first modify Dimensions.class)

    public CullingSetup(MAP map) {

        mapSize = map.size();
        int zoneSize = map.zoneSize();
        int sectionSize = map.sectionSize();

        zoneRows = mapSize/zoneSize;
        zoneCols = mapSize/zoneSize;
        sectionRows = mapSize/sectionSize;
        sectionCols = mapSize/sectionSize;

        scale = Settings.SCALE * TILE.MEDIUM.size();
        zoneScaledSize = zoneSize * scale;
        sectionScaledSize = sectionSize * scale;
    }

    public void init(TileMap tileMap, ArrayList<Tile> tiles) {

        ArrayList<Zone> zones = createZones();
        ArrayList<Section> sections = createSections();
        setTilemapBoundary(tileMap);

        Rectangle parentBoundary = new Rectangle();
        Vector2 childCordinates = new Vector2();
        int offset = 1; // read comments
        int x, y;

        // java.awt.Rectangle contains if <= gdx.math.Rectangle contains if <
        // I'm using the gdx Rectangle so i'm using the sections origin cordinates (childCordinates)
        // as a vector with a displacement (offset) of +1,+1 to check if a section is contained in the zone.
        // And the same displacement method is used for tiles to check if contained in a section.
        // I might create my own rectangle class later for overall concistency.

        for (Zone zone : zones) {
            parentBoundary.set(zone.getBoundary());
            for (Section section : sections) {
                x = (int)section.getBoundary().x;
                y = (int)section.getBoundary().y;
                childCordinates.set(x + offset, y + offset);
                if (parentBoundary.contains(childCordinates)) {
                    zone.addSection(section);
                }tileMap.sections.put(section.getPosition(),section);
            }tileMap.zones.put(zone.getPosition(),zone);
        }


        for (Section section : sections) {
            parentBoundary.set(section.getBoundary());
            for (Tile tile : tiles){
                x = tile.getWorldPosition().x;
                y = tile.getWorldPosition().y;
                childCordinates.set(x + offset , y + offset);
                if (parentBoundary.contains(childCordinates)) {
                    tile.setSection(section);
                    tileMap.tiles.put(tile.getGridPosition(), tile);
                }
            }
        }
    }


    private ArrayList<Section> createSections() {

        ArrayList<Section> sections = new ArrayList<>();

        for (int row = 0; row < sectionRows; row++) {
            for (int col = 0; col < sectionCols; col++) {
                Rectangle boundary = new Rectangle(
                        sectionScaledSize * col,
                        sectionScaledSize * row,
                        sectionScaledSize,
                        sectionScaledSize);
                sections.add(new Section(boundary, row, col));
            }
        }
        return sections;
    }

    private ArrayList<Zone> createZones() {

        ArrayList<Zone> zones = new ArrayList<>();

        for (int row = 0; row < zoneRows; row++) {
            for (int col = 0; col < zoneCols; col++) {
                Rectangle boundary = new Rectangle(
                        zoneScaledSize * col,
                        zoneScaledSize * row,
                        zoneScaledSize,
                        zoneScaledSize);
                zones.add(new Zone(boundary, row, col));
            }
        }
        return zones;
    }

    private void setTilemapBoundary(TileMap tilemap) {
        tilemap.boundary = new Rectangle(0,0, mapSize * scale, mapSize * scale);
    }
}
