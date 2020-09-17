package org.neverplan2.wololo.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.neverplan2.wololo.api.dto.Colours;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WololoColourConverter {
    private static final String MESSAGE = "Wololo: ";
    private static final String DEFAULT_MESSAGE = "Wololo: failed";

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/test/convert/{colour}")
    @ApiOperation(value = "This is just a test", response = String.class)
    public String convertColor(@PathVariable @ApiParam(value = "Get a colour converted (ex. red, blue, green") Colours colour) {
        switch (colour) {
            case RED: return MESSAGE + Colours.BLUE.name();
            case BLUE: return MESSAGE + Colours.GREEN.name();
            case GREEN: return MESSAGE + Colours.RED.name();
            default:
                return DEFAULT_MESSAGE;
        }
    }
}
