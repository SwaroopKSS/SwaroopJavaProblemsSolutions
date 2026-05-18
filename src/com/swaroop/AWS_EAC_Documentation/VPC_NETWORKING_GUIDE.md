# AWS VPC Networking Guide - Complete Reference

## What is a VPC?

**VPC (Virtual Private Cloud)** = Your own isolated network within AWS where you launch resources.

Think of it like a private data center you control, except it's virtual and on AWS infrastructure.

---

## VPC Architecture Overview

```
┌────────────────────────────────────────────────┐
│  VPC (10.0.0.0/16)                             │
│                                                │
│  ┌─────────────────┐  ┌─────────────────┐     │
│  │  Availability   │  │  Availability   │     │
│  │   Zone 1a       │  │   Zone 1b       │     │
│  │                 │  │                 │     │
│  │  ┌────────────┐ │  │  ┌────────────┐ │     │
│  │  │ Public SN  │ │  │  │ Public SN  │ │     │
│  │  │10.0.1.0/24 │ │  │  │10.0.2.0/24 │ │     │
│  │  └────────────┘ │  │  └────────────┘ │     │
│  │         ↓        │  │         ↓       │     │
│  │  ┌────────────┐ │  │  ┌────────────┐ │     │
│  │  │ Private SN │ │  │  │ Private SN │ │     │
│  │  │10.0.11.0/24│ │  │  │10.0.12.0/24│ │     │
│  │  └────────────┘ │  │  └────────────┘ │     │
│  │         │               │                │
│  │         └───┐   ┌───────┘                │
│  │             ↓   ↓                        │
│  │      ┌───────────────────────────┐       │
│  │      │        NAT Gateway         │       │
│  │      │   (Outbound only internet) │       │
│  │      │     Elastic IP required    │       │
│  │      └───────────────────────────┘       │
│         ┌─────────────────────────────────┐   │
│         │ Internet Gateway                │   │
│         │ (Connects to 0.0.0.0/0)         │   │
│         └─────────────────────────────────┘   │
└────────────────────────────────────────────────┘
           ↓
      External Users
```

---

## Core VPC Components

### 1. VPC CIDR Block

**CIDR (Classless Inter-Domain Routing)** = IP address range for your VPC.

**Common CIDR Blocks**:
```
10.0.0.0/16    = 65,536 IP addresses (recommended for enterprises)
10.0.0.0/24    = 256 IP addresses (smaller networks)
172.16.0.0/16  = 65,536 IP addresses (alternative)
192.168.0.0/16 = 65,536 IP addresses (small networks)
```

**How CIDR works**:
```
10.0.0.0/16
└─ First 16 bits (10.0) are fixed
└─ Last 16 bits (0.0) are variable
└─ Allows: 10.0.0.0 through 10.0.255.255
```

### How to calculate IP addresses from a CIDR block

Use this formula:

```
Number of IPs = 2^(32 - prefix-length)
```

Examples:
```
/16 => 2^(32 - 16) = 2^16 = 65,536 IP addresses
/24 => 2^(32 - 24) = 2^8  = 256 IP addresses
/20 => 2^(32 - 20) = 2^12 = 4,096 IP addresses
/28 => 2^(32 - 28) = 2^4  = 16 IP addresses
```

**Important note**: In AWS VPC, each subnet reserves 5 IP addresses for internal use, so usable IPs = total IPs - 5.

```
10.0.1.0/24 => 256 total IPs, 251 usable IPs
10.0.11.0/24 => 256 total IPs, 251 usable IPs
10.0.0.0/16 => 65,536 total IPs, 65,531 usable IPs
```

**Subnet sizing guidance**:
- /28: 16 total, 11 usable
- /26: 64 total, 59 usable
- /24: 256 total, 251 usable
- /20: 4,096 total, 4,091 usable
- /16: 65,536 total, 65,531 usable

