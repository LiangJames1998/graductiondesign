package com.gradution.chao.graductiondesign.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gradution.chao.graductiondesign.pojo.*;
import com.gradution.chao.graductiondesign.service.*;
import com.gradution.chao.graductiondesign.utils.Result;
import io.minio.MinioClient;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/travel")
public class IndexController {

    @Autowired
    MainService mainService;

    @Autowired
    UserService userService;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    NationService nationService;

    @Autowired
    AdminService adminService;

    private static String url = "http://127.0.0.1:9000";
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";



    /**
     * 展示 网站首页
     * 回传显示用户是否登录
     *
     */
    @RequestMapping({"","/index"})
    public String indexView(HttpServletRequest request, HttpSession session){

        /*  查询 受 管理员  推荐的旅游景点  */
        List<Mmsg> TuiTravele = adminService.getAdminMmsgByMtype(2);
        if(TuiTravele != null){
            List<Traveler> TuiTrave = new ArrayList<Traveler>();
            for(int i=0;i<TuiTravele.size(); i++){

                int travelID = TuiTravele.get(i).getM_mid();

                Traveler findIdTrave = mainService.findTravelingByTID(travelID);
                String path = "'" + findIdTrave.getT_picture1() + "'";
                findIdTrave.setT_picture1(path);
                TuiTrave.add(findIdTrave);
            }
            request.setAttribute("TuiTrave",TuiTrave);
        }


        /*  查询 受 管理员  推荐的博客信息  */
        List<Mmsg> TuiBlogMsg = adminService.getAdminMmsgByMtype(3);
        if(TuiBlogMsg != null){
            List<Blober> TuiBlog = new ArrayList<Blober>();
            for(int i=0;i<TuiBlogMsg.size(); i++){

                int blogID = TuiBlogMsg.get(i).getM_mid();
                Blober findIdBlog = mainService.findBlobByBID(blogID);
                String path = "'" + findIdBlog.getB_picture1() + "'";
                findIdBlog.setB_picture1(path);
                TuiBlog.add(findIdBlog);
            }
            request.setAttribute("TuiBlog",TuiBlog);
        }



        return "index";
    }


    /**
     * 展示 网站主页,开始展示数据的页面
     * 回传显示main主页
     *
     */
    @RequestMapping("/main")
    public String mainView(Model model,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="16",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        //System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Traveler> travelersList = mainService.findAllTravelinger();
            //System.out.println("分页数据："+travelersList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Traveler> pageInfo = new PageInfo<Traveler>(travelersList,pageSize);

            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("userList",travelersList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }

        return "main";
    }




