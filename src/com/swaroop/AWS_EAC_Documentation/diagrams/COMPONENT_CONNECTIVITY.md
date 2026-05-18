# AWS Component Connectivity Map

## Complete Component Relationships

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        EXTERNAL WORLD (Internet)                            │
│                                                                             │
│  Domain Registrar (GoDaddy, Route53, etc)                                  │
│       ↑                                                                     │
│       │ Points to nameservers                                              │
│       ↓                                                                     │
│   Route 53 (DNS Service)                                                   │
│   • Hosted Zone: example.com                                              │
│   • Records:                                                              │
│     - www.example.com → ALB DNS                                          │
│     - api.example.com → ALB DNS                                          │
│     - admin.example.com → CloudFront DNS                                 │
│                                                                             │
└────────────────────────────┬────────────────────────────────────────────────┘
                             │
                    Firewall, DoS Protection
                             │
                             ↓
          ┌───────────────────────────────────┐
          │  AWS Shield (DDoS Protection)     │
          │  AWS WAF (Web Application FW)     │
          └───────────────────────────────────┘
                             │
                             ↓
          ┌───────────────────────────────────┐
          │  CloudFront (CDN)                 │
          │  • Caches static content          │
          │  • Compresses responses           │
          │  • Geo-distributed                │
          └───────────────────────────────────┘
                             │
                             ↓

