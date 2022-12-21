package org.woehlke.computer.kurzweil.kochsnowflake;

import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see KochSnowflakeFrame
 * @see ComputerKurzweilProperties
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 */
public class KochSnowflakeApplication {

    private final KochSnowflakeFrame frame;

    private KochSnowflakeApplication() {
        String conf = "application.yml";
        String jarPath = "target/kochsnowflake.jar";
        ComputerKurzweilProperties config = ComputerKurzweilProperties.propertiesFactory(conf,jarPath);
        frame = new KochSnowflakeFrame(config);
    }

    public void start(){
        this.frame.start();
    }

    /**
     * Starting the Application.
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        KochSnowflakeApplication application = new KochSnowflakeApplication();
        application.start();
    }
}
