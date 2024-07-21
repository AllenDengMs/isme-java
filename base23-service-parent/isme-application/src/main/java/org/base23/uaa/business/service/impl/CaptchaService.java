package org.base23.uaa.business.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

/**
 验证码
 */
@Service
public class CaptchaService {

  private Random random = new Random();

  private static final String CAPTCHA_KEY = "captchaKey";

  /**
   写一个方法随机获取颜色
   */
  private Color getRandomColor() {
    //随机生成0-255之间整数
    int red = random.nextInt(256);
    int green = random.nextInt(256);
    int blue = random.nextInt(256);
    //参数：红，绿，蓝 (0-255)
    return new Color(red, green, blue);
  }


  public String createImgCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 1. 创建缓存图片：指定宽
    int width = 90, height = 30;
    //参数：宽，高，图片模式
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 2. 获取画笔对象
    Graphics graphics = img.getGraphics();
    // 3. 设置画笔颜色
    graphics.setColor(Color.WHITE);
    // 4. 填充矩形区域
    graphics.fillRect(0, 0, width, height);
    // 5. 从字符数组中随机得到字符
    char[] arr = {'A', 'B', 'C', 'D', 'N', 'E', 'W', 'b', 'o', 'y','l','c','q',
        '0', '1', '2', '3', '4', '5', '6','7','8','9'};
    // 6. 循环4次，画4个字符
    StringBuilder captcha = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      int index = random.nextInt(arr.length);
      char c = arr[index];  //随机得到一个字符
      // 7. 设置字的颜色为随机
      graphics.setColor(getRandomColor());
      // 8. 设置字体，大小为18。参数：字体，样式，大小
      graphics.setFont(new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 19));
      // 9. 将每个字符画到图片，x增加，y不变。
      //参数：画字符，x位置，y位置。把c从字符转成字符串
      String str = String.valueOf(c);
      captcha.append(str);
      graphics.drawString(str, 10 + (i * 20), 20);
    }
    for (int i = 0; i < 8; i++) {
      // 10. 线的位置是随机的，x范围在width之中，y的范围在height之中。
      int x1 = random.nextInt(width);
      int y1 = random.nextInt(height);
      int x2 = random.nextInt(width);
      int y2 = random.nextInt(height);
      // 11. 画8条干扰线，每条线的颜色不同
      graphics.setColor(getRandomColor());
      graphics.drawLine(x1, y1, x2, y2);
    }

    request.getSession().setAttribute(CAPTCHA_KEY, captcha.toString()); // todo 校验验证码

    // 12. 将缓存的图片输出到响应输出流中
    //参数：图片对象，图片格式，输出流
    ImageIO.write(img, "jpg", response.getOutputStream());
    return captcha.toString();
  }
}

