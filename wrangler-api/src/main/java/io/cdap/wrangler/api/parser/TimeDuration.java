package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TimeDuration implements Token {
    private final String originalValue;
    private final long milliseconds;

    public TimeDuration(String value) {
        this.originalValue = value;
        this.milliseconds = parse(value);
    }

    private long parse(String value) {
        value = value.trim().toLowerCase();
        double numeric = Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        if (value.endsWith("ms")) return (long) numeric;
        if (value.endsWith("s")) return (long) (numeric * 1000);
        if (value.endsWith("m")) return (long) (numeric * 1000 * 60);
        throw new IllegalArgumentException("Unsupported time duration format: " + value);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public Object value() {
        return milliseconds;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("milliseconds", milliseconds);
        object.addProperty("originalValue", originalValue);
        return object;
    }
}