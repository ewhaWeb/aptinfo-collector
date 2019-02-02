//package com.ewha.aptinfocollector.repository;
//
//import com.ewha.aptinfocollector.VO.Apartment;
//import com.ewha.aptinfocollector.VO.Transaction;
//import com.ewha.aptinfocollector.service.APIsetter;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpression;
//import javax.xml.xpath.XPathFactory;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.lang.reflect.Array;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//
//public class RepositoryTest {
//    @Autowired
//    APIsetter APIsetter;
//    @Autowired
//    ApartmentRepository apartmentRepository;
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    @Test
//    public void APIsetterTest () throws Exception {
//        ArrayList<Apartment> apartments = APIsetter.main();
//
//    }
//}
