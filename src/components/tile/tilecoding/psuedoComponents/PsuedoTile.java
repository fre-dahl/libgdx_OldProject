package components.tile.tilecoding.psuedoComponents;

public class PsuedoTile {


    public int tier;
    public int secondaryTier;
    public int row, col;
    public int bitmaskValue;
    public float noiceValue;
    public int secondaryBitmaskValue;
    public String code;
    public String secondaryCode;
    public boolean secondary;
    public int neighbors;
    public int potentialTier;

    public static final float LIM0 = 0.25f;
    public static final float LIM1 = 0.32f;
    public static final float LIM2 = 0.4f;
    public static final float LIM3 = 0.5f;
    public static final float LIM4 = 0.7f;



    /*
    public PsuedoTile (float c, int x, int y) {
        col = x; row = y; code = ""; secondaryCode = "";
        if 		(c <= LIM0) 			tier = 0;
        else if (c > LIM0 && c <= LIM1)	tier = 1;
        else if (c > LIM1 && c <= LIM2)	tier = 2;
        else if (c > LIM2 && c <= LIM3)	tier = 3;
        else if (c > LIM3 && c <= LIM4)	tier = 4;
        else 							tier = 5;
    } // continuous

     */


    public PsuedoTile (float c, int x, int y, float lim) {
        col = x; row = y; code = ""; secondaryCode = "";
        setSecondary(c,lim);
    }


    public PsuedoTile (float n, int x, int y) {
        noiceValue = n;
        col = x; row = y;
        code = ""; secondaryCode = "";
    }


    public PsuedoTile (int d, int x, int y) {
        tier = d; col = x; row = x;
        code = ""; secondaryCode = "";
    } // discrete

    public void setSecondary(float c, float lim) {
        if 		(c <= lim) 		secondaryTier = 1;
        else 					secondaryTier = 0;
    }
}
