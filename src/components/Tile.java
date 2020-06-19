package components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import components.Biome.BIOME;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;
import culling.Section;
import drawdata.DrwRegion;

public class Tile implements Disposable {

    private static final byte WATER_LAYER = 1;
    private static final byte GROUND_LAYER = 3;
    private static final byte SECONDARY_LAYER = 4;

    public Tiletype type;
    public int maskvalue, tier, size;
    private GridPoint2 worldPosition;
    private GridPoint2 gridPosition;
    private DrwRegion layer2;
    private DrwRegion layer1;
    private DrwRegion layer0;

    enum Tiletype {
        DEEP_WATER(),
        WATER(),
        SHALLOW_WATER(),
        SHORE(),
        GRASS(),
        TALL_GRASS(),
        //----------------
        NO_TYPE()
    }

    public Tile(int x, int y, int size) {
        worldPosition = new GridPoint2(x*size,y*size);
        gridPosition = new GridPoint2(x,y);
        this.size = size;
    }

    public void setLayer2(TextureRegion region) {
        layer1 = new DrwRegion(region, worldPosition.x, worldPosition.y, size, size, SECONDARY_LAYER);
    }
    public void setLayer1(TextureRegion region) {
        layer2 = new DrwRegion(region, worldPosition.x, worldPosition.y, size, size, GROUND_LAYER);
    }
    public void setLayer0(TextureRegion region) {
        layer0 = new DrwRegion(region, worldPosition.x, worldPosition.y, size, size, WATER_LAYER);
    }

    public void setTileType(BIOME biome) {
        switch (biome) {
            case TUNDRA:
                switch (tier) {
                    case 0: type = Tiletype.DEEP_WATER; break;
                    case 1: type = Tiletype.WATER; break;
                    case 2: type = Tiletype.SHALLOW_WATER; break;
                    case 3: type = Tiletype.SHORE; break;
                    case 4: type = Tiletype.GRASS; break;
                    case 5: type = Tiletype.TALL_GRASS; break;
                    default: type = Tiletype.NO_TYPE; break;
                } break;
            case LAKES: break;
            case FOREST: break;
            default: break;
        }
    }

    public GridPoint2 getWorldPosition() { return worldPosition; }

    public GridPoint2 getGridPosition() { return gridPosition; }


    public void setSection (Section section) {
        if (layer2 !=null) layer2.setSection(section);
        if (layer1 !=null) layer1.setSection(section);
        if (layer0 !=null) layer0.setSection(section);
    }

    @Override
    public void dispose() {
        layer2.dispose();
        layer1.dispose();
        layer0.dispose();
    }

    @Override
    public String toString() {
        return "Mask: " + maskvalue + " / Tier: " + tier + " / Type: " + type;
    }
}
