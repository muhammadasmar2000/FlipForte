package com.muhammadasmar.flipforte;

import java.util.ArrayList;

public class ArrayLists {
    public ArrayList<String> pdfName;
    public ArrayList<String> uploaded;
    public ArrayList<String> resource;

    public ArrayLists() {
        this.pdfName = new ArrayList<>();
        this.uploaded = new ArrayList<>();
        this.resource = new ArrayList<>();
    }

    public ArrayLists(ArrayList<String> pdfName, ArrayList<String> uploaded, ArrayList<String> location) {
        this.pdfName = pdfName;
        this.uploaded = uploaded;
        this.resource = location;
    }

    public ArrayList<String> getPdfName() {
        return pdfName;
    }

    public void setPdfName(ArrayList<String> pdfName) {
        this.pdfName = pdfName;
    }

    public ArrayList<String> getUploaded() {
        return uploaded;
    }

    public void setUploaded(ArrayList<String> uploaded) {
        this.uploaded = uploaded;
    }

    public ArrayList<String> getResource() {
        return resource;
    }

    public void setLocation(ArrayList<String> resource) {
        this.resource = resource;
    }
}
