//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class TemplateChecker {
    ArrayList<String> templateArray = new ArrayList(Arrays.asList("Quality Mat Inc", "Textile Max", "Mac", "National Tooling", "LA B E L"));
    ArrayList<String> vendorArray = new ArrayList(Arrays.asList("Quality Mat Inc", "Textile Max", "Mac & Associates", "National Tooling", "National Label"));
    String ocrContent = null;
    String template = null;
    ArrayList<Rectangle> rectangles = new ArrayList();
    BufferedImage bigray = null;

    public String getOcrContent() {
        return this.ocrContent;
    }

    public void setOcrContent(String ocrContent) {
        this.ocrContent = ocrContent;
    }

    public TemplateChecker(ArrayList<Rectangle> rectangles, BufferedImage bigray) {
        this.rectangles = rectangles;
        this.bigray = bigray;
    }

    public TemplateChecker(BufferedImage bigray) {
        this.bigray = bigray;
    }

    public String getTemplate() {
        return this.template;
    }

    public TemplateChecker(String ocrContent) {
        this.ocrContent = ocrContent;
    }

    public TemplateChecker(String ocrContent, ArrayList<Rectangle> rectangles) {
        this.ocrContent = ocrContent;
        this.rectangles = rectangles;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }

    public void setRectangles(ArrayList<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public void calculateTemplateFromOCRContent() {
        String text = this.ocrContent;

        for(int i = 0; i < this.templateArray.size(); ++i) {
            if (text.matches(".*" + (String)this.templateArray.get(i) + "\\s.*")) {
                this.template = (String)this.vendorArray.get(i);
                break;
            }
        }

    }

    public void calculateTemplateFromGrayScaleImage() {
    }
}
