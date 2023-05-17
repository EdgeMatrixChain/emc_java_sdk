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


#### Depoly jar to your local maven repository

$ mvn install:install-file -Dfile=/local/path/edge-matrix-sdk-java-1.1-SNAPSHOT.jar -DgroupId=pro.edge-matrix
-DartifactId=edge-matrix-sdk-java -Dversion=1.1-SNAPSHOT -Dpackaging=jar

```xml

<dependency>
    <groupId>pro.edge-matrix</groupId>
    <artifactId>edge-matrix-sdk-java</artifactId>
    <version>1.1-SNAPSHOT</version>
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
    private static EdgeWeb3j web3j = EdgeWeb3j.build(new HttpService("https://oregon.edgematrix.xyz"));

    // A edge-matrix node that is running Stable Diffusion service for testing purpose.
    private static String TEST_PEERID = "16Uiu2HAm14xAsnJHDqnQNQ2Qqo1SapdRk9j8mBKY6mghVDP9B9u5";

    public void sendRtcMessage() {
        EdgeService edgeService = new EdgeService();
        String s = edgeService.sendRtcMsg(
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

    public void callStableDeffusionEdgeApi() {
        EdgeService edgeService = new EdgeService();
        String peerId = TEST_PEERID;
        String apiHttpMethod = "POST";
        String apiPath = "/sdapi/v1/txt2img";
        String apiData = "{\n" +
                "      \"enable_hr\": false,\n" +
                "      \"denoising_strength\": 0,\n" +
                "      \"firstphase_width\": 0,\n" +
                "      \"firstphase_height\": 0,\n" +
                "      \"hr_scale\": 2,\n" +
                "      \"hr_upscaler\": \"\",\n" +
                "      \"hr_second_pass_steps\": 0,\n" +
                "      \"hr_resize_x\": 0,\n" +
                "      \"hr_resize_y\": 0,\n" +
                "      \"prompt\": \"white cat and dog\",\n" +
                "      \"styles\": [\n" +
                "        \"\"\n" +
                "      ],\n" +
                "      \"seed\": -1,\n" +
                "      \"subseed\": -1,\n" +
                "      \"subseed_strength\": 0,\n" +
                "      \"seed_resize_from_h\": -1,\n" +
                "      \"seed_resize_from_w\": -1,\n" +
                "      \"sampler_name\": \"\",\n" +
                "      \"batch_size\": 1,\n" +
                "      \"n_iter\": 1,\n" +
                "      \"steps\": 50,\n" +
                "      \"cfg_scale\": 7,\n" +
                "      \"width\": 512,\n" +
                "      \"height\": 512,\n" +
                "      \"restore_faces\": false,\n" +
                "      \"tiling\": false,\n" +
                "      \"do_not_save_samples\": false,\n" +
                "      \"do_not_save_grid\": false,\n" +
                "      \"negative_prompt\": \"\",\n" +
                "      \"eta\": 0,\n" +
                "      \"s_churn\": 0,\n" +
                "      \"s_tmax\": 0,\n" +
                "      \"s_tmin\": 0,\n" +
                "      \"s_noise\": 1,\n" +
                "      \"override_settings\": {},\n" +
                "      \"override_settings_restore_afterwards\": true,\n" +
                "      \"script_args\": [],\n" +
                "      \"sampler_index\": \"Euler\",\n" +
                "      \"script_name\": \"\",\n" +
                "      \"send_images\": true,\n" +
                "      \"save_images\": false,\n" +
                "      \"alwayson_scripts\": {}\n" +
                "    }";
        String s = edgeService.callEdgeApi(
                web3j,
                ChainId.TEST_NET.getId(),
                edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS),
                SampleKeys.CREDENTIALS,
                peerId,
                apiHttpMethod,
                apiPath,
                apiData);
        if (isValidJSON(s)) {
            ObjectMapper objectMapper = new ObjectMapper();
            EdgeCallResult edgeCallResult = objectMapper.readValue(s, EdgeCallResult.class);
            System.out.println("telegram_hash: " + edgeCallResult.getTelegram_hash());
            System.out.println("response: " + new String(Base64.getDecoder().decode(edgeCallResult.getResponse())));
        } else {
            System.out.println("invalid json");
        }
    }
}
```

License
------
Apache 2.0
