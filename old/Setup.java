package old;

import components.BaseTile;
import components.tile.tilecoding.noiceGenerators.PerlinNoiseGenerator;
import components.tile.tilecoding.psuedoComponents.PsuedoMap;
import components.tile.tilecoding.psuedoComponents.PsuedoTile;
import java.util.ArrayList;

public class Setup {




    public static TileMap init(int rows, int cols) {

        PsuedoMap map = new PsuedoMap(rows, cols);
        TileMap tileMap = new TileMap(rows, cols);

        float[][] whiteNoice = PerlinNoiseGenerator.generateWhiteNoise(cols,rows);
        float[][] perlinNoice = PerlinNoiseGenerator.generatePerlinNoise(whiteNoice,8);
        ArrayList<PsuedoTile> mapRow = new ArrayList<>(); int currentRow = 0;
        //StringBuilder sb;

        for(int row = 0; row < map.rows; row ++) {
            for (int col = 0; col < map.cols; col++) {
                PsuedoTile tile = new PsuedoTile(perlinNoice[col][row],col,row);

                if(currentRow == row){
                    mapRow.add(tile);
                    // Last row and column?
                    if (row == map.rows - 1 && col == map.cols - 1){
                        map.tiles.add(mapRow);
                    }
                }
                else {
                    //sb = new StringBuilder();
                    //for (PsuedoTile psuedoTile : mapRow) {
                        //sb.append(psuedoTile.tier);
                    //}
                    //System.out.println(sb);
                    currentRow = row;
                    map.tiles.add(mapRow);
                    mapRow = new ArrayList<PsuedoTile>();
                    mapRow.add(tile);
                }
            }
        }

        map.codeTiles();
        map.setBitMaskValue();

        currentRow = 0;
        int tier;
        int maskValue;
        ArrayList<BaseTile> mapRow2 = new ArrayList<BaseTile>();

        for(int row = 0; row < map.rows; row ++) {
            for (int col = 0; col < map.cols; col++) {
                BaseTile tile = new BaseTile(col,row, Settings.TILE_SIZE * Settings.SCALE);
                tier = map.getTile(row,col).tier;
                maskValue = map.getTile(row,col).bitmaskValue;
                tile.setBase(AssetHandler.get_tile_textureRegion(tier,maskValue,false));
                if(currentRow == row){
                    mapRow2.add(tile);
                    // Last row and column?
                    if (row == tileMap.rows - 1 && col == tileMap.cols - 1){
                        tileMap.tiles.add(mapRow2);
                    }
                }
                else {
                    currentRow = row;
                    tileMap.tiles.add(mapRow2);
                    mapRow2 = new ArrayList<BaseTile>();
                    mapRow2.add(tile);
                }
            }
        }
        return tileMap;
    }




}
