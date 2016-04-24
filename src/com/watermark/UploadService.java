package com.watermark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * description:图片文件上传服务类
 * @author Byron
 * @date 2016年2月20日 下午12:17:50
 *
 */
public class UploadService {

	public String uploadImage(File image, String imageFileName, String uploadPath, String realUploadPath){
		InputStream is = null;	//输入流
		OutputStream os = null;	//输出流
		
		try {
			is = new FileInputStream(image);	//创建输入流对象，指向上传图片对象
			os = new FileOutputStream(realUploadPath+"/"+imageFileName);	//创建输出流对象，指向最终要保存的目标文件对象
			
			byte[] buffer = new byte[1024];		
			int len = 0;
			
			while((len = is.read(buffer))>0){
				os.write(buffer);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try{
					is.close();	//关闭输入流
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			if(os!=null){
				try{
					os.close();	//关闭输出流
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
		}
		
		return uploadPath+"/"+imageFileName;	//相对路径
		
	}
}
