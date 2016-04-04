package com.gamebuddy.bigshow.view.vandor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DinPro字体
 * Created by qq on 15/9/16.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context) {
        super(context);
    }

    //DinPro格式枚举
    public enum MyTypeFace {
        ArchitectsDaughter,//默认
        Light,//细
        Medium,//中度
        BOLD//加粗
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //this below cost time
        setTypeFace(MyTypeFace.ArchitectsDaughter);
        setAutoLinkMask(Linkify.WEB_URLS);
        setAutoLinkTextColor(Color.argb(255, 0, 157, 245));
    }

    /***
     * 设置作为超链接可点击使用
     */
    public void setMovementMethod() {
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /***
     * 设置链接的颜色
     *
     * @param color
     */
    public void setAutoLinkTextColor(int color) {
        setLinkTextColor(color);
    }

    private String[] types = new String[]{"font/ArchitectsDaughter.ttf", "font/DINPro-Regular.otf", "font/ArchitectsDaughter.ttf", "font/ArchitectsDaughter.ttf"};

    /***
     * 对于多种字体情况.
     * The initial of typeface really really cost time when there are lots of textview.
     * <p>
     * So I do this to pre-save the typeface.
     */
    private static Typeface face0, face1, face2, face3;
    private static Typeface[] faces = {face0, face1, face2, face3};

    /***
     * 设置自定义字体格式
     *
     * @param typeFace 格式类型
     */
    public void setTypeFace(MyTypeFace typeFace) {
        if (faces[typeFace.ordinal()] == null)
            faces[typeFace.ordinal()] = Typeface.createFromAsset(getContext().getAssets(), types[typeFace.ordinal()]);
        setTypeface(faces[typeFace.ordinal()]);
    }

    private int dinProTextSize = 0;

    /***
     * 设置只有DinPro字体时所用的字体大小
     *
     * @param size
     */
    public void setDinProTextSize(int size) {
        dinProTextSize = size;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        checkDinProText();
    }

    /****
     * 检车是否为DinPro字体
     */
    private void checkDinProText() {
        String content = getText().toString().trim();
        if (!"".equals(content) && dinProTextSize > 0) {
            boolean isNumber = isNumber(content);
            boolean isLetter = content.matches(".*\\p{Alpha}.*");
            boolean isCharacter = checkCharacter(content);
            if (isNumber || isLetter && !isCharacter) {
                setTypeFace(MyTypeFace.ArchitectsDaughter);
                setTextSize(dinProTextSize);
            }
        }
    }

    /***
     * 是否为数字
     *
     * @param content
     * @return
     */
    public static boolean isNumber(String content) {
        for (int i = 0; i < content.length(); i++) { //循环遍历字符串
            if (Character.isDigit(content.charAt(i))) { //用char包装类中的判断数字的方法判断每一个字符 isNumber=true;
                return true;
            }
        }
        return false;
    }

    /***
     * 检测是否为字符串
     *
     * @param sequence
     * @return
     */
    public static boolean checkCharacter(String sequence) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        boolean result;
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        result = matcher.find();
        return result;
    }


}