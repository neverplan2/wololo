package org.neverplan2.wololo.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/wol")
public interface WolEndpoint {

    void wake(String macAddress);

}
