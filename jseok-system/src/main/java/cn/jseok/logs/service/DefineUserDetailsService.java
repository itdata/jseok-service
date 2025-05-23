package cn.jseok.logs.service;

import cn.jseok.logs.bean.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefineUserDetailsService {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User login() {
        User user = hibernateTemplate.get(User.class, "323238923902");
//        Session session = sessionFactory.getCurrentSession();
//        User user = session.get(User.class, "323238923902");
        User user1 = hibernateTemplate.execute(new HibernateCallback<User>() {
            public User doInHibernate(Session session) throws HibernateException {
                List<User> list = session.createQuery("from User").list();
                return list.get(0);
            }
        });
        User user2 = hibernateTemplate.execute(session -> {
            List<User> list = session.createQuery("from User").list();
            return list.get(0);
        });
        return user2;

    }
}
