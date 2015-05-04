# util
Utilities. 

This repository contains utility projects to support the build process and
basic utility bundles.


[ factory-project ] -> [ parent-project ] -> [ org.i3xx.util.basic ]


<b>org.i3xx.util.basic</b>

The project contains some fundamential interfaces and their implementations if necessary.

<b>org.i3xx.test.workspace</b>

The project reads the system property 'workspace.home' to get the project base directory for the maven test run in the eclipse SDK. This property must be set, otherwise the tests fail.

<b>parent-project</b>

The parent project contains a Maven pom file to set dependencies for testing and logging and the dependency to the basic 
utility bundle.

<b>factory-project</b>

The basic project contains a Maven pom file to set the distribution management and build plugins.

