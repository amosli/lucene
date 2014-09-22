package com.amos.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
 * @ClassName: Demo
 * @Description: luceneDemo
 * @author: amosli
 * @email:amosli@juxinli.com
 * @date 2014年8月6日 下午5:47:53
 */
public class LuceneDemo {

	/**
	 * 创建index
	 * 
	 * @param indexPath
	 */
	public static void createIndex(String indexPath) {


		try {

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
			Directory dir = FSDirectory.open(new File(indexPath));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);
			IndexWriter indexWriter = new IndexWriter(dir, config);//创建索引

			Document doc = new Document();//创建一个文档
			IndexableField idField = new IntField("id", 1, Store.YES);//int类型的域
			IndexableField titleField = new StringField("title", "title1 title2", Store.YES);//string类型的域
			IndexableField contentField = new TextField("content", "content1 content2", Store.YES);//文本类型的域

			doc.add(idField);
			doc.add(titleField);
			doc.add(contentField);
			indexWriter.addDocument(doc);
			
			indexWriter.commit();//commit 不能忘
			indexWriter.close();//close
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void searchIndex(String indexPath) {
		try {
			Directory dir = FSDirectory.open(new File(indexPath));
			IndexReader indexReader = DirectoryReader.open(dir);//打开索引目录 
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);//创建索引搜索器
			
//			Query query = new TermQuery(new Term("id", "1"));//不能查询数字
//			Query query = new TermQuery(new Term("title", "title1 title2"));//查询title必须要写全,因为是StringField
//			Query query = new TermQuery(new Term("content", "content1"));//查询content必须不能写全,因为是TextField
			
			Query query = NumericRangeQuery.newIntRange("id", 1, 200, true, true);//可以查询数字,查询数据的范围要定义好 1-200
			
			TopDocs topDocs = indexSearcher.search(query, 10);//查询最近10条
			ScoreDoc[] scoreDocs=topDocs.scoreDocs;//查询到的结果
			int totalHits = topDocs.totalHits;//查询到的结果数
			System.out.println("totalHits:"+totalHits);
			for(ScoreDoc sdoc:scoreDocs){//遍历结果
				int docId=sdoc.doc;
				Document doc = indexSearcher.doc(docId);
				System.out.println("id:"+doc.get("id") +"  title:"+doc.get("title") +" content:"+doc.get("content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
