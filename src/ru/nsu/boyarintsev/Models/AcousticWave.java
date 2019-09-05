package ru.nsu.boyarintsev.Models;

import ru.nsu.boyarintsev.Utils.AcousticWaveValue;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Артем on 04.03.2017.
 */
public class AcousticWave {

    public AcousticWave(double frequency,double amplitude,int startPhase)
    {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.startPhase = Math.toRadians(startPhase);
        createValuesTables();
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setStartPhase(double startPhase) {
        this.startPhase = Math.toRadians(startPhase);
    }

    public double getFrequency() {
        return frequency;
    }

    public int getStartPhase() {
        return (int)Math.toDegrees(startPhase);
    }

    private double getOffset(double time)
    {
        double period = 1 / frequency;
        int fullPeriod = (int)(time / period); //сколько полных периодов в time.
        time = time - fullPeriod * period; //время с эквивалентным значеним сигнала
        return time;
    }

    private double getBestTime(double time) {
        Set<Double> keySet = values.keySet();
        double difference = time;
        double bestTime = 0;
        for(Double key : keySet) {
            double diff = Math.abs(time - key);
            if (diff < difference) {
                bestTime = key;
                difference = diff;
            }
        }
        return bestTime;
    }

    public AcousticWaveValue getValue(double time)
    {
        double arg = 2*Math.PI * frequency * time + startPhase;
        double x = amplitude * Math.cos(arg);
        double y = amplitude * Math.sin(arg);
        return new AcousticWaveValue(x,y);
//        time = getOffset(time);
//        double bestTime = getBestTime(time);
//        return values.get(bestTime);
    }

    public double getAmplitude() {
        return amplitude;
    }

    private void createValuesTables()
    {
//        if (values != null)
//        {
//            return;
//        }
//        double period = 1 / frequency;
//        double step = period / accurateness;
//        values = new HashMap<Double, AcousticWaveValue>();
//        for(double time = 0.0; time < period; time+=step)
//        {
//            double arg = 2*Math.PI * frequency * time + startPhase;
//            double x = amplitude * Math.cos(arg);
//            double y = amplitude * Math.sin(arg);
//            values.put(time,new AcousticWaveValue(x,y));
//        }
    }

    private final double accurateness = 360.0;

    private double frequency;
    private double amplitude;
    private double startPhase;

    private static HashMap<Double,AcousticWaveValue> values; // таблица значений, предполагается сделать шаг в один градус
}