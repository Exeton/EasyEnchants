package online.fireflower.easy_enchants.enchant_parsing.numbers;

public class NumberParser implements INumberParser {

    @Override
    public int parse(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public String toInt(int val) {
        return Integer.toString(val);
    }

    @Override
    public boolean canParse(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
