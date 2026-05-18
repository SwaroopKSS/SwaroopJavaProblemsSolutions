# Advanced Multi-Tier Architecture with Disaster Recovery

## Enterprise-Grade Architecture (Multi-Region)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      GLOBAL ARCHITECTURE (Multi-Region)                     │
└─────────────────────────────────────────────────────────────────────────────┘

┌───────────────────────────────────┐    ┌───────────────────────────────────┐
│   PRIMARY REGION: us-east-1       │    │   DR REGION: us-west-2            │
│   (Active)                        │    │   (Standby/Failover)              │
├───────────────────────────────────┤    ├───────────────────────────────────┤
│                                   │    │                                   │
│  Route 53 (Global)                │    │  Route 53 (Global)                │
│  ├─ Primary: Primary ALB (100%)   │────│─ DR: Secondary ALB (0%)           │
│  ├─ Failover: Secondary ALB (0%)  │    │  ├─ Failover: Secondary ALB       │
│  └─ Health check: Primary         │    │  └─ Health check: Secondary       │
│                                   │    │                                   │
│  ┌─────────────────────────────┐  │    │  ┌─────────────────────────────┐  │
│  │ VPC: 10.0.0.0/16            │  │    │  │ VPC: 10.1.0.0/16            │  │
│  │ (3 AZs)                     │  │    │  │ (3 AZs)                     │  │
│  │                             │  │    │  │                             │  │
│  │ ┌──────────────────────────┐│  │    │  │ ┌──────────────────────────┐│  │
│  │ │ ALB (Active)             ││  │    │  │ │ ALB (Standby)            ││  │
│  │ │ • Health: HEALTHY        ││  │    │  │ │ • Health: HEALTHY        ││  │
│  │ │ • Receiving traffic ✓    ││  │    │  │ │ • NOT receiving traffic  ││  │
│  │ │ • SSL/TLS termination    ││  │    │  │ │ • SSL/TLS termination    ││  │
│  │ └──────────────────────────┘│  │    │  │ └──────────────────────────┘│  │
│  │         ↓                     │  │    │  │         ↓                  │  │
│  │ ┌──────────────────────────┐│  │    │  │ ┌──────────────────────────┐│  │
│  │ │ ASG: 3-10 instances      ││  │    │  │ │ ASG: 2 instances (idle) ││  │
│  │ │ • Currently: 5 running   ││  │    │  │ │ • Minimal capacity       ││  │
│  │ │ • Processing requests ✓  ││  │    │  │ │ • Ready for failover     ││  │
│  │ │                          ││  │    │  │ │                          ││  │
│  │ │ ┌────────────────────┐  ││  │    │  │ │ ┌────────────────────┐  ││  │
│  │ │ │ EC2 Server Pool    │  ││  │    │  │ │ │ EC2 Server Pool    │  ││  │
│  │ │ │ (AZ 1a, 1b, 1c)    │  ││  │    │  │ │ │ (AZ 2a, 2b, 2c)    │  ││  │
│  │ │ └────────────────────┘  ││  │    │  │ │ └────────────────────┘  ││  │
│  │ │         ↓                ││  │    │  │ │         ↓               ││  │
│  │ │ ┌────────────────────┐  ││  │    │  │ │ ┌────────────────────┐  ││  │
│  │ │ │ Secrets Manager    │  ││  │    │  │ │ │ Secrets Manager    │  ││  │
│  │ │ │ (Application creds)│  ││  │    │  │ │ │ (Replicated)       │  ││  │
│  │ │ └────────────────────┘  ││  │    │  │ │ └────────────────────┘  ││  │
│  │ │         ↓                ││  │    │  │ │         ↓               ││  │
│  │ │ ┌────────────────────┐  ││  │    │  │ │ ┌────────────────────┐  ││  │
│  │ │ │ RDS Primary (r/w)  │  ││  │    │  │ │ │ RDS Read Replica   │  ││  │
│  │ │ │ • Active instance  │  ││  │    │  │ │ │ • Continuous sync  │  ││  │
│  │ │ │ • Multi-AZ backup  ││  │    │  │ │ │ │ • Can be promoted  │  ││  │
│  │ │ │   in same region   │  ││  │    │  │ │ │ • Read-only        │  ││  │
│  │ │ └────────────────────┘  ││  │    │  │ │ └────────────────────┘  ││  │
│  │ │         ↓                ││  │    │  │ │         ↓               ││  │
│  │ │ ┌────────────────────┐  ││  │    │  │ │ ┌────────────────────┐  ││  │
│  │ │ │ ElastiCache Cluster│  ││  │    │  │ │ │ ElastiCache Cluster│  ││  │
│  │ │ │ (Redis)            │  ││  │    │  │ │ │ (Redis)            │  ││  │
│  │ │ │ • Multi-AZ replica │  ││  │    │  │ │ │ • Independent copy ││  │  │
│  │ │ └────────────────────┘  ││  │    │  │ │ └────────────────────┘  ││  │
│  │ │         ↓                ││  │    │  │ │         ↓               ││  │
│  │ │ ┌────────────────────┐  ││  │    │  │ │ ┌────────────────────┐  ││  │
│  │ │ │ S3 Bucket (source) │  ││  │    │  │ │ │ S3 Bucket (replica)│  ││  │
│  │ │ │ • Versioning on   │  ││  │    │  │ │ │ • Cross-region sync││  │  │
│  │ │ │ • Replication rules││  ││  │    │  │ │ • Real-time copy   │  ││  │
│  │ │ └────────────────────┘  ││  │    │  │ │ └────────────────────┘  ││  │
│  │ └──────────────────────────┘│  │    │  │ └──────────────────────────┘│  │
│  └─────────────────────────────┘  │    │  └─────────────────────────────┘  │
│                                   │    │                                   │
│  Monitoring (us-east-1):          │    │  Monitoring (us-west-2):          │
│  • CloudWatch (Active)            │    │  • CloudWatch (Backup logs)       │
│  • Alarms → SNS → On-call         │    │  • Alarms (disabled)              │
│  • Dashboards (real-time)         │    │  • Dashboards (metrics only)      │
│  • CloudTrail logging             │    │  • CloudTrail logging             │
│                                   │    │                                   │
└───────────────────────────────────┘    └───────────────────────────────────┘
         ↓                                         ↑
         └─────────── S3 Cross-Region Replication ──────────┘
                     (Continuous sync)

         └─────────── RDS Cross-Region Read Replica ────────┘
                     (Continuous replication)
