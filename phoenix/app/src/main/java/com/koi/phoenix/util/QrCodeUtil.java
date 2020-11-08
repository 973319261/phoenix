package com.koi.phoenix.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * 生成带有logo的二维码
 */
public class QrCodeUtil{
       /**
     * 黑点颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 正方形二维码宽度
     */
    private static final int CODE_WIDTH = 180;
    /**
     * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
     */
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 4;
    /**
     *LOGO宽度值,最小不能小于二维码10%宽度值,小于影响Logo与二维码的整体搭配
     */
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;
    /**
     * 生成的二维码图片存储的URI
     */
    private Uri imageFileUri;


    /**
     * 生成带LOGO的二维码
     * @param content 连接
     * @param codeSize 二维码大小
     * @param logoBitmap logo
     * @return
     * @throws WriterException
     */
    public static Bitmap createCode(String content,int codeSize, Bitmap logoBitmap)
            throws WriterException {
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();
        int logoHaleWidth = logoWidth >= CODE_WIDTH ? codeSize/10
                : codeSize/4;
        int logoHaleHeight = logoHeight >= CODE_WIDTH ? codeSize/10
                : codeSize/4;
        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();

        float sx = (float) logoHaleWidth / logoWidth;
        float sy = (float) logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);
        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别,H为最高
        hints.put(EncodeHintType.MAX_SIZE, LOGO_WIDTH_MAX);// 设置图片的最大值
        hints.put(EncodeHintType.MIN_SIZE, LOGO_WIDTH_MIN);// 设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 0);//设置白色边距值
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, codeSize, codeSize, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            /*
                 * 取值范围,可以画图理解下
                 * halfW + newLogoWidth / 2 - (halfW - newLogoWidth / 2) = newLogoWidth
                 * halfH + newLogoHeight / 2 - (halfH - newLogoHeight) = newLogoHeight
                 */
                if (x > halfW - newLogoWidth / 2&& x < halfW + newLogoWidth / 2
                        && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
                    /*
                     *  记录图片每个像素信息
                     *  halfW - newLogoWidth / 2 < x < halfW + newLogoWidth / 2
                     *  --> 0 < x - halfW + newLogoWidth / 2 < newLogoWidth
                     *   halfH - newLogoHeight / 2  < y < halfH + newLogoHeight / 2
                     *   -->0 < y - halfH + newLogoHeight / 2 < newLogoHeight
                     *   刚好取值newLogoBitmap。getPixel(0-newLogoWidth,0-newLogoHeight);
                     */
                    pixels[y * width + x] = newLogoBitmap.getPixel(
                            x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                } else {
                    pixels[y * width + x] = matrix.get(x, y) ? BLACK: WHITE;// 设置信息
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}