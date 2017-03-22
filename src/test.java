import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class test {

	public static void test1() throws IOException {
		Document doc = Jsoup.connect(
				"http://www.cqzb.gov.cn/zbgg-5-72911-1.aspx").get();

		String title = doc.title();
		System.out.println(title);
	}

	// http://www.cqzb.gov.cn

	public Document read1() throws IOException {
		File input = new File("data/1.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.cqzb.gov.cn/");

		// System.out.println(doc);
		// System.out.println("---------------------------");
		// System.out.println(doc.text());
		return doc;
	}

	public Document read2() throws IOException {
		// File input = new File("data/1.html");
		// Document doc = Jsoup.parse(input, "UTF-8",
		// "http://www.cqzb.gov.cn/");
		Document doc = Jsoup.connect(
				"http://www.cqzb.gov.cn/zbgg-5-72325-2.aspx").get();
		// System.out.println(doc);
		// System.out.println("---------------------------");
		// System.out.println(doc.text());
		return doc;

	}

	public void test2() throws IOException {
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = read1();
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		System.out.println(e);
		System.out.println("---------------------------");
		String html = Jsoup.clean(e.toString(), user_content_filter);
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		System.out.println(doc);
		System.out.println("---------------------------");
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<//a>", "").replaceAll("<a>", "")
				.replaceAll("&nbsp;", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		System.out.println(doc);
		System.out.println("---------------------------");
		for (Element el : doc.getAllElements()) {
			System.out.println(el.tagName());
		}
	}

	public Document preParse(String url) throws IOException {
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.connect(url).get();
//		File input = new File("data/1.html");
//		Document doc = Jsoup.parse(input, "UTF-8", "http://www.cqzb.gov.cn/");
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		String html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<//a>", "").replaceAll("<a>", "")
				.replaceAll("&nbsp;", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		return doc;
	}
	
	public Document preParseFromFile(String path, String baseUrl) throws IOException {
		Whitelist user_content_filter = Whitelist.relaxed();
		File input = new File(path);
		Document doc = Jsoup.parse(input, "UTF-8", baseUrl);
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		String html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<//a>", "").replaceAll("<a>", "")
				.replaceAll("&nbsp;", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		return doc;
	}
	
	public Document clean(String url) throws IOException{
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.connect(url).get();
		String html = Jsoup.clean(doc.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<//a>", "").replaceAll("<a>", "")
				.replaceAll("&nbsp;", "");
		doc = Jsoup.parseBodyFragment(html);
		return doc;
	}
	
	public void test3() throws IOException {
//		File input1 = new File("data/3.txt");
//		File input2 = new File("data/4.txt");
//		Document doc1 = Jsoup.parse(input1, "UTF-8", "http://www.cqzb.gov.cn/");
//		Document doc2 = Jsoup.parse(input2, "UTF-8", "http://www.cqzb.gov.cn/");
//		doc1 = preParseFromFile(doc1.toString(), "http://www.cqzb.gov.cn/");
//		doc2 = preParseFromFile(doc2.toString(), "http://www.cqzb.gov.cn/");
		Document doc1 = preParse("http://www.cqzb.gov.cn/zbgg-5-72980-1.aspx");
		Document doc2 = preParse("http://www.cqzb.gov.cn/zbgg-5-72916-1.aspx");
		doc1.select("p").remove();
		doc2.select("p").remove();
		System.out.println(doc1);
		System.out.println("---------------------");
		System.out.println(doc2);
//		if(doc1.body().toString().equals(doc2.body().toString())){
//			System.out.println("!!!!!!!!!!!!!");
//		}
		System.out
				.println(Similarity.DOM_Similarity(doc1.body(), doc2.body()));
	}
	
	public void test4() throws IOException {
		Document doc1 = getDoc_fakeHeader("http://www.cqzb.gov.cn/zbgg-5-72980-1.aspx");
		Document doc2 = getDoc_fakeHeader("http://www.cqzb.gov.cn/zbgg-5-72916-1.aspx");
		
		Whitelist user_content_filter = Whitelist.relaxed();
		String html1 = Jsoup.clean(doc1.toString(), user_content_filter);
		String html2 = Jsoup.clean(doc2.toString(), user_content_filter);
		
		doc1 = Jsoup.parse(html1);
		doc2 = Jsoup.parse(html2);
//		doc1.select("p").remove();
//		doc2.select("p").remove();
		System.out.println(doc1);
		System.out.println("---------------------");
		System.out.println(doc2);
//		if(doc1.body().toString().equals(doc2.body().toString())){
//			System.out.println("!!!!!!!!!!!!!");
//		}
		System.out
				.println(Similarity.DOM_Similarity(doc1.body(), doc2.body()));
	}
	
	
	public Document getDoc_fakeHeader(String url) throws IOException{
		Connection connect = Jsoup.connect(url);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Host", "http://info.bet007.com");
		header.put("User-Agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
//		header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		header.put("Accept-Language", "zh-cn,zh;q=0.5");
//		header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
		header.put("Connection", "keep-alive");
		Connection data = connect.data(header);
		Document document = data.get();
		return document;
	}
	
	
	public static void main(String[] args) throws IOException {
		new test().test4();
	}
}
