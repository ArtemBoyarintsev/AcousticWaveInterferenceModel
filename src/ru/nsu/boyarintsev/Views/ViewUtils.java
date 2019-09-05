package ru.nsu.boyarintsev.Views;

import javax.swing.*;

/**
 * Created by Артем on 24.03.2017.
 */
public class ViewUtils {

    public static String numberTopsy(Double number)
    {
        final int accurateness = 7;
        return numberTopsy(number,accurateness);
    }

    public static String numberTopsy(Double number,int accurateness)
    {
        String str = number.toString();
        int index = str.indexOf(".");
        if (index != -1) {
            int countSymbolToWrite = str.length();
            if (countSymbolToWrite > index + accurateness)
            {
                countSymbolToWrite = index + accurateness;
            }
            str = str.substring(0,countSymbolToWrite);
        }
        return str;
    }

    public static void setNumberText(JTextField textArea, Double number)
    {
        textArea.setText(numberTopsy(number));
    }

    public static void setNumberText(JTextField textArea, Integer number)
    {
        textArea.setText(number.toString());
    }

}