**In EAC**:
```yaml
VPC:
  CidrBlock: 10.0.0.0/16
  EnableDnsHostnames: true
  EnableDnsSupport: true
  Tags:
    Name: production-vpc
```

---

### 2. Subnets

**Subnet** = Division of VPC CIDR block, tied to specific Availability Zone.

**Public Subnet**: Has direct route to Internet Gateway
- Resources get public IP addresses
- Can be accessed from internet
- Use for: Load Balancers, NAT Gateways, Bastion Hosts

**Private Subnet**: No direct route to Internet Gateway
- Resources don't get public IPs
- Can't be accessed from internet directly
- Use for: EC2 applications, RDS databases

**Subnet Allocation Example**:
```
VPC: 10.0.0.0/16

Availability Zone 1a:
  Public Subnet:  10.0.1.0/24   (256 addresses)
  Private Subnet: 10.0.11.0/24  (256 addresses)

Availability Zone 1b:
  Public Subnet:  10.0.2.0/24   (256 addresses)
  Private Subnet: 10.0.12.0/24  (256 addresses)

Availability Zone 1c:
  Public Subnet:  10.0.3.0/24   (256 addresses)
  Private Subnet: 10.0.13.0/24  (256 addresses)

Total: 18 subnets (6 per AZ) = 4,608 usable IPs
```

**In EAC**:
```yaml
PublicSubnet1a:
  VpcId: !Ref VPC
  CidrBlock: 10.0.1.0/24
  AvailabilityZone: us-east-1a
  MapPublicIpOnLaunch: true

PrivateSubnet1a:
  VpcId: !Ref VPC
  CidrBlock: 10.0.11.0/24
  AvailabilityZone: us-east-1a
  MapPublicIpOnLaunch: false
```

---

### 3. Internet Gateway

**Internet Gateway (IGW)** = VPC's connection to the internet.

**Characteristics**:
- Enables communication between VPC and internet
- No bandwidth limitations
- Horizontally scaled (highly available)
- Each VPC has at most 1 IGW

**In EAC**:
```yaml
InternetGateway:
  Tags:
    Name: main-igw

AttachIGWToVPC:
  InternetGatewayId: !Ref InternetGateway
  VpcId: !Ref VPC
```

---

### 4. Route Tables

**Route Table** = Rules for where network traffic goes.

**Public Route Table** (for public subnets):
```
Destination      | Target           | Use Case
─────────────────────────────────────────────────
10.0.0.0/16      | Local            | Internal VPC traffic
0.0.0.0/0        | Internet Gateway | External traffic
```

**Private Route Table** (for private subnets):
```
Destination      | Target           | Use Case
─────────────────────────────────────────────────
10.0.0.0/16      | Local            | Internal VPC traffic
0.0.0.0/0        | NAT Gateway      | External traffic (one-way out)
```

**In EAC**:
```yaml
PublicRouteTable:
  VpcId: !Ref VPC

PublicRoute:
  RouteTableId: !Ref PublicRouteTable
  DestinationCidrBlock: 0.0.0.0/0
  GatewayId: !Ref InternetGateway

PublicSubnetRouteTableAssociation:
  SubnetId: !Ref PublicSubnet1a
  RouteTableId: !Ref PublicRouteTable

PrivateRouteTable:
  VpcId: !Ref VPC

PrivateRoute:
  RouteTableId: !Ref PrivateRouteTable
  DestinationCidrBlock: 0.0.0.0/0
  NatGatewayId: !Ref NATGateway

PrivateSubnetRouteTableAssociation:
  SubnetId: !Ref PrivateSubnet1a
  RouteTableId: !Ref PrivateRouteTable
```

---

### 5. NAT Gateway

**NAT Gateway** = Allows private subnet resources to access internet (one-way outbound).

