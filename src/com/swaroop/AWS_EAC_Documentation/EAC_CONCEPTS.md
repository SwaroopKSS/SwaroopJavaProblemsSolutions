# EAC (Environment as Code) Core Concepts

## What is Environment as Code (EAC)?

**Environment as Code** is the practice of defining, provisioning, and managing complete AWS infrastructure using declarative code and automation instead of manual configuration through the AWS Console.

---

## Key Principles

### 1. **Infrastructure as Code (IaC)**

Your entire infrastructure is defined in code files (YAML, JSON, or programming languages).

**Before EAC (Manual)**:
```
1. Log into AWS Console
2. Click through 20+ steps to create VPC
3. Create subnets manually
4. Set up security groups
5. Configure route tables
6. Launch EC2 instances
7. Create ALB
8. Configure target groups
9. Wait for DNS propagation
(Process: 1-2 hours, prone to errors)
```

**With EAC**:
```yaml
# infrastructure.yaml - One file, reproducible
VPC:
  CIDR: 10.0.0.0/16
  Subnets:
    - Name: public-subnet-1
      CIDR: 10.0.1.0/24
    - Name: private-subnet-1
      CIDR: 10.0.11.0/24

SecurityGroups:
  - Name: alb-sg
    Ingress:
      - Protocol: tcp
        Port: 443

ALB:
  Name: my-alb
  Listeners:
    - Port: 443
      TargetGroup: app-servers
```

**Deployment**: `terraform apply` or `aws cloudformation deploy` (5-10 minutes)

---

### 2. **Idempotency**

Running the same code multiple times produces the same result.

**Example**:
```yaml
# First run: Creates VPC with 10.0.0.0/16
# Second run: Checks if VPC exists, no change needed
# Third run: Checks if VPC exists, no change needed

VPC:
  Name: my-vpc
  CIDR: 10.0.0.0/16
  Tags:
    Name: production-vpc
```

---

### 3. **Version Control**

Infrastructure changes are tracked like code.

```
git log
commit abc123: Add NAT gateway for private subnet outbound
commit def456: Update security group rules for HTTPS
commit ghi789: Scale ALB to 3 AZs for HA
commit jkl012: Reduce EC2 instance size to save costs
```

**Benefits**:
- Know what changed and when
- Rollback to previous state easily
- Code reviews before deployment
- Audit trail for compliance

---

### 4. **Repeatability**

Deploy identical environments instantly.

**Scenario**:
```
Development Environment:
  terraform apply -var-file=dev.tfvars

Staging Environment:
  terraform apply -var-file=staging.tfvars

Production Environment:
  terraform apply -var-file=prod.tfvars

(Same code, different variables = consistent behavior)
```

---

## EAC Tools & Technologies

### 1. **CloudFormation (AWS Native)**

YAML/JSON-based AWS infrastructure provisioning.

**Pros**:
- Native AWS tool
- No additional costs
- Deep service integration
- Supports all AWS services

**Cons**:
- AWS-only (not multi-cloud)
- Verbose syntax
- Slower to update

**Example**:
```yaml
Resources:
  MyVPC:
    Type: AWS::EC2::VPC
    Properties:
      CidrBlock: 10.0.0.0/16
  
  MySubnet:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref MyVPC
      CidrBlock: 10.0.1.0/24
```

---

### 2. **Terraform (HashiCorp)**

Multi-cloud IaC tool using HCL (HashiCorp Configuration Language).

**Pros**:
- Multi-cloud (AWS, Azure, GCP, etc.)
- Declarative and easy to read
- Powerful state management
- Large community

**Cons**:
- Extra tool to learn
- State file management critical
- Potential for drift

**Example**:
```hcl
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  
  tags = {
    Name = "production-vpc"
  }
}

resource "aws_subnet" "public" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-east-1a"
}
```

---

### 3. **AWS CDK (Cloud Development Kit)**

Code infrastructure using Python, TypeScript, Java, C#, Go.

**Pros**:
- Use familiar programming languages
- Higher-level abstractions
- Powerful constructs and patterns
- Reduced code verbosity

