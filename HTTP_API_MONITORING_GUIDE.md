# HTTP REST API - Monitoring, Metrics & Alerting Guide

## 1. HTTP API Health and User Experience Indicators

### What Metrics Are Useful?

#### Request Metrics
- **Request Rate**: Requests per second/minute (throughput)
- **Request Duration**: Response time percentiles (p50, p95, p99, p999)
- **Request Size**: Payload sizes (request/response bytes)
- **Request Count by Status**: HTTP status code distribution (2xx, 4xx, 5xx)

#### Error Metrics
- **Error Rate**: Percentage of failed requests (4xx + 5xx)
- **Error Count by Type**: Breakdown by status code (404, 500, 503, etc.)
- **Error Rate by Endpoint**: Which endpoints are failing most

#### Availability Metrics
- **Uptime**: Service availability percentage
- **Health Check Status**: Internal health endpoint status
- **Dependency Health**: Database, cache, external API availability

#### Performance Metrics
- **Latency Percentiles**: p50, p95, p99, p999 response times
- **Slow Requests**: Requests exceeding thresholds (e.g., >1s, >5s)
- **Timeout Rate**: Requests timing out
- **Throughput**: Successful requests per second

#### Resource Metrics
- **CPU Usage**: CPU utilization percentage
- **Memory Usage**: Heap, non-heap, off-heap memory
- **Thread Pool**: Active threads, queue size, rejections
- **Connection Pool**: Active connections, idle connections, wait time
- **GC Metrics**: GC frequency, duration, pause times

#### Business Metrics
- **Active Users**: Concurrent or active users
- **API Usage by Client**: Mobile vs web client distribution
- **Feature Usage**: Endpoint popularity, feature adoption

### What Indicates This System is Healthy?

#### Green Signals (Healthy System)
1. **High Availability**
   - Uptime > 99.9% (less than 43 minutes downtime/month)
   - Health checks passing consistently
   - All critical dependencies available

2. **Good Performance**
   - p95 latency < SLA threshold (e.g., <500ms for most endpoints)
   - p99 latency < 2x p95 (no tail latency issues)
   - Error rate < 0.1% (99.9% success rate)
   - No timeout errors

3. **Stable Resource Usage**
   - CPU usage < 70% average, < 90% peak
   - Memory usage stable (no memory leaks)
   - GC pause times < 100ms, frequency reasonable
   - Thread pool utilization < 80%
   - Connection pool healthy (no connection exhaustion)

4. **Consistent Throughput**
   - Request rate within expected range
   - No sudden spikes or drops
   - Graceful handling of traffic patterns

5. **Low Error Rates**
   - 4xx errors < 1% (mostly client errors)
   - 5xx errors < 0.1% (server errors rare)
   - No error spikes or cascading failures

### What Tells You Users Are Having a Bad Experience?

#### Red Flags (Poor User Experience)
1. **High Latency**
   - p95 latency > 1 second (users notice delay)
   - p99 latency > 5 seconds (users frustrated)
   - Tail latency spikes (some users hit very slow requests)
   - Increasing latency trends over time

2. **High Error Rates**
   - Error rate > 1% (users seeing failures)
   - 5xx errors > 0.5% (server-side failures)
   - Specific endpoints failing (feature broken)
   - Error rate increasing over time

3. **Timeouts**
   - Timeout rate > 0.1% (requests not completing)
   - Increasing timeout frequency
   - Timeouts concentrated on specific endpoints

4. **Availability Issues**
   - Health checks failing intermittently
   - Service unavailable errors (503)
   - Circuit breakers opening
   - Degraded mode activations

5. **Resource Exhaustion**
   - Thread pool rejections (requests queued too long)
   - Connection pool exhaustion
   - Out of memory errors
   - CPU throttling

6. **Client-Specific Issues**
   - Mobile clients experiencing higher error rates
   - Specific geographic regions with issues
   - Certain user segments affected

### What Helps You Understand Capacity and Scaling Needs?

#### Capacity Indicators
1. **Throughput Trends**
   - Request rate growth over time
   - Peak vs average traffic patterns
   - Traffic growth rate (week-over-week, month-over-month)

2. **Resource Utilization Trends**
   - CPU usage trending upward
   - Memory usage increasing
   - Connection pool approaching limits
   - Thread pool utilization increasing

