package com.zsTrade.web.luence;

import java.io.StringReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.github.abel533.database.Dialect;
import com.github.abel533.database.SimpleDataSource;
import com.github.abel533.utils.DBMetadataUtils;
import com.zsTrade.common.utils.DateUtils;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.sys.utils.Symphonys;



/**
 * ����������
 * @author Administrator
 *
 */
public class ProductIndex {

	private Directory dir=null;
	

	/**
	 * ��ȡIndexWriterʵ��
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		dir=FSDirectory.open(Paths.get(Symphonys.get("luence_path")));
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * ��Ӳ�������
	 * @param Product
	 */
	public void addIndex(Product Product)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		doc.add(new StringField("id",String.valueOf(Product.getId()),Field.Store.YES));
		doc.add(new TextField("title",Product.getTitle(),Field.Store.YES));
		doc.add(new TextField("img",Product.getImg(),Field.Store.YES));
		doc.add(new TextField("typename",Product.getTypename(),Field.Store.YES));
		doc.add(new TextField("prices",Product.getPrices(),Field.Store.YES));
		doc.add(new StringField("createDate",DateUtils.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("summary",Product.getSummary(),Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	
	/**
	 * ���²�������
	 * @param Product
	 * @throws Exception
	 */
	public void updateIndex(Product Product)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		doc.add(new StringField("id",String.valueOf(Product.getId()),Field.Store.YES));
		doc.add(new TextField("title",Product.getTitle(),Field.Store.YES));
		doc.add(new StringField("createDate",DateUtils.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("img",Product.getImg(),Field.Store.YES));
		doc.add(new TextField("typename",Product.getTypename(),Field.Store.YES));
		doc.add(new TextField("prices",Product.getPrices(),Field.Store.YES));
		doc.add(new TextField("summary",Product.getSummary(),Field.Store.YES));
		writer.updateDocument(new Term("id", String.valueOf(Product.getId())), doc);
		writer.close();
	}
	
	/**
	 * ɾ��ָ�����͵�����
	 * @param ProductId
	 * @throws Exception
	 */
	public void deleteIndex(Long ProductId)throws Exception{
		IndexWriter writer=getWriter();
		writer.deleteDocuments(new Term("id",ProductId+""));
		writer.forceMergeDeletes(); // ǿ��ɾ��
		writer.commit();
		writer.close();
	}
	
	/**
	 * ��ѯ������Ϣ
	 * @param q ��ѯ�ؼ���
	 * @return
	 * @throws Exception
	 */
	public List<Product> searchProduct(String q)throws Exception{
		dir=FSDirectory.open(Paths.get(Symphonys.get("luence_path")));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser=new QueryParser("title",analyzer);
		Query query=parser.parse(q);
		QueryParser parser2=new QueryParser("summary",analyzer);
		Query query2=parser2.parse(q);
		booleanQuery.add(query,BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
		TopDocs hits=is.search(booleanQuery.build(), 100);
		QueryScorer scorer=new QueryScorer(query);  
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);  
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);  
		List<Product> ProductList=new LinkedList<Product>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			Product Product=new Product();
			Product.setId(Long.parseLong(doc.get(("id"))));
			Product.setImg(doc.get("img"));
			Product.setPrices(doc.get("prices"));
			Product.setTypename(doc.get("typename"));
			Product.setCreateDate(DateUtils.parseDate(doc.get("createDate")));
			String title=doc.get("title");
			String summary=StringEscapeUtils.escapeHtml(doc.get("summary"));
			if(title!=null){
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				if(StringUtils.isEmpty(hTitle)){
					Product.setTitle(title);
				}else{
					Product.setTitle(hTitle);					
				}
			}
			if(summary!=null){
				TokenStream tokenStream = analyzer.tokenStream("summary", new StringReader(summary)); 
				String hsummary=highlighter.getBestFragment(tokenStream, summary);
				if(StringUtils.isEmpty(hsummary)){
					if(summary.length()<=200){
						Product.setSummary(summary);
					}else{
						Product.setSummary(summary.substring(0, 200));						
					}
				}else{
					Product.setSummary(hsummary);					
				}
			}
			ProductList.add(Product);
		}
		return ProductList;
	}public static  String dbUserName=Symphonys.get("connection.username");
	public static  String dbPassword=Symphonys.get("connection.password");
	public static  String jdbcName="com.mysql.jdbc.Driver";
	public static String dbUrl=Symphonys.get("connection.url");
	public static String dbName=Symphonys.get("connection.dbName");
	public static void main(String[] args) throws Exception {
		 ProductIndex productIndex=new ProductIndex();
		
		DBMetadataUtils dbUtils = new DBMetadataUtils(
	            new SimpleDataSource(Dialect.MYSQL, dbUrl, dbUserName, dbPassword));
		Connection con = dbUtils.getConnection();
		PreparedStatement pst = con.prepareStatement("SELECT * FROM  t_product ");
	           ResultSet  rs = pst.executeQuery();
	           while(rs.next()){   
	        	   Product p =new Product();
	               String title = rs.getString("title") ;   
	               String summary = rs.getString("summary") ; 
	               String img = rs.getString("img") ;
	               String typename = rs.getString("typename") ; 
	               String prices = rs.getString("prices") ; 
	               Long id=rs.getLong("id");
	               Date da=rs.getDate("create_Date");
	               p.setId(id);
	               p.setTitle(title);
	               p.setSummary(summary);
	               p.setImg(img);
	               p.setTypename(typename);
	               p.setPrices(prices);
	               p.setCreateDate(da);
	               productIndex.addIndex(p);
	           }  
	           System.out.println("end...........");
	}
}
