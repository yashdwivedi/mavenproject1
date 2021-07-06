package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import java.io.Serializable;

public class Reply<T extends Serializable> extends CommData<T> {
    /**
     *  Constructor for the Reply object
     * @param value the value to store in the Reply packet
     */
    public Reply(T value) {
        super(value);
    }
}
