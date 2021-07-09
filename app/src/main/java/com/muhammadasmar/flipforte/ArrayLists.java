package com.muhammadasmar.flipforte;

import java.util.ArrayList;

public class ArrayLists {
    public ArrayList<String> pdfName;
    public ArrayList<String> uploaded;
    public ArrayList<String> location;

    public ArrayLists() {
        this.pdfName = new ArrayList<>();
        this.uploaded = new ArrayList<>();
        this.location = new ArrayList<>();
    }

    public ArrayLists(ArrayList<String> pdfName, ArrayList<String> uploaded, ArrayList<String> location) {
        this.pdfName = pdfName;
        this.uploaded = uploaded;
        this.location = location;
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

    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }
}
