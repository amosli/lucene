package com.amos.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by amosli on 14-9-17.
 */
public class HelloLucene {
    static String indexDir = "/home/amosli/developtest/lucene";

    public void index() {
        IndexWriter indexWriter = null;
        FSDirectory directory = null;
        try {
            //1、创建Directory
            directory = FSDirectory.open(new File(indexDir));
            //RAMDirectory directory = new RAMDirectory();

            //2、创建IndexWriter
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_0, new StandardAnalyzer(Version.LUCENE_4_10_0));
            indexWriter = new IndexWriter(directory, indexWriterConfig);

            File file = new File("/home/amosli/developtest/testfile");
            for (File f : file.listFiles()) {

                FieldType fieldType = new FieldType();
                //3、创建Docuemnt对象
                Document document = new Document();

                //4、为Document添加Field
                document.add(new TextField("content", new FileReader(f)));

                fieldType.setIndexed(true);
                fieldType.setStored(true);
                document.add(new Field("name", f.getName(), fieldType));

                fieldType.setIndexed(false);
                fieldType.setStored(true);
                document.add(new Field("path", f.getAbsolutePath(), fieldType));

                //5、通过IndexWriter添加文档索引中
                indexWriter.addDocument(document);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void search() {
        IndexReader indexReader = null;
        try {
            //1、创建Directory
            FSDirectory directory = FSDirectory.open(new File(indexDir));

            //2、创建IndexReader
             indexReader = DirectoryReader.open(directory);

            //3、根据IndexReader创建IndexSearcher
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //4、创建搜索的Query
            //创建querypaser来确定要搜索文件的内容，第二个参数表示搜索的域
            QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());
            //创建query，表示搜索域为content中包含java的文档
            Query query = queryParser.parse("java");
            //5、根据Searcher搜索并且返回TopDocs
            TopDocs topDocs = indexSearcher.search(query, 100);
            //6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] sds = topDocs.scoreDocs;
            //7、根据Seacher和ScoreDoc对象获取具体的Document对象
            for (ScoreDoc sdc : sds) {
                Document doc = indexSearcher.doc(sdc.doc);
                //8、根据Document对象获取需要的值
                System.out.println("name:" + doc.get("name") + "----->  path:" + doc.get("path"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally{

            if(indexReader!=null){
                try {
                    indexReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
