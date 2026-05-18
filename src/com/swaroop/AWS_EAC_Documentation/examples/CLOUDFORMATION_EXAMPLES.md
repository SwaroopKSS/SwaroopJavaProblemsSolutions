# CloudFormation EAC Examples - Complete Working Templates

## Example 1: Simple VPC Setup

```yaml
# vpc-template.yaml
AWSTemplateFormatVersion: '2010-09-09'
Description: 'VPC with Public and Private Subnets in 3 AZs'

Parameters:
  Environment:
    Type: String
    Default: dev
    AllowedValues: [dev, staging, prod]
  
  VpcCIDR:
    Type: String
    Default: 10.0.0.0/16
    Description: CIDR block for VPC

Resources:
  # VPC
  VPC:
    Type: AWS::EC2::VPC
    Properties:
      CidrBlock: !Ref VpcCIDR
      EnableDnsHostnames: true
      EnableDnsSupport: true
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-vpc'
        - Key: Environment
          Value: !Ref Environment

  # Internet Gateway
  InternetGateway:
    Type: AWS::EC2::InternetGateway
    Properties:
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-igw'

  AttachGateway:
    Type: AWS::EC2::VPCGatewayAttachment
    Properties:
      VpcId: !Ref VPC
      InternetGatewayId: !Ref InternetGateway

  # Public Subnets
  PublicSubnet1a:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.1.0/24
      AvailabilityZone: !Select [0, !GetAZs '']
      MapPublicIpOnLaunch: true
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-public-subnet-1a'

  PublicSubnet1b:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.2.0/24
      AvailabilityZone: !Select [1, !GetAZs '']
      MapPublicIpOnLaunch: true
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-public-subnet-1b'

  PublicSubnet1c:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.3.0/24
      AvailabilityZone: !Select [2, !GetAZs '']
      MapPublicIpOnLaunch: true
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-public-subnet-1c'

  # Private Subnets
  PrivateSubnet1a:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.11.0/24
      AvailabilityZone: !Select [0, !GetAZs '']
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-subnet-1a'

  PrivateSubnet1b:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.12.0/24
      AvailabilityZone: !Select [1, !GetAZs '']
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-subnet-1b'

  PrivateSubnet1c:
    Type: AWS::EC2::Subnet
    Properties:
      VpcId: !Ref VPC
      CidrBlock: 10.0.13.0/24
      AvailabilityZone: !Select [2, !GetAZs '']
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-subnet-1c'

  # NAT Gateways (One per AZ for HA)
  NATGatewayEIP1a:
    Type: AWS::EC2::EIP
    DependsOn: AttachGateway
    Properties:
      Domain: vpc
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-nat-eip-1a'

  NATGateway1a:
    Type: AWS::EC2::NatGateway
    Properties:
      AllocationId: !GetAtt NATGatewayEIP1a.AllocationId
      SubnetId: !Ref PublicSubnet1a
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-nat-1a'

  NATGatewayEIP1b:
    Type: AWS::EC2::EIP
    DependsOn: AttachGateway
    Properties:
      Domain: vpc

  NATGateway1b:
    Type: AWS::EC2::NatGateway
    Properties:
      AllocationId: !GetAtt NATGatewayEIP1b.AllocationId
      SubnetId: !Ref PublicSubnet1b

  NATGatewayEIP1c:
    Type: AWS::EC2::EIP
    DependsOn: AttachGateway
    Properties:
      Domain: vpc

  NATGateway1c:
    Type: AWS::EC2::NatGateway
    Properties:
      AllocationId: !GetAtt NATGatewayEIP1c.AllocationId
      SubnetId: !Ref PublicSubnet1c

  # Public Route Table
  PublicRouteTable:
    Type: AWS::EC2::RouteTable
    Properties:
      VpcId: !Ref VPC
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-public-rt'

  PublicRoute:
    Type: AWS::EC2::Route
    DependsOn: AttachGateway
    Properties:
      RouteTableId: !Ref PublicRouteTable
      DestinationCidrBlock: 0.0.0.0/0
      GatewayId: !Ref InternetGateway

  PublicSubnetRouteTableAssociation1a:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PublicSubnet1a
      RouteTableId: !Ref PublicRouteTable

  PublicSubnetRouteTableAssociation1b:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PublicSubnet1b
      RouteTableId: !Ref PublicRouteTable

  PublicSubnetRouteTableAssociation1c:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PublicSubnet1c
      RouteTableId: !Ref PublicRouteTable

  # Private Route Tables (one per AZ with NAT)
  PrivateRouteTable1a:
    Type: AWS::EC2::RouteTable
    Properties:
      VpcId: !Ref VPC
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-rt-1a'

  PrivateRoute1a:
    Type: AWS::EC2::Route
    Properties:
      RouteTableId: !Ref PrivateRouteTable1a
      DestinationCidrBlock: 0.0.0.0/0
      NatGatewayId: !Ref NATGateway1a

  PrivateSubnetRouteTableAssociation1a:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PrivateSubnet1a
      RouteTableId: !Ref PrivateRouteTable1a

  PrivateRouteTable1b:
    Type: AWS::EC2::RouteTable
    Properties:
      VpcId: !Ref VPC
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-rt-1b'

  PrivateRoute1b:
    Type: AWS::EC2::Route
    Properties:
      RouteTableId: !Ref PrivateRouteTable1b
      DestinationCidrBlock: 0.0.0.0/0
      NatGatewayId: !Ref NATGateway1b

  PrivateSubnetRouteTableAssociation1b:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PrivateSubnet1b
      RouteTableId: !Ref PrivateRouteTable1b

  PrivateRouteTable1c:
    Type: AWS::EC2::RouteTable
    Properties:
      VpcId: !Ref VPC
      Tags:
        - Key: Name
          Value: !Sub '${Environment}-private-rt-1c'

  PrivateRoute1c:
    Type: AWS::EC2::Route
    Properties:
      RouteTableId: !Ref PrivateRouteTable1c
      DestinationCidrBlock: 0.0.0.0/0
      NatGatewayId: !Ref NATGateway1c

  PrivateSubnetRouteTableAssociation1c:
    Type: AWS::EC2::SubnetRouteTableAssociation
    Properties:
      SubnetId: !Ref PrivateSubnet1c
      RouteTableId: !Ref PrivateRouteTable1c

Outputs:
  VpcId:
    Description: VPC ID
    Value: !Ref VPC
    Export:
      Name: !Sub '${Environment}-vpc-id'

  PublicSubnets:
    Description: Public Subnet IDs
    Value: !Join [',', [!Ref PublicSubnet1a, !Ref PublicSubnet1b, !Ref PublicSubnet1c]]
    Export:
      Name: !Sub '${Environment}-public-subnets'

  PrivateSubnets:
    Description: Private Subnet IDs
    Value: !Join [',', [!Ref PrivateSubnet1a, !Ref PrivateSubnet1b, !Ref PrivateSubnet1c]]
    Export:
      Name: !Sub '${Environment}-private-subnets'
```

