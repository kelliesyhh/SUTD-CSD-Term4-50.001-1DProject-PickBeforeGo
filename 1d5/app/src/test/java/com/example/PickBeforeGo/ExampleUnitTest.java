package com.example.PickBeforeGo;

import static org.junit.Assert.assertEquals;

import com.example.PickBeforeGo.helper.PromotionHelper;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void promotion_isCorrect() {
        String price = "10.00";
        String promotion = "20%";
        assertEquals("8.00", new PromotionHelper(price, promotion).promoChange());
    }
}