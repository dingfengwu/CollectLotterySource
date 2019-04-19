package com.pbcom.service;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.pbcom.entity.Log;
import com.pbcom.entity.LogInfo;

import com.alibaba.fastjson.JSONObject;


public class AnalysisLog {

    public String getLogInfo(String filePath){
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
                	if(temp.equals(""));
                	else {
                        stringBuffer.append(temp + "\n");
                    }
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
    
   	public Log AnalyzeContent(String content,String keyWord) {
       	 String[] logs = content.split("\n");
        	 Log ll = new Log();
        	 try {
   	         String time_RequestInfo = logs[0];
   	         String RequestInfo = logs[1];
   	         String decodinged = logs[2];
   	         String ErrorMassage =String.join("\n", Arrays.copyOfRange(logs, 3, logs.length));
   	         String time = time_RequestInfo.replace("[","").replace("]","").split(" ")[1];
   	         String ip = time_RequestInfo.replace("[","").replace("]","").split(" ")[3];
   	         String url = time_RequestInfo.replace("[","").replace("]","").split(" ")[4];
   	         decodinged = decodinged.replace("ERR: 解码后:","");
   	         ErrorMassage = ErrorMassage.replace("ERR: ","").replace("\r","").replace("\n","");
   	         ll.time = time;
   	         ll.ip = ip;
   	         ll.url = url;
   	         LogInfo info = JSONObject.parseObject(decodinged, LogInfo.class);
   	         ll.decodinged = info;
   	         ll.requestInfo = RequestInfo;
   	         ll.errorMessage = ErrorMassage;
   	         if(!ErrorMassage.contains(keyWord)) {
   	        	 return null;
   	         }
            }catch (Exception e) {
           	 ll.errorMessage=content;
   		}
   			return ll;
   	}

       public List<Log> showLog(String log,String keyWord) throws InterruptedException {
           List<Log> logList = new ArrayList<Log>();
           Pattern p=Pattern.compile("\\[([\\w-T:+\\s]+)\\]"); 
           Matcher m=p.matcher(log);
           int lastPoint=0;
       	   int currentPoint=0;
       	   String content="";
       	   Log item =null;
           do {
           	boolean isEnd=false;
           	currentPoint=0;
           	content="";
           	if(m.find(lastPoint)) {
           		lastPoint =m.start();
           		if(m.find(lastPoint+30)) {
           			currentPoint = m.start();
           			content=log.substring(lastPoint,currentPoint);
           			lastPoint = currentPoint;
           		}
           		else {
           			isEnd=true;
           			content=log.substring(lastPoint,log.length()-1);
           		}
           	}
           	if(content=="") {
           		content=log;
           		isEnd=true;
           	}
           	item=AnalyzeContent(content,keyWord);
           	if(item!=null) logList.add(item);
           	if(isEnd) break;
           }while(true);
           return logList;
       }
}
