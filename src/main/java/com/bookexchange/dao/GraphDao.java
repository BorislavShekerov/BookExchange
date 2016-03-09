package com.bookexchange.dao;

import com.bookexchange.dto.BookCategory;
import com.bookexchange.graph.Graph;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sheke on 3/2/2016.
 */
@Repository
public class GraphDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addGraph(Graph graph) {
        sessionFactory.getCurrentSession().saveOrUpdate(graph);
    }

    public List<Graph> getAllGraphs() {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(Graph.class);
        return criteria.list();
    }
}
