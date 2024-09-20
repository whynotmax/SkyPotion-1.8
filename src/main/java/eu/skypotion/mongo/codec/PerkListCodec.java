package eu.skypotion.mongo.codec;

import eu.skypotion.perks.Perk;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.Code;

import java.util.ArrayList;
import java.util.List;

public class PerkListCodec implements Codec<List<Perk>> {

    @Override
    public void encode(BsonWriter bsonWriter, List<Perk> perks, EncoderContext encoderContext) {
        bsonWriter.writeStartArray();
        for (Perk perk : perks) {
            encoderContext.encodeWithChildContext(new PerkCodec(), bsonWriter, perk);
        }
        bsonWriter.writeEndArray();
    }

    @Override
    public List<Perk> decode(BsonReader bsonReader, DecoderContext decoderContext) {
        List<Perk> perks = new ArrayList<>();
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != org.bson.BsonType.END_OF_DOCUMENT) {
            perks.add(decoderContext.decodeWithChildContext(new PerkCodec(), bsonReader));
        }
        bsonReader.readEndArray();
        return perks;
    }

    @Override
    public Class<List<Perk>> getEncoderClass() {
        List<Perk> perks = new ArrayList<>();
        return (Class<List<Perk>>) perks.getClass();
    }
}
