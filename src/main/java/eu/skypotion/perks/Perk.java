package eu.skypotion.perks;

import eu.skypotion.shop.buyable.Buyable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class Perk implements Buyable {

    String name;
    String displayName;
    String description;

    long price;

    public static final Perk NO_FOOD = new Perk("no_food", "Kein Essen mehr!", "Du benötigst kein Essen mehr.", 20000000);

    @SneakyThrows
    public Perk clone() {
        return (Perk) super.clone();
    }
}
