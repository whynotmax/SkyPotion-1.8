package eu.skypotion.shop.sellable;

import eu.skypotion.shop.buyable.Buyable;

public interface Sellable extends Buyable {

    void setPrice(double price);

}
