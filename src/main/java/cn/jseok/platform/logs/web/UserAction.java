package cn.jseok.platform.logs.web;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserAction{
    public UserAction(){
        System.out.println("Bean 注入成功");
    }

    @GetMapping(value = "/test",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Test(){
        System.out.println("进入springMvc1");
        return "进入sping mvc";
    }
    @RequestMapping("/view1")
    public String requestMapping1() {
        return "redirect:/pages/redirecting.html";
    }
}