**Cons**:
- AWS-only
- Requires programming knowledge
- Requires additional build step

**Example** (TypeScript):
```typescript
import * as cdk from 'aws-cdk-lib';
import * as ec2 from 'aws-cdk-lib/aws-ec2';

export class MyStack extends cdk.Stack {
  constructor(scope: cdk.App, id: string) {
    super(scope, id);
    
    const vpc = new ec2.Vpc(this, 'VPC', {
      cidr: '10.0.0.0/16'
    });
    
    const subnet = new ec2.PublicSubnet(this, 'PublicSubnet', {
      vpcId: vpc.vpcId,
      cidrBlock: '10.0.1.0/24',
      availabilityZone: 'us-east-1a'
    });
  }
}
```

---

### 4. **SAM (Serverless Application Model)**

AWS extension for CloudFormation focused on serverless.

**Example**:
```yaml
AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31

Resources:
  MyLambdaFunction:
    Type: AWS::Serverless::Function
    Properties:
      Handler: index.handler
      Runtime: python3.9
      CodeUri: ./src
      Events:
        ApiEvent:
          Type: Api
          Properties:
            Path: /api/{proxy+}
            Method: ANY
```

---

## EAC Architecture Layers

### Layer 1: Base Infrastructure (Foundation)

```yaml
1. VPC Setup
   - VPC with CIDR block
   - Public subnets
   - Private subnets
   - Internet Gateway
   - NAT Gateway
   - Route tables

2. Security Foundation
   - Security groups
   - Network ACLs
   - IAM roles and policies
```

**Why**: Everything else depends on this foundation.

---

### Layer 2: Application Infrastructure

```yaml
1. Load Balancer
   - ALB creation
   - Listeners (HTTP/HTTPS)
   - Target groups

2. Compute Resources
   - EC2 instances
   - Auto Scaling Groups
   - Launch templates

3. Database
   - RDS instance
   - RDS subnet groups
   - Database credentials
```

**Why**: Your application needs these to run.

---

### Layer 3: Supporting Services

```yaml
1. DNS Management
   - Route 53 hosted zones
   - DNS records
   - Health checks

2. Monitoring
   - CloudWatch log groups
   - Alarms
   - Dashboards

3. Storage
   - S3 buckets
   - Backup policies
   - Access control
```

**Why**: Monitoring, logging, and storage support operations.

---

### Layer 4: Advanced Features

```yaml
1. High Availability
   - Multi-AZ deployment
   - Cross-region replication
   - Failover mechanisms

2. Performance
   - Caching (CloudFront, ElastiCache)
   - Content delivery
   - Database read replicas

3. Cost Optimization
   - Spot instances
   - Reserved capacity
   - Lifecycle policies
```

**Why**: Optimize for production requirements.

---

## EAC Workflow

### Development Phase

```
1. Write infrastructure code
   └─ cloudformation.yaml or main.tf

2. Local validation
   └─ cfn-lint or terraform validate

3. Create stack/plan
   └─ aws cloudformation create-stack or terraform plan

4. Review changes
   └─ Check what will be created/modified

5. Deploy
   └─ aws cloudformation deploy or terraform apply
```

---

### Management Phase

```
1. Monitor resources
   └─ CloudWatch metrics and logs

2. Scale as needed
   └─ Modify ASG settings in code
   └─ Redeploy

3. Apply patches
   └─ Update AMI or code
   └─ Rolling deployment

4. Track changes
   └─ Git history shows who changed what
```

---

### Disaster Recovery Phase

```
1. Infrastructure fails
   └─ Entire stack can be recreated

2. Quick recovery
   └─ Run terraform apply or cloudformation deploy
   └─ Infrastructure restored in minutes

3. No manual steps
   └─ No need to remember which settings were used
```

---

## Parameters and Variables

### Making EAC Flexible

Without parameters (Bad):
```yaml
VPC:
  CIDR: 10.0.0.0/16  # Hardcoded!

# Can't reuse for different environments
```

