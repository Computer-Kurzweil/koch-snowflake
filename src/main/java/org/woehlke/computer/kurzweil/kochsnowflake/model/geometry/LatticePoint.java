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

    public LatticePoint minus(LatticePoint p) {
        int x = this.getX() - p.getX();
        int y = this.getY() - p.getY();
        return new LatticePoint(x,y);
    }

    public LatticePoint delta(LatticePoint p) {
        int x = p.getX() - this.x;
        int y = p.getY() - this.y;
        return new LatticePoint(x,y);
    }

    public LatticePoint scalarMultiplied(double scalar){
        int x = (int)(this.getX()*scalar);
        int y = (int)(this.getY()*scalar);
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
     * @see <a href="https://en.wikipedia.org/wiki/Rotation_matrix/">Rotation matrix</a>
     */
    public LatticePoint rotationMatrix(LatticePoint nextPoint){
        LatticePoint delta = this.delta(nextPoint);
        double angle = -45.0;
        //System.out.print("thisPoint: "+this.toString());
        //System.out.print(" nextPoint: "+nextPoint.toString());
        double x = delta.getX();
        double y = delta.getY();
        int xx = (int)(x * Math.cos(angle) - y * Math.sin(angle));
        int yy = (int)(x * Math.sin(angle) + y * Math.cos(angle));
        LatticePoint delta2 = new LatticePoint(xx,yy);
        //System.out.print(" --> delta2: "+delta2.toString());
        LatticePoint result = this.add(delta2);
        //System.out.println(" --> result: "+result.toString());
        return result;
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
        point[2] = point[1].rotationMatrix(point[2]);
        return point;
    }

}
