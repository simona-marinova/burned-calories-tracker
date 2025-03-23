package app.web.mapper;

import app.model.Activity;
import app.model.ActivityType;

import app.web.dto.CreateActivityResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DtoMapperUTest {

    @Test
    void givenHappyPath_whenMappingStringToActivityTypeWalking() {
        String input = "WALKING";
        ActivityType result = DtoMapper.fromString(input);
        assertEquals(ActivityType.WALKING, result);
    }

    @Test
    void givenHappyPath_whenMappingStringToActivityTypeRunning() {
        String input = "RUNNING";
        ActivityType result = DtoMapper.fromString(input);
        assertEquals(ActivityType.RUNNING, result);
    }
    @Test
    void givenHappyPath_whenMappingStringToActivityTypeCardio() {
        String input = "CARDIO";
        ActivityType result = DtoMapper.fromString(input);
        assertEquals(ActivityType.CARDIO, result);
    }

    @Test
    void givenHappyPath_whenMappingStringToActivityTypeWeightTraining() {
        String input = "WEIGHT_TRAINING";
        ActivityType result = DtoMapper.fromString(input);
        assertEquals(ActivityType.WEIGHT_TRAINING, result);
    }

    @Test
    void givenInvalidInput_whenMappingStringToActivityType_thenThrowException() {
        String input = "SWIMMING";
        assertThrows(IllegalStateException.class, () -> DtoMapper.fromString(input));
    }

    @Test
    public void fromActivity_thenMapActivityToCreateActivityResponseCorrectly() {
        UUID userId = UUID.randomUUID();
        LocalDateTime createdOn = LocalDateTime.now();
        Activity activity = Activity.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .activityType(ActivityType.RUNNING)
                .duration(60)
                .burnedCalories(600.0)
                .createdOn(createdOn)
                .build();
        CreateActivityResponse response = DtoMapper.fromActivity(activity);
        assertEquals(activity.getId(), response.getId());
        assertEquals(activity.getUserId(), response.getUserId());
        assertEquals(activity.getActivityType(), response.getActivityType());
        assertEquals(activity.getDuration(), response.getDuration());
        assertEquals(activity.getBurnedCalories(), response.getBurnedCalories());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String formattedDate = createdOn.format(formatter);
        assertEquals(formattedDate, response.getCreatedOn());
    }


}
