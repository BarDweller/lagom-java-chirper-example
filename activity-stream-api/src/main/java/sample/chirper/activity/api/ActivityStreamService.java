/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package sample.chirper.activity.api;

import sample.chirper.chirp.api.Chirp;

import akka.stream.javadsl.Source;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import static com.lightbend.lagom.javadsl.api.Service.*;

public interface ActivityStreamService extends Service {

  ServiceCall<NotUsed, Source<Chirp, ?>> getLiveActivityStream(String userId);

  ServiceCall<NotUsed, Source<Chirp, ?>> getHistoricalActivityStream(String userId);

  ServiceCall<NotUsed, String> health();

  ServiceCall<NotUsed, Source<String, ?>> infinite();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("activityservice").withCalls(
        pathCall("/api/activity/:userId/live", this::getLiveActivityStream),
        pathCall("/api/activity/:userId/history", this::getHistoricalActivityStream),
        pathCall("/health", this::health),
        pathCall("/infinite", this::infinite)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
