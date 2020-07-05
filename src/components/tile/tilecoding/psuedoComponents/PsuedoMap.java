package components.tile.tilecoding.psuedoComponents;

import components.tile.tilecoding.logic.BitmaskLogic;

import java.util.ArrayList;

public class PsuedoMap {

    public int rows, cols;
    public ArrayList<ArrayList<PsuedoTile>> tiles;

    public PsuedoMap (int rows, int cols) {
        tiles = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
    }

    public PsuedoTile getTile(int row, int col) {
        ArrayList<PsuedoTile> mapRow;
        if(tiles.size() > row && row >= 0){
            mapRow = tiles.get(row);
            if(mapRow != null && mapRow.size() > col && col >= 0){
                return mapRow.get(col);
            } }return null;
    }

    public String getRelativeCode(int tier, int row, int col){
        PsuedoTile tile;
        ArrayList<PsuedoTile> mapRow;
        if(tiles.size() > row && row >= 0){
            mapRow = tiles.get(row);
            if(mapRow != null && mapRow.size() > col && col >= 0){
                tile = mapRow.get(col);
                return  (tile.tier >= tier) ? "1" : "0";
            } }return "9"; // returns "9" for outside of map.
    }

    public String getSecondaryCode(int row, int col){
        PsuedoTile tile;
        ArrayList<PsuedoTile> mapRow;
        if(tiles.size() > row && row >= 0){
            mapRow = tiles.get(row);
            if(mapRow != null && mapRow.size() > col && col >= 0){
                tile = mapRow.get(col);
                return  (tile.secondary) ? "1" : "0";
            } }return "9"; // returns "9" for outside of map.
    }

    public void codeTiles() {
        int[] rows = {1,0,-1};
        int[] cols = {-1,0,1};
        for (ArrayList<PsuedoTile> row : tiles) {
            for (PsuedoTile tile : row) {
                for(int r: rows){
                    for(int c: cols){
                        tile.code += getRelativeCode(tile.tier,tile.row + r, tile.col + c);
                        if (tile.secondary)
                            tile.secondaryCode += getSecondaryCode(tile.row + r, tile.col + c);
                    }
                }
            }
        }
    }

    public void setMaskAll() {
        StringBuilder sb;
        StringBuilder sb2;
        boolean[] binary_code;
        boolean[] binary_code2;
        for(ArrayList<PsuedoTile> row : tiles) {
            for (PsuedoTile tile : row) {
                binary_code = new boolean[8];
                sb = new StringBuilder(tile.code);
                sb.deleteCharAt(4);
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '1') binary_code[i] = true;
                }
                tile.bitmaskValue = BitmaskLogic.convert(binary_code);
                if (tile.secondary) {
                    binary_code2 = new boolean[8];
                    sb2 = new StringBuilder(tile.secondaryCode);
                    sb2.deleteCharAt(4);
                    for (int i = 0; i < sb2.length(); i++) {
                        if (sb2.charAt(i) == '1') binary_code2[i] = true;
                    }
                    tile.secondaryBitmaskValue = BitmaskLogic.convert(binary_code2);
                }
            }
        }
    }

    public void setMask() {
        StringBuilder sb;
        boolean[] binary_code;
        for(ArrayList<PsuedoTile> row : tiles) {
            for (PsuedoTile tile : row) {
                binary_code = new boolean[8];
                sb = new StringBuilder(tile.code);
                sb.deleteCharAt(4);
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '1') binary_code[i] = true;
                }tile.bitmaskValue = BitmaskLogic.convert(binary_code);
            }
        }
    }

    public void resetTileCodes() {
        for(ArrayList<PsuedoTile> row : tiles) {
            for (PsuedoTile tile : row) {
                tile.code = ""; } }
    }

}
