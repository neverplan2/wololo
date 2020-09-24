package org.neverplan2.wololo.controller;

import org.neverplan2.wololo.api.endpoint.WolEndpoint;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WolController implements WolEndpoint {

    @Override
    public void wake(String macAddress) {

    }
}
