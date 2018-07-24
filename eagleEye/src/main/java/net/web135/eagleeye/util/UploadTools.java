package net.web135.eagleeye.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadTools {
	
	public static void save(String title, String timelength, File file) throws Exception{
		String path = "http://192.168.1.100:8080/videonews/ManageServlet";
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("timelength", timelength);
		FormFile formFile = new FormFile(file, "videofile", "audio/mpeg");
		SocketHttpRequester.post(path, params, formFile);
	}

}
