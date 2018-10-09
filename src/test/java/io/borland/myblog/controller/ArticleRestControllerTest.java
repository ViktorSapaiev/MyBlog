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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
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
        System.out.println("Before Function");
    }

    @Test
    public void should_create_valid_article_and_return_created_status() throws Exception {
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("http://localhost/api/articles/3")))
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

/*    @Test
    public void should_not_create_invalid_content_article_and_return_bad_request_status() throws Exception {
        Article article = new Article();
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(article)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }*/

    @Test
    public void should_not_allow_others_http_methods() throws Exception {
        mockMvc.perform(put("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void should_not_create_existing_book_and_return_conflict_status() throws Exception {
        validArticle.setHeader("Who am i?");
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(validArticle)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

}