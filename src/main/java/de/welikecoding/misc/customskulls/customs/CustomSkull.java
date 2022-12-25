package de.welikecoding.misc.customskulls.customs;

import de.welikecoding.misc.customskulls.CustomSkulls;
import de.welikecoding.misc.customskulls.message.Message;
import de.welikecoding.misc.customskulls.message.MessageBuilder;

import java.util.UUID;

public class CustomSkull {

    private final String key;
    private final UUID uuid;
    // default value (missing texture)
    private String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJhZGM1NmY1ZjRkOTFmYmFhY2YzNTQ2OGI4OWMzYzQ3MGYwZmUzY2NkYzFhMDE5ZjM2MDMwNDNmNjIxZDUifX19";
    private final String displayName;

    public CustomSkull(String key) {
        this.key = key;
        this.uuid = UUID.randomUUID();
        CustomSkulls main = CustomSkulls.getInstance();
        if(main.getCustomSkullFileManager().isKeyValid(key)) {
            value = main.getCustomSkullFileManager().getValue(key);
            displayName = key;
        } else {
            displayName = new MessageBuilder(Message.CS_MISSING_TEXTURE_DISPLAYNAME).buildWithoutPrefix();
        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }
}
