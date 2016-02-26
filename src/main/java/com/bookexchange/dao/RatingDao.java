package com.bookexchange.dao;

import com.bookexchange.dto.RatingComment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sheke on 2/19/2016.
 */
@Repository
public class RatingDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addRating(RatingComment rating) {
        sessionFactory.getCurrentSession().save(rating);
    }

    public List<RatingComment> getAllRatingsForUser(String currentUserEmail) {
        Session currentSession = sessionFactory.getCurrentSession();

        Criteria criteria = currentSession.createCriteria(RatingComment.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createAlias("commentFor", "userCommentFor")
                .createAlias("commentFrom", "userCommentFrom")
                .add(Restrictions.or(Restrictions.eq("userCommentFor.email", currentUserEmail),Restrictions.eq("userCommentFrom.email",currentUserEmail)));

        return (List<RatingComment>) criteria.list();
    }
}
