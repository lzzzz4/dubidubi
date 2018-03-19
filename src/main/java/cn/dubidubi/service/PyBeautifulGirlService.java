package cn.dubidubi.service;

import java.util.List;

public interface PyBeautifulGirlService {

	/**
	 * @Description: 图片搜索函数，根据源指定函数
	 * @data :@param source
	 * @data :@param width
	 * @data :@param height
	 * @data :@param word
	 * @data :@param pn
	 * @data :@param rn
	 * @data :@return
	 * @date :2018年3月13日下午4:55:54
	 */
	List<String> startPy(int width, int height, String word, int pn, int rn);

	/**
	 * @Description: 九妹源时，使用此函数
	 * @data :@param word
	 * @date :2018年3月19日下午12:50:43
	 */
	void startPy(String word);

}