With parameters (Good):
```yaml
Parameters:
  Environment:
    Type: String
    AllowedValues: [dev, staging, prod]
  
  VpcCIDR:
    Type: String
    Default: 10.0.0.0/16

VPC:
  CIDR: !Ref VpcCIDR  # Uses parameter
  Tags:
    Environment: !Ref Environment
```

---

## Secrets Management in EAC

### Don't put secrets in code!

```yaml
# ❌ WRONG - Don't do this!
RDSInstance:
  MasterUsername: admin
  MasterPassword: MySecretPassword123!  # EXPOSED!
```

**Correct approach**:
```yaml
# ✅ CORRECT - Use AWS Secrets Manager
RDSInstance:
  MasterUsername: admin
  MasterPassword: !Sub '{{resolve:secretsmanager:MyDatabaseSecret:SecretString:password}}'

# Or with Terraform:
RDSInstance:
  master_password = var.db_password  # Passed via -var or .tfvars

# .tfvars file (add to .gitignore):
db_password = "MySecretPassword123!"
```

---

## EAC Best Practices

### 1. **Modularity**

```
infrastructure/
├── modules/
│   ├── networking/
│   │   └─ main.tf, variables.tf, outputs.tf
│   ├── compute/
│   │   └─ main.tf, variables.tf, outputs.tf
│   └── database/
│       └─ main.tf, variables.tf, outputs.tf
└── main.tf  # Calls modules
```

---

### 2. **Environment Separation**

```
environments/
├── dev/
│   ├── terraform.tfvars  # dev-specific values
│   └── backend.tf  # dev state file
├── staging/
│   ├── terraform.tfvars
│   └── backend.tf
└── prod/
    ├── terraform.tfvars
    └── backend.tf
```

---

### 3. **Documentation**

```
Each module has:
├── README.md  (What does this module do?)
├── variables.tf  (Inputs)
├── outputs.tf  (Outputs)
└── main.tf  (Implementation)
```

---

### 4. **Testing**

```
terraform validate  # Syntax check
terraform plan     # Preview changes
terraform fmt      # Format code
terraform taint    # Mark for recreation
```

---

## Common EAC Patterns

### Pattern 1: Blue-Green Deployment

```yaml
# Primary (Blue)
Route53Record:
  Name: api.example.com
  Weight: 100
  AliasTarget: blue-alb

# Secondary (Green) - ready to switch
Route53Record:
  Name: api.example.com
  Weight: 0
  AliasTarget: green-alb

# To deploy: Switch weights 100→0 and 0→100
```

---

### Pattern 2: Canary Deployment

```yaml
# 95% traffic to stable version
Route53Record_Stable:
  Weight: 95
  AliasTarget: stable-alb

# 5% traffic to new version for testing
Route53Record_Canary:
  Weight: 5
  AliasTarget: canary-alb

# If canary is stable, switch weights
```

---

### Pattern 3: Multi-Region Failover

```yaml
# Primary region
Route53Record_Primary:
  Failover: PRIMARY
  AliasTarget: us-east-1-alb
  HealthCheck: health-check-id

# Backup region
Route53Record_Secondary:
  Failover: SECONDARY
  AliasTarget: eu-west-1-alb

# If primary fails, automatic failover
```

---

## EAC Benefits Summary

| Benefit | Impact |
|---------|--------|
| **Consistency** | Same environment everywhere |
| **Speed** | Deploy complete infrastructure in minutes |
| **Reproducibility** | Rebuild instantly if needed |
| **Auditability** | Track all changes in git |
| **Automation** | CI/CD pipelines can deploy infrastructure |
| **Cost** | No manual errors = cost savings |
| **Recovery** | Disaster recovery in minutes |
| **Scaling** | Easy to scale horizontally |

---

## Next Steps

1. Choose your tool (CloudFormation, Terraform, or CDK)
2. Start with base infrastructure (VPC)
3. Add application layer (EC2, ALB)
4. Add supporting services (RDS, S3)
5. Implement monitoring and logging
6. Create deployment pipelines
7. Document everything

---

**Ready to dive deeper?** Check out the examples in the `examples/` folder!
