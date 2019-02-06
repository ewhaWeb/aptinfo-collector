package com.ewha.aptinfocollector.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ewha.aptinfocollector.VO.Apartment;
import com.ewha.aptinfocollector.VO.Transaction;
import com.ewha.aptinfocollector.repository.ApartmentRepository;
import com.ewha.aptinfocollector.repository.TransactionRepository;

// API를 호출해서 DB에 업데이트할 객체 리스트를 리턴하는 서비스
@Service
public class APIsetter {
	@Autowired
	ApartmentRepository apartmentRepository;
	@Autowired
	TransactionRepository transactionRepository;

	@PersistenceContext
	private EntityManager em;

	public void main(String locationCode, String date) throws IOException {

		BufferedReader rd;
		Document doc = null;

		try {
			StringBuilder urlBuilder = new StringBuilder(
					"http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
					+ "=YjWouy%2BfplIs4bejgew6BhHDHBlxaYEUn2a%2BzHMYPhALM6dQm5S821OIrXd%2BC%2BwF7TT%2BKzLx%2Bb1eJ5kJEHvlNw%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "="
					+ URLEncoder.encode(locationCode, "UTF-8")); /* 지역코드 */
			urlBuilder.append(
					"&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /* 계약월 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
					+ URLEncoder.encode("100", "UTF-8")); /* 호출할row */

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String apiResult = "";
			String line;
			while ((line = rd.readLine()) != null) {
				apiResult = apiResult + line.trim();
			}
			rd.close();

			InputSource is = new InputSource(new StringReader(apiResult));

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = documentBuilderFactory.newDocumentBuilder();
			doc = builder.parse(is);

			doc.getDocumentElement().normalize();
			NodeList apiResults = doc.getElementsByTagName("item");

			System.out.println(apiResults.getLength());
			for (int i = 0; i < apiResults.getLength(); i++) {
				Transaction transaction = new Transaction();
				Apartment apartment = new Apartment();
				Node node = apiResults.item(i);
				Element element = (Element) node;
				String pName = element.getElementsByTagName("아파트").item(0).getTextContent();
				Double pSqm = Double.parseDouble(element.getElementsByTagName("전용면적").item(0).getTextContent());
				Integer pFloor = Integer.parseInt(element.getElementsByTagName("층").item(0).getTextContent());
				int price = Integer.parseInt(
						element.getElementsByTagName("거래금액").item(0).getTextContent().trim().replace(",", ""));
				String trxnY = element.getElementsByTagName("년").item(0).getTextContent();
				String trxnM = element.getElementsByTagName("월").item(0).getTextContent();
				if (!apartmentRepository.existsByNameAndSqmAndFloor(pName, pSqm, pFloor)) {
					System.out.println("get in!!");
					transaction.setTRXN_PRICE(price);
					transaction.setTRXN_Y(trxnY);
					transaction.setTRXN_M(trxnM);
					apartment.setAPT_BUILD_Y(element.getElementsByTagName("건축년도").item(0).getTextContent());
					apartment.setGU_CODE(
							Integer.parseInt(element.getElementsByTagName("법정동시군구코드").item(0).getTextContent()));
					apartment.setDONG_CODE(
							Integer.parseInt(element.getElementsByTagName("법정동읍면동코드").item(0).getTextContent()));
					apartment.setName(pName);
					apartment.setSqm(pSqm);
					apartment.setFloor(pFloor);
					transaction.setApartment(apartment);

					transactionRepository.save(transaction);
					apartmentRepository.save(apartment);

				} else {
					transaction.setTRXN_PRICE(price);
					transaction.setTRXN_Y(trxnY);
					transaction.setTRXN_M(trxnM);
					Apartment pApartment = apartmentRepository.findApartmentByNameAndFloorAndSqm(pName, pFloor, pSqm);
					transaction.setApartment(pApartment);
					transactionRepository.save(transaction);

				}

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
