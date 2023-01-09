package org.woehlke.computer.kurzweil.kochsnowflake.view.canvas;

import org.woehlke.computer.kurzweil.kochsnowflake.model.KochSnowflakeModel;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNode;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;


/**
 * Koch Snowflake. A Fractal with self self-similarity.
 * (C) 2006 - 2022 Thomas Woehlke
 * @author Thomas Woehlke
 *
 * @see KochSnowflakeModel
 * @see Dimension
 *
 * @see JComponent
 * @see Graphics
 *
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github Repository</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake/">Maven Project Reports</a>
 *
 * Date: 05.02.2006
 * Time: 00:51:51
 */
public class KochSnowflakeCanvas extends JComponent  {

    @Serial
    private final static long serialVersionUID = 242L;

    private volatile KochSnowflakeModel model;
    private volatile Dimension preferredSize;

    public KochSnowflakeCanvas(KochSnowflakeFrame tab) {
        this.model = tab.getModel();
        int width = this.model.getWorldDimensions().getWidth();
        int height = this.model.getWorldDimensions().getHeight();
        this.preferredSize = new Dimension(width, height);
        Rectangle bounds = new Rectangle(0,0,width,height);
        this.setBounds(bounds);
        this.setSize(this.preferredSize);
        this.setPreferredSize(preferredSize);
    }

    public void paint(Graphics g) {
        System.out.println("paint()");
        this.setSize(this.preferredSize);
        this.setPreferredSize(preferredSize);
        super.paintComponent(g);
        super.setBackground(Color.DARK_GRAY);
        int paddingX = (this.model.getWorldDimensions().getWidth()-this.model.getWorldDimensions().getHeight())/2;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(
            0,0,
            this.model.getWorldDimensions().getWidth(),
            this.model.getWorldDimensions().getHeight()
        );
        g.setColor(Color.BLACK);
        g.fillRect(
            paddingX,0,
            this.model.getWorldDimensions().getHeight(),
            this.model.getWorldDimensions().getHeight()
        );
        g.setColor(Color.RED);
        LinkedListNode startNode = model.getLinkedListNodeContainer().getStartNode();
        LinkedListNode currentNode = startNode;
        int i=0;
        do {
            System.out.println("paint: " + currentNode.toString());
            switch (i%3){
                case 0: g.setColor(Color.RED); break;
                case 1: g.setColor(Color.GREEN); break;
                case 2: g.setColor(Color.BLUE); break;
            }
            i++;
            g.drawLine(
                currentNode.getPoint().getX(),
                currentNode.getPoint().getY(),
                currentNode.getNext().getPoint().getX(),
                currentNode.getNext().getPoint().getY()
            );
            currentNode = currentNode.getNext();
        } while (! startNode.equals(currentNode));
        System.out.println("-------------------------------");
    }

    public void update(Graphics g) {
        paint(g);
    }
}
