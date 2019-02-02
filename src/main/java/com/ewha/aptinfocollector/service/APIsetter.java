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

import javax.persistence.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// API를 호출해서 DB에 업데이트할 객체 리스트를 리턴하는 서비스
@Service
public class APIsetter {
    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager em;


    public void main (String locationCode, String date) throws IOException {

        BufferedReader rd;
        Document doc = null;

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=SeCSY9%2FdXTuCWdFdAIAyTW83p3YFgmuJ4%2F%2BbT2sQzBxHOoCer8Wux5Y2rby0vcfoj5N4WbNQr1WLbfZ7%2B%2F0uGA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(locationCode, "UTF-8")); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*계약월*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*호출할row*/

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

            doc.getDocumentElement().normalize();
            NodeList apiResults = doc.getElementsByTagName("item");

            for (int i=0; i < apiResults.getLength(); i++) {
                // child가 각 거래 건임.
                NodeList result = apiResults.item(i).getChildNodes();
                Transaction transaction = new Transaction();
                Apartment apartment = new Apartment();

                for (int j=0; j < result.getLength(); j++) {
                    Node node = result.item(j);
                    switch (node.getNodeName()) {
                        case "거래금액": {
                            int price = Integer.parseInt(node.getTextContent().trim().replace(",",""));
                            transaction.setTRXN_PRICE(price); break;
                        }
                        case "년": transaction.setTRXN_Y(node.getTextContent()); break;
                        case "월": transaction.setTRXN_M(node.getTextContent()); break;
                        case "건축년도": apartment.setAPT_BUILD_Y(node.getTextContent()); break;
                        case "법정동시군구코드": apartment.setGU_CODE(Integer.parseInt(node.getTextContent())); break;
                        case "법정동읍면동코드": apartment.setDONG_CODE(Integer.parseInt(node.getTextContent())); break;
                        case "아파트": apartment.setName(node.getTextContent()); break;
                        case "전용면적": apartment.setSqm(Double.parseDouble(node.getTextContent())); break;
                        case "층": apartment.setFloor(Integer.parseInt(node.getTextContent())); break;

                    }


                }
                transaction.setApartment(apartment);

                if(apartmentRepository.existsByNameAndSqmAndFloor(apartment.getName(),apartment.getSqm(),apartment.getFloor())) {
                    transactionRepository.save(transaction);
                    apartmentRepository.save(apartment);
                } else {
                    apartmentRepository.save(apartment);
                    transactionRepository.save(transaction);
                }
//                Node node_TRXN_PRICE = child.item(0);
//                Node node_TRXN_Y = child.item(2);
//                Node node_TRXN_M = child.item(17);
//                Node node_APT_NM = child.item(16);
//                Node node_APT_BUILD_Y = child.item(1);
//                Node node_APT_SQM = child.item(20);
//                Node node_APT_FLOOR = child.item(23);
//                Node node_GU_CODE = child.item(13);
//                Node node_DONG_CODE = child.item(14);
//
//
//                int price = Integer.parseInt(node_TRXN_PRICE.getTextContent().trim().replace(",",""));
//                String aptName = node_APT_NM.getTextContent();
//                int aptFloor = Integer.parseInt(node_APT_FLOOR.getTextContent());
//                double aptSqm = Double.parseDouble(node_APT_SQM.getTextContent());
//                Boolean existingApartment = apartmentRepository.existsByNameAndSqmAndFloor(aptName, aptSqm, aptFloor);
//
//                if (!existingApartment) {
//                    apartment.setName(aptName);
//                    apartment.setAPT_BUILD_Y(node_APT_BUILD_Y.getTextContent());
//                    apartment.setSqm(aptSqm);
//                    apartment.setFloor(aptFloor);
//                    apartment.setGU_CODE(Integer.parseInt(node_GU_CODE.getTextContent()));
//                    apartment.setDONG_CODE(Integer.parseInt(node_DONG_CODE.getTextContent()));
//
//                    transaction.setTRXN_Y(node_TRXN_Y.getTextContent());
//                    transaction.setTRXN_M(node_TRXN_M.getTextContent());
//                    transaction.setApartment(apartment);
//
//                    apartmentRepository.save(apartment);
//                    transactionRepository.save(transaction);
//
//                } else {
//                    transaction.setTRXN_PRICE(price);
//                    transaction.setTRXN_Y(node_TRXN_Y.getTextContent());
//                    transaction.setTRXN_M(node_TRXN_M.getTextContent());
//                    transaction.setApartment(existingApartment);
//
//                    transactionRepository.save(transaction);
//
//                }

                transaction = null;
                apartment = null;


            }

            conn.disconnect();
        }

        catch (Exception e) {
            System.out.println(e);
        }

    }
}
