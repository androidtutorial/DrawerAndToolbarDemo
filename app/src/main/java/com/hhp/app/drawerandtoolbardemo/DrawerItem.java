package com.hhp.app.drawerandtoolbardemo;

/**
 * Created by hvo5 on 9/16/2015.
 */
public class DrawerItem {

    public DrawerItem(int icon, int title) {
        this.icon = icon;
        this.title = title;
    }

    private int icon;
    private int title;


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
