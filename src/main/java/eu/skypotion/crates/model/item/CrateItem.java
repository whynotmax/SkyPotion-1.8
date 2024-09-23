package eu.skypotion.crates.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CrateItem {

    ItemStack itemStack;
    double chance;

    /**
     * The message to broadcast when the item is won.
     */
    @Nullable
    String broadcastMessage;

    /**
     * The command to execute when the item is won.
     * @apiNote When set, player only receives the item, not the actual item.
     */
    @Nullable
    String command;

    /**
     * The amount of tokens to give to the player when the item is won.
     * @apiNote When set, player only receives tokens, not the actual item.
     */
    @Nullable
    double tokensToGive;

}