**How it works**:
```
Private EC2 Instance (10.0.11.50)
        ↓
   NAT Gateway (10.0.1.50)
        ↓
  Internet Gateway
        ↓
  External Server
        ↓
  Response comes back to NAT Gateway
        ↓
  Forwarded to EC2 Instance
        ↓
  EC2 Instance receives response
```

**Key Points**:
- Placed in PUBLIC subnet
- Requires Elastic IP (static public IP)
- One NAT per AZ for HA
- Charges apply (hourly + data transfer)

**In EAC**:
```yaml
NATGatewayEIP:
  Domain: vpc

NATGateway:
  SubnetId: !Ref PublicSubnet1a  # Must be in public subnet
  AllocationId: !GetAtt NATGatewayEIP.AllocationId

PrivateRoute:
  RouteTableId: !Ref PrivateRouteTable
  DestinationCidrBlock: 0.0.0.0/0
  NatGatewayId: !Ref NATGateway
```

---

### 6. VPC Endpoints

**VPC Endpoint** = Private connection to AWS services without internet.

**Types**:

**Gateway Endpoint** (Free):
- S3
- DynamoDB
```yaml
S3Endpoint:
  VpcId: !Ref VPC
  ServiceName: com.amazonaws.us-east-1.s3
  RouteTableIds:
    - !Ref PrivateRouteTable
```

**Interface Endpoint** (Charged):
- API Gateway, SNS, SQS, etc.

---

### 7. Network ACLs (NACLs)

**NACL** = Stateless firewall at subnet level.

**Characteristics**:
- Stateless (return traffic must be explicitly allowed)
- Subnet-level (not instance-level)
- Processed in order (priority matters)
- Default allows all traffic

**Example**:
```yaml
NetworkACL:
  VpcId: !Ref VPC

IngressRule:
  NetworkAclId: !Ref NetworkACL
  RuleNumber: 100
  Protocol: 6  # TCP
  RuleAction: allow
  CidrBlock: 0.0.0.0/0
  PortRange:
    From: 80
    To: 80

IngressRuleHTTPS:
  NetworkAclId: !Ref NetworkACL
  RuleNumber: 110
  Protocol: 6
  RuleAction: allow
  CidrBlock: 0.0.0.0/0
  PortRange:
    From: 443
    To: 443

EgressRule:
  NetworkAclId: !Ref NetworkACL
  RuleNumber: 100
  Protocol: -1  # All
  RuleAction: allow
  CidrBlock: 0.0.0.0/0
```

---

## Network Traffic Flow

### Scenario: Web Request to EC2 Instance

```
User (210.1.1.1)
    ↓
1. DNS Query (Route 53)
    ↓
2. Route 53 returns ALB IP (54.123.45.67)
    ↓
3. Browser connects to ALB
    ↓
4. ALB in Public Subnet (10.0.1.50)
    Internet Gateway checks:
    ✓ Source: 210.1.1.1 (from internet) - ALLOWED
    ✓ Destination: 54.123.45.67 (ALB IP) - ALLOWED
    ↓
5. ALB routes to EC2 in Private Subnet (10.0.11.50)
    Route Table checks:
    ✓ Destination: 10.0.11.50 (local) - ALLOWED
    Security Group checks:
    ✓ Source: ALB (10.0.1.50) - ALLOWED on port 8080
    ↓
6. EC2 Instance receives request
    ↓
7. EC2 sends response back through ALB
    (Reverse path)
    ↓
8. User receives response
```

---

## High Availability Architecture

