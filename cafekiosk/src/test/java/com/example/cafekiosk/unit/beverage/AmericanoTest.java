package com.example.cafekiosk.unit.beverage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class AmericanoTest {

    @Test
    void getPrice() {

        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4000);
    }

    @Test
    void getName() {
        Americano americano = new Americano();
        assertEquals(americano.getName(), "아메리카노");
        Assertions.assertThat(americano.getName()).isEqualTo("아메리카노");
    }
}