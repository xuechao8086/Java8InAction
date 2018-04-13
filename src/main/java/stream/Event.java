package stream;

import java.time.Instant;
import java.util.UUID;

/**
 * @author gumi
 * @since 2018/03/13 20:33
 */
public class Event {
    private Instant created = Instant.now();
    private int clientId;
    private UUID uuid;

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
