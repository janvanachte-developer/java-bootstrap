package io.vanachte.jan.bootstrap.address;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class AddressRestController {

    private final AddressService service;

    @Inject
    public AddressRestController(AddressService service) {
        this.service = service;
    }

    @RequestMapping("/addresses") // @GetMapping
    public List<Address> findAll() {
        return service.findAll();
    }
}
