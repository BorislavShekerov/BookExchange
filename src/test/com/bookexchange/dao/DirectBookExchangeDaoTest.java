package com.bookexchange.dao;

import com.bookexchange.dto.*;
import com.bookexchange.graph.Graph;
import com.bookexchange.graph.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by sheke on 11/13/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class DirectBookExchangeDaoTest {

    public static final String DUMMY_EMAIL_1 = "FirstEmail";
    public static final String DUMMY_EMAIL_2 = "SecondEmail";
    public static final String DUMMY_EMAIL_3 = "SecondEmail";
    public static final String DUMMY_BOOK_TITLE_1 = "Book Title 1";
    public static final String DUMMY_BOOK_TITLE_2 = "Book Title 2";
    public static final String DUMMY_BOOK_TITLE_3 = "Book Title 3";
    public static final String DUMMY_BOOK_TITLE_4 = "Book Title 4";
    public static final String DUMMY_CATEGORY_1 = "Category 1";
    public static final String DUMMY_CATEGORY_2 = "Category 2";

    @Autowired
    protected BookDao bookDao = null;
    @Autowired
    protected UserDao userDao = null;
    @Autowired
    protected CategoryDao categoryDao = null;
    @Autowired
    protected BookExchangeDao bookExchangeDao = null;

    @Test
    public void addBookExchange(){
        User user1 = new User();
        user1.setEmail(DUMMY_EMAIL_1);

        User user2 = new User();
        user2.setEmail(DUMMY_EMAIL_2);

        Book user1Book1 = new Book();
        user1Book1.setTitle(DUMMY_BOOK_TITLE_1);
        user1Book1.setPostedBy(user1);

        Book user2Book = new Book();
        user2Book.setTitle(DUMMY_BOOK_TITLE_2);
        user2Book.setPostedBy(user2);

        BookCategory category1 = new BookCategory();
        category1.setCategoryName(DUMMY_CATEGORY_1);

        BookCategory category2 = new BookCategory();
        category2.setCategoryName(DUMMY_CATEGORY_2);

        user1Book1.setCategory(category1);
        user2Book.setCategory(category2);

        userDao.saveUser(user1);
        userDao.saveUser(user2);
        categoryDao.addCategory(category1);
        categoryDao.addCategory(category2);
        bookDao.postBookOnExchange(user1Book1);
        bookDao.postBookOnExchange(user2Book);

        DirectBookExchange directBookExchange = new DirectBookExchange();
        directBookExchange.setBookRequested(user1Book1);
        directBookExchange.setBookRequested(user2Book);

       user1.addBookExchange(directBookExchange);
        user2.addBookExchange(directBookExchange);

        userDao.saveUser(user1);
        userDao.saveUser(user2);

        user1 = userDao.findUserByEmail(DUMMY_EMAIL_1).get();
        user2 = userDao.findUserByEmail(DUMMY_EMAIL_2).get();

        assertEquals("Exchange Added To User 1", directBookExchange, user1.getCurrentExchanges().iterator().next());
        assertEquals("Exchange Added To User 2", directBookExchange, user2.getCurrentExchanges().iterator().next());
    }

    @Test
    public void getExchangesForUserEmail(){
        BookCategory category1 = new BookCategory.BookCategoryBuilder().setCategoryName(DUMMY_CATEGORY_1).buildBookCategory();
        BookCategory category2 = new BookCategory.BookCategoryBuilder().setCategoryName(DUMMY_CATEGORY_2).buildBookCategory();

        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).buildUser();
        Book user1Book1 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_1).setCategory(category1).setPostedBy(user1).buildBook();
        Book user1Book2 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_3).setCategory(category2).setPostedBy(user1).buildBook();

        User user2 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();
        Book user2Book1 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_2).setCategory(category2).setPostedBy(user1).buildBook();
        Book user2Book2 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_4).setCategory(category1).setPostedBy(user1).buildBook();

        persistData(Arrays.asList(user1,user2), Arrays.asList(category1,category2), Arrays.asList(user1Book1, user1Book2, user2Book1, user2Book2));

        DirectBookExchange directBookExchange1 = new DirectBookExchange();
        directBookExchange1.setBookRequested(user1Book1);
        directBookExchange1.setBookRequested(user2Book1);

        DirectBookExchange directBookExchange2 = new DirectBookExchange();
        directBookExchange1.setBookRequested(user2Book2);
        directBookExchange1.setBookRequested(user1Book2);

        bookExchangeDao.saveBookExchange(directBookExchange1);
        bookExchangeDao.saveBookExchange(directBookExchange2);

        List<DirectBookExchange> result = bookExchangeDao.getDirectExchangesInitiatedByUser("DUMMY_EMAIL_1");

        assertEquals("There must be 2 exchanges for user1",2,result.size());
    }

    @Test
    public void addBookExchangeChain(){
        BookCategory category1 = new BookCategory.BookCategoryBuilder().setCategoryName(DUMMY_CATEGORY_1).buildBookCategory();

        User user1 = new User.UserBuilder().setEmail(DUMMY_EMAIL_1).buildUser();
        Book user1Book1 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_1).setCategory(category1).setPostedBy(user1).buildBook();

        User user2 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();
        Book user2Book1 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_2).setCategory(category1).setPostedBy(user1).buildBook();

        User user3 = new User.UserBuilder().setEmail(DUMMY_EMAIL_2).buildUser();
        Book user3Book1 = new Book.BookBuilder().setTitle(DUMMY_BOOK_TITLE_2).setCategory(category1).setPostedBy(user1).buildBook();

        persistData(Arrays.asList(user1,user2,user3), Arrays.asList(category1), Arrays.asList(user1Book1,user2Book1,user3Book1));

        BookExchangeChain bookExchangeChain = new BookExchangeChain();
        bookExchangeChain.setExchangeInitiator(user1);

        ExchangeChainRequest exchangeChainRequest1 = new ExchangeChainRequest();
        exchangeChainRequest1.setUserOfferingTo(user1);
        exchangeChainRequest1.setUserChoosingFrom(user2);

        ExchangeChainRequest exchangeChainRequest2 = new ExchangeChainRequest();
        exchangeChainRequest2.setUserOfferingTo(user2);
        exchangeChainRequest2.setUserChoosingFrom(user3);

        bookExchangeChain.setExchangeChainRequests(Arrays.asList(exchangeChainRequest1, exchangeChainRequest2));

        Graph closedComponent = constructGraph(user1,user2,user3);
        bookExchangeChain.setClosedComponent(closedComponent);

        bookExchangeDao.addBookExchangeChainRequest(exchangeChainRequest1);
        bookExchangeDao.addBookExchangeChainRequest(exchangeChainRequest2);
        bookExchangeDao.saveBookExchangeChain(bookExchangeChain);

        List<BookExchangeChain> result = bookExchangeDao.getExchangeChainsInitiatedByUser(user1.getEmail());

        assertEquals("There should only be one exchange chain initiated by user",1,result.size());

        List<ExchangeChainRequest> exchangeChainRequests = result.get(0).getExchangeChainRequests();
        Graph closedComponentForChain = result.get(0).getClosedComponent();
        assertEquals(3,closedComponentForChain.getVerticies().size());
        assertEquals(2,closedComponentForChain.getEdges().size());
        assertEquals("There should be 2 chain exchange requests in chain",2, exchangeChainRequests.size());
        assertEquals("There should be 2 chain exchange requests in chain",2, exchangeChainRequests.size());

    }

    private Graph constructGraph(User user1, User user2, User user3) {
        Vertex vertex1 = new Vertex(user1.getEmail());
        Vertex vertex2 = new Vertex(user2.getEmail());
        Vertex vertex3 = new Vertex(user3.getEmail());

        Graph graph = new Graph(Arrays.asList(vertex1,vertex2,vertex3),new ArrayList<>());
        graph.addEdge(vertex1,vertex2,0);
        graph.addEdge(vertex2,vertex3,0);

        return graph;
    }

    private void persistData(List<User> users, List<BookCategory> bookCategories, List<Book> books) {
        users.stream().forEach(user -> userDao.saveUser(user));
        bookCategories.stream().forEach(bookCategory -> categoryDao.addCategory(bookCategory));
        books.stream().forEach(book -> bookDao.postBookOnExchange(book));
    }

}
