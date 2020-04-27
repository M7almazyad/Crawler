import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler implements Runnable {
    private final String[] urls;
    private int maxNumPages;
    private String storageFName;

    public Crawler(String[] urls, int maxNumPages, String storageFName) {
        this.urls = urls;
        this.maxNumPages = maxNumPages;
        this.storageFName = storageFName;
    }

    public void run() {
        try {
            crawl(maxNumPages, storageFName);
        } catch (Exception e) {
            System.out.println("Error in run(): " + e.getMessage());
        }
    }

    void crawl(int maxNumPages, String storageFName) throws IOException {
        int arrCounter = 0;
        int index = 0;
        int docCounter = 1;

        //Count number of element in the array and set arrCounter value
        for (int i = 0; i < urls.length; i++) {
            if (urls[i] != null) {
                arrCounter++;
            } else {
                break;
            }
        }
        	int i=0;
        while (i < maxNumPages) {
            //Perform crawling
        	if(arrCounter >= maxNumPages) {
        		break;
        	}            try {
            	Document document=null;
                Elements links =null;
                synchronized (urls) {
                     document = Jsoup.connect(urls[i++]).get();
                    //Extract all URL from the document
                     links = document.select("a[href]");
                    //Add extracted URL to the list of URL
                    System.out.println(links.size()); 
                   
                }
                for (Element link : links) {
                 	if(arrCounter>= maxNumPages) {
                 		break;
                 
                 	}
                     if (arrCounter <= maxNumPages) {
                        if (link.attr("abs:href") != null && !link.attr("abs:href").isEmpty()) {
                         	
                             urls[arrCounter++] = link.attr("abs:href");
                             System.out.println(Thread.currentThread().getName());
                             String doc = document.outerHtml();
                             writeDocumentToFile(storageFName, "Document_" + docCounter++, doc);
                             System.out.println(index + " - " + urls[index++]);
                           

                         }
                     }
                 }
            
            } catch (Exception e) {
                if (e.getMessage().equals("Must supply a valid URL") && index < arrCounter) {
                    index++;
                }
            }
          
        }
    }

    static void writeDocumentToFile(String folderPath, String fileName, String document) throws IOException {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter(folderPath + "/" + fileName + ".txt");
            out = new BufferedWriter(fstream);
            out.write(document);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
