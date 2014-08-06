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
public class DemoTest {
	@Test
	public void testCreateIndex() {
		String indexPath = "D:/learn/Test";
		Demo.createIndex(indexPath);
	}

	@Test
	public void testSearchIndex() {
		String indexPath = "D:/learn/Test";
		Demo.searchIndex(indexPath);
	}
}
