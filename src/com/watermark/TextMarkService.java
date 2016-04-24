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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 实现添加单个文字水印
 */
public class TextMarkService implements MarkService{

	@Override
	public String watermark(File image, String imageFileName,
			String uploadPath, String realUploadPath) {
		
		String logoFileName = "logo_"+imageFileName; //定义目标文件输出的名称
		OutputStream os = null;
		
		try {
			//1 创建图片缓存对象
			Image image2 = ImageIO.read(image);	//解码原图
			int width = image2.getWidth(null);	//获取原图的宽度
			int height = image2.getHeight(null);//获取原图的高度
			BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);	//图像颜色的设置
			
			//2 创建Java绘图工具对象
			Graphics2D g = bufferedImage.createGraphics();
			
			//3 使用绘图工具对象将原图绘制到缓存图片对象
			g.drawImage(image2, 0, 0, width, height, null);
			
			//4 使用绘图工具对象将水印（文字/图片）绘制到缓存图片
			g.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));
			g.setColor(FONT_COLOR);
			
			//获取文字水印的宽度和高度值
			int width1 = FONT_SIZE*getTextLength(MARK_TEXT);//文字水印宽度
			int height1= FONT_SIZE;							//文字水印高度
			
			//计算原图和文字水印的宽度和高度之差
			int widthDiff = width - width1;		//宽度之差
			int heightDiff= height - height1;	//高度之差
			
			int x = X;	//横坐标
			int y = Y;	//纵坐标
			
			//保证文字水印在右下方显示
			if(x > widthDiff){
				x = widthDiff;	
			}
			if(y > heightDiff){
				y = heightDiff;	
			}
			
			//水印透明度的设置
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//绘制文字
			g.drawString(MARK_TEXT, x, y + FONT_SIZE);
			//释放工具
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
					os.close();	//关闭流
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return uploadPath+"/"+logoFileName;
	}

	//文本长度的处理：文字水印的中英文字符的宽度转换
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
