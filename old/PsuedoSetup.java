package tilecoding.process;

import tilecoding.logic.BitmaskLogic;
import tilecoding.noiceGenerators.PerlinNoiseGenerator;
import tilecoding.psuedoComponents.PsuedoMap;
import tilecoding.psuedoComponents.PsuedoTile;
import main.Settings;
import tools.Counter;

import java.util.ArrayList;
import java.util.Random;

public class PsuedoSetup {

    // This static class readies a "psuedomap" for creation of an actual tile-based map (Setup.class).
    // This greatly simplifies actual tile and tile-map classes. Holding everything related to generation seperate from them.
    // It's all done statically with no leftover references to "Psuedo-components".
    // It takes the input from the Settings.class and makes a perlin-noise based psuedomap.
    // The main component of the psuedomap is the psuedotile, holding a tier [0,4], corresponding to the perlin noice output.
    // Psuedosetup will give each psuedo-tile a tier and bitmask-value. The bitmask value is used for giving the actual TILE the correct texture-region.
    // PsuedoSetup can also make a secondary "perlin-map" of another terrain overlapping the basemap in a nice way.
    // A "smoothen" method does just that. It "removes" some or all of tiles with no neighbors of same type.



    public static PsuedoMap baseTerrain (int rows, int cols) {
        PsuedoMap map = new PsuedoMap(rows, cols);

        float[][] whiteNoice = PerlinNoiseGenerator.generateWhiteNoise(cols,rows);
        float[][] perlinNoice = PerlinNoiseGenerator.generatePerlinNoise(whiteNoice, Settings.OCTAVE_COUNT);
        ArrayList<PsuedoTile> mapRow = new ArrayList<>();
        int currentRow = 0;

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
                    currentRow = row;
                    map.tiles.add(mapRow);
                    mapRow = new ArrayList<PsuedoTile>();
                    mapRow.add(tile);
                }
            }
        }

        smoothen(map);
        if (Settings.SECONDARY_TERRAIN) { secondaryTerrain(map);}

        map.codeTiles();
        map.setBitMaskValue();

        if (Settings.SECONDARY_TERRAIN){
            map.setSecondaryBitMaskValue();
        }
        return map;
    }

    private static void smoothen(PsuedoMap map) {

        int[] rows = {1,0,-1};
        int[] cols = {-1,0,1};
        Random rng = new Random();
        PsuedoTile neighbor;
        PsuedoTile tile;
        int chance = 9;

        for(int row = 0; row < map.rows; row ++) {
            for (int col = 0; col < map.cols; col++) {
                tile = map.getTile(row,col);
                for(int r: rows){
                    for(int c: cols){
                        boolean thisTile = (r == 0 && c == 0);
                        boolean outsideX = (tile.col + c) >= map.cols || (tile.col + c) < 0;
                        boolean outsideY = (tile.row + r) >= map.rows || (tile.row + r) < 0;
                        if(!(outsideX || outsideY || thisTile)) {
                            neighbor = map.getTile(tile.row + r, tile.col + c);
                            if(tile.tier == neighbor.tier) tile.neighbors++;
                            else if(!(Math.abs(tile.tier - neighbor.tier) > 1 )) {
                                tile.potentialTier = neighbor.tier;
                            }
                        }
                    }
                }
                if(tile.neighbors <= 1){
                    if(rng.nextInt(10) < chance) {
                        tile.tier = tile.potentialTier;
                    }
                }
            }
        }
    }

    private static void secondaryTerrain (PsuedoMap map) {
        PsuedoMap secondaryMap = new PsuedoMap(map.rows, map.cols);
        float[][] whiteNoice = PerlinNoiseGenerator.generateWhiteNoise(secondaryMap.cols,secondaryMap.rows);
        float[][] perlinNoice = PerlinNoiseGenerator.generatePerlinNoise(whiteNoice, Settings.OCTAVE_COUNT);
        ArrayList<PsuedoTile> mapRow = new ArrayList<>();
        int currentRow = 0;

        for(int row = 0; row < map.rows; row ++) {
            for (int col = 0; col < map.cols; col++) {

                PsuedoTile tile = new PsuedoTile(perlinNoice[col][row],col,row, Settings.SECONDARY_AMOUNT);

                if(currentRow == row){
                    mapRow.add(tile);
                    if (row == secondaryMap.rows - 1 && col == secondaryMap.cols - 1){
                        secondaryMap.tiles.add(mapRow);
                    }
                }
                else {
                    currentRow = row;
                    secondaryMap.tiles.add(mapRow);
                    mapRow = new ArrayList<PsuedoTile>();
                    mapRow.add(tile);
                }
            }
        }
        smoothen(secondaryMap);
        PsuedoTile baseMapTile, secondaryMapTile;
        for(int row = 0; row < map.rows; row ++) {
            for (int col = 0; col < map.cols; col++) {

                baseMapTile = map.getTile(row,col);
                secondaryMapTile = secondaryMap.getTile(row,col);

                if ((baseMapTile.tier > 2) && (secondaryMapTile.secondaryTier == 1)) {
                    baseMapTile.secondary = true;
                }
            }
        }
    }
}
