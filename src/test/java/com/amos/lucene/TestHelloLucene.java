package com.amos.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.junit.Test;

/**
 * Created by amosli on 14-9-17.
 */
public class TestHelloLucene {
    @Test
    public void testIndex(){
        HelloLucene helloLucene = new HelloLucene();
        helloLucene.index();
    }
    @Test
    public void testSearch(){
        HelloLucene helloLucene = new HelloLucene();
        helloLucene.search();
    }
}