```

---

## Failover Scenario

### Normal Operation (No Failures)

```
                          Route 53
                            │
                    Health check: OK
                            │
                  ┌─────────┴─────────┐
                  ↓                   ↓
            Primary ALB          Secondary ALB
            (100% traffic)       (0% traffic)
            us-east-1            us-west-2
            ✓ Active             ✓ Ready
```

### Primary Region Failure

```
Timeline:

T=0s:   Primary ALB health check fails
        Route 53 detects unhealthy

T=5s:   Route 53 switches DNS
        www.example.com now points to Secondary ALB IP

T=10s:  New requests go to Secondary ALB
        (Minimal delay for DNS propagation)

T=60s:  RDS read replica promoted to primary
        Secondary region becomes fully active

T=300s: ASG in secondary region scales up
        Increases from 2 to desired capacity

Result:
        • Application continues running
        • Data consistent (synced from primary)
        • Minor increase in latency (cross-region)
        • Automated recovery without manual intervention
```

### Failback to Primary

```
Timeline:

T=0s:   Primary region comes back online
        Route 53 continues using secondary

T=120s: Primary RDS restored from backup
        Database is back online

T=180s: Manual decision: Failback?
        After validation, switch back to primary

T=300s: Route 53 switches DNS back
        Traffic returns to primary ALB

Result:
        • Primary region is now primary again
        • Secondary region backs down to standby
        • Costs return to normal
```

---

## Advanced Architecture Patterns

### Pattern 1: Active-Active (Both regions active)

```
                     Route 53
                       │
            ┌──────────┴──────────┐
            ↓                     ↓
       Primary ALB          Secondary ALB
       (50% traffic)        (50% traffic)
       us-east-1            us-west-2

       • Both processing requests
       • Data synced both ways
       • No single point of failure
       • Higher complexity & cost
       • Best for critical systems
```

### Pattern 2: Active-Passive with Auto-Failover

```
                     Route 53
                       │
            Health Check → Primary
                       │
                ┌──────┴──────┐
                ↓             ↓
           Primary ALB   Secondary ALB
           (100% load)   (0% load)
           
           If primary fails:
           Route 53 → Secondary ALB
           Traffic switches instantly
```

### Pattern 3: Blue-Green with Route 53 Weights

```
                     Route 53
                    (example.com)
                       │
            ┌──────────┴──────────┐
            ↓                     ↓
       Blue ALB             Green ALB
       (Weight: 100)        (Weight: 0)
       v1.2.3               v1.3.0
       
       To deploy:
       • Deploy new version to Green
       • Test Green thoroughly
       • Change Route 53 weights:
         Blue: 100 → 90
         Green: 0 → 10
       • Monitor metrics
       • If good: Blue: 90 → 0, Green: 10 → 100
       • If bad: Revert instantly
```

---

## Disaster Recovery Tiers

### Tier 1: Backup & Restore (Cheapest)

```
Primary broken ────────→ Restore from backup ────────→ Back online
                        (4-24 hours)                    (Manual)

Cost: Minimal
Recovery Time Objective (RTO): 4-24 hours
Recovery Point Objective (RPO): 1+ hours
```

### Tier 2: Pilot Light (This architecture)

```
Primary broken ────────→ Promote secondary ────────→ Back online
                        (5-15 minutes)               (Automatic)

Cost: Moderate (pay for small secondary)
RTO: 5-15 minutes
RPO: <5 minutes
```

### Tier 3: Warm Standby

```
Primary broken ────────→ Scale up secondary ────────→ Back online
                        (30-60 seconds)              (Auto-scaling)

