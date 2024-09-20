package eu.skypotion.mongo.codec;

import eu.skypotion.perks.Perk;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.Code;

public class PerkCodec implements Codec<Perk> {
    public Perk decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String name = bsonReader.readString("name");
        String displayName = bsonReader.readString("displayName");
        String description = bsonReader.readString("description");
        long price = (long) bsonReader.readDouble("price");
        return new Perk(name, displayName, description, price);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Perk perk, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("name", perk.getName());
        bsonWriter.writeString("displayName", perk.getDisplayName());
        bsonWriter.writeString("description", perk.getDescription());
        bsonWriter.writeDouble("price", perk.getPrice());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Perk> getEncoderClass() {
        return Perk.class;
    }
}
