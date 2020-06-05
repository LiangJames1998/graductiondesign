package com.gradution.chao.graductiondesign.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gradution.chao.graductiondesign.pojo.*;
import com.gradution.chao.graductiondesign.service.AdminService;
import com.gradution.chao.graductiondesign.service.MainService;
import com.gradution.chao.graductiondesign.service.SendMailService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.minio.MinioClient;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/travel")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    MainService mainService;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    TemplateEngine templateEngine;


    private static String url = "http://127.0.0.1:9000";
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";


    @RequestMapping("/admin-login")
    public String adminLogin(){

        return "admin-login";
    }


    @RequestMapping(value = "/checkAdminlogin",method = RequestMethod.POST)
    public ModelAndView checkAdminLogin(@RequestParam("account") String account,
                                        @RequestParam("password") String password,
                                        @RequestParam("vcode") String vcode,
                                        HttpSession session,
                                        HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        if(StringUtils.isEmpty(vcode)){

            String msg = "验证码不能为空!";
            request.setAttribute("adminmsg",msg);

            modelAndView.setViewName("admin-login");
            return modelAndView;
        }
        String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
        if(StringUtils.isEmpty(loginCpacha)){

            String msg = "长时间未操作，会话已失效，请刷新后重试!";
            request.setAttribute("adminmsg",msg);

            modelAndView.setViewName("admin-login");
            return modelAndView;
        }
        if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){

            String msg = "验证码错误!";
            request.setAttribute("adminmsg",msg);

            modelAndView.setViewName("admin-login");
            return modelAndView;
        }

        //根据管理员ID 查询密码
        Admins admins = adminService.getAdmingByMaccount(account);

        if(admins == null){
            //用户名不存在
            String msg = "用户不存在!";
            request.setAttribute("adminmsg",msg);
            modelAndView.setViewName("admin-login");
            return modelAndView;
        }

        //MD5加密认证
        String encodePassword = new Md5Hash(password).toString().toUpperCase();
        if(!encodePassword.equals(admins.getM_password())){
            //密码错误
            //用户名不存在
            String msg = "密码错误!";
            request.setAttribute("adminmsg",msg);
            System.out.println(msg);
            modelAndView.setViewName("admin-login");
            return modelAndView;
        }

        //验证成功
        //System.out.println("成功");

        //写入缓存
        session.setAttribute("adminNow",admins);
        //进入管理界面
        modelAndView.setViewName("redirect:/travel/admin-index");

        return modelAndView;
    }

    /**
     * 管理员首页
     * @return
     */

    @RequestMapping("/admin-index")
    public String AdmingManageIndexView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        /*  查询公告信息  */
        List<Mmsg> mmsgFirst = adminService.getAdminMmsgByMtype(1);
        for(Mmsg msgShow : mmsgFirst){
            request.setAttribute("mmsgFirst",msgShow);
            break;
        }

        /*  查询推荐的景点信息  */
        List<Mmsg> mmsgSecond = adminService.getAdminMmsgByMtype(2);
        request.setAttribute("mmsgSecond",mmsgSecond);

        /*  查询推荐的博客信息  */
        List<Mmsg> mmsgThird = adminService.getAdminMmsgByMtype(3);
        request.setAttribute("mmsgThird",mmsgThird);


        /*  查询排行前列的旅游景点   */
        List<Traveler> travelers = adminService.findMostPopularTravel();
        request.setAttribute("trave_1",travelers.get(0));
        request.setAttribute("trave_2",travelers.get(1));

        /*  查询排行前列的博客信息   */
        List<Blober> blobers =  adminService.findMostPopularBlog();
        request.setAttribute("blog_1",blobers.get(0));
        request.setAttribute("blog_2",blobers.get(1));

        /*  查询用户问题与回答  */
        List<Mmsg> userAsk = adminService.getAdminMmsgByMtype(5);
        request.setAttribute("userAsk",userAsk);


        /*  查询基本信息，用户点击评论注册量  */
        request.setAttribute("usData1",adminService.findWebSationClicked());
        request.setAttribute("usData2",adminService.findWebSationComments());
        request.setAttribute("usData3",adminService.findWebSationResiger());


        return "admin-index";
    }

    /**
     * 管理员 管理旅游景点信息
     * @return
     */

    @RequestMapping("/admin-travel")
    public String AdmingManageTravelView(HttpServletRequest request,
                                         @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                         @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

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
            // 查询所有旅游景点信息
            List<Traveler> adminTravel = adminService.getAllTravelerAdmin();

            System.out.println(adminTravel.size()+"changdu----------");

            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Traveler> pageInfo = new PageInfo<Traveler>(adminTravel,pageSize);

            //4.使用model传参数回前端
            request.setAttribute("pageInfo",pageInfo);
            request.setAttribute("adminTravel",adminTravel);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }

        return "admin-travel";
    }

    /**
     * 管理员 管理博客信息
     * @return
     */

    @RequestMapping("/admin-blog")
    public String AdmingManageBlogView(HttpServletRequest request,
                                       @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                       @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

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
            // 查询所有旅游景点信息
            List<Blober> adminBlober = adminService.getAllBloberAdmin();

            System.out.println(adminBlober.size());

            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Blober> pageInfo = new PageInfo<Blober>(adminBlober,pageSize);

            //4.使用model传参数回前端
            request.setAttribute("pageInfo",pageInfo);
            request.setAttribute("adminBlober",adminBlober);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }

        return "admin-blog";
    }

    /**
     * 管理员 管理 用户信息
     * @return
     */

    @RequestMapping("/admin-user")
    public String AdmingManageUserView(HttpServletRequest request,
                                       @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                       @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

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
            // 查询所有旅游景点信息
            List<User> adminUser = adminService.getAllUserbyAdmin();

            System.out.println(adminUser.size());

            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<User> pageInfo = new PageInfo<User>(adminUser,pageSize);

            //4.使用model传参数回前端
            request.setAttribute("pageInfo",pageInfo);
            request.setAttribute("adminUser",adminUser);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }

        return "admin-user";
    }


    @RequestMapping("/adminlogout")
    public ModelAndView logOuter(HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        //清除缓存
        session.removeAttribute("adminNow");

        modelAndView.setViewName("redirect:/travel/index");
        return modelAndView;
    }

    @RequestMapping("/admin-publish")
    public String AdminPublishView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        return "admin-publish";
    }

    @RequestMapping("/travelpublish")
    public ModelAndView adminpublicTravel(HttpServletRequest request,
                                          @RequestParam("traveName") String traveName,
                                          @RequestParam("traveCity") String traveCity,
                                          @RequestParam("traveCityWeath") Integer traveCityWeath,
                                          @RequestParam("travePointX") String travePointX,
                                          @RequestParam("travePointY") String travePointY,
                                          @RequestParam("traveOpenTime") String traveOpenTime,
                                          @RequestParam("paragraph1") String paragraph1,
                                          @RequestParam("paragraph2") String paragraph2,
                                          @RequestParam("paragraph3") String paragraph3,
                                          @RequestParam("paragraph4") String paragraph4,
                                          @RequestParam("fileToUpload") MultipartFile[] file ){
        ModelAndView modelAndView = new ModelAndView();

        //System.out.println(traveName+traveCityWeath+travePointX+travePointY);

        //判断是否为空
        if(StringUtils.isEmpty(traveName) || StringUtils.isEmpty(traveCity)){
            modelAndView.setViewName("redirect:/travel/admin-publish");
            request.getSession().setAttribute("travepublicmsg","关键信息为空");
            return modelAndView;
        }
        if(StringUtils.isEmpty(travePointX) || StringUtils.isEmpty(travePointY)){
            modelAndView.setViewName("redirect:/travel/admin-publish");
            request.getSession().setAttribute("travepublicmsg","关键信息为空");
            return modelAndView;
        }
        if(StringUtils.isEmpty(traveOpenTime)){
            modelAndView.setViewName("redirect:/travel/admin-publish");
            request.getSession().setAttribute("travepublicmsg","关键信息为空");
            return modelAndView;
        }
        if(traveCityWeath == null){
            modelAndView.setViewName("redirect:/travel/admin-publish");
            request.getSession().setAttribute("travepublicmsg","关键信息为空");
            return modelAndView;
        }

        //添加城市天气信息，加城市名字 天气网ID
        //查询有无相同的城市天气信息
        WeatherID forCheck = adminService.getWeatherbyWID(traveCityWeath);
        if(forCheck == null){
            //向数据库添加城市天气信息
            WeatherID forSend = new WeatherID();
            forSend.setW_id(traveCityWeath);
            forSend.setW_city(traveCity);
            adminService.addTraveCityWeather(forSend);
        }

        //添加景点具体信息
        Traveler forTrave = new Traveler();
        forTrave.setT_name(traveName);
        forTrave.setT_city(traveCity);
        forTrave.setT_uptime(traveOpenTime);
        forTrave.setT_text1(paragraph1);
        forTrave.setT_text2(paragraph2);
        forTrave.setT_text3(paragraph3);
        forTrave.setT_text4(paragraph4);

        //图片上传业
        try{
            for(int i=0;i<file.length;i++){
                if(file[i]!=null){
                    //开始上传文件
                    String fileName = System.currentTimeMillis()+file[i].getOriginalFilename();

                    //判断获取的文件是否为jpg文件或者png文件
                    int pandu = fileName.lastIndexOf(".");
                    if((fileName.substring(pandu+1)).equals("mp4") || (fileName.substring(pandu+1)).equals("avi")){
                        //非视频文件
                        //单独上传视频文件
                        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                        InputStream is = file[i].getInputStream();
                        String contentType = file[i].getContentType();

                        minioClient.putObject("traveling",fileName,is,contentType);
                        forTrave.setT_picture6(minioClient.presignedGetObject("traveling", fileName));
                        continue;
                    }else {
                        //判断获取的文件是否为jpg文件或者png文件
                        //int pandu = fileName.lastIndexOf(".");
                        if(!(fileName.substring(pandu+1)).equals("jpg") && !(fileName.substring(pandu+1)).equals("png")){
                            //非jpg文件跳出循环
                            continue;
                        }
                        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                        InputStream is = file[i].getInputStream();
                        String contentType = file[i].getContentType();
                        minioClient.putObject("traveling",fileName,is,contentType);

                        switch (i){
                            case 0:
                                forTrave.setT_picture1(minioClient.presignedGetObject("traveling", fileName));
                                break;
                            case 1:
                                forTrave.setT_picture2(minioClient.presignedGetObject("traveling", fileName));
                                break;
                            case 2:
                                forTrave.setT_picture3(minioClient.presignedGetObject("traveling", fileName));
                                break;
                            case 3:
                                forTrave.setT_picture4(minioClient.presignedGetObject("traveling", fileName));
                                break;
                            case 4:
                                forTrave.setT_picture5(minioClient.presignedGetObject("traveling", fileName));
                                break;
                            default:
                                System.out.println("出错了！");
                        }
                    }
                }
            }
        }catch (Exception e){
            //System.out.println("上传失败");
            modelAndView.setViewName("redirect:/travel/admin-publish");
            request.getSession().setAttribute("travepublicmsg","上传图片失败!");
            return modelAndView;
        }

        System.out.println(forTrave.getT_picture6());

        //添加旅游景点信息
        adminService.addTravelingMsg(forTrave);

        //查询最新的景点Id
        int newTrave = adminService.findNewestTravelMsg();

        //添加景点 坐标信息 经度与纬度 X与Y (后面再加，数据库自动生成t_id)
        Point pointSend = new Point();
        pointSend.setP_trid(newTrave);
        pointSend.setP_x(travePointX);
        pointSend.setP_y(travePointY);
        adminService.addTravelCityPointXY(pointSend);

        request.getSession().setAttribute("travepublicmsg","发布成功!");
        modelAndView.setViewName("redirect:/travel/admin-publish");
        return modelAndView;
    }


    @RequestMapping(value = "/changeMsg",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> doTheMsgChange(@RequestParam("changeMsg") String changeMsg,
                                              HttpServletRequest request){

        Map<String, String> ret = new HashMap<String, String>();

        if(StringUtils.isEmpty(changeMsg)){
            ret.put("type","error");
            ret.put("msg","修改内容不能为空...");
            return ret;
        }

        adminService.updateMmsgByM_Type(changeMsg,1);

        ret.put("type","success");
        ret.put("msg","修改成功！");

        return ret;
    }


    @RequestMapping(value = "/addTuiTravel",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addRecommendTodayTravel(@RequestParam("tr_id") int tr_id,
                                                      HttpServletRequest request){

        Map<String,String> ret = new HashMap<String, String>();

        if(tr_id == 0){
            ret.put("type","error");
            ret.put("msg","输入推荐的旅游景点id");
            return ret;
        }

        Traveler traveAdd = mainService.findTravelingByTID(tr_id);
        if(traveAdd == null){
            ret.put("type","error");
            ret.put("msg","输入的ID不存在！");
            return ret;
        }

        Mmsg addMsg = new Mmsg();
        addMsg.setM_type(2);
        addMsg.setM_msg("");
        addMsg.setM_mid(traveAdd.getT_id());
        addMsg.setM_title(traveAdd.getT_name());
        addMsg.setM_clicked(traveAdd.getT_clicked());
        addMsg.setM_mark(traveAdd.getT_city());

        adminService.addAdminMmsgByMid(addMsg);

        ret.put("type","success");
        ret.put("msg","添加成功！");

        return ret;
    }


    @RequestMapping(value = "/addTuiBlog",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addRecommendTodayBlog(@RequestParam("blog_id") int blog_id,
                                                      HttpServletRequest request){

        Map<String,String> ret = new HashMap<String, String>();

        if(blog_id == 0){
            ret.put("type","error");
            ret.put("msg","输入推荐的博客信息id");
            return ret;
        }

        Blober blobAd = mainService.findBlobByBID(blog_id);
        if(blobAd == null){
            ret.put("type","error");
            ret.put("msg","输入的ID不存在！");
            return ret;
        }

        Mmsg addMsg = new Mmsg();
        addMsg.setM_type(3);
        addMsg.setM_msg("");
        addMsg.setM_mid(blobAd.getB_id());
        addMsg.setM_title(blobAd.getB_title());
        addMsg.setM_clicked(blobAd.getB_clicked());
        addMsg.setM_mark(blobAd.getB_author_name());

        adminService.addAdminMmsgByMid(addMsg);

        ret.put("type","success");
        ret.put("msg","添加成功！");

        return ret;
    }

    @RequestMapping("/deleteMmsg")
    public ModelAndView deleteMmsgTuiComment(@RequestParam(required = true,value="m_mid") Integer m_mid  ){

        ModelAndView modelAndView = new ModelAndView();

        adminService.deleteMmsgByMmid(m_mid);

        modelAndView.setViewName("redirect:/travel/admin-index");

        return modelAndView;
    }


    @RequestMapping(value = "/addSubscribe",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> addSubscribeUser(@RequestParam("userEmial") String userEmial,
                                               HttpServletRequest request){

        Map<String,String> ret = new HashMap<>();

        /*  判断用户是否登录  */
        Object user = request.getSession().getAttribute("userNow");
        if(user == null){
            //sessoion为空，用户未登录
            ret.put("type","error");
            ret.put("msg","请您先登录后操作");
            return ret;
        }

        if(StringUtils.isEmpty(userEmial)){
            ret.put("type","error");
            ret.put("msg","输入的邮箱地址为空！");
            return ret;
        }
        String reg="com";
        if(userEmial.indexOf("com") == -1){
            ret.put("type","error");
            ret.put("msg","输入的邮箱格式不正确！");
            return ret;
        }

        /*  新增用户订阅的电子邮箱号  */
        Mmsg userMsg = new Mmsg();
        userMsg.setM_type(4);
        userMsg.setM_msg(userEmial);
        /*  设置用户的ID   */
        User toUser = (User)user;
        userMsg.setM_mid(toUser.getU_id());

        adminService.addAdminMmsgByMid(userMsg);

        ret.put("type","success");
        ret.put("msg","订阅成功！一有新的文章就会发送邮件给您哦");
        return ret;
    }



    @RequestMapping("/adminAnswer")
    public String userASKing(@RequestParam("answerontent") String askcontent, HttpServletRequest request){

        if(StringUtils.isEmpty(askcontent)){
            return "redirect:/travel/admin-index";
        }

        int emptyIndex = 0;
        List<Mmsg> getMsg = adminService.getAdminMmsgByMtype(5);
        for(Mmsg mfg : getMsg){
            if(StringUtils.isEmpty(mfg.getM_mark())){
                emptyIndex = mfg.getM_id();
                break;
            }
        }
        if(emptyIndex == 0){
            //没有要 回答 的问题
            return "redirect:/travel/admin-index";
        }

        //根据m_id 更新Mmsg 信息
        adminService.updataMmsgAnswerFive(askcontent,emptyIndex);

        return "redirect:/travel/admin-index";
    }



    @RequestMapping("/deleteTravelingData")
    public ModelAndView deleteAdminTravelingData(@RequestParam(required = true,value="t_id") Integer t_id  ){

        ModelAndView modelAndView = new ModelAndView();

        adminService.DeleteTravelingByID(t_id);

        modelAndView.setViewName("redirect:/travel/admin-index");

        return modelAndView;
    }


    @RequestMapping("/deleteBlogData")
    public ModelAndView deleteAdminBlogData(@RequestParam(required = true,value="b_id") Integer b_id  ){

        ModelAndView modelAndView = new ModelAndView();

        adminService.DeleteBloberByID(b_id);

        modelAndView.setViewName("redirect:/travel/admin-index");

        return modelAndView;
    }


    /**
     * 管理员审核界面
     * @param request
     * @return
     */
    @RequestMapping("/admin-examine")
    public String AdminExamineView(HttpServletRequest request){

        //读取缓存数据
        Object chech = request.getSession().getAttribute("toAdminCheck");
        if (chech != null){
            List<Blober> toAdminCheck = new ArrayList<>();
            if (chech instanceof ArrayList<?>){
                for (Object o : (List<?>) chech ){
                    toAdminCheck.add((Blober) o);
                }
            }

            //添加博客信息至 缓存中去，让管理员进行审核
            request.setAttribute("toCheck",toAdminCheck);
        }else {
            request.setAttribute("toCheck",null);
        }

        return "admin-examine";
    }


    /**
     * 审核博客同意
     * @return
     */
    @RequestMapping("/examine-yes")
    public ModelAndView examinePass(@RequestParam(required = true,value="b_id") Integer blog_id,HttpServletRequest request){


        ModelAndView modelAndView = new ModelAndView();

        //读取缓存数据
        Object chech = request.getSession().getAttribute("toAdminCheck");
        List<Blober> toAdminCheck = new ArrayList<>();
        if (chech instanceof ArrayList<?>){
            for (Object o : (List<?>) chech ){
                toAdminCheck.add((Blober) o);
            }
        }
        for (Blober blogYes : toAdminCheck){
            if (blogYes.getB_id() == blog_id){
                //存入数据库
                Blober baseDateblog = new Blober();
                baseDateblog.setB_title(blogYes.getB_title());
                baseDateblog.setB_time(blogYes.getB_time());
                baseDateblog.setB_author_id(blogYes.getB_author_id());
                baseDateblog.setB_author_name(blogYes.getB_author_name());
                baseDateblog.setB_author_autograph(blogYes.getB_author_autograph());
                baseDateblog.setB_text1(blogYes.getB_text1());
                baseDateblog.setB_text2(blogYes.getB_text2());
                baseDateblog.setB_text3(blogYes.getB_text3());
                baseDateblog.setB_picture1(blogYes.getB_picture1());
                baseDateblog.setB_picture2(blogYes.getB_picture2());
                baseDateblog.setB_picture3(blogYes.getB_picture3());

                System.out.println(baseDateblog);
                mainService.UserPublishBlober(baseDateblog);
                //修改缓存
                toAdminCheck.remove(blogYes);
                request.getSession().setAttribute("toAdminCheck",toAdminCheck);

                //发送邮件信息,代做
                //发送邮件给订阅文章的用户
                List<Mmsg> sendMail = adminService.getAdminMmsgByMtype(4);
                for(Mmsg snd: sendMail){
                    // 发送邮件
                    Context ctx = new Context();
                    ctx.setVariable("title",blogYes.getB_title());
                    ctx.setVariable("author",blogYes.getB_author_name());
                    ctx.setVariable("content1",blogYes.getB_text1());
                    ctx.setVariable("content2",blogYes.getB_text2());
                    ctx.setVariable("content3",blogYes.getB_text3());

                    //String sendTo = "2864581368@qq.com";
                    String subject = "旅游推荐系统最新优质博客文章(自动发送)";
                    String mailContext = templateEngine.process("mailtemplate.html",ctx);
                    sendMailService.sendHtmlQQMail(snd.getM_msg(),subject,mailContext);
                }

                String exmStr = "已审核通过标题为："+ blogYes.getB_title() +" 的用户博客，已经自动发送订阅文章...";
                request.setAttribute("examinmsg",exmStr);
                break;
            }
        }

        modelAndView.setViewName("admin-examine");

        return modelAndView;
    }


    /**
     * 审核博客 不同意
     * @return
     */
    @RequestMapping("/examine-no")
    public ModelAndView examineNoPass(@RequestParam(required = true,value="b_id") Integer blog_id ,HttpServletRequest request ){


        //读取缓存数据
        Object chech = request.getSession().getAttribute("toAdminCheck");
        List<Blober> toAdminCheck = new ArrayList<>();
        if (chech instanceof ArrayList<?>){
            for (Object o : (List<?>) chech ){
                toAdminCheck.add((Blober) o);
            }
        }

        for (Blober blogNo : toAdminCheck) {
            if (blogNo.getB_id() == blog_id) {
                //移除缓存中的博客信息
                System.out.println(blogNo);
                toAdminCheck.remove(blogNo);
                request.getSession().setAttribute("toAdminCheck",toAdminCheck);

                String exmStr = "已驳回通过标题为："+ blogNo.getB_title() +" 的用户博客。";
                request.setAttribute("examinmsg",exmStr);

                break;
            }
        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin-examine");

        return modelAndView;
    }

    /**
     * 发布民族文化信息首页
     * @param request
     * @return
     */
    @RequestMapping("/admin-nation")
    public String publicNationView(HttpServletRequest request){
        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }


        return "admin-nation";
    }

    /**
     * 管理员发布民族文化信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/nationpublish", method = RequestMethod.POST)
    public ModelAndView adminNationMsgPublish(HttpServletRequest request,
                                              @RequestParam("n_title") String nationTitleName,
                                              @RequestParam("nation_type") Integer nation_type,
                                              @RequestParam("paragraph1") String paragraph1,
                                              @RequestParam("paragraph2") String paragraph2,
                                              @RequestParam("paragraph3") String paragraph3,
                                              @RequestParam("fileToUpload") MultipartFile[] file ){

        ModelAndView modelAndView = new ModelAndView();

        System.out.println(nationTitleName+nation_type+paragraph1+paragraph2+paragraph3);

        if (StringUtils.isEmpty(nationTitleName) || StringUtils.isEmpty(paragraph1) ){
            request.setAttribute("nationPublishMsg","关键信息缺失...");
            modelAndView.setViewName("admin-nation");
            return modelAndView;
        }

        if (nation_type == 1){
            NationZhuang zhu = new NationZhuang();
            zhu.setN_title(nationTitleName);
            zhu.setN_time(new Date());
            zhu.setN_content1(paragraph1);
            zhu.setN_content2(paragraph2);
            zhu.setN_content3(paragraph3);

            try{
                for(int i=0;i<file.length;i++){
                    if(file[i]!=null){
                        //开始上传文件
                        String fileName = System.currentTimeMillis()+file[i].getOriginalFilename();
                        //判断获取的文件是否为jpg文件或者png文件
                        int pandu = fileName.lastIndexOf(".");
                        if(!(fileName.substring(pandu+1)).equals("jpg") && !(fileName.substring(pandu+1)).equals("png")){
                            //非jpg文件
                            continue;
                        }
                        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                        InputStream is = file[i].getInputStream();
                        String contentType = file[i].getContentType();

                        minioClient.putObject("nation-zhuang",fileName,is,contentType);
                        //存储路径
                        switch (i){
                            case 0:
                                zhu.setN_picture1(minioClient.presignedGetObject("nation-zhuang", fileName));
                                break;
                            case 1:
                                zhu.setN_picture2(minioClient.presignedGetObject("nation-zhuang", fileName));
                                break;
                            case 2:
                                zhu.setN_picture3(minioClient.presignedGetObject("nation-zhuang", fileName));
                                break;
                            default:
                                System.out.println("出错了！");
                        }
                    }
                }
            }catch (Exception e){

                //System.out.println("上传失败");
                request.setAttribute("nationPublishMsg","图片上传出错！");
                modelAndView.setViewName("admin-nation");
                return modelAndView;
            }
            //添加至数据库
            adminService.addNationZhuangMsg(zhu);

        }else {
            NationYao yao = new NationYao();
            yao.setN_title(nationTitleName);
            yao.setN_time(new Date());
            yao.setN_content1(paragraph1);
            yao.setN_content2(paragraph2);
            yao.setN_content3(paragraph3);

            try{
                for(int i=0;i<file.length;i++){
                    if(file[i]!=null){
                        //开始上传文件
                        String fileName = System.currentTimeMillis()+file[i].getOriginalFilename();
                        //判断获取的文件是否为jpg文件或者png文件
                        int pandu = fileName.lastIndexOf(".");
                        if(!(fileName.substring(pandu+1)).equals("jpg") && !(fileName.substring(pandu+1)).equals("png")){
                            //非jpg文件
                            continue;
                        }
                        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                        InputStream is = file[i].getInputStream();
                        String contentType = file[i].getContentType();

                        minioClient.putObject("nation-yao",fileName,is,contentType);
                        //存储路径
                        switch (i){
                            case 0:
                                yao.setN_picture1(minioClient.presignedGetObject("nation-yao", fileName));
                                break;
                            case 1:
                                yao.setN_picture2(minioClient.presignedGetObject("nation-yao", fileName));
                                break;
                            case 2:
                                yao.setN_picture3(minioClient.presignedGetObject("nation-yao", fileName));
                                break;
                            default:
                                System.out.println("出错了！");
                        }
                    }
                }
            }catch (Exception e){

                //System.out.println("上传失败");
                request.setAttribute("nationPublishMsg","图片上传出错！");
                modelAndView.setViewName("admin-nation");
                return modelAndView;
            }
            //添加至数据库
            adminService.addNationYaoMsg(yao);
        }
        request.setAttribute("nationPublishMsg","民族文化信息发布成功！");

        modelAndView.setViewName("admin-nation");
        return modelAndView;
    }


    @RequestMapping("/admin-prefer")
    public String adminPrefer(HttpServletRequest request){
        Object objectU = request.getSession().getAttribute("adminNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }


        return "admin-prefer";
    }



    /**
     *  管理员更改个人信息，昵称头像
     * @return
     */

    @RequestMapping(value = "/updataAdmin" , method = RequestMethod.POST)
    public ModelAndView adminPersonMsgSetting(HttpServletRequest request,
                                              @RequestParam("inputRealName") String inputRealName,
                                              @RequestParam("inputNewPassword") String inputNewPassword,
                                              @RequestParam("inputConfirmNewPassword") String inputConfirmNewPassword,
                                              @RequestParam("fileToUpload") MultipartFile file ){

        ModelAndView modelAndView = new ModelAndView();

        //判断两个密码是否一样
        if(!inputNewPassword.equals(inputConfirmNewPassword)){
            request.getSession().setAttribute("msg","两次密码不一致！");
            modelAndView.setViewName("admin-prefer");
            return modelAndView;
        }

        Object object = request.getSession().getAttribute("adminNow");
        Admins uSession = (Admins)object;

        Admins upAdmin = new Admins();
        upAdmin.setM_id(uSession.getM_id());
        upAdmin.setM_realname(inputRealName);

        //判断密码是否不为空，空则不做更改
        if(inputNewPassword.equals("")){
            upAdmin.setM_password(uSession.getM_password());
        }else {
            // MD5转换
            String encodePassword = new Md5Hash(inputNewPassword).toString().toUpperCase();
            upAdmin.setM_password(encodePassword);
        }

        if(file.isEmpty()){
            upAdmin.setM_headimg(uSession.getM_headimg());
        }else {
            try {
                String fileName = System.currentTimeMillis()+file.getOriginalFilename();

                MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                InputStream is = file.getInputStream();
                String contentType = file.getContentType();
                minioClient.putObject("user",fileName,is,contentType);
                upAdmin.setM_headimg(minioClient.presignedGetObject("user", fileName));

            } catch (Exception e) {
                System.out.println("上传失败1111");
                request.getSession().setAttribute("msg","上传出错！");
                modelAndView.setViewName("admin-prefer");
                return modelAndView;
            }
        }

        adminService.updateAdminErInfo(upAdmin);
        //更改当前session信息
        request.getSession().setAttribute("adminNow",upAdmin);

        request.getSession().setAttribute("msg","修改管理员信息成功！");
        modelAndView.setViewName("admin-prefer");


        return modelAndView;
    }





}