3. **Performance Degradation Under Load**
   - Latency increasing with load
   - Error rate increasing with load
   - Resource saturation points
   - Breaking points (when system fails)

4. **Saturation Metrics**
   - Queue lengths increasing
   - Wait times increasing
   - Resource contention
   - Backpressure indicators

5. **Scaling Triggers**
   - Approaching resource limits (CPU > 70%, Memory > 80%)
   - Latency degrading under load
   - Error rate increasing with traffic
   - Approaching connection/thread limits

---

## 2. Design Prometheus Metrics

### Metric Types

#### Counter
- **Use for**: Cumulative values that only increase
- **Examples**: Total requests, total errors, total bytes transferred
- **Operations**: `rate()`, `increase()`, `irate()`

#### Gauge
- **Use for**: Values that can go up or down
- **Examples**: Current CPU usage, active connections, queue size, memory usage
- **Operations**: Direct value, `delta()`, `deriv()`

#### Histogram
- **Use for**: Distribution of measurements (latency, sizes)
- **Examples**: Request duration, response size, payload size
- **Operations**: `histogram_quantile()`, `rate()`
- **Benefits**: Captures percentiles, distribution shape

#### Summary
- **Use for**: Pre-computed quantiles (less common)
- **Examples**: Similar to histogram but with pre-computed quantiles
- **Note**: Less flexible than histogram, rarely used

### Metric Naming Conventions

#### Prometheus Naming Rules
- Use `snake_case` (not camelCase)
- Use base unit (seconds, bytes, not milliseconds, kilobytes)
- Include metric type suffix: `_total`, `_count`, `_sum`, `_bucket`, `_bytes`, `_seconds`
- Use descriptive names: `http_requests_total` not `requests`

#### Naming Patterns
```
<namespace>_<subsystem>_<metric_name>_<unit>_<type_suffix>
```

Examples:
- `http_requests_total` (Counter)
- `http_request_duration_seconds` (Histogram)
- `http_request_size_bytes` (Histogram)
- `http_active_connections` (Gauge)
- `jvm_memory_used_bytes` (Gauge)
- `jvm_gc_duration_seconds` (Summary/Histogram)

### Labels/Dimensions

#### Essential Labels
- `method`: HTTP method (GET, POST, PUT, DELETE)
- `endpoint`: API endpoint path (normalized, e.g., `/api/v1/users/{id}`)
- `status`: HTTP status code (200, 404, 500, etc.)
- `status_class`: Status class (2xx, 4xx, 5xx)

#### Useful Labels
- `client_type`: Client type (mobile, web, api)
- `user_agent`: User agent category (browser, app version)
- `region`: Geographic region
- `environment`: Environment (prod, staging, dev)
- `instance`: Server instance identifier
- `version`: API version (v1, v2)

#### Cardinality Considerations
- **Low Cardinality**: status_class (3 values), method (4-5 values), client_type (2-3 values)
- **Medium Cardinality**: endpoint (10-100 values), status (10-20 values)
- **High Cardinality**: user_agent (1000s), instance (10-100s), full endpoint with IDs (unbounded)

### Cardinality Risk

#### What is Cardinality?
Cardinality = number of unique time series = product of all label value combinations

Example:
```
http_requests_total{method="GET", endpoint="/users", status="200"} = 1000
http_requests_total{method="GET", endpoint="/users", status="404"} = 50
http_requests_total{method="POST", endpoint="/users", status="201"} = 200
```

If you have:
- 5 methods × 50 endpoints × 10 status codes = 2,500 time series
- Add client_type (3) = 7,500 time series
- Add instance (10) = 75,000 time series

#### Cardinality Risks
1. **High Memory Usage**: Each time series consumes memory
2. **Query Performance**: High cardinality slows queries
3. **Storage Costs**: More time series = more storage
4. **Prometheus Limits**: Default limit ~1M active time series

#### Best Practices
1. **Avoid High-Cardinality Labels**
   - Don't use user IDs, session IDs, request IDs as labels
   - Don't use full URLs with dynamic parameters
   - Use normalized endpoint paths

2. **Use Label Filtering**
   - Filter out high-cardinality labels in recording rules
   - Use `keep()` or `drop()` relabel configs

3. **Limit Label Combinations**
   - Only include labels that add value
   - Remove labels that don't help with alerting/querying

4. **Monitor Cardinality**
   - Track `prometheus_tsdb_head_series` metric
   - Alert on high cardinality

### Example Metric Definitions

