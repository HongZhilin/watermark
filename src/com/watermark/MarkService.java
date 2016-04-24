package com.watermark;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

public interface MarkService {

	public static final String MARK_TEXT = "Byron";
	public static final String FONT_NAME = "微软雅黑";
	public static final int FONT_STYLE = Font.BOLD;	//黑体
	public static final int FONT_SIZE = 20;			//文字大小
	public static final Color FONT_COLOR = Color.red;//文字颜色
	
	public static final int X = 10;  //文字坐标
	public static final int Y = 10;
	
	public static float ALPHA = 0.3F; //文字水印透明度 
	
	public static final String LOGO = "logo.gif";	//图片形式的水印
	
	public String watermark(File image,String imageFileName,String uploadPath,String realUploadPath);
	
}
