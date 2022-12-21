package org.woehlke.computer.kurzweil.kochsnowflake.view.canvas;

import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNode;
import org.woehlke.computer.kurzweil.kochsnowflake.view.ApplicationFrame;

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

    public ApplicationCanvas(ApplicationFrame tab) {
        this.model = tab.getModel();
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
        g.setColor(Color.BLACK);
        g.drawRect(
           0,0, this.model.getWorldDimensions().getWidth(),  this.model.getWorldDimensions().getHeight()
        );
        g.setColor(Color.RED);
        LinkedListNode startNode = model.getLinkedListNodeContainer().getStartNode();
        LinkedListNode currentNode = model.getLinkedListNodeContainer().getStartNode();
        do {
            g.drawLine(
                currentNode.getLine().getStart().getX(),
                currentNode.getLine().getStart().getY(),
                currentNode.getNext().getLine().getStart().getX(),
                currentNode.getNext().getLine().getStart().getY()
            );
            currentNode = model.getLinkedListNodeContainer().getNext();
        } while (! startNode.equals(currentNode));
    }

    public void update(Graphics g) {
        paint(g);
    }

}