#### Request Counter
```prometheus
# Type: Counter
# Name: http_requests_total
# Labels: method, endpoint, status_class, status
# Cardinality: ~500 (5 methods × 50 endpoints × 2 status_classes)
http_requests_total{method="GET", endpoint="/api/v1/users", status_class="2xx", status="200"} 15234
```

#### Request Duration Histogram
```prometheus
# Type: Histogram
# Name: http_request_duration_seconds
# Labels: method, endpoint, status_class
# Buckets: [0.005, 0.01, 0.025, 0.05, 0.1, 0.25, 0.5, 1, 2.5, 5, 10]
# Cardinality: ~250 (5 methods × 50 endpoints × 1 status_class × 11 buckets)
http_request_duration_seconds_bucket{method="GET", endpoint="/api/v1/users", status_class="2xx", le="0.1"} 14500
http_request_duration_seconds_bucket{method="GET", endpoint="/api/v1/users", status_class="2xx", le="0.5"} 15100
http_request_duration_seconds_bucket{method="GET", endpoint="/api/v1/users", status_class="2xx", le="+Inf"} 15234
http_request_duration_seconds_sum{method="GET", endpoint="/api/v1/users", status_class="2xx"} 1234.56
http_request_duration_seconds_count{method="GET", endpoint="/api/v1/users", status_class="2xx"} 15234
```

#### Active Connections Gauge
```prometheus
# Type: Gauge
# Name: http_active_connections
# Labels: instance
# Cardinality: ~10 (number of instances)
http_active_connections{instance="api-server-1"} 45
```

#### Error Rate (Derived)
```prometheus
# Derived metric using rate() and division
# rate(http_requests_total{status_class="5xx"}[5m]) / rate(http_requests_total[5m])
```

---

## 3. Define Alerts

### What Deserves to Wake Someone Up at 3am?

#### Critical Alerts (Page On-Call)
These indicate **immediate user impact** or **service degradation**:

1. **Service Down**
   - Service completely unavailable
   - Health checks failing across all instances
   - All endpoints returning 5xx errors

2. **High Error Rate**
   - 5xx error rate > 1% for 5+ minutes
   - Critical endpoints failing (>10% error rate)
   - Cascading failures detected

3. **Complete Outage**
   - Database unavailable
   - All instances down
   - Critical dependency failure

4. **Security Incident**
   - Unusual authentication failures spike
   - Potential DDoS attack
   - Unauthorized access attempts

5. **Data Loss Risk**
   - Database connection pool exhausted
   - Write failures to persistent storage
   - Replication lag critical

#### Warning Alerts (Investigate During Business Hours)
These indicate **potential issues** or **degradation**:

1. **Elevated Error Rate**
   - 5xx error rate > 0.5% for 15+ minutes
   - Specific endpoints showing errors
   - Error rate trending upward

2. **Performance Degradation**
   - p95 latency > SLA threshold
   - Latency trending upward
   - Slow query detection

3. **Resource Pressure**
   - CPU usage > 80% for 15+ minutes
   - Memory usage > 85%
   - Connection pool > 80% utilized

4. **Capacity Concerns**
   - Approaching resource limits
   - Traffic spike detected
   - Queue lengths increasing

### Alert Thresholds

#### Error Rate Alerts

**Critical: High 5xx Error Rate**
```prometheus
# Alert when 5xx error rate exceeds 1% for 5 minutes
(
  rate(http_requests_total{status_class="5xx"}[5m])
  /
  rate(http_requests_total[5m])
) > 0.01
```
- **Threshold**: > 1% (1 in 100 requests failing)
- **Duration**: 5 minutes
- **Severity**: Critical

**Warning: Elevated 5xx Error Rate**
```prometheus
# Alert when 5xx error rate exceeds 0.5% for 15 minutes
(
  rate(http_requests_total{status_class="5xx"}[5m])
  /
  rate(http_requests_total[5m])
) > 0.005
```
- **Threshold**: > 0.5% (1 in 200 requests failing)
- **Duration**: 15 minutes
- **Severity**: Warning

**Critical: Endpoint-Specific Failures**
```prometheus
# Alert when specific critical endpoint has >10% error rate
(
  rate(http_requests_total{endpoint="/api/v1/payments", status_class="5xx"}[5m])
  /
  rate(http_requests_total{endpoint="/api/v1/payments"}[5m])
) > 0.10
```
- **Threshold**: > 10% for critical endpoints
- **Duration**: 5 minutes
- **Severity**: Critical

