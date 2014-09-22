package com.amos.lucene;

import org.junit.Test;
/**
 * 
* @ClassName: DemoTest 
* @Description: luceneDemo测试
* @author: amosli
* @email:amosli@juxinli.com
* @date 2014年8月6日 下午6:42:37
 */
public class TestLuceneDemo {
	@Test
	public void testCreateIndex() {
		String indexPath = "/home/amosli/developtest/lucene";
		LuceneDemo.createIndex(indexPath);
	}

	@Test
	public void testSearchIndex() {
		String indexPath = "/home/amosli/developtest/lucene";
		LuceneDemo.searchIndex(indexPath);
	}
}
