package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ServerPacketTest {

    @ParameterizedTest
    @CsvSource({"1,1,test string", "2,2,test string"})
    public void legitOrderTypeTest(byte packetType, byte orderType, String name) {
        Assertions.assertDoesNotThrow(
                () -> new ServerPacket(packetType, orderType, new Order<String>(name)));
    }

    @ParameterizedTest
    @CsvSource({"0,0", "7,3"})
    public void boundaryTest(byte packetType, byte orderType) {
        Assertions.assertDoesNotThrow(
                () -> new ServerPacket(packetType, orderType, new Order<Byte>(orderType)));
    }

    @ParameterizedTest
    @CsvSource({"0,-1", "1,8", "2,9"})
    public void beyondRange(byte packet, byte orderType) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new ServerPacket(packet, orderType, new Order<Byte>(packet)));
    }
}
