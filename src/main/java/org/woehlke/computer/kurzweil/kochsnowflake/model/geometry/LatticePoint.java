package org.woehlke.computer.kurzweil.kochsnowflake.model.geometry;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNode;
import org.woehlke.computer.kurzweil.kochsnowflake.view.KochSnowflakeFrame;

import java.io.Serializable;

/**
 * A Point is used to define the Position of Cell or as a Vector for defining Dimensions.
 *
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
 *
 * Date: 04.02.2006
 * Time: 23:47:05
 */
@Log4j2
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LatticePoint implements Serializable {

    static final long serialVersionUID = 242L;

    /**
     * Horizontal X-Coordinate. Also used as Width;
     */
    private int x;

    /**
     * Vertical Y-Coordinate. Also used as Height;
     */
    private int y;

    public LatticePoint(LatticePoint other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public void absoluteValue() {
        x *= Integer.signum(x);
        y *= Integer.signum(y);
    }

    public void killNagative() {
        absoluteValue();
    }

    public void plus(LatticePoint p) {
        this.x += p.getX();
        this.y += p.getY();
        absoluteValue();
    }

    public LatticePoint add(LatticePoint p) {
        int x = this.getX() + p.getX();
        int y = this.getY() + p.getY();
        return new LatticePoint(x,y);
    }

    public void substract(LatticePoint p) {
        this.x += p.getX();
        this.y += p.getY();
    }

    public void mninus(LatticePoint p) {
        int helpX = this.x;
        int helpY = this.y;
        this.x = p.getX()-helpX;
        this.y = p.getY()-helpY;

    }

    public LatticePoint delta(LatticePoint p) {
        int helpX = this.x;
        int helpY = this.y;
        int x = p.getX()-helpX;
        int y = p.getY()-helpY;
        return new LatticePoint(x,y);
    }

    public void normalize(LatticePoint p) {
        this.x %= p.getX();
        this.y %= p.getY();
    }

    public LatticePoint copy() {
        return new LatticePoint(this);
    }

    public LatticeDimension toLatticePoint() {
        return new LatticeDimension(
            this.getX(),
            this.getY()
        );
    }

    public static LatticePoint of(int x, int y) {
        return new LatticePoint(x,y);
    }

    public static LatticePoint of(LatticeDimension p) {
        return new LatticePoint(p.getWidth(), p.getHeight());
    }

    public static LatticePoint delta(LatticePoint thisPoint, LatticePoint to) {
        return thisPoint.delta(to);
    }

    /**
     * Get Neighbourhood.
     * @param max - limit the dimensions of the world around
     * @return The Set of Points belonging to the Neighbourhood of the position given by this Point Object.
     */
    public LatticePoint[] getNeighbourhood(LatticePoint max){
        LatticePoint neighbourhood[] = new LatticePoint[9];
        int maxX = max.getX();
        int maxY = max.getY();
        neighbourhood[0]= new LatticePoint((this.x+maxX-1) % maxX,(this.y+maxY-1) % maxY);
        neighbourhood[1]= new LatticePoint((this.x+maxX-1) % maxX,this.y);
        neighbourhood[2]= new LatticePoint((this.x+maxX-1) % maxX,(this.y+maxY+1) % maxY);
        neighbourhood[3]= new LatticePoint(this.x,(this.y+maxY-1) % maxY);
        neighbourhood[4]= new LatticePoint(this.x,this.y);
        neighbourhood[5]= new LatticePoint(this.x,(this.y+maxY+1) % maxY);
        neighbourhood[6]= new LatticePoint((this.x+maxX+1) % maxX,(this.y+maxY-1) % maxY);
        neighbourhood[7]= new LatticePoint((this.x+maxX+1) % maxX,this.y);
        neighbourhood[8]= new LatticePoint((this.x+maxX+1) % maxX,(this.y+maxY+1) % maxY);
        return neighbourhood;
    }

    /**
     * @see <a href="https://en.wikipedia.org/wiki/Rotation_matrix/">Rotation matrix</a>
     */
    public static LatticePoint rotationMatrix(LatticePoint thisPoint, LatticePoint nextPoint){
        LatticePoint delta = LatticePoint.delta(thisPoint, nextPoint);
        double angle = 45.0;
        System.out.print("thisPoint: "+thisPoint.toString());
        System.out.print(" nextPoint: "+nextPoint.toString());
        double x = delta.getX();
        double y = delta.getY();
        int xx = (int)(x * Math.cos(angle) - y * Math.sin(angle));
        int yy = (int)(x * Math.sin(angle) + y * Math.cos(angle));
        LatticePoint delta2 = new LatticePoint(xx,yy);
        System.out.print(" --> delta2: "+delta2.toString());
        thisPoint.add(delta2);
        System.out.println(" --> nextPoint: "+nextPoint.toString());
        return thisPoint;
    }

    public LatticePoint[] getNewParts(LatticePoint nextPoint){
        LatticePoint delta = LatticePoint.delta(this, nextPoint);
        LatticePoint delta1 = delta.scalarMultiplied(1.0/3.0);
        LatticePoint delta2 = delta.scalarMultiplied(2.0/3.0);
        LatticePoint[] point = new LatticePoint[5];
        point[0] = this.copy();
        point[1] = this.add(delta1);
        point[2] = this.add(delta2);
        point[3] = this.add(delta2);
        point[4] = nextPoint;
        point[2] = LatticePoint.rotationMatrix(point[1], point[2]);
        return point;
    }

    public LatticePoint scalarMultiplied(double scalar){
        LatticePoint o = this.copy();
        o.setX((int)(o.getX()*scalar));
        o.setY((int)(o.getY()*scalar));
        return o;
    }
}
