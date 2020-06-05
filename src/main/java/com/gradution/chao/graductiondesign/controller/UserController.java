package com.gradution.chao.graductiondesign.controller;


import com.gradution.chao.graductiondesign.mapper.UserMapper;
import com.gradution.chao.graductiondesign.pojo.User;
import com.gradution.chao.graductiondesign.service.UserService;
import com.gradution.chao.graductiondesign.utils.ReplaceUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/travel")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 跳转登录界面
     * @return
     */
    @RequestMapping("/login")
    public String loginView(HttpServletRequest request){


        return "login";
    }

    /**
     * 跳转注册界面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }



    @RequestMapping(value = "/checklogin",method = RequestMethod.POST)
    public ModelAndView checkLogin(@RequestParam("account") String account,
                                   @RequestParam("password") String password,
                                   @RequestParam("vcode") String vcode,
                                         HttpSession session,
                                         HttpServletRequest request ){
        

        ModelAndView modelAndView = new ModelAndView();

        if(StringUtils.isEmpty(vcode)){

            String msg = "验证码不能为空!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
        if(StringUtils.isEmpty(loginCpacha)){

            String msg = "长时间未操作，会话已失效，请刷新后重试!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){

            String msg = "验证码错误!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //根据账号查询密码
        User user = userService.getUserByPassword(account);

        if(user == null){
            //用户名不存在
            String msg = "用户名不存在!";
            request.setAttribute("msg",msg);

            System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //MD5加密认证
        String encodePassword = new Md5Hash(password).toString().toUpperCase();

        if(!encodePassword.equals(user.getU_password())){
            //密码错误
            //用户名不存在
            String msg = "密码错误!";
            request.setAttribute("msg",msg);
            System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        //验证成功
        System.out.println("成功");

        //写入缓存
        session.setAttribute("userNow",user);

        modelAndView.setViewName("redirect:/travel/index");
        return modelAndView;
    }


    @RequestMapping(value = "/checkregister",method = RequestMethod.POST)
    public ModelAndView checkRegister(@RequestParam("account") String account,
                                      @RequestParam("email")  String email,
                                      @RequestParam("password1") String password1,
                                      @RequestParam("password2") String password2,
                                      @RequestParam("vcode") String vcode,
                                      HttpServletRequest request ){

        ModelAndView modelAndView = new ModelAndView();

        if(StringUtils.isEmpty(vcode)){

            String msg = "验证码不能为空!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
        if(StringUtils.isEmpty(loginCpacha)){

            String msg = "长时间未操作，会话已失效，请刷新后重试!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){

            String msg = "验证码错误!";
            request.setAttribute("msg",msg);

            //System.out.println(msg);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //判断输入的账号是否为手机号格式
        ReplaceUtil replaceUtil =new ReplaceUtil();
        if(!replaceUtil.isMobileNO(account)){
            String msg = "账号格式输入错误！应为11位手机号码";
            request.setAttribute("msg",msg);
            System.out.println(msg);
            modelAndView.setViewName("register");
            return modelAndView;
        }


        //判断密码是否一致
        if(!(password1.equals(password2))){
            String msg = "两次密码不一致!";
            request.setAttribute("msg",msg);
            System.out.println(msg);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        //根据账号查询密码
        User finduser = userService.getUserByPassword(account);
        if(finduser != null){
            //用户名已经存在
            String msg = "用户名已存在!";
            request.setAttribute("msg",msg);
            System.out.println(msg);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        //MD5加密认证
        String encodePassword = new Md5Hash(password1).toString().toUpperCase();

        //设置新的用户
        User newU = new User();
        newU.setU_account(account);
        newU.setU_password(encodePassword);
        newU.setU_email(email);

        userService.addUserNew(newU);
        String msg = "注册成功！可到登录界面登录!";
        request.setAttribute("msg",msg);
        System.out.println(msg);
        modelAndView.setViewName("redirect:/travel/register");

        return modelAndView;
    }


    @RequestMapping("/logout")
    public ModelAndView logOuter(HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        //清除缓存
        session.removeAttribute("userNow");

        modelAndView.setViewName("redirect:/travel/index");
        return modelAndView;
    }



}
