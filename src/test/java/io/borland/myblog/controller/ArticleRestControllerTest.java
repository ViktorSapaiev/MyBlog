package io.borland.myblog.controller;

import io.borland.myblog.MyblogApplication;
import io.borland.myblog.entity.Article;
import io.borland.myblog.entity.UserShort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyblogApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ArticleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private Article validArticle;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Before
    public void beforeFunction() {
        this.validArticle = new Article();
        validArticle.setId(3);
        validArticle.setDate(LocalDateTime.now());
        validArticle.setHeader("Header");
        validArticle.setText("Text..");
        UserShort author = new UserShort();
        author.setId(2);
        author.setUsername("Superman");
        validArticle.setAuthor(author);
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_create_valid_article_and_return_created_status() throws Exception {

        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("http://localhost/api/articles/3")))
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_not_allow_others_http_methods() throws Exception {
        mockMvc.perform(put("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_not_create_existing_article_and_return_conflict_status() throws Exception {
        validArticle.setHeader("Who am i?");
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user1@gmail.com")
    public void should_get_valid_article_with_ok_status() throws Exception {
        mockMvc.perform(get("/api/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.header", is("I'll be back")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_no_get_unknown_article_with_not_found_status() throws Exception {
        mockMvc.perform(get("/api/articles/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].logref", is("error")))
                .andExpect(jsonPath("$[0].message", containsString("could not find article with id: '3'")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_not_update_unknown_article_and_return_not_found_status() throws Exception {
        validArticle.setId(5);
        mockMvc.perform(put("/api/articles/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].logref", is("error")))
                .andExpect(jsonPath("$[0].message", containsString("could not find article with id: '5'")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_update_valid_article_and_return_ok_status() throws Exception {
        validArticle.setId(2);
        validArticle.setHeader("Hello world!!");
        mockMvc.perform(put("/api/articles/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.header", is("Hello world!!")))
                .andExpect(jsonPath("$.author.username", is("Superman")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_delete_existing_article_and_return_no_content_status() throws Exception {
        mockMvc.perform(delete("/api/articles/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_not_delete_unknown_article_and_return_not_found_status() throws Exception {
        mockMvc.perform(delete("/api/articles/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].logref", is("error")))
                .andExpect(jsonPath("$[0].message", containsString("could not find article with id: '5'")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_get_articles_list_for_user() throws Exception {
        mockMvc.perform(get("/api/articles?authorId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("user2@gmail.com")
    public void should_get_articles_list() throws Exception {
        mockMvc.perform(get("/api/articles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}