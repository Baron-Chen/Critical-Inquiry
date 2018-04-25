package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import controller.ConnectDB;

import static com.mongodb.client.model.Filters.*;

/**
 * Servlet implementation class Del_data
 */
@WebServlet("/Del_data")
public class Del_data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Del_data() {
        super();
        // TODO Auto-generated constructor stub
    }
    private void removeImage(String images, String path) {
    	String[] image_list = images.split(",");
    	for (int i = 0; i < image_list.length; i++) {
    		if(image_list[i] != null && image_list[i] != "") {
    			try {  
    			      // Specify the file name and path 
    			      File file = new File(path + image_list[i]);
    			      if (file.isFile() && file.exists()) {
    			    	  file.delete();
    			      } else {  
    			        System.out.println("Delete failed.");  
    			      }  
    			    } catch (Exception e) {
    			      System.out.println(e);  
    			      e.printStackTrace();  
    			    }  	
    		}
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletContext().getRealPath("/");
		String json = new String();
		try{
			MongoCollection<Document> collection = ConnectDB.getCollection_Commodity();
		    String id = request.getParameter("id");
		    String images = request.getParameter("images");
		    removeImage(images, path);
		    BasicDBObject queryObject = new BasicDBObject("_id",new ObjectId(id)); 
			Document detail = ConnectDB.getCollection_Commodity().find(queryObject).first();
		    collection.deleteOne(detail);
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
