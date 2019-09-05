package ru.nsu.boyarintsev.Utils;

/**
 * Created by Артем on 15.03.2017.
 */
public class AcousticWaveValue {

    private double xValue;
    private double yValue;

    public AcousticWaveValue(double xValue, double yValue)
    {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public AcousticWaveValue() {
        xValue = yValue = 0;
    }

    public void addAnotherWaveValue(AcousticWaveValue another)
    {
        this.xValue +=another.xValue;
        this.yValue += another.yValue;
    }

    public double getNoiseValue()
    {
        return Math.sqrt(xValue*xValue + yValue*yValue);
    }


    public double getXValue() {
        return xValue;
    }

    public double getYValue() {
        return yValue;
    }

}


