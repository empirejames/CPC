package com.james.cpc;

/**
 * Created by 101716 on 2017/11/21.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.james.cpc.qrcode.Contents;
import com.james.cpc.qrcode.QRCodeEncoder;


public class QRCodeGenActivity extends AppCompatActivity {
    ImageView imgView;
    String TAG = QRCodeGenActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Bundle bundle = getIntent().getExtras();
        String packageContent = bundle.getString("QRdata");
        String QRCodeContent = packageContent;
        int QRCodeWidth = 100;
        int QRCodeHeight = 100;
        try {
            //Bitmap bitmap = Create2DCode(QRCodeContent, QRCodeWidth,QRCodeHeight );
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(QRCodeContent,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    getScreenSize());
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            imgView = (ImageView) findViewById(R.id.imageView);
            imgView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public int getScreenSize(){
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        //Log.e(TAG,smallerDimension + " : " +  width + " : "+height);
        smallerDimension = smallerDimension * 3/4;
        return smallerDimension;
    }

    public Bitmap Create2DCode(String str, int QRCodeWidth, int QRCodeHeight) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(matrix.get(x, y)){
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}