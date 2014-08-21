package com.owner.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.marker.weixin.DefaultSession;
import org.marker.weixin.HandleMessageAdapter;
import org.marker.weixin.MySecurity;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4Image;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Link;
import org.marker.weixin.msg.Msg4Location;
import org.marker.weixin.msg.Msg4Text;
import org.marker.weixin.msg.Msg4Video;
import org.marker.weixin.msg.Msg4Voice;

import com.owner.onsants.Consant;
import com.owner.service.GirlsPictureService;

/**
 * 处理微信服务器请求的Servlet URL地址：http://xxx/weixin/dealwith.do
 * 
 * 注意：官方文档限制使用80端口哦！
 * 
 * @author owner
 *@qq 756699074
 */
public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
     
    public WeixinServlet() {  }
     
     
    //TOKEN 是你在微信平台开发模式中设置的哦
    public static final String TOKEN = "owner";
    /**
     * 处理微信服务器验证
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
 
        // 重写totring方法，得到三个参数的拼接字符串
        List<String> list = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;
            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// 排序
        String tmpStr = new MySecurity().encode(list.toString(),
                MySecurity.SHA_1);// SHA-1加密
        Writer out = response.getWriter();
        if (signature.equals(tmpStr)) {
            out.write(echostr);// 请求验证成功，返回随机码
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }
 
    /**
     * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
     * 
     * 
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
    	InputStream is = request.getInputStream();
        OutputStream os = response.getOutputStream();
         
         
        final DefaultSession session = DefaultSession.newInstance(); 
         
        session.addOnHandleMessageListener(new HandleMessageAdapter(){
             
            @Override
            public void onTextMsg(Msg4Text msg) {
                //System.out.println("收到微信消息："+msg.getContent());
            	//1  代表美女图
                //if("1".equals(msg.getContent())){
                	//new GirlsPictureService().downloadUrlToStream(msg,is, os);
                //}
            	int picid=new Random().nextInt(999)+1;
            	String url=GirlsPictureService.downloadUrlToStream(picid);           	
                //回复一条消息
                Data4Item d1 = new Data4Item("", "",url,"http://127.0.0.1:8080/weixin/BaiDuPicServLet?picid="+picid);     
                Msg4ImageText mit = new Msg4ImageText();
                mit.setFromUserName(msg.getToUserName());
                mit.setToUserName(msg.getFromUserName()); 
                mit.setCreateTime("");
                mit.addItem(d1);
                               
                /*Msg4Link ml=new Msg4Link();
                ml.setFromUserName(msg.getToUserName());
                ml.setToUserName(msg.getFromUserName()); 
                ml.setCreateTime("");
                ml.setDescription("下一张");
                ml.setTitle("下一张");
                ml.setUrl(GirlsPictureService.downloadUrlToStream(new Random().nextInt(999)+1));*/
                 
                session.callback(mit);
            }

			@Override
			public void onErrorMsg(int errorCode) {
				// TODO Auto-generated method stub
				super.onErrorMsg(errorCode);
			}

			@Override
			public void onEventMsg(Msg4Event msg) {
				// TODO Auto-generated method stub
				super.onEventMsg(msg);
			}

			@Override
			public void onImageMsg(Msg4Image msg) {
				// TODO Auto-generated method stub
				super.onImageMsg(msg);
			}

			@Override
			public void onLinkMsg(Msg4Link msg) {
				// TODO Auto-generated method stub
				super.onLinkMsg(msg);
			}

			@Override
			public void onLocationMsg(Msg4Location msg) {
				// TODO Auto-generated method stub
				super.onLocationMsg(msg);
			}

			@Override
			public void onVideoMsg(Msg4Video msg) {
				// TODO Auto-generated method stub
				super.onVideoMsg(msg);
			}

			@Override
			public void onVoiceMsg(Msg4Voice msg) {
				// TODO Auto-generated method stub
				super.onVoiceMsg(msg);
			}
        });
         
        //必须调用这两个方法
                //如果不调用close方法，将会出现响应数据串到其它Servlet中。
        session.process(is, os);//处理微信消息 
        session.close();//关闭Session
    }
 
}