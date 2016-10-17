package xyz.enhorse.datatypes;

import xyz.enhorse.forms.Messages;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Observable;

/**
 * Class for manipulation with price.xml
 * Created by PAK on 18.04.2014.
 */
public class PriceList {
    private static final String DEFAULT_LOADED = "Будет загружен прайс-лист по умолчанию.";
    private final static String ROOT = "pricelist";
    private final static String ID = "id";
    private final static String INKS_BRANCH = "inks";
    private final static String INKS_AMOUNT = "amount";
    private final static String INKS_BLACK = "black";
    private final static String INKS_COLOR = "color";
    private final static String PAPERS_BRANCH = "papers";
    private final static String PAPER_PAPER = "paper";
    private final static String PAPER_NAME = "name";
    private final static String PAPER_FORMAT = "format";
    private final static String PAPER_PRICE = "price";
    private final static long[] INTERVALS = {0, 70, 100, 200, 400, 1000};
    private final static double[][] INK_PRICES = {{0.4, 0.5},
            {0.3, 0.4},
            {0.25, 0.35},
            {0.2, 0.3},
            {0.15, 0.25},
            {0.1, 0.2}};

    private String filename;
    Map<Long, Double[]> inkPrices = new TreeMap<>();
    Map<Long, Paper> paperPrices = new TreeMap<>();
    private Observable state = new Observable() {
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public PriceList(String xmlFilename) {
        super();
        this.filename = xmlFilename;
        read();
    }

    public Map<Long, Double[]> getInkPrices(){
        return inkPrices;
    }

    public Map<Long, Paper> getPaperPrices(){
        return paperPrices;
    }

    public Set<Paper> getAvailablePapers() {
        Set<Paper> result = new HashSet<>();
        for (Map.Entry<Long, Paper> paper : paperPrices.entrySet()) {
            result.add(paper.getValue());
        }
        return result;
    }

    public double getPrintingPrice(long amount, boolean isBW) {
        Double[] result = {0.0D, 0.0D};
        for (Map.Entry<Long, Double[]> ink : inkPrices.entrySet()) {
            if (ink.getKey() <= amount) {
                result = ink.getValue();
            } else {
                break;
            }
        }
        return isBW ? result[0] : result[1];
    }

    public void addPaper(String name, Formats format, double price) {
        Paper paper = new Paper(name, format, price);
        paperPrices.put((long)paper.hashCode(), paper);
        write();
    }

    public void removePaper(long id) {
        paperPrices.remove(id);
        write();
    }

    public void clearPapers() {
        paperPrices.clear();
    }

    public void addInk(long interval, double blackPrice, double colorPrice) {
        inkPrices.put(interval, new Double[]{blackPrice, colorPrice});
        write();
    }

    public void removeInk(long id) {
        inkPrices.remove(id);
        write();
    }

    public void clearInks(){
        inkPrices.clear();
    }

    public void update() {
        this.read();
        state.notifyObservers(this);
    }

    private void read() {
        try {
            File fXmlFile = new File(this.filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            inkPrices.clear();
            NodeList inkList = doc.getElementsByTagName(INKS_AMOUNT);
            for (int i = 0; i < inkList.getLength(); i++) {
                Node inkNode = inkList.item(i);
                if (inkNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) inkNode;
                    long id = Long.parseLong(eElement.getAttribute(ID));
                    double black = Double.parseDouble(eElement.getElementsByTagName(INKS_BLACK).item(0).getTextContent());
                    double color = Double.parseDouble(eElement.getElementsByTagName(INKS_COLOR).item(0).getTextContent());
                    inkPrices.put(id, new Double[]{black, color});
                }
            }

            paperPrices.clear();
            NodeList paperList = doc.getElementsByTagName(PAPER_PAPER);
            for (int i = 0; i < paperList.getLength(); i++) {
                Node paperNode = paperList.item(i);
                if (paperNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) paperNode;
                    long id = Long.parseLong(eElement.getAttribute(ID));
                    String name = eElement.getElementsByTagName(PAPER_NAME).item(0).getTextContent();
                    Formats format = Formats.parseFormat(eElement.getElementsByTagName(PAPER_FORMAT).item(0).getTextContent());
                    double price = Double.parseDouble(eElement.getElementsByTagName(PAPER_PRICE).item(0).getTextContent());
                    Paper paper = new Paper(name, format, price);
                    paperPrices.put(id, paper);
                }
            }
        } catch (IOException e) {
            Messages.frameMsg("Price.xml не найден!\n" + DEFAULT_LOADED);
            create();
        } catch (ParserConfigurationException e) {
            Messages.frameMsg("Price.xml: parsing error!\n" + DEFAULT_LOADED);
            create();
        } catch (SAXException e) {
            Messages.frameMsg("Price.xml: SAXException error!\n" + DEFAULT_LOADED);
            create();
        } catch (NumberFormatException e) {
            Messages.frameMsg("Price.xml: Number error!\n" + DEFAULT_LOADED);
            create();
        }

    }

    private void write() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(ROOT);
            doc.appendChild(rootElement);

            Element inks = doc.createElement(INKS_BRANCH);
            rootElement.appendChild(inks);
            for (Map.Entry<Long, Double[]> pair : inkPrices.entrySet()) {
                createInk(
                        doc, inks, pair.getKey(),
                        pair.getValue()[0],
                        pair.getValue()[1]);
            }

            Element paper = doc.createElement(PAPERS_BRANCH);
            rootElement.appendChild(paper);
            for (Map.Entry<Long, Paper> pair : paperPrices.entrySet()) {
                createPaper(
                        doc, paper, pair.getValue());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            Messages.frameMsg("Price.xml: Ошибка при записи!\n");
        }
    }

    private void create() {
        for (int i = 0; i < INTERVALS.length; i++) {
            inkPrices.put(INTERVALS[i], new Double[]{INK_PRICES[i][0], INK_PRICES[i][1]});
        }
        paperPrices.put(0L, new Paper("По умолчанию", Formats.A4, 0.5));
        write();
    }

    private void createInk(Document doc, Element parent, long interval, double blackPrice, double colorPrice) {
        Element amount = doc.createElement(INKS_AMOUNT);
        parent.appendChild(amount);
        amount.setAttribute(ID, String.valueOf(interval));
        amount.appendChild(this.createElement(doc, INKS_BLACK, String.valueOf(blackPrice)));
        amount.appendChild(this.createElement(doc, INKS_COLOR, String.valueOf(colorPrice)));
    }

    private void createPaper(Document doc, Element parent, Paper paper) {
        Element paper_paper = doc.createElement(PAPER_PAPER);
        parent.appendChild(paper_paper);
        paper_paper.setAttribute(ID, String.valueOf(paper.hashCode()));
        paper_paper.appendChild(this.createElement(doc, PAPER_NAME, paper.getName()));
        paper_paper.appendChild(this.createElement(doc, PAPER_FORMAT, paper.getFormat().getName()));
        paper_paper.appendChild(this.createElement(doc, PAPER_PRICE, String.valueOf(paper.getPrice())));
    }

    private Element createElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }

    public void addObserver(Observer o)
    {
        state.addObserver(o);
    }


}