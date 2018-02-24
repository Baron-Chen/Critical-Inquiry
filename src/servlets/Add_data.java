package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Add_data
 */
@WebServlet("/Add_data")
public class Add_data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_data() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String json = new String();
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );// connect to the database
		    MongoDatabase mongoDatabase = mongoClient.getDatabase("lists");  
		    MongoCollection<Document> collection = mongoDatabase.getCollection("lists");
		    String name = request.getParameter("name");
		    Document doc = new Document("name", name);
		    collection.insertOne(doc);
		    json = "{\"success\": true }";
		} catch(Exception e){
		    json = "{\"success\": false }";
   	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
   	     }
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
