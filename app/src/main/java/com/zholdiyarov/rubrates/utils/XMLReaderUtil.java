package com.zholdiyarov.rubrates.utils;

import android.provider.Telephony;

import com.zholdiyarov.rubrates.objects.Rates;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * author: szholdiyarov
 * date:17/02/2016
 * Purpose: This is helper class to parse xml.
 */
public class XMLReaderUtil {

    /* Variable declaration */
    private ArrayList<Rates> arrayListOfRates;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private final String VALUTE = "Valute";
    private final String CHAR_CODE = "CharCode";
    private final String VALUE = "Value";
    private final String NOMINAL = "Nominal";

    /* Initial setup */
    public XMLReaderUtil(InputSource inputSource) throws ParserConfigurationException, IOException, SAXException {
        arrayListOfRates = new ArrayList<>();
        dbFactory
                = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(inputSource);
        doc.getDocumentElement().normalize();
    }

    public ArrayList<Rates> getRates() {

        NodeList nList = doc.getElementsByTagName(VALUTE); // get parent tag

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp); // get current node

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode; // get current element

                /* parse element */
                String charCode = getElement(eElement, CHAR_CODE);
                String rate = getElement(eElement, VALUE);
                String nominal = getElement(eElement, NOMINAL);

                Rates rates = new Rates(charCode, rate, nominal);
                arrayListOfRates.add(rates);
            }
        }
        return arrayListOfRates;
    }

    /* returns element by given text*/
    private String getElement(Element element, String elementName) {
        return element.getElementsByTagName(elementName)
                .item(0)
                .getTextContent();
    }


}
