package com.pbcom.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbcom.entity.Log;
import com.pbcom.entity.LogFile;
import com.pbcom.service.AnalysisLog;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@SpringBootApplication
@Controller
@RequestMapping("/Log")
public class LogApplication extends SpringBootServletInitializer {
    @RequestMapping(value = "/index",method = { RequestMethod.GET })
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> index(HttpServletRequest request) throws IOException {
    	Properties properties = new Properties();
		File file = new File("url.properties");
		FileInputStream fis = new FileInputStream(file);
		properties.load(fis);
		fis.close();
		String fileUrl = properties.getProperty("fileUrl");
		
    	String pageSize = request.getParameter("pageSize");
    	String pageNum =  request.getParameter("pageNum");
    	String psth = request.getParameter("path");
    	String keyWord = request.getParameter("keyWord");
    	Map<String, Object> map = new HashMap<String, Object>();
    	int pSize = 0;
    	int pNum = 0;
    	try {
    	if(pageSize == null||pageSize == "") {
    		pSize = 20;
    	}else {
    		pSize = Integer.parseInt(pageSize);
    	}
    	if(pageNum==null||pageNum=="") {
    		pNum = 0;
    	}else {
    		pNum = Integer.parseInt(pageNum);
    	}
    	}catch (Exception e) {
			// TODO: handle exception
			map.put("msg", e.getMessage());
			map.put("status", 400);
			return map;
		}
    	try {
        AnalysisLog analy = new AnalysisLog();
        System.err.println("路径---------------->"+fileUrl+psth+".log");
        String log = analy.getLogInfo(fileUrl+psth+".log");
        List<Log> list = new ArrayList<Log>();
        try {
            list = analy.showLog(log,keyWord);
        } catch (InterruptedException e) {
           return  null;
        }
        map.put("msg", "查询成功！");
        map.put("status", 0);
        map.put("total", list.size());
        list.sort(new Comparator<Log>() {
        	@Override
        	public int compare(Log o1, Log o2) {
        		// TODO Auto-generated method stub
        		return o2.time.compareTo(o1.time);
        	}	       	
        });     
        if(pSize*(pNum+1)>=list.size()) {
        	map.put("data", list.subList(pSize*pNum, list.size()));
        	return map;
        }
        map.put("data", list.subList(pSize*pNum, pSize*(pNum+1)));
        }catch (Exception e) {
        	map.put("msg", e.getMessage());
			map.put("status", 400);
		}        
        return  map;
    }
    
    
    @RequestMapping(value = "/getFile",method = { RequestMethod.GET })
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> GetFileNames(HttpServletRequest request) throws IOException {
    	Properties properties = new Properties();
		File file = new File("url.properties");
		FileInputStream fis = new FileInputStream(file);
		properties.load(fis);
		fis.close();
		String url = properties.getProperty("url");
		System.out.println("-------------------------------->"+url);
    	Map<String, Object> map = new HashMap<>();
    	String date = request.getParameter("date");
    	String pageSize = request.getParameter("pageSize");
    	String pageNum =  request.getParameter("pageNum");
    	int pSize = 0;
    	int pNum = 0;
    	try {
    	if(pageSize == null||pageSize == "") {
    		pSize = 20;
    	}else {
    		pSize = Integer.parseInt(pageSize);
    		if(pSize<=0) {
    			pSize = 500;
    		}
    	}
    	if(pageNum==null||pageNum=="") {
    		pNum = 0;
    	}else {
    		pNum = Integer.parseInt(pageNum)-1;
    		if(pNum<=0) {
    		pNum = 0;
    		}
    	}
    	}catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "查询错误！");
			map.put("status", 400);
			return map;
		}
	   // path = "C:\\tools\\UPUPW_AP7.2_64\\htdocs\\App\\Runtime\\Logs\\Api"; // 路径
        File f = new File(url);
        if (!f.exists()) {
        map.put("msg", "文件夹为空！");
        map.put("status", 400);
            return map;
        }
        List<LogFile> fileList = new ArrayList<LogFile>();
        File fa[] = f.listFiles();
        System.out.println("fa的大小>>>>>"+fa.length);
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
            	LogFile file2 = new LogFile();
            	file2.fileName = fs.getName();
            	File file3 = new File(fs.getAbsolutePath());
            	file2.lastModifiedTime = new Date(fs.lastModified());
            	file2.size = file3.length();
            	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    			String s = formatter.format(file2.lastModifiedTime);
    			if(date==""||date==null) {
    				fileList.add(file2);
    			}else {
    			if(!s.equals(date));
    			else {
            	fileList.add(file2);
    				}
    			}
            }else {
            
            }
        }
        if(pSize*pNum>fileList.size()) {
        	map.put("msg", "超过最大页面！");
        	map.put("status", 400);
            return map;
        }
        map.put("msg", "获取成功！");
        map.put("status", 0);
        map.put("total", fileList.size());
        fileList.sort(new Comparator<LogFile>() {
        	@Override
        	public int compare(LogFile o1, LogFile o2) {
        		// TODO Auto-generated method stub
        		return o2.lastModifiedTime.compareTo(o1.lastModifiedTime);
        	}	       	
        });
        if(pSize*(pNum+1)>=fileList.size()) {
        	map.put("data", fileList.subList(pSize*pNum, fileList.size()));
        	return map;
        }
        map.put("data", fileList.subList(pSize*pNum, pSize*(pNum+1)));
        return map;
    }

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
