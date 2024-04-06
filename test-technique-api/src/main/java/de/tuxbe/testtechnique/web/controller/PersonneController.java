package de.tuxbe.testtechnique.web.controller;

import de.tuxbe.testtechnique.domain.service.daoimpl.PersonneDAOImpl;
import de.tuxbe.testtechnique.web.dto.PersonneDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/personnes")
public class PersonneController {

    private final PersonneDAOImpl personneDAO;

    public PersonneController(PersonneDAOImpl personneDAO) {
        this.personneDAO = personneDAO;
    }

    @GetMapping
    public ResponseEntity<List<PersonneDto>> getPersonnes() {
        return new ResponseEntity<>(personneDAO.listPersonnes(), HttpStatus.OK);
    }

    @GetMapping("/{numberIdentity}")
    public ResponseEntity<PersonneDto> getPersonneByNumberIdentity(@PathVariable String numberIdentity) {
        return new ResponseEntity<>(personneDAO.findByNumberIdentity(numberIdentity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonneDto> create(@RequestBody PersonneDto personneDto) {
        return new ResponseEntity<>(personneDAO.create(personneDto), HttpStatus.CREATED);
    }

    @PutMapping("/{numberIdentity}")
    public ResponseEntity<PersonneDto> update(@PathVariable String numberIdentity, @RequestBody PersonneDto personneDto) {
        return new ResponseEntity<>(personneDAO.update(numberIdentity, personneDto), HttpStatus.OK);
    }

    @GetMapping("/scoring")
    public ResponseEntity<Map<String, Double>> verifyIdentity(PersonneDto personneDto) {
        return new ResponseEntity<>(personneDAO.verifyPersonne(personneDto), HttpStatus.OK);
    }


}
