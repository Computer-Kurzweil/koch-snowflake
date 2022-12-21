package org.woehlke.computer.kurzweil.kochsnowflake.model;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeDimension;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNodeContainer;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

import java.io.Serializable;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * @see ComputerKurzweilProperties
 * @see KochSnowflakeFrame
 *
 * Created by tw on 16.12.2019.
 */
@Getter
public class KochSnowflakeModel implements Serializable {

    static final long serialVersionUID = 242L;

    private volatile KochSnowflakeFrame tab;
    private volatile LinkedListNodeContainer linkedListNodeContainer;

    private final LatticeDimension worldDimensions;

    public KochSnowflakeModel(KochSnowflakeFrame tab) {
        this.tab = tab;
        ComputerKurzweilProperties config = tab.getConfig();
        int scale = config.getKochsnowflake().getView().getScale();
        int width = scale * config.getKochsnowflake().getView().getWidth();
        int height = scale * config.getKochsnowflake().getView().getHeight();
        this.worldDimensions = LatticeDimension.of(width,height);
        this.linkedListNodeContainer = new LinkedListNodeContainer(tab, this.worldDimensions);
    }

    public synchronized boolean step() {
        boolean repaint = false;
        return repaint;
    }

    public void click(LatticePoint c) {

    }

    public void start(){
        this.linkedListNodeContainer.start();
    }
}
