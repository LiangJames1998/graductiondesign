package com.gradution.chao.graductiondesign.controller;


import com.gradution.chao.graductiondesign.pojo.*;
import com.gradution.chao.graductiondesign.service.*;
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
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@RequestMapping("/travel")
public class ManageController {


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


    @RequestMapping("manage-index")
    public String ManageIndexView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        User toUser = (User)objectU;

        request.setAttribute("userMsg",toUser);

        /*  获取公告信息   */
        /*  查询公告信息  */
        List<Mmsg> mmsgFirst = adminService.getAdminMmsgByMtype(1);
        for(Mmsg msgShow : mmsgFirst){
            request.setAttribute("mmsgFirst",msgShow);
            break;
        }

        /*  查询用户问题与回答  */
        List<Mmsg> userAsk = adminService.getAdminMmsgByMtype(5);
        request.setAttribute("userAsk",userAsk);

        /*  获取点击信息  */
        request.setAttribute("usData1",userService.findUserMsgDataBrowse(toUser.getU_id()));
        request.setAttribute("usData2",userService.findUserMsgDatablob(toUser.getU_id()));
        request.setAttribute("usData3",userService.findUserMsgDataComments(toUser.getU_realname()));


        return "manage-index";
    }


    @RequestMapping("manage-prefer")
    public String ManagePrerferView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        User toUser = (User)objectU;

        request.setAttribute("userMsg",toUser);

        return "manage-prefer";
    }



    @RequestMapping("manage-blog")
    public String ManageBlogView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        User toUser = (User)objectU;

        /**
         * 查询博客浏览记录
         *
         */

        List<Browseblog> browseblog = mainService.findBrowseByUID(toUser.getU_id());

        List<BrowseCombine> userCombine1 = new ArrayList<>();

        for(Browseblog  blogM : browseblog){

            Blober blober = mainService.findBlobByBID(blogM.getB_blogid());

            BrowseCombine browseCombine = new BrowseCombine();

            //设置添加 Browseblog内的信息
            browseCombine.setBr_id(blogM.getB_id());
            browseCombine.setBr_userid(blogM.getB_userid());
            browseCombine.setBr_blogid(blogM.getB_blogid());
            browseCombine.setBr_time(blogM.getB_time());

            if(blober != null){
                //设置添加 Blober内的信息
                browseCombine.setBr_title(blober.getB_title());
                browseCombine.setBr_author(blober.getB_author_name());
                browseCombine.setBr_clicked(blober.getB_clicked());
                browseCombine.setBr_comment(blober.getB_comment());
            }

            userCombine1.add(browseCombine);
        }

        request.setAttribute("userMsg",toUser);

        request.setAttribute("browswMsg",userCombine1);


        /**
         * 查询旅游景点记录
         *
         */

        List<Browsetravel> browsetravels = mainService.findBrowseTravelByUID(toUser.getU_id());

        List<BrowseCombine> userCombine2 = new ArrayList<>();

        for(Browsetravel  blogTravel : browsetravels){

            //Blober blober = mainService.findBlobByBID(blogM.getB_blogid());

            Traveler traveler = mainService.findTravelingByTID(blogTravel.getB_travelid());

            BrowseCombine browseCombineTravel = new BrowseCombine();

            //设置添加 BrowseTravle内的信息
            browseCombineTravel.setBr_id(blogTravel.getB_id());
            browseCombineTravel.setBr_userid(blogTravel.getB_userid());
            browseCombineTravel.setBr_blogid(blogTravel.getB_travelid());
            browseCombineTravel.setBr_time(blogTravel.getB_time());

            if(traveler != null){
                //设置添加 Traveler内的信息
                browseCombineTravel.setBr_title(traveler.getT_name());
                browseCombineTravel.setBr_city(traveler.getT_city());
                browseCombineTravel.setBr_clicked(traveler.getT_clicked());
                browseCombineTravel.setBr_customer("壮族风情");
            }

            userCombine2.add(browseCombineTravel);
        }

        request.setAttribute("browseTravelMsg",userCombine2);


        /**
         * 查询用户发布的博客记录
         *
         */
        List<Blober> upuBlober = mainService.getUserPublishBlober(toUser.getU_id());
        request.setAttribute("userPublish",upuBlober);

        return "manage-blog";
    }


    @RequestMapping("manage-publish")
    public String ManagePublishView(HttpServletRequest request){

        Object objectU = request.getSession().getAttribute("userNow");

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            return "redirect:/travel/index";
        }

        User toUser = (User)objectU;

        request.setAttribute("userMsg",toUser);

        return "manage-publish";
    }



    @RequestMapping(value = "/publiccomments",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> publishComment(@RequestParam("content") String content,
                                              @RequestParam("blog_id") String blog_id,
                                              HttpServletRequest request){

        //判断用户是否登录
        Object objectU = request.getSession().getAttribute("userNow");

        Map<String, String> ret = new HashMap<String, String>();

        if(content == ""){
            ret.put("type","error");
            ret.put("msg","请输入评论内容...");
            return ret;
        }

        if(content.length() > 512){
            ret.put("type","error");
            ret.put("msg","评论字数不能超过512个字符...");
            return ret;
        }

        //判断session内的user是否为空
        if(objectU == null){
            //sessoion为空，用户未登录
            ret.put("type","error");
            ret.put("msg","您未登录，请登录后操作！");

            return ret;
        }

        User nowU = (User)objectU;

        Comment comment = new Comment();
        comment.setC_person(nowU.getU_realname());
        comment.setC_person_img(nowU.getU_headimg());
        comment.setC_time(new Date());
        comment.setC_text(content);
        comment.setC_articleid(Integer.valueOf(blog_id));

        mainService.publishComments(comment);
        mainService.addArticleComments(Integer.valueOf(blog_id));

        ret.put("type", "success");
        ret.put("msg", "发表成功!");
        return ret;
    }




    /**
     * 用户更改个人信息 头像信息等Controller
     *
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public ModelAndView uploadUser(HttpServletRequest request,
                                   @RequestParam("inputRealName") String inputRealName,
                                   @RequestParam("inputAutograph") String inputAutograph,
                                   @RequestParam("inputTel") String inputTel,
                                   @RequestParam("inputEmail") String inputEmail,
                                   @RequestParam("inputHomeTown") String inputHomeTown,
                                   @RequestParam("inputBirthday") String inputBirthday,
                                   @RequestParam("inputNewPassword") String inputNewPassword,
                                   @RequestParam("inputConfirmNewPassword") String inputConfirmNewPassword,
                                   @RequestParam("fileToUpload") MultipartFile file ) {

        ModelAndView modelAndView = new ModelAndView();

        //判断两个密码是否一样
        if(!inputNewPassword.equals(inputConfirmNewPassword)){
            request.getSession().setAttribute("msg","两次密码不一致！");
            modelAndView.setViewName("redirect:/travel/manage-prefer");
            return modelAndView;
        }

        Object object = request.getSession().getAttribute("userNow");
        User uSession = (User)object;

        User upUser = new User();
        upUser.setU_id(uSession.getU_id());

        upUser.setU_realname(inputRealName);
        upUser.setU_hometown(inputHomeTown);
        upUser.setU_email(inputEmail);
        upUser.setU_tel(inputTel);
        upUser.setU_autograph(inputAutograph);

        //日期格式转换
        SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputdate = simpleDate.parse(inputBirthday);
            //填写新日期
            upUser.setU_birthday(inputdate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        //判断密码是否不为空，空则不做更改
        if(inputNewPassword.equals("")){
            upUser.setU_password(uSession.getU_password());
        }else {
            // MD5转换
            String encodePassword = new Md5Hash(inputNewPassword).toString().toUpperCase();

            upUser.setU_password(encodePassword);
        }

        if(file.isEmpty()){
            upUser.setU_headimg(uSession.getU_headimg());
        }else {
            try {
                String fileName = System.currentTimeMillis()+file.getOriginalFilename();

                MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
                InputStream is = file.getInputStream();
                String contentType = file.getContentType();
                minioClient.putObject("user",fileName,is,contentType);
                upUser.setU_headimg(minioClient.presignedGetObject("user", fileName));

            } catch (Exception e) {
                System.out.println("上传失败1111");
                request.getSession().setAttribute("msg","上传出错！");
                modelAndView.setViewName("redirect:/travel/manage-prefer");
                return modelAndView;
            }
        }

        System.out.println(upUser.toString());

        userService.updateUserInfor(upUser);

        //更改当前session信息
        request.getSession().setAttribute("userNow",upUser);

        //request.setAttribute("msg","修改个人信息成功！");
        request.getSession().setAttribute("msg","修改个人信息成功！");
        modelAndView.setViewName("redirect:/travel/manage-prefer");
        return modelAndView;
    }


    /**
     * 删除对应的浏览记录---博客篇
     * @param b_id
     * @return
     */
    @RequestMapping("/deletebrowse")
    public ModelAndView deleteBrowse(@RequestParam(required = true,value="b_id") Integer b_id  ){

        ModelAndView modelAndView = new ModelAndView();

        mainService.deleteBrowseByBID(b_id);

        modelAndView.setViewName("redirect:/travel/manage-blog");

        return modelAndView;
    }


    /**
     * 删除对应的浏览记录---旅游景点篇
     * @param b_id
     * @return
     */
    @RequestMapping("/deletebrowsetravel")
    public ModelAndView deleteBrowseTravel(@RequestParam(required = true,value="b_id") Integer b_id  ){

        ModelAndView modelAndView = new ModelAndView();

        mainService.deleteBrowseTravel(b_id);

        modelAndView.setViewName("redirect:/travel/manage-blog");

        return modelAndView;
    }


    /**
     *   删除自己发布的 博客 信息
     * @param b_id
     * @return
     */
    @RequestMapping("/deleteblobbyid")
    public ModelAndView deleteBloberByBID(@RequestParam(required = true,value="b_id") Integer b_id  ){

        ModelAndView modelAndView = new ModelAndView();

        mainService.deleteBloberByBID(b_id);

        modelAndView.setViewName("redirect:/travel/manage-blog");

        return modelAndView;
    }





    /**
     * 用户发布博客文章
     * @param request
     * @param inputTitle
     * @param paragraph1
     * @param paragraph2
     * @param paragraph3
     * @param file
     * @return
     */
    @RequestMapping("/blogpublish")
    public ModelAndView userPublishBlog(HttpServletRequest request,
                                        HttpSession session,
                                        @RequestParam("inputTitle") String inputTitle,
                                        @RequestParam("paragraph1") String paragraph1,
                                        @RequestParam("paragraph2") String paragraph2,
                                        @RequestParam("paragraph3") String paragraph3,
                                        @RequestParam("fileToUpload") MultipartFile[] file ){

        ModelAndView modelAndView = new ModelAndView();

        if(StringUtils.isEmpty(inputTitle)){

            session.setAttribute("publicmsg","请输入文章标题...");
            modelAndView.setViewName("manage-publish");
            return modelAndView;
        }

        Object object = session.getAttribute("userNow");
        User uSession = (User)object;

        Blober blober = new Blober();
        blober.setB_author_id(uSession.getU_id());
        blober.setB_author_autograph(uSession.getU_autograph());
        blober.setB_author_name(uSession.getU_realname());
        blober.setB_time(new Date());
        blober.setB_title(inputTitle);
        blober.setB_text1(paragraph1);
        blober.setB_text2(paragraph2);
        blober.setB_text3(paragraph3);

        //开始上传多文件
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

                    minioClient.putObject("blog",fileName,is,contentType);
                    //存储路径
                    switch (i){
                        case 0:
                            blober.setB_picture1(minioClient.presignedGetObject("blog", fileName));
                            break;
                        case 1:
                            blober.setB_picture2(minioClient.presignedGetObject("blog", fileName));
                            break;
                        case 2:
                            blober.setB_picture3(minioClient.presignedGetObject("blog", fileName));
                            break;
                        default:
                            System.out.println("出错了！");
                    }
                }
            }
        }catch (Exception e){

            //System.out.println("上传失败");
            session.setAttribute("publicmsg","上传出错！");
            modelAndView.setViewName("manage-publish");
            return modelAndView;
        }

        //获取缓存check数据
        Object chech = session.getAttribute("toAdminCheck");
        //设置 4000 - 6000的随机数
        int random = (int)(Math.random()*2000+4000);
        blober.setB_id(random);
        if (chech != null){

            List<Blober> toAdminCheck = new ArrayList<>();
            if (chech instanceof ArrayList<?>){
                for (Object o : (List<?>) chech ){
                    toAdminCheck.add((Blober) o);
                }
            }

            toAdminCheck.add(blober);
            //添加博客信息至 缓存中去，让管理员进行审核
            session.setAttribute("toAdminCheck",toAdminCheck);
        }else {
            List<Blober> toAdminCheck = new ArrayList<>();
            toAdminCheck.add(blober);
            session.setAttribute("toAdminCheck",toAdminCheck);
        }

        request.setAttribute("publicmsg","发布成功！等待管理员进行审核！");

        modelAndView.setViewName("manage-publish");
        return modelAndView;
    }


    /**
     *   向管理员提问题
     * @param askcontent
     * @param request
     * @return
     */
    @RequestMapping("/userAsk")
    public String userASKing(@RequestParam("askcontent") String askcontent, HttpServletRequest request){

        if(StringUtils.isEmpty(askcontent)){
            return "redirect:/travel/manage-index";
        }

        Object userNow = request.getSession().getAttribute("userNow");

        User user = (User)userNow;

        //存入数据库
        Mmsg mmsg = new Mmsg();
        mmsg.setM_type(5);
        mmsg.setM_msg(askcontent);
        mmsg.setM_mid(user.getU_id());
        mmsg.setM_title(user.getU_realname());
        adminService.addAdminMmsgByMid(mmsg);

        return "redirect:/travel/manage-index";
    }


}
