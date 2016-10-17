package xyz.enhorse.datatypes;

import java.util.*;

/**
 * Created by PAK on 02.04.2014.
 */
public class Circulation {
    private List<Page> pages = new ArrayList<Page>();

    public Circulation(List<Page> pages) {
        this.setPages(pages);
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void addPage(Page page) {
        if (page != null) {
            this.pages.add(page);
        }
    }

    public double costPaper(PriceList price) {
        double result = 0.0D;
        for (Page page : pages) {
            Paper paper = page.getPaper();
            result +=paper.getPrice() * page.getQuantity();
        }
        return result;
    }

    public double costPrinting(PriceList price) {
        double result = 0.0D;
        for (Page page : pages) {
            Paper paper = page.getPaper();
            double printPrice = 0;
            long amount = (int) Math.ceil(paper.getFormat().asA4()) * page.getQuantity();
            for (Inks ink : page.getFront().getInks()) {
                printPrice += price.getPrintingPrice(amount, Inks.isBW(ink));
            }
            for (Inks ink : page.getBack().getInks()) {
                printPrice += price.getPrintingPrice(amount, Inks.isBW(ink));
            }
            result += printPrice * amount;
        }
        return result;
    }

    public double cost(PriceList price) {
        return this.costPrinting(price) + this.costPaper(price);
    }
}
