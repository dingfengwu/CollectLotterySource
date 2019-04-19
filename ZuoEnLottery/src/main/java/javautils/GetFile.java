package javautils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import lottery.domains.content.entity.Lottery;
import net.sf.json.util.JavaIdentifierTransformer;


public class GetFile {
	/**
	 * 获取文件夹下的文件目录
	 * */
	  public String getFile(String filePath){
	        StringBuffer stringBuffer = null;
	        File file = new File(filePath);
	        if(file.exists()){
	            stringBuffer = new StringBuffer();
	            FileReader fileReader = null;
	            BufferedReader bufferedReader = null;
	            String temp = null;
	            try {
	                fileReader = new FileReader(file);
	            bufferedReader = new BufferedReader(fileReader);
	                while((temp = bufferedReader.readLine()) != null){
	                        stringBuffer.append(temp + "\n");
	                    }
	            } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else{
	            return null;
	        }
	    return stringBuffer.toString();
	    }
	    
	    /**
	     * 根据字符串获得需要遍历的Lottery对象
	     */
	    public List<Lottery> getLottery(String ss){
	    	List<Lottery> list = new ArrayList<>();
	    	JSONArray array = JSONArray.fromObject(ss);
	    	for (Iterator iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				 JsonConfig config = new JsonConfig();
			        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			          @Override
			          public String transformToJavaIdentifier(String str) {
			            char[] chars = str.toCharArray();
			            chars[0] = Character.toLowerCase(chars[0]);
			            return new String(chars);
			          }
			        });
			        config.setRootClass(Lottery.class);
			        Lottery bean = (Lottery) JSONObject.toBean(jsonObject,
							config);
					list.add(bean);	
	    	}
	    	return list;
	    }
	    
	    
	    
}
