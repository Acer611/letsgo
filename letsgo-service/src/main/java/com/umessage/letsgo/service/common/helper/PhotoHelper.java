package com.umessage.letsgo.service.common.helper;

import com.umessage.letsgo.core.utils.IDUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 绘制透明背景的文字图片
  */
public class PhotoHelper {

    private static String FONT_FACE = "微软雅黑";

	/**
	 * 绘制一个带文字的圆形
	 * @param width
	 * @param height
	 * @param fontHeight
	 * @param fillColor
	 * @param drawStr
	 * @return
	 */
    public static BufferedImage drawTranslucentStringPic(int width, int height, Integer fontHeight, Color fillColor, String drawStr) {  
        try {  
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();

            // 设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
            gd = buffImg.createGraphics();  
            // 设置透明  end

            // 设置平滑性
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 画圆形
            gd.setColor(fillColor);	// 设置填充色
            gd.fillOval(0, 0, width, height);
            
            // 画字体
            gd.setColor(Color.WHITE); //设置字体颜色  
            Font font = new Font(FONT_FACE, Font.PLAIN, fontHeight);
            gd.setFont(font);

            // 设置文字垂直居中
            FontMetrics fm = gd.getFontMetrics();
            int x = setFontX(fm, drawStr, width);
            int y = setFontY(fm, height);

            gd.drawString(drawStr, x, y);

            gd.dispose();
            return buffImg;  
        } catch (Exception e) {
            return null;  
        }  
    }

