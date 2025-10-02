## What is the difference between associating an Elastic IP with an instance and network interface

When you allocate an Elastic IP address in Amazon Web Services (AWS), you can associate it either with an instance or
with a network interface. The main difference between the two is in the level of granularity.

When you associate an Elastic IP address with an instance, you are essentially assigning the IP address to the primary
network interface of that instance. This means that the Elastic IP address will remain associated with the instance even
if the instance is stopped and started again, or if its IP address changes for any other reason.

On the other hand, when you associate an Elastic IP address with a network interface, you are assigning the IP address
to a specific network interface within your VPC (Virtual Private Cloud) environment. This allows you to have greater
flexibility in how you manage your network resources, as you can move the Elastic IP address between instances or attach
it to a different network interface if needed.

In general, it's recommended to associate an Elastic IP address with an instance unless you have a specific need for
attaching it to a network interface. This is because associating an Elastic IP address with an instance provides a
simpler and more straightforward way of managing your IP addresses, and it also ensures that your instance will always
have a stable IP address that you can use to access it from the internet.

## When to associate an Elastic IP address with a network interface ?

If you want to ensure that your instance always has the same public IP address, even if it is terminated and replaced by
a new one, you can associate the Elastic IP address with the network interface instead of the instance.

When you associate an Elastic IP address with a network interface, it remains associated with that network interface
even if the instance is terminated and replaced by a new one. This means that when a new instance is launched to replace
the terminated one, it will automatically be associated with the Elastic IP address that was attached to the network
interface.

To associate an Elastic IP address with a network interface, you can follow these steps:

Allocate an Elastic IP address:

a. Navigate to the "Elastic IPs" page in the EC2 dashboard.

b. Click on the "Allocate new address" button to allocate a new Elastic IP address.

c. Select the Elastic IP address that you just allocated, and click on the "Actions" button.

d. Click on "Associate IP address" and select "Network interface" as the association type.

Create a network interface:

a. Navigate to the "Network Interfaces" page in the EC2 dashboard.

b. Click on the "Create network interface" button to create a new network interface.

c. Configure the network interface settings as required, including the subnet, security group, and any other options.

d. Select the Elastic IP address that you just associated, and attach the network interface to an existing or new
instance.

Once you have associated the Elastic IP address with the network interface, it will remain attached to that network
interface even if the instance is terminated and replaced by a new one. This ensures that your instance always has the
same public IP address, which can be useful if you need to maintain a stable IP address for your application or service.

### Resource

- ChatGPT3:

```
Act like a developer who is writing a blog post about EC2 AWS.
I want you to explain the following topic in a blog post format.
Consider that the audience of this blog post is a developer who is new to AWS and he/she wants to learn about EC2.

- What is a network interface in AWS?
```

- ```