package org.woehlke.computer.kurzweil.kochsnowflake.control;

import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.view.ApplicationFrame;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * @see KochSnowflakeModel
 * @see ApplicationFrame
 *
 * @see Thread
 * @see Runnable
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class ControllerThread extends Thread implements Runnable {

    private volatile KochSnowflakeModel model;
    private volatile ApplicationFrame tab;

    private final int threadSleepTtime;
    private volatile Boolean goOn;

    public ControllerThread(ApplicationFrame tab) {
        this.tab = tab;
        this.model = this.tab.getModel();
        goOn = Boolean.TRUE;
        this.threadSleepTtime = this.tab.getConfig().getKochsnowflake().getControl().getThreadSleepTime();
    }

    public void run() {
        do {
            if(this.model.step()){
                tab.getCanvas().repaint();
                tab.repaint();
            }
            try {
                sleep( this.threadSleepTtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (goOn());
    }

    public synchronized boolean goOn() {
        return goOn;
    }

    public synchronized void exit() {
        goOn = Boolean.FALSE;
    }

}
