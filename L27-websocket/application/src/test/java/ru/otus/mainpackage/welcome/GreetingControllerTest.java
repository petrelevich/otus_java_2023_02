package ru.otus.mainpackage.welcome;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sayHelloTest() throws Exception {
        //given
        var name = "Mr.Test";

        //when
        var result = mockMvc.perform(get(String.format("/api/v1/hello?name=%s", name)))
                .andReturn()
                .getResponse();

        //then
        var expectedResult = "{\"Mr.Test\":\"Hello, Mr.Test\"}";
        assertThat(result.getContentAsString()).isEqualTo(expectedResult);
    }

    @Test
    void sayHelloTestMock() throws Exception {
        //given
        var name = "Mr.Test";

        //when
        var result = mockMvc.perform(get(String.format("/api/v1/hello/%s", name)))
                .andReturn()
                .getResponse();

        //then
        var expectedResult = "{\"Mr.Test\":\"Hello, Mr.Test\"}";
        assertThat(result.getContentAsString()).isEqualTo(expectedResult);
    }
}