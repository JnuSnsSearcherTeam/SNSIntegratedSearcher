package kr.jnu.embedded.snssearcher.base;

import lombok.Data;

/**
 * Created by Shane on 2017. 12. 1..
 */

@Data
public class Item {
    String name;
    String userImage;
    String date;
    String text;
    String textImage;

    public Item(String n, String ui, String d, String t, String ti) {
        name = n;
        userImage = ui;
        date = d;
        text = t;
        textImage = ti;
    }
}
