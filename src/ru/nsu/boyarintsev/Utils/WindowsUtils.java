package ru.nsu.boyarintsev.Utils;

import java.awt.*;
import java.awt.Point;

public class WindowsUtils {
    public static void settingSize(Component component, java.awt.Point start)
    {
        Dimension sizeFstImpact = component.getPreferredSize();
        java.awt.Point fstImpactPosition = new Point(start.x,start.y);
        component.setBounds(fstImpactPosition.x,fstImpactPosition.y,sizeFstImpact.width,sizeFstImpact.height);
    }

}
