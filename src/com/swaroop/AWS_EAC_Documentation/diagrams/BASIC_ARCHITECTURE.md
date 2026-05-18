# Basic AWS EAC Architecture

## Standard 3-Tier Web Application Architecture

```
┌────────────────────────────────────────────────────────────────────────┐
│                          INTERNET (0.0.0.0/0)                          │
└────────────────────────────────────────┬───────────────────────────────┘
                                         │
                    ┌────────────────────▼─────────────────────┐
                    │        Route 53 (DNS Service)            │
                    │  www.example.com → ALB IP 54.123.45.67  │
                    │  api.example.com → ALB IP 54.123.45.67  │
                    └────────────────────┬─────────────────────┘
                                         │
                                         ↓
┌────────────────────────────────────────────────────────────────────────┐
│  VPC: 10.0.0.0/16                                                      │
│                                                                        │
│  ┌──────────────────────────────────────────────────────────────┐    │
│  │  PUBLIC SUBNETS (AZ 1a, 1b, 1c)                             │    │
│  │  10.0.1.0/24, 10.0.2.0/24, 10.0.3.0/24                     │    │
│  │                                                              │    │
│  │  ┌──────────────────────────────────────────────────────┐  │    │
│  │  │         Application Load Balancer (ALB)              │  │    │
│  │  │  IP: 10.0.1.100, 10.0.2.100, 10.0.3.100            │  │    │
│  │  │  DNS: my-alb-123456.us-east-1.elb.amazonaws.com    │  │    │
│  │  │                                                       │  │    │
│  │  │  Listeners:                                          │  │    │
│  │  │    Port 80 (HTTP) → Redirect to HTTPS               │  │    │
│  │  │    Port 443 (HTTPS) → Target Groups                 │  │    │
│  │  │                                                       │  │    │
│  │  │  Rules:                                              │  │    │
│  │  │    /api/* → API Target Group                        │  │    │
│  │  │    /web/* → Web Target Group                        │  │    │
│  │  │    default → Default Target Group                   │  │    │
│  │  └──────────────────────────────────────────────────────┘  │    │
│  │         ↓                    ↓                ↓              │    │
│  │  ┌────────────┐      ┌────────────┐   ┌────────────┐       │    │
│  │  │  NAT GW   │      │  NAT GW    │   │  NAT GW    │       │    │
│  │  │ (1a)      │      │  (1b)      │   │  (1c)      │       │    │
│  │  └────────────┘      └────────────┘   └────────────┘       │    │
│  └──────────────────────────────────────────────────────────────┘    │
│                    ↓                ↓                ↓                 │
│  ┌──────────────────────────────────────────────────────────────┐    │
│  │  PRIVATE SUBNETS (AZ 1a, 1b, 1c)                            │    │
│  │  10.0.11.0/24, 10.0.12.0/24, 10.0.13.0/24                  │    │
│  │                                                              │    │
│  │  ┌────────────────┐  ┌────────────────┐ ┌────────────────┐ │    │
│  │  │  EC2 Instances │  │ EC2 Instances  │ │ EC2 Instances  │ │    │
│  │  │  (Auto Scaling)│  │ (Auto Scaling) │ │(Auto Scaling)  │ │    │
│  │  │                │  │                │ │                │ │    │
│  │  │ 10.0.11.10-50 │  │ 10.0.12.10-50 │ │10.0.13.10-50  │ │    │
│  │  │                │  │                │ │                │ │    │
│  │  │ Runs on Port:  │  │ Runs on Port:  │ │ Runs on Port:  │ │    │
│  │  │ 8080 (API)     │  │ 8080 (API)     │ │ 8080 (API)     │ │    │
│  │  │ 3000 (Web)     │  │ 3000 (Web)     │ │ 3000 (Web)     │ │    │
│  │  └────────────────┘  └────────────────┘ └────────────────┘ │    │
│  │         ↓                    ↓                ↓              │    │
│  │         └────────────┬───────┴────────┬──────┘              │    │
│  │                      ↓                                       │    │
│  │           ┌──────────────────────┐                          │    │
│  │           │   RDS Instance       │                          │    │
│  │           │  (MySQL/PostgreSQL)  │                          │    │
│  │           │   10.0.14.50         │                          │    │
│  │           │                      │                          │    │
│  │           │  Multi-AZ Enabled:   │                          │    │
│  │           │  Primary: 1a         │                          │    │
│  │           │  Standby: 1b         │                          │    │
│  │           └──────────────────────┘                          │    │
│  │                      ↓                                       │    │
│  │           ┌──────────────────────┐                          │    │
│  │           │   ElastiCache        │                          │    │
│  │           │   (Redis/Memcached)  │                          │    │
│  │           │   10.0.15.50         │                          │    │
│  │           └──────────────────────┘                          │    │
│  └──────────────────────────────────────────────────────────────┘    │
│         ↑                              ↑                               │
│         │ Outbound Internet (NAT)      │                               │
│         ↓                              ↓                               │
│  ┌──────────────────────────────────────────────────────────────┐    │
│  │  Internet Gateway (IGW)                                      │    │
│  │  Connects VPC to internet                                    │    │
│  └──────────────────────────────────────────────────────────────┘    │
│         ↑                                                              │
│         │ Route: 0.0.0.0/0 → IGW                                     │
│         ↓                                                              │
└────────────────────────────────────────┬───────────────────────────────┘
                                         │
                                         ↓
                    ┌────────────────────────────────────────┐
                    │        External Services                │
                    │  • S3 (via Gateway Endpoint)           │
                    │  • SNS (via Interface Endpoint)        │
                    │  • SQS (via Interface Endpoint)        │
                    │  • CloudWatch (Logs/Metrics)          │
                    └────────────────────────────────────────┘
```

