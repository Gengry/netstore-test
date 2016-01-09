package edu.imut.util;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import edu.imut.domain.Book;



public class FillBeanUtil {
	public static <T> T fillBean(HttpServletRequest request,Class<T> clazz){
		try {
			T bean = clazz.newInstance();
			BeanUtils.copyProperties(bean, request.getParameterMap());
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param clazz
	 * @return 返回结果为空，出错跳至错误页面。
	 * @throws Exception 
	 */
	public static <T> T fillBeanAndFileUpload(HttpServletRequest request,Class<T> clazz) throws Exception{
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			return null;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = sfu.parseRequest(request);
		
		T book = clazz.newInstance();
		for(FileItem item:items){
			if(item.isFormField()){
				//封装基本数据到book
				String fieldName = item.getFieldName();
				String fieldValue = item.getString(request.getCharacterEncoding());
				BeanUtils.copyProperty(book, fieldName, fieldValue);
			}else{
				//文件上传
				String fileName = item.getName();
				fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
				String storeDirectory = request.getRealPath("/images");
				String path = makeDirs(storeDirectory,fileName);
				//上传
				item.write(new File(storeDirectory+path+"/"+fileName));
				Book bean = (Book)book;
				bean.setPhotoFileName(fileName);
				bean.setPath(path);
			}
		}
		return book;
	}

	private static String makeDirs(String storeDirectory, String fileName) {
		int hashCode = fileName.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;
		
		String newPath = "/"+dir1+"/"+dir2;
		File file = new File(storeDirectory, newPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return newPath;
	}
}
