package tools;


public class Counter {

    private static int i;
    private static boolean b = true;


    public static void count() {
        if (b) i++;
    }

    public static void countIf(boolean condition) {
        if (condition && b) i++;
    }

    public static void resetAndShow() {
        printResult("");
        i = 0;
    }

    public static void resetAndShow(String s) {
        printResult(s);
        i = 0;
    }

    public static void endAndShow() {
        if (b) {
            printResult("");
            b = false;
            i = 0;
        }
    }

    public static void endAndShow(String s) {
        if (b){
            printResult(s);
            b = false;
            i = 0;
        }
    }

    private static void printResult(String s) {
        System.out.println("Counted: " + i + " " + s);
    }
}
