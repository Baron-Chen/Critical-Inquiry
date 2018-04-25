package servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

import controller.Commodity;
import controller.ConnectDB;


/**
 * Servlet implementation class Add_Commodity
 */
@WebServlet("/Add_Commodity")
public class Add_Commodity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_Commodity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Commodity commodity = new Commodity();
		request.setCharacterEncoding("utf-8");
        String savePath = request.getServletContext().getRealPath("/myImages");
        File mkdir = new File(savePath);
        if(!mkdir.exists()) {
        		mkdir.mkdir();
        }
        HashMap<String, String> map = new HashMap<String, String>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        String json = new String();
        String image = new String();
        try {
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
            int i = 0;
    			MongoCollection<Document> collection = ConnectDB.getCollection_Commodity();
            for (FileItem item : items) {
                if (!item.isFormField()) {
                     String prefix = item.getName().substring(item.getName().lastIndexOf(".")); 
                     SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
                     String datename = time.format(new Date());
                     File newfile = new File(savePath + "/" + datename + "post" + i + prefix);
                     item.write(newfile);
                     //System.out.println(item.getFieldName());
                     //System.out.println("savePath = " + savePath);
                     //System.out.println(newfile.getName());
             		image += "myImages" + "/" + datename + "post" + i + prefix + ",";
                    i++;
                } else {
                		map.put(item.getFieldName(), item.getString("utf-8"));
                }
            }
            commodity.setName(map.get("name"));
            commodity.setBrand(map.get("brand"));
            commodity.setCapacity(map.get("capacity"));
            commodity.setManufacture(map.get("manufacture"));
            commodity.setCountry(map.get("country"));
            commodity.setUpc(map.get("upc"));
            commodity.setRemarks(map.get("remarks"));
            commodity.setImage(image);
     		Document doc = commodity.getDoc();
     		collection.insertOne(doc);
     		json = "{\"success\": true }";
        } catch (Exception e) {
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
