## FAQ

### Can I attach one network interface to multiple instances

No, in Amazon Web Services (AWS) EC2, you cannot attach one network interface to multiple instances at the same time.
Each network interface can be attached to only one instance at a time.

However, you can move a network interface from one instance to another by detaching it from the current instance and
attaching it to the new instance. This can be done using the EC2 console, CLI or SDKs.