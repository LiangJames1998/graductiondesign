package com.gradution.chao.graductiondesign.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gradution.chao.graductiondesign.mapper.NationMapper;
import com.gradution.chao.graductiondesign.pojo.Blober;
import com.gradution.chao.graductiondesign.pojo.NationYao;
import com.gradution.chao.graductiondesign.pojo.NationZhuang;
import com.gradution.chao.graductiondesign.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/travel")
public class NationController {

    @Autowired
    NationService nationService;

    private final String[] pointMsgZhuang = {"建筑","民俗","语言","文字","习俗","风俗","节日","服饰","美食","文化","民风"};
    private final String[] pointMsgYao = {"观念","宗教","美食","文化","服饰","习俗","器乐","手艺","民歌","节日"};

    @RequestMapping("/nation-zhuang")
    public String nationZhuangView(Model model,
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
            List<NationZhuang> blobList = nationService.getNactionZhuang();

            for (NationZhuang yao : blobList){
                String pSt =  "'" + yao.getN_picture1() + "'";
                yao.setN_picture1(pSt);
            }

            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<NationZhuang> pageInfo = new PageInfo<NationZhuang>(blobList,pageSize);

            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("userList",blobList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }



        return "nation-zhuang";
    }



    @RequestMapping("/nation-yao")
    public String nationYaoView(Model model,
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
            List<NationYao> blobList = nationService.getNactionYao();

            for (NationYao yao : blobList){
                String pSt =  "'" + yao.getN_picture1() + "'";
                yao.setN_picture1(pSt);
            }

            //System.out.println("分页数据："+blobList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<NationYao> pageInfo = new PageInfo<NationYao>(blobList,pageSize);

            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("userYao",blobList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }


        return "nation-yao";
    }

    @RequestMapping("/nation-z-single")
    public String nationZhuangSingleView(Model model,
                                         @RequestParam(required = true,value="nation_id")Integer nation_id ,
                                         HttpSession session){

        //根据nation_id获取对应的文章信息
        model.addAttribute("nationMsg", nationService.getNationZbyID(nation_id));
        //文章浏览量加一
        nationService.addBrowseZhuang(nation_id);
        //图文推荐部分，壮族文化信息
        model.addAttribute("zhuangTui",nationService.getTuijianZNationMsg());

        /*  判断标题是否包含的 民俗 信息  */
        NationZhuang zPonit = nationService.getNationZbyID(nation_id);
        String poniyMsg = "";
        for (int i=0;i<pointMsgZhuang.length;i++){
            if (zPonit.getN_title().contains(pointMsgZhuang[i])){
                poniyMsg = pointMsgZhuang[i];
                break;
            }
        }
        if (poniyMsg.equals("")){
            model.addAttribute("pointZh",null);
        }else {
            model.addAttribute("pointZh",poniyMsg);
        }

        return "nation-z-single";
    }


    @RequestMapping("/nation-y-single")
    public String nationYaoSingleView(Model model,
                                      @RequestParam(required = true,value="nation_id")Integer nation_id ,
                                      HttpSession session){

        //根据nation_id获取对应的文章信息
        model.addAttribute("nationMsg", nationService.getNationYbyID(nation_id));

        //文章浏览量加一
        nationService.addBrowseYao(nation_id);

        //图文推荐部分，瑶族文化信息
        model.addAttribute("yaoTui",nationService.getTuijianYNationMsg());

        /*  判断标题是否包含的 民俗 信息  */
        NationYao yPoint = nationService.getNationYbyID(nation_id);
        String poniyMsg = "";
        for (int i=0;i<pointMsgYao.length;i++){
            if (yPoint.getN_title().contains(pointMsgYao[i])){
                poniyMsg = pointMsgYao[i];
                break;
            }
        }
        if (poniyMsg.equals("")){
            model.addAttribute("pointYao",null);
        }else {
            model.addAttribute("pointYao",poniyMsg);
        }


        return "nation-y-single";
    }


    /*   具体详情分类展示   */
    @RequestMapping("/nation-detail")
    public String nationDetailMsgView(Model model,
                                      @RequestParam(required = true,value="nation_ty")Integer nation_ty,
                                      @RequestParam(required = true,value="nation_str")String nation_str,
                                      @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                      @RequestParam(defaultValue="8",value="pageSize")Integer pageSize){
        /*
        nation_ty为民族总类，壮族=1，瑶族=2
        nation_str为匹配字符串内容,模糊查询
         */
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        PageHelper.startPage(pageNum,pageSize);
        //传参数
        model.addAttribute("n_str",nation_str);
        nation_str = "%" + nation_str + "%";
        if (nation_ty == 1){
            try {
                List<NationZhuang> blobList = nationService.getDetailZhuangMsgList(nation_str);

                for (NationZhuang yao : blobList){
                    String pSt =  "'" + yao.getN_picture1() + "'";
                    yao.setN_picture1(pSt);
                }

                PageInfo<NationZhuang> pageInfo = new PageInfo<NationZhuang>(blobList,pageSize);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("userList",blobList);
                model.addAttribute("nat_ty","zhuang");
            }finally {
                PageHelper.clearPage();
            }
        }else {
            try {
                List<NationYao> blobList = nationService.getDetailYaoMsgList(nation_str);
                for (NationYao yao : blobList){
                    String pSt =  "'" + yao.getN_picture1() + "'";
                    yao.setN_picture1(pSt);
                }
                PageInfo<NationYao> pageInfo = new PageInfo<NationYao>(blobList,pageSize);
                model.addAttribute("pageInfo",pageInfo);
                model.addAttribute("userList",blobList);
                model.addAttribute("nat_ty",null);
            }finally {
                //清理 ThreadLocal 存储的分页参数,保证线程安全
                PageHelper.clearPage();
            }
        }

        model.addAttribute("nat_ty",null);
        model.addAttribute("nat_ty",null);

        return "nation-detail";
    }


}