---

## Component Connectivity Details

### 1. User Request Flow

```
User Browser (210.1.1.1)
        ↓
1. GET https://www.example.com
        ↓
2. DNS Query to Route 53
   "What is the IP for www.example.com?"
        ↓
3. Route 53 Response
   "www.example.com is an ALIAS to:
    my-alb-123456.us-east-1.elb.amazonaws.com"
        ↓
4. Browser resolves ALB DNS
   "ALB DNS resolves to: 54.123.45.67"
        ↓
5. Browser establishes HTTPS to 54.123.45.67
        ↓
6. ALB (in AZ 1a, 1b, or 1c)
   - Terminates SSL/TLS
   - Examines request path/hostname
   - Routes to appropriate target group
        ↓
7. Request goes to EC2 instance
   (one of many in Auto Scaling Group)
        ↓
8. EC2 processes request
   - May query RDS database
   - May get data from ElastiCache
   - Generates response
        ↓
9. Response sent back through ALB to user
        ↓
10. User receives response
```

### 2. Database Connection Flow

```
EC2 Instance (10.0.11.50, Port 8080)
        ↓
1. Application needs data
   "SELECT * FROM users WHERE id=123"
        ↓
2. App opens connection to RDS endpoint
   "mysql-db.12345.us-east-1.rds.amazonaws.com"
        ↓
3. EC2 DNS resolves RDS endpoint to IP
   "10.0.14.50" (inside VPC)
        ↓
4. EC2 opens connection to 10.0.14.50:3306
   - Security Group check: EC2-SG allows traffic to RDS-SG ✓
   - Network ACL check: Private subnet allows outbound ✓
        ↓
5. RDS connection established
   - Security Group check: RDS-SG allows traffic from EC2-SG ✓
   - Network ACL check: Private subnet allows inbound ✓
        ↓
6. Query executes in RDS database
        ↓
7. RDS returns result set
        ↓
8. EC2 closes connection
```

### 3. Outbound Internet Access Flow

```
EC2 Instance (10.0.11.50)
        ↓
1. Application needs external data
   "GET https://api.external-service.com/data"
        ↓
2. EC2 creates outbound connection
        ↓
3. Check Route Table for private subnet
   Route: 0.0.0.0/0 → NAT Gateway
        ↓
4. Packet sent to NAT Gateway (10.0.1.50 in AZ 1a)
   - NAT translates source IP
   - 10.0.11.50:53421 → 54.100.200.1:53421
   - (Elastic IP of NAT Gateway)
        ↓
5. NAT Gateway sends request to Internet Gateway
        ↓
6. Internet Gateway sends to external internet
        ↓
7. External service responds
        ↓
8. NAT Gateway receives response
   - Translates destination back
   - 54.100.200.1:53421 → 10.0.11.50:53421
        ↓
9. Response sent back to EC2 instance
        ↓
10. Application receives data
```

---

## Security Groups Architecture

```
┌─────────────────────────────────────────────────┐
│  ALB Security Group (alb-sg)                    │
│  Inbound:                                       │
│    - HTTP (80) from 0.0.0.0/0 (internet)       │
│    - HTTPS (443) from 0.0.0.0/0 (internet)     │
│  Outbound:                                      │
│    - All traffic to EC2-SG                      │
│    - All traffic to RDS-SG                      │
└─────────────────────────────────────────────────┘
         ↓
         │ (allows traffic)
         ↓
┌─────────────────────────────────────────────────┐
│  EC2 Security Group (ec2-sg)                    │
│  Inbound:                                       │
│    - Port 8080 from alb-sg                      │
│    - Port 3000 from alb-sg                      │
│    - Port 22 from bastion-sg (SSH)              │
│  Outbound:                                      │
│    - Port 3306 to rds-sg (MySQL)                │
│    - Port 6379 to cache-sg (Redis)              │
│    - All traffic to 0.0.0.0/0 (internet)        │
└─────────────────────────────────────────────────┘
         ↓
         │ (allows traffic)
         ↓
┌─────────────────────────────────────────────────┐
│  RDS Security Group (rds-sg)                    │
│  Inbound:                                       │
│    - Port 3306 from ec2-sg (MySQL)              │
│  Outbound:                                      │
│    - None (RDS doesn't initiate outbound)       │
└─────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────┐
│  ElastiCache SG (cache-sg)                      │
│  Inbound:                                       │
│    - Port 6379 from ec2-sg (Redis)              │
│  Outbound:                                      │
│    - None (Cache doesn't initiate outbound)     │
└─────────────────────────────────────────────────┘
```

