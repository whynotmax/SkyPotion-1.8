package eu.skypotion.util.builder;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder extends ItemStack {

    public static final ItemBuilder AIR = new ItemBuilder(Material.AIR);

    private ItemBuilder(Material material) {
        super(material);
    }

    private ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    private ItemBuilder(Material material, int amount, short durability) {
        super(material, amount, durability);
    }

    public static ItemBuilder of(Material material) {
        if (material == Material.SKULL_ITEM) {
            return new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
        }
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder withAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public ItemBuilder withName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        ItemMeta meta = this.getItemMeta();
        meta.setLore(List.of(lore));
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        ItemMeta meta = this.getItemMeta();
        meta.setLore(lore);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withDurability(short durability) {
        this.setDurability(durability);
        return this;
    }

    public ItemBuilder withData(int data) {
        this.getData().setData((byte) data);
        return this;
    }

    public ItemBuilder withGlow() {
        this.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        this.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
        this.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder withItemFlag(ItemFlag itemFlag) {
        this.getItemMeta().addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder allItemFlags() {
        this.getItemMeta().addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemBuilder withUnbreakable() {
        //TODO: Make this work
        //this.getItemMeta().setUnbreakable(true);
        return this;
    }

    //TODO: Add more methods

    public ItemBuilder withColor(Color color) {
        if (this.getItemMeta() instanceof LeatherArmorMeta) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
            meta.setColor(color);
            this.setItemMeta(meta);
        }
        return this;
    }

//    public ItemBuilder withSkullOwner(GameProfile profile) {
//        if (this.getItemMeta() instanceof SkullMeta) {
//            SkullMeta meta = (SkullMeta) this.getItemMeta();
//            try {
//                Field field;
//                field = meta.getClass().getDeclaredField("profile");
//                field.setAccessible(true);
//                field.set(meta, profile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            this.setItemMeta(meta);
//        }
//        return this;
//    }

    public ItemBuilder withSkullOwner(String owner) {
        if (this.getItemMeta() instanceof SkullMeta) {
            SkullMeta meta = (SkullMeta) this.getItemMeta();
            meta.setOwner(owner);
            this.setItemMeta(meta);
        }
        return this;
    }

//    public ItemBuilder withSkullTexture(String textureUrl) {
//        if (this.getItemMeta() instanceof SkullMeta) {
//            SkullMeta meta = (SkullMeta) this.getItemMeta();
//            StringBuilder s_url = new StringBuilder();
//            s_url.append("https://textures.minecraft.net/texture/").append(textureUrl);
//            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
//            byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", s_url.toString()).getBytes());
//            profile.getProperties().put("textures", new Property("textures", new String(data)));
//            try {
//                Field field;
//                field = meta.getClass().getDeclaredField("profile");
//                field.setAccessible(true);
//                field.set(meta, profile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            this.setItemMeta(meta);
//        }
//        return this;
//    }

    public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::addEnchantment);
        return this;
    }

    public ItemBuilder withItemFlags(ItemFlag... itemFlags) {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(itemFlags);
        this.setItemMeta(meta);
        return this;
    }

}
