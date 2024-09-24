package eu.skypotion.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemStackCodec implements Codec<ItemStack> {
    @Override
    public ItemStack decode(BsonReader bsonReader, DecoderContext decoderContext) {
        ItemStack itemStack = null;
        bsonReader.readStartDocument();
        String base64 = bsonReader.readString("base64");
        bsonReader.readEndDocument();
        if (base64 != null) {
            itemStack = fromBase64(base64);
        }
        return itemStack;
    }

    @Override
    public void encode(BsonWriter bsonWriter, ItemStack itemStack, EncoderContext encoderContext) {
        String base64 = toBase64(itemStack);
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("base64", base64);
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<ItemStack> getEncoderClass() {
        return ItemStack.class;
    }

    private String toBase64(ItemStack itemStack) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    private ItemStack fromBase64(String base64) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            return (ItemStack) dataInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to decode item stack.", e);
        }
    }
}
