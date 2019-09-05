package ru.nsu.boyarintsev.Views;

import ru.nsu.boyarintsev.Models.NoiseModel;
import ru.nsu.boyarintsev.Utils.Point;
import ru.nsu.boyarintsev.Utils.WindowsUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


public class RoomZone extends JPanel implements Observer{
    private NoiseModel noiseModel;
    private int[] colors;
    private Image image = null;

    RoomZone(NoiseModel noiseModel,int[] colors)
    {
        this.noiseModel = noiseModel;
        this.colors = colors;
        noiseModel.addObserver(this);
        addMouseFeature();
    }

    public void makeImage()
    {
        image = makeImage(noiseModel.getMinNoiseValue(),noiseModel.getMaxNoiseValue());
    }

    private void addMouseFeature()
    {
        int x = getSize().width - coordinates.getWidth() - 10;
        int y = getImagePosition().getY();

        WindowsUtils.settingSize(coordinates,new java.awt.Point(x, y));
        WindowsUtils.settingSize(coordinatesText,new java.awt.Point(coordinates.getX(), coordinates.getY()+coordinates.getHeight()+3));

        add(coordinates);
        add(coordinatesText);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                double valueChangeOnHorizontalAxe = getValueChangesOnHorizontalAxe();
                double valueChangeOnVerticalAxe = getValueChangesOnVerticalAxe();

                int imageHeight = getActualHeightOfPlacementInMapping();
                int imageWidth = getActualWidthOfPlacementInMapping();

                int imageX = getImagePosition().getX();
                int imageY = getImagePosition().getY();

                int mouseX = e.getX() - imageX;
                int mouseY = e.getY() - imageY;

                if (mouseX < 0 || mouseX >= imageX + imageWidth || mouseY < 0 || mouseY >= imageY+imageHeight){
                    return;
                }

                Double X = mouseX * valueChangeOnHorizontalAxe/imageWidth;
                Double Y = mouseY * valueChangeOnVerticalAxe/imageHeight;
                String x = ViewUtils.numberTopsy(X,5);
                String y = ViewUtils.numberTopsy(Y,5);

                coordinatesText.setText(x +"," +y);
            }
        });
    }

    void setColors(int[] colors)
    {
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        draw(g);
    }

    public double getStepOnAxes()
    {
        //По горизонтали будет меняться максимальная сторона комнаты
        double valueChangesOnHorizontalAxe = getValueChangesOnHorizontalAxe();
        double valueChangesOnVerticalAxe = getValueChangesOnVerticalAxe();
        double stepOnAxes =  valueChangesOnHorizontalAxe / (getImageSize().width-1);
        if (valueChangesOnVerticalAxe / stepOnAxes > getImageSize().height - 1) {
            stepOnAxes = valueChangesOnVerticalAxe / (getImageSize().height -1);
        }
        return stepOnAxes;
    }

    private int getActualWidthOfPlacementInMapping()
    {
        double valueChangesOnHorizontalAxe = getValueChangesOnHorizontalAxe();
        double step = getStepOnAxes();
        return (int) (valueChangesOnHorizontalAxe / step);
    }

    private int getActualHeightOfPlacementInMapping()
    {
        double valueChangesOnVerticalAxe = getValueChangesOnVerticalAxe();
        double step = getStepOnAxes();
        return (int) (valueChangesOnVerticalAxe / step);
    }

    private Image makeImage(double minValue,double maxValue)
    {
        if (noiseModel == null)
        {
            return null;
        }
        double valueChangeOnHorizontalAxe = getValueChangesOnHorizontalAxe();
        double valueChangeOnVerticalAxe = getValueChangesOnVerticalAxe();

        int imageHeight = getImageSize().height;
        int imageWidth = getImageSize().width;

        double stepOnAxes = getStepOnAxes();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
        int i = 0;
        for (double x = 0; x <= valueChangeOnHorizontalAxe; x+=stepOnAxes) {
            int j = 0;
            for (double y = 0 ; y <= valueChangeOnVerticalAxe; y+=stepOnAxes) {
                double value = noiseModel.getSumNoiseValue(x, y);
                int color = getColorByValue(value, minValue, maxValue);
                image.setRGB(i, j, color);
                j++;
            }
            ++i;
        }
        return image;
    }

    private double getValueCorrespondingToColorIndex(double minValue,double maxValue,int countColors,int colorIndex)
    {
        if (colorIndex >= countColors) {
            colorIndex = countColors - 1;
        }
        return minValue + colorIndex*(maxValue - minValue)/(countColors - 1);
    }

    private int getColorByValueAndColors(double value,double valueForFirst,double valueForSecond,int firstColor,int secondColor)
    {
       int val = (int)((value-valueForFirst) * secondColor / (valueForSecond-valueForFirst) + (valueForSecond-value)*firstColor/(valueForSecond-valueForFirst));
        if (val > 255) {
            val = 255;
        }
        if (val < 0) {
            val = 0;
        }
        return val;
    }

    private int getColorByValue(double value,double minValue,double maxValue)
    {
        int countColors = colors.length ;
        if (maxValue == minValue)
        {
            return colors[0];
        }
        int colorIndex = (int)((countColors - 1) * ((value - minValue) /(maxValue - minValue))); //получили нижний индекс цвета
        if (colorIndex >= countColors-1)
        {
            return colors[countColors-1];
        }
        Color c1 = new Color(colors[colorIndex]);
        double valueForFirstColor = getValueCorrespondingToColorIndex(minValue,maxValue,countColors,colorIndex);
        Color c2 = new Color(colors[colorIndex+1]);
        double valueForSecondColor = getValueCorrespondingToColorIndex(minValue,maxValue,countColors,colorIndex + 1);
        int red = getColorByValueAndColors(value,valueForFirstColor,valueForSecondColor,c1.getRed(),c2.getRed());
        int green = getColorByValueAndColors(value,valueForFirstColor,valueForSecondColor,c1.getGreen(),c2.getGreen());
        int blue = getColorByValueAndColors(value,valueForFirstColor,valueForSecondColor,c1.getBlue(),c2.getBlue());
        return new Color(red,green,blue).getRGB();
    }

    public double getValueChangesOnHorizontalAxe() {
        return Math.max(noiseModel.getPlacementLength(),noiseModel.getPlacementWidth());
    }

    public double getValueChangesOnVerticalAxe() {
        double placementLength = noiseModel.getPlacementLength();
        double placementWidth = noiseModel.getPlacementWidth();
        return getValueChangesOnHorizontalAxe() == placementWidth ? placementLength : placementWidth;
    }

    private Dimension getImageSize()
    {
        int imageHeight = 7 * getSize().height / 8 ;
        int imageWidth = 7 * getSize().width / 8;
        return new Dimension(imageWidth,imageHeight);
    }
    private Point getImagePosition()
    {
        int imageX = getSize().width / 16;
        int imageY = getSize().height / 16;
        return new Point(imageX,imageY);
    }

    private void drawStringOverAndLeftTheImage(String str,Graphics g)
    {
        int imageX = getImagePosition().getX();
        int imageY = getImagePosition().getY();
        int y = imageY - g.getFont().getSize();
        int x = imageX;
        g.drawString(str,x,y);
    }


    private void drawStringOverAndRightTheImage(String str,Graphics g)
    {
        int imageX = getImagePosition().getX();
        int imageY = getImagePosition().getY();
        int y = imageY - g.getFont().getSize();
        int x = imageX + getActualWidthOfPlacementInMapping() - 5 * str.length();
        g.drawString(str,x,y);
    }

    private void drawStringUnderAndLeftTheImage(String str,Graphics g)
    {
        int imageX = getImagePosition().getX();
        int imageY = getImagePosition().getY();
        int y = imageY  + getActualHeightOfPlacementInMapping() + g.getFont().getSize();
        int x = imageX;
        g.drawString(str,x,y);
    }

    private void drawStringUnderAndRightTheImage(String str,Graphics g)
    {
        int imageX = getImagePosition().getX();
        int imageY = getImagePosition().getY();
        int y = imageY + getActualHeightOfPlacementInMapping() + g.getFont().getSize();
        int x = imageX + getActualWidthOfPlacementInMapping() -  5 * str.length();
        g.drawString(str,x,y);
    }

    private void drawCoordinates(Graphics g)
    {
        Double valueChangeOnHorizontalAxe = getValueChangesOnHorizontalAxe();
        Double valueChangeOnVerticalAxe = getValueChangesOnVerticalAxe();
        g.setColor(Color.BLACK);
        drawStringOverAndLeftTheImage("(0, 0)",g);
        drawStringOverAndRightTheImage("("+valueChangeOnHorizontalAxe.toString() +", 0)",g);
        drawStringUnderAndLeftTheImage("(0, "+valueChangeOnVerticalAxe.toString()+")",g);
        drawStringUnderAndRightTheImage("("+valueChangeOnHorizontalAxe.toString() + ", "+valueChangeOnVerticalAxe.toString()+")",g);
    }

    private void draw(Graphics g)
    {
        if (null != image) {
            int imageX = getImagePosition().getX();
            int imageY = getImagePosition().getY();
            drawCoordinates(g);
            g.drawImage(image, imageX, imageY, null);
        }
    }

    public void update(Observable o, Object arg) {
        Date date = new Date();
        image = makeImage(noiseModel.getMinNoiseValue(),noiseModel.getMaxNoiseValue());
        System.err.println(new Date().getTime() - date.getTime());
        repaint();
    }

    private final  JLabel coordinates = new JLabel("Координаты");
    private final  JLabel coordinatesText = new JLabel();
}
