package com.miiikr.taixian.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.HashMap

object QRUtils {

    fun generateBitmapNoFrame(content: String, width: Int, height: Int): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        val hints = HashMap<EncodeHintType, Any>()
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        hints[EncodeHintType.MARGIN] = 5
        try {
            val encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints)
            val w = encode.width
            val h = encode.height
            val rec = encode.enclosingRectangle
            val pixels = IntArray(width * height)
            for (i in 0 until height) {
                for (j in 0 until width) {
                    var color = Color.WHITE
                    if (encode.get(j, i)) {
                        color = Color.BLACK
                    }
                    // 设置白边的颜色
                    if (i < rec[0] || j < rec[1] || i > rec[0] + rec[2] || j > rec[1] + rec[3]) {
                        color = 0xFFFFFF
                    }
                    pixels[i + j * w] = color
                }
            }
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }


    fun getViewShot(view: View): Bitmap {

        /**
         * View组件显示的内容可以通过cache机制保存为bitmap
         * 我们要获取它的cache先要通过setDrawingCacheEnable方法把cache开启，
         * 然后再调用getDrawingCache方法就可 以获得view的cache图片了
         * 。buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，
         * 若果 cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         * 若果要更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         */
        //        view.setDrawingCacheEnabled(true);
        //        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //设置绘制缓存背景颜色
        //        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        val cachebmp = loadBitmapFromView(view)
//        view.destroyDrawingCache()
        return cachebmp
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val w = v.width
        val h = v.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明  */
        //        v.layout(0, 0, w, h);
        v.draw(c)

        return bmp
    }



}