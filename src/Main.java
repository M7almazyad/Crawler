import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static void setInitialUrls(String path, String[] arrUrl) {
        BufferedReader reader;
        int counter = 0;

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                arrUrl[counter] = line;
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int maxNumPages = 100;
        String[] urls = new String[maxNumPages];
        String seedFName = "C:\\Users\\Mohammed\\Desktop\\csc484\\url.txt";
        String storageFName = "C:\\Users\\Mohammed\\Desktop\\csc484\\result";

        setInitialUrls(seedFName, urls);
        Crawler c =new Crawler(urls, maxNumPages, storageFName);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        
        //Thread t3 = new Thread(c);

        t1.start();
        t2.start();
        //t3.start();
    }
}

