package com.bookexchange.utils;

import com.bookexchange.dto.Book;
import com.bookexchange.dto.BookCategory;
import com.bookexchange.dto.User;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Created by sheke on 11/14/2015.
 */
public class BookJSONSerializerTest {

    public static final String DUMMY_TITLE = "dummy_title";
    public static final String DUMMY_IMG_URL = "dummy_img_url";
    public static final String DUMMY_USERNAME = "dummy-username";
    public static final String DUMMY_CATEGORY_NAME_1 = "dummy-category-name-1";
    public static final String DUMMY_CATEGORY_NAME_2 = "dummy-category-name-2";
    public static final String DUMMY_USER_AVATAR_URL = "ummy-user-avatar-url";
    BookJSONSerializer testObj = new BookJSONSerializer();
    JsonGenerator jsonGeneratorMock;

    @Before
    public void setUp(){
        jsonGeneratorMock = mock(JsonGenerator.class);
    }

    @Test
    public void serialize() throws IOException {
        Book bookToTestWith = new Book();
        LocalDateTime dateOfPublish = LocalDateTime.now();
        bookToTestWith.setDatePosted(dateOfPublish);
        bookToTestWith.setTitle(DUMMY_TITLE);
        bookToTestWith.setImgUrl(DUMMY_IMG_URL);

        User bookOwner = new User();
        bookOwner.setUsername(DUMMY_USERNAME);
        bookOwner.setAvatarUrl(DUMMY_USER_AVATAR_URL);
        Set<BookCategory> categoriesInterestedIn = new HashSet<>();

        BookCategory category1 = new BookCategory();
        category1.setCategoryName(DUMMY_CATEGORY_NAME_1);

        BookCategory category2 = new BookCategory();
        category2.setCategoryName(DUMMY_CATEGORY_NAME_2);

        categoriesInterestedIn.addAll(Arrays.asList(category1,category2));
        bookOwner.setCategoriesInterestedIn(categoriesInterestedIn);

        bookToTestWith.setCategory(category1);
        bookToTestWith.setPostedBy(bookOwner);

        testObj.serialize(bookToTestWith,jsonGeneratorMock,null);

        Mockito.verify(jsonGeneratorMock).writeStringField("title", DUMMY_TITLE);
        Mockito.verify(jsonGeneratorMock).writeStringField("imgUrl",DUMMY_IMG_URL);
        Mockito.verify(jsonGeneratorMock).writeObjectField("datePosted", dateOfPublish);
        Mockito.verify(jsonGeneratorMock).writeStringField("category", DUMMY_CATEGORY_NAME_1);

        Mockito.verify(jsonGeneratorMock).writeStringField("ownedBy",DUMMY_USERNAME);
        Mockito.verify(jsonGeneratorMock).writeStringField("ownerAvatar", DUMMY_USER_AVATAR_URL);
        Mockito.verify(jsonGeneratorMock).writeFieldName("ownerCategoriesOfInterest");
        Mockito.verify(jsonGeneratorMock).writeString(DUMMY_CATEGORY_NAME_1);
        Mockito.verify(jsonGeneratorMock).writeString(DUMMY_CATEGORY_NAME_2);

    }
}
