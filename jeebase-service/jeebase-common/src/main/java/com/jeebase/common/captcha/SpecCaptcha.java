package com.jeebase.common.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class SpecCaptcha extends Captcha {

    public SpecCaptcha() {
    }

    public SpecCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public SpecCaptcha(int width, int height, int len) {
        this(width, height);
        this.len = len;
    }

    public SpecCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.font = font;
    }

    @Override
    public void out(OutputStream out) {
        this.graphicsImage(this.alphas(), out);
    }

    private boolean graphicsImage(char[] strs, OutputStream out) {
        boolean ok = false;
        try {
            BufferedImage e = new BufferedImage(this.width, this.height, 1);
            Graphics2D g = (Graphics2D) e.getGraphics();
            int len = strs.length;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.width, this.height);
            Color color;
            int h;
            for (h = 0; h < 15; ++h) {
                color = this.color(150, 250);
                g.setColor(color);
                g.drawOval(num(this.width), num(this.height), 5 + num(10), 5 + num(10));
                color = null;
            }
            g.setFont(this.font);
            h = this.height - (this.height - this.font.getSize() >> 1);
            int w = this.width / len;
            int size = w - this.font.getSize() + 1;
            for (int i = 0; i < len; ++i) {
                AlphaComposite ac3 = AlphaComposite.getInstance(3, 0.7F);
                g.setComposite(ac3);
                color = new Color(20 + num(110), 20 + num(110), 20 + num(110));
                g.setColor(color);
                g.drawString(String.valueOf(strs[i]), this.width - (len - i) * w + size, h - 4);
                color = null;
                ac3 = null;
            }
            ImageIO.write(e, "png", out);
            out.flush();
            ok = true;
        } catch (IOException arg20) {
            ok = false;
        } finally {
            try {
                out.close();
            } catch (IOException arg19) {
                arg19.printStackTrace();
            }
        }
        return ok;
    }
}