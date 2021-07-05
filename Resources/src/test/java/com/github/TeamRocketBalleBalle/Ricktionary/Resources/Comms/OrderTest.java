package com.github.TeamRocketBalleBalle.Ricktionary.Resources.Comms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    /**
     * To test:
     *  - When objects are different
     *  - when objects are same instance but different val
     *  - when objects are same
     */
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testEqualsInt(Integer input) {
        Order<Integer> obj = (Order<Integer>) new Order<>(input);
        Order<Integer> testObj = new Order<>(5);
        assertNotEquals(testObj, obj);
        assertNotEquals(testObj, input);

        Order<String> testObjString = new Order<>("name");
        assertNotEquals(testObjString, obj);

        Order<Boolean> booleanOrder = new Order<>(true);
        assertNotEquals(booleanOrder, obj);

        Order<Integer> integerOrder = new Order<Integer>(1);
        assertEquals(integerOrder, obj);
    }
}