package ru.nsu.boyarintsev.Utils;

/**
 * Created by Артем on 15.03.2017.
 */
public class OkCancelPressed
{
    private Runnable runnable;

    public OkCancelPressed(Runnable runnable) {
        this.runnable = runnable;
    }
    public Runnable getIfOkPressed()
    {
        return runnable;
    }

    public void cancelPressed()
    {
        //TODO здесь что нужно сделать
    }
}
