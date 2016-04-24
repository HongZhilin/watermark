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
 * 实现添加单个图片水印
 */
public class ImageMarkService implements MarkService{

	@Override
	public String watermark(File image, String imageFileName,
			String uploadPath, String realUploadPath) {
		
		String logoFileName = "logo_"+imageFileName;
		OutputStream os = null;
		
		String logoPath = realUploadPath+"/"+LOGO;	//logo水印文件的具体路径
		
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
//			g.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));
//			g.setColor(FONT_COLOR);
			
			//创建logo文件,使用ImageIO流来读取文件
			File logo = new File(logoPath);	
			Image imageLogo = ImageIO.read(logo);
			
			//分析图片文件的高度和宽度
			int width1 = imageLogo.getWidth(null);
			int height1= imageLogo.getHeight(null);	//图片水印高度
			
			//计算原图与水印图片的宽度、高度之差
			int widthDiff = width - width1;		//宽度之差
			int heightDiff= height - height1;	//高度之差
			
			int x = X;	//横坐标
			int y = Y;	//纵坐标
			
			//保证图片水印在右下方显示
			if(x > widthDiff){
				x = widthDiff;	
			}
			if(y > heightDiff){
				y = heightDiff;	
			}
			
			//水印透明度的设置
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));//透明度的设置
			//绘制图片水印
			g.drawImage(imageLogo, x, y,null);
			//释放工具
			g.dispose();
			
			//创建文件输出流，指向最终的目标文件
			os = new FileOutputStream(realUploadPath+"/"+logoFileName);	//最终目标文件
			
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
