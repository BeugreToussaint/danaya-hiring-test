package de.tuxbe.testtechnique.domain.service.dao;

import de.tuxbe.testtechnique.web.dto.PersonneDto;

import java.util.List;
import java.util.Map;

public interface PersonneDAO {
    PersonneDto create(PersonneDto personneDto);
    PersonneDto findByNumberIdentity(String numberIdentity);
    PersonneDto update(String numberIdentity, PersonneDto personneDto);
    List<PersonneDto> listPersonnes();
    Map<String, Double> verifyPersonne(PersonneDto personneDto);

}
