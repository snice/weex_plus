package cn.avcon.zxing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by zhuzhe on 2016/9/23.
 */

public class CustomViewFinderView extends ViewFinderView {
    public static final String TRADE_MARK_TEXT = "将二维码放入框内,即可自动扫描";
    public static final int TRADE_MARK_TEXT_SIZE_SP = 13;
    public final Paint PAINT = new Paint();

    public CustomViewFinderView(Context context) {
        super(context);
        init();
    }

    public CustomViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        PAINT.setColor(Color.WHITE);
        PAINT.setAntiAlias(true);
        float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
        PAINT.setTextSize(textPixelSize);
        setSquareViewFinder(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTradeMark(canvas);
    }

    private void drawTradeMark(Canvas canvas) {
        Rect framingRect = getFramingRect();
        float tradeMarkTop;
        float tradeMarkLeft;
        float txtWidth = PAINT.measureText(TRADE_MARK_TEXT);
        if (framingRect != null) {
            tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 30;
        } else {
            tradeMarkTop = 30;
        }
        tradeMarkLeft = (canvas.getWidth() - txtWidth) / 2 - 10;
        canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
    }
}