**Deploy**:
```bash
aws cloudformation create-stack \
  --stack-name dev-vpc \
  --template-body file://vpc-template.yaml \
  --parameters ParameterKey=Environment,ParameterValue=dev
```

---

## Example 2: ALB with EC2 Auto Scaling

```yaml
# alb-asg-template.yaml
AWSTemplateFormatVersion: '2010-09-09'
Description: 'ALB with Auto Scaling EC2 Instances'

Parameters:
  VpcId:
    Type: AWS::EC2::VPC::Id
    Description: VPC ID (from vpc-template output)
  
  PublicSubnets:
    Type: List<AWS::EC2::Subnet::Id>
    Description: Public Subnet IDs for ALB
  
  PrivateSubnets:
    Type: List<AWS::EC2::Subnet::Id>
    Description: Private Subnet IDs for EC2
  
  InstanceType:
    Type: String
    Default: t3.micro
    AllowedValues: [t3.micro, t3.small, t3.medium, m5.large]

Resources:
  # Security Groups
  ALBSecurityGroup:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: ALB Security Group
      VpcId: !Ref VpcId
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
      Tags:
        - Key: Name
          Value: alb-sg

  EC2SecurityGroup:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: EC2 Security Group
      VpcId: !Ref VpcId
      SecurityGroupIngress:
        - IpProtocol: tcp
          FromPort: 8080
          ToPort: 8080
          SourceSecurityGroupId: !Ref ALBSecurityGroup
        - IpProtocol: tcp
          FromPort: 3000
          ToPort: 3000
          SourceSecurityGroupId: !Ref ALBSecurityGroup
        - IpProtocol: tcp
          FromPort: 22
          ToPort: 22
          CidrIp: 0.0.0.0/0  # Restrict this in production
      SecurityGroupEgress:
        - IpProtocol: -1
          CidrIp: 0.0.0.0/0
      Tags:
        - Key: Name
          Value: ec2-sg

  # Application Load Balancer
  ApplicationLoadBalancer:
    Type: AWS::ElasticLoadBalancingV2::LoadBalancer
    Properties:
      Name: my-alb
      Scheme: internet-facing
      Type: application
      Subnets: !Ref PublicSubnets
      SecurityGroups:
        - !Ref ALBSecurityGroup
      Tags:
        - Key: Name
          Value: my-alb

  # Target Group
  TargetGroup:
    Type: AWS::ElasticLoadBalancingV2::TargetGroup
    Properties:
      Name: app-tg
      Port: 8080
      Protocol: HTTP
      VpcId: !Ref VpcId
      HealthCheckEnabled: true
      HealthCheckPath: /health
      HealthCheckProtocol: HTTP
      HealthCheckPort: 8080
      HealthCheckIntervalSeconds: 30
      HealthCheckTimeoutSeconds: 5
      HealthyThresholdCount: 2
      UnhealthyThresholdCount: 3
      TargetType: instance
      Tags:
        - Key: Name
          Value: app-tg

  # ALB Listener
  ALBListener:
    Type: AWS::ElasticLoadBalancingV2::Listener
    Properties:
      LoadBalancerArn: !GetAtt ApplicationLoadBalancer.Arn
      Port: 80
      Protocol: HTTP
      DefaultActions:
        - Type: forward
          TargetGroupArn: !GetAtt TargetGroup.Arn

  # IAM Role for EC2
  EC2Role:
    Type: AWS::IAM::Role
    Properties:
      AssumeRolePolicyDocument:
        Version: '2012-10-17'
        Statement:
          - Effect: Allow
            Principal:
              Service: ec2.amazonaws.com
            Action: sts:AssumeRole
      ManagedPolicyArns:
        - arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore
        - arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy

  EC2InstanceProfile:
    Type: AWS::IAM::InstanceProfile
    Properties:
      Roles:
        - !Ref EC2Role

  # Launch Template
  LaunchTemplate:
    Type: AWS::EC2::LaunchTemplate
    Properties:
      LaunchTemplateName: app-launch-template
      LaunchTemplateData:
        ImageId: ami-0c55b159cbfafe1f0  # Amazon Linux 2
        InstanceType: !Ref InstanceType
        IamInstanceProfile:
          Arn: !GetAtt EC2InstanceProfile.Arn
        SecurityGroupIds:
          - !Ref EC2SecurityGroup
        UserData:
          Fn::Base64: |
            #!/bin/bash
            yum update -y
            yum install -y httpd
            
            # Simple health check endpoint
            echo "OK" > /var/www/html/health
            
            # Start web server
            systemctl start httpd
            systemctl enable httpd
        TagSpecifications:
          - ResourceType: instance
            Tags:
              - Key: Name
                Value: app-server

  # Auto Scaling Group
  AutoScalingGroup:
    Type: AWS::AutoScaling::AutoScalingGroup
    Properties:
      LaunchTemplate:
        LaunchTemplateId: !Ref LaunchTemplate
        Version: !GetAtt LaunchTemplate.LatestVersionNumber
      MinSize: 2
      MaxSize: 10
      DesiredCapacity: 3
      VPCZoneIdentifier: !Ref PrivateSubnets
      TargetGroupARNs:
        - !GetAtt TargetGroup.Arn
      HealthCheckType: ELB
      HealthCheckGracePeriod: 300
      Tags:
        - Key: Name
          Value: app-asg
          PropagateAtLaunch: true

  # Scaling Policy - Scale Up
  ScaleUpPolicy:
    Type: AWS::AutoScaling::ScalingPolicy
    Properties:
      AdjustmentType: ChangeInCapacity
      AutoScalingGroupName: !Ref AutoScalingGroup
      Cooldown: 60
      ScalingAdjustment: 1

  # Scaling Policy - Scale Down
  ScaleDownPolicy:
    Type: AWS::AutoScaling::ScalingPolicy
    Properties:
      AdjustmentType: ChangeInCapacity
      AutoScalingGroupName: !Ref AutoScalingGroup
      Cooldown: 300
      ScalingAdjustment: -1

  # CloudWatch Alarms
  HighCPUAlarm:
    Type: AWS::CloudWatch::Alarm
    Properties:
      AlarmName: high-cpu-alarm
      AlarmDescription: Trigger scale up when CPU > 70%
      MetricName: CPUUtilization
      Namespace: AWS/EC2
      Statistic: Average
      Period: 60
      EvaluationPeriods: 2
      Threshold: 70
      ComparisonOperator: GreaterThanThreshold
      Dimensions:
        - Name: AutoScalingGroupName
          Value: !Ref AutoScalingGroup
      AlarmActions:
        - !Ref ScaleUpPolicy

  LowCPUAlarm:
    Type: AWS::CloudWatch::Alarm
    Properties:
      AlarmName: low-cpu-alarm
      AlarmDescription: Trigger scale down when CPU < 30%
      MetricName: CPUUtilization
      Namespace: AWS/EC2
      Statistic: Average
      Period: 300
      EvaluationPeriods: 2
      Threshold: 30
      ComparisonOperator: LessThanThreshold
      Dimensions:
        - Name: AutoScalingGroupName
          Value: !Ref AutoScalingGroup
      AlarmActions:
        - !Ref ScaleDownPolicy

Outputs:
  LoadBalancerDNS:
    Description: DNS name of the Load Balancer
    Value: !GetAtt ApplicationLoadBalancer.DNSName
    Export:
      Name: alb-dns

  TargetGroupArn:
    Description: Target Group ARN
    Value: !GetAtt TargetGroup.Arn
    Export:
      Name: target-group-arn
```

