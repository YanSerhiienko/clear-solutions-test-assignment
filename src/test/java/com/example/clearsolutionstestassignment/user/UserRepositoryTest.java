package com.example.clearsolutionstestassignment.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private User getUser() {
        return User.builder()
                .email("saul@mail.com")
                .firstName("Saul")
                .lastName("Goodman")
                .phoneNumber("505-842-5662")
                .birthDate(LocalDate.of(1960, 11, 12))
                .build();
    }

    @Test
    public void FindByBirthDateBetween_ReturnUsersList() {
        User user = getUser();
        userRepository.save(user);

        List<User> response1 = userRepository.findByBirthDateBetween(LocalDate.of(1940, 1, 1), LocalDate.of(1950, 1, 1));
        Assertions.assertThat(response1).isEmpty();

        List<User> response2 = userRepository.findByBirthDateBetween(LocalDate.of(1940, 1, 1), LocalDate.of(1960, 11, 12));
        Assertions.assertThat(response2).isNotEmpty();

        List<User> response3 = userRepository.findByBirthDateBetween(LocalDate.of(1940, 1, 1), LocalDate.of(1970, 11, 1));
        Assertions.assertThat(response3).isNotEmpty();

        List<User> response4 = userRepository.findByBirthDateBetween(LocalDate.of(1960, 11, 12), LocalDate.of(1970, 1, 1));
        Assertions.assertThat(response4).isNotEmpty();

        List<User> response5 = userRepository.findByBirthDateBetween(LocalDate.of(1970, 1, 1), LocalDate.of(1980, 1, 1));
        Assertions.assertThat(response5).isEmpty();
    }

    @Test
    public void Save_ReturnSavedUser() {
        User user1 = getUser();

        User user2 = User.builder()
                .email("wexler@mail.com")
                .firstName("Kim")
                .lastName("Wexler")
                .birthDate(LocalDate.of(1968, 2, 13))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertThat(userList).isNotEmpty();
        Assertions.assertThat(userList).hasSize(2);
    }

    @Test
    public void FindAll_ReturnMoreThanOneUser() {
        User user = getUser();
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void FindById_ReturnUser() {
        User user = getUser();
        User savedUser = userRepository.save(user);

        User userReturn = userRepository.findById(savedUser.getId()).get();

        Assertions.assertThat(userReturn).isNotNull();
        Assertions.assertThat(userReturn).isEqualTo(savedUser);
    }

    @Test
    public void DeleteById_ReturnUserIsEmpty() {
        User user = getUser();
        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());
        Optional<User> userReturn = userRepository.findById(savedUser.getId());

        Assertions.assertThat(userReturn).isEmpty();
    }

    @Test
    public void UpdateUser_ReturnUpdatedUser() {
        User user = getUser();
        User savedUser = userRepository.save(user);

        User userReturn = userRepository.findById(savedUser.getId()).get();

        userReturn.setEmail("mcgill@mail.com");

        User updatedUser = userRepository.save(userReturn);

        Assertions.assertThat(updatedUser.getEmail()).isNotNull();
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("mcgill@mail.com");
    }

    @Test
    public void CountByEmail_ReturnOne() {
        User user = getUser();
        userRepository.save(user);

        int count = userRepository.countByEmail(user.getEmail());

        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void CountByEmail_ReturnZero() {
        int count = userRepository.countByEmail(getUser().getEmail());

        Assertions.assertThat(count).isEqualTo(0);
    }

    @Test
    public void UpdatePhoneNumber_ReturnUpdatedUser() {
        User user = getUser();
        User saved = userRepository.save(user);

        userRepository.updatePhoneNumber("11111", saved.getId());

        User updated = userRepository.findById(saved.getId()).get();

        Assertions.assertThat(user.getPhoneNumber()).isNotEqualTo(updated);
        Assertions.assertThat(updated.getPhoneNumber()).isEqualTo("11111");
    }
}