```
┌─────────────────────────────────────────────────┐
│  VPC (10.0.0.0/16)                              │
│                                                 │
│  AZ 1a            Route 53            AZ 1b    │
│  ┌──────────┐        ↓         ┌──────────┐   │
│  │Public SN │     (Health)     │Public SN │   │
│  │10.0.1.0  │        ↓         │10.0.2.0  │   │
│  │          │                  │          │   │
│  │ ┌──────┐ │      ALB        │ ┌──────┐ │   │
│  │ │ NAT  │ │   (Shared)      │ │ NAT  │ │   │
│  │ └──────┘ │      ↓          │ └──────┘ │   │
│  └──────────┘                 └──────────┘   │
│       ↓                             ↓         │
│  ┌──────────┐                 ┌──────────┐   │
│  │Private   │                 │Private   │   │
│  │SN        │                 │SN        │   │
│  │10.0.11.0 │                 │10.0.12.0 │   │
│  │          │                 │          │   │
│  │ ┌──────┐ │                 │ ┌──────┐ │   │
│  │ │ EC2  │─┼────┐    ┌───────┼─│ EC2  │ │   │
│  │ │ ASG  │ │    │    │       │ │ ASG  │ │   │
│  │ └──────┘ │    └────┼───────┘ └──────┘ │   │
│  │          │         ↓         │        │   │
│  └──────────┘   ┌──────────┐   └──────────┘   │
│                 │   RDS    │                   │
│                 │ Multi-AZ │                   │
│                 │ Database │                   │
│                 └──────────┘                   │
└─────────────────────────────────────────────────┘
```

**Key HA Features**:
1. Multiple AZs
2. ALB distributes traffic
3. RDS Multi-AZ has standby in another AZ
4. Auto Scaling replaces failed instances

---

## Security Layers

```
Layer 1: Network ACLs (Subnet level)
         ↓ (Stateless firewall)
Layer 2: Security Groups (Instance level)
         ↓ (Stateful firewall)
Layer 3: Application Layer (Inside instance)
         ↓
EC2 Instance with Application Code
```

---

## VPC Peering

**VPC Peering** = Connect two VPCs privately.

```
VPC-A (10.0.0.0/16)     VPC-B (172.16.0.0/16)
    ↓                            ↓
    └────────── VPC Peering ────────┘
    
Applications in VPC-A can now communicate
with applications in VPC-B directly
(No internet Gateway needed)
```

---

## Summary Table

| Component | Purpose | Scope | Cost |
|-----------|---------|-------|------|
| VPC | Network isolation | Account | Free |
| Subnet | IP range | AZ | Free |
| IGW | Internet access | VPC | Free |
| NAT | Outbound access | AZ | Hourly charge |
| Route Table | Traffic rules | Subnet | Free |
| NACL | Subnet firewall | Subnet | Free |
| Security Group | Instance firewall | Instance | Free |

---

## Best Practices

### 1. **Use Multiple AZs for HA**
```yaml
Subnets:
  - AZ: us-east-1a
    PublicCIDR: 10.0.1.0/24
    PrivateCIDR: 10.0.11.0/24
  - AZ: us-east-1b
    PublicCIDR: 10.0.2.0/24
    PrivateCIDR: 10.0.12.0/24
  - AZ: us-east-1c
    PublicCIDR: 10.0.3.0/24
    PrivateCIDR: 10.0.13.0/24
```

### 2. **Separate Public and Private Subnets**
- Public: Load Balancers, NAT Gateways
- Private: Applications, Databases

### 3. **Use Security Groups as Primary Defense**
```yaml
SecurityGroup:
  Ingress:
    - SourceSecurityGroup: ALB-SG  # Not CIDR
    - Port: 8080
  Egress:
    - CIDR: 0.0.0.0/0
    - Protocol: All
```

### 4. **Plan IP Space**
- Use IP calculator to avoid overlap
- Reserve space for future growth
- Document subnet allocation

### 5. **Enable VPC Flow Logs**
```yaml
VPCFlowLogs:
  ResourceType: VPC
  TrafficType: ALL
  LogDestinationType: CloudWatch Logs
  LogGroupName: /aws/vpc/flowlogs
```

---

**Next**: Review [ROUTE53_ALB_CONNECTIVITY.md](./ROUTE53_ALB_CONNECTIVITY.md) to see how VPC networking connects to Route 53 and ALB.
