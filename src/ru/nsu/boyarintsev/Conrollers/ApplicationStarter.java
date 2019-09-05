package ru.nsu.boyarintsev.Conrollers;

import ru.nsu.boyarintsev.Models.SoundSourceCreatingParams;
import ru.nsu.boyarintsev.Views.MainWindow;
import java.awt.*;


public class ApplicationStarter
{
    public static void main(String[] args)
    {
        NoiseController noiseController = new NoiseController();
        noiseController.addNoiseSource(getSoundSourceCreatingParams(0,2,0));
        noiseController.addNoiseSource(getSoundSourceCreatingParams(0.14,2,180));
        new MainWindow(noiseController, noiseController.getNoiseModel(),createColors());
    }

    private static SoundSourceCreatingParams getSoundSourceCreatingParams(double x, double y, int startPhase)
    {
        SoundSourceCreatingParams ret = new SoundSourceCreatingParams();
        ret.startPhase = startPhase;
        ret.xNoise = x;
        ret.yNoise = y;
        ret.amplitude = 3.0;
        ret.frequency = 700.0;
        return ret;
    }

    private static int[] createColors()
    {
        int[] colors = {
                new Color(255,255,255).getRGB(),
                new Color(0,255,0).getRGB(),
                new Color(0,0,255).getRGB(),
                new Color(255,255,0).getRGB(),
                new Color(255,0,0).getRGB(),
                new Color(0,0,0).getRGB()
        };
        return colors;
    }
}
