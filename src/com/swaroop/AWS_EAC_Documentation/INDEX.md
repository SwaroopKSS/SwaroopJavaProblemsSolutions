# AWS EAC Documentation - Quick Reference Index

## 📚 Documentation Structure

```
AWS_EAC_Documentation/
│
├── README.md ........................... START HERE - Overview & navigation
├── AWS_COMPONENTS_DEFINITIONS.md ....... Detailed component definitions
├── EAC_CONCEPTS.md .................... Core EAC principles & tools
├── ROUTE53_ALB_CONNECTIVITY.md ........ DNS to ALB flow & routing
├── VPC_NETWORKING_GUIDE.md ............ VPC design & networking
├── SECURITY_AND_IAM.md ............... Security groups & IAM roles
├── WAF_MULTI_ACCOUNT_GUIDE.md ........ Web Application Firewall setup
├── MONITORING_AND_LOGGING.md ......... CloudWatch & logging
│
├── diagrams/
│   ├── BASIC_ARCHITECTURE.md ......... 3-tier application diagram
│   ├── ADVANCED_ARCHITECTURE.md ...... (coming soon)
│   ├── COMPONENT_CONNECTIVITY.md ..... Detailed component relationships
│   └── DATA_FLOW_DIAGRAM.md ......... (coming soon)
│
└── examples/
    ├── CLOUDFORMATION_EXAMPLES.md .... Complete CloudFormation templates
    ├── TERRAFORM_EXAMPLES.md ........ (coming soon)
    ├── CDK_EXAMPLES.md ............. (coming soon)
    ├── EAC_CONFIG_FILES.md ......... (coming soon)
    ├── VPC_SETUP_EXAMPLE.yaml
    ├── ALB_EC2_EXAMPLE.yaml
    ├── RDS_EXAMPLE.yaml
    └── IAM_EXAMPLE.yaml
```

---

## 🎯 Learning Paths

### Path 1: Complete Beginner (Never used AWS)

**Time: 2-3 days**

```
Day 1: Concepts
  1. README.md (30 min)
     → Understand what EAC is
  
  2. EAC_CONCEPTS.md (1 hour)
     → Learn principles and tools
  
  3. AWS_COMPONENTS_DEFINITIONS.md (2 hours)
     → Know what each AWS service does

Day 2: Architecture
  1. VPC_NETWORKING_GUIDE.md (1.5 hours)
     → Understand VPC design
  
  2. BASIC_ARCHITECTURE.md (1 hour)
     → See complete diagram
  
  3. ROUTE53_ALB_CONNECTIVITY.md (1.5 hours)
     → Understand traffic flow

Day 3: Implementation & Security
  1. SECURITY_AND_IAM.md (1 hour)
     → Learn security best practices
  
  2. WAF_MULTI_ACCOUNT_GUIDE.md (1 hour)
     → Understand application firewall
  
  3. MONITORING_AND_LOGGING.md (1 hour)
     → Understand monitoring
  
  4. CLOUDFORMATION_EXAMPLES.md (2 hours)
     → Study working templates
     → Deploy first stack
```

### Path 2: AWS Developer (Familiar with basics)

**Time: 1 day**

```
Morning:
  1. Skip README & EAC_CONCEPTS (already know)
  2. AWS_COMPONENTS_DEFINITIONS.md (1 hour)
     → Refresh component details
  3. ROUTE53_ALB_CONNECTIVITY.md (1.5 hours)
     → Deepen routing knowledge

Afternoon:
  1. CLOUDFORMATION_EXAMPLES.md (2 hours)
     → Study working templates
  2. COMPONENT_CONNECTIVITY.md (1 hour)
     → See how everything connects
```

### Path 3: DevOps/SRE (Infrastructure focus)

**Time: 4-6 hours**

