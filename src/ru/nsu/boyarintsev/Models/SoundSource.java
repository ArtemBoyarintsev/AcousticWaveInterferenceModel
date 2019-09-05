package ru.nsu.boyarintsev.Models;

import ru.nsu.boyarintsev.Utils.AcousticWaveValue;
import ru.nsu.boyarintsev.Utils.DoublePoint;

/**
 * Created by Артем on 04.03.2017.
 */
public class SoundSource {
    public SoundSource(SoundSourceCreatingParams params)
    {
        acousticWave = new AcousticWave(params.frequency,params.amplitude,params.startPhase);
        sourcePosition = new DoublePoint(params.xNoise,params.yNoise);
    }

    public void setNewParams(SoundSourceCreatingParams params)
    {
        if (params.startPhase != null) {
            acousticWave.setStartPhase(params.startPhase);
        }
        if (params.frequency != null) {
            acousticWave.setFrequency(params.frequency);
        }
        if (params.amplitude != null) {
            acousticWave.setAmplitude(params.amplitude);
        }
        if (params.xNoise != null) {
            sourcePosition.setX(params.xNoise);
        }
        if (params.yNoise != null) {
            sourcePosition.setY(params.yNoise);
        }
    }

    public AcousticWaveValue getValue(Double time)
    {
        return acousticWave.getValue(time);
    }

    public Double getAmplitude()
    {
        return acousticWave.getAmplitude();
    }

    public Double getFrequency()
    {
        return acousticWave.getFrequency();
    }

    public Integer getStartPhase()
    {
        return acousticWave.getStartPhase();
    }

    public Double getXCoordinate()
    {
        return sourcePosition.getX();
    }

    public Double getYCoordinate()
    {
        return sourcePosition.getY();
    }

    public DoublePoint getSourcePosition() {
        return sourcePosition;
    }

    public double getMaxNoiseValue()
    {
        return acousticWave.getAmplitude();
    }


    private AcousticWave acousticWave;
    private DoublePoint sourcePosition;
}
