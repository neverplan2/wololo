package org.neverplan2.wololo.api.endpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.neverplan2.wololo.api.model.NetAddress;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public interface NetAddressEndpoint {

    @PostMapping("/create")
    @ApiOperation(value = "Create new network address", response = NetAddress.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Address already exists."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    NetAddress createNetAddress(@RequestParam String mac, @RequestParam(required = false) String comment);

    @PostMapping("/addlist")
    @ApiOperation(value = "Add an entire list of network addresses", response = NetAddress.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Address already exists."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    NetAddress addNetAddressList(@RequestBody List<NetAddress> netAddressList);

    @PostMapping("/update")
    @ApiOperation(value = "Update a network address", response = NetAddress.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    NetAddress updateNetAddress(@RequestBody NetAddress netAddress);

    @PostMapping("/delete")
    @ApiOperation(value = "Delete a network address", response = NetAddress.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    void deleteNetAddress(final NetAddress netAddress);

    @PostMapping("/all")
    @ApiOperation(value = "Return all persisted network addresses.", response = List.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    List<NetAddress> getNetAddressList();

    @PostMapping("/deletebatch")
    @ApiOperation(value = "Delete a list of network addresses")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    void deleteNetAddressList(final List<NetAddress> netAddressList);
}
