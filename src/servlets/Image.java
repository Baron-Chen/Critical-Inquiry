package servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 * Servlet implementation class Image
 */
@WebServlet("/Image")
public class Image extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        String savePath = request.getServletContext().getRealPath("/myImages");
        Collection<Part> files = request.getParts();
        for (Part file : files) {
            String prefix = file.getName().substring(file.getName().lastIndexOf(".")); 
            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
            String name = time.format(new Date());
            file.write(savePath + "/" + name + "get" + prefix);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        String savePath = request.getServletContext().getRealPath("/myImages");
        File mkdir = new File(savePath);
        if(!mkdir.exists()) {
        		mkdir.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
            int i = 0;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                     String prefix = item.getName().substring(item.getName().lastIndexOf(".")); 
                     SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
                     String name = time.format(new Date());
                     File newfile = new File(savePath + "/" + name + "post" + i + prefix);
                     item.write(newfile);
                     //System.out.println(item.getFieldName());
                     //System.out.println("savePath = " + savePath);
                     //System.out.println(newfile.getName());
                     String json = new String();
             		try{
             			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );// connect to the database
             		    MongoDatabase mongoDatabase = mongoClient.getDatabase("lists");  
             		    MongoCollection<Document> collection = mongoDatabase.getCollection("lists");
             		    String image = "myImages" + "/" + name + "post" + i + prefix;
             		    Document doc = new Document("image", image);
             		    collection.insertOne(doc);
             		    json = "{\"success\": true }";
             		} catch(Exception e){
             		    json = "{\"success\": false }";
                	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                	     }
             		response.getWriter().append(json);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
