package com.watermark;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * description:控制器
 * @author Byron
 * @date 2016年2月20日 上午10:28:00
 * 
 */
public class WaterMarkAction extends ActionSupport {

	/* private File image; // 单个图片上传
	 private String imageFileName;
	 private PicInfo picInfo = new PicInfo();	//创建数据模型
	 */
	
	 //===================以上为单个图片添加（文字/图片水印），下面的是批量添加==========
	
	private File[] image;  // 多个图片上传
	private String[] imageFileName;
	private List<PicInfo> picInfo = new ArrayList<PicInfo>();
	
	private String uploadPath; // 文件上传的相对路径，要在struts.xml文件中配置

	/*
	 * 请求处理方法：负责接收图片上传以及水印添加的请求
	 */
	public String watermark() throws Exception {

		/*
		 * 单个图片上传处理逻辑
		 */
		 //文件上传的绝对路径是基于相对路径获取的 
		/* String realUploadPath = ServletActionContext.getServletContext()
				.getRealPath(uploadPath);
				
		 UploadService uploadService = new UploadService();
		
		 // 调用uploadService来完成图片上传
		 picInfo.setImageURL(uploadService.uploadImage(image, imageFileName,
				 uploadPath, realUploadPath));
		
		// MarkService markService = new TextMarkService();	//创建上传单个图片添加文字水印服务
		 
		// MarkService markService = new ImageMarkService();	//创建上传单个图片添加图片水印服务

		// MarkService markService = new MoreTextMarkService();	//创建上传多个图片添加文字水印服务

		 MarkService markService = new MoreImageMarkService();	//创建上传多个图片添加图片水印服务
		
		//将上传好之后图片的路径保存到数据变量中，会将picInfo变量返回到watermark.jsp页面中
		 picInfo.setLogoImageURL(markService.watermark(image, imageFileName,
		 uploadPath, realUploadPath));*/
		
		/*
		 * 批量添加图片水印
		 */
		String realUploadPath = ServletActionContext.getServletContext()
				.getRealPath(uploadPath);

		UploadService uploadService = new UploadService();
		
		// 创建水印服务类
		MarkService markService = new MoreImageMarkService();

		// 判断是否为多图片上传
		if (image != null && image.length > 0) {
			for (int i = 0; i < image.length; i++) {
				PicInfo pi = new PicInfo();

				pi.setImageURL(uploadService.uploadImage(image[i], imageFileName[i],
						uploadPath, realUploadPath));

				// 水印添加操作
				pi.setLogoImageURL(markService.watermark(image[i], imageFileName[i],
						uploadPath, realUploadPath));
				// 将有水印的图片添加的picInfo数组中
				picInfo.add(pi);
			}
		}
		return SUCCESS;
	}

	// ===========================================================
	
	/*  public File getImage(){ return image; }
	  
	  public String getImageFileName() { return imageFileName; }
	  
	  public void setImageFileName(String imageFileName) { this.imageFileName =
	  imageFileName; }
	 
	  public void setImage(File image) { this.image = image; }
	 */

	public File[] getImage() {
		return image;
	}

	public void setImage(File[] image) {
		this.image = image;
	}

	public String[] getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String[] imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/*public PicInfo getPicInfo() { 
	  return picInfo; 
	}
	
	public void setPicInfo(PicInfo picInfo) { 
		  this.picInfo = picInfo; 
	}*/
	
	public List<PicInfo> getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(List<PicInfo> picInfo) {
		this.picInfo = picInfo;
	}


}
