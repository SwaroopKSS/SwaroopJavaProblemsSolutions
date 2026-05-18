# Route 53 & ALB Connectivity - Comprehensive Guide

## Overview

This document explains how **Route 53** (DNS) and **Application Load Balancer (ALB)** work together in an AWS Environment as Code architecture.

---

## Table of Contents

1. [Basic Connectivity Flow](#basic-connectivity-flow)
2. [Route 53 to ALB Mapping](#route-53-to-alb-mapping)
3. [DNS Resolution Process](#dns-resolution-process)
4. [Traffic Routing Patterns](#traffic-routing-patterns)
5. [Implementation Examples](#implementation-examples)
6. [Health Checking](#health-checking)
7. [Failover Scenarios](#failover-scenarios)
8. [Advanced Routing](#advanced-routing)

---

## Basic Connectivity Flow

### Simple Web Application Flow

```
1. User enters: www.example.com
         ↓
2. Browser queries Route 53 (AWS DNS service)
         ↓
3. Route 53 resolves to ALB DNS name: my-alb-123456.us-east-1.elb.amazonaws.com
         ↓
4. Browser performs TCP connection to ALB IP
         ↓
5. ALB receives HTTP request
         ↓
6. ALB routes to target EC2 instance based on rules
         ↓
7. EC2 instance processes request
         ↓
8. Response sent back through ALB to browser
```

### Key Points:

- **Route 53** = DNS service (translates domain names to IP addresses)
- **ALB** = Load balancer (distributes traffic across instances)
- **EC2** = Application servers (process actual requests)

---

## Route 53 to ALB Mapping

### DNS Alias Records (AWS Specific)

Route 53 allows **Alias Records** that point directly to AWS resources without additional charges.

#### Record Types:

| Record Type | Purpose | Example |
|------------|---------|---------|
| A Record | Maps domain to IPv4 | example.com → 10.0.0.1 |
| AAAA Record | Maps domain to IPv6 | example.com → 2001:0db8... |
| CNAME | Alias to another domain | www → example.com |
| Alias | AWS-specific alias | www → alb-dns-name |

### Alias vs CNAME Difference:

```
Traditional CNAME:
┌─────────────────┐
│ www.example.com │
│     (CNAME)     │
└────────┬────────┘
         │ points to
         ↓
┌──────────────────────┐
│ my-alb.elb.amazonaws │
│      (CNAME)         │
└─────────┬────────────┘
          │ points to
          ↓
      [IP Address]

AWS Alias Record:
┌──────────────────────┐
│ www.example.com      │
│   (Alias Record)     │
└──────────┬───────────┘
           │ directly points to
           ↓
      [ALB Resource]
      (No extra lookup)
```

### Benefits of Alias Records:

1. **No additional charges** - CNAME queries cost extra
2. **Can use at zone apex** - Can create alias for naked domain (example.com)
3. **Dynamic updates** - Automatically updates if ALB IP changes
4. **Health checking** - Integrated with Route 53 health checks

---

## DNS Resolution Process

### Step-by-Step Resolution:

```
Step 1: User makes request
┌──────────────────────────────┐
│ Client: nslookup www.example.com
│        or browser lookup     │
└──────────────────────────────┘
              ↓
Step 2: Query Route 53 Hosted Zone
┌──────────────────────────────────────────┐
│ Query: www.example.com IN A              │
│ Route 53 Hosted Zone: example.com        │
│ Record Type: Alias                       │
│ Target: ALB DNS Name                     │
└──────────────────────────────────────────┘
              ↓
Step 3: Route 53 Responds
┌────────────────────────────────────────────┐
│ Response: www.example.com ALIAS            │
│ Value: my-alb-123456.us-east-1.            │
│        elb.amazonaws.com                   │
│ TTL: 60 seconds                            │
└────────────────────────────────────────────┘
              ↓
Step 4: DNS Resolution of ALB Name
┌────────────────────────────────────┐
│ Query: my-alb-123456.us-east-1.    │
│        elb.amazonaws.com IN A      │
│ (Resolver queries AWS DNS)         │
└────────────────────────────────────┘
              ↓
Step 5: Get ALB IP Address
┌──────────────────────────────┐
│ ALB IP: 54.123.45.67         │
│ (AWS ELB elastic IP pool)    │
└──────────────────────────────┘
              ↓
Step 6: Client Caches Result
┌────────────────────────────────────┐
│ Cache Entry (TTL=60 seconds):      │
│ www.example.com → 54.123.45.67     │
└────────────────────────────────────┘
```

---

## Traffic Routing Patterns

### 1. Simple Routing (Default)

**Use Case**: Single target, no load distribution preference

```yaml
Route53RecordSet:
  Name: api.example.com
  Type: A
  TTL: 300
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K  # ALB Hosted Zone ID
    DNSName: my-alb-123456.us-east-1.elb.amazonaws.com
    EvaluateTargetHealth: true
```

**Flow**:
```
User → Route 53 → ALB IP → ALB → All Targets
```

---

### 2. Weighted Routing

**Use Case**: Distribute traffic by percentage (A/B testing, gradual deployment)

```yaml
Route53RecordSet_Primary:
  Name: api.example.com
  Type: A
  SetIdentifier: api-primary
  Weight: 70  # 70% traffic
  TTL: 300
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: primary-alb.us-east-1.elb.amazonaws.com
    EvaluateTargetHealth: true

Route53RecordSet_Secondary:
  Name: api.example.com
  Type: A
  SetIdentifier: api-secondary
  Weight: 30  # 30% traffic
  TTL: 300
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: secondary-alb.us-east-1.elb.amazonaws.com
    EvaluateTargetHealth: true
```

**Flow**:
```
User → Route 53 → 70% to Primary ALB, 30% to Secondary ALB
                      ↓                        ↓
                  Primary Targets         Secondary Targets
```

---

### 3. Geolocation Routing

**Use Case**: Route based on user's geographic location

```yaml
Route53RecordSet_US:
  Name: api.example.com
  Type: A
  SetIdentifier: api-us
  GeoLocation:
    CountryCode: US
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: us-alb.us-east-1.elb.amazonaws.com

Route53RecordSet_EU:
  Name: api.example.com
  Type: A
  SetIdentifier: api-eu
  GeoLocation:
    CountryCode: DE
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: eu-alb.eu-west-1.elb.amazonaws.com

Route53RecordSet_Default:
  Name: api.example.com
  Type: A
  SetIdentifier: api-default
  GeoLocation:
    CountryCode: "*"  # Default for other locations
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: default-alb.us-east-1.elb.amazonaws.com
```

**Flow**:
```
User (from US)     → Route 53 → US ALB
User (from EU)     → Route 53 → EU ALB
User (from Asia)   → Route 53 → Default ALB
```

---

### 4. Latency-Based Routing

**Use Case**: Route to closest region for lowest latency

```yaml
Route53RecordSet_EastRegion:
  Name: api.example.com
  Type: A
  SetIdentifier: api-us-east
  Region: us-east-1
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: us-east-alb.us-east-1.elb.amazonaws.com

Route53RecordSet_WestRegion:
  Name: api.example.com
  Type: A
  SetIdentifier: api-us-west
  Region: us-west-2
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: us-west-alb.us-west-2.elb.amazonaws.com

Route53RecordSet_EURegion:
  Name: api.example.com
  Type: A
  SetIdentifier: api-eu
  Region: eu-west-1
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: eu-alb.eu-west-1.elb.amazonaws.com
```

**Flow**:
```
User (Low latency to us-east-1)  → Route 53 → us-east ALB
User (Low latency to eu-west-1)  → Route 53 → eu ALB
```

---

### 5. Failover Routing

**Use Case**: Active-passive setup with automatic failover

```yaml
Route53RecordSet_Primary:
  Name: api.example.com
  Type: A
  SetIdentifier: api-primary
  Failover: PRIMARY
  HealthCheckId: !Ref PrimaryHealthCheck
  TTL: 60
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: primary-alb.us-east-1.elb.amazonaws.com
    EvaluateTargetHealth: true

Route53RecordSet_Secondary:
  Name: api.example.com
  Type: A
  SetIdentifier: api-secondary
  Failover: SECONDARY
  TTL: 60
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: backup-alb.us-west-2.elb.amazonaws.com

HealthCheck:
  Type: CloudWatch
  CloudWatchAlarmConfiguration:
    AlarmIdentifier: arn:aws:cloudwatch:us-east-1:...
    InsufficientDataHealthStatus: Unhealthy
```

**Flow**:
```
Primary ALB Healthy:
User → Route 53 → Primary ALB → Targets

Primary ALB Fails:
User → Route 53 → (detects failure) → Secondary ALB → Targets
```

---

## Implementation Examples

### Example 1: Basic Setup (HTTP Only)

```yaml
# Route 53 Configuration
HostedZone:
  Name: example.com
  Type: Public

AliasRecord:
  Name: www.example.com
  Type: A
  HostedZoneId: Z12345
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: !GetAtt LoadBalancer.DNSName
    EvaluateTargetHealth: true
  TTL: 300

# ALB Configuration
ApplicationLoadBalancer:
  Name: web-alb
  Scheme: internet-facing
  Subnets:
    - PublicSubnet1
    - PublicSubnet2

Listener:
  Port: 80
  Protocol: HTTP
  DefaultActions:
    - Type: forward
      TargetGroupArn: !Ref WebTargetGroup

TargetGroup:
  Name: web-targets
  Port: 80
  Protocol: HTTP
  VpcId: vpc-12345
  Targets:
    - Id: i-ec2instance1
      Port: 80
    - Id: i-ec2instance2
      Port: 80
```

**Result**: 
- User visits www.example.com
- Route 53 resolves to ALB
- ALB distributes to EC2 instances

---

### Example 2: HTTPS with Certificate

```yaml
# Route 53 Configuration
AliasRecord:
  Name: api.example.com
  Type: A
  HostedZoneId: Z12345
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: !GetAtt LoadBalancer.DNSName
    EvaluateTargetHealth: true

# ALB Configuration
Listener_HTTPS:
  Port: 443
  Protocol: HTTPS
  Certificates:
    - CertificateArn: arn:aws:acm:us-east-1:123456:certificate/12345
  DefaultActions:
    - Type: forward
      TargetGroupArn: !Ref ApiTargetGroup

Listener_HTTP_Redirect:
  Port: 80
  Protocol: HTTP
  DefaultActions:
    - Type: redirect
      RedirectConfig:
        Protocol: HTTPS
        Port: '443'
        StatusCode: HTTP_301
```

**Result**:
- User visits api.example.com
- Route 53 resolves to ALB
- ALB redirects HTTP to HTTPS
- HTTPS request goes to target group

---

## Health Checking

### ALB Health Checks (Target Level)

```yaml
TargetGroup:
  Name: api-targets
  Port: 8080
  Protocol: HTTP
  VpcId: vpc-12345
  
  HealthCheckEnabled: true
  HealthCheckPath: /health
  HealthCheckProtocol: HTTP
  HealthCheckPort: 8080
  HealthCheckIntervalSeconds: 30
  HealthCheckTimeoutSeconds: 5
  HealthyThresholdCount: 2
  UnhealthyThresholdCount: 3
  
  Targets:
    - Id: i-ec2instance1
      Port: 8080
    - Id: i-ec2instance2
      Port: 8080
```

**How it works**:
```
Every 30 seconds:
ALB → HTTP GET /health to EC2 instance
Response 200-299 → Healthy
Response >= 300 → Unhealthy (after 3 failures)
After 2 consecutive success → Mark Healthy
```

### Route 53 Health Checks (Endpoint Level)

```yaml
Route53HealthCheck:
  Type: HTTPS
  FullyQualifiedDomainName: api.example.com
  Port: 443
  ResourcePath: /health
  RequestInterval: 30
  FailureThreshold: 3
  
  HealthCheckTags:
    Name: api-endpoint-health

Route53RecordSet:
  Name: api.example.com
  Type: A
  SetIdentifier: api-primary
  Failover: PRIMARY
  HealthCheckId: !Ref Route53HealthCheck
  AliasTarget:
    HostedZoneId: Z35SXDOTRQ7X7K
    DNSName: primary-alb.us-east-1.elb.amazonaws.com
    EvaluateTargetHealth: true
```

**How it works**:
```
Every 30 seconds:
Route 53 → HTTPS request to api.example.com:443/health
If endpoint returns 200 → Healthy
If endpoint fails 3 times → Mark unhealthy
Route 53 → Failover to secondary record
```

---

## Failover Scenarios

### Scenario 1: Single Target Group Failure

```
Initial State:
api.example.com (Route 53) → ALB → [EC2-1(Healthy), EC2-2(Healthy)]

EC2-1 Fails:
ALB detects unhealthy → Removes from rotation
api.example.com (Route 53) → ALB → [EC2-2(Healthy)]

All Targets Fail:
ALB marks all unhealthy but still serves (degraded mode)
Route 53 EvaluateTargetHealth: true → Failover to secondary
api.example.com (Route 53) → Secondary ALB
```

### Scenario 2: Primary ALB Region Failure

```
Initial State:
api.example.com (Route 53) → Primary ALB (us-east-1)

Primary ALB Fails:
Route 53 Health Check detects failure
Route 53 → Failover to Secondary record
api.example.com → Secondary ALB (us-west-2)
```

---

## Advanced Routing

### Multi-Path Based Routing

```yaml
ALBListener:
  Port: 443
  Protocol: HTTPS

Rule_1:
  Priority: 1
  Conditions:
    - Field: path-pattern
      Values: ["/api/*"]
  Actions:
    - Type: forward
      TargetGroupArn: !Ref ApiTargetGroup

Rule_2:
  Priority: 2
  Conditions:
    - Field: path-pattern
      Values: ["/static/*"]
  Actions:
    - Type: forward
      TargetGroupArn: !Ref StaticTargetGroup

Rule_3:
  Priority: 3
  Conditions:
    - Field: hostname-pattern
      Values: ["admin.example.com"]
  Actions:
    - Type: forward
      TargetGroupArn: !Ref AdminTargetGroup

Rule_Default:
  Priority: 4 (Highest number = default)
  Actions:
    - Type: forward
      TargetGroupArn: !Ref DefaultTargetGroup
```

**Flow**:
```
GET /api/users → API Target Group
GET /static/image.jpg → Static Target Group
GET admin.example.com/dashboard → Admin Target Group
GET /anything-else → Default Target Group
```

---

## Summary

| Component | Role | In EAC |
|-----------|------|--------|
| Route 53 | DNS resolution, traffic routing policy | HostedZones, RecordSets, Health Checks |
| ALB | Load balancing, SSL termination | LoadBalancer, Listeners, TargetGroups |
| EC2 | Application servers | Targets in target groups |
| Security Groups | Network firewall | Ingress/Egress rules |
| CloudWatch | Monitoring | Alarms for scaling, health checks |

**Next Steps**:
- Review [ADVANCED_ARCHITECTURE.md](./diagrams/ADVANCED_ARCHITECTURE.md) for complex setups
- Check [CLOUDFORMATION_EXAMPLES.md](./examples/CLOUDFORMATION_EXAMPLES.md) for working code
- Study [VPC_NETWORKING_GUIDE.md](./VPC_NETWORKING_GUIDE.md) for network design

