# Hibernate - Architecture

Hibernate has a layered architecture which helps the user to operate without having to know the underlying APIs.
Hibernate makes use of the database and configuration data to provide persistence services (and persistent objects) to
the application.

Following is a very high level view of the Hibernate Application Architecture.

![](https://www.tutorialspoint.com/hibernate/images/hibernate_high_level.jpg)

## Configuration Object

## SessionFactory

Configuration object is used to create a `SessionFactory` object which in turn configures Hibernate for the application
using the supplied configuration file and allows for a Session object to be instantiated. The SessionFactory is a thread
safe object and used by all the threads of an application.

The SessionFactory is a heavyweight object; it is usually **created during application start up and kept for later use.
**
You would need one SessionFactory object per database using a separate configuration file. So, if you are using multiple
databases, then you would have to create multiple SessionFactory objects.

## Session

A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be
instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved through a
Session object.

The session objects should not be kept open for a long time because they are not usually thread safe and they should be
created and destroyed them as needed. The main function of the Session is to offer, create, read, and delete operations
for instances of mapped entity classes.