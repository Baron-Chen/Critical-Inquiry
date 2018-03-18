package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.*;

import controller.ConnectDB;

/**
 * Servlet implementation class Search_info
 */
@WebServlet("/Search_info")
public class Search_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search_info() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String text = request.getParameter("name");
	    String content = getContent(ConnectDB.getCollection_Commodity(), text);
	    String json = "{\"success\":";
        if (content != "") {
            json += "true,\"result\":";
            json += "[";
            json += content;
            json += "]}";
        }
        else {
        		json += "false}";
        }
	    System.out.println(content);
		response.getWriter().append(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String getContent(MongoCollection<Document> collection, String text) {
		String strArray[] = {"name", "brand", "capacity", "manufacturer", "country", "upc", "remarks"};
		String string = "";
		int i = 0;
		for(String str : strArray) {
			MongoCursor<Document> cursor = collection.find(Filters.regex(str, ".*"+text+".*", "i")).iterator();
			try {
			    while (cursor.hasNext()) {
			    	if (i == 0) {
			    		string += cursor.next().toJson();
			    	} else {
			    		string += "," + cursor.next().toJson();
			    	}
			     i++;  
			    }
			} finally {
			    cursor.close();
			}
		}
		return string;
	}

}
