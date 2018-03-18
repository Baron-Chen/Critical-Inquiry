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

import controller.Commodity;
import controller.ConnectDB;

/**
 * Servlet implementation class Update_data
 */
@WebServlet("/Update_data")
public class Update_data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update_data() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConnectDB db = new ConnectDB();
		Commodity commodity = new Commodity();
	    String id = request.getParameter("id");
	    String name = request.getParameter("name");
	    commodity.setName(name);
	    BasicDBObject queryObject = new BasicDBObject("_id",new ObjectId(id));
	    db.getCollection_Commodity().updateOne(queryObject, new Document("$set", commodity.getDoc()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
