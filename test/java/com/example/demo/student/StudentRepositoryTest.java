package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldIfStudentExistsEmail(){
        //given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila",
                email,
                Gender.FEMALE);
        underTest.save(student);

        //when
        boolean expected = underTest.selectExistsEmail(email);
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void itShouldIfStudentEmailDoesNotExists(){
        //given
        String email = "jamila@gmail.com";
        //when
        boolean expected = underTest.selectExistsEmail(email);
        //then
        assertThat(expected).isFalse();
    }
}
