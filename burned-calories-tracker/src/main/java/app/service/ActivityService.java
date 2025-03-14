package app.service;

import app.model.ActivityType;
import app.repository.ActivityRepository;
import app.web.dto.CreateActivityRequest;
import org.springframework.stereotype.Service;
import app.model.Activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public double calculateCaloriesBurned(CreateActivityRequest createActivityRequest) {

        double caloriesPerMinute = 0;

        switch (createActivityRequest.getActivityType()) {
            case ActivityType.WALKING:
                caloriesPerMinute = 3.5;
                break;
            case ActivityType.CARDIO:
                caloriesPerMinute = 7;
                break;
            case ActivityType.WEIGHT_TRAINING:
                caloriesPerMinute = 5;
                break;
            case ActivityType.RUNNING:
                caloriesPerMinute = 10;
                break;
            default:
                caloriesPerMinute = 0;
                break;
        }
        return createActivityRequest.getDuration() * caloriesPerMinute;
    }

    public Activity createActivity(CreateActivityRequest createActivityRequest) {
        Activity activity = Activity.builder()
                .activityType(createActivityRequest.getActivityType())
                .duration(createActivityRequest.getDuration())
                .caloriesBurned(calculateCaloriesBurned(createActivityRequest))
                .userId(createActivityRequest.getUserId())
                .createdOn(LocalDateTime.now())
                .build();

        return activityRepository.save(activity);
    }


    public List<Activity> getActivityHistory(UUID userId) {

        return activityRepository.findAllByUserId(userId);
    }
}
