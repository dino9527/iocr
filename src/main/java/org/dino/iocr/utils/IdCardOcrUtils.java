package org.dino.iocr.utils;

import com.benjaminwan.ocrlibrary.TextBlock;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证 OCR 工具类
 *
 * @author Ethan Chu
 * @since 2025/07/28 10:52
 */
public class IdCardOcrUtils {

    private IdCardOcrUtils() {
    }

    /**
     * 获取身份证姓名
     *
     * @param textBlocks 识别的结果集合
     * @return 姓名
     */
    public static String predictName(ArrayList<TextBlock> textBlocks) {
        String name = "";
        for (TextBlock textBlock: textBlocks) {
            String str = textBlock.getText().trim().replace(" ", "");
            if (str.contains("姓名") || str.contains("名")) {
                String pattern = ".*名[\\u4e00-\\u9fa5]{1,4}";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(str);
                if (m.matches()) {
                    name = str.substring(str.indexOf("名") + 1);
                }
            }
        }
        return name;
    }

    /**
     * 获取民族
     *
     * @param textBlocks 识别的结果集合
     * @return 民族信息
     */
    public static String national(ArrayList<TextBlock> textBlocks) {
        String nation = "";
        for (TextBlock textBlock: textBlocks) {
            String str = textBlock.getText();
            String pattern = ".*民族[\u4e00-\u9fa5]{1,4}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if (m.matches()) {
                nation = str.substring(str.indexOf("族") + 1);
            }
        }
        return nation;
    }

    /**
     * 获取身份证地址
     *
     * @param textBlocks 识别的结果集合
     * @return 身份证地址信息
     */
    public static String address(ArrayList<TextBlock> textBlocks) {
        String address = "";
        StringBuilder addressJoin = getAddressJoin(textBlocks);
        String s = addressJoin.toString();
        if (s.contains("省") || s.contains("县") || s.contains("住址") || s.contains("址") || s.contains("公民身份")) {
            // 通过这里的截取可以知道，即使是名字中有上述的那些字段，也不要紧，因为这个ocr识别是一行一行来的，所以名字的会在地址这两个字
            // 前面，除非是名字中也有地址的”地“或者”址“字，这个还可以使用lastIndexOf()来从后往左找，也可以在一定程度上避免这个。
            // 具体看后面的截图，就知道了
            address = s.substring(s.indexOf("址") + 1, s.indexOf("公民身份"));
        } else {
            address = s;
        }
        return address;
    }

    private static StringBuilder getAddressJoin(ArrayList<TextBlock> textBlocks) {
        StringBuilder addressJoin = new StringBuilder();
        for (TextBlock textBlock: textBlocks) {
            String str = textBlock.getText().trim().replace(" ", "");
            // 看身份证地址那一栏，具体可以看一下自己的身份证，几乎都包含这些字，具体可以自己debugger看一下就知道了
            // 具体可以自己debugger看一下就知道了
            if (str.contains("住址") || str.contains("址") || str.contains("省") || str.contains("市")
                    || str.contains("县") || str.contains("街") || str.contains("乡") || str.contains("村")
                    || str.contains("镇") || str.contains("区") || str.contains("城") || str.contains("组")
                    || str.contains("号") || str.contains("幢") || str.contains("室")
            ) {
                addressJoin.append(str);
            }
        }
        return addressJoin;
    }

    /**
     * 获取身份证号
     *
     * @param textBlocks ocr识别的内容列表
     * @return 身份证号码
     */
    public static String cardNumber(ArrayList<TextBlock> textBlocks) {
        String cardNumber = "";
        for (TextBlock textBlock: textBlocks) {
            String str = textBlock.getText().trim().replace(" ", "");
            // 之里注意了，这里的双斜杆，是因为这里是java，\会转义，所以使用双鞋干\\，去掉试一试就知道了
            String pattern = "\\d{17}[\\d|x|X]|\\d{15}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);
            if (m.matches()) {
                cardNumber = str;
            }
        }
        return cardNumber;
    }

    /**
     * 二代身份证18位
     * 这里之所以这样做，是因为如果直接从里面截取，也可以，但是从打印的内容中，有时候
     * 性别性别男，是在同一行，有些照片是
     * 性
     * 别
     * 男
     * 等，如果单纯是使用字符串的str.contains("男") ==》 然后返回性别男，
     * str.contains("女") ==> 然后返回性别女
     * 这个万姓名中有男字，地址中有男字，等。而这个人的性别是女。这是可能会按照识别顺序
     * 排序之后，识别的是地址的男字，所以这里直接从身份证倒数第二位的奇偶性判断男女更加准确一点
     * 从身份证号码中提取性别
     *
     * @param cardNumber 身份证号码，二代身份证18位
     * @return 性别
     */
    public static String sex(String cardNumber) {
        String sex = "";
        // 取倒身份证倒数第二位的数字的奇偶性判断性别，二代身份证18位
        String substring = cardNumber.substring(cardNumber.length() - 2, cardNumber.length() - 1);
        int parseInt = Integer.parseInt(substring);
        if (parseInt % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    /**
     * 从身份证中获取出生信息
     *
     * @param cardNumber 二代身份证，18位
     * @return 出生日期
     */
    public static String birthday(String cardNumber) {
        String birthday = "";
        String date = cardNumber.substring(6, 14);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        birthday = year + "年" + month + "月" + day + "日";
        return birthday;
    }




}
