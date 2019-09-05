package ru.nsu.boyarintsev.Conrollers;

import ru.nsu.boyarintsev.Models.*;
import ru.nsu.fit.g13201.boyarintsev.Models.*;


public class NoiseController {

    public NoiseController()
    {
        placement = new Placement(getDefaultPlacementParams());
        noiseModel = new NoiseModel(placement,0);
    }

    public void setPlacementParams(PlacementCreatingParams params)
    {
        placement = new Placement(params);
        noiseModel.setPlacement(placement);
    }

    public NoiseModel getNoiseModel() {
        return noiseModel;
    }

    public void addNoiseSource(SoundSourceCreatingParams params)
    {
        noiseModel.addSoundSource(new SoundSource(params));
    }

    public SoundSource getSoundSourceByIndex(int index)
    {
        return placement.getSoundSourceByIndex(index);
    }

    public boolean updateNoiseSource(int index,SoundSourceCreatingParams params)
    {
        return noiseModel.updateSoundSourceWithIndex(index,params);
    }


    private PlacementCreatingParams getDefaultPlacementParams()
    {
        PlacementCreatingParams ret = new PlacementCreatingParams();
        ret.widthRoom = 6.0;
        ret.lengthRoom = 4.0;
        return ret;
    }

    private Placement placement;
    private NoiseModel noiseModel;
}
