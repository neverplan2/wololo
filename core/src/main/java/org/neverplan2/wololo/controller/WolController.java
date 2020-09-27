package org.neverplan2.wololo.controller;

import lombok.AllArgsConstructor;
import org.neverplan2.wololo.api.endpoint.WolEndpoint;
import org.neverplan2.wololo.service.WolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WolController implements WolEndpoint {

    private final WolService wolService;

    @Override
    public void wake(String macAddress) {
        wolService.wake(macAddress);
    }
}
