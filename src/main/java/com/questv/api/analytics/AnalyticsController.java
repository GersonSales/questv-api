package com.questv.api.analytics;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.questv.api.uitl.Strings.*;

@Api(
    value = ANALYTICS_API_NAME,
    description = ANALYTICS_API_DESCRIPTION,
    tags = {ANALYTICS_API_NAME})
@RestController
@RequestMapping(value = "/user/{userId}/analytics")
public class AnalyticsController {

  private final AnalyticsService analyticsService;


  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
    assert this.analyticsService != null;
  }

  @GetMapping()
  public AnalyticsDTO get(@PathVariable String userId) {
    return this.analyticsService.getUserAnalytics(userId);
  }

}