Cost: High (secondary always running)
RTO: 30-60 seconds
RPO: <1 minute
```

### Tier 4: Hot/Hot (Active-Active)

```
Primary broken ────────→ No action needed ────────→ Never down
                        (0 seconds)                (No failover)

Cost: Very High (2x infrastructure)
RTO: 0 seconds
RPO: 0 seconds (transaction level)
```

---

## Cost Optimization in Multi-Region

| Component | Primary | Secondary | Saving Strategy |
|-----------|---------|-----------|-----------------|
| **ALB** | Active | Standby | Scale down to 0 NATs per AZ during standby |
| **EC2** | Scaled | Min capacity | Use smaller instances or fewer |
| **RDS** | Primary | Read replica | Less expensive read replica vs primary |
| **ElastiCache** | Active | Independent | Smaller cluster size or no cache |
| **NAT Gateway** | Multi (3) | Single | Only 1 NAT per region for fallback |
| **Data Transfer** | High | Replication | S3 replication charges, RDS sync charges |

**Cost estimate**: 
- Primary region: $X/month
- Secondary region (standby): 20-30% of X/month
- **Total**: ~1.25X baseline cost

---

## Monitoring in Multi-Region

```
┌────────────────────────────────────────────────────────┐
│             CloudWatch (Centralized)                   │
│                                                        │
│  Dashboard: Global Health                             │
│  ├─ Primary Region Status                             │
│  │   ├─ ALB: Healthy/Unhealthy                       │
│  │   ├─ RDS: Primary/Secondary                       │
│  │   ├─ EC2: Running/Stopped                         │
│  │   └─ Data sync lag: X ms                          │
│  │                                                    │
│  ├─ Secondary Region Status                          │
│  │   ├─ ALB: Healthy/Unhealthy                       │
│  │   ├─ RDS: Replica in sync/lagging                 │
│  │   ├─ EC2: Running/Stopped                         │
│  │   └─ Replica lag: X seconds                       │
│  │                                                    │
│  └─ Failover Metrics                                 │
│      ├─ Last failover: HH:MM:SS ago                  │
│      ├─ Failovers this month: N                      │
│      └─ Average failover time: X seconds             │
│                                                        │
│  Alarms:                                              │
│  ├─ Primary ALB unhealthy → Page engineer             │
│  ├─ Replica lag > 30 seconds → Alert                  │
│  ├─ Both regions down → Critical alert                │
│  └─ Failover initiated → Notify team                  │
└────────────────────────────────────────────────────────┘
```

---

## Implementation Checklist

### Phase 1: Single Region (Weeks 1-2)
- [ ] VPC with multi-AZ subnets
- [ ] ALB with health checks
- [ ] ASG for EC2 instances
- [ ] RDS Multi-AZ
- [ ] Monitoring & alarms
- [ ] Backup strategy

### Phase 2: Add Secondary Region (Weeks 3-4)
- [ ] Replicate VPC design
- [ ] Set up RDS read replica
- [ ] Configure S3 cross-region replication
- [ ] Create Route 53 failover records
- [ ] Test manual failover

### Phase 3: Automate Failover (Weeks 5-6)
- [ ] Set up Route 53 health checks
- [ ] Enable automatic failover
- [ ] Create runbooks
- [ ] Disaster recovery drills
- [ ] Document failover process

### Phase 4: Optimize & Scale (Weeks 7+)
- [ ] Implement active-active (optional)
- [ ] Optimize costs
- [ ] Performance tuning
- [ ] Continuous optimization

---

## Common Issues & Solutions

### Issue 1: Replica Lag During Heavy Load

**Problem**: Primary writes too fast, replica can't keep up

**Solution**:
```
1. Monitor replica lag CloudWatch metric
2. If lag > threshold:
   - Increase replica RDS instance type
   - Reduce write throughput (use cache)
   - Add read replicas in primary region
```

### Issue 2: DNS Propagation Delay

**Problem**: After failover, some users still hit primary

**Solution**:
```
1. Reduce TTL to 60 seconds (before failover)
2. Use health check with quick failure (30s)
3. Implement application-level retry logic
```

### Issue 3: Data Inconsistency Between Regions

**Problem**: Writes to primary, reads from secondary, data is stale

**Solution**:
```
1. Always read from primary region (if available)
2. Use strongly consistent reads
3. Accept eventual consistency trade-off
4. Document freshness expectations
```

---

## Summary

Multi-region architecture provides:

✅ **High Availability**: No single point of failure
✅ **Disaster Recovery**: Automatic failover in minutes
✅ **Data Durability**: Replicated across regions
✅ **Compliance**: Data residency in multiple regions
✅ **Performance**: Lower latency for global users
✅ **Resilience**: Can survive entire region failure

**Trade-offs**:
- Higher complexity (more to manage)
- Higher costs (2 infrastructure sets)
- Data consistency challenges
- Operational overhead (testing, monitoring)

**Best for**:
- Mission-critical applications
- Financial systems
- E-commerce platforms
- Healthcare systems
- Government services
