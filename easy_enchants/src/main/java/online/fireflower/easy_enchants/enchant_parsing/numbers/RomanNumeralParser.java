package online.fireflower.easy_enchants.enchant_parsing.numbers;

import org.bukkit.Bukkit;

import java.util.HashMap;

public class RomanNumeralParser implements INumberParser {

    private static HashMap<String, Integer> values = new HashMap<>();
    private static RomanNumeralParser instance;


    //TODO: Recode this whole class

    public static RomanNumeralParser getInstance(){
        if (instance == null)
            instance = new RomanNumeralParser();
        return instance;
    }

    private RomanNumeralParser(){

        values.put("I", 1);
        values.put("V", 5);
        values.put("X", 5);

    }


    @Override
    public boolean canParse(String str) {

        if (str.length() < 1)
            return false;

        if (str.startsWith("X") || str.startsWith("V") || str.startsWith("I"))
            return true;
        return false;
    }

    @Override
    public int parse(String str) {

        switch (str){
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
        }
        return 0;
    }

    @Override
    public String toInt(int val) {

        switch (val){

            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }

        return "I";
    }
}
