package java8inaction.chapter11_completablefuture;

/**
 * Created by c6s on 18-7-1
 */
public class Quote {
    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        return new Quote(split[0], Double.valueOf(split[1]), Discount.Code.valueOf(split[2]));
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}
