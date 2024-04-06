package de.tuxbe.testtechnique.domain.repository;

import de.tuxbe.testtechnique.domain.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonneRepository extends JpaRepository<Personne, UUID> {
    Optional<Personne> findByNumIdentityUnique(String uniqueNumberIdentity);
    List<Personne> findByNumIdentityUniqueLike(String uniqueNumberIdentity);
    List<Personne> findByFirstNameLike(String firstName);
    List<Personne> findByLastNameLike(String lastName);
    List<Personne> findByBirthday(LocalDate birthDay);
    List<Personne> findByBirthdayOrLastNameLikeOrFirstNameLikeOrNumIdentityUniqueLike(LocalDate birthDay, String lastName, String firstName, String uniqueNumberIdentity);
}
