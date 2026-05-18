# AWS Security & IAM in EAC

## IAM Fundamentals

**IAM (Identity and Access Management)** = AWS's authorization system.

```
Who (Principal)
    ↓
Principal = User, Role, or Service
    ↓
What (Action)
    ↓
Action = s3:GetObject, ec2:RunInstances, etc.
    ↓
Where (Resource)
    ↓
Resource = S3 bucket, EC2 instance, etc.
    ↓
When (Condition)
    ↓
Condition = IP range, time of day, etc.
```

---

## IAM Policy Structure

### Basic IAM Policy

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": "arn:aws:s3:::my-bucket/*"
    }
  ]
}
```

### Policy Components

| Component | Meaning |
|-----------|---------|
| **Version** | Policy language version (always "2012-10-17") |
| **Statement** | Array of permissions |
| **Effect** | Allow or Deny |
| **Action** | What can be done (service:action) |
| **Resource** | What resources are affected |
| **Condition** | When the policy applies |

---

## IAM Roles vs Users

### IAM Users
- Individual person or application
- Long-term credentials (access key + secret)
- Not for AWS services

### IAM Roles
- Temporary credentials (STS tokens)
- Assumed by users or AWS services
- Recommended for services

**In EAC Context**:
```yaml
# User (for humans)
User:
  UserName: john.doe
  LoginProfile:
    Password: !Sub 'arn:aws:secretsmanager:...'
  Groups:
    - developers

# Role (for services)
EC2Role:
  AssumeRolePolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          Service: ec2.amazonaws.com
        Action: sts:AssumeRole

RDSRole:
  AssumeRolePolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          Service: rds.amazonaws.com
        Action: sts:AssumeRole
```

---

## Trust Policy (AssumeRolePolicyDocument)

**Trust Policy** = Who can assume this role?

```yaml
AssumeRolePolicyDocument:
  Version: '2012-10-17'
  Statement:
    # Allow EC2 service to assume this role
    - Effect: Allow
      Principal:
        Service: ec2.amazonaws.com
      Action: sts:AssumeRole

    # Allow Lambda service to assume this role
    - Effect: Allow
      Principal:
        Service: lambda.amazonaws.com
      Action: sts:AssumeRole

    # Allow specific user to assume this role
    - Effect: Allow
      Principal:
        AWS: arn:aws:iam::123456:user/john.doe
      Action: sts:AssumeRole
      Condition:
        StringEquals:
          sts:ExternalId: unique-external-id
```

---

## Common IAM Policies

### 1. EC2 to S3 Access

```yaml
EC2S3Policy:
  PolicyName: EC2-S3-Access
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      # Allow reading from S3 bucket
      - Effect: Allow
        Action:
          - s3:GetObject
        Resource: arn:aws:s3:::my-app-bucket/*

      # Allow listing bucket contents
      - Effect: Allow
        Action:
          - s3:ListBucket
        Resource: arn:aws:s3:::my-app-bucket
      
      # Allow uploading to S3
      - Effect: Allow
        Action:
          - s3:PutObject
        Resource: arn:aws:s3:::my-app-bucket/uploads/*
      
      # Deny access to sensitive folder
      - Effect: Deny
        Action: s3:*
        Resource: arn:aws:s3:::my-app-bucket/secrets/*
```

**In EAC**:
```yaml
EC2Role:
  AssumeRolePolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          Service: ec2.amazonaws.com
        Action: sts:AssumeRole

EC2S3Policy:
  PolicyName: EC2-S3-Access
  PolicyDocument: # (same as above)
  Roles:
    - !Ref EC2Role

EC2InstanceProfile:
  Roles:
    - !Ref EC2Role
```

### 2. EC2 to RDS Database Access

```yaml
EC2RDSPolicy:
  PolicyName: EC2-RDS-Access
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      # For IAM database authentication
      - Effect: Allow
        Action:
          - rds-db:connect
        Resource:
          - arn:aws:rds:us-east-1:123456:db:mydb
          - arn:aws:rds:us-east-1:123456:db:mydb/*
```

### 3. EC2 to Secrets Manager

```yaml
EC2SecretsPolicy:
  PolicyName: EC2-Secrets-Access
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      # Allow reading database password
      - Effect: Allow
        Action:
          - secretsmanager:GetSecretValue
        Resource: arn:aws:secretsmanager:us-east-1:123456:secret:MyDatabaseSecret-*
```

### 4. Lambda to DynamoDB

```yaml
LambdaDynamoPolicy:
  PolicyName: Lambda-DynamoDB-Access
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Action:
          - dynamodb:GetItem
          - dynamodb:PutItem
          - dynamodb:Query
          - dynamodb:Scan
        Resource: arn:aws:dynamodb:us-east-1:123456:table/MyTable
```

---

## Security Groups (Network-Level)

**Security Group** = Virtual firewall at EC2 instance level.

**Characteristics**:
- Stateful (return traffic auto-allowed)
- Default: Deny all inbound, Allow all outbound
- Can reference other security groups

### Example: Three-Tier Application

```yaml
# Tier 1: ALB (Public)
ALBSecurityGroup:
  GroupDescription: Allow internet traffic
  SecurityGroupIngress:
    - IpProtocol: tcp
      FromPort: 80
      ToPort: 80
      CidrIp: 0.0.0.0/0  # From internet
    
    - IpProtocol: tcp
      FromPort: 443
      ToPort: 443
      CidrIp: 0.0.0.0/0  # From internet
  
  SecurityGroupEgress:
    - IpProtocol: -1
      CidrIp: 0.0.0.0/0  # To anywhere

# Tier 2: EC2 Application Servers (Private)
AppSecurityGroup:
  GroupDescription: Allow traffic from ALB only
  SecurityGroupIngress:
    # From ALB only
    - IpProtocol: tcp
      FromPort: 8080
      ToPort: 8080
      SourceSecurityGroupId: !Ref ALBSecurityGroup
    
    # SSH from Bastion Host only
    - IpProtocol: tcp
      FromPort: 22
      ToPort: 22
      SourceSecurityGroupId: !Ref BastionSecurityGroup
  
  SecurityGroupEgress:
    # To RDS
    - IpProtocol: tcp
      FromPort: 3306
      ToPort: 3306
      DestinationSecurityGroupId: !Ref DBSecurityGroup
    
    # To internet (NAT Gateway)
    - IpProtocol: -1
      CidrIp: 0.0.0.0/0

# Tier 3: RDS Database (Private)
DBSecurityGroup:
  GroupDescription: Allow traffic from EC2 only
  SecurityGroupIngress:
    - IpProtocol: tcp
      FromPort: 3306
      ToPort: 3306
      SourceSecurityGroupId: !Ref AppSecurityGroup  # From EC2 only
  
  SecurityGroupEgress:
    # RDS doesn't need outbound (usually)
    - IpProtocol: -1
      CidrIp: 127.0.0.1/32  # Essentially none
```

**Traffic Flow**:
```
Internet (0.0.0.0/0)
    ↓
Port 80/443 → ALBSecurityGroup ✓ Allow
    ↓
Port 8080 → AppSecurityGroup ✓ Allow (from ALB)
    ↓
Port 3306 → DBSecurityGroup ✓ Allow (from EC2)
    ↓
Database processes request
```

---

## Network ACLs (NACLs) - Subnet-Level

**NACL** = Stateless firewall at subnet level.

```yaml
PrivateSubnetNACL:
  VpcId: !Ref VPC
  Tags:
    - Key: Name
      Value: private-subnet-nacl

# Inbound Rules
InboundRule_Local:
  NetworkAclId: !Ref PrivateSubnetNACL
  RuleNumber: 100
  Protocol: -1  # All protocols
  RuleAction: allow
  CidrBlock: 10.0.0.0/16  # VPC CIDR (local)

InboundRule_NAT:
  NetworkAclId: !Ref PrivateSubnetNACL
  RuleNumber: 110
  Protocol: 6  # TCP
  RuleAction: allow
  CidrBlock: 0.0.0.0/0  # Return traffic from NAT
  PortRange:
    From: 1024
    To: 65535  # Ephemeral ports

InboundRule_Deny_SSH:
  NetworkAclId: !Ref PrivateSubnetNACL
  RuleNumber: 32767  # Lowest priority
  Protocol: 6  # TCP
  RuleAction: deny
  CidrBlock: 0.0.0.0/0
  PortRange:
    From: 22
    To: 22  # Block SSH from internet

# Outbound Rules (similar)
```

---

## Multi-Account IAM Setup

**Scenario**: Organization with dev, staging, prod AWS accounts

```
Root Account (Billing)
    ├─ Dev Account
    │   └─ Developer role
    ├─ Staging Account
    │   └─ DevOps role
    └─ Prod Account
        └─ DBA role

Developer in Dev Account:
  1. Assume DeveloperRole (in dev account)
  2. Can access resources in dev account only
  
DBA in Prod Account:
  1. Assume DBARole (in prod account)
  2. Can access databases in prod account only
```

**Cross-Account Access**:
```yaml
# In Dev Account
DevAccountRole:
  AssumeRolePolicyDocument:
    Statement:
      - Effect: Allow
        Principal:
          AWS: arn:aws:iam::123456:root  # Root of dev account
        Action: sts:AssumeRole

# In Prod Account
ProdDatabaseRole:
  AssumeRolePolicyDocument:
    Statement:
      - Effect: Allow
        Principal:
          AWS: arn:aws:iam::789012:role/DBARole  # Role from dev account
        Action: sts:AssumeRole
        Condition:
          StringEquals:
            sts:ExternalId: unique-id-123
```

---

## Best Practices

### 1. **Principle of Least Privilege**

```yaml
# ❌ BAD - Too permissive
Policy:
  Statement:
    - Effect: Allow
      Action: s3:*
      Resource: '*'

# ✅ GOOD - Specific permissions
Policy:
  Statement:
    - Effect: Allow
      Action:
        - s3:GetObject
        - s3:PutObject
      Resource: arn:aws:s3:::my-bucket/app-data/*
```

### 2. **Use Roles for Services, Not Access Keys**

```yaml
# ❌ BAD - Access keys exposed
EC2UserData:
  Content: |
    export AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE
    export AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

# ✅ GOOD - IAM Role
EC2InstanceProfile:
  Roles:
    - !Ref EC2Role
```

### 3. **Encrypt Secrets**

```yaml
# Don't hardcode in CloudFormation
MasterPassword: !Sub '{{resolve:secretsmanager:MyDatabaseSecret:SecretString:password}}'

# Manage with Secrets Manager
Secret:
  Name: MyDatabaseSecret
  SecretString: !Sub |
    {
      "username": "admin",
      "password": "${RandomPassword}"
    }
```

### 4. **Use IAM Policies with Conditions**

```yaml
Policy:
  Statement:
    # Allow S3 access only from VPC endpoint
    - Effect: Allow
      Action: s3:GetObject
      Resource: arn:aws:s3:::my-bucket/*
      Condition:
        StringEquals:
          aws:PrincipalOrgID: o-abc123
    
    # Allow RDS access only during business hours
    - Effect: Allow
      Action: rds-db:connect
      Resource: arn:aws:rds:*:*:db/*
      Condition:
        DateGreaterThan:
          aws:CurrentTime: 2024-01-01T08:00:00Z
        DateLessThan:
          aws:CurrentTime: 2024-12-31T17:00:00Z
```

### 5. **Enable MFA for Admin Users**

```yaml
# Enforce MFA for root user
# Enforce MFA for admin role users
```

### 6. **Audit IAM with CloudTrail**

```yaml
CloudTrail:
  IsLogging: true
  S3BucketName: !Ref CloudTrailBucket
  IncludeGlobalServiceEvents: true
  IsMultiRegionTrail: true
  Events:
    - EventName: AssumeRole
    - EventName: CreateAccessKey
    - EventName: DeleteUser
```

---

## Security Group Rules - Decision Tree

```
Request arrives at EC2 instance
    ↓
Check Security Group Ingress rules (in order)
    ├─ Match found?
    │   ├─ Effect: Allow → ALLOW traffic
    │   └─ Effect: Deny → DENY traffic
    └─ No match? → DENY traffic (default)

Response goes back
    ↓
Check Security Group Egress rules
    ├─ Match found?
    │   ├─ Effect: Allow → ALLOW traffic
    │   └─ Effect: Deny → DENY traffic
    └─ No match? → ALLOW traffic (default for stateful)
```

---

## Common Security Mistakes

| Mistake | Fix |
|---------|-----|
| 0.0.0.0/0 (all IPs) for SSH | Restrict to bastion/VPN IP |
| Storing secrets in code | Use Secrets Manager/Parameter Store |
| Too many permissions | Apply least privilege |
| Root account for daily use | Use IAM users/roles |
| No encryption in transit | Enable HTTPS/TLS |
| No encryption at rest | Enable S3/RDS encryption |
| No backup strategy | Enable automated backups |
| No MFA on root | Enable MFA for root |

---

**Next**: Review [MONITORING_AND_LOGGING.md](../MONITORING_AND_LOGGING.md) for security monitoring!
