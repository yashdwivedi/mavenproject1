package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import java.io.Serializable;

abstract class CommData<Type extends Serializable> implements Serializable {
    private final Type value;
    protected volatile int hashCode;

    public CommData(Type value) {
        this.value = value;
    }

    public Type getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "value=" + value +
                '}';
    }
}
