package nhom1.gem.com.exceptionplugin.network.dto;

import java.util.UUID;

/**
 * Base DTO
 * Created by neo on 2/5/2016.
 */
public class BaseDTO {
    protected UUID uuid;

    public BaseDTO() {
    }

    public BaseDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