**Deploy**:
```bash
aws cloudformation create-stack \
  --stack-name app-alb-asg \
  --template-body file://alb-asg-template.yaml \
  --parameters \
    ParameterKey=VpcId,ParameterValue=vpc-12345 \
    ParameterKey=PublicSubnets,ParameterValue="subnet-1,subnet-2,subnet-3" \
    ParameterKey=PrivateSubnets,ParameterValue="subnet-11,subnet-12,subnet-13" \
  --capabilities CAPABILITY_NAMED_IAM
```

---

## Example 3: Route 53 with Alias Record

```yaml
# route53-template.yaml
AWSTemplateFormatVersion: '2010-09-09'
Description: 'Route 53 DNS Configuration'

Parameters:
  DomainName:
    Type: String
    Default: example.com
    Description: Domain name for hosting zone
  
  ALBDNSName:
    Type: String
    Description: DNS name of ALB (from alb-asg output)

Resources:
  # Hosted Zone
  HostedZone:
    Type: AWS::Route53::HostedZone
    Properties:
      Name: !Ref DomainName
      Tags:
        - Key: Name
          Value: !Sub '${DomainName}-hosted-zone'

  # A Record for root domain
  RootAliasRecord:
    Type: AWS::Route53::RecordSet
    Properties:
      HostedZoneId: !Ref HostedZone
      Name: !Ref DomainName
      Type: A
      AliasTarget:
        HostedZoneId: Z35SXDOTRQ7X7K  # ALB Hosted Zone ID (us-east-1)
        DNSName: !Ref ALBDNSName
        EvaluateTargetHealth: true

  # A Record for www subdomain
  WWWAliasRecord:
    Type: AWS::Route53::RecordSet
    Properties:
      HostedZoneId: !Ref HostedZone
      Name: !Sub 'www.${DomainName}'
      Type: A
      AliasTarget:
        HostedZoneId: Z35SXDOTRQ7X7K
        DNSName: !Ref ALBDNSName
        EvaluateTargetHealth: true

  # A Record for api subdomain
  APIAliasRecord:
    Type: AWS::Route53::RecordSet
    Properties:
      HostedZoneId: !Ref HostedZone
      Name: !Sub 'api.${DomainName}'
      Type: A
      AliasTarget:
        HostedZoneId: Z35SXDOTRQ7X7K
        DNSName: !Ref ALBDNSName
        EvaluateTargetHealth: true

Outputs:
  HostedZoneId:
    Description: Hosted Zone ID
    Value: !Ref HostedZone
    Export:
      Name: hosted-zone-id

  NameServers:
    Description: Name Servers for domain registration
    Value: !Join [',', !GetAtt HostedZone.NameServers]
    Export:
      Name: name-servers
```

**Deploy**:
```bash
aws cloudformation create-stack \
  --stack-name app-route53 \
  --template-body file://route53-template.yaml \
  --parameters \
    ParameterKey=DomainName,ParameterValue=example.com \
    ParameterKey=ALBDNSName,ParameterValue=my-alb-123456.us-east-1.elb.amazonaws.com
```

---

## Deployment Order

```
1. Deploy VPC Stack
   aws cloudformation create-stack --stack-name dev-vpc ...

2. Get outputs from VPC stack
   aws cloudformation describe-stacks --stack-name dev-vpc

3. Deploy ALB + ASG Stack (uses VPC outputs)
   aws cloudformation create-stack --stack-name app-alb-asg ...

4. Get ALB DNS from ALB stack output
   aws cloudformation describe-stacks --stack-name app-alb-asg

5. Deploy Route 53 (uses ALB DNS)
   aws cloudformation create-stack --stack-name app-route53 ...

6. Update domain registrar with Route 53 nameservers
```

---

**Next**: Check [TERRAFORM_EXAMPLES.md](./TERRAFORM_EXAMPLES.md) for Terraform equivalent!
