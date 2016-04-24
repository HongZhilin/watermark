package com.watermark;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.*;

/*
 * 实现添加多个文字水印
 */
public class MoreTextMarkService implements MarkService{

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
			g.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));
			g.setColor(FONT_COLOR);
			
			int width1 = FONT_SIZE*getTextLength(MARK_TEXT);//文字水印宽度
			int height1= FONT_SIZE;							//文字水印高度
			
			//透明度的设置
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			
			//旋转图片(30°)
			g.rotate(Math.toRadians(30),bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
			
			//设置水印的坐标
			int x= -width/2;
			int y= -height/2;
			
			while(x < width*1.5){
				y = -height/2;
				while(y < height*1.5){
					g.drawString(MARK_TEXT,x,y);
					y += height1 + 50;
				}
				x += width1 + 50;	//水印之间的间隔设为50
			}
			
			//释放工具
			g.dispose();
			
			//最终目标文件
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

	//处理文字水印的中英文字符的宽度转换
	public int getTextLength(String text){
		int length = text.length();
		for(int i=0;i<text.length();i++){
			String s = String.valueOf(text.charAt(i));
			if(s.getBytes().length>1){	//中文字符
				length++;
			}
		}
		length = length%2 == 0?length/2:length/2+1;  //中文和英文字符的转换
		return length;
	}
	
}
