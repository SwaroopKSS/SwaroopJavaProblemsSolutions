# AWS Monitoring & Logging Guide

## CloudWatch Overview

**CloudWatch** = AWS's monitoring, logging, and observability service.

### Key Components

```
┌─────────────────────────────────────────┐
│       CloudWatch Service                │
│                                         │
│  ├─ Metrics (Quantitative data)         │
│  │   └─ CPU, Memory, Network, etc.      │
│  │                                      │
│  ├─ Logs (Event data)                   │
│  │   └─ Application logs, AWS service   │
│  │                                      │
│  ├─ Alarms (Threshold triggers)         │
│  │   └─ Auto scaling, notifications     │
│  │                                      │
│  ├─ Dashboards (Visualization)          │
│  │   └─ Real-time monitoring            │
│  │                                      │
│  └─ Insights (Log analysis)             │
│      └─ Query logs with SQL-like syntax │
└─────────────────────────────────────────┘
```

---

## Metrics

**Metric** = Time-series data point with timestamp and value.

```
Metric: CPUUtilization
    Timestamp: 2024-01-01T12:00:00Z  Value: 45%
    Timestamp: 2024-01-01T12:01:00Z  Value: 52%
    Timestamp: 2024-01-01T12:02:00Z  Value: 48%
    Timestamp: 2024-01-01T12:03:00Z  Value: 61%
    ...
```

### Default Metrics (Free)

```
EC2:
  - CPUUtilization
  - NetworkIn
  - NetworkOut
  - DiskReadBytes
  - DiskWriteBytes

ALB:
  - RequestCount
  - TargetResponseTime
  - HTTPCode_Target_2XX_Count
  - HTTPCode_Target_5XX_Count

RDS:
  - DatabaseConnections
  - CPUUtilization
  - FreeableMemory
  - ReadLatency
  - WriteLatency
```

### Custom Metrics

```yaml
PutMetricData:
  Namespace: MyApplication
  MetricName: OrdersProcessed
  Value: 150
  Unit: Count
  Timestamp: 2024-01-01T12:00:00Z
  Dimensions:
    - Name: Environment
      Value: production
    - Name: Service
      Value: order-service
```

### Metric Statistics

```
Statistic  | Description
-----------|------------------
Average    | Mean value
Sum        | Total of all values
Min        | Lowest value
Max        | Highest value
Count      | Number of data points
```

---

## Logs

**Log** = Text event data from applications and services.

### Log Structure

```
Log Group: /aws/lambda/my-function
  └─ Log Stream: 2024/01/01/[$LATEST]abcdef123456
      ├─ [2024-01-01T12:00:00.000Z] Function invoked
      ├─ [2024-01-01T12:00:01.000Z] Processing request
      ├─ [2024-01-01T12:00:02.000Z] Database query: 150ms
      ├─ [2024-01-01T12:00:03.000Z] Response generated
      └─ [2024-01-01T12:00:04.000Z] Function completed
```

### Log Sources

```
AWS Services:
  - VPC Flow Logs (network traffic)
  - CloudTrail (API calls)
  - RDS (database logs)
  - Lambda (function logs)
  - ALB (access logs)

Applications:
  - CloudWatch Agent (on EC2)
  - Application code (putLogEvents API)
  - Docker containers (CloudWatch logging driver)
```

### In EAC

```yaml
LogGroup:
  LogGroupName: /aws/application/logs
  RetentionInDays: 7  # Keep 7 days
  Tags:
    - Key: Name
      Value: app-logs

LogStream:
  LogGroupName: !Ref LogGroup
  LogStreamName: app-server-1

LogResourcePolicy:
  PolicyName: AllowALBLogs
  PolicyText:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          Service: elasticloadbalancing.amazonaws.com
        Action: logs:PutLogEvents
        Resource: !GetAtt LogGroup.Arn
```

---

## Alarms

**Alarm** = Triggers action when metric crosses threshold.

### Alarm States

```
OK (Green)
    ↑
    │ Metric crosses threshold
    ↓
ALARM (Red)
    ↑
    │ Metric returns to normal
    ↓
OK (Green)
```

### Example: Auto Scaling Alarm

