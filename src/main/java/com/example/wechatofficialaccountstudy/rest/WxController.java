package com.example.wechatofficialaccountstudy.rest;

import com.alibaba.fastjson.JSONObject;
import com.example.wechatofficialaccountstudy.model.BaseXml;
import com.example.wechatofficialaccountstudy.model.LocationXmlReceiver;
import com.example.wechatofficialaccountstudy.model.TemplateMessageNotification;
import com.example.wechatofficialaccountstudy.model.TextXmlResponse;
import com.example.wechatofficialaccountstudy.service.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;


@Slf4j
@RestController
public class WxController {

    @Autowired
    WxService wxService;

    /**
     * 开发者服务器接入微信公众号平台
     *
     * @param req
     * @return
     */
    @GetMapping("/wx")
    public String access2server(HttpServletRequest req) {
        String sig = req.getParameter("signature");
        String times = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        log.debug("sig :" + sig);
        log.debug("times :" + times);
        log.debug("nonce :" + nonce);
        log.debug("echostr :" + echostr);
        System.out.println(sig);
        System.out.println(times);
        System.out.println(nonce);
        System.out.println(echostr);

        if (wxService.check(sig, times, nonce)) {
            log.debug("successfully access to wx developer platform");
            return echostr;
        }
        log.debug("access to wx developer platform failed");
        return "fail";
    }

    @PostMapping(value = "/wx", consumes = "text/xml", produces = "text/xml")
    public Object acceptUserMsg(@RequestBody BaseXml baseXml, HttpServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements())  {
            String s = parameterNames.nextElement();
            System.out.println(s);


        }
        System.out.println("hello");
        String msgType = baseXml.getMsgType();
        System.out.println(msgType);
        if(msgType.equalsIgnoreCase("event")) {
            TemplateMessageNotification xmlobj =  (TemplateMessageNotification)baseXml;
            System.out.println(xmlobj.getStatus());
            System.out.println("event");
        }

        if(msgType.equalsIgnoreCase("text")) {

            System.out.println("text");
        }

        //log.debug(locationXml.toString());
        //TextXmlResponse textXml = new TextXmlResponse();
        //textXml.setToUserName(locationXml.getFromUserName());
        //textXml.setFromUserName(locationXml.getToUserName());
        //textXml.setCreateTime("" + new Date().getTime() / 1000);
        //textXml.setMsgType("text");
        //textXml.setContent("您的经纬坐标 : "
        //        + "(" + locationXml.getLocation_X() + "," + locationXml.getLocation_Y() + ")"
        //        + "，您的位置：" + locationXml.getLabel());
        //log.debug(textXml.toString());
        return null;
    }



}

