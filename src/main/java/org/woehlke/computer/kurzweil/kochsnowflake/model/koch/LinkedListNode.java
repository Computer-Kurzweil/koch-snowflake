package org.woehlke.computer.kurzweil.kochsnowflake.model.koch;

import lombok.*;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticePoint;

import java.io.Serializable;

/**
 * Koch Snowflake. A Fractal with self self-similarity.
 * (C) 2006 - 2022 Thomas Woehlke
 * @author Thomas Woehlke
 *
 * @see LatticePoint
 *
 * @see <a href="https://github.com/Computer-Kurzweil/kochsnowflake">Github Repository</a>
 * @see <a href="https://java.woehlke.org/kochsnowflake/">Maven Project Reports</a>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkedListNode implements Serializable {

    static final long serialVersionUID = 242L;

    private LatticePoint point;

    private LinkedListNode next;
}
