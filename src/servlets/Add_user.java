package servlets;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import controller.ConnectDB;

/**
 * Servlet implementation class Add_user
 */
@WebServlet("/Add_user")
public class Add_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_user() {
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
	    Document myDoc_name = collection.find(eq("username", username)).first();
	    if(myDoc_name != null) {
            json += "false,\"result\":\"User already exists\"";
	    } else {
		    Document doc = new Document("username", username)
		               .append("password", password);
		    collection.insertOne(doc);
            json += "true,\"result\":\"Registered successfully\"";
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
