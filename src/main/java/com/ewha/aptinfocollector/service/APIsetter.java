package com.ewha.aptinfocollector.service;


import com.ewha.aptinfocollector.VO.Apartment;
import com.ewha.aptinfocollector.VO.Transaction;
import com.ewha.aptinfocollector.repository.ApartmentRepository;
import com.ewha.aptinfocollector.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

// API를 호출해서 DB에 업데이트할 객체 리스트를 리턴하는 서비스
@Service
public class APIsetter {
    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    TransactionRepository transactionRepository;

    //String locationCode, String date
    public static ArrayList<Apartment> main () throws IOException {

        BufferedReader rd;
        Document doc = null;
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        ArrayList<Apartment> apartmentList = new ArrayList<Apartment>();

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=SeCSY9%2FdXTuCWdFdAIAyTW83p3YFgmuJ4%2F%2BbT2sQzBxHOoCer8Wux5Y2rby0vcfoj5N4WbNQr1WLbfZ7%2B%2F0uGA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("201801", "UTF-8")); /*계약월*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*호출할row*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

            String apiResult = "";
            String line;
            while ((line = rd.readLine()) != null) { apiResult = apiResult + line.trim();}
            rd.close();

            InputSource is = new InputSource(new StringReader(apiResult));

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = documentBuilderFactory.newDocumentBuilder();
            doc = builder.parse(is);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            XPathExpression xPathExpression = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

            for (int i=0; i < nodeList.getLength(); i++) {
                // child가 각 거래 건임.
                NodeList child = nodeList.item(i).getChildNodes();

                Node node_TRXN_PRICE = child.item(0);
                Node node_TRXN_Y = child.item(2);
                Node node_TRXN_M = child.item(17);
                Node node_APT_NM = child.item(16);
                Node node_APT_BUILD_Y = child.item(1);
                Node node_APT_SQM = child.item(20);
                Node node_APT_FLOOR = child.item(23);
                Node node_GU_CODE = child.item(13);
                Node node_DONG_CODE = child.item(14);

                Transaction transaction = new Transaction();
                Apartment apartment = new Apartment();

                int price = Integer.parseInt(node_TRXN_PRICE.getTextContent().trim().replace(",",""));
                transaction.setTRXN_PRICE(price);
                transaction.setTRXN_Y(node_TRXN_Y.getTextContent());
                transaction.setTRXN_M(node_TRXN_M.getTextContent());
                apartment.setAPT_NM(node_APT_NM.getTextContent());
                apartment.setAPT_BUILD_Y(node_APT_BUILD_Y.getTextContent());
                apartment.setAPT_SQM(Double.parseDouble(node_APT_SQM.getTextContent()));
                apartment.setAPT_FLOOR(Integer.parseInt(node_APT_FLOOR.getTextContent()));
                apartment.setGU_CODE(Integer.parseInt(node_GU_CODE.getTextContent()));
                apartment.setDONG_CODE(Integer.parseInt(node_DONG_CODE.getTextContent()));

                apartmentList.add(apartment);

                System.out.println(apartment.getAPT_NM());
                System.out.println(transaction.getTRXN_PRICE());

            }

            conn.disconnect();

        }

        catch (Exception e) {
            System.out.println(e);
        }

        return apartmentList;
    }
}
