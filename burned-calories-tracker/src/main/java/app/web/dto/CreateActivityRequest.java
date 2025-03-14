package app.web.dto;

import app.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateActivityRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private ActivityType activityType;

    @NotNull
    private int duration;


}
