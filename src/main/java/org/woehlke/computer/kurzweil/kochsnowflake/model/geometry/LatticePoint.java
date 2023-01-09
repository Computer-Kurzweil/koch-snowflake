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

    public LatticePoint plus(LatticePoint p) {
        int xx = this.getX() + p.getX();
        int yy = this.getY() + p.getY();
        return new LatticePoint(xx,yy);
    }

    public LatticePoint minus(LatticePoint p) {
        int xx = this.getX() - p.getX();
        int yy = this.getY() - p.getY();
        return new LatticePoint(xx,yy);
    }

    public static LatticePoint minus(LatticePoint thisLatticePoint, LatticePoint other ) {
        int xx = thisLatticePoint.getX() - other.getX();
        int yy = thisLatticePoint.getY() - other.getY();
        return new LatticePoint(xx,yy);
    }

    public LatticePoint delta(LatticePoint p) {
        int xx = p.getX() - this.getX();
        int yy = p.getY() - this.getY();
        return new LatticePoint(xx,yy);
    }

    public LatticePoint scalarMultiplied(double scalar){
        double xx = Integer.valueOf(this.getX()).doubleValue();
        double yy = Integer.valueOf(this.getY()).doubleValue();
        long xxx = Math.round( xx * scalar);
        long yyy = Math.round( yy * scalar);
        int ixxx = Long.valueOf( xxx ).intValue();
        int iyyy = Long.valueOf( yyy ).intValue();
        return new LatticePoint(ixxx,iyyy);
    }

    public void normalize(LatticePoint p) {
        this.x %= p.getX();
        this.y %= p.getY();
    }

    public LatticePoint copy() {
        return new LatticePoint(
            this.getX(),
            this.getY()
        );
    }

    public LatticeDimension toLatticeDimension() {
        return new LatticeDimension(
            this.getX(),
            this.getY()
        );
    }

    public static LatticePoint of(LatticePoint p) {
        return new LatticePoint(p.getX(),p.getY());
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
        LatticePoint deltaVector = this.delta(nextPoint);
        double angle = 45.0d;
        double dx = Integer.valueOf(deltaVector.getX()).doubleValue();
        double dy = Integer.valueOf(deltaVector.getY()).doubleValue();
        double xxx = dx * Math.cos(angle) - dy * Math.sin(angle);
        double yyy = dx * Math.sin(angle) + dy * Math.cos(angle);
        int xx = Double.valueOf(xxx).intValue();
        int yy = Double.valueOf(yyy).intValue();
        LatticePoint rotatedDeltaVector = new LatticePoint(xx,yy);
        LatticePoint rotatedNextPoint = this.plus(rotatedDeltaVector);
        return rotatedNextPoint;
    }

    public LatticePoint[] getNewPoints(LatticePoint nextPoint){
        double oneThird = 1.0d / 3.0d;
        double twoThird = 2.0d / 3.0d;
        LatticePoint deltaVector = this.delta( nextPoint );
        LatticePoint deltaOneThird = deltaVector.scalarMultiplied(oneThird);
        LatticePoint deltaTwoThird = deltaVector.scalarMultiplied(twoThird);
        LatticePoint[] points = new LatticePoint[5];
        points[0] = this.copy();
        points[1] = this.copy().plus(deltaOneThird);
        points[2] = this.copy().plus(deltaTwoThird);
        points[3] = this.copy().plus(deltaTwoThird);
        points[4] = nextPoint.copy();
        points[2] = points[1].copy().rotationMatrix(points[3]);
        return points;
    }

}
