package app.web;

import app.service.ActivityService;
import app.web.dto.CreateActivityRequest;
import app.web.dto.CreateActivityResponse;
import app.web.mapper.DtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.model.Activity;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;


    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<CreateActivityResponse> createActivity(@RequestBody CreateActivityRequest createActivityRequest) {
        Activity activity = activityService.createActivity(createActivityRequest);
        CreateActivityResponse createActivityResponse = DtoMapper.fromActivity(activity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createActivityResponse);
    }


    @GetMapping
    public ResponseEntity<List<CreateActivityResponse>> getActivityHistory(@RequestParam(name = "userId") UUID userId) {

        List<CreateActivityResponse> activityHistory = activityService.getActivityHistory(userId).stream().map(activity -> DtoMapper.fromActivity(activity)).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(activityHistory);
    }


}
