package app.service;

import app.repository.ActivityRepository;
import app.web.dto.CreateActivityRequest;
import app.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.model.Activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public double calculateCaloriesBurned(CreateActivityRequest createActivityRequest) {

        double caloriesPerMinute = 0;

        switch (createActivityRequest.getActivityType()) {
            case "WALKING":
                caloriesPerMinute = 3.5;
                break;
            case "CARDIO":
                caloriesPerMinute = 7;
                break;
            case "WEIGHT_TRAINING":
                caloriesPerMinute = 5;
                break;
            case "RUNNING":
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
                .activityType(DtoMapper.fromString(createActivityRequest.getActivityType()))
                .duration(createActivityRequest.getDuration())
                .burnedCalories(calculateCaloriesBurned(createActivityRequest))
                .userId(createActivityRequest.getUserId())
                .createdOn(LocalDateTime.now())
                .build();

        return activityRepository.save(activity);
    }


    public List<Activity> getActivityHistory(UUID userId) {

        return activityRepository.findAllByUserId(userId);
    }
}
