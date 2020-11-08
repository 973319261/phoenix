package com.koi.phoenix.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类（合成，转换，旋转、保存）
 */
public class ImageUtil {
   /**
    * 图片合成，利用canvas合成图片
    *
    * @return
    */
   //首先传入两张图片
   public static Bitmap compoundBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
      //以其中一张图片的大小作为画布的大小，或者也可以自己自定义
      Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap
              .getHeight(), firstBitmap.getConfig());
      //生成画布
      Canvas canvas = new Canvas(bitmap);
      //因为我传入的secondBitmap的大小是不固定的，所以我要将传来的secondBitmap调整到和画布一样的大小
      Matrix m = new Matrix();
      //确定secondBitmap大小比例
      m.setScale(1, 1);
      Paint paint = new Paint();
      canvas.drawBitmap(firstBitmap, 0, 0, null);
      canvas.drawBitmap(secondBitmap, m, paint);
      return bitmap;
   }

   /**
    * Drawable转换成一个Bitmap
    * @param context 上下文
    * @param drawable drawable对象
    * @return
    */
   public static Bitmap drawableToBitmap(Context context, int drawable) {
      Resources resources = context.getResources();
      InputStream is = resources.openRawResource(drawable);
      BitmapDrawable bmpDraw = new BitmapDrawable(is);
      Bitmap bmp = bmpDraw.getBitmap();
      return bmp;
   }

   /**
    * 旋转图片
    * @param context 上下文
    * @param drawable 原图片 drawable对象
    * @param orientationDegree //角度
    * @return
    */
   public static Bitmap rotateBitmap(Context context, int drawable, int orientationDegree) {
      Bitmap bm=ImageUtil.drawableToBitmap(context,drawable);
      //方便判断，角度都转换为正值
      int degree = orientationDegree;
      if( degree < 0){
         degree = 360 + orientationDegree;
      }

      int srcW = bm.getWidth();
      int srcH = bm.getHeight();

      Matrix m = new Matrix();
      m.setRotate(degree, (float) srcW / 2, (float) srcH / 2);
      float targetX, targetY;

      int destH = srcH;
      int destW = srcW;

      //根据角度计算偏移量，原理不明
      if (degree == 90 ) {
         targetX = srcH;
         targetY = 0;
         destH = srcW;
         destW = srcH;
      } else if( degree == 270){
         targetX = 0;
         targetY = srcW;
         destH = srcW;
         destW = srcH;
      }else if(degree == 180){
         targetX = srcW;
         targetY = srcH;
      }else {
         return bm;
      }

      final float[] values = new float[9];
      m.getValues(values);

      float x1 = values[Matrix.MTRANS_X];
      float y1 = values[Matrix.MTRANS_Y];

      m.postTranslate(targetX - x1, targetY - y1);

      //注意destW 与 destH 不同角度会有不同
      Bitmap bm1 = Bitmap.createBitmap(destW, destH, Bitmap.Config.ARGB_8888);
      Paint paint = new Paint();
      Canvas canvas = new Canvas(bm1);
      canvas.drawBitmap(bm, m, paint);
      return bm1;
   }


   /**
    * 把图片保存到设备
    * @param context
    * @param bmp
    * @param isPhoto
    * @return
    */
   public static File saveImageToGallery(Context context, Bitmap bmp, boolean isPhoto) {
      // 首先保存图片
      File appDir = new File(Environment.getExternalStorageDirectory(), "Card");
      if (!appDir.exists()) {
         appDir.mkdir();
      }
      String fileName = System.currentTimeMillis() + ".jpg";
      File file = new File(appDir, fileName);
      try {
         FileOutputStream fos = new FileOutputStream(file);
         bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
         fos.flush();
         fos.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      // 最后通知图库更新
      if (isPhoto){
         context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
      }
      return file;
   }
}