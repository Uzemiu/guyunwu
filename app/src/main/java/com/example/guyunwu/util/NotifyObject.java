package com.example.guyunwu.util;

import android.app.Activity;
import androidx.annotation.DrawableRes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotifyObject implements Serializable {
    public Integer type;
    public String title;
    public String subText;
    public String content;
    public String param;
    public Long firstTime;
    public Class<? extends Activity> activityClass;
    @DrawableRes
    public int icon;
    public List<Long> times = new ArrayList<>();

    public static byte[] toBytes(NotifyObject obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);
        oos.close();
        byte[] bytes = bout.toByteArray();
        bout.close();
        return bytes;
    }

    public static NotifyObject from(String content) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bin = new ByteArrayInputStream(content.getBytes("ISO-8859-1"));
        ObjectInputStream ois;
        NotifyObject obj;

        ois = new ObjectInputStream(bin);
        obj = (NotifyObject) ois.readObject();
        ois.close();
        bin.close();
        return obj;
    }

    public static String to(NotifyObject obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        String content;
        oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);
        oos.close();
        content = bout.toString("ISO-8859-1");
        bout.close();
        return content;
    }
}
