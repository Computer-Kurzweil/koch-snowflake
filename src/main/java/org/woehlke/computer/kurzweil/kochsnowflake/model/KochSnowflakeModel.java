package org.woehlke.computer.kurzweil.kochsnowflake.model;

import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.fractal.GaussianNumberPlane;
import org.woehlke.computer.kurzweil.kochsnowflake.model.common.Point;
import org.woehlke.computer.kurzweil.kochsnowflake.view.state.ApplicationStateMachine;
import org.woehlke.computer.kurzweil.kochsnowflake.model.turing.KochSnowflakeTuringMachine;
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
 * @see GaussianNumberPlane
 * @see KochSnowflakeTuringMachine
 * @see ApplicationStateMachine
 *
 * @see ComputerKurzweilProperties
 * @see ApplicationFrame
 *
 * Created by tw on 16.12.2019.
 */
public class KochSnowflakeModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile KochSnowflakeTuringMachine kochSnowflakeTuringMachine;
    private volatile ApplicationStateMachine applicationStateMachine;

    private volatile ComputerKurzweilProperties config;
    private volatile ApplicationFrame frame;

    public KochSnowflakeModel(ComputerKurzweilProperties config, ApplicationFrame frame) {
        this.config = config;
        this.frame = frame;
        this.gaussianNumberPlane = new GaussianNumberPlane(this);
        this.kochSnowflakeTuringMachine = new KochSnowflakeTuringMachine(this);
        this.applicationStateMachine = new ApplicationStateMachine();
    }

    public synchronized boolean click(Point c) {
        applicationStateMachine.click();
        boolean repaint = true;
        switch (applicationStateMachine.getApplicationState()) {
            case MANDELBROT:
                kochSnowflakeTuringMachine.start();
                repaint = false;
                break;
            case JULIA_SET:
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                break;
        }
        return repaint;
    }

    public synchronized boolean step() {
        boolean repaint = false;
        switch (applicationStateMachine.getApplicationState()) {
            case MANDELBROT:
                repaint = kochSnowflakeTuringMachine.step();
                break;
            case JULIA_SET:
                break;
        }
        return repaint;
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x, y);
    }

    public Point getWorldDimensions() {
        int scale = config.getMandelbrotZoom().getView().getScale();
        int width = scale * config.getMandelbrotJulia().getView().getWidth();
        int height = scale * config.getMandelbrotJulia().getView().getHeight();
        return new Point(width, height);
    }

    public GaussianNumberPlane getGaussianNumberPlane() {
        return gaussianNumberPlane;
    }

}