#### Latency Alerts

**Critical: High p95 Latency**
```prometheus
# Alert when p95 latency exceeds 2 seconds for 5 minutes
histogram_quantile(0.95,
  rate(http_request_duration_seconds_bucket[5m])
) > 2
```
- **Threshold**: p95 > 2 seconds
- **Duration**: 5 minutes
- **Severity**: Critical

**Warning: Elevated p95 Latency**
```prometheus
# Alert when p95 latency exceeds 1 second for 15 minutes
histogram_quantile(0.95,
  rate(http_request_duration_seconds_bucket[5m])
) > 1
```
- **Threshold**: p95 > 1 second
- **Duration**: 15 minutes
- **Severity**: Warning

**Critical: Tail Latency (p99)**
```prometheus
# Alert when p99 latency exceeds 5 seconds for 5 minutes
histogram_quantile(0.99,
  rate(http_request_duration_seconds_bucket[5m])
) > 5
```
- **Threshold**: p99 > 5 seconds
- **Duration**: 5 minutes
- **Severity**: Critical

#### Availability Alerts

**Critical: Service Down**
```prometheus
# Alert when health check fails
up{job="api-server"} == 0
```
- **Threshold**: Health check down
- **Duration**: 2 minutes (immediate)
- **Severity**: Critical

**Critical: High Unavailability**
```prometheus
# Alert when >50% of instances are down
avg(up{job="api-server"}) < 0.5
```
- **Threshold**: < 50% instances available
- **Duration**: 2 minutes
- **Severity**: Critical

#### Resource Alerts

**Warning: High CPU Usage**
```prometheus
# Alert when CPU usage exceeds 80% for 15 minutes
100 - (avg(irate(process_cpu_seconds_total[5m])) * 100) < 20
# Or using node_exporter:
100 - (avg(irate(node_cpu_seconds_total{mode="idle"}[5m])) * 100) > 80
```
- **Threshold**: > 80% CPU
- **Duration**: 15 minutes
- **Severity**: Warning

**Critical: Very High CPU Usage**
```prometheus
100 - (avg(irate(node_cpu_seconds_total{mode="idle"}[5m])) * 100) > 95
```
- **Threshold**: > 95% CPU
- **Duration**: 5 minutes
- **Severity**: Critical

**Warning: High Memory Usage**
```prometheus
# Alert when memory usage exceeds 85%
(jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) > 0.85
```
- **Threshold**: > 85% memory
- **Duration**: 15 minutes
- **Severity**: Warning

**Critical: Memory Exhaustion Risk**
```prometheus
(jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) > 0.95
```
- **Threshold**: > 95% memory
- **Duration**: 5 minutes
- **Severity**: Critical

**Warning: Thread Pool Exhaustion**
```prometheus
# Alert when thread pool utilization exceeds 80%
(thread_pool_active_threads / thread_pool_max_threads) > 0.80
```
- **Threshold**: > 80% thread pool utilization
- **Duration**: 10 minutes
- **Severity**: Warning

**Critical: Thread Pool Rejections**
```prometheus
# Alert when thread pool rejections occur
rate(thread_pool_rejected_tasks_total[5m]) > 0
```
- **Threshold**: Any rejections
- **Duration**: 5 minutes
- **Severity**: Critical

#### Capacity Alerts

**Warning: High Request Rate**
```prometheus
# Alert when request rate exceeds expected capacity (e.g., 1000 req/s)
rate(http_requests_total[5m]) > 1000
```
- **Threshold**: > 1000 requests/second
- **Duration**: 15 minutes
- **Severity**: Warning

**Warning: Connection Pool Pressure**
```prometheus
# Alert when connection pool utilization exceeds 80%
(connection_pool_active / connection_pool_max) > 0.80
```
- **Threshold**: > 80% connection pool utilization
- **Duration**: 15 minutes
- **Severity**: Warning

### Alert Duration (Avoiding Flapping)

#### Flapping Prevention Strategies

1. **Use Appropriate Windows**
   - Critical alerts: 2-5 minutes (fast response needed)
   - Warning alerts: 10-15 minutes (avoid noise)
   - Capacity alerts: 15-30 minutes (trends, not spikes)

