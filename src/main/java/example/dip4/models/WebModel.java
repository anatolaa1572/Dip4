package example.dip4.models;

public class WebModel {
    private double id;
    private String nameWeb;
    private String refWeb;
    private String textWeb;

    public WebModel(double id, String nameWeb, String refWeb, String textWeb) {
        this.id = id;
        this.nameWeb = nameWeb;
        this.refWeb = refWeb;
        this.textWeb = textWeb;
    }

    public WebModel() {
    }

    public double getId() {
        return id;
    }

    public String getNameWeb() {
        return nameWeb;
    }

    public String getRefWeb() {
        return refWeb;
    }

    public String getTextWeb() {
        return textWeb;
    }

    public void setId(double id) {
        this.id = id;
    }

    public void setNameWeb(String nameWeb) {
        this.nameWeb = nameWeb;
    }

    public void setRefWeb(String refWeb) {
        this.refWeb = refWeb;
    }

    public void setTextWeb(String textWeb) {
        this.textWeb = textWeb;
    }

    @Override
    public String toString() {
        return "id= " + id + "'\n'" + "nameWeb= " + nameWeb + "'\n'" + ", refWeb= " + refWeb + "'\n'" + ", textWeb= " + textWeb + "'\n'" ;
    }
}
