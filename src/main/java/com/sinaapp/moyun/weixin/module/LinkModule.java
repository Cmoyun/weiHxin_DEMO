package com.sinaapp.moyun.weixin.module;

import com.sinaapp.moyun.weixin.util.Filec;
import com.sinaapp.moyun.weixin.util.log.Hc;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@IocBean
@Ok("json:full")
public class LinkModule {

    @Inject("wxServiceObj")
    private WxMpService wxService;
    @Inject("wxRouterObj")
    private WxMpMessageRouter wxRouter;
    @Inject("wxConfigObj")
    private WxMpInMemoryConfigStorage wxConfig;
    private Properties wxProp;

    @At("/wx")
    @Ok("raw")
    public void wx(@Param("signature") String signature, @Param("timestamp") String timestamp,
                   @Param("nonce") String nonce, @Param("echostr") String echostr,
                   @Param("encrypt_type") String encryptType, @Param("msg_signature") String msgSignature,
                   HttpServletRequest request, HttpServletResponse response) throws IOException {

        Hc.v("wx", "[signature   ]" + signature +
                        "\n[timestamp   ]" + timestamp +
                        "\n[nonce       ]" + nonce +
                        "\n[echostr     ]" + echostr +
                        "\n[encryptType ]" + encryptType +
                        "\n[msgSignature]" + msgSignature
        );

        Hc.v("wxConfig", wxConfig);


        if (!wxService.checkSignature(timestamp, nonce, signature)) { // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }
        if (StringUtils.isNotBlank(echostr)) { // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }
        encryptType = StringUtils.isBlank(encryptType) ? "raw" : encryptType;

        if ("raw".equals(encryptType)) { // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = wxRouter.route(inMessage);
            System.out.println("  # --> 外面部分 明文 " + outMessage);
            if (outMessage != null) {
                // 说明是同步回复的消息
                // 将xml写入HttpServletResponse
                response.getWriter().write(outMessage.toXml());
            } else {
                // 说明是异步回复的消息，直接将空字符串写入HttpServletResponse
                response.getWriter().write("");
            }
            return;
        }

        if ("aes".equals(encryptType)) { // 是aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxConfig, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wxRouter.route(inMessage);
            response.getWriter().write(outMessage.toEncryptedXml(wxConfig));
            return;
        }

        response.getWriter().println("不可识别的加密类型");
        return;
    }

    /**
     * 获得关于微信的配置文件
     *
     * @param sc
     * @return
     */
    private Properties getWXProp(ServletContext sc) {
        if (wxProp == null) {
            InputStream is = sc.getResourceAsStream("/WEB-INF/classes/conf/WXconfig.properties");
            wxProp = Filec.getProperties(is);
        }
        return wxProp;
    }
}
