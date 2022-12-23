package org.woehlke.computer.kurzweil.kochsnowflake.control;

import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

/**
 * Koch Snowflake. A Fractal with self self-similarity.
 * (C) 2006 - 2022 Thomas Woehlke
 * @author Thomas Woehlke
 *
 * @see KochSnowflakeFrame
 * @see KochSnowflakeModel
 *
 * @see Thread
 * @see Runnable
 *
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github Repository</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake/">Maven Project Reports</a>
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class ControllerThread extends Thread implements Runnable {

    private volatile KochSnowflakeModel model;
    private volatile KochSnowflakeFrame view;

    private final int threadSleepTtime;
    private final int maxIterations;
    private volatile Boolean goOn;

    public ControllerThread(KochSnowflakeFrame view) {
        this.view = view;
        this.model = this.view.getModel();
        goOn = Boolean.TRUE;
        this.threadSleepTtime = this.view.getConfig().getKochsnowflake().getControl().getThreadSleepTime();
        this.maxIterations = this.view.getConfig().getKochsnowflake().getControl().getMaxIterations();
        //this.maxIterations = 2;
    }

    public void run() {
        int i = 0;
        while (goOn() &&  i < this.maxIterations) {
            i++;
            if(this.model.step()){
                view.getCanvas().repaint();
                view.repaint();
            }
            try {
                sleep( this.threadSleepTtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean goOn() {
        return goOn;
    }

    public synchronized void exit() {
        goOn = Boolean.FALSE;
    }

}
