package app.web.mapper;

import app.model.Activity;
import app.web.dto.CreateActivityResponse;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DtoMapper {

    public static CreateActivityResponse fromActivity (Activity activity) {

        String dateString = String.valueOf(activity.getCreatedOn());
        LocalDateTime dateTime = LocalDateTime.parse(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String formattedDate = dateTime.format(formatter);

        return CreateActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getActivityType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .createdOn(formattedDate)
                .build();
    }


}



