package scrape;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import data.fetch.fetch;

public class main {
	
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
    public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 
	
	//*[@id="boxik-163425"]/div/div/div[3]/div[1]/div/h3/a
	public static void main(String[] args) throws NoSuchAlgorithmException {
		Integer memeCount = 0;
		// TODO Auto-generated method stub
		String[] previous = null;
		int index = 0;
		
		String[] subs = {"/r/me_irl/top/?t=hour", "/r/memes/top/", "/r/iamverybadass/top/?t=day", "/r/bestmemes/new/", "/r/memes/top/?t=hour", "/r/LegalMemes/new/", "/r/dankmemes/top/?t=hour", "/r/dogelore/top/?t=day"}; // "/r/memes/top/?t=hour"
		String[] ext = {"https://imgflip.com/tag/memes", "https://imgflip.com/tag/memes?sort=top-1d", "https://memes.com/recent"};
 		
		
		for(int r = 27 ; r >= 0 ; r--) {
			for(String dir : subs) {
				try {
				fetch f = new fetch("https://www.reddit.com"+dir);
				
				for(String link : f.retrieve()) {

					 File file = new File("/var/www/html/memes/"+toHexString(getSHA(link))+".jpg");
					 if(!file.exists()) {
							Image image = null;
							try {
							    URL url = new URL(link);
							    //image = ImageIO.read(url);
							    //FileOutputStream fos = new FileOutputStream("C://borrowed_image.jpg");				    
							    InputStream in = new BufferedInputStream(url.openStream());
							    ByteArrayOutputStream out = new ByteArrayOutputStream();
							    byte[] buf = new byte[1024];
							    int n = 0;
							    while (-1!=(n=in.read(buf)))
							    {
							       out.write(buf, 0, n);
							    }
							    out.close();
							    in.close();
							    byte[] response = out.toByteArray();
							    FileOutputStream fos = new FileOutputStream("/var/www/html/memes/"+(System.currentTimeMillis())+"_"+toHexString(getSHA(link))+".jpg");
							    fos.write(response);
							    fos.close();
							    memeCount+=1;
							} catch (IOException e) {
								e.printStackTrace();
							}
					 }
					 	
					}

				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			for (String lk : ext) {
				fetch f = new fetch(lk);
				
				for(String link : f.retrieve()) {
					
					 File file = new File("/var/www/html/memes/"+toHexString(getSHA(link))+".jpg");
					 if(!file.exists()) {
						Image image = null;
						try {
						    URL url = new URL(link);
						    //image = ImageIO.read(url);
						    System.setProperty("http.agent", "Chrome");
						    //FileOutputStream fos = new FileOutputStream("C://borrowed_image.jpg");				    
						    InputStream in = new BufferedInputStream(url.openStream());
						    ByteArrayOutputStream out = new ByteArrayOutputStream();
						    byte[] buf = new byte[1024];
						    int n = 0;
						    while (-1!=(n=in.read(buf)))
						    {
						       out.write(buf, 0, n);
						    }
						    out.close();
						    in.close();
						    byte[] response = out.toByteArray();
						    FileOutputStream fos = new FileOutputStream("/var/www/html/memes/"+toHexString(getSHA(link))+".jpg");
						    fos.write(response);
						    fos.close();
						    memeCount+=1;
						} catch (IOException e) {
							e.printStackTrace();
						}
					 }
				}	
			}
			try {
				TimeUnit.MINUTES.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println(" --------- TOTAL MEMES DOWNLOADED = "+memeCount);
		

		 
		
		
			
		
		
/*
 * 		while() {
			if(index == 0) {
				fetch f = new fetch("https://www.novinky.cz");
				f.retrieve();	
				f.analyze(f.retrieve());
				previous = f.retrieve().split(" ");				
				System.out.println(index);
			}
			else {
				fetch f = new fetch("https://www.novinky.cz");
				String res = f.retrieve();
				System.out.println(res.split(" ")[0].length());
				System.out.println(previous[0].length());
				if(res.split(" ")[0].length() == previous[0].length()) {
					System.out.println("No new data");
				}
				else {
					previous = f.retrieve().split(" ");
					f.analyze(f.retrieve());
				}

				try {
					TimeUnit.SECONDS.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			index+=1;
		}
 * 
 */
		//System.out.println(f.query(""));

	}

}
