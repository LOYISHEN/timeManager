package timeManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import timeManager.Plan.Plan;
import timeManager.Plan.PlanContent;
import timeManager.Plan.PlanTime;

public class FileSaver {
	public static final String NAME_OF_TABLE = "table";

	private String filename;
	private String errorString;

	public FileSaver(String filename) {
		this.filename = filename;
		this.setErrorString("");
	}

	// 保存时间表到文件
	public boolean save(Table table) {
		FileOutputStream fileOutputStream;
		Writer writer;

		// 打开文件
		try {
			fileOutputStream = new FileOutputStream(this.filename);
			writer = new OutputStreamWriter(fileOutputStream, "utf-8"); // 解决不能写入中文的问题
		} catch (FileNotFoundException e) {
			this.setErrorString("Failed to open the json file!");
			System.out.println("Failed to open the json file!");
			return false;
		} catch (UnsupportedEncodingException e) {
			this.setErrorString("unsupported encoding exception, FileSaver.java:save()");
			System.out.println("unsupported encoding exception, FileSaver.java:save()");
			return false;
		}

		// 新建一个json对象
		JSONObject jsonObject = new JSONObject();
		// 设置json对象
		jsonObject.put(NAME_OF_TABLE, table.getAllPlan());

		// 写入文件
		try {
			writer.write(jsonObject.toString());
		} catch (IOException e) {
			System.out.println("Failed to save to the json file!");
			this.setErrorString("Failed to save to the json file!");
			return false;
		}

		// 关闭文件对象
		try {
			// 必须先关闭writer，再关闭fileOutputStream，否则会有异常
			writer.close();
			fileOutputStream.close();
		} catch (IOException e) {
			System.out.println("Failed to close the json file!");
			this.setErrorString("Failed to close the json file!");
			return false;
		}
		
		return true;
	}

	// 从文件读取时间表
	public ArrayList<Plan> read() {
		ArrayList<Plan> planArrayList = new ArrayList();

		File file = new File(this.filename);
		FileInputStream fileInputStream;

		// 打开文件
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open the json file!");
			this.setErrorString("Failed to open the json file!");
			return null;
		}

		// 获取文件长度
		Long fileLength = file.length();
		// 为文件内容增添空间
		byte[] b = new byte[fileLength.intValue()];

		String string = new String();

		// 读取文件内容
		try {
			fileInputStream.read(b);
		} catch (IOException e) {
			System.out.println("Failed to read the json file!");
			this.setErrorString("Failed to read the json file!");
			return null;
		}

		// 文件内容转码
		try {
			string = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.setErrorString("unsupported encoding exception, FileSaver.java:read()");
			System.out.println("unsupported encoding exception, FileSaver.java:read()");
			return null;
		}

		// 读取json对象数据
		JSONObject jsonObject;
		JSONArray jsonArray;
		try {
			// 从string中载入jsonObject
			jsonObject = JSONObject.fromObject(string);
			// 从json中载入table对象
			jsonArray = jsonObject.getJSONArray(NAME_OF_TABLE);
			//jsonObject = jsonObject.getJSONObject(NAME_OF_TABLE);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			this.setErrorString("Failed to read table object!");
			return null;
		}

		// 转换json对象为Plan类型对象
		JSONObject obj = null;
		JSONObject planTimeJSONObject = null;
		JSONObject planContentJSONObject = null;
		for (Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext(); ) {
			// 假如有任何异常就直接跳过该记录
			try {
				obj = iterator.next();
				planTimeJSONObject = obj.getJSONObject("time");
				planContentJSONObject = obj.getJSONObject("planContent");
			} catch (Exception e) {
				System.out.println("exception in timeManager.read");
				continue;
			}

			PlanTime planTime = new PlanTime(planTimeJSONObject.getInt("day"),
												planTimeJSONObject.getInt("hour"),
												planTimeJSONObject.getInt("minute"));
			PlanContent planContent = new PlanContent(planContentJSONObject.getString("content"));

			// 添加一条计划
			planArrayList.add(new Plan(planTime, planContent));
			System.out.println(planArrayList.get(planArrayList.size()-1));
		}

		try {
			fileInputStream.close();
		} catch (IOException e) {
			System.out.println("Failed to close the file!");
			this.setErrorString("Failed to close the file!");
		}

		return planArrayList;
	}
	
	// 提供给外部获取错误信息
	public String getErrorString() {
		return this.errorString;
	}
	
	// 内部设置错误信息
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
}
