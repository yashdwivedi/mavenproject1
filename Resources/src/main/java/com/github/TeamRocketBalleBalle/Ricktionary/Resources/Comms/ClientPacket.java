package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

public class ClientPacket extends Packet {
    private final Order<?> replyingTo;
    private final Reply<?> reply;

    /**
     * Constructor for ClientPacket
     * @param packetType the packet type belonging to PacketType
     * @param replyingTo the Order object this packet is replying to
     * @param reply the reply object
     */
    public ClientPacket(byte packetType, Order<?> replyingTo, Reply<?> reply) {
        super(packetType);
        this.replyingTo = replyingTo;
        this.reply = reply;
    }

    public Order<?> getReplyingTo() {
        return replyingTo;
    }

    public Reply<?> getReply() {
        return reply;
    }

    @Override
    public String toString() {
        return "ClientPacket{"
                + "replyingTo="
                + replyingTo
                + ", reply="
                + reply
                + ", packetType="
                + packetType
                + '}';
    }
}
