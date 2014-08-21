package com.owner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owner.onsants.Consant;
import com.owner.service.GirlsPictureService;
import com.owner.utils.GsonUtil;

public class BaiDuPicServLet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -778564842299038811L;

	/**
	 * Constructor of the object.
	 */
	public BaiDuPicServLet() {
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");
        System.out.println("get-----");
        int picid=Integer.parseInt(request.getParameter("picid"));
		/*PrintWriter out = response.getWriter();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("url", GirlsPictureService.downloadUrlToStream(picid));
		map.put("picid", picid+"");
		out.print(GsonUtil.getGson().toJson(map));*/
		request.setAttribute("url",GirlsPictureService.downloadUrlToStream(picid));
		request.setAttribute("picid", picid);
		request.getRequestDispatcher("./picshow.jsp").forward(request,response);
		//response.sendRedirect("picshow.jsp");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("post-----");
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");
        int picid=Integer.parseInt(request.getParameter("picid"));
        picid++;
		PrintWriter out = response.getWriter();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("url", GirlsPictureService.downloadUrlToStream(picid));
		map.put("picid", picid+"");
		out.print(GsonUtil.getGson().toJson(map));
		out.flush();
		out.close();
	}

}
