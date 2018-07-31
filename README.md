# *Quasi*<br/>Fibers, Channels and Actors for the JVM

This project is forked from Quasar: http://docs.paralleluniverse.co/quasar in order to:
- Drop JDK7 support
- Support JDK from 8 to 11
- Remove all non-core modules

## Getting started

Add the following Maven/Gradle dependencies:

| Feature          | Artifact
|------------------|------------------
| Core (required)  | `com.github.fromage.quasi:quasi-core:0.7.10`

Or, build from sources by running:

```
gradle install
```

## Usage

* [Documentation](http://docs.paralleluniverse.co/quasar/)
* [Javadoc](http://docs.paralleluniverse.co/quasar/javadoc)

You can also study the examples [here](https://github.com/puniverse/quasar/tree/master/quasar-actors/src/test/java/com/github/fromage/quasi/actors).

You can also read the introductory [blog post](http://blog.paralleluniverse.co/post/49445260575/quasar-pulsar).

When running code that uses Quasi, the instrumentation agent must be run by adding this to the `java` command line:

```
-javaagent:path-to-quasi-jar.jar
```

## Related Projects

* [Pulsar](https://github.com/puniverse/pulsar) is Quasar's extra-cool Clojure API
* [Comsat](https://github.com/puniverse/comsat) integrates Quasar with the JVM's web APIs

## Getting help

You can open a [new GitHub issue](https://github.com/fromage/quasi/issues/new)

## Contributions (including Pull Requests)

Contributions welcome

## License

Quasi is free software published under the following license:

```
Copyright (c) 2013-2017, Parallel Universe Software Co. All rights reserved.

This program and the accompanying materials are dual-licensed under
either the terms of the Eclipse Public License v1.0 as published by
the Eclipse Foundation

  or (per the licensee's choosing)

under the terms of the GNU Lesser General Public License version 3.0
as published by the Free Software Foundation.
```
