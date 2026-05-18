# AWS Components - Detailed Definitions

## Table of Contents
1. [Networking Components](#networking-components)
2. [Compute Components](#compute-components)
3. [Storage Components](#storage-components)
4. [Database Components](#database-components)
5. [Security Components](#security-components)
6. [Monitoring Components](#monitoring-components)

---

## Networking Components

### 1. VPC (Virtual Private Cloud)

**Definition**: A logically isolated section of the AWS cloud where you can launch AWS resources.

**Key Features**:
- Custom IP address range (CIDR block) - e.g., 10.0.0.0/16
- Public and private subnets
- Route tables to control traffic
- Network Access Control Lists (NACLs)
- Internet Gateway for external connectivity

**In EAC Context**:
```yaml
VPC:
  CIDR: 10.0.0.0/16
  EnableDnsHostnames: true
  EnableDnsSupport: true
  
Subnets:
  - Name: PublicSubnet1
    CIDR: 10.0.1.0/24
    AZ: us-east-1a
    MapPublicIpOnLaunch: true
  
  - Name: PrivateSubnet1
    CIDR: 10.0.11.0/24
    AZ: us-east-1a
```

**Use Cases**:
- Network isolation
- Multi-tier architecture
- Hybrid cloud setup

---

### 2. Route 53

**Definition**: AWS's scalable DNS (Domain Name System) and domain registration service.

**Key Features**:
- Domain name registration
- DNS query resolution
- Health checking
- Traffic policy routing
  - Simple routing
  - Weighted routing (distribute traffic by percentage)
  - Latency-based routing (route to closest region)
  - Geolocation routing (based on geographic location)
  - Failover routing (active-passive)
  - Multi-value answer routing

**DNS Record Types**:
- **A Record**: Maps domain to IPv4 address
- **AAAA Record**: Maps domain to IPv6 address
- **CNAME**: Canonical name (alias to another domain)
- **MX**: Mail exchange records
- **TXT**: Text records for domain verification
- **NS**: Nameserver records

**In EAC Context**:
```yaml
HostedZone:
  Name: example.com
  Type: Public

RecordSets:
  - Name: example.com
    Type: A
    TTL: 300
    AliasTarget: ALB_DNS_Name
  
  - Name: www.example.com
    Type: A
    TTL: 300
    AliasTarget: ALB_DNS_Name
  
  - Name: api.example.com
    Type: A
    TTL: 300
    Weight: 100
    SetIdentifier: api-primary
    AliasTarget: ALB_DNS_Name
```

**Use Cases**:
- Domain management
- Traffic distribution
- Disaster recovery
- A/B testing
- Blue-green deployments

---

### 3. Application Load Balancer (ALB)

**Definition**: A Layer 7 (Application layer) load balancer that distributes incoming application traffic across multiple targets.

**Key Features**:
- **Layer 7 Routing**: Can route based on:
  - Path patterns (/api/*, /images/*)
  - Hostname (api.example.com vs app.example.com)
  - HTTP headers
  - Query parameters
- Target groups (EC2 instances, Lambda, IP addresses)
- Health checks
- SSL/TLS termination
- Request/response modification

**Architecture**:
```
ALB (Public)
├── Listener (Port 80, 443)
│   ├── Rule 1: /api/* → API Target Group
│   ├── Rule 2: /web/* → Web Target Group
│   └── Default → Default Target Group
│
├── Target Group 1 (API)
│   ├── EC2-API-1
│   └── EC2-API-2
│
└── Target Group 2 (Web)
    ├── EC2-Web-1
    └── EC2-Web-2
```

**In EAC Context**:
```yaml
LoadBalancer:
  Name: my-alb
  Scheme: internet-facing
  Type: application
  Subnets:
    - PublicSubnet1
    - PublicSubnet2

Listeners:
  - Port: 80
    Protocol: HTTP
    DefaultActions:
      - Type: forward
        TargetGroupArn: DefaultTargetGroup
  
  - Port: 443
    Protocol: HTTPS
    Certificates:
      - CertificateArn: arn:aws:acm:...
    Rules:
      - Conditions:
          - Field: path-pattern
            Values: ["/api/*"]
        Actions:
          - TargetGroupArn: ApiTargetGroup
      
      - Conditions:
          - Field: hostname-pattern
            Values: ["static.example.com"]
        Actions:
          - TargetGroupArn: StaticTargetGroup

TargetGroups:
  - Name: ApiTargetGroup
    Port: 8080
    Protocol: HTTP
    HealthCheck:
      Path: /health
      Interval: 30
      Timeout: 5
      HealthyThreshold: 2
      UnhealthyThreshold: 3
```

**Use Cases**:
- Microservices routing
- High-traffic applications
- Content-based routing
- SSL/TLS offloading

---

### 4. Internet Gateway

**Definition**: A VPC component that allows communication between instances in VPC and the internet.

**Key Features**:
- Enables IPv4 and IPv6 communication
- No bandwidth limitations
- Highly available

**In EAC Context**:
```yaml
InternetGateway:
  VpcId: vpc-12345
  Tags:
    - Key: Name
      Value: my-igw

RouteTable:
  VpcId: vpc-12345
  Routes:
    - DestinationCidrBlock: 0.0.0.0/0
      GatewayId: igw-12345
```

---

### 5. NAT Gateway

**Definition**: Allows private subnet resources to connect to the internet while remaining private.

**Key Features**:
- One-way outbound traffic only
- Elastic IP required
- Placed in public subnet

**In EAC Context**:
```yaml
NATGateway:
  SubnetId: PublicSubnet1
  AllocationId: eip-12345
  Tags:
    - Key: Name
      Value: my-nat

PrivateRouteTable:
  VpcId: vpc-12345
  Routes:
    - DestinationCidrBlock: 0.0.0.0/0
      NatGatewayId: nat-12345
```

---

## Compute Components

### 1. EC2 (Elastic Compute Cloud)

**Definition**: Resizable virtual computing resources (virtual machines).

**Key Features**:
- Instance types (t2.micro, t3.medium, m5.large, etc.)
- Flexible pricing (on-demand, reserved, spot)
- Security groups for network isolation
- Key pairs for SSH access

**Instance Types**:
- **t2/t3**: General purpose, burstable
- **m5/m6**: General purpose, balanced
- **c5/c6**: Compute optimized
- **r5/r6**: Memory optimized
- **i3/i4**: Storage optimized
- **g4/p3**: GPU instances

**In EAC Context**:
```yaml
EC2Instance:
  ImageId: ami-0c55b159cbfafe1f0  # Amazon Linux 2
  InstanceType: t3.medium
  KeyName: my-key-pair
  SubnetId: PrivateSubnet1
  SecurityGroupIds:
    - sg-12345
  IamInstanceProfile: EC2InstanceProfile
  UserData: |
    #!/bin/bash
    yum update -y
    yum install -y httpd
    systemctl start httpd
  Tags:
    - Key: Name
      Value: web-server-1
```

---

### 2. Auto Scaling Group (ASG)

**Definition**: Automatically scales the number of EC2 instances based on demand.

**Key Features**:
- Minimum, maximum, and desired capacity
- Scaling policies (target tracking, step scaling)
- Launch templates
- Instance refresh for rolling updates
- Health check replacement

**Scaling Policy Types**:
- **Target Tracking**: Maintain specific metric (e.g., 70% CPU)
- **Step Scaling**: Scale in steps based on metric value
- **Simple Scaling**: Single adjustment
- **Scheduled Actions**: Scale at specific times

**In EAC Context**:
```yaml
LaunchTemplate:
  ImageId: ami-0c55b159cbfafe1f0
  InstanceType: t3.medium
  KeyName: my-key-pair
  SecurityGroupIds:
    - sg-12345
  IamInstanceProfile: EC2InstanceProfile
  TagSpecifications:
    - ResourceType: instance
      Tags:
        - Key: Name
          Value: web-server

AutoScalingGroup:
  LaunchTemplate: web-server-template
  MinSize: 2
  MaxSize: 10
  DesiredCapacity: 3
  VPCZoneIdentifier:
    - PrivateSubnet1
    - PrivateSubnet2
  TargetGroupArns:
    - ApiTargetGroup
  HealthCheckType: ELB
  HealthCheckGracePeriod: 300

ScalingPolicy:
  PolicyName: cpu-scaling
  AdjustmentType: TargetTrackingScaling
  TargetValue: 70.0
  PredefinedMetricSpecification:
    PredefinedMetricType: ASGAverageCPUUtilization
  ScaleOutCooldown: 60
  ScaleInCooldown: 300
```

---

## Storage Components

### 1. S3 (Simple Storage Service)

**Definition**: Object storage service for files, backups, and static content.

**Key Features**:
- Unlimited storage capacity
- 99.99% availability
- Versioning
- Encryption (SSE-S3, SSE-KMS)
- Lifecycle policies
- Access control (bucket policies, ACLs)
- Static website hosting

**Storage Classes**:
- **S3 Standard**: General purpose
- **S3-IA**: Infrequent access
- **S3 Glacier**: Long-term archival
- **S3 Intelligent-Tiering**: Automatic cost optimization

**In EAC Context**:
```yaml
S3Bucket:
  BucketName: my-app-bucket
  VersioningConfiguration: Enabled
  BucketEncryption:
    ServerSideEncryptionConfiguration:
      - ServerSideEncryptionByDefault:
          SSEAlgorithm: AES256
  PublicAccessBlockConfiguration:
    BlockPublicAcls: true
    BlockPublicPolicy: true
    IgnorePublicAcls: true
    RestrictPublicBuckets: true
  LifecycleConfiguration:
    Rules:
      - Id: archive-old-files
        Status: Enabled
        Prefix: logs/
        Transitions:
          - Days: 30
            StorageClass: GLACIER
        Expiration:
          Days: 365

BucketPolicy:
  Sid: AllowCloudFront
  Effect: Allow
  Principal:
    Service: cloudfront.amazonaws.com
  Action: s3:GetObject
  Resource: arn:aws:s3:::my-app-bucket/*
```

---

## Database Components

### 1. RDS (Relational Database Service)

**Definition**: Managed relational database service supporting multiple database engines.

**Supported Engines**:
- MySQL
- MariaDB
- PostgreSQL
- Oracle
- SQL Server
- Amazon Aurora

**Key Features**:
- Automated backups
- Multi-AZ deployment for HA
- Read replicas for scaling
- Automated patching
- Encryption at rest and in transit
- Performance Insights

**In EAC Context**:
```yaml
RDSInstance:
  DBInstanceIdentifier: my-database
  DBInstanceClass: db.t3.micro
  Engine: mysql
  EngineVersion: 8.0.28
  MasterUsername: admin
  MasterUserPassword: !Sub '{{resolve:secretsmanager:MyDatabaseSecret:SecretString:password}}'
  AllocatedStorage: 100
  StorageType: gp3
  StorageEncrypted: true
  VpcSecurityGroupIds:
    - sg-rds-12345
  DBSubnetGroupName: private-subnets
  MultiAZ: true
  BackupRetentionPeriod: 30
  PreferredBackupWindow: 03:00-04:00
  PreferredMaintenanceWindow: sun:04:00-sun:05:00
  EnableCloudwatchLogsExports:
    - error
    - general
    - slowquery
  EnableIAMDatabaseAuthentication: true
```

---

## Security Components

### 1. Security Groups

**Definition**: Virtual firewall rules controlling inbound and outbound traffic.

**Key Features**:
- Stateful (return traffic auto-allowed)
- Can reference other security groups
- Rules by protocol and port
- Default deny all inbound

**In EAC Context**:
```yaml
ALBSecurityGroup:
  GroupDescription: Allow HTTP/HTTPS from internet
  VpcId: vpc-12345
  SecurityGroupIngress:
    - IpProtocol: tcp
      FromPort: 80
      ToPort: 80
      CidrIp: 0.0.0.0/0
    - IpProtocol: tcp
      FromPort: 443
      ToPort: 443
      CidrIp: 0.0.0.0/0
  SecurityGroupEgress:
    - IpProtocol: -1
      CidrIp: 0.0.0.0/0

EC2SecurityGroup:
  GroupDescription: Allow traffic from ALB
  VpcId: vpc-12345
  SecurityGroupIngress:
    - IpProtocol: tcp
      FromPort: 8080
      ToPort: 8080
      SourceSecurityGroupId: !Ref ALBSecurityGroup

RDSSecurityGroup:
  GroupDescription: Allow traffic from EC2
  VpcId: vpc-12345
  SecurityGroupIngress:
    - IpProtocol: tcp
      FromPort: 3306
      ToPort: 3306
      SourceSecurityGroupId: !Ref EC2SecurityGroup
```

---

### 2. IAM (Identity and Access Management)

**Definition**: Service for managing users, roles, and permissions.

**Key Concepts**:
- **Users**: Individual accounts
- **Roles**: Assumed by services or users
- **Policies**: Permission statements
- **Trust Policy**: Who can assume the role

**In EAC Context**:
```yaml
EC2Role:
  AssumeRolePolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          Service: ec2.amazonaws.com
        Action: sts:AssumeRole

EC2Policy:
  PolicyName: EC2AppPolicy
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Action:
          - s3:GetObject
          - s3:PutObject
        Resource:
          - arn:aws:s3:::my-app-bucket/*
      - Effect: Allow
        Action:
          - rds-db:connect
        Resource:
          - arn:aws:rds:region:account:db:my-database

RoleAttachment:
  RoleName: !Ref EC2Role
  PolicyArn: !GetAtt EC2Policy.Arn
```

---

## Monitoring Components

### 1. CloudWatch

**Definition**: Monitoring and observability service for AWS resources.

**Key Features**:
- Metrics collection
- Log aggregation
- Alarms and notifications
- Dashboards
- Insights queries

**In EAC Context**:
```yaml
LogGroup:
  LogGroupName: /aws/application/logs
  RetentionInDays: 7

Alarm:
  AlarmName: high-cpu-alarm
  MetricName: CPUUtilization
  Namespace: AWS/EC2
  Statistic: Average
  Period: 300
  EvaluationPeriods: 2
  Threshold: 80
  ComparisonOperator: GreaterThanThreshold
  AlarmActions:
    - !Ref SNSTopic
```

---

**This completes the AWS Components definitions. Refer to the other documentation files for connectivity and examples.**