```yaml
HighCPUAlarm:
  Type: AWS::CloudWatch::Alarm
  Properties:
    AlarmName: high-cpu-alarm
    AlarmDescription: Scale up when CPU > 70%
    
    # What to monitor
    MetricName: CPUUtilization
    Namespace: AWS/EC2
    Dimensions:
      - Name: AutoScalingGroupName
        Value: !Ref AutoScalingGroup
    
    # How to evaluate
    Statistic: Average
    Period: 60  # seconds
    EvaluationPeriods: 2  # consecutive periods
    Threshold: 70  # percent
    ComparisonOperator: GreaterThanThreshold
    
    # What to do
    AlarmActions:
      - !Ref ScaleUpPolicy
    
    # Optional: notify
    AlarmActions:
      - !Ref SNSTopic

LowCPUAlarm:
  Type: AWS::CloudWatch::Alarm
  Properties:
    AlarmName: low-cpu-alarm
    AlarmDescription: Scale down when CPU < 30%
    
    MetricName: CPUUtilization
    Namespace: AWS/EC2
    Statistic: Average
    Period: 300  # 5 minutes
    EvaluationPeriods: 2
    Threshold: 30
    ComparisonOperator: LessThanThreshold
    
    AlarmActions:
      - !Ref ScaleDownPolicy
```

### Common Alarm Patterns

**Pattern 1: Threshold-Based Alerting**
```
If CPUUtilization > 80% for 5 minutes → Send SNS notification
```

**Pattern 2: Composite Alarm**
```
If (HighCPU AND HighMemory AND HighNetworkIn) → Page on-call engineer
```

**Pattern 3: Anomaly Detection**
```
If metric deviates > 2 standard deviations from normal → Alert
```

---

## Dashboards

**Dashboard** = Visual representation of metrics and logs.

```yaml
Dashboard:
  DashboardName: production-overview
  DashboardBody:
    Widgets:
      # Widget 1: ALB Metrics
      - Type: Metric
        Properties:
          Metrics:
            - Namespace: AWS/ApplicationELB
              MetricName: RequestCount
              Stat: Sum
              Period: 300
            - Namespace: AWS/ApplicationELB
              MetricName: TargetResponseTime
              Stat: Average
          Period: 300
          Stat: Average
          Region: us-east-1
          Title: ALB Performance
      
      # Widget 2: EC2 Metrics
      - Type: Metric
        Properties:
          Metrics:
            - Namespace: AWS/EC2
              MetricName: CPUUtilization
              Stat: Average
          Period: 300
          Title: EC2 CPU Usage
      
      # Widget 3: Log Insights Query
      - Type: Log
        Properties:
          Query: |
            fields @timestamp, @message, @duration
            | filter @message like /ERROR/
            | stats count() by bin(5m)
          Region: us-east-1
          Title: Error Rate (last 5 minutes)
```

---

## Log Insights (Query Logs)

**Log Insights** = SQL-like language for analyzing logs.

### Common Queries

**Query 1: Error Count by Source**
```sql
fields @timestamp, @message, @logStream
| filter @message like /ERROR/
| stats count() as error_count by @logStream
| sort error_count desc
```

**Query 2: Response Time Analysis**
```sql
fields @duration, @logStream
| filter @duration > 1000
| stats avg(@duration), max(@duration), pct(@duration, 95) by @logStream
```

**Query 3: Request Rate Over Time**
```sql
fields @timestamp, @message
| filter @message like /Request/
| stats count() as request_count by bin(1m)
```

**Query 4: Find Slow API Calls**
```sql
fields @timestamp, @logStream, @duration, @message
| filter @duration > 2000
| sort @duration desc
| limit 20
```

---

## VPC Flow Logs

**VPC Flow Logs** = Network traffic logs for debugging connectivity.

```yaml
VPCFlowLog:
  ResourceType: VPC
  ResourceId: !Ref VPC
  TrafficType: ALL  # ACCEPT, REJECT, or ALL
  LogDestinationType: CloudWatch Logs
  LogGroupName: /aws/vpc/flowlogs
  DeliverLogsPermissionIAM: !GetAtt VPCFlowLogsRole.Arn

# Log format:
# version account-id interface-id srcaddr dstaddr srcport dstport protocol packets bytes start end action log-status
# 2 123456 eni-12345 10.0.1.10 10.0.2.20 49154 22 6 2 120 1418530010 1418530070 ACCEPT OK
```

### Analyzing VPC Flow Logs

```sql
fields @timestamp, srcaddr, dstaddr, dstport, action
| filter action = "REJECT"
| stats count() by dstport
```

---

## CloudTrail (Audit Logging)

**CloudTrail** = Records all AWS API calls.

```yaml
CloudTrail:
  IsLogging: true
  S3BucketName: !Ref CloudTrailBucket
  S3KeyPrefix: cloudtrail
  IncludeGlobalServiceEvents: true
  IsMultiRegionTrail: true
  EnableLogFileValidation: true  # Detect tampering
  EventSelectors:
    # Track data events
    - ReadWriteType: All
      IncludeManagementEvents: true
      DataResources:
        - Type: AWS::S3::Object
          Values:
            - "arn:aws:s3:::my-bucket/*"
        - Type: AWS::Lambda::Function
          Values:
            - "arn:aws:lambda:*:*:function/*"

# Example CloudTrail event:
# {
#   "eventTime": "2024-01-01T12:00:00Z",
#   "eventName": "RunInstances",
#   "eventSource": "ec2.amazonaws.com",
#   "awsRegion": "us-east-1",
#   "sourceIPAddress": "210.1.1.1",
#   "userAgent": "aws-cli/2.0",
#   "requestParameters": {
#     "instanceType": "t3.medium",
#     "imageId": "ami-12345"
#   },
#   "responseElements": {
#     "instancesSet": {
#       "items": [{
#         "instanceId": "i-1234567"
#       }]
#     }
#   }
# }
```

