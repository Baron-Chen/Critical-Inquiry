package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import java.io.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.json.*;
import static com.mongodb.client.model.Filters.*;

/**
 * Servlet implementation class MyServlet
 */

@WebServlet("/MyServlet")

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public String getContent(MongoCollection<Document> collection) {
    		String string = new String();
    		int i = 0;
    		MongoCursor<Document> cursor = collection.find().iterator();
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
    
    
    public String ConnectDatabase() {
    	
    String content = new String();
   	 try{   
   	       // mongodb server
   	        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
   	       
   	         // connect to the database
   	         MongoDatabase mongoDatabase = mongoClient.getDatabase("lists");  
   	         MongoCollection<Document> collection = mongoDatabase.getCollection("lists");
   	         Document myDoc = collection.find().first();
   	         //content = myDoc.toJson();
   	         content = getContent(collection);
   	        
   	      }catch(Exception e){
   	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
   	     }
   	 return content;
   	 }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = ConnectDatabase();
		JSONObject jsonObject = new JSONObject(content); 
		String name = jsonObject.getString("name");
		response.setContentType("text/html;charset=UTF-8");
		// Allocate a output writer to write the response message into the network socket
		PrintWriter out = response.getWriter();
		// Write the response message, in an HTML page
		try {
		out.println("<!DOCTYPE html>");
		out.println("<html><head>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF- 8'>");
		out.println("<title>Hello, World</title></head>");
		out.println("<body>");
		out.println("<h1>Hello, world!</h1>"); // says Hello
		// Extract Form data
		out.println("<p>Username: " + request.getParameter("username") + "</p>"); out.println("<p>Gender: " + request.getParameter("gender") + "</p>"); out.println("<p>Year of study : " + request.getParameter("study_year") + "</p>");
		// Echo client's request information
		out.println("<p>Request URI: " + request.getRequestURI() + "</p>"); out.println("<p>Protocol: " + request.getProtocol() + "</p>"); out.println("<p>PathInfo: " + request.getPathInfo() + "</p>"); out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>"); // Generate a random number upon each request
		out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>"); out.println("<hr/>");
		out.println("<p>Content: <strong>" + name + "</strong></p>");
		out.println("</body>");
		out.println("</html>");
		} finally {
		out.close(); // Always close the output writer
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String content = ConnectDatabase();
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

}
