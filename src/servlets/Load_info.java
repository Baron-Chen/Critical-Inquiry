package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import controller.ConnectDB;


/**
 * Servlet implementation class Load_info
 */
@WebServlet("/Load_info")
public class Load_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Load_info() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
	    String name = (String)session.getAttribute("name");
	    String id = request.getParameter("id");
		String json = "{\"success\":";
	    try {
	    	BasicDBObject queryObject = new BasicDBObject("_id",new ObjectId(id)); 
			Document detail = ConnectDB.getCollection_Commodity().find(queryObject).first();
			String content = detail.toJson();
	        if (content != null) {
	            json += "true,\"result\":";
	            json += "[";
	            json += content;
	            json += "],";
	        }
		} catch (Exception e) {
			// TODO: handle exception
			json += "false,\"result\":";
            json += ",";
		}
	    json += "\"user\":\"" + name + "\"}";
	    System.out.println(name);
		response.getWriter().append(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
