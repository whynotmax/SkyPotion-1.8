package eu.skypotion.mongo.shop.model.item;

import eu.skypotion.shop.buyable.Buyable;
import eu.skypotion.shop.sellable.Sellable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShopItem implements Sellable {

    String id;
    ItemStack itemStack;
    double price;

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }
}
