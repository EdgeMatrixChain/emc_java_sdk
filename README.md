EMC Java SDK
==================================


emc_java_sdk is a lightweight Java library for integrating with clients (nodes) on the edge-matrix network:

This allows you to work with the [EdgeMatrix](https://www.edgematrix.pro/)
Decentralized Computing Network, without the additional overhead of having to write your own integration code for the
platform.


QuickStart
---------
The simplest way to start your journey with emc_java_sdk is to create a project.

#### Please head to the  [EdgeMatrix](https://www.edgematrix.pro/) for further instructions on using emc Java SDK.

Maven
-----

Java:

#### Depoly jar to your local maven repository

$ mvn install:install-file -Dfile=/local/path/edge-matrix-sdk-java-1.0-SNAPSHOT.jar -DgroupId=pro.edge-matrix
-DartifactId=edge-matrix-sdk-java -Dversion=1.0-SNAPSHOT -Dpackaging=jar

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

Sample code
-----

```java
public class YourEdgeCaller {
    private static EdgeWeb3j web3j = EdgeWeb3j.build(new HttpService("http://3.145.214.36:40012/"));

    public void sendRtcMessage() {
        EdgeService edgeService=new EdgeService();
        String s=edgeService.sendRtcMsg(
                web3j,
                ChainId.TEST_NET.getId(),
                SampleKeys.CREDENTIALS,
                RtcMsg.createRtcMsg(
                        "0x8eeb338239ada22d81ffb7adc995fe31a4d1dc2d701bc8a58fffe5b53e14281e",
                        "edge_chat",
                        "hello",
                        new Address("0x0").toString()));
        System.out.println(s);
    }    
}
```

License
------
Apache 2.0
