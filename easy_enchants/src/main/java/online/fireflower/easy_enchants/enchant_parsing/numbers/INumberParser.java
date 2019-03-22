package online.fireflower.easy_enchants.enchant_parsing.numbers;

public interface INumberParser {

    boolean canParse(String str);
    int parse(String str);
    String toInt(int val);

}
