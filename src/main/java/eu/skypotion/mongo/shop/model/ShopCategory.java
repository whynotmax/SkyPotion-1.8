package eu.skypotion.mongo.shop.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.skypotion.mongo.shop.model.item.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ShopCategory {

    @Id
    String id;
    String displayName;
    String description;
    Material icon;

    List<ShopItem> shopItems;

}
