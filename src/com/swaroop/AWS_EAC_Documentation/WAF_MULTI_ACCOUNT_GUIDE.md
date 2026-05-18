# AWS WAF (Web Application Firewall) - Multi-Account Guide

## Table of Contents

1. [Introduction to AWS WAF](#introduction-to-aws-waf)
2. [WAF vs Other Security Layers](#waf-vs-other-security-layers)
3. [Multi-Account WAF Architecture](#multi-account-waf-architecture)
4. [WAF Components & Concepts](#waf-components--concepts)
5. [Application Onboarding Process](#application-onboarding-process)
6. [WAF Rules & Rule Groups](#waf-rules--rule-groups)
7. [Multi-Account Implementation](#multi-account-implementation)
8. [Monitoring & Logging](#monitoring--logging)
9. [Best Practices](#best-practices)

---

## Introduction to AWS WAF

### What is AWS WAF?

**AWS WAF (Web Application Firewall)** = Application-level firewall that protects web applications from common web exploits.

```
Layer 7 (Application Layer) Protection:

                        Internet
                          ↓
                    ┌────────────┐
                    │   AWS WAF  │ ← Protects against:
                    │            │   • SQL Injection
                    │ Web ACL    │   • XSS (Cross-Site Scripting)
                    │ Rules      │   • DDoS
                    │ Rate Limit │   • Bot attacks
                    │ IP Blocks  │   • Credential stuffing
                    └────────────┘
                          ↓
                    ┌────────────┐
                    │   ALB/     │
                    │ CloudFront │
                    └────────────┘
                          ↓
                    ┌────────────┐
                    │   Your     │
                    │   App      │
                    └────────────┘
```

### Key Characteristics

- **Layer 7 Protection**: Understands HTTP/HTTPS
- **Real-time Rules**: Inspect request content
- **Managed Rules**: AWS-provided rule groups (OWASP, IP reputation)
- **Custom Rules**: Create your own rules
- **Rate Limiting**: Throttle requests from single IP
- **Geographic Blocking**: Block requests from specific countries
- **Bot Control**: Identify and block malicious bots
- **DDoS Protection**: Integrated with AWS Shield

---

## WAF vs Other Security Layers

### Security Layer Comparison

```
┌─────────────────────────────────────────────────────────────┐
│                  Internet Users (0.0.0.0/0)                 │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
        ┌───────────────────────────────────────┐
        │   Layer 7 (Application) - AWS WAF      │
        │   ✓ SQL Injection                     │
        │   ✓ XSS Attacks                       │
        │   ✓ Bot Detection                     │
        │   ✓ Rate Limiting                     │
        │   ✓ Custom Rules                      │
        └───────────────────────────┬───────────┘
                                    ↓
        ┌───────────────────────────────────────┐
        │   Layer 4 (Transport) - ALB/NLB       │
        │   ✓ Connection limits                 │
        │   ✓ Health checks                     │
        │   ✓ Traffic distribution              │
        └───────────────────────────┬───────────┘
                                    ↓
        ┌───────────────────────────────────────┐
        │   Layer 3 (Network) - Security Groups │
        │   ✓ Stateful firewall                 │
        │   ✓ Protocol/Port control             │
        │   ✓ IP-based filtering                │
        └───────────────────────────┬───────────┘
                                    ↓
        ┌───────────────────────────────────────┐
        │   Layer 3 (Network) - NACLs           │
        │   ✓ Stateless firewall                │
        │   ✓ Subnet-level control              │
        │   ✓ Explicit deny rules                │
        └───────────────────────────┬───────────┘
                                    ↓
                    Your Application (Private Subnet)
```

### When to Use What?

| Layer | Component | Use Case |
|-------|-----------|----------|
| **Layer 7** | **AWS WAF** | Protect against application attacks (SQL injection, XSS, bots) |
| **Layer 4** | **ALB/NLB** | Load balancing, SSL/TLS termination, health checks |
| **Layer 3** | **Security Groups** | Instance-level firewall, source/port control |
| **Layer 3** | **NACLs** | Subnet-level stateless firewall, explicit deny |
| **Layer 3** | **AWS Shield** | DDoS protection (Standard = free, Advanced = paid) |

### Defense in Depth Example

```
Attacker sends malicious request
        ↓
AWS WAF Rule Block (L7)
✗ SQL Injection detected → DROP
    ↓ (if bypasses)
ALB Health Check (L4)
✓ Valid HTTP → Forward
    ↓
Security Group Check (L3)
✓ Port 80 allowed → Forward
    ↓
NACL Check (L3)
✓ Stateless rule → Forward
    ↓
Application receives request
✓ Input validation catches (Application Layer)
```

---

## Multi-Account WAF Architecture

### Typical Organization Structure

```
┌─────────────────────────────────────────────────────────────────┐
│                  AWS Organization / Root Account                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────┐  ┌──────────────────┐  ┌──────────────┐   │
│  │ WAF Account      │  │ Shared Services  │  │ Audit        │   │
│  │ (Centralized)    │  │ Account          │  │ Account      │   │
│  │                  │  │                  │  │              │   │
│  │ • Web ACLs       │  │ • Logging        │  │ • CloudTrail │   │
│  │ • Rule Groups    │  │ • Monitoring     │  │ • Compliance │   │
│  │ • IP Lists       │  │ • DNS            │  │              │   │
│  │ • Policies       │  │                  │  │              │   │
│  └────────┬─────────┘  └──────────────────┘  └──────────────┘   │
│           │                                                       │
│  ┌────────┴────────────────────────────────┐                     │
│  ↓                                          ↓                     │
│  ┌──────────────────────┐  ┌──────────────────────┐             │
│  │  Team A Account      │  │  Team B Account      │ ...         │
│  │  (Development)       │  │  (Production)        │             │
│  │                      │  │                      │             │
│  │ Resources:           │  │ Resources:           │             │
│  │ • ALB                │  │ • ALB                │             │
│  │ • EC2 Instances      │  │ • EC2 Instances      │             │
│  │ • RDS Database       │  │ • RDS Database       │             │
│  │ • VPC                │  │ • VPC                │             │
│  │                      │  │                      │             │
│  │ WAF Attached:        │  │ WAF Attached:        │             │
│  │ • Dev Web ACL        │  │ • Prod Web ACL       │             │
│  │   (from WAF Account) │  │   (from WAF Account) │             │
│  └──────────────────────┘  └──────────────────────┘             │
│           ↓                              ↓                       │
│     WAF Account                    WAF Account                   │
│   (Cross-Account)                (Cross-Account)                 │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘

                              ↓

                    ┌──────────────────┐
                    │   External User  │
                    │ (Internet Access)│
                    └────────┬─────────┘
                             ↓
                     ┌───────────────┐
                     │ Route 53 DNS  │
                     │  (Resolution) │
                     └───────┬───────┘
                             ↓
            ┌────────────────────────────────┐
            │  AWS WAF Web ACL (Centralized) │
            │  • Rules for all teams         │
            │  • Cross-account association  │
            │  • Centralized management      │
            └────────────┬───────────────────┘
                         ↓
            ┌────────────────────────────────┐
            │  ALB (in Team Accounts)        │
            │  Team A ALB                    │
            │  Team B ALB                    │
            └────────────┬───────────────────┘
                         ↓
            ┌────────────────────────────────┐
            │  Application (Backend)         │
            │  EC2, RDS, etc.                │
            └────────────────────────────────┘
```

### Advantages of Centralized WAF Account

1. **Single Point of Management** - All rules in one place
2. **Consistency** - Same rules across all teams
3. **Cost Optimization** - Shared WAF infrastructure
4. **Compliance** - Centralized policy enforcement
5. **Easier Auditing** - All logs in one location
6. **Rapid Response** - Update rules for all apps instantly
7. **Knowledge Centralization** - WAF team maintains expertise

---

## WAF Components & Concepts

### 1. Web ACL (Access Control List)

**Web ACL** = Collection of rules evaluated in order

```yaml
WebACL:
  Name: dev-web-acl
  Scope: REGIONAL  # or CLOUDFRONT
  DefaultAction: ALLOW  # Default if no rules match
  
  Rules:
    - Priority: 1
      Name: RateLimitRule
      Statement:
        RateBasedStatement:
          Limit: 2000  # 2000 requests per 5 minutes
          AggregateKeyType: IP
      Action: BLOCK
      
    - Priority: 2
      Name: SQLInjectionRule
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesSQLiRuleSet
      Action: BLOCK
      
    - Priority: 3
      Name: XSSRule
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesKnownBadInputsRuleSet
      Action: BLOCK
      
    - Priority: 4
      Name: GeoBlockingRule
      Statement:
        GeoMatchStatement:
          CountryCodes: [CN, RU, KP]  # Block these countries
      Action: BLOCK
      
    - Priority: 5
      Name: IPReputationRule
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesAmazonIpReputationList
      Action: BLOCK
      
    - Priority: 6
      Name: BotControlRule
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesBotControlRuleSet
      Action: BLOCK
      
    - Priority: 7
      Name: CustomBlockRule
      Statement:
        ByteMatchStatement:
          SearchString: "admin"
          FieldToMatch:
            UriPath: {}
          TextTransformation: LOWERCASE
          PositionalArgument: STARTS_WITH
      Action: BLOCK
      
    - Priority: 8
      Name: AllowListRule
      Statement:
        IPSetReferenceStatement:
          IPSetName: TrustedPartners
      Action: ALLOW
      OverrideAction: NONE
```

### 2. Rule Groups

**Rule Group** = Reusable collection of rules

```
AWS Managed Rule Groups:
├─ Core Rule Set (AWSManagedRulesCoreRuleSet)
│  └─ Protects against common vulnerabilities
├─ SQL Injection (AWSManagedRulesSQLiRuleSet)
│  └─ SQL injection attacks
├─ Known Bad Inputs (AWSManagedRulesKnownBadInputsRuleSet)
│  └─ Malformed requests
├─ Amazon IP Reputation (AWSManagedRulesAmazonIpReputationList)
│  └─ Blocked by Amazon's threat intelligence
├─ Bot Control (AWSManagedRulesBotControlRuleSet)
│  └─ Identifies and blocks bots
└─ OWASP Top 10 (AWSManagedRulesOWASPCoreRuleSet)
   └─ Open Web Application Security Project rules

Custom Rule Groups:
├─ Team A Custom Rules
├─ Team B Custom Rules
├─ Geo-blocking Rules
└─ Rate Limiting Rules
```

### 3. IP Sets

**IP Set** = List of IP addresses or CIDR blocks

```yaml
TrustedPartnersIPSet:
  Name: TrustedPartners
  Description: Partner IPs that bypass WAF
  IPAddressVersion: IPV4
  Addresses:
    - 203.0.113.0/24  # Partner 1 CIDR
    - 198.51.100.5/32  # Partner 2 specific IP
    - 192.0.2.0/24     # Partner 3 CIDR
  
  # Used in Web ACL:
  # If request source IP in this set → ALLOW

CustomerBlockListIPSet:
  Name: BlockedCustomers
  Description: IPs to block
  IPAddressVersion: IPV4
  Addresses:
    - 10.0.0.1/32  # Malicious customer IP
    - 10.0.0.2/32  # Another blocked IP
```

### 4. Rule Actions

```
ALLOW
  └─ Request passes through to backend
  
BLOCK
  └─ Request is denied
  
COUNT
  └─ Request counted but not blocked (monitoring mode)
```

### 5. Rule Conditions

```
ByteMatch
  └─ Matches specific strings in request
  └─ Example: Block if URI contains "admin"

GeoMatch
  └─ Matches geographic location
  └─ Example: Block requests from specific countries

IPMatch
  └─ Matches source IP address
  └─ Example: Block IPs in blocklist

RateLimit
  └─ Matches if requests exceed threshold
  └─ Example: Block IP if >2000 requests/5min

HTTPHeader
  └─ Matches specific HTTP header values
  └─ Example: Block if User-Agent is bot

BodyMatch
  └─ Matches request body content
  └─ Example: Block if body contains SQL keywords

URIPath
  └─ Matches URI path
  └─ Example: Block if path contains "../../"
```

---

## Application Onboarding Process

### Step 1: Application Registration Form

**Applications need to provide**:

```
Application Registration Form:
│
├─ Basic Information
│  ├─ Application Name
│  ├─ Application ID
│  ├─ Owner Team/Department
│  ├─ Contact Email
│  └─ Slack Channel
│
├─ Infrastructure Details
│  ├─ AWS Account ID
│  ├─ AWS Region(s)
│  ├─ ALB DNS Name / CloudFront Domain
│  ├─ ALB ARN
│  ├─ Application Port(s)
│  └─ Protocol (HTTP/HTTPS)
│
├─ Traffic Pattern
│  ├─ Expected RPS (Requests Per Second)
│  ├─ Peak RPS
│  ├─ Geographic regions
│  ├─ User types
│  └─ API documentation link
│
├─ Security Requirements
│  ├─ Threat model
│  ├─ Data classification
│  ├─ Compliance requirements
│  ├─ Known vulnerabilities
│  └─ Custom rules needed
│
└─ Integration Preferences
   ├─ Preferred rule sets
   ├─ Rate limit threshold
   ├─ IP whitelist
   ├─ Country restrictions
   └─ Bot protection level
```

### Step 2: WAF Account Configuration

```yaml
# In WAF Account: Create application-specific Web ACL

ApplicationWebACL:
  Name: myapp-prod-web-acl
  Scope: REGIONAL
  DefaultAction: ALLOW
  
  Rules:
    - Priority: 1
      Name: RateLimit
      Statement:
        RateBasedStatement:
          Limit: 2000  # Based on app requirements
          AggregateKeyType: IP
      Action: BLOCK
      
    - Priority: 2
      Name: CoreRules
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesCoreRuleSet
      Action: BLOCK
      
    - Priority: 3
      Name: SQLInjection
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesSQLiRuleSet
      Action: BLOCK
      
    - Priority: 4
      Name: BotControl
      Statement:
        ManagedRuleGroupStatement:
          VendorName: AWS
          Name: AWSManagedRulesBotControlRuleSet
      Action: BLOCK

# Create cross-account permission (in WAF Account)
CrossAccountWAFAccess:
  PolicyName: AllowTeamAWAFAccess
  PolicyDocument:
    Version: '2012-10-17'
    Statement:
      - Effect: Allow
        Principal:
          AWS: arn:aws:iam::TEAM-A-ACCOUNT-ID:root
        Action:
          - wafv2:GetWebACLForResource
          - wafv2:ListResourcesForWebACL
          - wafv2:GetWebACL
        Resource: arn:aws:wafv2:us-east-1:WAF-ACCOUNT-ID:regional/webacl/myapp-prod/*
```

### Step 3: Associate WAF to Team Account Resources

```yaml
# In Team Account: Link WAF to ALB

LinkWAFtoALB:
  # Via CloudFormation or AWS CLI
  
  # CLI Command:
  aws wafv2 associate-web-acl \
    --web-acl-arn arn:aws:wafv2:us-east-1:WAF-ACCOUNT-ID:regional/webacl/myapp-prod-web-acl/abc123 \
    --resource-arn arn:aws:elasticloadbalancing:us-east-1:TEAM-A-ACCOUNT-ID:loadbalancer/app/myapp-alb/1234567890abcdef \
    --region us-east-1

# Or CloudFormation:
ALBResourceAssociation:
  Type: AWS::WAFv2::ResourceAssociation
  Properties:
    WebACLArn: !Sub 'arn:aws:wafv2:${AWS::Region}:${WafAccountId}:regional/webacl/myapp-prod-web-acl/abc123'
    ResourceArn: !GetAtt LoadBalancer.LoadBalancerArn
```

---

## WAF Rules & Rule Groups

### Common Rule Patterns

#### Pattern 1: Rate Limiting

```yaml
RateLimitingRule:
  Name: ApplicationRateLimit
  Priority: 10
  Statement:
    RateBasedStatement:
      Limit: 2000  # Requests per 5 minutes
      AggregateKeyType: IP
      ScopeDownStatement:
        ByteMatchStatement:
          SearchString: "/api/"  # Only limit API calls
          FieldToMatch:
            UriPath: {}
          TextTransformation: LOWERCASE
          PositionalArgument: STARTS_WITH
  Action: BLOCK
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: ApplicationRateLimitMetric
```

#### Pattern 2: SQL Injection Protection

```yaml
SQLInjectionProtectionRule:
  Name: BlockSQLInjection
  Priority: 20
  Statement:
    ManagedRuleGroupStatement:
      VendorName: AWS
      Name: AWSManagedRulesSQLiRuleSet
      ExcludedRules:
        # Exclude specific rules if they cause false positives
        - Name: SQLi_BODY
        - Name: SQLi_QUERYSTRING
  OverrideAction:
    None: {}  # Don't override the managed rule action
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: SQLInjectionMetric
```

#### Pattern 3: Geographic Blocking

```yaml
GeoBlockingRule:
  Name: BlockHighRiskCountries
  Priority: 30
  Statement:
    GeoMatchStatement:
      CountryCodes:
        - CN  # China
        - RU  # Russia
        - KP  # North Korea
        - IR  # Iran
  Action: BLOCK
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: GeoBlockingMetric
```

#### Pattern 4: Bot Protection

```yaml
BotControlRule:
  Name: BlockMaliciousBots
  Priority: 40
  Statement:
    ManagedRuleGroupStatement:
      VendorName: AWS
      Name: AWSManagedRulesBotControlRuleSet
  OverrideAction:
    None: {}
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: BotControlMetric
```

#### Pattern 5: Custom IP Whitelist

```yaml
IPWhitelistRule:
  Name: AllowTrustedPartners
  Priority: 5  # High priority to allow trusted IPs first
  Statement:
    IPSetReferenceStatement:
      IPSetName: TrustedPartnerIPs
  Action: ALLOW
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: IPWhitelistMetric
```

#### Pattern 6: XSS Protection

```yaml
XSSProtectionRule:
  Name: BlockXSSAttacks
  Priority: 25
  Statement:
    ManagedRuleGroupStatement:
      VendorName: AWS
      Name: AWSManagedRulesKnownBadInputsRuleSet
  OverrideAction:
    None: {}
  VisibilityConfig:
    SampledRequestsEnabled: true
    CloudWatchMetricsEnabled: true
    MetricName: XSSMetric
```

---

## Multi-Account Implementation

### Architecture: Cross-Account WAF Association

```
┌─────────────────────────────────────────────────────────────┐
│  WAF Central Account (123456789012)                         │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Web ACL 1: dev-web-acl                            │   │
│  │  ├─ Rate limiting rule                             │   │
│  │  ├─ SQL injection rule                             │   │
│  │  ├─ XSS rule                                       │   │
│  │  └─ Geo-blocking rule                              │   │
│  └─────────────────────────────────────────────────────┘   │
│                         ↓                                    │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Web ACL 2: prod-web-acl                           │   │
│  │  ├─ Rate limiting rule (stricter)                  │   │
│  │  ├─ SQL injection rule                             │   │
│  │  ├─ XSS rule                                       │   │
│  │  ├─ Geo-blocking rule (stricter)                   │   │
│  │  └─ Bot control rule                               │   │
│  └─────────────────────────────────────────────────────┘   │
│                         ↓                                    │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  IP Sets (Shared)                                  │   │
│  │  ├─ TrustedPartners (allow list)                   │   │
│  │  ├─ BlockedCustomers (deny list)                   │   │
│  │  └─ HighRiskIPs (reputation)                       │   │
│  └─────────────────────────────────────────────────────┘   │
│                         ↓                                    │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Logging & Monitoring                              │   │
│  │  ├─ CloudWatch Logs (all WAF activity)             │   │
│  │  ├─ S3 bucket (long-term storage)                  │   │
│  │  └─ CloudWatch Dashboards (metrics)                │   │
│  └─────────────────────────────────────────────────────┘   │
│                         ↓                                    │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  IAM Permissions (Cross-Account Access)            │   │
│  │  ├─ Team A Account: Permissions for dev-web-acl   │   │
│  │  ├─ Team B Account: Permissions for prod-web-acl  │   │
│  │  └─ Team C Account: Permissions for staging-web-acl│  │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                         ↓
        ┌────────────────┼────────────────┐
        ↓                ↓                ↓
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ Team A Acct  │  │ Team B Acct  │  │ Team C Acct  │
│ (Dev)        │  │ (Prod)       │  │ (Staging)    │
├──────────────┤  ├──────────────┤  ├──────────────┤
│              │  │              │  │              │
│ ALB 1        │  │ ALB 2        │  │ ALB 3        │
│ ↓            │  │ ↓            │  │ ↓            │
│ Linked to    │  │ Linked to    │  │ Linked to    │
│ dev-web-acl  │  │ prod-web-acl │  │ staging-    │
│ (from WAF)   │  │ (from WAF)   │  │ web-acl     │
│              │  │              │  │ (from WAF)  │
│ App 1        │  │ App 2        │  │ App 3       │
└──────────────┘  └──────────────┘  └──────────────┘
```

### Step-by-Step Implementation

#### Step 1: Create Web ACL in WAF Account

```yaml
# In WAF Account (123456789012) CloudFormation template

AWSTemplateFormatVersion: '2010-09-09'
Description: 'WAF Web ACLs for multi-account organization'

Parameters:
  EnvironmentType:
    Type: String
    AllowedValues: [dev, staging, prod]
    Default: dev

Resources:
  # IP Set for Trusted Partners
  TrustedPartnersIPSet:
    Type: AWS::WAFv2::IPSet
    Properties:
      Name: TrustedPartners
      Description: IP addresses of trusted partners
      Scope: REGIONAL
      IPAddressVersion: IPV4
      Addresses:
        - 203.0.113.0/24
        - 198.51.100.0/24

  # IP Set for Blocked Customers
  BlockedCustomersIPSet:
    Type: AWS::WAFv2::IPSet
    Properties:
      Name: BlockedCustomers
      Description: IP addresses to block
      Scope: REGIONAL
      IPAddressVersion: IPV4
      Addresses:
        - 10.0.0.1/32

  # Web ACL
  ApplicationWebACL:
    Type: AWS::WAFv2::WebACL
    Properties:
      Name: !Sub '${EnvironmentType}-web-acl'
      Scope: REGIONAL
      DefaultAction:
        Allow: {}
      
      Rules:
        # Priority 1: Allow trusted IPs
        - Name: AllowTrustedPartners
          Priority: 1
          Statement:
            IPSetReferenceStatement:
              Arn: !GetAtt TrustedPartnersIPSet.Arn
          Action:
            Allow: {}
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: AllowTrustedPartners

        # Priority 2: Block specific IPs
        - Name: BlockMaliciousIPs
          Priority: 2
          Statement:
            IPSetReferenceStatement:
              Arn: !GetAtt BlockedCustomersIPSet.Arn
          Action:
            Block:
              CustomResponse:
                ResponseCode: 403
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: BlockMaliciousIPs

        # Priority 3: Rate Limiting
        - Name: RateLimit
          Priority: 3
          Statement:
            RateBasedStatement:
              Limit: 2000
              AggregateKeyType: IP
          Action:
            Block:
              CustomResponse:
                ResponseCode: 429
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: RateLimitMetric

        # Priority 4: SQL Injection
        - Name: BlockSQLInjection
          Priority: 4
          Statement:
            ManagedRuleGroupStatement:
              VendorName: AWS
              Name: AWSManagedRulesSQLiRuleSet
          OverrideAction:
            None: {}
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: SQLInjectionMetric

        # Priority 5: XSS
        - Name: BlockXSS
          Priority: 5
          Statement:
            ManagedRuleGroupStatement:
              VendorName: AWS
              Name: AWSManagedRulesKnownBadInputsRuleSet
          OverrideAction:
            None: {}
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: XSSMetric

        # Priority 6: Geo-blocking (only for prod)
        - Name: GeoBlock
          Priority: 6
          Statement:
            GeoMatchStatement:
              CountryCodes:
                - CN
                - RU
                - KP
          Action:
            Block:
              CustomResponse:
                ResponseCode: 403
          VisibilityConfig:
            SampledRequestsEnabled: true
            CloudWatchMetricsEnabled: true
            MetricName: GeoBlockMetric

      VisibilityConfig:
        SampledRequestsEnabled: true
        CloudWatchMetricsEnabled: true
        MetricName: !Sub '${EnvironmentType}-web-acl-metric'

Outputs:
  WebACLArn:
    Description: ARN of the Web ACL
    Value: !GetAtt ApplicationWebACL.Arn
    Export:
      Name: !Sub '${EnvironmentType}-web-acl-arn'

  WebACLId:
    Description: ID of the Web ACL
    Value: !GetAtt ApplicationWebACL.Id
    Export:
      Name: !Sub '${EnvironmentType}-web-acl-id'
```

#### Step 2: Create IAM Policy in WAF Account

```yaml
# WAF Account: Create IAM policy for cross-account access

CrossAccountWAFPolicy:
  Type: AWS::IAM::ManagedPolicy
  Properties:
    ManagedPolicyName: AllowCrossAccountWAFAccess
    PolicyDocument:
      Version: '2012-10-17'
      Statement:
        - Sid: AllowReadWebACL
          Effect: Allow
          Action:
            - wafv2:GetWebACL
            - wafv2:GetWebACLForResource
            - wafv2:ListResourcesForWebACL
          Resource:
            - !Sub 'arn:aws:wafv2:${AWS::Region}:${AWS::AccountId}:regional/webacl/*'
        
        - Sid: AllowListWebACLs
          Effect: Allow
          Action:
            - wafv2:ListWebACLs
            - wafv2:ListTagsForResource
          Resource: '*'
```

#### Step 3: Set Up Trust Relationship

```yaml
# WAF Account: Allow team accounts to access WAF

WafAccessRole:
  Type: AWS::IAM::Role
  Properties:
    RoleName: CrossAccountWafAccessRole
    AssumeRolePolicyDocument:
      Version: '2012-10-17'
      Statement:
        # Team A Account
        - Effect: Allow
          Principal:
            AWS: !Sub 'arn:aws:iam::TEAM-A-ACCOUNT-ID:root'
          Action: 'sts:AssumeRole'
          Condition:
            StringEquals:
              'sts:ExternalId': !Sub 'TeamA-${AWS::AccountId}-waf'
        
        # Team B Account
        - Effect: Allow
          Principal:
            AWS: !Sub 'arn:aws:iam::TEAM-B-ACCOUNT-ID:root'
          Action: 'sts:AssumeRole'
          Condition:
            StringEquals:
              'sts:ExternalId': !Sub 'TeamB-${AWS::AccountId}-waf'
        
        # Team C Account
        - Effect: Allow
          Principal:
            AWS: !Sub 'arn:aws:iam::TEAM-C-ACCOUNT-ID:root'
          Action: 'sts:AssumeRole'
          Condition:
            StringEquals:
              'sts:ExternalId': !Sub 'TeamC-${AWS::AccountId}-waf'
    
    ManagedPolicyArns:
      - !GetAtt CrossAccountWAFPolicy.Arn
```

#### Step 4: Configure Team Account to Link WAF

```yaml
# In Team Account: CloudFormation to link ALB to WAF

AWSTemplateFormatVersion: '2010-09-09'
Description: 'Link ALB to centralized WAF'

Parameters:
  WafAccountId:
    Type: String
    Default: '123456789012'
  
  WebACLArn:
    Type: String
    Description: ARN of the Web ACL from WAF account
    Default: 'arn:aws:wafv2:us-east-1:123456789012:regional/webacl/dev-web-acl/abc123'

Resources:
  # ALB in team account
  ApplicationLoadBalancer:
    Type: AWS::ElasticLoadBalancingV2::LoadBalancer
    Properties:
      Name: myapp-alb
      Subnets:
        - subnet-12345
        - subnet-67890
      SecurityGroups:
        - sg-12345

  # Link WAF to ALB
  LinkWafToAlb:
    Type: AWS::WAFv2::WebACLAssociation
    Properties:
      ResourceArn: !GetAtt ApplicationLoadBalancer.LoadBalancerArn
      WebACLArn: !Ref WebACLArn

Outputs:
  ALBArn:
    Value: !GetAtt ApplicationLoadBalancer.LoadBalancerArn
  
  WafAssociationStatus:
    Value: 'WAF successfully associated to ALB'
```

#### Step 5: Alternative - Direct CLI Command

```bash
# In team account: Directly link WAF to ALB without CloudFormation

# Step 1: Get ALB ARN
ALB_ARN=$(aws elbv2 describe-load-balancers \
  --names myapp-alb \
  --query 'LoadBalancers[0].LoadBalancerArn' \
  --output text)

# Step 2: Get Web ACL ARN from WAF account (cross-account)
WEB_ACL_ARN="arn:aws:wafv2:us-east-1:123456789012:regional/webacl/dev-web-acl/abc123"

# Step 3: Associate WAF to ALB
aws wafv2 associate-web-acl \
  --web-acl-arn $WEB_ACL_ARN \
  --resource-arn $ALB_ARN \
  --region us-east-1

# Verify association
aws wafv2 get-web-acl-for-resource \
  --resource-arn $ALB_ARN \
  --region us-east-1
```

---

## Monitoring & Logging

### WAF Logging Architecture

```
┌──────────────────────────────┐
│   ALB (in Team Account)      │
│   Protected by WAF           │
└────────────────┬─────────────┘
                 │ HTTP Requests
                 ↓
    ┌────────────────────────┐
    │   WAF Web ACL          │
    │   (in WAF Account)     │
    │                        │
    │ • Evaluates requests   │
    │ • Applies rules        │
    │ • Takes action (block) │
    └────────────────┬───────┘
                     │
         ┌───────────┼───────────┐
         ↓           ↓           ↓
    ┌─────────┐  ┌─────────┐  ┌─────────┐
    │ ALLOW   │  │ BLOCK   │  │ COUNT   │
    │ Logs    │  │ Logs    │  │ Logs    │
    └────┬────┘  └────┬────┘  └────┬────┘
         │           │            │
         └───────────┼────────────┘
                     ↓
        ┌──────────────────────┐
        │ CloudWatch Logs      │
        │ /aws/waf/logs        │
        │ (Real-time)          │
        └──────────┬───────────┘
                   │
         ┌─────────┼─────────┐
         ↓         ↓         ↓
      ┌──────┐ ┌──────┐ ┌──────┐
      │ S3   │ │CloudWatch│ │Athena│
      │Bucket│ │Metrics   │ │Query │
      │      │ │Dashboard │ │Logs  │
      └──────┘ └──────┘ └──────┘
```

### CloudFormation for WAF Logging

```yaml
WafLoggingConfiguration:
  Type: AWS::WAFv2::LoggingConfiguration
  Properties:
    ResourceArn: !GetAtt ApplicationWebACL.Arn
    LogDestinationConfigs:
      - !Sub 'arn:aws:logs:${AWS::Region}:${AWS::AccountId}:log-group:/aws/waf/${EnvironmentType}'
    RedactedFields:
      - SingleHeader:
          Name: Authorization
      - SingleHeader:
          Name: X-API-Key
    LoggingFilter:
      DefaultBehavior: KEEP
      Filters:
        # Log only blocked requests
        - Behavior: KEEP
          Condition:
            ActionCondition:
              Action: BLOCK
          Requirement: MEETS_ALL

WafLogGroup:
  Type: AWS::Logs::LogGroup
  Properties:
    LogGroupName: !Sub '/aws/waf/${EnvironmentType}'
    RetentionInDays: 30

# Export logs to S3 for long-term storage
WafLogsBucket:
  Type: AWS::S3::Bucket
  Properties:
    BucketName: !Sub 'waf-logs-${AWS::AccountId}'
    VersioningConfiguration:
      Status: Enabled
    LifecycleConfiguration:
      Rules:
        - Id: ArchiveOldLogs
          Status: Enabled
          Transitions:
            - TransitionInDays: 90
              StorageClass: GLACIER
        - Id: DeleteVeryOldLogs
          Status: Enabled
          ExpirationInDays: 365

WafLogsExportToS3:
  Type: AWS::Events::Rule
  Properties:
    Description: Export WAF logs to S3
    ScheduleExpression: rate(1 hour)
    State: ENABLED
    Targets:
      - Arn: !GetAtt ExportLogsFunction.Arn
        RoleArn: !GetAtt EventsRole.Arn

# CloudWatch Dashboard for monitoring
WafDashboard:
  Type: AWS::CloudWatch::Dashboard
  Properties:
    DashboardName: !Sub '${EnvironmentType}-waf-dashboard'
    DashboardBody: !Sub |
      {
        "widgets": [
          {
            "type": "metric",
            "properties": {
              "metrics": [
                [ "AWS/WAFV2", "AllowedRequests", { "stat": "Sum" } ],
                [ ".", "BlockedRequests", { "stat": "Sum" } ],
                [ ".", "CountedRequests", { "stat": "Sum" } ]
              ],
              "period": 300,
              "stat": "Average",
              "region": "${AWS::Region}",
              "title": "WAF Request Summary"
            }
          },
          {
            "type": "log",
            "properties": {
              "query": "fields @timestamp, action, terminatingRuleId, httpRequest.clientIp | stats count() by action",
              "region": "${AWS::Region}",
              "title": "Requests by Action"
            }
          }
        ]
      }

# Alarms for critical events
HighBlockRateAlarm:
  Type: AWS::CloudWatch::Alarm
  Properties:
    AlarmName: !Sub '${EnvironmentType}-waf-high-block-rate'
    MetricName: BlockedRequests
    Namespace: AWS/WAFV2
    Statistic: Sum
    Period: 300
    EvaluationPeriods: 1
    Threshold: 1000
    ComparisonOperator: GreaterThanThreshold
    AlarmActions:
      - !Sub 'arn:aws:sns:${AWS::Region}:${AWS::AccountId}:waf-alerts'
```

### CloudWatch Insights Queries

```sql
-- Query 1: Top blocked requests
fields @timestamp, terminatingRuleId, action, httpRequest.clientIp
| filter action = "BLOCK"
| stats count() as BlockCount by terminatingRuleId
| sort BlockCount desc
| limit 20

-- Query 2: Requests by country
fields @timestamp, httpRequest.country, action
| stats count() as RequestCount by httpRequest.country
| sort RequestCount desc

-- Query 3: Rate limiting violations
fields @timestamp, httpRequest.clientIp, action
| filter terminatingRuleId like /RateLimit/
| stats count() as Requests by httpRequest.clientIp
| filter Requests > 100
| sort Requests desc

-- Query 4: SQL injection attempts
fields @timestamp, httpRequest.clientIp, httpRequest.uri, httpRequest.args
| filter terminatingRuleId like /SQL/
| stats count() as SQLAttempts by httpRequest.clientIp

-- Query 5: User agents of blocked requests
fields @timestamp, httpRequest.headers.User-Agent, action
| filter action = "BLOCK"
| stats count() as BlockCount by `httpRequest.headers.User-Agent`
```

---

## Best Practices

### 1. Start with COUNT Mode

```yaml
# First deploy rules in COUNT mode to measure impact
Rule:
  Name: TestRule
  Priority: 10
  Statement:
    RateBasedStatement:
      Limit: 2000
      AggregateKeyType: IP
  Action:
    Block: {}
  
  # Override to COUNT - don't block yet
  OverrideAction:
    Count: {}  # Switch to Block after testing
```

### 2. Maintain IP Sets Externally

```python
# Lambda function to manage IP sets
import boto3
import json

waf_client = boto3.client('wafv2')

def update_ip_set(ip_set_name, ips):
    # Get current IP set
    response = waf_client.list_ip_sets(
        Scope='REGIONAL'
    )
    
    # Find IP set
    ip_set = next((s for s in response['IPSets'] 
                   if s['Name'] == ip_set_name), None)
    
    if ip_set:
        # Update IP set
        waf_client.update_ip_set(
            Name=ip_set_name,
            Scope='REGIONAL',
            Id=ip_set['Id'],
            Addresses=ips,
            LockToken=ip_set['LockToken']
        )
        print(f"Updated {ip_set_name} with {len(ips)} IPs")
```

### 3. Implement Change Control Process

```
1. Propose rule change (Jira ticket)
   ↓
2. Review with security team
   ↓
3. Deploy to DEV environment in COUNT mode
   ↓
4. Monitor for false positives (24-48 hours)
   ↓
5. If no issues, deploy to STAGING in COUNT mode
   ↓
6. Monitor for false positives (24-48 hours)
   ↓
7. If no issues, deploy to PROD in COUNT mode (1 week)
   ↓
8. After 1 week, switch from COUNT to BLOCK
   ↓
9. Monitor for issues (1 week)
   ↓
10. Document change
```

### 4. Document Rule Decisions

```yaml
RuleDocumentation:
  RuleName: BlockSQLInjection
  Description: |
    Protects against SQL injection attacks using AWS managed rule set
    
    False Positive Rate: < 0.1%
    Effectiveness: Blocks 99%+ of SQL injection attempts
    
    Maintained By: Security Team
    Last Updated: 2024-01-15
    
    Excluded Rules:
      - SQLi_BODY: Excluded for payment processing API
        Reason: Legitimate SQL-like patterns in request body
    
    Contact: security@company.com
```

### 5. Test Rules Regularly

```python
# Test WAF rules with sample payloads
test_payloads = [
    # SQL Injection tests
    "' OR '1'='1",
    "admin' --",
    "1'; DROP TABLE users--",
    
    # XSS tests
    "<script>alert('XSS')</script>",
    "../../etc/passwd",
    
    # Valid requests (should not be blocked)
    "/api/users/123",
    "/search?q=python",
]

for payload in test_payloads:
    response = requests.get(
        f"https://myapp.example.com{payload}",
        timeout=5
    )
    print(f"{payload}: {response.status_code}")
    # Expect:
    # - SQL/XSS payloads: 403 (blocked)
    # - Valid requests: 200 (allowed)
```

### 6. Version Your Web ACLs

```yaml
WebACLVersioning:
  v1.0.0: Initial release (rate limit 2000)
  v1.1.0: Added SQL injection protection
  v1.2.0: Added XSS protection
  v1.3.0: Added geo-blocking
  v2.0.0: Stricter rate limit (1000), added bot control
  v2.1.0: Refined bot detection, reduced false positives
  v2.2.0: Added custom IP whitelist for partners
  
# Tag Web ACL with version
Tags:
  - Key: Version
    Value: v2.2.0
  - Key: ChangeLog
    Value: Added custom IP whitelist
```

### 7. Create Runbooks for Common Issues

```markdown
# WAF Runbook

## Issue: High False Positive Rate (Legitimate Requests Blocked)

### Symptoms:
- Users report "403 Forbidden" errors
- CloudWatch logs show BLOCK actions for legitimate paths

### Investigation:
1. Check recent rule deployments
2. Review CloudWatch Insights logs
3. Identify which rule is blocking

### Resolution:
1. Switch rule to COUNT mode (temporary)
2. Analyze blocked requests
3. Adjust rule conditions or exclude patterns
4. Test with refined rule
5. Switch back to BLOCK

## Issue: Rate Limiting Blocking Legitimate Traffic

### Symptoms:
- Specific IPs or customers hitting rate limit
- Request volume spikes

### Resolution:
1. Identify source IP in logs
2. Add to whitelist if legitimate
3. Increase rate limit if threshold too low
4. Use IP set exclusions for legitimate high-volume clients
```

---

## Summary Table: WAF vs Other Security Layers

| Feature | WAF | Security Group | NACL | Shield |
|---------|-----|---|---|---|
| Layer | 7 (App) | 3 (Network) | 3 (Network) | 3 (Network) |
| SQL Injection | ✅ Yes | ❌ No | ❌ No | ❌ No |
| XSS Protection | ✅ Yes | ❌ No | ❌ No | ❌ No |
| Rate Limiting | ✅ Yes | ❌ No | ❌ No | ❌ No |
| IP Filtering | ✅ Yes | ✅ Yes | ✅ Yes | ✅ Yes |
| Geo-blocking | ✅ Yes | ❌ No | ❌ No | ❌ No |
| Bot Detection | ✅ Yes | ❌ No | ❌ No | ❌ No |
| DDoS Protection | ✅ Limited | ❌ No | ❌ No | ✅ Yes |
| Stateful | ✅ Yes | ✅ Yes | ❌ No | N/A |
| Per-request cost | $$ | Free | Free | $$$ |

---

## Next Steps

1. **Set up WAF Account**: Create centralized AWS account for WAF
2. **Define Organization Standards**: Create base Web ACLs for all environments
3. **Implement Onboarding Process**: Create application registration form
4. **Set up Monitoring**: Configure CloudWatch logs and dashboards
5. **Create Runbooks**: Document common issues and resolutions
6. **Train Teams**: Educate all teams on WAF features and limits
7. **Start with Dev**: Deploy to dev environment first
8. **Gradually Roll Out**: Move to staging, then production

**See related docs**:
- [SECURITY_AND_IAM.md](../SECURITY_AND_IAM.md) - Network security layers
- [MONITORING_AND_LOGGING.md](../MONITORING_AND_LOGGING.md) - CloudWatch setup
- [ROUTE53_ALB_CONNECTIVITY.md](../ROUTE53_ALB_CONNECTIVITY.md) - ALB configuration
