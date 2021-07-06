package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import java.io.Serializable;

abstract class CommData<Type extends Serializable> implements Serializable {
    private final Type value;

    public CommData(Type value) {
        this.value = value;
    }

    public Type getValue() {
        return value;
    }
}
