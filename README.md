# util
Core repository

This repository contains utility projects to support the build process and
basic utility bundles.


[ factory-project ] -> [ parent-project ] -> [ org.i3xx.util.basic ]


<b>org.i3xx.util.basic</b>

The project contains some fundamential interfaces and their implementations if necessary.

<b>parent-project</b>

The parent project contains a Maven pom file to set dependencies for testing and logging and the dependency to the basic 
utility bundle.

<b>factory-project</b>

The basic project contains a Maven pom file to set the distribution management and build plugins.

