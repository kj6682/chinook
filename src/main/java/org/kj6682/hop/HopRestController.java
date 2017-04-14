package org.kj6682.hop;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luigi on 29/08/16.
 * <p>
 * As its names states clearly, this class is the RestController for the Hop application.
 * In case the hopster microservice should provide also other interfaces, by mvc and templates for instance,
 * we will provide the appropriated controller.
 * <p>
 * This class wraps the service and exposes it in a defined protocol to clients.
 * <p>
 * No business logic must be implemented in this class.
 * <p>
 * It is not necessary to expose this class as public, so keep it package private.
 */
@RestController
@RequestMapping("/api")
class HopRestController {

    private HopService hopService;

    HopRestController(HopService hopService) {
        this.hopService = hopService;
    }

    @GetMapping(value = "/hops/{id}")
    Hop get(@PathVariable Long id) {
        return hopService.findOne(id);

    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping(value = "/hops")
    List<Hop> find(@RequestParam(value = "search4me", required = false) String search4me,
                   Pageable pageable) {

        return hopService.find(search4me, pageable);

    }


    @PostMapping(value = "/hops")
    void create(@RequestBody Hop hop) {

        hopService.insertOne(hop.getTitle(), hop.getAuthor(), hop.getType(), hop.getLocation());

    }

    @PutMapping(value = "/hops/{id}")
    void update(@PathVariable Long id,
                @RequestParam(value = "title", defaultValue = "no title") String title,
                @RequestParam(value = "author", defaultValue = "no author") String author,
                @RequestParam(value = "type", defaultValue = "BOOK") String type,
                @RequestParam(value = "location", defaultValue = "nowhere") String location) {

        hopService.deleteOne(id);
        hopService.insertOne(title, author, type, location);
    }

    @DeleteMapping(value = "/hops/{id}")
    void delete(@PathVariable Long id) {
        hopService.deleteOne(id);
    }

}
