package ru.nsu.boyarintsev.Models;

import ru.nsu.boyarintsev.Utils.AcousticWaveValue;
import ru.nsu.boyarintsev.Utils.DoublePoint;

import java.util.ArrayList;
import java.util.Observable;


/**
 * Created by Артем on 13.02.2017.
 */

public class NoiseModel extends Observable {
    final double metresPerSecond = 340.0;

    public NoiseModel(Placement placement,int modelDepth)
    {
        this.placement = placement;
        this.modelDepth = modelDepth;
        maxNoiseValue = minNoiseValue = getSumNoiseValue(0,0);
    }
    private double getDistanceBetweenPoints(DoublePoint a,DoublePoint b)
    {
        double first = a.getX() - b.getX();
        double second = a.getY() - b.getY();
        return Math.sqrt(first*first+second*second);
    }
    private double getTimeForNoiseSource(double x,double y,DoublePoint sourcePosition)
    {
        double distance = getDistanceBetweenPoints(
                new DoublePoint(x,y),
                sourcePosition
        );
        return distance / metresPerSecond;
    }
    private double noiseExpress(double x, double y)
    {
        if (x>=2.34 && x<2.36 && y >=1.99&& y<=2.01)
        {
            System.err.println("no");
        }
        ArrayList<SoundSource> soundSources = placement.getNoiseSoundSources();
        AcousticWaveValue resultValue = new AcousticWaveValue();
        for(SoundSource noiseSource: soundSources) {
            double timeForNoise = getTimeForNoiseSource(x, y, noiseSource.getSourcePosition());
            AcousticWaveValue noiseSourceValue = noiseSource.getValue(timeForNoise);
            resultValue.addAnotherWaveValue(noiseSourceValue);
        }
        return resultValue.getNoiseValue();
    }
    public double getSumNoiseValue(double x,double y)
    {
        double retValue = noiseExpress(x,y);
        if (maxNoiseValue < retValue) {
            maxNoiseValue = retValue;
        }
        if (minNoiseValue > retValue) {
            minNoiseValue = retValue;
        }
        return retValue;
    }

    public double getMaxNoiseValue() {
        double sum = 0.0;
        ArrayList<SoundSource> soundSources = placement.getNoiseSoundSources();
        for(SoundSource noiseSource: soundSources) {
            sum += noiseSource.getMaxNoiseValue();
        }
        return sum;
    }

    public double getMinNoiseValue() {
        return 0;
    }

    public double getPlacementWidth()
    {
        return placement.getWidth();
    }

    public double getPlacementLength()
    {
        return placement.getLength();
    }

    public void setPlacement(Placement placement) {
        ArrayList<SoundSource> soundSources = this.placement.getNoiseSoundSources();
        this.placement = placement;
        this.placement.addSoundSources(soundSources);
        update();
    }

    public void update()
    {
        setChanged();
        notifyObservers();
    }


    public boolean updateSoundSourceWithIndex(int index, SoundSourceCreatingParams params)
    {
        SoundSource soundSource =  placement.getSoundSourceByIndex(index);
        if (soundSource != null)
        {
            soundSource.setNewParams(params);
            update();
            return true;
        }
        return false;
    }

    public void addSoundSource(SoundSource soundSource)
    {
        placement.addSoundSource(soundSource);
        update();
    }

    public void setModelDepth(int modelDepth) {
        this.modelDepth = modelDepth;
        update();
    }

    private double maxNoiseValue;
    private double minNoiseValue;

    private Placement placement;
    private int modelDepth;
}
