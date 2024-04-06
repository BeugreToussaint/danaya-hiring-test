package de.tuxbe.testtechnique.domain.service.daoimpl;

import de.tuxbe.testtechnique.domain.mapper.MapperImpl;
import de.tuxbe.testtechnique.domain.model.Personne;
import de.tuxbe.testtechnique.domain.repository.PersonneRepository;
import de.tuxbe.testtechnique.domain.service.dao.PersonneDAO;
import de.tuxbe.testtechnique.util.TuxUtility;
import de.tuxbe.testtechnique.web.dto.PersonneDto;
import de.tuxbe.testtechnique.web.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonneDAOImpl implements PersonneDAO {

    private final PersonneRepository personneRepository;
    private final MapperImpl mapperImpl;

    public PersonneDAOImpl(PersonneRepository personneRepository, MapperImpl mapperImpl) {
        this.personneRepository = personneRepository;
        this.mapperImpl = mapperImpl;
    }

    @Override
    public PersonneDto create(PersonneDto personneDto) {
        Personne personne = mapperImpl.toDomain(personneDto);
        Personne personneSave = this.personneRepository.save(personne);
        return mapperImpl.toDto(personneSave);
    }

    @Override
    public PersonneDto findByNumberIdentity(String numberIdentity) {
        Personne personne = this.personneRepository.findByNumIdentityUnique(numberIdentity).orElseThrow(() -> new ResourceNotFoundException("Personne avec N°" + numberIdentity + " est introuvable"));
        return mapperImpl.toDto(personne);
    }

    @Override
    public PersonneDto update(String numberIdentity, PersonneDto personneDto) {
        Personne personne = this.personneRepository.findByNumIdentityUnique(personneDto.numIdentityUnique()).orElseThrow(() -> new ResourceNotFoundException("Personne avec N°" + personneDto.numIdentityUnique() + " est introuvable"));
        BeanUtils.copyProperties(personneDto, personne);
        this.personneRepository.save(personne);
        return mapperImpl.toDto(personne);
    }

    @Override
    public List<PersonneDto> listPersonnes() {
        return this.personneRepository.findAll().stream().map(mapperImpl::toDto).collect(Collectors.toList());
    }


    @Override
    public Map<String, Double> verifyPersonne(PersonneDto personneDto) {

        List<Personne> personnes = this.personneRepository
                .findByBirthdayOrLastNameLikeOrFirstNameLikeOrNumIdentityUniqueLike(personneDto.birthday(), "%" + personneDto.lastName() + "%", "%" + personneDto.firstName() + "%", "%" + personneDto.numIdentityUnique() + "%");

        // Comparaison des scoring de similitude
        double jaroIdentity = !personnes.isEmpty() ? Collections.max(personnes.parallelStream().map(jaro -> TuxUtility.compareStrings(personneDto.numIdentityUnique(), jaro.getNumIdentityUnique())).toList()) : 1.0d;
        double jaroNom = !personnes.isEmpty() ? Collections.max(personnes.parallelStream().map(jaro -> TuxUtility.compareStrings(personneDto.firstName(), jaro.getFirstName())).toList()) : 1.0d;
        double jaroPrenom = !personnes.isEmpty() ? Collections.max(personnes.parallelStream().map(jaro -> TuxUtility.compareStrings(personneDto.lastName(), jaro.getLastName())).toList()) : 1.0d;
        double jaroBirthDay = !personnes.isEmpty() ? Collections.max(personnes.parallelStream().map(jaro -> TuxUtility.compareStrings(personneDto.birthday().toString(), jaro.getBirthday().toString())).toList()) : 1.0d;

        log.info("Jaro {}", new JaroWinklerDistance().apply(personneDto.numIdentityUnique(), !personnes.isEmpty() ? personnes.get(0).getNumIdentityUnique() : ""));

        Map<String, Double> scoring = new HashMap<>();
        scoring.put("numéro d'identité scoring", 100 - jaroIdentity * 100);
        scoring.put("Nom scoring", 100 - jaroNom * 100);
        scoring.put("Prenoms scoring ", 100 - jaroPrenom * 100);
        scoring.put("Date anniversaire scoring ", 100 - jaroBirthDay * 100);

        return scoring;
    }
}
