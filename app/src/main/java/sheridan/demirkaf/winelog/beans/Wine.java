package sheridan.demirkaf.winelog;

import java.util.ArrayList;
import java.util.Date;

public class Wine {
    private String name;
    private String year;
    private String wineryName;
    private Date dateOfVisit;
    private String style;
    private int oak;
    private int flavourIntensity;
    private ArrayList<String> mainFlavours;
    private int rating;
    private String notes;
    private String base64Image;

    public Wine(String name, String year, String wineryName, Date dateOfVisit, String style, int oak, int flavourIntensity, ArrayList<String> mainFlavours, int rating, String notes, String base64Image) {
        this.name = name;
        this.year = year;
        this.wineryName = wineryName;
        this.dateOfVisit = dateOfVisit;
        this.style = style;
        this.oak = oak;
        this.flavourIntensity = flavourIntensity;
        this.mainFlavours = mainFlavours;
        this.rating = rating;
        this.notes = notes;
        this.base64Image = base64Image;
    }

    public Wine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String wineryName) {
        this.wineryName = wineryName;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getOak() {
        return oak;
    }

    public void setOak(int oak) {
        this.oak = oak;
    }

    public int getFlavourIntensity() {
        return flavourIntensity;
    }

    public void setFlavourIntensity(int flavourIntensity) {
        this.flavourIntensity = flavourIntensity;
    }

    public ArrayList<String> getMainFlavours() {
        return mainFlavours;
    }

    public void setMainFlavours(ArrayList<String> mainFlavours) {
        this.mainFlavours = mainFlavours;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

}
