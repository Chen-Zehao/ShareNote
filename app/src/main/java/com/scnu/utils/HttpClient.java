package com.scnu.utils;

import android.util.Log;
import android.util.Xml;

import com.scnu.utils.CharacterSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */

public class HttpClient {

    private static final int NET_CONNECT_TIMEOUT = 1000;
    private static final int NET_READ_TIMEOUT = 10000;

    //目标IP的ping测试
    public static final boolean ping(String ip) {
        //ping地址，可以换成任何一种可靠的外网
        String result = null;
        if (ip.isEmpty())
            return false;
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            /*
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            */
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }
    //HTTP-GET请求
    public static final boolean get(String target, String request, StringBuffer response){
        if (!request.isEmpty())
            target = target + "/?" + CharacterSet.stringToUrlBase64(request.trim());
        try {
            URL url = new URL(target);
            //创建一个HTTP连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            connection.setRequestMethod("GET");
            // 设置超时时间
            connection.setConnectTimeout(NET_CONNECT_TIMEOUT);
            connection.setReadTimeout(NET_READ_TIMEOUT);
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(false);
            //设定传送的内容类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            //连接
            connection.connect();
            //读取返回码
            int code = connection.getResponseCode();
            if (code == 200) { // 正常响应
                InputStreamReader in = new InputStreamReader(connection.getInputStream()); // 获得读取的内容
                BufferedReader reader = new BufferedReader(in); // 获取输入流对象
                String line = null;
                response.setLength(0);
                while ((line = reader.readLine()) != null) { // 循环从流中读取
                    response.append(line + "\n");
                }
                reader.close();// 关闭流
                //in.close();//关闭字符输入流对象
            }
            //断开连接
            connection.disconnect();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //HTTP-POST请求
    public static final boolean post(String target, String request, StringBuffer response){
        try {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(NET_CONNECT_TIMEOUT);
            connection.setReadTimeout(NET_READ_TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.connect();
            OutputStream out = connection.getOutputStream();
            out.write(request.getBytes("utf-8"));
            out.flush();
            out.close();
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStreamReader in = new InputStreamReader(connection.getInputStream(),"utf-8");
                BufferedReader reader = new BufferedReader(in);
                String line = null;
                response.setLength(0);
                while ((line = reader.readLine()) != null) {
                    response.append(line + "\n");
                }
                reader.close();
                //in.close();
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.length() > 0;
    }

    //解析xml字段
    private static boolean xmlParser(InputStream is, StringBuffer version, StringBuffer path) {
        try{
            String strVersion="",strPath="";
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"utf-8");
            int eventType = parser.getEventType();
            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name.equals("nrc_pad")){
                            strVersion = parser.getAttributeValue(0);
                        }else if (name.equals("package")){
                            if (!strVersion.isEmpty() && parser.getAttributeValue(1).equals(strVersion)){
                                strPath = parser.getAttributeValue(0);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
            }
            version.append(strVersion);
            path.append(strPath);
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e){
            e.printStackTrace();
            return false;
        }
    }
}
