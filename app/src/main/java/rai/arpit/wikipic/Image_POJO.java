package rai.arpit.wikipic;

/**
 * Created by Rai_Sahab on 6/5/2016.
 */
public class Image_POJO {
    private String title;
    private String source;
    private int width;
    private int height;

    public Image_POJO(String title, String source, int width, int height) {
        this.title = title;
        this.source = source;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return source;
    }

    public void setSrc(String source) {
        this.source = source;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
