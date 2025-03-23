package app.web.dto;

import app.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateActivityRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private String activityType;

    @NotNull
    private int duration;


}
