package kr.jnu.embedded.snssearcher.data;

import kr.jnu.embedded.snssearcher.base.Item;

public class TwitterItem {
    private String name;
    private String userImage;
    private String date;
    private String text;
    private String textImage;

    public TwitterItem(String name, String userImage, String date, String text, String textImage) {
        this.name = name;
        this.userImage = userImage;
        this.date = date;
        this.text = text;
        this.textImage = textImage;
    }

    @Override
    public String toString() {
        return "TwitterItem{" +
                "name='" + name + '\'' +
                ", userImage='" + userImage + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", textImage='" + textImage + '\'' +
                '}';
    }

    public Item toTwitterItem(){
        return new Item(name, userImage, date, text, textImage);
    }
}
