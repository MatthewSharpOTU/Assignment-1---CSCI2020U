package sample;

import java.text.DecimalFormat;

// From the Assignment 1 PDF
public class TestFile {
    // Class Variables
    private String filename;
    private double spamProbability;
    private String actualClass;

    // Constructor Method
    public TestFile(String filename, double spamProbability, String actualClass) {
        this.filename = filename;
        this.spamProbability = spamProbability;
        this.actualClass = actualClass;
    }

    // Member Functions
    public String getFilename(){ return this.filename; }
    public double getSpamProbability(){ return this.spamProbability; }
    public String getSpamProbRounded(){
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }
    public String getActualClass(){return this.actualClass;}
    public void setFilename(String value) { this.filename = value; }
    public void setSpamProbability(double val) { this.spamProbability = val; }
    public void setActualClass(String value) { this.actualClass = value; }
}