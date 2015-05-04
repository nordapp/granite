# util
Utility bundles. 

This repository contains utility projects to support the build process and basic functionality.


[ factory-project ] -> [ parent-project ] -> [ org.i3xx.util.basic ]

<b>org.i3xx.util.general.setup</b>

An OSGi bundle, that provides a service with the three basic fields describing a mandator. The fields are the id, the title and the root directory. The properties of the service are set by a blueprint configuration. The service is used by STEP as an abstract configuration.

<b>org.i3xx.util.platform</b>

A service to provide platform dependent code and settings. The interface 'Platform' of the project 'org.i3xx.util.basic' is implemented. Actually there is an implementation for Apache Karaf and the property 'server.home'.

<b>org.i3xx.util.store</b>

The utility implements a file based store with a garbage collector and an id-to-file mapping. Any implementation should use the interfaces only, because there are other solutions available in the net. Feel free to write an adapter if you need one.

<b>org.i3xx.util.symbol</b>

This is an OSGi service that provides a symbol to integer mapping. A symbol means something like a word that represents an idea. But Integers are much better to process in some cases.

<b>org.i3xx.util.whiteboard</b>

The whiteboard bundle is used to avoid any dependency between bundles. It uses a combination of the whiteboard pattern with annotation and java reflection.

<b>org.i3xx.util.basic</b>

The project contains some fundamential interfaces and their implementations if necessary.

<b>org.i3xx.test.workspace</b>

The project reads the system property 'workspace.home' to get the project base directory for the maven test run in the eclipse SDK. This property must be set, otherwise the tests fail.

<b>parent-project</b>

The parent project contains a Maven pom file to set dependencies for testing and logging and the dependency to the basic utility bundle.

<b>factory-project</b>

The basic project contains a Maven pom file to set the distribution management and build plugins.