    @RequestMapping("/search")
    public String goSearch(Model model,
                           @RequestParam(required = false,defaultValue="",value = "finding") String finding,
                           @RequestParam(defaultValue="1",value = "typeing") Integer f_type,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="6",value="pageSize")Integer pageSize,
                           HttpSession session){

        //判断查找的type类型
        if(f_type == 1){
            //用户查找景区

            if(pageNum==null || pageNum<=0){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 1;
            }
            PageHelper.startPage(pageNum,pageSize);
            try {
                List<Traveler> travelersList = mainService.findFuzzyTravelName("%"+finding+"%");

                for(Traveler t1: travelersList){
                    String content = t1.getT_picture1();
                    content = "'" + content + "'";
                    //String path = "/travelimage/pic1001.jpg";
                    t1.setT_picture1(content);
                }

                PageInfo<Traveler> pageInfo = new PageInfo<Traveler>(travelersList,pageSize);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("findingList",travelersList);

                //传值回去获取finding与type 参数
                model.addAttribute("para1",finding);
                model.addAttribute("para2",f_type);

                model.addAttribute("choosen",true);
            }finally {
                //清理 ThreadLocal 存储的分页参数,保证线程安全
                PageHelper.clearPage();
            }
            //  结束-------------------


        }else if(f_type == 2){

            //用户查找博客
            if(pageNum==null || pageNum<=0){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 1;
            }
            PageHelper.startPage(pageNum,pageSize);
            try {
                List<Blober> blobersList = mainService.findFuzzyBlogTitle("%"+finding+"%");

                for(Blober t1: blobersList){
                    String content = t1.getB_picture1();
                    content = "'" + content + "'";
                    //String path = "/travelimage/pic1001.jpg";
                    t1.setB_picture1(content);
                }

                PageInfo<Blober> pageInfo = new PageInfo<Blober>(blobersList,pageSize);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("findingList",blobersList);

                //传值回去获取finding与type 参数
                model.addAttribute("para1",finding);
                model.addAttribute("para2",f_type);

                model.addAttribute("choosen",false);
            }finally {
                //清理 ThreadLocal 存储的分页参数,保证线程安全
                PageHelper.clearPage();
            }
            //  结束-------------------

        }else {
            //无法判断,系统出错,跳转出错界面

        }


        return "search";
    }


    @RequestMapping("/mainsingle")
    public String mainSingle(Model model,
                             @RequestParam(required = true,value="trave_id")Integer trave_id,
                             HttpSession session){

        if(trave_id<1001)
            trave_id = 1001;

        //获取trave_id对应的旅游景点信息
        model.addAttribute("traveMeg",mainService.findTravelingByTID(trave_id));

        //点击量加一
        mainService.addTravelClickedBYBID(trave_id);

        Object objectU = session.getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU != null){
            //sessoion不为空，用户未登录

            //判断当前的文章用户之前是否点击过
            User toUser = (User)objectU;

            Browsetravel findBt = mainService.findSameBrowseTravel(toUser.getU_id(),trave_id);

            if(findBt == null){
                //不存在之前的同用户同文章的浏览记录
                Browsetravel browsetravel = new Browsetravel();

                browsetravel.setB_time(new Date());

                browsetravel.setB_userid(toUser.getU_id());

                browsetravel.setB_travelid(trave_id);
                //添加用户的对应浏览记录
                mainService.addNewBrowseTravel(browsetravel);
            }

        }


        int cityID = nationService.getWearthIDbyCityName((mainService.findTravelingByTID(trave_id).getT_city()));

        //System.out.println(voWeath.toString());
        model.addAttribute("weatherID",cityID);

        //获取数据库中的经度纬度
        Point point = nationService.getPointbyPtrID(trave_id);

        Double pX = Double.parseDouble(point.getP_x());
        Double pY = Double.parseDouble(point.getP_y());

        model.addAttribute("pX",pX);
        model.addAttribute("pY",pY);

        return "mainsingle";
    }


    @RequestMapping("/home")
    public String homeView(HttpServletRequest request,Model model){

        return "home";
    }

    @RequestMapping("about")
    public String aboutView(HttpServletRequest request){


        return "about";
    }

    @RequestMapping("curlture")
    public String curltureView(HttpServletRequest request){




        return "curlture";
    }


    @RequestMapping("/blog")
    public String blogView(Model model,
                           @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="8",value="pageSize")Integer pageSize){

        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        //System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Blober> blobList = mainService.findAllBlober();
            if(blobList != null){
                for(Blober blober : blobList){
                    String path = "'" + blober.getB_picture1() + "'";
                    blober.setB_picture1(path);
                }
            }
            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Blober> pageInfo = new PageInfo<Blober>(blobList,pageSize);

            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("userList",blobList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }

        return "blog";
    }

    @RequestMapping("blogsingle")
    public String blogSingleView(Model model,
                                 @RequestParam(required = true,value="blog_id")Integer blog_id ,
                                 HttpSession session){

        model.addAttribute("blobMsg",mainService.findBlobByBID(blog_id));

        model.addAttribute("commentMsg",mainService.findCommentsByBID(blog_id));

        model.addAttribute("blogid",blog_id);


        List<Blober> tuiBolberMsg =  mainService.getTuijianBlogsMsg();
        if(tuiBolberMsg != null){
            for(Blober blober : tuiBolberMsg){
                String path = "'" + blober.getB_picture1() + "'";
                blober.setB_picture1(path);
            }
        }

        model.addAttribute("tuiBlogMsg",tuiBolberMsg);

        int blog_author_id = mainService.findBlobByBID(blog_id).getB_author_id();
        //获取用户头像地址
        User u_headimg = userService.getUserByUID(blog_author_id);
        model.addAttribute("authorimg",u_headimg.getU_headimg());

        //点击量加一
        mainService.addArticleClicked(blog_id);

        Object objectU = session.getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU != null){
            //sessoion不为空，用户未登录

            //判断当前的文章用户之前是否点击过
            User toUser = (User)objectU;

            Browseblog findBr = mainService.findSameBrowse(toUser.getU_id(),blog_id);

            if(findBr == null){
                //不存在之前的同用户同文章的浏览记录
                Browseblog browseblog = new Browseblog();

                browseblog.setB_time(new Date());

                browseblog.setB_userid(toUser.getU_id());

                browseblog.setB_blogid(blog_id);

                mainService.addNewBrowseBy(browseblog);

            }else {
                //若存在浏览记录则更新记录的 点击量，评论量，浏览时间
                //不做了，没多大意义。。。。。。。
            }

        }

        return "blogsingle";
    }


}
