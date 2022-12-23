package org.woehlke.computer.kurzweil.kochsnowflake.model.geometry;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.kochsnowflake.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.kochsnowflake.model.koch.LinkedListNode;
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
@Log4j2
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LatticeVector implements Serializable {

    static final long serialVersionUID = 242L;

    private LatticePoint start;

    private LatticePoint relative;

    public static LatticeVector of(LatticePoint start, LatticePoint to){
        LatticeVector lb = new LatticeVector(start, to);
        return lb;
    }

    public static LatticeVector ofTwoPoints(int startX, int  startY, int toX, int toY){
        LatticePoint start = new LatticePoint(startX, startY);
        LatticePoint to = new LatticePoint(toX, toY);
        return LatticeVector.ofTwoPoints(start,to);
    }

    public static LatticeVector ofTwoPoints(LatticePoint startpoint, LatticePoint endpoint) {
        LatticeVector result = new LatticeVector();
        LatticePoint relative = endpoint.copy();
        relative.minus(startpoint.copy());
        result.setStart(startpoint);
        result.setRelative(relative);
        return result;
    }
}
