package com.jeebase.common.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class GifCaptcha extends Captcha {

    public GifCaptcha() {
    }

    public GifCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
        this.alphas();
    }

    public GifCaptcha(int width, int height, int len) {
        this(width, height);
        this.len = len;
    }

    public GifCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.font = font;
    }

    @Override
    public void out(OutputStream os) {
        try {
            GifEncoder gifEncoder = new GifEncoder();
            gifEncoder.start(os);
            gifEncoder.setQuality(180);
            gifEncoder.setDelay(200);
            gifEncoder.setRepeat(0);
            char[] rands = this.textChar();
            Color[] fontcolor = new Color[this.len];
            int i;
            for (i = 0; i < this.len; ++i) {
                fontcolor[i] = new Color(20 + num(110), 20 + num(110), 20 + num(110));
            }
            for (i = 0; i < this.len; ++i) {
                BufferedImage frame = this.graphicsImage(fontcolor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
        } finally {
            try {
                os.close();
            } catch (IOException arg11) {
                arg11.printStackTrace();
            }
        }
    }

    private BufferedImage graphicsImage(Color[] fontcolor, char[] strs, int flag) {
        BufferedImage image = new BufferedImage(this.width, this.height, 1);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(this.width, this.height,Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d=image.createGraphics();
        g2d.setColor(new Color(64,158,255));
        g2d.setStroke(new BasicStroke(1));
        g2d.fillRect(0, 0, this.width, this.height);
        int h = this.height - (this.height - this.font.getSize() >> 1);
        int w = this.width / this.len;
        g2d.setFont(this.font);
        for (int i = 0; i < this.len; ++i) {
            AlphaComposite ac3 = AlphaComposite.getInstance(3, this.getAlpha(flag, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(num(this.width), num(this.height), 5 + num(10), 5 + num(10));
            g2d.drawString(String.valueOf(strs[i]), this.width - (this.len - i) * w + (w - this.font.getSize()) + 1,
                    h - 4);
        }
        g2d.dispose();
        return image;
    }

    private float getAlpha(int i, int j) {
        int num = i + j;
        float r = 1.0F / this.len;
        float s = (this.len + 1) * r;
        return num > this.len ? num * r - s : num * r;
    }
}