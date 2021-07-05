package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import com.github.TeamRocketBalleBalle.Ricktionary.Resources.Constants.OrderTypeLookupTable;

import java.io.Serializable;

public class ServerPacket extends Packet implements Serializable {
    private final byte orderType;
    private final Order<?> order;

    /**
     * Constructor for making a Server Packet
     * @param packetType the packet type belonging to PacketType
     * @param orderType  the order type belonging to OrderTypeLookupTable
     * @param order the order object
     * @throws IllegalArgumentException if packetType or orderType are beyond range, then exception is thrown
     */
    public ServerPacket(byte packetType, byte orderType, Order<?> order) throws IllegalArgumentException {
        super(packetType);
        if (OrderTypeLookupTable.INITIALISE <= orderType && orderType < OrderTypeLookupTable.LAST) {
            this.orderType = orderType;
        } else {
            throw new IllegalArgumentException(orderType + " is not a valid order type");
        }
        this.order = order;
    }

    public byte getPacketType() {
        return packetType;
    }

    public byte getOrderType() {
        return orderType;
    }

    public Order<?> getOrder() {
        return order;
    }
}
