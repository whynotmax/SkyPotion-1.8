package eu.skypotion.mongo.betakey;

import eu.skypotion.mongo.betakey.exception.InvalidBetaKeyException;
import eu.skypotion.mongo.betakey.model.BetaKey;
import eu.skypotion.mongo.betakey.repository.BetaKeyRepository;
import eu.skypotion.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BetaKeyManager {

    List<BetaKey> betaKeys;
    BetaKeyRepository betaKeyRepository;

    public BetaKeyManager(BetaKeyRepository betaKeyRepository) {
        this.betaKeyRepository = betaKeyRepository;
        this.betaKeys = betaKeyRepository.findAll();
    }

    public BetaKey[] generateKeys(int amount) {
        BetaKey[] keys = new BetaKey[amount];
        for (int i = 0; i < amount; i++) {
            BetaKey key = new BetaKey();
            key.setKey(StringUtil.generateRandomString(5, '-', 3));
            keys[i] = key;
            betaKeys.add(key);
            betaKeyRepository.save(key);
        }
        return keys;
    }

    public BetaKey[] getKeys() {
        return betaKeys.toArray(new BetaKey[0]);
    }

    public void linkKey(String key, UUID uuid) throws InvalidBetaKeyException {
        BetaKey betaKey = betaKeys.stream().filter(bk -> bk.getKey().equals(key)).findFirst().orElse(null);
        if (betaKey != null) {
            if (!isKeyValid(key)) {
                throw new InvalidBetaKeyException(key, uuid);
            }
            if (!betaKey.isLinked()) {
                betaKey.setLinkedTo(uuid);
            } else {
                throw new InvalidBetaKeyException(key, uuid);
            }
            betaKey.setLinkedTo(uuid);
            betaKeyRepository.save(betaKey);
            return;
        }
        throw new InvalidBetaKeyException(key, uuid);
    }

    public boolean isKeyValid(String key) {
        return betaKeys.stream().anyMatch(bk -> bk.getKey().equals(key));
    }

    public boolean isKeyLinked(String key) {
        return betaKeys.stream().anyMatch(bk -> bk.getKey().equals(key) && bk.isLinked());
    }

    public void deleteKey(BetaKey betaKey) {
        betaKeys.remove(betaKey);
        betaKeyRepository.delete(betaKey);
    }
}
