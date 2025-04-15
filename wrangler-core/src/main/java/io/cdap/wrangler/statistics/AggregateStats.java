package io.cdap.wrangler.statistics;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * AggregateStats is a utility class that computes statistics like min, max, sum, and average
 * for numerical values parsed from string inputs.
 */
public class AggregateStats {
  private long count;
  private double sum;
  private double min;
  private double max;
 
  public AggregateStats() {
    this.count = 0;
    this.sum = 0.0;
    this.min = Double.MAX_VALUE;
    this.max = Double.MIN_VALUE;
  }

  /**
   * Adds a value to the aggregation statistics.
   *
   * @param valueStr The string representation of the value.
   */
  public void add(String valueStr) {
    if (!NumberUtils.isParsable(valueStr)) {
      return;
    }

    double value = Double.parseDouble(valueStr);
    count++;
    sum += value;
    min = Math.min(min, value);
    max = Math.max(max, value);
  }

  /**
   * Gets the statistics as a map.
   *
   * @return Map with keys: count, sum, avg, min, max
   */
  public Map<String, Object> getStats() {
    Map<String, Object> result = new HashMap<>();
    result.put("count", count);
    result.put("sum", sum);
    result.put("avg", count > 0 ? sum / count : 0);
    result.put("min", count > 0 ? min : 0);
    result.put("max", count > 0 ? max : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format("count=%d, sum=%.2f, avg=%.2f, min=%.2f, max=%.2f",
            count, sum, count > 0 ? sum / count : 0, count > 0 ? min : 0, count > 0 ? max : 0);
  }
}
