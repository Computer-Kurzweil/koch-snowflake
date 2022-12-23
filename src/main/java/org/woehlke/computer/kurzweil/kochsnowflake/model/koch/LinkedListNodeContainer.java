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
        int x1 = padding;
        int x2 = this.worldDimensions.getWidth()/2;
        int x3 = this.worldDimensions.getWidth() - padding;
        int y1 = this.worldDimensions.getHeight() - padding;
        int y2 = padding;
        LatticePoint point1 = new LatticePoint(x1,y1);
        LatticePoint point2 = new LatticePoint(x2,y2);
        LatticePoint point3 = new LatticePoint(x3,y1);
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

    public LinkedListNode getNext(){
        currentNode = currentNode.getNext();
        return currentNode;
    }

    public boolean step() {
        System.out.println("step()");
        boolean repaint = true;
        currentNode = startNode;
        do {
            LinkedListNode nextNode = this.currentNode.getNext();
            int x0 = this.currentNode.getPoint().getX();
            int y0 = this.currentNode.getPoint().getY();
            int x4 = this.currentNode.getNext().getPoint().getX();
            int y4 = this.currentNode.getNext().getPoint().getY();
            int x1 = x0 + ((x4 - x0) / 3);
            int x2 = x0 + ((x4 - x0) * 2 / 3);
            int x3 = x0 + ((x4 - x0) * 2 / 3);
            int y1 = y0 + ((y4 - y0) / 3);
            int y2 = y0 + ((y4 - y0) * 2 / 3);
            int y3 = y0 + ((y4 - y0) * 2 / 3);
            LatticePoint point1 = new LatticePoint(x1, y1);
            LatticePoint point2 = new LatticePoint(x2, y2);
            LatticePoint point3 = new LatticePoint(x3, y3);
            LinkedListNode node0 = this.currentNode;
            LinkedListNode node1 = new LinkedListNode();
            LinkedListNode node2 = new LinkedListNode();
            LinkedListNode node3 = new LinkedListNode();
            LinkedListNode node4 = nextNode;
            node1.setPoint(point1);
            node2.setPoint(point2);
            node3.setPoint(point3);
            node3.setNext(node4);
            node2.setNext(node3);
            node1.setNext(node2);
            node0.setNext(node1);
            this.currentNode = nextNode;
        } while (!this.currentNode.equals(startNode));
        return repaint;
    }
}
