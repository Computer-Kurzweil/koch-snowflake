package org.woehlke.computer.kurzweil.kochsnowflake.model.koch;

import lombok.Getter;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeDimension;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeVector;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

import java.io.Serializable;

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
        int padding=10;
        int x1 = padding;
        int x2 = this.worldDimensions.getWidth()/2;
        int x3 = this.worldDimensions.getWidth() - padding;
        int y1 = this.worldDimensions.getHeight() - padding;
        int y2 = padding;
        LatticePoint point1 = new LatticePoint(x1,y1);
        LatticePoint point2 = new LatticePoint(x2,y2);
        LatticePoint point3 = new LatticePoint(x3,y1);
        LatticeVector v1 = LatticeVector.ofTwoPoints(point1,point2);
        LatticeVector v2 = LatticeVector.ofTwoPoints(point2,point3);
        LatticeVector v3 = LatticeVector.ofTwoPoints(point3,point1);
        this.startNode = new  LinkedListNode();
        LinkedListNode node2 = new  LinkedListNode();
        LinkedListNode node3 = new  LinkedListNode();
        this.startNode.setLine(v1);
        node2.setLine(v2);
        node3.setLine(v3);
        this.startNode.setNext(node2);
        node2.setNext(node3);
        node3.setNext(this.startNode);
        node2.setPrevious(this.startNode);
        node3.setPrevious(node2);
        startNode.setPrevious(node3);
        this.currentNode = this.startNode;
    }

    public LinkedListNode getNext(){
        currentNode = currentNode.getNext();
        return currentNode;
    }
}
