package ru.siblion.main.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.siblion.main.MyApplication;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyApplication.class)
@WebAppConfiguration
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
                        containsString("Welcome to...")));
    }
}


/**
 * Работало, пока не была подключена БД
 * @WebMvcTest - подгружает только контроллеры, игнорируя сервисы и репозитории
 *
 * @RunWith(SpringRunner.class)
 * @WebMvcTest()
 * public class HomeControllerTest {
 *
 *     @Autowired
 *     private MockMvc mockMvc;
 *
 *     @Test
 *     public void testHomePage() throws Exception {
 *         mockMvc.perform(get("/"))
 *                 .andDo(print())
 *                 .andExpect(status().isOk())
 *                 .andExpect(view().name("home"))
 *                 .andExpect(content().string(
 *                         containsString("Welcome to...")));
 *     }
 * }
 */