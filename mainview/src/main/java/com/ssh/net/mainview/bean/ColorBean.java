package com.ssh.net.mainview.bean;

import java.io.Serializable;

public class ColorBean implements Serializable {
    int res;

    int type;

    public ColorBean(int res, int type) {
        this.res = res;
        this.type = type;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
