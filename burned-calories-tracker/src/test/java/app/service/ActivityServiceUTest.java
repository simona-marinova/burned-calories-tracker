package app.service;

import app.model.Activity;
import app.model.ActivityType;
import app.repository.ActivityRepository;
import app.web.dto.CreateActivityRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceUTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    public void calculateCaloriesBurned_whenWalking_thenReturnCorrectCalories() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("WALKING")
                .duration(30)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(105.0, caloriesBurned);
    }

    @Test
    public void calculateCaloriesBurned_whenCardio_thenReturnCorrectCalories() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("CARDIO")
                .duration(45)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(315.0, caloriesBurned);
    }

    @Test
    public void calculateCaloriesBurned_whenWeightTraining_thenReturnCorrectCalories() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("WEIGHT_TRAINING")
                .duration(60)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(300.0, caloriesBurned);
    }


    @Test
    public void calculateCaloriesBurned_whenRunning_thenReturnCorrectCalories() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("RUNNING")
                .duration(20)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(200.0, caloriesBurned);
    }

    @Test
    public void calculateCaloriesBurned_whenUnknownActivity_thenReturnZero() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("SWIMMING")
                .duration(30)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(0.0, caloriesBurned);
    }

    @Test
    public void calculateCaloriesBurned_whenZeroDuration_thenReturnZero() {
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(UUID.randomUUID())
                .activityType("RUNNING")
                .duration(0)
                .build();
        double caloriesBurned = activityService.calculateCaloriesBurned(request);
        assertEquals(0.0, caloriesBurned);
    }


    @Test
    public void createActivity_thenSaveActivityAndReturnIt() {
        UUID userId = UUID.randomUUID();
        CreateActivityRequest request = CreateActivityRequest.builder()
                .userId(userId)
                .activityType("WALKING")
                .duration(30)
                .build();

        ActivityType activityType = ActivityType.WALKING;
        Activity activityToSave = Activity.builder()
                .activityType(activityType)
                .duration(30)
                .burnedCalories(105.0)
                .userId(userId)
                .createdOn(LocalDateTime.now())
                .build();

        when(activityRepository.save(any(Activity.class))).thenReturn(activityToSave);
        Activity activity = activityService.createActivity(request);
        assertEquals(request.getActivityType(), activity.getActivityType().toString());
        assertEquals(request.getDuration(), activity.getDuration());
        assertEquals(request.getUserId(), activity.getUserId());
        assertEquals(105.0, activity.getBurnedCalories()); // Проверяваме изчислените калории
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    public void getActivityHistory_thenReturnListOfActivities() {
        UUID userId = UUID.randomUUID();
        Activity activityOne = Activity.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .build();
        Activity activityTwo = Activity.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .build();
        List<Activity> activities = new ArrayList<>();
        activities.add(activityOne);
        activities.add(activityTwo);
        when(activityRepository.findAllByUserId(userId)).thenReturn(activities);
        List<Activity> result = activityService.getActivityHistory(userId);
        assertEquals(activities, result);
        verify(activityRepository, times(1)).findAllByUserId(userId);
    }

}