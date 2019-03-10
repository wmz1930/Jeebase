package com.jeebase.common.captcha;

import java.awt.*;
import java.io.OutputStream;

public abstract class Captcha extends Randoms {

    protected Font font = new Font("Verdana", 3, 28);

    protected int len = 5;

    protected int width = 150;

    protected int height = 40;

    private String chars = null;

    protected char[] alphas() {
        char[] cs = new char[this.len];
        for (int i = 0; i < this.len; ++i) {
            cs[i] = alpha();
        }
        this.chars = new String(cs);
        return cs;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * @param arg0
     */
    public abstract void out(OutputStream arg0);

    public String text() {
        return this.chars;
    }

    public char[] textChar() {
        return this.chars.toCharArray();
    }
}
