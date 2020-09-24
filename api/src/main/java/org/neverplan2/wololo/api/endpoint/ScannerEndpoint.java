package org.neverplan2.wololo.api.endpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.api.model.NicAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/scanner")
public interface ScannerEndpoint {

    @GetMapping("/nic")
    @ApiOperation(value = "Returns a list of NIC adapters", response = List.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    List<NicAdapter> scanNicAdapters();

    @GetMapping("/network")
    @ApiOperation(value = "Delete a network address", response = List.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    List<NetAddress> scanNetwork(@RequestParam String address, @RequestParam String netmask);

}
