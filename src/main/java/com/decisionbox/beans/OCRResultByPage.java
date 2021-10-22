
package com.decisionbox.beans;

import java.util.TreeMap;
import java.io.File;
import java.util.ArrayList;

public class OCRResultByPage
{
    ArrayList<File> pdfPAges;
    ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> tableResult;
    ArrayList<TreeMap<String, Object>> formFieldArray;
    ArrayList<TreeMap<String, Object>> allData;
    
    public OCRResultByPage() {
        this.pdfPAges = new ArrayList<File>();
        this.tableResult = new ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>>();
        this.formFieldArray = new ArrayList<TreeMap<String, Object>>();
        this.allData = new ArrayList<TreeMap<String, Object>>();
    }
    
    public ArrayList<TreeMap<String, Object>> getAllData() {
        return this.allData;
    }
    
    public void setAllData(final ArrayList<TreeMap<String, Object>> allData) {
        this.allData = allData;
    }
    
    public ArrayList<File> getPdfPAges() {
        return this.pdfPAges;
    }
    
    public void setPdfPAges(final ArrayList<File> pdfPAges) {
        this.pdfPAges = pdfPAges;
    }
    
    public ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> getTableResult() {
        return this.tableResult;
    }
    
    public void setTableResult(final ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> tableResult) {
        this.tableResult = tableResult;
    }
    
    public ArrayList<TreeMap<String, Object>> getFormFieldArray() {
        return this.formFieldArray;
    }
    
    public void setFormFieldArray(final ArrayList<TreeMap<String, Object>> formFieldArray) {
        this.formFieldArray = formFieldArray;
    }
    
    public void pushToPdfPages(final File pdfFile) {
        this.pdfPAges.add(pdfFile);
    }
    
    public void pushToFormFieldArray(final TreeMap<String, Object> formField) {
        this.formFieldArray.add(formField);
    }
}