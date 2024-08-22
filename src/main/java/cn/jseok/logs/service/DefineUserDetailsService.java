package cn.jseok.logs.service;

import cn.jseok.logs.bean.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefineUserDetailsService {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User  login() {

        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, "323238923902");

        return user;

    }
}
