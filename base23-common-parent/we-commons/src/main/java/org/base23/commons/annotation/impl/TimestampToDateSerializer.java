package org.base23.commons.annotation.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimestampToDateSerializer extends JsonSerializer<Number> {


  private static final DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
      .withZone(ZoneId.of("UTC"));

  @Override
  public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value != null) {
      gen.writeString(formatter.format(Instant.ofEpochSecond(value.longValue())));
    }
  }
}
