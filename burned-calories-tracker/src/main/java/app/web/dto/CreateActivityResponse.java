package app.web.dto;

import app.model.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateActivityResponse {

    private UUID id;

    private UUID userId;

    private ActivityType activityType;

    private int duration;

    private double caloriesBurned;

    private String createdOn;


}
