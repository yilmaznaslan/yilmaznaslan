# IAM Identities

- IAM User:
- IAM Usergroup
- IAM Role

## IAM User

An IAM user is an AWS identity with permanent access credentials that are associated with a specific user, typically an
individual, who is granted permissions to access AWS resources. IAM users can sign in to AWS console, access the AWS
management console, and perform actions allowed by their IAM policies.

## What is the best practice to grant access a java application to access AWS service ? IAM user or role

IAM Roles and IAM Users are two different entities within IAM that serve distinct purposes.

An IAM user is an AWS identity with permanent access credentials that are associated with a specific user, typically an
individual, who is granted permissions to access AWS resources. IAM users can sign in to AWS console, access the AWS
management console, and perform actions allowed by their IAM policies.

On the other hand, IAM Roles are temporary permissions that can be assumed by users, applications, or services to access
AWS resources. Instead of a user having their own credentials, they can assume a role that has been configured with
specific permissions to perform certain actions on the resources they need access to. This approach allows for greater
security and control because the user is not permanently granted permissions but only has access to the resources when
they assume the role.

In summary, IAM users are permanent AWS identities associated with specific users, while IAM roles are temporary
permissions that can be assumed by users, applications, or services to access AWS resources for a limited period.