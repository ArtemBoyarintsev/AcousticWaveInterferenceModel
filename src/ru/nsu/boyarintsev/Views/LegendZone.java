package ru.nsu.boyarintsev.Views;

import ru.nsu.boyarintsev.Models.NoiseModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class LegendZone extends JPanel implements Observer {
    private NoiseModel noiseModel;
    private int[] colors;

    private double min;
    private double max;


    public LegendZone(NoiseModel noiseModel, int[] colors) {
        this.noiseModel = noiseModel;
        this.colors = colors;
        min = noiseModel.getMinNoiseValue();
        max = noiseModel.getMaxNoiseValue();
        noiseModel.addObserver(this);
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int noDots = colors.length;
        int height = getSize().height;
        int bandWidth = getSize().width;;
        int bandHeight = 7 * height/(8*colors.length);
        int colorCount = colors.length;
        Point legendStart = new Point(0,height/16); // рисуем после первой четверти зоны
        double step = (max - min) / (noDots);
        for(int i = 0 ; i < colorCount; ++i)
        {
            Point point = new Point(legendStart.x,legendStart.y + bandHeight*i);
            Color c = new Color(colors[i]);
            g.setColor(c);
            g.fillRect(point.x, point.y, bandWidth,bandHeight );
            g.setColor(getOppositeGray(c));
            String value = ViewUtils.numberTopsy((min + step*i )/ max,5);
            String str = "Noise Level "+i+"="+value;
            g.drawString(str, 0, point.y + g.getFont().getSize());
        }
    }

    private Color getOppositeGray(Color c)
    {
        int blue = c.getBlue();
        int green = c.getGreen();
        int red = c.getRed();
        int nGray = (int)(0.56 * (255-green) + 0.33 * (255-red) +
                0.11 * (255-blue));

        return new Color( (0xff000000 | nGray << 16 |
                nGray << 8 | nGray));
    }

    public void update(Observable o, Object arg) {
        min = noiseModel.getMinNoiseValue();
        max = noiseModel.getMaxNoiseValue();
        repaint();
    }

    private final int interpolationDegree = 10;
}
