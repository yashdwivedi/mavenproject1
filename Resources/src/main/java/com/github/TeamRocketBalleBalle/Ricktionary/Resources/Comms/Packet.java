package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.PacketType;

import java.io.Serializable;

abstract class Packet implements Serializable {
    protected final byte packetType;

    /**
     * protected Constructor for Packet
     * @param packetType packet type
     * @throws IllegalArgumentException throws exception if packetType
     */
    protected Packet(byte packetType) throws IllegalArgumentException {
        if (PacketType.INITIALISE <= packetType && packetType < PacketType.LAST) {
            this.packetType = packetType;
        } else {
            throw new IllegalArgumentException(packetType + " is not valid packet type");
        }
    }
}
