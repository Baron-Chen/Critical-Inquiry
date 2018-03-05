package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		ConnectDB db = new ConnectDB();
	    String id = request.getParameter("id");
	    BasicDBObject queryObject = new BasicDBObject("_id",new ObjectId(id)); 
		Document detail = db.getCollection_Commodity().find(queryObject).first();
		String content = detail.toJson();
		String json = "{\"success\":";
        if (content != null) {
            json += "true,\"result\":";
            json += "[";
            json += content;
            json += "]}";
        }
	    System.out.println(json);
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
