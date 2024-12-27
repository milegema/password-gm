package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.network.web.DefaultWebClientFactory;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.network.web.WebClientFactory;
import com.bitwormhole.passwordgm.network.web.WebConfiguration;
import com.bitwormhole.passwordgm.network.web.WebRequest;
import com.bitwormhole.passwordgm.network.web.WebResponse;
import com.bitwormhole.passwordgm.network.web.WebStatus;

import org.junit.Test;

import java.io.IOException;

public class WebClientTest {

    @Test
    public void useWebClient() throws IOException {

        WebConfiguration config = new WebConfiguration();
        WebClientFactory factory = new DefaultWebClientFactory();
        WebClient client = factory.create(config);

        WebRequest req = new WebRequest();
        req.setUrl("https://gitee.com/bitwormhole/cloud-aliyun-api");

        WebResponse resp = client.execute(req);


        WebStatus status = resp.getStatus();
        System.out.println(status);
    }
}
