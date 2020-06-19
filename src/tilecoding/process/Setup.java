package tilecoding.process;

import components.Tile;
import components.Biome;
import components.TileMap;
import culling.CullingSetup;
import culling.Dimensions;
import main.Settings;
import assets.Assets;
import tilecoding.psuedoComponents.PsuedoMap;
import tilecoding.psuedoComponents.PsuedoTile;

import java.util.ArrayList;

public class Setup {

    public static TileMap init(int rows, int cols, Biome biome) {

        // Psuedo Setup

        PsuedoMap psuedoMap = new PsuedoSetup().baseTerrain(rows,cols, biome);

        // Tile Setup

        boolean secondary;
        int tier;
        int maskValue;
        int secondaryMaskValue;

        int lonelyTile = 47;
        int baseTile = 46;

        ArrayList<Tile> tiles = new ArrayList<Tile>();

        for(int row = 0; row < psuedoMap.rows; row ++) {
            for (int col = 0; col < psuedoMap.cols; col++) {

                PsuedoTile psuedoTile = psuedoMap.getTile(row,col);
                maskValue = psuedoTile.bitmaskValue;
                secondary = psuedoTile.secondary;
                tier = psuedoTile.tier;
                Tile tile = new Tile(col,row,Settings.TILE_SIZE_M * Settings.SCALE);

                //todo This is why overlay and base can be null in drwregion
                if (secondary) {
                    secondaryMaskValue = psuedoTile.secondaryBitmaskValue;
                    //tile = new OverlayTile(col,row,Settings.TILE_SIZE_M * Settings.SCALE);
                    tile = new Tile(col,row,Settings.TILE_SIZE_M * Settings.SCALE);
                    if (secondaryMaskValue != baseTile) {
                        // Don't set base texture if irrellevant
                        //tile.setLayer2(Assets.instance.assetTiles.getTileRegion(tier,maskValue));
                    }
                    if (secondaryMaskValue != lonelyTile) {
                        // Don't set overlay if no other of the type around
                        //tile.setLayer1(Assets.instance.assetTiles.getSecondary(secondaryMaskValue));
                    }

                }
                else {


                    tile.maskvalue = psuedoTile.bitmaskValue;
                    tile.tier = psuedoTile.tier;
                    tile.setTileType(biome.getBiome());

                    switch (biome.getBiome()){
                        case LAKES:
                            break;
                        case COAST:
                            break;
                        case MOUNTAIN:
                            break;
                        case DESSERT:
                            break;
                        case PLAINS:
                            break;
                        case FOREST:
                            break;
                        case CAVE:
                            break;
                        case TUNDRA:
                            if (tile.tier < 3) {
                                tile.setLayer0(Assets.instance.assetTiles.getTileRegion(tier,maskValue));
                            }
                            else if (tile.tier == 3) {
                                if (maskValue != baseTile) {
                                    tile.setLayer0(Assets.instance.assetTiles.getTileRegion(tier,maskValue));
                                }
                                tile.setLayer1(Assets.instance.assetTiles.getShoreGroundTile(maskValue));
                            }
                            else
                                tile.setLayer1(Assets.instance.assetTiles.getTileRegion(tier,maskValue));
                            break;
                        default:
                            break;
                    }
                }
                tiles.add(tile);
            }
        }

        // Culling

        TileMap tileMap = new TileMap(rows, cols);
        new CullingSetup(Dimensions.MAP.BIG).init(tileMap,tiles);
        return tileMap;
    }

}
