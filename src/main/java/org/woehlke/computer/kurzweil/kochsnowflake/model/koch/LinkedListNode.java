package org.woehlke.computer.kurzweil.kochsnowflake.model.koch;

import lombok.*;
import org.woehlke.computer.kurzweil.kochsnowflake.model.geometry.LatticeVector;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkedListNode implements Serializable {

    static final long serialVersionUID = 242L;

    private LatticeVector line;

    private LinkedListNode next;

    private LinkedListNode previous;
}
