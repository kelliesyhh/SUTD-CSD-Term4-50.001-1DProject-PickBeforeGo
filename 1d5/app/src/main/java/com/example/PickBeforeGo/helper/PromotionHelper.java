package com.example.PickBeforeGo.helper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class PromotionHelper {

    private final String price;
    private final String promotion;

    public PromotionHelper(String price, String promotion){
        StringBuffer priceSB= new StringBuffer(price);
        StringBuffer promotionSB= new StringBuffer(promotion);

        this.price = priceSB.toString();
        this.promotion = promotionSB.deleteCharAt(promotionSB.length()-1).toString();

        // Testing //
        System.out.println("this price is: " + this.price);
        System.out.println("this promotion is: " + this.promotion);
    }

    public String promoChange() {
        String currentPromoPercent = String.valueOf(1-(Double.parseDouble(this.promotion)/100));
        System.out.println("currentPromoPercent is: " + currentPromoPercent);

        BigDecimal priceBD = new BigDecimal(this.price);
        BigDecimal promotionBD = new BigDecimal(currentPromoPercent);
        int sigFigure = 10;
        MathContext mc = new MathContext(sigFigure, RoundingMode.CEILING);

        String afterPriceBD = priceBD.multiply(promotionBD,mc).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return (afterPriceBD);
    }

}
