package com.cang.os.mgr.sys.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.WebUtils;
import com.cang.os.bean.sys.Applicants;
import com.cang.os.bean.wz.Navigation;
import com.cang.os.common.core.OperatorConstant;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.mgr.sys.service.ApplicantsService;
import com.cang.os.mgr.sys.service.impl.ApplicantsServiceImpl;
import com.cang.os.security.utils.SessionUserUtils;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.jy.OrderDiscountDefine;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 37cang-1 on 2019/1/3.
 */
@Controller
@RequestMapping("/applicants")
public class ApplicantsController {

    private static final Logger log = LoggerFactory.getLogger(ApplicantsController.class);

    @Autowired
    ApplicantsServiceImpl applicantsServiceImpl;

    @Autowired
    ApplicantsService applicantsService;

    @Autowired
    MongoTemplate m;


    @Value("${smerchantCode}")
    private String smerchantCode;

    @Value("${msgService}")
    private String msgService;

    @Value("${smerchantId}")
    private String smerchantId;

    @Value("${adminTel}")
    private String adminTel;

    /**
     * ???????????????
     **/
    @ResponseBody
    @RequestMapping(value = "/captcha", method = RequestMethod.POST)
    String aCaptcha(HttpSession session, HttpServletRequest request) {
        System.out.println("?????????-???" + session.getAttribute("KAPTCHA_SESSION_KEY"));
        String text = request.getParameter("captcha");
        System.out.println("?????????????????????" + text);
        String captcha = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
        // return  captcha.equals(text);
        if (captcha.equals(text)) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * ??????????????????????????????
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo save(HttpServletRequest request, @ModelAttribute("TestAccount") Applicants applicants) throws Exception {

        Date date = new Date();//??????????????????
        applicants.setCreatTime(date);
        applicants.setUpdateTime(date);
        applicants.setStatus("?????????");
        applicants.setIp(InetAddress.getLocalHost().toString().split("/")[1]);
        System.out.println("yonghuming->" + request.getParameter("userName"));
        applicantsServiceImpl.save(applicants);
        String linkMan = applicants.getLinkMan();
        String tel = applicants.getTel();
        String accountType = applicants.getAccountType();
        String userName = applicants.getUserName();
        String require = applicants.getRequire();//????????????
        MessageDto messageDto = new MessageDto();
        messageDto.setSmerchantId(smerchantId);
        messageDto.setSmerchantCode(smerchantCode);
        //??????
        Map<String, Object> contentParam = new HashMap<String, Object>();
        contentParam.put("tel", tel);
        contentParam.put("accountType", accountType);
        contentParam.put("userName", userName);
        contentParam.put("linkMan", linkMan);
        messageDto.setContentParam(contentParam);
        messageDto.setMobile(adminTel);
        System.out.println("tel->");
        messageDto.setTemplateType("application_account");
        /**??????????????????????????????*/
        Map map = new HashMap();

        map.put("param", JSON.toJSONString(messageDto));
        String str_url = msgService;

        String status = WebUtils.doPost(str_url, map, "utf-8", 2000, 2000, null, 0);
        log.info("??????????????????????????????:{}",status);
        return ResponseVo.getSuccessResponse();
      /*  MessageDto messageDto = new MessageDto();
        messageDto.setSmerchantId("f6befd4fff6e11e7be44000c2937a246");
        messageDto.setSmerchantCode("OP201801220037");
        // ????????????
      //  messageDto.setTemplateType("reset_password");
        //????????????
        //??????
        Map<String, Object> contentParam = new HashMap<String, Object>();
        //contentParam.put("tel", applicants.getTel());
        messageDto.setContentParam(contentParam);
        messageDto.setMobile(tel);
        messageDto.setTemplateType("application_account");

        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//????????????

        invoke.setRequestObj(messageDto); //post ??????
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();*/
    }


    /***??????????????????????????????????????????**/
/*
    @RequestMapping(value = "/save1/{accountType}", method = RequestMethod.POST)
    public String save1(HttpServletRequest request, Applicants applicants, @PathVariable String accountType) throws Exception {

        Date date = new Date();//??????????????????
        applicants.setCreatTime(date);
        applicants.setUpdateTime(date);
        applicants.setStatus("?????????");
        applicants.setAccountType(accountType);
        applicants.setIp(InetAddress.getLocalHost().toString().split("/")[1]);

        System.out.println("yonghuming->" + request.getParameter("userName"));
        applicantsServiceImpl.save(applicants);
        String tel = applicants.getTel();

        return "redirect:/cn/index/";

    }*/

    /**
     * ????????????????????????
     **/
    @RequestMapping("/list")
    public String applicantsList(ModelMap map, ParamPage paramPage, Applicants applicants) {
        Page<Applicants> page = new Page<Applicants>();
        page.setPageNum(paramPage.pageNo());
        page.setPageSize(paramPage.getLimit());
       /*
        List<Applicants> list = applicantsServiceImpl.findAll();
        map.put("applicants", list);
        for (Applicants a : list) {
            System.out.println("a->" + a);
        }*/
        Query query = new Query();
        if (StringUtils.isNotBlank(applicants.getUserName())) {
            System.out.println("applicants.getUserName()" + applicants.getUserName());
            query.addCriteria(Criteria.where("userName").regex(MongoDbUtil.getLikeQueryCondition(applicants.getUserName())));

        }
        /*if (StringUtils.isNotBlank(applicants.getSname())) {
            query.addCriteria(Criteria.where("sname").regex(MongoDbUtil.getLikeQueryCondition(navigation.getSname())));
        }*/
        query.with(new Sort(Sort.Direction.ASC, "isort").and(new Sort(Sort.Direction.DESC, "creatTime")));
        page = applicantsService.findPage(page, query);
        map.put("page", page);
        return "mgr/sys/applicantsList";
    }

    /***????????????_id??????????????????**/
    @RequestMapping("/delete")
    public ModelAndView delete(HttpServletRequest request, String backUri) {

        try {
            String _id = request.getParameter("_id");
            System.out.println("backUri" + backUri);

            if (StringUtils.isNotBlank(_id)) {

                Query applicants = new Query(Criteria.where("_id").is(_id));
                applicantsServiceImpl.remove(applicants);
            }

            return new ModelAndView("redirect:/mgr/success").addObject("backUri", backUri);
        } catch (Exception e) {
            return new ModelAndView("redirect:/mgr/error").addObject("backUri", backUri);
        }


    }

    /***??????_id??????????????????**/
    @RequestMapping("/selece/{_id}")
    public String select(ModelMap map, @PathVariable String _id) {
        if (StringUtils.isNotBlank(_id)) {
            Query select = new Query(Criteria.where("_id").is(_id));
            List<Applicants> list = applicantsServiceImpl.find(select);
            System.out.println("????????????-->" + list.size());
            map.put("applicant", list.get(0));
        }
        return "mgr/sys/applicantsEdit";

    }

    /**
     * ??????????????????
     */
    @RequestMapping("/update")
    public ModelAndView update(Applicants applicant, String backUri, HttpServletRequest req) {

        Query q = new Query();
        Criteria c = new Criteria();
        q.addCriteria(Criteria.where("_id").is(req.getParameter("_id")));
        String CollectionName = "applicants";
        Update u = new Update();
        u.set("userName", applicant.getUserName());
        u.set("linkMan", applicant.getLinkMan());
        u.set("tel", applicant.getTel());
        u.set("require", applicant.getRequire());
        u.set("remarks", applicant.getRemarks());
        u.set("status", req.getParameter("status"));
        u.set("accountType", applicant.getAccountType());
        u.set("operator", SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        u.set("operationTime", applicant.getOperationTime());
        u.set("creatTime", applicant.getCreatTime());
        u.set("ip", applicant.getIp());
        u.set("updateTime", new Date());
        System.out.println(applicant.getStatus());

        m.updateFirst(q, u, CollectionName);


        System.out.println("??????-???" + applicant);
        applicant.set_id(req.getParameter("_id"));
        System.out.println(req.getParameter("_id"));
       /* applicantsServiceImpl.save(applicant);
        Query select = new Query(Criteria.where("_id").is(applicant.get_id()));
        applicantsServiceImpl.update(applicant);
        Update update = new Update();
        update.({'title':'MongoDB ??????'},{$set:{'title':'MongoDB'}})
        return "";
        region.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName()); //??????????????????????????????

        applicantsService.update(applicant);*/
           /* map.put("sRes", "save_suc");
        } catch (Exception e) {
            map.put("sRes", "??????????????????????????????");
            return new ModelAndView("redirect:/mgr/error").addObject("backUri",backUri);
        }
        return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);*/

        return new ModelAndView("redirect:/mgr/success").addObject("backUri", backUri);


    }

    /***?????????????????????????????????**//*
    @RequestMapping("/findOne")
    public String findOne(HttpServletRequest request, ModelMap map) {
        String userName = request.getParameter("userName");
        if (StringUtils.isNotBlank(userName)) {
            System.out.println("?????????=" + "userName");
            Query select = new Query(Criteria.where("userName").is(userName));
            List<Applicants> list = applicantsServiceImpl.find(select);
            System.out.println("????????????-->" + list.size());
            map.put("applicants", list);
        } else {
            List<Applicants> list = applicantsServiceImpl.findAll();
            map.put("applicants", list);
        }

        *//*List<Applicants> list = applicantsServiceImpl.findAll();
        applicantsServiceImpl.find(Query query);*//*

        return "mgr/sys/applicantsList";
    }*/
}
