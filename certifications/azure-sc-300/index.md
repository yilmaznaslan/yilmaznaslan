# Preperation Notes

- Implement and manage user identities (20–25%)

- Implement authentication and access management (25–30%)

- Plan and implement workload identities (20–25%)

- Plan and automate identity governance (25–30%)

## Implement an identity management solution

- Configure and manage a Microsoft Entra tenant
- Create, configure, and manage Microsoft Entra identities
- Implement and manage identities for external users and tenants
- Implement and manage hybrid identity

### Configure and manage a Microsoft Entra tenant

- Configure and manage built-in and custom Microsoft Entra roles

- Recommend when to use administrative units

- Configure and manage administrative units

- Evaluate effective permissions for Microsoft Entra roles

- Configure and manage custom domains

- Configure Company branding settings

- Configure tenant properties, user settings, group settings, and device settings

- You can assign licences to all groups created in AD

#### Configure and manage built-in and custom Microsoft Entra roles

Helpdesk Administrator Role:

- can change passwords, invalidate refreseh tokens,
- create and manage support requests for azure and microsoft 365 services
- Monitor service health

* Administrative units
  - users, devices, groups(Security, Microsoft 365, )

### Administriavet roles

- Authentication administrator: Can access to view, set and reset authentication method information for any non-admin user.
- Helpdesk administrator: Can reset passwords for non-administrators and Helpdesk Administrators.
  License administrator
  Password administrator
- User administrator: Can manage all aspects of users and groups, including resetting passwords for limited admins. Can also assign licenses ?

- Adding a group to an administrative unit brings the group itself into the management scope of the administrative unit, but not the members of the group. In other words, an administrator scoped to the administrative unit can manage properties of the group, such as group name or membership, but they cannot manage properties of the users or devices within that group

### Groups

- Group-based licensing currently doesn't support groups that contain other groups (nested groups). If you apply a license to a nested group, only the immediate first-level user members of the group have the licenses applied.

### Implement and manage hybrid identity

Plan, design, and implement Microsoft Entra Connect
Manage Microsoft Entra Connect
Implement and manage password hash synchronization (PHS)
Implement and manage pass-through authentication (PTA)
Implement and manage seamless single sign-on (seamless SSO)
Implement and manage federation excluding manual AD FS deployments

- ? Troubleshoot synchronization errors
  Implement and manage Microsoft Entra Connect Health

## Implement authentication and access management

- Implement access management for Azure resources

## Implement Access Management for Apps

### Plan and design the integration of enterprise apps for SSO

- Only users can be assigned as owners to the enterprise apps.
- Both users and service principles can be the owners of app registrations.

## Implement app registration

- **Application Developer** role can create an app registration

## Plan and implement an identity governance strategy

- Plan and implement entitlement management in Microsoft Entra
- Plan, implement and manage acces reviews in Microsoft Entra
- Plan and implement privileged access
- Monitor identity acitivity by using logs, workbooks and reports
- Plan and implement Microsoft Entra Permission Management

### Entitlement management

### Plan, implement, and manage access reviews in Microsoft Entra

- Plan for access reviews

- Create and configure access reviews

- Monitor access review activity

- Manually respond to access review activity

Notes;

- You need Entra ID premium P2 license to crate reviews under Identity Governance

### Monitor identity activity by using logs, workbooks, and reports

- Design a strategy for monitoring Microsoft Entra

- Review and analyze sign-in, audit, and provisioning logs by using the Microsoft Entra admin center

- Configure diagnostic settings, including configuring destinations such as Log Analytics workspaces, storage accounts, and event hubs

- Monitor Microsoft Entra by using KQL queries in Log Analytics

- Analyze Microsoft Entra by using workbooks and reporting

- Monitor and improve the security posture by using Identity Secure Score

#### Identity Secure Score

To update the status of an improvement action, you need to have

- Security Administrator,
- Exchange Administrator, or
- SharePoint Administrator permissions.

To view the improvement action but not update, you need to have

- Helpdesk Administrator,
- User Administrator,
- Service Support Administrator,
- Security Reader,
- Security Operator, or Global Reader permissions.

* Managed entitiy can be assigned ot security groups only(Cloud)
* Azure AD can be assiedn tou Secuirty(assigend not dynmanic) & 365

## Notes

- Entra roles are assigned to a group, group can't bhave dynamic but only assigned membership type
- To create access reviews for Azure resources, you must be assigned to the Owner or the User Access Administrator role for the Azure resources. To create access reviews for Microsoft Entra roles, you must be assigned at least the Privileged Role Administrator role.