┌─────────────────────────────────────────────────────────────────────────────┐
│  AWS REGION: us-east-1                                                     │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │ VPC: 10.0.0.0/16                                                    │ │
│  │                                                                     │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │ PUBLIC SUBNETS (AZ 1a, 1b, 1c)                              │ │ │
│  │  │ 10.0.1.0/24, 10.0.2.0/24, 10.0.3.0/24                      │ │ │
│  │  │                                                              │ │ │
│  │  │  ┌──────────────────────────────────────────────────────┐  │ │ │
│  │  │  │ Application Load Balancer (ALB)                      │  │ │ │
│  │  │  │ • Layer 7 routing                                    │  │ │ │
│  │  │  │ • Path-based: /api/* → api-tg                        │  │ │ │
│  │  │  │ • Path-based: /web/* → web-tg                        │  │ │ │
│  │  │  │ • Host-based: admin.example.com → admin-tg           │  │ │ │
│  │  │  │ • SSL/TLS termination on port 443                    │  │ │ │
│  │  │  │ • HTTP redirect (80→443)                             │  │ │ │
│  │  │  │                                                       │  │ │ │
│  │  │  │ Security Group: alb-sg (0.0.0.0/0 on 80, 443)        │  │ │ │
│  │  │  │ CloudWatch:                                          │  │ │ │
│  │  │  │   • Metrics: RequestCount, ResponseTime              │  │ │ │
│  │  │  │   • Logs: Access logs to S3                          │  │ │ │
│  │  │  └──────────────────────────────────────────────────────┘  │ │ │
│  │  │            ↓            ↓              ↓                    │ │ │
│  │  │  ┌──────────┐ ┌──────────┐  ┌──────────┐                  │ │ │
│  │  │  │ NAT GW  │ │ NAT GW  │  │ NAT GW  │                  │ │ │
│  │  │  │ 1a      │ │ 1b      │  │ 1c      │                  │ │ │
│  │  │  │ EIP     │ │ EIP     │  │ EIP     │                  │ │ │
│  │  │  └──────────┘ └──────────┘  └──────────┘                  │ │ │
│  │  │            ↓            ↓              ↓                    │ │ │
│  │  │  ┌──────────────────────────────────────────────────────┐  │ │ │
│  │  │  │ Internet Gateway (IGW)                               │  │ │ │
│  │  │  │ • Connects VPC to internet (0.0.0.0/0)              │  │ │ │
│  │  │  │ • Route: 0.0.0.0/0 → IGW                            │  │ │ │
│  │  │  └──────────────────────────────────────────────────────┘  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                           ↓                                     │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ PRIVATE SUBNETS (AZ 1a, 1b, 1c)                          │  │ │ │
│  │  │ 10.0.11.0/24, 10.0.12.0/24, 10.0.13.0/24               │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  Target Group 1 (API)                                   │  │ │ │
│  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │  │ │ │
│  │  │  │ EC2 API-1    │  │ EC2 API-2    │  │ EC2 API-3    │   │  │ │ │
│  │  │  │ 10.0.11.10   │  │ 10.0.12.10   │  │ 10.0.13.10   │   │  │ │ │
│  │  │  │ :8080        │  │ :8080        │  │ :8080        │   │  │ │ │
│  │  │  │              │  │              │  │              │   │  │ │ │
│  │  │  │ IAM Role: ec2-role                                 │  │ │ │
│  │  │  │ SG: ec2-sg (ALB on 8080)                          │  │ │ │
│  │  │  │ CloudWatch Agent (logs/metrics)                    │  │ │ │
│  │  │  │ ASG: min 2, max 10, desired 3                      │  │ │ │
│  │  │  └──────────────┘  └──────────────┘  └──────────────┘   │  │ │ │
│  │  │                           ↓                              │  │ │ │
│  │  │  ┌──────────────────────────────────────────────────┐    │  │ │ │
│  │  │  │ RDS Database (MySQL/PostgreSQL)                 │    │  │ │ │
│  │  │  │ • Endpoint: mydb.123456.us-east-1.rds.amazonaws │    │  │ │ │
│  │  │  │ • Instance: 10.0.14.50 (Database Subnet Group)  │    │  │ │ │
│  │  │  │ • Multi-AZ: Primary 1a, Standby 1b             │    │  │ │ │
│  │  │  │ • SG: rds-sg (EC2 on 3306)                     │    │  │ │ │
│  │  │  │ • Automatic backup (30 days)                   │    │  │ │ │
│  │  │  │ • Encryption at rest (KMS)                     │    │  │ │ │
│  │  │  │ • CloudWatch: Connections, CPU, Memory, Latency│    │  │ │ │
│  │  │  └──────────────────────────────────────────────────┘    │  │ │ │
│  │  │           ↑            ↑              ↑                   │  │ │ │
│  │  │  EC2 instances connect via app code |                    │  │ │ │
│  │  │                                      ↓                   │  │ │ │
│  │  │  ┌──────────────────────────────────────────────────┐    │  │ │ │
│  │  │  │ ElastiCache (Redis/Memcached)                   │    │  │ │ │
│  │  │  │ • Endpoint: cache.123456.ng.0001.use1.cache... │    │  │ │ │
│  │  │  │ • Instance: 10.0.15.50                         │    │  │ │ │
│  │  │  │ • SG: cache-sg (EC2 on 6379)                   │    │  │ │ │
│  │  │  │ • Multi-AZ replication (optional)              │    │  │ │ │
│  │  │  │ • Encryption in transit                        │    │  │ │ │
│  │  │  │ • CloudWatch: CPU, Memory, Evictions           │    │  │ │ │
│  │  │  └──────────────────────────────────────────────────┘    │  │ │ │
│  │  │                                                          │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                                                                 │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ VPC Endpoints (Private connectivity to AWS services)   │  │ │ │
│  │  │                                                         │  │ │ │
│  │  │  Gateway Endpoints (Free):                             │  │ │ │
│  │  │    • S3 (no internet access needed)                    │  │ │ │
│  │  │    • DynamoDB (no internet access needed)              │  │ │ │
│  │  │                                                         │  │ │ │
│  │  │  Interface Endpoints (Charged):                         │  │ │ │
│  │  │    • SNS (push notifications)                          │  │ │ │
│  │  │    • SQS (message queues)                              │  │ │ │
│  │  │    • Secrets Manager (password management)             │  │ │ │
│  │  │    • Parameter Store (configuration)                   │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                           ↓                                     │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ Storage & Services                                       │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  S3 Bucket (my-app-bucket)                             │  │ │ │
│  │  │  • Stores application logs, backups, static files       │  │ │ │
│  │  │  • Versioning enabled                                   │  │ │ │
│  │  │  • Encryption: SSE-S3                                   │  │ │ │
│  │  │  • Lifecycle: Move old files to Glacier after 30 days  │  │ │ │
│  │  │  • Access via IAM role (EC2)                            │  │ │ │
│  │  │  • CloudWatch: Requests, Errors                         │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  SNS Topic (notifications)                              │  │ │ │
│  │  │  • Alarm notifications (Slack, Email)                  │  │ │ │
│  │  │  • Application events                                   │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  SQS Queue (async processing)                           │  │ │ │
│  │  │  • Decouple components                                  │  │ │ │
│  │  │  • Retry logic                                          │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  Secrets Manager                                        │  │ │ │
│  │  │  • Database credentials                                 │  │ │ │
│  │  │  • API keys                                             │  │ │ │
│  │  │  • SSL certificates                                     │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                                                                 │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ Monitoring & Logging                                     │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  CloudWatch Logs                                        │  │ │ │
│  │  │  • /aws/application/logs (application logs)             │  │ │ │
│  │  │  • /aws/alb/access-logs (ALB access logs)               │  │ │ │
│  │  │  • /aws/vpc/flowlogs (network traffic)                  │  │ │ │
│  │  │  • /aws/rds/instance/mydb/error (database logs)         │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  CloudWatch Metrics                                     │  │ │ │
│  │  │  • ALB: RequestCount, TargetResponseTime                │  │ │ │
│  │  │  • EC2: CPUUtilization, NetworkIn/Out                   │  │ │ │
│  │  │  • RDS: DatabaseConnections, ReadLatency                │  │ │ │
│  │  │  • Custom: OrdersProcessed, ErrorCount                  │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  CloudWatch Alarms                                      │  │ │ │
│  │  │  • high-cpu-alarm (70%) → trigger ScaleUpPolicy         │  │ │ │
│  │  │  • low-cpu-alarm (30%) → trigger ScaleDownPolicy        │  │ │ │
│  │  │  • high-error-alarm (5%) → SNS notification             │  │ │ │
│  │  │  • high-latency-alarm (p99>2s) → Page engineer          │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  CloudWatch Dashboard (prod-overview)                   │  │ │ │
│  │  │  • Real-time metrics visualization                      │  │ │ │
│  │  │  • Health status of all components                      │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  CloudTrail (Audit)                                    │  │ │ │
│  │  │  • Logs all API calls                                   │  │ │ │
│  │  │  • Stores in S3 bucket (cloudtrail-logs)                │  │ │ │
│  │  │  • Multi-region tracking                                │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                                                                 │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ IAM (Access Control)                                     │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  Roles:                                                 │  │ │ │
│  │  │  • EC2Role: Access to S3, RDS, Secrets Manager         │  │ │ │
│  │  │  • RDSMonitoringRole: CloudWatch metrics               │  │ │ │
│  │  │  • LambdaRole: Invoke API, write to DynamoDB           │  │ │ │
│  │  │  • CloudTrailRole: Write audit logs to S3              │  │ │ │
│  │  │                                                          │  │ │ │
│  │  │  Policies attached to roles:                            │  │ │ │
│  │  │  • AmazonSSMManagedInstanceCore                         │  │ │ │
│  │  │  • CloudWatchAgentServerPolicy                          │  │ │ │
│  │  │  • Custom policy: S3 bucket access                      │  │ │ │
│  │  │  • Custom policy: RDS database access                   │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  │                                                                 │ │ │
│  │  ┌──────────────────────────────────────────────────────────┐  │ │ │
│  │  │ ACM (SSL/TLS Certificates)                              │  │ │ │
│  │  │ • Certificate: *.example.com                            │  │ │ │
│  │  │ • Used by: ALB listener on port 443                     │  │ │ │
│  │  │ • Renewal: Automatic (AWS manages)                      │  │ │ │
│  │  └──────────────────────────────────────────────────────────┘  │ │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│ AWS SECONDARY REGION: us-west-2 (Disaster Recovery)                        │
│ • Replicated infrastructure                                                │
│ • Active-passive failover                                                  │
│ • S3 Cross-region replication                                              │
│ • RDS read replica promotion                                               │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Component Interaction Matrix

| Source | Destination | Protocol | Port | Reason |
|--------|-------------|----------|------|--------|
| User | Route 53 | DNS | 53 | Domain resolution |
| User | CloudFront | HTTPS | 443 | Static content |
| User | ALB | HTTP/HTTPS | 80/443 | Web traffic |
| ALB | EC2 | HTTP | 8080 | App server |
| EC2 | NAT | TCP/UDP | Any | Outbound internet |
| NAT | IGW | Any | Any | Internet access |
| EC2 | RDS | MySQL | 3306 | Database queries |
| EC2 | ElastiCache | TCP | 6379 | Cache access |
| EC2 | S3 | HTTPS | 443 | File storage |
| EC2 | Secrets Manager | HTTPS | 443 | Credentials |
| EC2 | CloudWatch | HTTPS | 443 | Logs/metrics |
| EC2 | SNS | HTTPS | 443 | Notifications |
| EC2 | SQS | HTTPS | 443 | Message queue |
| RDS | S3 | HTTPS | 443 | Backups |
| S3 | CloudFront | Internal | N/A | Content delivery |

---

## Data Flow Sequences

### Scenario 1: User Request to API Endpoint

```
1. User types: api.example.com
2. Browser DNS query → Route 53
3. Route 53 responds: api.example.com → ALIAS → ALB-DNS
4. Browser resolves ALB DNS → ALB IP (54.123.45.67)
5. Browser HTTPS → ALB:443
6. ALB terminates SSL/TLS
7. ALB examines path (/api/users)
8. ALB routes to API Target Group
9. ALB sends to EC2-API-1 (or next in rotation)
10. EC2 receives request on port 8080
11. Application code processes
12. App needs data → RDS query
13. EC2 checks security group: RDS rule allows 3306 ✓
14. EC2 connects to RDS endpoint
15. RDS connection goes to database
16. Query executes
17. RDS returns results
18. App processes results → generates HTML
19. Response sent back to ALB
20. ALB sends back to browser
21. Browser renders page
```

### Scenario 2: Outbound Internet Access

```
1. EC2 in private subnet (10.0.11.50) needs external API
2. Application initiates HTTPS to external.com
3. Route table check: 0.0.0.0/0 → NAT Gateway
4. Packet sent to NAT Gateway (10.0.1.50)
5. NAT translates source:
   FROM: 10.0.11.50:49234
   TO: 54.100.200.1:49234 (NAT's Elastic IP)
6. Packet sent to Internet Gateway
7. IGW sends to internet
8. External service receives from 54.100.200.1
9. Responds to 54.100.200.1:49234
10. NAT receives response
11. NAT translates destination:
    FROM: 54.100.200.1:49234
    TO: 10.0.11.50:49234
12. NAT sends to EC2
13. EC2 application receives response
```

### Scenario 3: Auto Scaling Event

```
1. CloudWatch monitors ASG (CPU metric)
2. Average CPU > 70% for 2 periods (2 minutes)
3. Alarm triggers "HighCPUAlarm"
4. Alarm action: Execute ScaleUpPolicy
5. ASG increases desired capacity: 3 → 4
6. ASG launches new EC2 instance in AZ
7. EC2 boots (2-3 minutes)
8. UserData script runs (1 minute)
   - Updates packages
   - Starts application
9. CloudWatch Agent starts sending metrics
10. ALB starts health checks to new instance
11. GET /health → EC2 port 8080
12. EC2 returns 200 OK (health check passes)
13. After 2 consecutive successes → Instance marked healthy
14. ALB starts routing traffic to new instance
15. New instance now receives ~25% of traffic (4 instances)
16. System rebalances
```

---

## Security Group Permission Map

```
┌────────────────────────────────────────────────────────────┐
│                    INTERNET (0.0.0.0/0)                    │
└────────────────────────────────────────────────────────────┘
              │ Port 80, 443 (HTTP/HTTPS)
              ↓ ALLOWED
         ┌─────────────┐
         │ ALB-SG      │
         │ (Inbound:   │
         │  80, 443)   │
         └─────────────┘
              │ Port 8080
              ↓ ALLOWED (to EC2-SG)
         ┌─────────────┐
         │ EC2-SG      │
         │ (Inbound:   │
         │  8080,3000) │
         └─────────────┘
              │ Port 3306
              ↓ ALLOWED (to RDS-SG)
         ┌─────────────┐
         │ RDS-SG      │
         │ (Inbound:   │
         │  3306)      │
         └─────────────┘
              │
              ↓
         RDS Database
              
Also allowed from EC2-SG:
  • Port 6379 → Cache-SG (ElastiCache)
  • Port 443 → Internet (via NAT)
  • Port 22 → Bastion-SG (SSH)
```

---

## Summary

This architecture provides:

✅ **High Availability**: Multi-AZ with ALB
✅ **Auto Scaling**: Responds to demand
✅ **Scalability**: Horizontal scaling (more EC2s)
✅ **Security**: Multi-layer (SGs, NACL, IAM, encryption)
✅ **Monitoring**: CloudWatch metrics, logs, alarms
✅ **Disaster Recovery**: Multi-region capable
✅ **Cost Efficiency**: Pay only for used resources
✅ **Operational Excellence**: IaC, automation, logging

**Every component serves a purpose and is connected to others via defined security rules and IAM policies.**
