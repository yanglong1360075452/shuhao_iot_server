package com.wizinno.shuhao.app.utils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.wizinno.shuhao.app.athoCode.Consttant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;
public class AuthoCode {
    public static String getAuthCode(String phone,Integer type) {
        //随机生成 num 位验证码
        String code="";
        Random r = new Random(new Date().getTime());
        for(int i=0;i<6;i++){
         code = code+r.nextInt(10);
         }
        TaobaoClient client = new DefaultTaobaoClient(Consttant.url, Consttant.appkey, Consttant.secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType("normal");
            req.setSmsFreeSignName(Consttant.sign);
            req.setSmsParamString("{\"code\":\""+code+"\",\"product\":\"数好生态\"}");
            req.setRecNum(phone);
           if(type==1){
               req.setSmsTemplateCode("SMS_34435186");
           }
           else if(type==2){
               req.setSmsTemplateCode("SMS_37605203");
           }



        //将验证码存入session
//        HttpSession session = request.getSession();
//        session.setAttribute(phone,code);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return code;

    }


}
