package com.watermark;

/**
 * 
 * description:模型类
 * @author Byron
 * @date 2016年2月20日 上午11:29:56
 *
 */
public class PicInfo {

	private String imageURL;	//图片的返回路径
	private String logoImageURL;//添加水印的图片的返回路径
	
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getLogoImageURL() {
		return logoImageURL;
	}
	public void setLogoImageURL(String logoImageURL) {
		this.logoImageURL = logoImageURL;
	}
	
}
