package sku.uitest1;

import android.graphics.Bitmap;

/**
 * Created by 618 on 2015-09-23.
 */
public class Listviewitem {
    private Bitmap classIcon;
    private String lect_name;
    private String lect_time;
    private String teacher_name;

    public Listviewitem(Bitmap classIcon, String lect_name, String lect_time, String teacher_name) {
        this.classIcon = classIcon;
        this.lect_name = lect_name;
        this.lect_time = lect_time;
        this.teacher_name = teacher_name;
    }

    public Bitmap getClassIcon() {
        return classIcon;
    }

    public void setClassIcon(Bitmap classIcon) {
        this.classIcon = classIcon;
    }

    public String getLect_name() {
        return lect_name;
    }

    public void setLect_name(String lect_name) {
        this.lect_name = lect_name;
    }

    public String getLect_time() {
        return lect_time;
    }

    public void setLect_time(String lect_time) {
        this.lect_time = lect_time;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }



}