2. **Use Rate Functions**
   - `rate()` over 5-minute windows smooths spikes
   - `avg_over_time()` for additional smoothing
   - Avoid instant values that fluctuate

3. **Hysteresis**
   - Different thresholds for alerting vs recovery
   - Example: Alert at >80%, recover at <70%

4. **Grouping and Aggregation**
   - Aggregate across instances to avoid per-instance noise
   - Use `avg()`, `max()`, or `sum()` appropriately

#### Example: Flapping Prevention
```prometheus
# Bad: Instant value, will flap
http_requests_total{status="500"} > 100

# Good: Rate over time window
rate(http_requests_total{status="500"}[5m]) > 10

# Better: Rate with percentage threshold
(
  rate(http_requests_total{status_class="5xx"}[5m])
  /
  rate(http_requests_total[5m])
) > 0.01
```

### Warning vs Critical Alerts

#### Critical Alerts
- **Purpose**: Immediate action required
- **Impact**: Users affected right now
- **Response**: Page on-call, immediate investigation
- **Examples**:
  - Service down
  - High error rate (>1%)
  - Complete outage
  - Security incident
  - Data loss risk

#### Warning Alerts
- **Purpose**: Investigate during business hours
- **Impact**: Potential issues, degradation
- **Response**: Review during next business day
- **Examples**:
  - Elevated error rate (0.5-1%)
  - Performance degradation
  - Resource pressure
  - Capacity concerns
  - Trending issues

#### Alert Escalation
1. **Warning** → Monitor for 15-30 minutes
2. **If persists or worsens** → Escalate to Critical
3. **Critical** → Page on-call immediately

### Complete Alert Rule Example (Prometheus)

```yaml
groups:
  - name: http_api_critical
    interval: 30s
    rules:
      - alert: HighErrorRate
        expr: |
          (
            sum(rate(http_requests_total{status_class="5xx"}[5m]))
            /
            sum(rate(http_requests_total[5m]))
          ) > 0.01
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "High 5xx error rate detected"
          description: "Error rate is {{ $value | humanizePercentage }} (threshold: 1%)"

      - alert: ServiceDown
        expr: up{job="api-server"} == 0
        for: 2m
        labels:
          severity: critical
        annotations:
          summary: "API service is down"
          description: "Instance {{ $labels.instance }} has been down for more than 2 minutes"

      - alert: HighLatency
        expr: |
          histogram_quantile(0.95,
            sum(rate(http_request_duration_seconds_bucket[5m])) by (le)
          ) > 2
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "High p95 latency detected"
          description: "p95 latency is {{ $value }}s (threshold: 2s)"

  - name: http_api_warning
    interval: 30s
    rules:
      - alert: ElevatedErrorRate
        expr: |
          (
            sum(rate(http_requests_total{status_class="5xx"}[5m]))
            /
            sum(rate(http_requests_total[5m]))
          ) > 0.005
        for: 15m
        labels:
          severity: warning
        annotations:
          summary: "Elevated 5xx error rate"
          description: "Error rate is {{ $value | humanizePercentage }} (threshold: 0.5%)"

      - alert: HighCPUUsage
        expr: |
          100 - (avg(irate(node_cpu_seconds_total{mode="idle"}[5m])) * 100) > 80
        for: 15m
        labels:
          severity: warning
        annotations:
          summary: "High CPU usage"
          description: "CPU usage is {{ $value }}% (threshold: 80%)"
```

---

## Summary

### Key Takeaways

1. **Monitor the Four Golden Signals**
   - Latency (response time)
   - Traffic (request rate)
   - Errors (error rate)
   - Saturation (resource usage)

2. **Use Appropriate Metric Types**
   - Counters for cumulative values
   - Gauges for current state
   - Histograms for distributions (latency, sizes)

3. **Control Cardinality**
   - Avoid high-cardinality labels (user IDs, request IDs)
   - Use normalized endpoint paths
   - Monitor cardinality growth

4. **Design Alerts Carefully**
   - Critical: Immediate user impact (page on-call)
   - Warning: Potential issues (investigate during business hours)
   - Use appropriate durations to avoid flapping
   - Set thresholds based on SLA and user experience

5. **Focus on User Experience**
   - High latency = bad UX
   - High error rate = bad UX
   - Timeouts = bad UX
   - Resource exhaustion = bad UX

6. **Plan for Capacity**
   - Monitor trends, not just current values
   - Alert before hitting limits
   - Understand scaling triggers