---

## Scaling Behavior

```
Normal Load (Traffic: 5 req/s):
  Auto Scaling Group desired capacity: 3
  ┌────────────────────────────────────────┐
  │  EC2-1  │  EC2-2  │  EC2-3  │         │
  │         │         │         │         │
  │ 33 req  │ 33 req  │ 33 req  │         │
  │  /3s    │  /3s    │  /3s    │         │
  └────────────────────────────────────────┘

High Load (Traffic: 50 req/s):
  CloudWatch detects high CPU (>70%)
  ASG scales up to desired capacity: 9
  ┌────────────────────────────────────────┐
  │ EC2-1 │ EC2-2 │ EC2-3 │ ... │ EC2-9  │
  │ ~5.5  │ ~5.5  │ ~5.5  │ ... │ ~5.5   │
  │ req/s │ req/s │ req/s │ ... │ req/s  │
  └────────────────────────────────────────┘

Low Load (Traffic: 2 req/s):
  CloudWatch detects low CPU (<30%)
  ASG scales down to desired capacity: 2
  ┌────────────────────────────────────────┐
  │  EC2-1  │  EC2-2  │                    │
  │         │         │                    │
  │  1 req  │  1 req  │                    │
  │  /s     │  /s     │                    │
  └────────────────────────────────────────┘
```

---

## High Availability Features

### 1. Multi-AZ Deployment

```
Route 53 (DNS)
    ↓
    ├─→ AZ 1a: ALB + EC2 instances + NAT + Database
    ├─→ AZ 1b: ALB + EC2 instances + NAT + Database (Replica)
    └─→ AZ 1c: ALB + EC2 instances + NAT + Database

If AZ 1a fails:
  - Route 53 fails over to AZ 1b/1c
  - Users continue to access application
  - RDS automatic failover to replica
  - No downtime
```

### 2. ALB Health Checks

```
Every 30 seconds:
  ALB → GET /health to each EC2 instance
  
Instance Response:
  200 OK → Mark healthy
  500 Error → Mark unhealthy (after 3 failures)
  
If unhealthy:
  ALB removes from rotation
  ASG terminates and launches replacement
```

### 3. Auto Scaling Recovery

```
EC2 instance fails:
  ↓
ASG detects unhealthy (failed health check)
  ↓
ASG terminates failed instance
  ↓
ASG launches replacement instance
  ↓
Replacement goes through:
  1. Boot process (1-2 minutes)
  2. User data script
  3. Health check (passes after 2 successes)
  ↓
Traffic routed to new instance
  (No user impact if ASG > 1)
```

---

## Data Flow Summary

| Flow Type | Source | Destination | Protocol | Port |
|-----------|--------|-------------|----------|------|
| User to ALB | Internet | ALB | HTTP/HTTPS | 80/443 |
| ALB to EC2 | ALB | EC2 | HTTP | 8080/3000 |
| EC2 to RDS | EC2 | RDS | MySQL | 3306 |
| EC2 to Cache | EC2 | ElastiCache | Redis | 6379 |
| EC2 to NAT | EC2 | NAT | Any | Any |
| NAT to IGW | NAT | IGW | Any | Any |
| IGW to Internet | IGW | External | Any | Any |

---

## Cost Optimization in This Architecture

| Component | Cost Factor |
|-----------|------------|
| **ALB** | Hourly + LCU (Load Capacity Units) |
| **NAT Gateway** | Hourly + data transfer |
| **EC2** | Instance hours + data transfer |
| **RDS** | Instance hours + storage |
| **ElastiCache** | Instance hours + data transfer |
| **Data Transfer** | Out of VPC to internet |
| **Route 53** | Hosted zone + queries |

**Cost Reduction Tips**:
1. Use Single NAT per AZ (vs. one per instance)
2. Right-size EC2 instances
3. Use Reserved Instances for predictable load
4. Enable Auto Scaling to match actual demand
5. Use VPC endpoints for AWS service access (avoid internet)

---

See [ROUTE53_ALB_CONNECTIVITY.md](../ROUTE53_ALB_CONNECTIVITY.md) for detailed connectivity examples.
