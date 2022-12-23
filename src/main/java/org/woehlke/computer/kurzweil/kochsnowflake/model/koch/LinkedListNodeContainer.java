package org.woehlke.computer.kurzweil.kochsnowflake.model.koch;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeDimension;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeVector;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

import java.io.Serializable;

/**
 * Koch Snowflake. A Fractal with self self-similarity.
 * (C) 2006 - 2022 Thomas Woehlke
 * @author Thomas Woehlke
 *
 * @see ComputerKurzweilProperties
 * @see KochSnowflakeFrame
 * @see LatticeDimension
 *
 * @see LinkedListNode
 *
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github Repository</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake/">Maven Project Reports</a>
 */
@Getter
public class LinkedListNodeContainer implements Serializable {

    static final long serialVersionUID = 242L;

    private final KochSnowflakeFrame tab;

    private final LatticeDimension worldDimensions;

    private LinkedListNode startNode;

    private LinkedListNode currentNode;

    public LinkedListNodeContainer(KochSnowflakeFrame tab, LatticeDimension worldDimensions){
        this.tab = tab;
        this.worldDimensions = worldDimensions;
    }

    public void start(){
        int padding = 30;
        int paddingX = (this.worldDimensions.getWidth()-this.worldDimensions.getHeight())/2;
        int x1 = paddingX + padding;
        int x2 = paddingX + this.worldDimensions.getHeight()/2;
        int x3 = paddingX + this.worldDimensions.getHeight() - padding;
        double myHeight01 = this.worldDimensions.getHeight() * (4.0 / 5.0);
        int myHeight02 = (int)(( myHeight01 / 2.0 ) * Math.sqrt(3.0d));
        int myHeight = myHeight02 - (2*padding);
        int y1 = padding + myHeight;
        int y2 = padding;
        int y3 = padding + myHeight;
        LatticePoint point1 = new LatticePoint(x1,y1);
        LatticePoint point2 = new LatticePoint(x2,y2);
        LatticePoint point3 = new LatticePoint(x3,y3);
        LinkedListNode node1 = new  LinkedListNode();
        LinkedListNode node2 = new  LinkedListNode();
        LinkedListNode node3 = new  LinkedListNode();
        node1.setPoint(point1);
        node2.setPoint(point2);
        node3.setPoint(point3);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node1);
        this.startNode = node1;
        this.currentNode = this.startNode;
    }

    public boolean step() {
        System.out.println("step");
        boolean repaint = true;
        this.currentNode = this.startNode;
        do {
            LinkedListNode workNodeNext = currentNode.getNext();
            LatticePoint[] points = currentNode.getPoint().getNewParts(workNodeNext.getPoint());
            LinkedListNode[] node = new LinkedListNode[5];
            node[0] = currentNode;
            node[1] = new LinkedListNode();
            node[2] = new LinkedListNode();
            node[3] = new LinkedListNode();
            node[4] = workNodeNext;
            node[1].setPoint(points[1]);
            node[2].setPoint(points[2]);
            node[3].setPoint(points[3]);
            node[0].setNext(node[1]);
            node[1].setNext(node[2]);
            node[2].setNext(node[3]);
            node[3].setNext(node[4]);
            currentNode = workNodeNext;
        } while (!currentNode.equals(startNode));
        return repaint;
    }

}
