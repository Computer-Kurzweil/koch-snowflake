package org.woehlke.computer.kurzweil.kochsnowflake.model.koch;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeDimension;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
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
        int marginY = 30;
        int padding = 10;
        int squareSide = this.worldDimensions.getHeight();
        double triangleSideDouble = ((squareSide - (2.0 * padding)) * 4.0) / 5.0;
        double triangleHeightDouble = (triangleSideDouble * Math.sqrt(3.0d)) / 2.0;
        int triangleSide = Double.valueOf(Math.abs(triangleSideDouble)).intValue();
        int triangleHeight = Double.valueOf(Math.abs(triangleHeightDouble)).intValue();
        marginY += padding;
        int marginX = (this.worldDimensions.getWidth() - triangleSide) / 2;
        int x1 = marginX;
        int y1 = marginY + triangleHeight;
        int x2 = marginX + triangleSide;
        int y2 = marginY + triangleHeight;
        int x3 = marginX + triangleSide / 2;
        int y3 = marginY;
        LatticePoint leftBottom = new LatticePoint(x1,y1);
        LatticePoint rightBottom = new LatticePoint(x2,y2);
        LatticePoint upperCenter = new LatticePoint(x3,y3);
        LinkedListNode leftBottomNode = new LinkedListNode(leftBottom);
        LinkedListNode rightBottomNode = new LinkedListNode(rightBottom);
        LinkedListNode upperCenterNode = new LinkedListNode(upperCenter);
        leftBottomNode.setNext(rightBottomNode);
        rightBottomNode.setNext(upperCenterNode);
        upperCenterNode.setNext(leftBottomNode);
        this.startNode = leftBottomNode;
        this.currentNode = this.startNode;
    }

    public void step() {
        System.out.println("step");
        this.currentNode = this.startNode;
        do {
            LinkedListNode nodeNext = currentNode.getNext();
            LatticePoint nextPoint = nodeNext.getPoint();
            LatticePoint[] newPoints = currentNode.getPoint().getNewPoints(nextPoint);
            LinkedListNode[] node = new LinkedListNode[5];
            node[0] = currentNode;
            node[1] = new LinkedListNode(newPoints[1]);
            node[2] = new LinkedListNode(newPoints[2]);
            node[3] = new LinkedListNode(newPoints[3]);
            node[4] = nodeNext;
            node[0].setNext(node[1]);
            node[1].setNext(node[2]);
            node[2].setNext(node[3]);
            node[3].setNext(node[4]);
            currentNode = nodeNext;
        } while (!currentNode.equals(startNode));
    }
}
