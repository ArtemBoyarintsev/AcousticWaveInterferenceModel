package ru.nsu.boyarintsev.Models;


import ru.nsu.boyarintsev.Utils.DoublePoint;

import java.util.ArrayList;

public class Placement {

    public Placement(PlacementCreatingParams params) {
        double length = params.lengthRoom;
        double width = params.widthRoom;
        noiseSoundSources = new ArrayList<SoundSource>();
        if (length < width) {
            double k = width;
            width = length;
            length = k;
        }
        this.length = length;
        this.width = width;
    }
    private boolean checkSourceValidation(SoundSource source)
    {
        DoublePoint position = source.getSourcePosition();
        return position.getX() <= length && position.getX() >= 0 &&
                position.getY() >= 0 && position.getY() <= width;
    }

    public void addSoundSource(SoundSource source)
    {
        if (checkSourceValidation(source)) {
            noiseSoundSources.add(source);
        }
    }

    public SoundSource getSoundSourceByIndex(int index)
    {
        try {
            return noiseSoundSources.get(index);
        }
        catch(IndexOutOfBoundsException er)
        {
            return null;
        }
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public ArrayList<SoundSource> getNoiseSoundSources() {
        return noiseSoundSources;
    }

    public void addSoundSources(ArrayList<SoundSource> soundSources)
    {
        for (SoundSource soundSource : soundSources) {
            addSoundSource(soundSource);
        }
    }

    private double width;
    private double length;


    private ArrayList<SoundSource> noiseSoundSources;
}
