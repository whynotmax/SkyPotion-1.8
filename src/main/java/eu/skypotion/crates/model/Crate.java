package eu.skypotion.crates.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.skypotion.crates.model.item.CrateItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Crate {

    @Id
    String name;
    String displayName;
    ItemStack displayItem;

    boolean enabled;

    String collection;
    long timeCreated;
    String createdBy;
    long lastUpdated;

    List<CrateItem> items;

}
