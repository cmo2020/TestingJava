package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentService underTest;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp(){

        underTest = new StudentService(studentRepository);

    }
/*
   private AutoCloseable autoCloseable;
     autoCloseable = MockitoAnnotations.openMocks(this)
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
*/
    @Test
    void canGetAllStudents(){
        //when
        underTest.getAllStudents();

        //then
        verify(studentRepository).findAll();
    }


    @Test
    void willThrowWhenEmailIsTaken(){
        //given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE);

        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //when
        //then
        assertThatThrownBy(() ->underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());


    }
}