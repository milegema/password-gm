package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public interface WebClientFactory {

    WebClient create(WebConfiguration config);

}