    /**
     * 绘制两个带文字的圆形
     * @param width
     * @param height
     * @param fontHeight
     * @param fillColor
     * @param drawStrList
     * @return
     */
    public static BufferedImage drawTranslucentStringPic1(int width, int height, List<Integer> fontHeight, List<Color> fillColor, List<String> drawStrList) {

        try {
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            // 设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            gd = buffImg.createGraphics();
            // 设置透明  end

            // 设置平滑性
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 0;
            int y = 0;
            FontMetrics fm = null;
            Font font = null;

            // 画第一个圆形
            gd.setColor(fillColor.get(0));	// 设置填充色
            gd.fillOval(0, 41, 80, 80);
            gd.setColor(Color.WHITE); //设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(0));
            gd.setFont(font); //设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(0), 80);
            y = setFontY(fm, height);
            gd.drawString(drawStrList.get(0), x, y);


            return buffImg;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 绘制两个带文字的圆形
     * @param width
     * @param height
     * @param fontHeight
     * @param fillColor
     * @param drawStrList
     * @return
     */
    public static BufferedImage drawTranslucentStringPic2(int width, int height, List<Integer> fontHeight, List<Color> fillColor, List<String> drawStrList) {
        try {
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            // 设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            gd = buffImg.createGraphics();
            // 设置透明  end

            // 设置平滑性
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 0;
            int y = 0;
            FontMetrics fm = null;
            Font font = null;

            // 画第一个圆形
            gd.setColor(fillColor.get(0));	// 设置填充色
            gd.fillOval(0, 41, 80, 80);
            gd.setColor(Color.WHITE); //设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(0));
            gd.setFont(font); //设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(0), 80);
            y = setFontY(fm, height);
            gd.drawString(drawStrList.get(0), x, y);

            // 画第二个圆形
            gd.setColor(fillColor.get(1));	// 设置填充色
            gd.fillOval(82, 41, 80, 80);
            gd.setColor(Color.WHITE); //设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(1));
            gd.setFont(font); //设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(1), 80);
            y = setFontY(fm, height);
            gd.drawString(drawStrList.get(1), (x + 82), y);

            return buffImg;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 绘制三个带文字的圆形
     * @param width
     * @param height
     * @param fontHeight
     * @param fillColor
     * @param drawStrList
     * @return
     */
    public static BufferedImage drawTranslucentStringPic3(int width, int height, List<Integer> fontHeight, List<Color> fillColor, List<String> drawStrList) {
        try {
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            // 设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            gd = buffImg.createGraphics();
            // 设置透明  end

            // 设置平滑性
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 0;
            int y = 0;
            Font font = null;
            FontMetrics fm = null;

            // 画第一个圆形
            gd.setColor(fillColor.get(0));	// 设置填充色
            gd.fillOval(41, 0, 80, 80);
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(0));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(0), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(0), x + 41, y);

            // 画第二个圆形
            gd.setColor(fillColor.get(1));	// 设置填充色
            gd.fillOval(0, 82, 80, 80);	// 填充
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(1));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(1), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(1), x, y + 82);

            // 画第三个圆形
            gd.setColor(fillColor.get(2));	// 设置填充色
            gd.fillOval(82, 82, 80, 80);	// 填充
            gd.setColor(Color.WHITE); //设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(2));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(2), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(2), x + 82, y + 82);

            return buffImg;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 绘制四个带文字的圆形
     * @param width
     * @param height
     * @param fontHeight
     * @param fillColor
     * @param drawStrList
     * @return
     */
    public static BufferedImage drawTranslucentStringPic4(int width, int height, List<Integer> fontHeight, List<Color> fillColor, List<String> drawStrList) {
        try {  
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);  
            Graphics2D gd = buffImg.createGraphics();  
            // 设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
            gd.dispose();
            gd = buffImg.createGraphics();
            // 设置透明  end

            // 设置平滑性
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 0;
            int y = 0;
            Font font = null;
            FontMetrics fm = null;

            // 画第一个圆形
            gd.setColor(fillColor.get(0));	// 设置填充色
            gd.fillOval(0, 0, 80, 80);	// 填充
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(0));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(0), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(0), x, y);

            // 画第二个圆形
            gd.setColor(fillColor.get(1));	// 设置填充色
            gd.fillOval(82, 0, 80, 80);	// 填充
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(1));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(1), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(1), (x + 82), y);
            
            // 画第三个圆形
            gd.setColor(fillColor.get(2));	// 设置填充色
            gd.fillOval(0, 82, 80, 80);	// 填充
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(2));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(2), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(2), x, (y + 82));
            
            // 画第四个圆形
            gd.setColor(fillColor.get(3));	// 设置填充色
            gd.fillOval(82, 82, 80, 80);	// 填充
            gd.setColor(Color.WHITE); // 设置字体颜色
            font = new Font(FONT_FACE, Font.PLAIN, fontHeight.get(3));
            gd.setFont(font); // 设置字体
            fm = gd.getFontMetrics();
            x = setFontX(fm, drawStrList.get(3), 80);
            y = setFontY(fm, 80);
            gd.drawString(drawStrList.get(3), (x + 82), (y + 82));
            
            gd.dispose();
            
            return buffImg;  
        } catch (Exception e) {
            return null;  
        }  
    }

    /**
     * 计算文字的左上角的x坐标
     * @param fm
     * @param str
     * @param width
     * @return
     */
    private static int setFontX(FontMetrics fm, String str, int width) {
        int textWidth = fm.stringWidth(str);
        int x = (width - textWidth) / 2;
        return x;
	}

    /**
     * 计算文字的左上角的y坐标
     * @param fm
     * @param height
     * @return
     */
    private static int setFontY(FontMetrics fm, int height) {
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (height - (ascent + descent)) / 2 + ascent;
        return y;
	}
    
    /**
     * 获取女性颜色
     * @return
     */
    private static Color getRedColor() {
		return new Color(255, 111, 148);
	}

    /**
     * 获取男性颜色
     * @return
     */
    public static Color getBlueColor() {
        return new Color(88, 186, 222);
    }

    /**
     * 生成小组或者团队头像
     * @param names
     * @param sexs
     * @return
     */
    public static String createGroupOrTeamImage(List<String> names, List<Integer> sexs) throws Exception {
        String imgUrl = ""; // 头像链接
        BufferedImage bufferedImage = null;
        int width = 162;
        int height = 162;
        List<Color> colors = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Integer> fonts = new ArrayList<>();

        for (Integer sex : sexs) {
            Color color = getColorBySex(sex);
            colors.add(color);
        }

        for (String name : names) {
            // 判断名字是中文名还是英文名
            if (name.matches("^[A-Za-z][A-Za-z\\s]*[A-Za-z]$")) {
                name = subStringByEnglishName(name);
                nameList.add(name);
                fonts.add(44);
            } else {
                name = subStringByChineseName(name);
                nameList.add(name);
                fonts.add(30);
            }
        }
        if(names.size()==1){

            bufferedImage = drawTranslucentStringPic1(width, height, fonts, colors,nameList);
        }
        else if (names.size() == 2) {
            // 生成两个圆形的头像
            bufferedImage = drawTranslucentStringPic2(width, height, fonts, colors, nameList);
        } else if (names.size() == 3) {
            // 生成三个圆形的头像
            bufferedImage = drawTranslucentStringPic3(width, height, fonts, colors, nameList);
        } else {
            // 生成四个圆形的头像
            bufferedImage = drawTranslucentStringPic4(width, height, fonts, colors, nameList);
        }

        // 保存到服务器上
//        imgUrl = savaImageFile(bufferedImage);

        // 保存在腾讯云存储上
        imgUrl = saveQCloudFile(bufferedImage);

        return imgUrl;
    }

    /**
     * 生成成员头像
     * @param name
     * @param sex
     * @return
     */
    public static String createMemberImage(String name, Integer sex) throws Exception {
        String imgUrl = ""; // 头像链接
        BufferedImage bufferedImage = null;
        int width = 158;
        int height = 158;
        Integer fontHight = 60;
        Color color = getColorBySex(sex);

        if (name.matches("^[A-Za-z][A-Za-z\\s]*[A-Za-z]$")) {
            name = subStringByEnglishName(name);
            fontHight = 88;
        } else {
            name = subStringByChineseName(name);
            fontHight = 60;
        }

        bufferedImage = drawTranslucentStringPic(width, height, fontHight, color, name);

        // 保存到服务器上
//        imgUrl = savaImageFile(bufferedImage);

        // 保存在腾讯云存储上
        imgUrl = saveQCloudFile(bufferedImage);
        return imgUrl;
    }

    /**
     * 处理中文名的截取方法
     * @param name
     * @return
     */
    private static String subStringByChineseName(String name) {
        int len = name.length();
        if (len > 2) {
            name = name.substring(len - 2, len);
        }

        return name;
    }

    /**
     * 处理英文名的截取方法
     * @param name
     * @return
     */
    private static String subStringByEnglishName(String name) {
        // 截取首字母
        int len = name.length();
        if (len > 1) {
            name = name.substring(0, 1);
        }
        // 转换成大写字母
        name = name.toUpperCase();
        return name;
    }

    private static Color getColorBySex(Integer sex) {
        Color color = getRedColor();
        if (sex == 1) {
            color = getBlueColor();
        } else {
            color = getRedColor();
        }

        return color;
    }

    /**
     * 保存到自己的服务器上
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    private static String savaImageFile(BufferedImage bufferedImage) throws IOException {
        String fileName = IDUtil.uuid() + ".png";
        File directory = new File("Images");
        String currentPath = directory.getAbsolutePath() + File.separator + fileName;
        File dir = new File(currentPath);
        if (!dir.getParentFile().exists()) {
            dir.mkdirs();
        }

        ImageIO.write(bufferedImage, "png", dir);

        return currentPath;
    }

    /**
     * 保存头像到腾讯云存储服务器上
     * @param bufferedImage
     * @return
     * @throws Exception
     */
    private static String saveQCloudFile(BufferedImage bufferedImage) throws Exception {
        // 构建存储路径
        String fileName = "/pic/faceUrl/" + IDUtil.uuid() + ".png";

        // BufferedImage转换InputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        String url = QCloudHelper.fileUpload(fileName, inputStream);
        url = QCloudHelper.changDomain(url);

        return url;
    }

    public static void main(String args[]) {

        // 准备成员头像数据
        // "赵思杰","吉华祥","高辉","崔鑫辉","查江龙","仝光华","Wendy","魏俊俊","杨鑫","王一力","梁世民","鑫辉","华祥","张帅","小鑫"
        // 1,1,1,1,1,1,2,2,1,1,1,1,1,1,1
        /*String [] nameArr = new String[] {"Wendy", "范冰冰", "李晨", "john"};
        List<String> names = Arrays.asList(nameArr);

        Integer [] sexArr = new Integer[] {1,2,1,2};
        List<Integer> sexs = Arrays.asList(sexArr);

        try {
            int i = 0;
            for (String name : names) {
                String url = createMemberImage(name, sexs.get(i));
                System.out.println(url);
                ++i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // 准备团队头像数据
        String [] nameArr = new String[] {"Wendy Jams", "范冰冰", "李晨", "john"};
        List<String> names = Arrays.asList(nameArr);

        Integer [] sexArr = new Integer[] {1,2,1,2};
        List<Integer> sexs = Arrays.asList(sexArr);

        try {
            String url = createGroupOrTeamImage(names, sexs);
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
