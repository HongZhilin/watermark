package com.watermark;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 实现添加多个图片水印
 */
public class MoreImageMarkService implements MarkService{

	@Override
	public String watermark(File image, String imageFileName,
			String uploadPath, String realUploadPath) {

		String logoFileName = "logo_"+imageFileName;	//定义目标文件输出的名称
		OutputStream os = null;
		
		try {
			//1 创建图片缓存对象
			Image image2 = ImageIO.read(image);
			
			int width = image2.getWidth(null);
			int height = image2.getHeight(null);
			
			BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			
			//2 创建Java绘图工具对象
			Graphics2D g = bufferedImage.createGraphics();
			
			//3 使用绘图工具对象将原图绘制到缓存图片对象
			g.drawImage(image2, 0, 0, width, height, null);
			
			//4 使用绘图工具对象将水印（文字/图片）绘制到缓存图片
			
			String logoPath = realUploadPath + "/" + LOGO;
			File logo = new File(logoPath);
			
			Image logoImage = ImageIO.read(logo);
			
			int width1 = logoImage.getWidth(null);
			int height1= logoImage.getHeight(null);
			
			//透明度的设置
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			
			//旋转图片(30°)
			g.rotate(Math.toRadians(30),bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
			
			int x= -width/2;
			int y= -height/2;
			
			while(x < width*1.5){
				y = -height/2;
				while(y < height*1.5){
					g.drawImage(logoImage,x,y,null);
					y+=height1+50;
				}
				x+= width1 + 50;
			}
			g.dispose();
			
			//创建文件输出流，指向最终的目标文件
			os = new FileOutputStream(realUploadPath+"/"+logoFileName);	
			
			//5 创建图像文件编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			
			//6 使用图像编码工具类，输出缓存图像到目标文件
			en.encode(bufferedImage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return uploadPath+"/"+logoFileName;
	}
	
}
