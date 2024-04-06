package de.tuxbe.testtechnique.domain.mapper;

import de.tuxbe.testtechnique.domain.model.Personne;
import de.tuxbe.testtechnique.web.dto.PersonneDto;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements MapperGeneric<Personne, PersonneDto> {

    @Override
    public Personne toDomain(PersonneDto personneDto) {
        return new Personne(null, personneDto.numIdentityUnique(), personneDto.firstName(), personneDto.lastName(), personneDto.birthday());
    }

    @Override
    public PersonneDto toDto(Personne personne) {
        return new PersonneDto(personne.getNumIdentityUnique(), personne.getFirstName(), personne.getLastName(), personne.getBirthday());
    }

}
