**Mediscreen – Abernathy Clinic**
=================================
A powerful application for patients management
----------------------------------------------

This repository contains the source code of the project.

This corresponds to the _OpenClassroom_ Project entitle "Build a microservice application for your client", the last project of the _JAVA application developer_ training.

It has been written by Jean-Luc METZ ([luc.metz@icloud.com]()) between July and September 2023 using Angular for the UI, and Spring Boot for the backend.

This exact application is currently deployed and hosted here for you to test: http://syngleton.co/mediscreen

---
Green Code
============
A Philosophy of Sustainability
---------------------------------

'Green code' is a way to write code in a more environmentally friendly way.

Its practice rely on some key points:

1. Energy efficiency (minimising the energy consumption of the software)
2. Reducing carbon footprint (optimising algorithms to reduce unnecessary computations)
3. Resource efficiency (reducing network traffic, minimizing memory usage and I/O)
4. Monitoring and measurement (implementing tools to monitor the energy consumption of the software)
5. Long-Term-Sustainability (remaining eco-friendly and efficient as the application evolves)

We at Mediscreen are deeply concerned with environmental friendly-ness as a keystone of a sustainable business development.

Whereas some of these points may seem irrelevant at the early stages of a project, it is important to keep them in mind from the get-go and leading the way with best practices where possible.

As a result, here are some measures we took when designing the first features of this application :

**Microservice architecture**

Microservices allow for efficient load balancing as the application scales, saving computational resources and avoiding the use of permanent hardware infrastructures that are too big when network traffic is low.
In addition, they help maintain the software and make the code less bloated; they also help with accessing different databases at different times and thus enable application-scale 'lazy loading' strategies.
For example, in our code the NoSql database is not being accessed as a user browses the list of patients unless it accesses a specific one.

**Cleaner Code**

A clean code is not only easier to maintain, but it also reduces the amount of lines that are computed for a same result.
As an example, our implementation of the algorithm that calculates the risk for diabetes avoids using too many if/else statements, and 'returns' (stops) as soon as it reaches the minimum effort to know for sure one patient is at one given diabetes risk.
This is small-scale example, but every small piece of code matters as it accumulates.

Similarly, the UI is implementing the Material Design concepts, minimizing clutter and unnecessary visual effects that can take a heavy toll on energy consumption and performances.
Instead, the graphic design simple (but not simplistic) concepts that puts every component where it needs to be and gets rid of the noise.

**Lazy loading**

These must be used when possible as they are key to reduce computational overhead and useless I/Os.
In our case, we used the Spring MongoDB feature of creating an interface for a 'light object' to be fetched so only partial content is retrieved from the database and sent to the server (the patient notes without the content).
When the actual content is needed, then another call is made.

Another example is leveraging Spring JPA implementations of explicit function names for 'repository' functions accessing the SQL database when possible, and avoiding 'anti-patterns' like performing 'findAll' calls and them filter them for relevant data.

**Up-to-date tech stack**

Using proven, but relatively up-to-date industry standards is important to prevent the formation of hard to maintain 'legacy' code, and obsolete standards.
For example, the UI has been developed with Angular CLI version 16 and its 'standalone components', which can be leveraged to enable single-component loading in a very easily when the app is ready to scale.

These are just some examples of the way we implemented the concept of Green Code. Surely improvements can be made.
But we believe a first step in the right direction is worth thousands of wishful thoughts.


