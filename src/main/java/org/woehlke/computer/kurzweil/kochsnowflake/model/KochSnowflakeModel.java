package org.woehlke.computer.kurzweil.kochsnowflake.model;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeDimension;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNodeContainer;
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
 * @see KochSnowflakeTuringMachine
 *
 * @see ComputerKurzweilProperties
 * @see ApplicationFrame
 *
 * Created by tw on 16.12.2019.
 */
@Getter
public class KochSnowflakeModel {

    private volatile LinkedListNodeContainer linkedListNodeContainer;
    private volatile ComputerKurzweilProperties config;
    private volatile ApplicationFrame tab;

    private final LatticeDimension worldDimensions;

    public KochSnowflakeModel(ComputerKurzweilProperties config, ApplicationFrame tab) {
        this.config = config;
        this.tab = tab;
        this.linkedListNodeContainer = new LinkedListNodeContainer(tab);
        int scale = config.getKochsnowflake().getView().getScale();
        int x = scale * config.getKochsnowflake().getView().getWidth();
        int y = scale * config.getKochsnowflake().getView().getHeight();
        this.worldDimensions = LatticeDimension.of(x,y);
    }

    public synchronized boolean step() {
        boolean repaint = false;
        return repaint;
    }

    public void click(LatticePoint c) {

    }

    public void start(){

    }
}