### CloudTrail Query Examples

**Who created security group?**
```
EventName: CreateSecurityGroup
```

**Who deleted database?**
```
EventName: DeleteDBInstance
ServiceEventDetails: includes database name
```

**When was IAM policy changed?**
```
EventName: PutUserPolicy or AttachUserPolicy
```

---

## Monitoring Strategy

### 1. Define Key Metrics (for your app)

```
Business Metrics:
  - Orders processed per minute
  - Revenue generated
  - Users active online

Technical Metrics:
  - API latency (p50, p95, p99)
  - Error rate
  - Throughput (requests/sec)

Infrastructure Metrics:
  - CPU utilization
  - Memory usage
  - Disk I/O
  - Network throughput
```

### 2. Set Appropriate Thresholds

```
Alert if:
  - CPU > 80% for 5 minutes
  - Error rate > 5% for 5 minutes
  - API latency p99 > 2 seconds for 10 minutes
  - Database connections > 90% of max

Don't alert if:
  - Normal business spike (use time-based rules)
  - Expected maintenance window
  - Known non-critical issues
```

### 3. Implement Alerting Strategy

```
Severity   | Response Time | Notification
-----------|---------------|-------------------
Critical   | Immediate     | Page engineer
High       | 5 minutes     | Slack + Email
Medium     | 30 minutes    | Email only
Low        | Next day      | Daily digest
```

---

## Best Practices

### 1. **Use Structured Logging**

```json
// ❌ Bad - unstructured
"User login failed"

// ✅ Good - structured
{
  "timestamp": "2024-01-01T12:00:00Z",
  "event": "user_login_failed",
  "user_id": "user-123",
  "reason": "invalid_password",
  "source_ip": "210.1.1.1",
  "service": "auth-service"
}
```

### 2. **Appropriate Log Levels**

```
DEBUG   - Detailed diagnostic info
INFO    - Significant events
WARN    - Warning conditions
ERROR   - Error conditions
FATAL   - System failure
```

### 3. **Log Retention Policy**

```yaml
LogGroup:
  RetentionInDays: 7  # Dev: 7 days
  # or
  RetentionInDays: 30  # Prod: 30 days
```

### 4. **Metric Aggregation**

```
Individual metrics:
  EC2-1: CPU 45%, Mem 70%
  EC2-2: CPU 52%, Mem 65%
  EC2-3: CPU 48%, Mem 72%

Aggregated metrics:
  ASG Average CPU: 48%, Max CPU: 52%
  ASG Average Mem: 69%, Max Mem: 72%
```

### 5. **Alert Fatigue Prevention**

```
❌ Alerting on every spike
❌ Too many false alarms
❌ Not actionable alerts

✅ Alert on sustained high CPU (>80% for 10 min)
✅ Alert on critical errors (not warnings)
✅ Each alert requires action
```

---

## Complete Monitoring Stack (EAC)

```yaml
# Log Groups
AppLogGroup:
  LogGroupName: /aws/app/application
  RetentionInDays: 30

# CloudWatch Alarms
HighErrorRateAlarm:
  MetricName: ErrorCount
  Threshold: 10  # errors per minute
  AlarmActions:
    - !Ref SNSTopicOnCall

HighLatencyAlarm:
  MetricName: Latency
  Statistic: p99
  Threshold: 2000  # ms
  AlarmActions:
    - !Ref SNSTopicOnCall

# Dashboards
ProductionDashboard:
  DashboardName: prod-overview
  Widgets:
    - ALB metrics
    - EC2 metrics
    - RDS metrics
    - Application custom metrics
    - Error logs

# CloudTrail
AuditTrail:
  S3BucketName: !Ref AuditBucket
  IsLogging: true

# VPC Flow Logs
NetworkFlowLogs:
  LogGroupName: /aws/vpc/flowlogs
```

---

**Summary**: Metrics → Logs → Alarms → Dashboards → Action

- **Metrics**: Quantitative data (numbers)
- **Logs**: Event data (text)
- **Alarms**: Trigger when thresholds crossed
- **Dashboards**: Visual monitoring
- **Action**: Auto scaling, notifications, healing

**Next**: Review example CloudFormation templates with monitoring!
