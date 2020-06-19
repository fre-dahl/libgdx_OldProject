package tilecoding.logic;

import java.util.Map;

import static java.util.Map.entry;

public class BitmaskLogic {

    /*
        Converts an boolean array of 8-bits to a desimal [0 - 47] ; corresponding to a texture region.
    */

    //  TODO: 21/12/2019 Implement try / exception catch

    private static Map<Integer, Integer> logic = Map.ofEntries(
            entry(2,1), entry(8,2), entry(10,3),
            entry(11,4), entry(16,5), entry(18,6),
            entry(22,7), entry(24,8), entry(26,9),
            entry(27,10), entry(30,11), entry(31,12),
            entry(64,13), entry(66,14), entry(72,15),
            entry(74,16), entry(75,17), entry(80,18),
            entry(82,19), entry(86,20), entry(88,21),
            entry(90,22), entry(91,23), entry(94,24),
            entry(95,25), entry(104,26), entry(106,27),
            entry(107,28), entry(120,29), entry(122,30),
            entry(123,31), entry(126,32), entry(127,33),
            entry(208,34), entry(210,35), entry(214,36),
            entry(216,37), entry(218,38), entry(219,39),
            entry(222,40), entry(223,41), entry(248,42),
            entry(250,43), entry(251,44), entry(254,45),
            entry(255,46), entry(0,47)
    );

    public static int convert(boolean[] code) {

        int[] power = {1, 2, 4, 8, 16, 32, 64, 128};
        int converted_code = 0;

        for (int i = 0; i < code.length; i++) {
            if (code[i]) {
                if (i == 1 || i == 3 || i == 4 || i == 6){
                    converted_code += power[i];
                }
                else {
                    if (i == 0){
                        if (code[1] && code[3]) {
                            converted_code += power[i];
                        }
                    }
                    if (i == 2){
                        if (code[1] && code[4]) {
                            converted_code += power[i];
                        }
                    }
                    if (i == 5){
                        if (code[3] && code[6]) {
                            converted_code += power[i];
                        }
                    }
                    if (i == 7){
                        if (code[4] && code[6]) {
                            converted_code += power[i];
                        }
                    }
                }
            }
        }
        try {
            return logic.get(converted_code);
        }catch (NullPointerException e) {
            System.out.println(e + " - BitmaskLogic.java");
            return 47;
        }
    }
}
