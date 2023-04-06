EMC Java SDK
==================================


emc_java_sdk is a lightweight Java library for integrating with
clients (nodes) on the edge-matrix network:

This allows you to work with the [EdgeMatrix](https://www.edgematrix.pro/)
Decentralized Computing Network, without the additional overhead of having to write your own
integration code for the platform.


QuickStart
---------
The simplest way to start your journey with emc_java_sdk is to create a project.


#### Please head to the  [EdgeMatrix](https://www.edgematrix.pro/) for further instructions on using emc Java SDK.

Maven
-----

Java:

#### Depoly jar to your local maven repository

$ mvn install:install-file -Dfile=/local/path/edge-matrix-sdk-java-1.0-SNAPSHOT.jar -DgroupId=pro.edge-matrix -DartifactId=edge-matrix-sdk-java -Dversion=1.0-SNAPSHOT -Dpackaging=jar

```xml
<dependency>
    <groupId>pro.edge-matrix</groupId>
    <artifactId>edge-matrix-sdk-java</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```

```xml
<dependency>
    <groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>4.9.7</version>
</dependency>
```

License
------
Apache 2.0
