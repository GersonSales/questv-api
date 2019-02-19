package com.questv.api.analytics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/{userId}/analytics")
public class AnalyticsRest {

  private final AnalyticsService analyticsService;


  public AnalyticsRest(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
    assert this.analyticsService != null;
  }

  @GetMapping()
  public AnalyticsDTO get(@PathVariable String userId) {
    return this.analyticsService.getUserAnalytics(userId);
  }

}
