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
 * ����΢�ŷ����������Servlet URL��ַ��http://xxx/weixin/dealwith.do
 * 
 * ע�⣺�ٷ��ĵ�����ʹ��80�˿�Ŷ��
 * 
 * @author owner
 *@qq 756699074
 */
public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
     
    public WeixinServlet() {  }
     
     
    //TOKEN ������΢��ƽ̨����ģʽ�����õ�Ŷ
    public static final String TOKEN = "owner";
    /**
     * ����΢�ŷ�������֤
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");// ΢�ż���ǩ��
        String timestamp = request.getParameter("timestamp");// ʱ���
        String nonce = request.getParameter("nonce");// �����
        String echostr = request.getParameter("echostr");// ����ַ���
 
        // ��дtotring�������õ�����������ƴ���ַ���
        List<String> list = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;
            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// ����
        String tmpStr = new MySecurity().encode(list.toString(),
                MySecurity.SHA_1);// SHA-1����
        Writer out = response.getWriter();
        if (signature.equals(tmpStr)) {
            out.write(echostr);// ������֤�ɹ������������
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }
 
    /**
     * ����΢�ŷ������������ĸ�����Ϣ���������ı���ͼƬ������λ�á����ֵȵ�
     * 
     * 
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
    	InputStream is = request.getInputStream();
        OutputStream os = response.getOutputStream();
         
         
        final DefaultSession session = DefaultSession.newInstance(); 
         
        session.addOnHandleMessageListener(new HandleMessageAdapter(){
             
            @Override
            public void onTextMsg(Msg4Text msg) {
                //System.out.println("�յ�΢����Ϣ��"+msg.getContent());
            	//1  ������Ůͼ
                //if("1".equals(msg.getContent())){
                	//new GirlsPictureService().downloadUrlToStream(msg,is, os);
                //}
            	int picid=new Random().nextInt(999)+1;
            	String url=GirlsPictureService.downloadUrlToStream(picid);           	
                //�ظ�һ����Ϣ
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
                ml.setDescription("��һ��");
                ml.setTitle("��һ��");
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
         
        //�����������������
                //���������close���������������Ӧ���ݴ�������Servlet�С�
        session.process(is, os);//����΢����Ϣ 
        session.close();//�ر�Session
    }
 
}