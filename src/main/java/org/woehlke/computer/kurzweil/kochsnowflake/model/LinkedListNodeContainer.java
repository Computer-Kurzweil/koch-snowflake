package org.woehlke.computer.kurzweil.kochsnowflake.model;

import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeVector;
import org.woehlke.computer.kurzweil.kochsnowflake.view.ApplicationFrame;

import java.io.Serializable;

public class LinkedListNodeContainer implements Serializable {

    private final ApplicationFrame tab;

    private LinkedListNode startNode;

    private LinkedListNode currentNode;

    public LinkedListNodeContainer(ApplicationFrame tab){
        this.tab = tab;
        setup();
    }

    public void setup(){
        int x1=10;
        int x2=this.tab.getCanvas().getX()/2;
        int x3=this.tab.getCanvas().getX()-10;
        int y1=10;
        int y3=this.tab.getCanvas().getY()-10;
        LatticePoint point1 = new LatticePoint(x1,y3);
        LatticePoint point2 = new LatticePoint(x2,y1);
        LatticePoint point3 = new LatticePoint(x3,y3);
        LatticeVector v1 = LatticeVector.ofTwoPoints(point1,point2);
        LatticeVector v2 = LatticeVector.ofTwoPoints(point2,point3);
        LatticeVector v3 = LatticeVector.ofTwoPoints(point3,point1);
        startNode = new  LinkedListNode();
        LinkedListNode node2 = new  LinkedListNode();
        LinkedListNode node3 = new  LinkedListNode();
        startNode.setLine(v1);
        node2.setLine(v2);
        node3.setLine(v3);
        startNode.setNext(node2);
        node2.setNext(node3);
        node3.setNext(startNode);
        node2.setPrevious(startNode);
        node3.setPrevious(node2);
        startNode.setPrevious(node3);
        currentNode = startNode;
    }
}
