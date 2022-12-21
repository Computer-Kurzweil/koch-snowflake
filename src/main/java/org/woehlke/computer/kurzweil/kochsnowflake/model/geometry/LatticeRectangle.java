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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LatticeRectangle implements Serializable {

    static final long serialVersionUID = 242L;

    private LatticePoint start;

    private LatticeDimension dimension;

    public static LatticeRectangle of(LatticePoint start, LatticeDimension dimension){
        LatticeRectangle lb = new LatticeRectangle(start, dimension);
        return lb;
    }

    public static LatticeRectangle of(int startX, int  startY, int width, int height){
        LatticePoint start = new LatticePoint(startX, startY);
        LatticeDimension dimension = new LatticeDimension(width, height);
        LatticeRectangle lb = new LatticeRectangle(start, dimension);
        return lb;
    }
}
