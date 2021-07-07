package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Stuff to test:
 *  - in the middle
 *  - at 0
 *  - below zero
 *  - above 0
 *  - below 1
 *  - at 1
 *  - above 1
 *  - when packet = 0
 *  - when packet > 0
 */
class PacketTestNotAbstract extends Packet {

    /**
     * protected Constructor for Packet
     *
     * @param packetType packet type
     * @throws IllegalArgumentException throws exception if packetType
     */
    protected PacketTestNotAbstract(byte packetType) throws IllegalArgumentException {
        super(packetType);
    }
}

class PacketTest {

    @ParameterizedTest
    @CsvSource({"1", "2", "4", "6"})
    public void legitPacketTest(byte packet) {
        PacketTestNotAbstract testObj = new PacketTestNotAbstract(packet);
        Assertions.assertEquals(packet, testObj.packetType);
    }

    @ParameterizedTest
    @CsvSource({"0", "7"})
    public void boundaryTest(byte packet) {
        PacketTestNotAbstract testObj = new PacketTestNotAbstract(packet);
        Assertions.assertEquals(packet, testObj.packetType);
    }

    @ParameterizedTest
    @CsvSource({"-1", "8", "9"})
    public void beyondRange(byte packet) {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> new PacketTestNotAbstract(packet));
    }
}
