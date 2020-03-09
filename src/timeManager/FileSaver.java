package timeManager;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FileSaver {
	public FileSaver(String filename) {
		this.filename = filename;
	}
	
	public boolean save(Table table) {
		FileOutputStream fileOutputStream;
		
		try {
			fileOutputStream = new FileOutputStream(this.filename);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open the json file!");
			return false;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("table", table.getAllPlan());
		
		try {
			fileOutputStream.write(jsonObject.toString().getBytes());
		} catch (IOException e) {
			System.out.println("Failed to save to the json file!");
			return false;
		}
		
		try {
			fileOutputStream.close();
		} catch (IOException e) {
			System.out.println("Failed to close the json file!");
			return false;
		}
		
		return true;
	}
	
	public Map<String, String> read() {
		Map<String, String> planMap = new HashMap<String, String>();
		
		File file = new File(this.filename);
		FileInputStream fileInputStream;
		
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open the json file!");
			return null;
		}
		
		Long fileLength = file.length();
		byte[] b = new byte[fileLength.intValue()];
		
		String string = new String();
		
		try {
			fileInputStream.read(b);
		} catch (IOException e) {
			System.out.println("Failed to read the json file!");
			return null;
		}
		
		try {
			string = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//System.out.println(string);
		
		JSONObject jsonObject = JSONObject.fromObject(string);
		jsonObject = jsonObject.getJSONObject("table");
		
		//System.out.println(jsonObject.toString());
		
		String key = new String();
		String value = new String();
		for (Iterator<?> iterator = jsonObject.keys(); iterator.hasNext();) {
			key = (String)iterator.next();
			value = jsonObject.getString(key);
			
			planMap.put(key, value);
		}
		
		//System.out.println(planMap.toString());
		
		return planMap;
	}
	
	private String filename;
}
