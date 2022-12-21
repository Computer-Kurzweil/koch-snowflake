package org.woehlke.computer.kurzweil.kochsnowflake.view.canvas;

import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNode;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;


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
 * @see Dimension
 *
 * @see JComponent
 * @see Graphics
 *
 * Date: 05.02.2006
 * Time: 00:51:51
 */
public class ApplicationCanvas extends JComponent {

    @Serial
    private final static long serialVersionUID = 242L;

    private volatile KochSnowflakeModel model;
    private volatile Dimension preferredSize;

    public ApplicationCanvas(KochSnowflakeModel model) {
        this.model = model;
        int width = this.model.getWorldDimensions().getWidth();
        int height = this.model.getWorldDimensions().getHeight();
        this.preferredSize = new Dimension(width, height);
        this.setSize(this.preferredSize);
        this.setPreferredSize(preferredSize);
    }

    public void paint(Graphics g) {
        this.setSize(this.preferredSize);
        this.setPreferredSize(preferredSize);
        super.paintComponent(g);
        super.setBackground(Color.BLACK);
        LinkedListNode startNode = model.getLinkedListNodeContainer().getStartNode();
        LinkedListNode line = model.getLinkedListNodeContainer().getStartNode();
        do {
            g.drawLine(
                line.getLine().getStart().getX(),
                line.getLine().getStart().getY(),
                line.getLine().getRelative().getX(),
                line.getLine().getRelative().getY()
            );
            line = model.getLinkedListNodeContainer().getNext();
        } while (startNode.equals(line));
        /*
        int red = 0;
        int green = 0;
        int blue = 0;
        for(int y = 0; y < app.getWorldDimensions().getY(); y++){
            for(int x = 0; x < app.getWorldDimensions().getX(); x++){
                blue = (((app.getCellStatusFor(x,y))*4)%256);
                Color stateColor = new Color(red, green, blue);
                g.setColor(stateColor);
                g.drawLine(x,y,x,y);
            }
        }
        */
    }

    public void update(Graphics g) {
        paint(g);
    }

}
