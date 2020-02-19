
package multihreading;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Multihreading {
//jeden wątek 30770 ,30534
  // wielewątków   1453 ,1439
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        URL otodom = new URL("https://www.otodom.pl/sprzedaz/mieszkanie/katowice/");
        BufferedReader in = new BufferedReader(new InputStreamReader(otodom.openStream()));
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();
        
        while((inputLine = in.readLine()) != null) {
            stringBuffer.append(inputLine);
            stringBuffer.append(System.lineSeparator()); 
    }
        in.close();
        Set<String> setofLinks = new TreeSet<>();
        String content = stringBuffer.toString();
        
        for(int i=0; i< content.length(); i++){
            i = content.indexOf("https://www.otodom.pl/oferta/",i); 
            if(i < 0)
                break;
            String substring = content.substring(i);
            String link = substring.split(".html")[0];
            setofLinks.add(link);
        }
        for(int j=0; j<setofLinks.size(); j++){
         //   readWebsite(setofLinks.toArray()[j].toString(), j + ".html");
            
            int finalj = j;
            executorService.submit(()->{
                try{
                    readWebsite(setofLinks.toArray()[finalj].toString(), finalj + ".html");
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
            
            
        }
        executorService.shutdown();
       long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
    
    
    public static void readWebsite(String link,String fileName) throws IOException{
        URL otodom = new URL(link);
        BufferedReader in = new BufferedReader(new InputStreamReader(otodom.openStream()));
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();
        
        while((inputLine = in.readLine()) != null) {
            stringBuffer.append(inputLine);
            stringBuffer.append(System.lineSeparator()); 
    }
        in.close();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,false));
        bw.write(stringBuffer.toString());
        bw.close();
    }
}
