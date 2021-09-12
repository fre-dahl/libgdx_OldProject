package input;

public class GameKeys {

    private static final byte NUM_KEYS = 10;

    private static boolean[] keys = new boolean[NUM_KEYS];
    private static boolean[] pkeys = new boolean[NUM_KEYS];



    public static final byte UP = 0;
    public static final byte RIGHT = 1;
    public static final byte DOWN = 2;
    public static final byte LEFT = 3;
    public static final byte ENTER = 4;
    public static final byte ESCAPE = 5;
    public static final byte SPACE = 6;
    public static final byte SHIFT = 7;
    public static final byte PAUSE = 8;
    public static final byte CONTROL = 9;


    public static void update() {
        System.arraycopy(keys, 0, pkeys, 0, NUM_KEYS);
    }

    public static void resetKeys() {
        //keys = new boolean[NUM_KEYS];
        //pkeys = new boolean[NUM_KEYS];
    }

    public static void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public static boolean isDown(int k) {
        return keys[k];
    }

    public static boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }
}
