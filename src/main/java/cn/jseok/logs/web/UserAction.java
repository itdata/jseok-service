package cn.jseok.logs.web;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")

public class UserAction{

    @Autowired
    private SessionFactory sessionFactory;


    public UserAction(){
        System.out.println("Bean 注入成功");
    }

    @GetMapping(value = "/test",produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional
    public String Test(){
        Session session =  sessionFactory.getCurrentSession();
        Transaction tx  = session.getTransaction();
        tx.begin();
        System.out.println(session);
        tx.commit();
        System.out.println("进入springMvc1");
        return "进入sping mvc";
    }
    @RequestMapping("/view1")
    public String requestMapping1() {
        return "redirect:/pages/redirecting.html";
    }
}
