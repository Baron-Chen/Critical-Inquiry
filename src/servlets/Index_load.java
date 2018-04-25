package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;

import controller.ConnectDB;

/**
 * Servlet implementation class Index_load
 */
@WebServlet("/Index_load")
public class Index_load extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index_load() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String getContent(MongoCollection<Document> collection) {
		String string = new String();
		int i = 0;
		MongoCursor<Document> cursor = collection.find().sort(new BasicDBObject("_id",-1)).iterator();
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
		return string;
	}
	
	public String getContent(MongoCollection<Document> collection, String text) {
		String string = "";
		int i = 0;
		collection.createIndex(Indexes.text("$**"));
		MongoCursor<Document> cursor = collection.find(Filters.text("^.*"+text+".*$", new TextSearchOptions().language("english").caseSensitive(false)))
				.projection(Projections.metaTextScore("score"))
		        .sort(Sorts.metaTextScore("score"))
		        .iterator();
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
		return string;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
	    String text = request.getParameter("search");
	    String content = "";
	    if(text != "" && text != null) {
		    content = getContent(ConnectDB.getCollection_Commodity(), text);
	    } else {
		    content = getContent(ConnectDB.getCollection_Commodity());
	    }
	    String name = (String)session.getAttribute("name");
	    String json = "{\"success\":";
	    if (content != "") {
	        json += "true,\"result\":";
	        json += "[";
	        json += content;
	        json += "],";
	    }
	    else {
	    		json += "false,";
	    }
	    json += "\"user\":\"" + name + "\"}";
	    System.out.println(name);
//	    System.out.println(json);
		response.getWriter().append(json);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("name", "null");
	}

}
