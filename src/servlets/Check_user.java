package servlets;

import static com.mongodb.client.model.Filters.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import controller.ConnectDB;

/**
 * Servlet implementation class Check_user
 */
@WebServlet("/Check_user")
public class Check_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check_user() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConnectDB db = new ConnectDB();
		String json = "{\"success\":";
	    MongoCollection<Document> collection = db.getCollection_User();
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    BasicDBObject query = new BasicDBObject();
	    Document myDoc_name = collection.find(eq("username", username)).first();
	    if(myDoc_name == null) {
            json += "false,\"result\":\"Don't have this user\"";
	    } else {
	        query.put("username", username);
	        query.put("password", password);
		    Document myDoc_info = collection.find(query).first();
		    if(myDoc_info != null) {
	            json += "true,\"result\":\"You are logged in\"";
		    } else {
	            json += "false,\"result\":\"Password is incorrect\"";
		    }
	    }
	    json += "}";
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
