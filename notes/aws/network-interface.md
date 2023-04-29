## What is a network interface in AWS ?

In Amazon Web Services (AWS), a network interface is a logical networking component that connects an Amazon Elastic
Compute Cloud (EC2) instance to a VPC (Virtual Private Cloud) network. A network interface provides an IP address and
other network configuration information to an EC2 instance, and allows it to communicate with other resources within the
VPC.

Each EC2 instance can have one or more network interfaces attached to it, and each network interface is associated with
a specific subnet and security group within the VPC. This allows you to control the traffic that flows to and from the
EC2 instance based on network rules and security policies.

## How to create a network interface in AWS ?

Network interfaces can be created and managed using the AWS Management Console, the AWS Command Line Interface (CLI), or
the AWS SDKs (Software Development Kits). You can create a network interface when you launch a new EC2 instance, or you
can create one separately and then attach it to an existing instance.

## Common use cases for network interfaces in AWS include:

Some common use cases for network interfaces in AWS include:

- Creating multi-homed instances: You can attach multiple network interfaces to a single EC2 instance, allowing it to
communicate with different subnets and security groups within the VPC.

- Creating an Elastic Network Interface (ENI): An ENI is a special type of network interface that can be attached to an
EC2 instance to provide a fixed, static IP address that remains the same even if the instance is stopped and started
again.

- Attaching a network interface to a load balancer: You can attach a network interface to an Elastic Load Balancer (ELB)
to distribute incoming traffic to multiple EC2 instances

## Resources

- ChatGPTPrompt:
  > What is a network interface in EC2 in AWS
- 