package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ByteSize implements Token {
    private final String originalValue;
    private final long bytes;

    public ByteSize(String value) {
        this.originalValue = value;
        this.bytes = parse(value);
    }

    private long parse(String value) {
        value = value.trim().toUpperCase();
        double numeric = Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        if (value.endsWith("KB")) return (long) (numeric * 1024);
        if (value.endsWith("MB")) return (long) (numeric * 1024 * 1024);
        if (value.endsWith("GB")) return (long) (numeric * 1024 * 1024 * 1024);
        if (value.endsWith("B")) return (long) numeric;
        throw new IllegalArgumentException("Unsupported byte size format: " + value);
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public Object value() {
        return bytes;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("bytes", bytes);
        object.addProperty("originalValue", originalValue);
        return object;
    }
}