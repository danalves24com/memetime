package data.fetch;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
import com.gargoylesoftware.htmlunit.html.HtmlVideo;

public class fetch {
	public String baseURL;
	public HtmlPage data;
	public fetch(String url) {
		 baseURL = url;		

	}
	
	public ArrayList<String> retrieve() {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(true);
		client.getOptions().setThrowExceptionOnScriptError(false);
		try {
			HtmlPage page = client.getPage(this.baseURL);
			this.data = page;
//			record-top - div
//			resp-h3 - h3
			//HtmlUnorderedList req = data.getFirstByXPath("//div[@class='_1RYN-7H8gYctjOQeL8p2Q7']");
			//HtmlListItem req_res = req.getFirstByXPath("//li[@class='d_d5 g_hi']");
			//String req_res_text = req.asText();			
			//System.out.println(req_res_text);
			//System.out.println(req.asXml());
	        final List<?> images = page.getByXPath("//img");	        
	        ArrayList<String> links = new ArrayList<String>();
	        for (Object imageObject : images) {	        	
	            HtmlImage image = (HtmlImage) imageObject;
	            if((image.getSrcAttribute().contains("preview.redd.it") || image.getSrcAttribute().contains("imgflip") || image.getSrcAttribute().contains("cdn.memes")) && !image.getSrcAttribute().contains("award_images") && !image.getSrcAttribute().contains("external-preview.redd.it") && !image.getSrcAttribute().contains("icon")) // image.getSrcAttribute().contains("preview.redd.it") &&  	          
	            	{
	            	String link = image.getSrcAttribute();
	            	if(image.getSrcAttribute().contains("imgflip")) {
	            		 link = image.getSrcAttribute().substring(image.getSrcAttribute().indexOf("i"));
	            	}
	            		if(!link.contains("http")) {
	            			link = "https://"+link;
	            		}
	            		links.add(link);
	            		System.out.println(link);
	            	}
	        }

			return links;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String query(String xPath) {
		return this.data.getByXPath(xPath).toString() ;
	}
	public void analyze(String data) {
		data = data.toLowerCase();
		if(data.contains("prymula") || data.contains("covid-19") || data.contains("epidemic") || data.contains("schools") || data.contains("andrej") || data.contains("minister") || data.contains("limit") || data.contains("close") || data.contains("opat") || data.contains("lockdown") || data.contains("epidemi") || data.contains("virus") || data.contains("kriz" )|| data.contains("19") || data.contains("koronavirem") || data.contains("potvrzen") || data.contains("nakaz") || data.contains("testovat")) { 
			JOptionPane.showMessageDialog(null, "New Updates to add");
			StringSelection selection = new StringSelection(",\""+data+"\"");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			try {
				java.awt.Desktop.getDesktop().browse(new URI("https://translate.google.com/#view=home&op=translate&sl=cs&tl=en&text="));
				java.awt.Desktop.getDesktop().browse(new URI("https://github.com/danalves24com/corona-news/edit/main/news"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
