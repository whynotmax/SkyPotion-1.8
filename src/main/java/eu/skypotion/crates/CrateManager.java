package eu.skypotion.crates;

import eu.skypotion.crates.model.Crate;
import eu.skypotion.crates.repository.CrateRepository;
import eu.skypotion.util.DateUtil;
import eu.skypotion.util.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateManager {

    CrateRepository crateRepository;
    Map<String, Crate> crateMap;

    public CrateManager(CrateRepository crateRepository) {
        this.crateRepository = crateRepository;
        this.crateMap = this.crateRepository.findAll().stream().collect(HashMap::new, (map, crate) -> map.put(crate.getName(), crate), HashMap::putAll);
    }

    public Crate get(String name) {
        return this.crateMap.get(name);
    }

    public void createCrate(String name, String createdBy) {
        Crate crate = new Crate();
        crate.setName(name);
        crate.setDisplayName(name);
        crate.setDisplayItem(ItemBuilder.AIR);
        crate.setEnabled(true);
        crate.setCollection(1);
        crate.setCreatedBy(createdBy);
        crate.setTimeCreated(System.currentTimeMillis());
        crate.setLastUpdated(System.currentTimeMillis());
        crate.setItems(new ArrayList<>());

        this.crateRepository.save(crate);
        this.crateMap.put(name, crate);
    }

    public void save(Crate crate) {
        this.crateRepository.save(crate);
        this.crateMap.put(crate.getName(), crate);
    }

    public void save(String name) {
        Crate crate = this.crateMap.get(name);
        if (crate != null) {
            this.crateRepository.save(crate);
        }
    }

    public void delete(String name) {
        this.crateRepository.delete(this.crateMap.get(name));
        this.crateMap.remove(name);
    }

    public ItemStack[] get(String name, int amount) {
        Crate crate = this.crateMap.get(name);
        if (crate == null) {
            return new ItemStack[0];
        }


        ItemBuilder itemBuilder = ItemBuilder.of(crate.getDisplayItem());
        itemBuilder.withAmount(1);

        itemBuilder.withName(crate.getDisplayName());

        itemBuilder.withLore(
                "§r",
                "§7Kollektion: §e" + crate.getCollection(),
                "§r",
                "§7Diese Crate wurde am §e" + DateUtil.formatDate(crate.getTimeCreated()) + " §7erstellt.",
                "§7Sie enthält §e" + crate.getItems().size() + " §7Items.",
                "§r",
                "§aRechtsklick§8 ┃ §7Öffnen",
                "§aLinksklick§8 ┃ §7Vorschau öffnen",
                "§aShift + Linksklick§8 ┃ §7Mehr Informationen",
                "§r"
        );

        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            itemStacks.add(itemBuilder);
        }
        return itemStacks.toArray(new ItemStack[0]);
    }

}
