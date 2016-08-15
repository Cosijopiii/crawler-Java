package sample;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Crawler{
    public static void main(String[] args) throws Exception{
    String urls[] = new String[10000];
    String url = "";
    int i=0,j=0,tmp=0,total=0, MAX = 10000;
    int start=0, end=0;
    String webpage = Web.getWeb(url);
    end = webpage.indexOf("<body");
    for(i=total;i<MAX; i++, total++){
        start = webpage.indexOf("http://", end);
        if(start == -1){
            start = 0;
            end = 0;
            try{
                webpage = Web.getWeb(urls[j++]);
            }catch(Exception e){
                System.out.println("******************");
                System.out.println(urls[j-1]);
                System.out.println("Exception caught \n"+e);
            }

            /*logic to fetch urls out of body of webpage only */
            end = webpage.indexOf("<body");
            if(end == -1){
                end = start = 0;
                continue;
            }       
        }
        end = webpage.indexOf("\"", start);
        tmp = webpage.indexOf("'", start);
        if(tmp < end && tmp != -1){
            end = tmp;
        }
        try{
            url = webpage.substring(start, end);
        }catch (Exception e){
         url=urls[i-1];
        }
        urls[i] = url;
        System.out.println(urls[i]);

            try {
                URL urlx = new URL(urls[i]);
                InputStream in = new BufferedInputStream(urlx.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                FileOutputStream fos = new FileOutputStream("/Users/cosijopii/IdeaProjects/crawler/" + i + ".jpg");
                fos.write(response);
                fos.close();
            } catch (Exception e) {
                System.out.println("potato");
            }

    }   
    System.out.println("Total URLS Fetched are " + total);
    }
}



/*This class contains a static function which will fetch the webpage
  of the given url and return as a string */
class Web{
    public static String getWeb(String address)throws Exception{
    String webpage = "";
        String inputLine = "";
        URL url = new URL(address);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        while ((inputLine = in.readLine()) != null)
        webpage += inputLine;
        in.close();
    return webpage;
    }
}
