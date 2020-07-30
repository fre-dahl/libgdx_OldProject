package old;

import components.BaseTile;
import components.map.tilecoding.process.PsuedoSetup;
import components.map.tilecoding.psuedoComponents.PsuedoMap;
import components.map.tilecoding.psuedoComponents.PsuedoTile;
import main.Settings;

import java.util.ArrayList;

public class Setup2 {

    public static TileMap init(int rows, int cols) {

        PsuedoMap psuedoMap = PsuedoSetup.baseTerrain(rows,cols);
        TileMap tileMap = new TileMap(rows, cols);
        psuedoMap.codeTiles();
        psuedoMap.setBitMaskValue();
        if (Settings.SECONDARY_TERRAIN){
            psuedoMap.setSecondaryBitMaskValue();
        }

        boolean secondary;
        int currentRow = 0;
        int tier;
        int maskValue;
        int secondaryMaskValue;

        ArrayList<BaseTile> mapRow = new ArrayList<BaseTile>();

        for(int row = 0; row < psuedoMap.rows; row ++) {
            for (int col = 0; col < psuedoMap.cols; col++) {

                PsuedoTile psuedoTile = psuedoMap.getTile(row,col);
                secondaryMaskValue = psuedoTile.secondaryBitmaskValue;
                maskValue = psuedoTile.bitmaskValue;
                secondary = psuedoTile.secondary;
                tier = psuedoTile.tier;
                BaseTile tile;

                if (secondary) {
                    tile = new OverlayTile(col,row,Settings.TILE_SIZE * Settings.SCALE);
                    if (secondaryMaskValue != 46) {
                        // Don't set base texture if irrellevant
                        tile.setBase(AssetHandler.get_tile_textureRegion(tier,maskValue,false));
                    }
                    if (secondaryMaskValue != 47) {
                        // Don't set overlay if no other of the type around
                        tile.setOverlay(AssetHandler.get_tile_textureRegion(tier,secondaryMaskValue,true));
                    }

                }
                else {
                    tile = new BaseTile(col,row,Settings.TILE_SIZE * Settings.SCALE);
                    tile.setBase(AssetHandler.get_tile_textureRegion(tier,maskValue,false));
                }

                if(currentRow == row){
                    mapRow.add(tile);
                    // Last row and column?
                    if (row == tileMap.rows - 1 && col == tileMap.cols - 1){
                        tileMap.tiles.add(mapRow);
                    }
                }
                else {
                    currentRow = row;
                    tileMap.tiles.add(mapRow);
                    mapRow = new ArrayList<BaseTile>();
                    mapRow.add(tile);
                }
            }
        }
        return tileMap;
    }
}