```
Session 1:
  1. EAC_CONCEPTS.md (1 hour)
  2. SECURITY_AND_IAM.md (1.5 hours)
  3. WAF_MULTI_ACCOUNT_GUIDE.md (1 hour)
     → Critical for multi-team deployments

Session 2:
  1. MONITORING_AND_LOGGING.md (1.5 hours)
  2. COMPONENT_CONNECTIVITY.md (1 hour)
  3. CLOUDFORMATION_EXAMPLES.md (1.5 hours)
```

---

## 🔍 Topic Quick Finder

### Networking
- [VPC Basics](./VPC_NETWORKING_GUIDE.md#what-is-a-vpc)
- [Subnets & Routing](./VPC_NETWORKING_GUIDE.md#core-vpc-components)
- [NAT Gateway](./VPC_NETWORKING_GUIDE.md#5-nat-gateway)
- [Internet Gateway](./VPC_NETWORKING_GUIDE.md#4-internet-gateway)

### DNS & Load Balancing
- [Route 53 Basics](./AWS_COMPONENTS_DEFINITIONS.md#2-route-53)
- [ALB Features](./AWS_COMPONENTS_DEFINITIONS.md#3-application-load-balancer-alb)
- [Route 53 to ALB Flow](./ROUTE53_ALB_CONNECTIVITY.md#basic-connectivity-flow)
- [Traffic Routing Patterns](./ROUTE53_ALB_CONNECTIVITY.md#traffic-routing-patterns)
- [Health Checks](./ROUTE53_ALB_CONNECTIVITY.md#health-checking)

### Compute
- [EC2 Instances](./AWS_COMPONENTS_DEFINITIONS.md#1-ec2-elastic-compute-cloud)
- [Auto Scaling Groups](./AWS_COMPONENTS_DEFINITIONS.md#2-auto-scaling-group-asg)
- [Instance Types](./AWS_COMPONENTS_DEFINITIONS.md#instance-types)

### Database
- [RDS Setup](./AWS_COMPONENTS_DEFINITIONS.md#1-rds-relational-database-service)
- [Multi-AZ Configuration](./BASIC_ARCHITECTURE.md#high-availability-features)

### Security
- [Security Groups](./SECURITY_AND_IAM.md#security-groups-network-level)
- [IAM Roles](./SECURITY_AND_IAM.md#iam-roles-vs-users)
- [Policies](./SECURITY_AND_IAM.md#common-iam-policies)
- [Network ACLs](./SECURITY_AND_IAM.md#network-acls-nacls---subnet-level)

### Web Application Security
- [WAF Introduction](./WAF_MULTI_ACCOUNT_GUIDE.md#introduction-to-aws-waf)
- [Multi-Account WAF Setup](./WAF_MULTI_ACCOUNT_GUIDE.md#multi-account-waf-architecture)
- [Application Onboarding](./WAF_MULTI_ACCOUNT_GUIDE.md#application-onboarding-process)
- [WAF Rules & Rule Groups](./WAF_MULTI_ACCOUNT_GUIDE.md#waf-rules--rule-groups)
- [Cross-Account Implementation](./WAF_MULTI_ACCOUNT_GUIDE.md#multi-account-implementation)
- [WAF Monitoring](./WAF_MULTI_ACCOUNT_GUIDE.md#monitoring--logging)

### Storage
- [S3 Basics](./AWS_COMPONENTS_DEFINITIONS.md#1-s3-simple-storage-service)
- [Lifecycle Policies](./AWS_COMPONENTS_DEFINITIONS.md#in-eac-context-4)

### Monitoring
- [CloudWatch Metrics](./MONITORING_AND_LOGGING.md#metrics)
- [CloudWatch Logs](./MONITORING_AND_LOGGING.md#logs)
- [Alarms](./MONITORING_AND_LOGGING.md#alarms)
- [Dashboards](./MONITORING_AND_LOGGING.md#dashboards)

### IaC & Deployment
- [CloudFormation Templates](./examples/CLOUDFORMATION_EXAMPLES.md)
- [EAC Concepts](./EAC_CONCEPTS.md)
- [Best Practices](./EAC_CONCEPTS.md#eac-best-practices)

### Architecture
- [Basic 3-Tier Architecture](./diagrams/BASIC_ARCHITECTURE.md)
- [Component Relationships](./diagrams/COMPONENT_CONNECTIVITY.md)
- [Connectivity Map](./diagrams/COMPONENT_CONNECTIVITY.md)

---

## 💡 Common Questions Answered

### "What is EAC?"
See: [EAC_CONCEPTS.md - Introduction](./EAC_CONCEPTS.md#what-is-environment-as-code-eac)

### "How does traffic reach my application?"
See: [ROUTE53_ALB_CONNECTIVITY.md - Basic Connectivity Flow](./ROUTE53_ALB_CONNECTIVITY.md#basic-connectivity-flow)

### "How do I set up a VPC?"
See: [examples/CLOUDFORMATION_EXAMPLES.md - Example 1](./examples/CLOUDFORMATION_EXAMPLES.md#example-1-simple-vpc-setup)

### "How does auto-scaling work?"
See: [BASIC_ARCHITECTURE.md - Scaling Behavior](./diagrams/BASIC_ARCHITECTURE.md#scaling-behavior)

### "How do I connect EC2 to RDS securely?"
See: [SECURITY_AND_IAM.md - Security Groups](./SECURITY_AND_IAM.md#example-three-tier-application)

### "How do I monitor my infrastructure?"
See: [MONITORING_AND_LOGGING.md - Monitoring Strategy](./MONITORING_AND_LOGGING.md#monitoring-strategy)

### "What's the difference between VPC and Subnets?"
See: [VPC_NETWORKING_GUIDE.md - VPC Architecture](./VPC_NETWORKING_GUIDE.md#vpc-architecture-overview)

### "How does Route 53 work?"
See: [ROUTE53_ALB_CONNECTIVITY.md - DNS Resolution Process](./ROUTE53_ALB_CONNECTIVITY.md#dns-resolution-process)

### "What are security groups vs NACLs vs WAF?"
See: [SECURITY_AND_IAM.md](./SECURITY_AND_IAM.md#security-groups-network-level), [VPC_NETWORKING_GUIDE.md](./VPC_NETWORKING_GUIDE.md#7-network-acls-nacls), and [WAF_MULTI_ACCOUNT_GUIDE.md - Layer Comparison](./WAF_MULTI_ACCOUNT_GUIDE.md#waf-vs-other-security-layers)

### "How do I set up WAF for multiple team accounts?"
See: [WAF_MULTI_ACCOUNT_GUIDE.md - Multi-Account Architecture](./WAF_MULTI_ACCOUNT_GUIDE.md#multi-account-waf-architecture)

### "How do I onboard a new application with WAF?"
See: [WAF_MULTI_ACCOUNT_GUIDE.md - Application Onboarding](./WAF_MULTI_ACCOUNT_GUIDE.md#application-onboarding-process)

---

## 📊 Component Decision Matrix

### Which service to use?

**For DNS?**
- Route 53 (AWS managed, integrated)

**For Load Balancing?**
- ALB (Layer 7, for web apps)
- NLB (Layer 4, for ultra-high performance)
- CLB (Legacy, avoid)

**For Compute?**
- EC2 (full control, managed by you)
- Lambda (serverless, no servers)
- Fargate (containerized, managed)

**For Database?**
- RDS (SQL databases)
- DynamoDB (NoSQL, serverless)
- Aurora (high-performance SQL)

**For Caching?**
- ElastiCache (Redis/Memcached)
- DAX (DynamoDB caching)

**For Storage?**
- S3 (object storage)
- EBS (block storage for EC2)
- EFS (shared file system)

**For Monitoring?**
- CloudWatch (all metrics/logs)
- X-Ray (distributed tracing)

---

## 🚀 Deployment Checklist

Before deploying to production:

### Architecture Review
- [ ] VPC design documented
- [ ] Security groups whitelist-based (not 0.0.0.0/0 for SSH)
- [ ] Multi-AZ for HA
- [ ] Database backup strategy defined

### Security
- [ ] IAM roles use least privilege
- [ ] WAF attached to ALBs/CloudFront
- [ ] WAF rules in COUNT mode tested for 24-48 hours before BLOCK
- [ ] Secrets stored in Secrets Manager
- [ ] Encryption at rest enabled
- [ ] Encryption in transit enabled (HTTPS)
- [ ] CloudTrail logging enabled
- [ ] VPC Flow Logs enabled
- [ ] WAF logging to CloudWatch and S3 configured

### Monitoring
- [ ] CloudWatch alarms configured
- [ ] Log retention policy set
- [ ] Dashboard created
- [ ] Runbook for common issues

### Testing
- [ ] Manual testing in staging
- [ ] Failover testing
- [ ] Load testing
- [ ] Security testing

### Documentation
- [ ] Architecture documented
- [ ] Runbook created
- [ ] Deployment process documented
- [ ] Rollback procedure documented

---

## 📚 External Resources

### AWS Official Documentation
- [AWS Documentation](https://docs.aws.amazon.com)
- [CloudFormation User Guide](https://docs.aws.amazon.com/cloudformation/)
- [EC2 User Guide](https://docs.aws.amazon.com/ec2/)
- [RDS User Guide](https://docs.aws.amazon.com/rds/)
- [VPC User Guide](https://docs.aws.amazon.com/vpc/)
- [IAM User Guide](https://docs.aws.amazon.com/iam/)

### Learning Resources
- [AWS Well-Architected Framework](https://aws.amazon.com/architecture/well-architected/)
- [AWS Architecture Icons](https://aws.amazon.com/architecture/icons/)
- [AWS Whitepapers](https://aws.amazon.com/whitepapers/)

### Tools
- [CloudFormation Designer](https://console.aws.amazon.com/cloudformation/designer/)
- [AWS CLI Documentation](https://docs.aws.amazon.com/cli/)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)

---

## 🔧 Tools & Commands Reference

### AWS CLI

```bash
# Create CloudFormation stack
aws cloudformation create-stack \
  --stack-name my-stack \
  --template-body file://template.yaml

# List stacks
aws cloudformation list-stacks

# Describe stack resources
aws cloudformation describe-stack-resources \
  --stack-name my-stack

# Get stack outputs
aws cloudformation describe-stacks \
  --stack-name my-stack \
  --query 'Stacks[0].Outputs'

# Delete stack
aws cloudformation delete-stack --stack-name my-stack
```

### Terraform

```bash
# Initialize Terraform
terraform init

# Plan changes
terraform plan

# Apply changes
terraform apply

# Destroy resources
terraform destroy

# Validate syntax
terraform validate
```

---

## 📋 Glossary

| Term | Definition |
|------|-----------|
| **VPC** | Virtual Private Cloud - isolated network |
| **Subnet** | Division of VPC, tied to an AZ |
| **CIDR** | Classless Inter-Domain Routing - IP notation |
| **ALB** | Application Load Balancer - Layer 7 LB |
| **ASG** | Auto Scaling Group - auto-scales EC2 |
| **RDS** | Relational Database Service - managed DB |
| **IAM** | Identity & Access Management - auth/authz |
| **SG** | Security Group - instance-level firewall |
| **NACL** | Network ACL - subnet-level firewall |
| **NAT** | Network Address Translation - proxy |
| **IGW** | Internet Gateway - VPC-to-internet bridge |
| **EIP** | Elastic IP - static public IP |
| **CloudWatch** | Monitoring service - metrics/logs |
| **EAC** | Environment as Code - IaC practice |

---

## 🎓 Next Steps

1. **Choose your path** based on your experience level
2. **Follow the learning path** sequentially
3. **Study the diagrams** to visualize architecture
4. **Review the examples** to understand templates
5. **Deploy a test stack** to hands-on learning
6. **Join AWS community** for support

---

**Happy learning! 🚀**

For questions or updates, refer to the [AWS Documentation](https://docs.aws.amazon.com).

**Last Updated**: May 2026
**Version**: 1.0
