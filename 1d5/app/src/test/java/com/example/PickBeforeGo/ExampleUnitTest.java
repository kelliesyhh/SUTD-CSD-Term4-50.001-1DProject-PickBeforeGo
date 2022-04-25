package com.example.PickBeforeGo;

import static org.junit.Assert.assertEquals;

import com.example.PickBeforeGo.activities.SignUpActivity;
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

    @Test
    public void hashPW_isCorrect() {
        String password = "testPWhash";
        System.out.println(SignUpActivity.sha256(password));
        assertEquals("ce88a9d24cc6d87cfe1799902e13a0faffccf9b394886b9585ee48f8425c3a99", SignUpActivity.sha256(password));
    }
}