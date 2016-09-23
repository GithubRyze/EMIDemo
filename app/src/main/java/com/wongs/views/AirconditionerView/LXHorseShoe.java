package com.wongs.views.AirconditionerView;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.wongs.emidemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class LXHorseShoe extends View
        implements LXHorseShoeModel.LXOnHorseShoeChangeListener
{
    private static final int ANGLE_OFFSET = -90;
    private static final int ANGLE_TO_BOTTOM = 90;
    private static final int ARC_ROTATION = 180;
    private static final int ARC_TOP = 450;
    private static final int ARC_WIDTH = 35;
    private static final int BACK_TIMER_DURATION = 5000;
    private static final boolean CLOCKWISE = true;
    private static final int DEFAULT_IGNORE_TOUCH_RADIUS = 200;
    private static final int DEGREE_LABEL_ADJUSTED = 13;
    private static final int DEGREE_LABEL_PAD = 88;
    private static final int DEGREE_LABEL_TEXT_SIZE = 26;
    private static final double DRAW_ARC_75_FIX = 0.5D;
    public static final int END_SP = 90;
    private static final int EXTRA_KNOB_RADIUS = 0;
    private static final int FEELS_GRADIENT_END_X = 900;
    private static final int FEELS_GRADIENT_START_X = 300;
    private static final int FULL_CIRCLE = 360;
    private static final int INDOOR_PAINT_WIDTH = 8;
    private static final int KNOB_EDGE_MAX = 110;
    private static final int KNOB_EDGE_MIN = 0;
    private static final int LONG_TICK_END_PAD = 55;
    private static final int LONG_TICK_START_PAD = 10;
    private static final int PROGRESS_MAX = 30;
    private static final int PROGRESS_WIDTH = 37;
    private static final int RETURN_TO_VIEW_PADDING = 100;
    private static final int SHORT_TICK_END_PAD = 40;
    private static final int SHORT_TICK_START_PAD = 20;
    private static final int SP_GRADIENT_END_Y = 650;
    private static final int SP_GRADIENT_START_Y = 40;
    private static final int START_ANGLE = 45;
    public static final int START_SP = 60;
    private static final int SWEEP_ANGLE = 270;
    private static final String TAG = LXHorseShoe.class.getSimpleName();
    private static final boolean TOUCH_INSIDE=false;
    private int obArcRadius;
    private RectF obArcRect = new RectF();
    private Timer obBackToViewTimer;
    private Paint obBackgroundArcPaint;
    private Paint obBackgroundAwayPaint;
    private Paint obBackgroundEditArcPaint;
    private Paint obBackgroundProgramArcPaint;
    private Bitmap obBlueSingleSetPointActiveThumb;
    private Bitmap obBlueSingleSetPointInActiveThumb;
    private Bitmap obCoolActiveThumb;
    private Paint obCoolArcPaint;
    private Paint obCoolAwayPaint;
    private Bitmap obCoolAwayThumb;
    private Bitmap obCoolInActiveThumb;
    private Point obCoolKnobPos;
    private Bitmap obCoolViewThumb;
    private Bitmap obHeatActiveThumb;
    private Paint obHeatArcPaint;
    private Paint obHeatAwayPaint;
    private Bitmap obHeatAwayThumb;
    private Bitmap obHeatInActiveThumb;
    private Point obHeatKnobPos;
    private Bitmap obHeatViewThumb;
    private LXHorseShoeModel obHorseShoeModel;
    private Paint obIndoorPaint;
    private int obProgress;
    private Bitmap obRedSingleSetPointActiveThumb;
    private Bitmap obRedSingleSetPointInActiveThumb;
    private Point obSingleSetPointKnobPos;
    private Paint obSingleSetPointPaint;
    private double obTouchAngle;
    private float obTouchIgnoreRadius;
    private int obTranslateX;
    private int obTranslateY;
    DisplayMetrics dm;
    boolean blnDrawSmall=false;

    public LXHorseShoe(Context paramContext)
    {
        super(paramContext);
        setDefaults();
    }

    public LXHorseShoe(Context paramContext, AttributeSet paramAttributeSet)
    {

        super(paramContext, paramAttributeSet);
        setDefaults();
    }

    public LXHorseShoe(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        setDefaults();
    }

    private Paint createArcPaint(int paramInt)
    {
        Paint localPaint = new Paint();
        localPaint.setColor(paramInt);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Style.STROKE);
        if (blnDrawSmall)
        {
            localPaint.setStrokeWidth(18.0F);

        }
        else
        {
            localPaint.setStrokeWidth(35.0F);
        }
        localPaint.setStrokeCap(Cap.ROUND);
        return localPaint;
    }

    private Paint createSPPaint(int paramInt1, int paramInt2)
    {
        Point localPoint1 = new Point(0, 40);
        Point localPoint2 = new Point(0, 650);
        Paint localPaint = new Paint();
        localPaint.setColor(0xFF000000);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Style.STROKE);
        if (blnDrawSmall)
        {
            localPaint.setStrokeWidth(18.0F);

        }
        else
        {
            localPaint.setStrokeWidth(37.0F);
        }
        localPaint.setStrokeCap(Cap.ROUND);
        localPaint.setShader(new LinearGradient(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y, paramInt1, paramInt2, TileMode.CLAMP));
        return localPaint;
    }

    private void drawAwayMode(Canvas canvas)
    {

        int iEndTemp=90;
        int iStartTemp=60;
        float iTotalTemp=30.0f;

        if(obHorseShoeModel.blnTempC)
        {
            iEndTemp=32;
            iStartTemp=16;
            iTotalTemp=16;
        }

        int i;
        int j;


        float f;
        float f1;
        if(obHorseShoeModel.getSystemMode() == LXSystemMode.OFF)
        {
            canvas.drawArc(obArcRect, 135F, 270F, false, obBackgroundProgramArcPaint);

        }
        else
        {
            canvas.drawArc(obArcRect, 135F, 270F, false, obBackgroundAwayPaint);
        }
        f1 = ((float)(iEndTemp - obHorseShoeModel.getCoolSP()) / iTotalTemp) * 270F;
        f = (float)((double)(405F - f1) + 0.5D);
        if(obHorseShoeModel.getSystemMode() == LXSystemMode.COOL || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            canvas.drawArc(obArcRect, f, f1, false, obCoolAwayPaint);
        }
        f1 = ((float)(obHorseShoeModel.getHeatSP() - iStartTemp) / iTotalTemp) * 270F;
        if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            canvas.drawArc(obArcRect, 135F, f1, false, obHeatAwayPaint);
        }
        j = (int)f + 180;
        i = (int)((double)obArcRadius * Math.cos(Math.toRadians(j)));
        j = (int)((double)obArcRadius * Math.sin(Math.toRadians(j)));

        if(obHorseShoeModel.getSystemMode() == LXSystemMode.COOL || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            if (obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_AWAY)
            {
                obCoolKnobPos = new Point(obTranslateX - i - obCoolAwayThumb.getScaledWidth(canvas) / 2, obTranslateY - j - obCoolAwayThumb.getScaledHeight(canvas) / 2);
                canvas.drawBitmap(obCoolAwayThumb, obCoolKnobPos.x, obCoolKnobPos.y, new Paint());
            }
            else
            {
                obCoolKnobPos = new Point(obTranslateX - i - obCoolViewThumb.getScaledWidth(canvas) / 2, obTranslateY - j - obCoolViewThumb.getScaledHeight(canvas) / 2);
                canvas.drawBitmap(obCoolViewThumb, obCoolKnobPos.x, obCoolKnobPos.y, new Paint());
            }
        }
        j = (int)(45F + f1 + 180F + 90F);
        i = (int)((double)obArcRadius * Math.cos(Math.toRadians(j)));
        j = (int)((double)obArcRadius * Math.sin(Math.toRadians(j)));
        if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            if(obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_AWAY)
            {
                return;
            }
            obHeatKnobPos = new Point(obTranslateX - i - obHeatAwayThumb.getScaledWidth(canvas) / 2, obTranslateY - j - obHeatAwayThumb.getScaledHeight(canvas) / 2);
            canvas.drawBitmap(obHeatAwayThumb, obHeatKnobPos.x, obHeatKnobPos.y, new Paint());
        }
        return;

       // obHeatKnobPos = new Point(obTranslateX - i - obHeatViewThumb.getScaledWidth(canvas) / 2, obTranslateY - j - obHeatViewThumb.getScaledHeight(canvas) / 2);
        //canvas.drawBitmap(obHeatViewThumb, obHeatKnobPos.x, obHeatKnobPos.y, new Paint());




//
//        float f3;
//        int j;
//        int k;
//        if (this.obHorseShoeModel.getSystemMode() == LXSystemMode.OFF)
//        {
//            paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundProgramArcPaint);
//            float f1 = 270.0F * ((90 - this.obHorseShoeModel.getCoolSP()) / 30.0F);
//            float f2 = (float)(0.5D + (405.0F - f1));
//            if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.COOL) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
//                paramCanvas.drawArc(this.obArcRect, f2, f1, false, this.obCoolAwayPaint);
//            f3 = 270.0F * ((-60 + this.obHorseShoeModel.getHeatSP()) / 30.0F);
//            if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
//                paramCanvas.drawArc(this.obArcRect, 135.0F, f3, false, this.obHeatAwayPaint);
//            int i = 180 + (int)f2;
//            j = (int)(this.obArcRadius * Math.cos(Math.toRadians(i)));
//            k = (int)(this.obArcRadius * Math.sin(Math.toRadians(i)));
//            if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.COOL) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
//            {
//                if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_AWAY)
//                    break label514;
//                this.obCoolKnobPos = new Point(this.obTranslateX - j - this.obCoolAwayThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - this.obCoolAwayThumb.getScaledHeight(paramCanvas) / 2);
//                paramCanvas.drawBitmap(this.obCoolAwayThumb, this.obCoolKnobPos.x, this.obCoolKnobPos.y, new Paint());
//            }
//        }
//        int n;
//        int i1;
//        while (true)
//        {
//            int m = (int)(90.0F + (180.0F + (45.0F + f3)));
//            n = (int)(this.obArcRadius * Math.cos(Math.toRadians(m)));
//            i1 = (int)(this.obArcRadius * Math.sin(Math.toRadians(m)));
//            if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
//            {
//                if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_AWAY)
//                    break label595;
//                this.obHeatKnobPos = new Point(this.obTranslateX - n - this.obHeatAwayThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - i1 - this.obHeatAwayThumb.getScaledHeight(paramCanvas) / 2);
//                paramCanvas.drawBitmap(this.obHeatAwayThumb, this.obHeatKnobPos.x, this.obHeatKnobPos.y, new Paint());
//            }
//            return;
//            paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundAwayPaint);
//            break;
//            label514: this.obCoolKnobPos = new Point(this.obTranslateX - j - this.obCoolViewThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - this.obCoolViewThumb.getScaledHeight(paramCanvas) / 2);
//            paramCanvas.drawBitmap(this.obCoolViewThumb, this.obCoolKnobPos.x, this.obCoolKnobPos.y, new Paint());
//        }
//        label595: this.obHeatKnobPos = new Point(this.obTranslateX - n - this.obHeatViewThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - i1 - this.obHeatViewThumb.getScaledHeight(paramCanvas) / 2);
//        paramCanvas.drawBitmap(this.obHeatViewThumb, this.obHeatKnobPos.x, this.obHeatKnobPos.y, new Paint());
    }

    private void drawConnectedMode(Canvas paramCanvas)
    {
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundProgramArcPaint);
    }

    private void drawCoolKnob(Canvas paramCanvas, float paramFloat, Bitmap paramBitmap)
    {
        int i = 180 + (int)paramFloat;
        int j = (int)(this.obArcRadius * Math.cos(Math.toRadians(i)));
        int k = (int)(this.obArcRadius * Math.sin(Math.toRadians(i)));
        Bitmap localBitmap = rotateBitmap(paramBitmap, i - 450);
        this.obCoolKnobPos = new Point(this.obTranslateX - j - localBitmap.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - localBitmap.getScaledHeight(paramCanvas) / 2);
        paramCanvas.drawBitmap(localBitmap, this.obCoolKnobPos.x, this.obCoolKnobPos.y, new Paint());
    }

    private void drawCoolMode(Canvas paramCanvas)
    {
        Log.e(TAG, "drawCoolMode.");

        int iStartTemp=60;
        float iEndTemp=90;
        float iTotalTemp=30;

        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp = 16;
            iEndTemp =32;
            iTotalTemp=16;
        }

        this.obHorseShoeModel.setCoolSP(iStartTemp + this.obProgress, false);
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundEditArcPaint);
        float f1 = 270.0F * ((iEndTemp - this.obHorseShoeModel.getCoolSP()) / iTotalTemp);
        float f2 = (float)(0.5D + (405.0F - f1));
        paramCanvas.drawArc(this.obArcRect, f2, f1, false, this.obCoolArcPaint);
        float f3 = 270.0F * ((-iStartTemp + this.obHorseShoeModel.getHeatSP()) / iTotalTemp);
        drawTicks(paramCanvas);
        drawDegreeLabels(paramCanvas);
        drawIndoorLabel(paramCanvas);
        if (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            drawSPLabel(getResources().getColor(R.color.red_text), this.obHorseShoeModel.getHeatSP(), paramCanvas);
        }
        drawSPLabel(getResources().getColor(R.color.blue_text), this.obHorseShoeModel.getCoolSP(), paramCanvas);
        drawIndoorTick(paramCanvas);
        this.obCoolKnobPos = drawSPKnob(paramCanvas, getCoolKnobAngle(f2), this.obCoolActiveThumb);
        if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
        {
            this.obHeatKnobPos = drawSPKnob(paramCanvas, getHeatKnobAngle(f3), this.obHeatInActiveThumb);
        }
    }

    private void drawDegreeLabels(Canvas paramCanvas)
    {
        Paint localPaint = new Paint();
        localPaint.setColor(getResources().getColor(R.color.background_arc_off_white));
        localPaint.setAntiAlias(true);

        int iGap=13;
        int iGapX=13;
        int iR=105;
        if(blnDrawSmall)
        {
            localPaint.setTextSize(14.0f);
            iGap = 7;
            iGapX=9;
             iR=48;
        }
        else
        {
            localPaint.setTextSize(26.0F);

        }

        int iStartTemp=60;
        int iTotalTemp=30;
        int iMods=5;
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp=16;
            iTotalTemp=16;
            iMods=4;
        }

        int i = 0;
        for (i = 0;i <= iTotalTemp; i += iMods)
        {
            int j = i + iStartTemp;
            LXSystemMode localLXSystemMode1;
            int k=0;
            if (j != this.obHorseShoeModel.getIndoorSP())
            {
                localLXSystemMode1 = this.obHorseShoeModel.getSystemMode();
                if ((localLXSystemMode1 != LXSystemMode.HEAT_AND_COOL) || (j == this.obHorseShoeModel.getHeatSP()) || (j == this.obHorseShoeModel.getCoolSP()))
                {

                    if ((localLXSystemMode1 == LXSystemMode.COOL) && (j != this.obHorseShoeModel.getCoolSP()))
                    {
                        k = 1;
                    }
                    else
                    {

                        k = 0;
                        if (localLXSystemMode1 == LXSystemMode.HEAT)
                        {

                            k = 0;
                            if (j != this.obHorseShoeModel.getHeatSP())
                            {
                                k = 1;
                            }
                        }
                    }

                }
                k = 1;
            }

                if ((this.obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_SINGLE_SET_POINT_EDIT_MODE) && (j == this.obHorseShoeModel.getSingleSetPointSP()))
                {
                    k = 0;
                }



                if (k == 1)
                {
                    Point localPoint = tickPointForValue(i, iR);
                    paramCanvas.drawText(Integer.toString(j) + "°",  (this.obTranslateX - localPoint.x)-iGapX, iGap + (this.obTranslateY - localPoint.y), localPaint);
                }




        }
    }

    private void drawDisconnectedMode(Canvas paramCanvas)
    {
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundProgramArcPaint);
    }

    private void drawFeelsLabel(Canvas canvas)
    {

        if(obHorseShoeModel.getSingleSetPointSP() == obHorseShoeModel.getIndoorSP())
            return;
        Paint paint = new Paint();
        Point point;
        if(obHorseShoeModel.getSingleSetPointSP() < 75)
            paint.setColor(getResources().getColor(R.color.blue_text));
        else
            paint.setColor(getResources().getColor(R.color.red_gradient_end));
        paint.setAntiAlias(true);
        int iGap=0;
        if(blnDrawSmall)
        {
            paint.setTextSize(14F);
            iGap = 30;
        }
        else
        {
            paint.setTextSize(26F);
        }
        point = tickPointForValue(obHorseShoeModel.getSingleSetPointSP() - 60+iGap, 105-iGap);
        canvas.drawText((new StringBuilder()).append(Integer.toString(obHorseShoeModel.getSingleSetPointSP())).append("\260").toString(), obTranslateX - point.x - 13, (obTranslateY - point.y) + 13, paint);


//        if (this.obHorseShoeModel.getSingleSetPointSP() == this.obHorseShoeModel.getIndoorSP())
//            return;
//        Paint localPaint = new Paint();
//        if (this.obHorseShoeModel.getSingleSetPointSP() < 75)
//            localPaint.setColor(getResources().getColor(R.color.blue_text));
//        while (true)
//        {
//            localPaint.setAntiAlias(true);
//            localPaint.setTextSize(26.0F);
//            Point localPoint = tickPointForValue(-60 + this.obHorseShoeModel.getSingleSetPointSP(), 105);
//            paramCanvas.drawText(Integer.toString(this.obHorseShoeModel.getSingleSetPointSP()) + "°", -13 + (this.obTranslateX - localPoint.x), 13 + (this.obTranslateY - localPoint.y), localPaint);
//            return;
//            localPaint.setColor(getResources().getColor(R.color.red_gradient_end));
//        }
    }

    private void drawHeatKnob(Canvas paramCanvas, float paramFloat, Bitmap paramBitmap)
    {
        int i = (int)(90.0F + (180.0F + (45.0F + paramFloat)));
        int j = (int)(this.obArcRadius * Math.cos(Math.toRadians(i)));
        int k = (int)(this.obArcRadius * Math.sin(Math.toRadians(i)));
        Bitmap localBitmap = rotateBitmap(paramBitmap, i - 450);
        this.obHeatKnobPos = new Point(this.obTranslateX - j - localBitmap.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - localBitmap.getScaledHeight(paramCanvas) / 2);
        paramCanvas.drawBitmap(localBitmap, this.obHeatKnobPos.x, this.obHeatKnobPos.y, new Paint());
    }

    private void drawHeatMode(Canvas paramCanvas)
    {
        Log.e(TAG, "drawHeatMode.");
        int iStartTemp=60;
        float iEndTemp=90;
        float iTotalTemp=30;
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp = 16;
            iEndTemp=32;
            iTotalTemp=16;
        }

        this.obHorseShoeModel.setHeatSP(iStartTemp + this.obProgress, false);
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundEditArcPaint);
        float f1 = (float)(0.5D + (405.0F - 270.0F * ((iEndTemp - this.obHorseShoeModel.getCoolSP()) / iTotalTemp)));
        float f2 = 270.0F * ((-iStartTemp + this.obHorseShoeModel.getHeatSP()) / iTotalTemp);
        paramCanvas.drawArc(this.obArcRect, 135.0F, f2, false, this.obHeatArcPaint);
        drawTicks(paramCanvas);
        drawDegreeLabels(paramCanvas);
        drawIndoorLabel(paramCanvas);
        if (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
        {
            drawSPLabel(getResources().getColor(R.color.blue_text), this.obHorseShoeModel.getCoolSP(), paramCanvas);
        }
        drawSPLabel(getResources().getColor(R.color.red_text), this.obHorseShoeModel.getHeatSP(), paramCanvas);
        drawIndoorTick(paramCanvas);
        if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.COOL) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
        {
            this.obCoolKnobPos = drawSPKnob(paramCanvas, getCoolKnobAngle(f1), this.obCoolInActiveThumb);
        }
        this.obHeatKnobPos = drawSPKnob(paramCanvas, getHeatKnobAngle(f2), this.obHeatActiveThumb);
    }

    private void drawIndoorLabel(Canvas paramCanvas)
    {
        Paint localPaint = new Paint();
        localPaint.setColor(getResources().getColor(R.color.indoor_yellow));
        localPaint.setAntiAlias(true);
        int iGap=13;
        int iGapX=13;
        int iR=105;
        if(blnDrawSmall)
        {
            localPaint.setTextSize(14.0f);
            iR=48;
            iGap=7;
            iGapX=9;
        }
        else
        {
            localPaint.setTextSize(26.0F);

        }

        int iStartTemp=60;
        int iDisTemp=this.obHorseShoeModel.getIndoorSP();
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp = 16;
        }
        else
        {
            iDisTemp= Math.round(iDisTemp * 1.8f + 32.0f);
        }
        Point localPoint = tickPointForValue(iDisTemp-iStartTemp, iR);
        paramCanvas.drawText(Integer.toString(iDisTemp) + "°", (this.obTranslateX - localPoint.x) - iGapX, iGap + (this.obTranslateY - localPoint.y), localPaint);
        Log.e(TAG, "drawIndoorLabel");
    }

    private void drawIndoorTick(Canvas paramCanvas)
    {

        int iIndoorTemp=this.obHorseShoeModel.getIndoorSP();



        int iTickUP=22;
        int iTickDown=-20;

        if(blnDrawSmall)
        {
            iTickUP=11;
            iTickDown=-10;
        }

        int iStartTemp=60;

        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp = 16;
            if(iIndoorTemp>32)
            {
                return;
            }

        }
        else
        {
            iIndoorTemp= Math.round(iIndoorTemp * 1.8f + 32.0f);
            if(iIndoorTemp>90)
            {
                return;
            }
        }

       Point localPoint1 = tickPointForValue(  iIndoorTemp-iStartTemp  ,iTickUP);

        Point localPoint2 = tickPointForValue(iIndoorTemp - iStartTemp, iTickDown);

//        paramCanvas.drawLine(this.obTranslateX , this.obTranslateY, this.obTranslateX - localPoint2.x, this.obTranslateY, this.obIndoorPaint);


        paramCanvas.drawLine(this.obTranslateX - localPoint1.x, this.obTranslateY - localPoint1.y, this.obTranslateX - localPoint2.x, this.obTranslateY - localPoint2.y, this.obIndoorPaint);

        Log.e(TAG, "drawIndoorTick");
    }

    private Point drawSPKnob(Canvas paramCanvas, float paramFloat, Bitmap paramBitmap)
    {
        int i = (int)(this.obArcRadius * Math.cos(Math.toRadians(paramFloat)));
        int j = (int)(this.obArcRadius * Math.sin(Math.toRadians(paramFloat)));
        Bitmap localBitmap = rotateBitmap(paramBitmap, paramFloat - 450.0F);
        Point localPoint = new Point(this.obTranslateX - i - localBitmap.getScaledWidth(paramCanvas) / 2, this.obTranslateY - j - localBitmap.getScaledHeight(paramCanvas) / 2);
        paramCanvas.drawBitmap(localBitmap, localPoint.x, localPoint.y, new Paint());
        return localPoint;
    }

    private void drawSPLabel(int paramInt1, int paramInt2, Canvas paramCanvas)
    {
        if (paramInt2 == this.obHorseShoeModel.getIndoorSP())
            return;
        Paint localPaint = new Paint();
        localPaint.setColor(paramInt1);
        localPaint.setAntiAlias(true);
        int iGap=13;
        int iGapX=13;
        int iR=105;
        if(blnDrawSmall)
        {
            localPaint.setTextSize(14.0f);
            iR=48;
            iGap=7;
            iGapX=9;
        }
        else
        {
            localPaint.setTextSize(26.0F);
        }

        int iStartTemp=60;
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp =16;
        }
        Point localPoint = tickPointForValue(paramInt2 - iStartTemp, iR);
        paramCanvas.drawText(Integer.toString(paramInt2) + "°", (this.obTranslateX - localPoint.x) - iGapX, iGap + (this.obTranslateY - localPoint.y), localPaint);
    }

    private void drawSelectMode(Canvas paramCanvas)
    {
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundProgramArcPaint);
    }

    private void drawSingleSetPointMode(Canvas canvas)
    {


        Bitmap bitmap;
        int i;
        int j;
        int k;
        if(obProgress >= 0 && obProgress <= 30)
            obHorseShoeModel.setSingleSetPointSP(obProgress + 60, false);
        canvas.drawArc(obArcRect, 135F, 270F, false, obSingleSetPointPaint);
        drawTicks(canvas);
        drawDegreeLabels(canvas);
        drawIndoorLabel(canvas);
        drawFeelsLabel(canvas);
        drawIndoorTick(canvas);
        i = (int)(45F + ((float)(obHorseShoeModel.getSingleSetPointSP() - 60) / 30F) * 270F + 180F + 90F);
        j = (int)((double)obArcRadius * Math.cos(Math.toRadians(i)));
        k = (int)((double)obArcRadius * Math.sin(Math.toRadians(i)));
        if(i < 450)
            bitmap = rotateBitmap(obBlueSingleSetPointActiveThumb, i - 450);
        else
            bitmap = rotateBitmap(obRedSingleSetPointActiveThumb, i - 450);
        obSingleSetPointKnobPos = new Point(obTranslateX - j - bitmap.getScaledWidth(canvas) / 2, obTranslateY - k - bitmap.getScaledHeight(canvas) / 2);
        canvas.drawBitmap(bitmap, obSingleSetPointKnobPos.x, obSingleSetPointKnobPos.y, new Paint());

//        int i;
//        int j;
//        int k;
//        if ((this.obProgress < 0) || (this.obProgress > 30))
//        {
//            paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obSingleSetPointPaint);
//            drawTicks(paramCanvas);
//            drawDegreeLabels(paramCanvas);
//            drawIndoorLabel(paramCanvas);
//            drawFeelsLabel(paramCanvas);
//            drawIndoorTick(paramCanvas);
//            i = (int)(90.0F + (180.0F + (45.0F + 270.0F * ((-60 + this.obHorseShoeModel.getSingleSetPointSP()) / 30.0F))));
//            j = (int)(this.obArcRadius * Math.cos(Math.toRadians(i)));
//            k = (int)(this.obArcRadius * Math.sin(Math.toRadians(i)));
//            if (i >= 450)
//                break label235;
//        }
//        label235: for (Bitmap localBitmap = rotateBitmap(this.obBlueSingleSetPointActiveThumb, i - 450); ; localBitmap = rotateBitmap(this.obRedSingleSetPointActiveThumb, i - 450))
//        {
//            this.obSingleSetPointKnobPos = new Point(this.obTranslateX - j - localBitmap.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - localBitmap.getScaledHeight(paramCanvas) / 2);
//            paramCanvas.drawBitmap(localBitmap, this.obSingleSetPointKnobPos.x, this.obSingleSetPointKnobPos.y, new Paint());
//            return;
//            this.obHorseShoeModel.setSingleSetPointSP(60 + this.obProgress, false);
//            break;
//        }
    }

    private void drawTicks(Canvas paramCanvas)
    {


        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.background_arc_off_white));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        int iStart=37;


        int iLGapEnd=72;
        int iSGapEnd=60;
        if(blnDrawSmall)
        {
            paint.setStrokeWidth(1.0F);
            iStart=15;
            iLGapEnd=34;
            iSGapEnd=22;
        }
        else
        {
            paint.setStrokeWidth(2.0F);
        }
        int i = 0;


        int iTotalTicks=31;
        int iMods=5;
        if(obHorseShoeModel.blnTempC)
        {
            iTotalTicks = 17;
            iMods=4;
        }

        while(i < iTotalTicks)
        {
            Point point;
            Point point1;
            if(i % iMods == 0)//long tick
            {
                point = tickPointForValue(i, iStart);
                point1 = tickPointForValue(i, iLGapEnd);
            }
            else //short tick
            {
                point = tickPointForValue(i, iStart);
                point1 = tickPointForValue(i, iSGapEnd);
            }
            paramCanvas.drawLine(obTranslateX - point.x, obTranslateY - point.y, obTranslateX - point1.x, obTranslateY - point1.y, paint);
            i++;
        }

//        Paint localPaint = new Paint();
//        localPaint.setColor(getResources().getColor(R.color.background_arc_off_white));
//        localPaint.setAntiAlias(true);
//        localPaint.setStrokeCap(Paint.Cap.ROUND);
//        localPaint.setStrokeWidth(2.0F);
//        int i = 0;
//        if (i < 31)
//        {
//            Point localPoint1;
//            if (i % 5 == 0)
//                localPoint1 = tickPointForValue(i, 27);
//            for (Point localPoint2 = tickPointForValue(i, 72); ; localPoint2 = tickPointForValue(i, 57))
//            {
//                paramCanvas.drawLine(this.obTranslateX - localPoint1.x, this.obTranslateY - localPoint1.y, this.obTranslateX - localPoint2.x, this.obTranslateY - localPoint2.y, localPaint);
//                i++;
//                break;
//                localPoint1 = tickPointForValue(i, 37);
//            }
//        }
    }

    private void drawViewMode(Canvas canvas)
    {

        Log.e(TAG, "drawViewMode.blnTempC:" + obHorseShoeModel.blnTempC);
        int iEndTemp=90;
        float iTotalTemp=30.0f;
        int iStartTemp=60;

        if(obHorseShoeModel.blnTempC)
        {
              iEndTemp=32;
              iTotalTemp=16;
              iStartTemp=16;
        }
        int k1;

        if(obHorseShoeModel.isSingleSetPoint() && obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL && !obHorseShoeModel.isUsingSchedule())
        {
            canvas.drawArc(obArcRect, 135F, 270F, false, obSingleSetPointPaint);
        }
        else if(obHorseShoeModel.getSystemMode() == LXSystemMode.OFF)
        {
            canvas.drawArc(obArcRect, 135F, 270F, false, obBackgroundProgramArcPaint);
        }
        else
        {
            canvas.drawArc(obArcRect, 135F, 270F, false, obBackgroundArcPaint);

        }

        if(!obHorseShoeModel.isSingleSetPoint() || obHorseShoeModel.getSystemMode() != LXSystemMode.HEAT_AND_COOL || obHorseShoeModel.isUsingSchedule())
        {
            float f = 270F * ((float)(iEndTemp - obHorseShoeModel.getCoolSP()) / iTotalTemp);
            float f1 = (float)(0.5D + (double)(405F - f));
            if(obHorseShoeModel.getSystemMode() == LXSystemMode.COOL || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                canvas.drawArc(obArcRect, f1, f, false, obCoolArcPaint);
            float f2 = 270F * ((float)(-iStartTemp + obHorseShoeModel.getHeatSP()) / iTotalTemp);


            if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                canvas.drawArc(obArcRect, 135F, f2, false, obHeatArcPaint);
            drawIndoorTick(canvas);
            int i = 180 + (int)f1;
            int j = (int)((double)obArcRadius * Math.cos(Math.toRadians(i)));
            int k = (int)((double)obArcRadius * Math.sin(Math.toRadians(i)));
            int l;
            int i1;
            int j1;
            if(obHorseShoeModel.getSystemMode() == LXSystemMode.COOL || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                if(obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_AWAY)
                {
                    obCoolKnobPos = new Point(obTranslateX - j - obCoolAwayThumb.getScaledWidth(canvas) / 2, obTranslateY - k - obCoolAwayThumb.getScaledHeight(canvas) / 2);
                    canvas.drawBitmap(obCoolAwayThumb, obCoolKnobPos.x, obCoolKnobPos.y, new Paint());
                } else
                {
                    obCoolKnobPos = new Point(obTranslateX - j - obCoolViewThumb.getScaledWidth(canvas) / 2, obTranslateY - k - obCoolViewThumb.getScaledHeight(canvas) / 2);
                    canvas.drawBitmap(obCoolViewThumb, obCoolKnobPos.x, obCoolKnobPos.y, new Paint());
                }
            l = (int)(90F + (180F + (45F + f2)));
            i1 = (int)((double)obArcRadius * Math.cos(Math.toRadians(l)));
            j1 = (int)((double)obArcRadius * Math.sin(Math.toRadians(l)));
            if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT || obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                if(obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_AWAY)
                {
                    obHeatKnobPos = new Point(obTranslateX - i1 - obHeatAwayThumb.getScaledWidth(canvas) / 2, obTranslateY - j1 - obHeatAwayThumb.getScaledHeight(canvas) / 2);
                    canvas.drawBitmap(obHeatAwayThumb, obHeatKnobPos.x, obHeatKnobPos.y, new Paint());
                    return;
                } else
                {
                    obHeatKnobPos = new Point(obTranslateX - i1 - obHeatViewThumb.getScaledWidth(canvas) / 2, obTranslateY - j1 - obHeatViewThumb.getScaledHeight(canvas) / 2);
                    canvas.drawBitmap(obHeatViewThumb, obHeatKnobPos.x, obHeatKnobPos.y, new Paint());
                    return;
                }
            return;
        }
        else
        {
            k1 = (int)(90F + (180F + (45F + 270F * ((float)(-iStartTemp + obHorseShoeModel.getSingleSetPointSP()) / iTotalTemp))));
            int l1 = (int)((double)obArcRadius * Math.cos(Math.toRadians(k1)));
            int i2 = (int)((double)obArcRadius * Math.sin(Math.toRadians(k1)));
            obSingleSetPointKnobPos = new Point(obTranslateX - l1 - obRedSingleSetPointInActiveThumb.getScaledWidth(canvas) / 2, obTranslateY - i2 - obRedSingleSetPointInActiveThumb.getScaledHeight(canvas) / 2);
            if(k1 >= 450)
            {
                canvas.drawBitmap(obRedSingleSetPointInActiveThumb, obSingleSetPointKnobPos.x, obSingleSetPointKnobPos.y, new Paint());
                return;
            }

            else
            {
                canvas.drawBitmap(obBlueSingleSetPointInActiveThumb, obSingleSetPointKnobPos.x, obSingleSetPointKnobPos.y, new Paint());
                return;
            }
        }

       /*

        if ((this.obHorseShoeModel.isSingleSetPoint()) && (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL) && (!this.obHorseShoeModel.isUsingSchedule()))
            paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obSingleSetPointPaint);
        if ((this.obHorseShoeModel.isSingleSetPoint()) && (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL) && (!this.obHorseShoeModel.isUsingSchedule()))
        {
            int i2 = (int)(90.0F + (180.0F + (45.0F + 270.0F * ((-60 + this.obHorseShoeModel.getSingleSetPointSP()) / 30.0F))));
            int i3 = (int)(this.obArcRadius * Math.cos(Math.toRadians(i2)));
            int i4 = (int)(this.obArcRadius * Math.sin(Math.toRadians(i2)));
            this.obSingleSetPointKnobPos = new Point(this.obTranslateX - i3 - this.obRedSingleSetPointInActiveThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - i4 - this.obRedSingleSetPointInActiveThumb.getScaledHeight(paramCanvas) / 2);
            if (i2 < 450)
            {
                paramCanvas.drawBitmap(this.obBlueSingleSetPointInActiveThumb, this.obSingleSetPointKnobPos.x, this.obSingleSetPointKnobPos.y, new Paint());


//                return;;\
            }
            paramCanvas.drawBitmap(this.obRedSingleSetPointInActiveThumb, this.obSingleSetPointKnobPos.x, this.obSingleSetPointKnobPos.y, new Paint());
            return;
        }

        if (this.obHorseShoeModel.getSystemMode() == LXSystemMode.OFF)
        {
            paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundProgramArcPaint);

        }
        paramCanvas.drawArc(this.obArcRect, 135.0F, 270.0F, false, this.obBackgroundArcPaint);

        float f1 = 270.0F * ((90 - this.obHorseShoeModel.getCoolSP()) / 30.0F);
        float f2 = (float)(0.5D + (405.0F - f1));
        if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.COOL) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
            paramCanvas.drawArc(this.obArcRect, f2, f1, false, this.obCoolArcPaint);
        float f3 = 270.0F * ((-60 + this.obHorseShoeModel.getHeatSP()) / 30.0F);
        if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
            paramCanvas.drawArc(this.obArcRect, 135.0F, f3, false, this.obHeatArcPaint);
        drawIndoorTick(paramCanvas);
        int i = 180 + (int)f2;
        int j = (int)(this.obArcRadius * Math.cos(Math.toRadians(i)));
        int k = (int)(this.obArcRadius * Math.sin(Math.toRadians(i)));
        if ((this.obHorseShoeModel.getSystemMode() == LXSystemMode.COOL) || (this.obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL))
        {
            if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_AWAY)
            {
                this.obCoolKnobPos = new Point(this.obTranslateX - j - this.obCoolViewThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - this.obCoolViewThumb.getScaledHeight(paramCanvas) / 2);
                paramCanvas.drawBitmap(this.obCoolViewThumb, this.obCoolKnobPos.x, this.obCoolKnobPos.y, new Paint());
            }
            this.obCoolKnobPos = new Point(this.obTranslateX - j - this.obCoolAwayThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - k - this.obCoolAwayThumb.getScaledHeight(paramCanvas) / 2);
            paramCanvas.drawBitmap(this.obCoolAwayThumb, this.obCoolKnobPos.x, this.obCoolKnobPos.y, new Paint());
        }
        int n;
        int i1;
        //2015107
        //while (true)
        {
            int m = (int)(90.0F + (180.0F + (45.0F + f3)));
            n = (int)(this.obArcRadius * Math.cos(Math.toRadians(m)));
            i1 = (int)(this.obArcRadius * Math.sin(Math.toRadians(m)));
            if ((this.obHorseShoeModel.getSystemMode() != LXSystemMode.HEAT) && (this.obHorseShoeModel.getSystemMode() != LXSystemMode.HEAT_AND_COOL))
                return;
            if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_AWAY)
            {
                this.obHeatKnobPos = new Point(this.obTranslateX - n - this.obHeatViewThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - i1 - this.obHeatViewThumb.getScaledHeight(paramCanvas) / 2);
                paramCanvas.drawBitmap(this.obHeatViewThumb, this.obHeatKnobPos.x, this.obHeatKnobPos.y, new Paint());
            }
            this.obHeatKnobPos = new Point(this.obTranslateX - n - this.obHeatAwayThumb.getScaledWidth(paramCanvas) / 2, this.obTranslateY - i1 - this.obHeatAwayThumb.getScaledHeight(paramCanvas) / 2);
            paramCanvas.drawBitmap(this.obHeatAwayThumb, this.obHeatKnobPos.x, this.obHeatKnobPos.y, new Paint());
            return;

        }
        */
    }

    private float getCoolKnobAngle(float paramFloat)
    {
        return 180 + (int)paramFloat;
    }

    private float getHeatKnobAngle(float paramFloat)
    {
        return (int)(90.0F + (180.0F + (45.0F + paramFloat)));
    }

    private int getProgressForAngle(double paramDouble)
    {
        int i = (int) Math.round(paramDouble * valuePerDegree());
        if ((i < 0) || (i > 30))
            i = -1;
        return i;
    }

    private double getTouchDegrees(float paramFloat1, float paramFloat2)
    {
        float f = paramFloat1 - this.obTranslateX;
        double d = Math.toDegrees(1.570796326794897D + Math.atan2(paramFloat2 - this.obTranslateY, f) - Math.toRadians(180.0D));
        if (d < 0.0D)
            d += 360.0D;
        return d - 45.0D;
    }

    private boolean ignoreTouch(float f, float f1)
    {
//        float f1 = paramFloat1 - this.obTranslateX;
//        float f2 = paramFloat2 - this.obTranslateY;
//        boolean bool = (float)Math.sqrt(f1 * f1 + f2 * f2) < this.obTouchIgnoreRadius;
//        int i = 0;
//        if (bool)
//            i = 1;
//        return i;
        Log.e(TAG, "obTranslateX:" + obTranslateX);
        Log.e(TAG, "obTranslateY:" + obTranslateY);
        boolean flag = false;
        f -= obTranslateX;
        f1 -= obTranslateY;
        if((float) Math.sqrt(f * f + f1 * f1) < obTouchIgnoreRadius)
            flag = true;
        return flag;

    }

    private void modifyTimer(MotionEvent paramMotionEvent)
    {
        if (paramMotionEvent.getAction() == 0)
            cancelBackToViewTimer();
        if (paramMotionEvent.getAction() == 1)
            startBackToViewTimer();
    }

    private void onProgressRefresh(int paramInt)
    {
        updateProgress(paramInt);

    }

    private boolean pointsAreClose(Point paramPoint1, Point paramPoint2)
    {

        Log.e(TAG, "pointsAreClose\nparamPoint1.x:" + paramPoint1.x
                + " paramPoint1.y:" + paramPoint1.y
                + "\nparamPoint2.x:" + paramPoint2.x
                + " paramPoint2.y:" + paramPoint2.y);

        if(blnDrawSmall)
        {
            return (paramPoint2 != null)
                    && (paramPoint1.x < (80 + paramPoint2.x))
                    && (paramPoint1.x >  paramPoint2.x-10)
                    && (paramPoint1.y <  (80 + paramPoint2.y))
                    && (paramPoint1.y > paramPoint2.y-10);
        }

        if(dm.density>=2)
        {
            return (paramPoint2 != null)
                    && (paramPoint1.x < (50*dm.density + paramPoint2.x))
                    && (paramPoint1.x >  paramPoint2.x-10*dm.density)
                    && (paramPoint1.y <  (50*dm.density + paramPoint2.y))
                    && (paramPoint1.y > paramPoint2.y-10*dm.density);
        }

        return (paramPoint2 != null)
                && (paramPoint1.x < (130 + paramPoint2.x))
                && (paramPoint1.x >  paramPoint2.x-10)
                && (paramPoint1.y <  (130 + paramPoint2.y))
                && (paramPoint1.y > paramPoint2.y-10);

//        return (paramPoint2 != null)
//                && (paramPoint1.x < 0 + (110 + paramPoint2.x))
//                && (paramPoint1.x > 0 + paramPoint2.x)
//                && (paramPoint1.y < 0 + (110 + paramPoint2.y))
//                && (paramPoint1.y > 0 + paramPoint2.y);
    }

    private Bitmap rotateBitmap(Bitmap paramBitmap, float paramFloat)
    {
        Matrix localMatrix = new Matrix();
        localMatrix.postRotate(paramFloat);
        return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
    }

    private void sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE paramUI_MODE, MotionEvent paramMotionEvent)
    {
        this.obHorseShoeModel.setUIMode(paramUI_MODE, true);
        updateFromOnTouch(paramMotionEvent);
        invalidate();
    }

    private void setDefaults()
    {
        dm=getResources().getDisplayMetrics();
        if(dm.widthPixels<500)
        {
            Log.e(TAG, "blnDrawSmall=true");
            blnDrawSmall=true;
        }

        this.obProgress = 0;
        this.obHorseShoeModel = new LXHorseShoeModel();
        this.obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
        this.obHorseShoeModel.setSystemMode(LXSystemMode.HEAT_AND_COOL, false);
        this.obHorseShoeModel.setFanMode(LXFanMode.AUTO, false);
        this.obHorseShoeModel.setIndoorSP(73);
        this.obHorseShoeModel.setHeatSP(64, false);
        this.obHorseShoeModel.setCoolSP(78, false);
        this.obHorseShoeModel.setSingleSetPointSP(70, false);
        this.obHorseShoeModel.addHorseShoeListener(this);
        this.obBackToViewTimer = new Timer();
        Resources localResources = getResources();
        this.obBackgroundArcPaint = createArcPaint(localResources.getColor(R.color.background_arc_off_white));
        this.obBackgroundAwayPaint = createArcPaint(localResources.getColor(R.color.white_away));
        this.obBackgroundEditArcPaint = createArcPaint(localResources.getColor(R.color.background_arc_gray));
        this.obBackgroundProgramArcPaint = createArcPaint(localResources.getColor(R.color.background_arc_dark));
        this.obHeatArcPaint = createSPPaint(localResources.getColor(R.color.red_gradient_start), localResources.getColor(R.color.red_gradient_end));
        this.obCoolArcPaint = createSPPaint(localResources.getColor(R.color.blue_gradient_start), localResources.getColor(R.color.blue_gradient_end));
        this.obHeatAwayPaint = createSPPaint(localResources.getColor(R.color.red_away_gradient_light), localResources.getColor(R.color.red_away_gradient_dark));
        this.obCoolAwayPaint = createSPPaint(localResources.getColor(R.color.blue_away_gradient_dark), localResources.getColor(R.color.button_material_light));
        this.obIndoorPaint = new Paint();
        this.obIndoorPaint.setColor(localResources.getColor(R.color.indoor_yellow));
        this.obIndoorPaint.setStyle(Style.STROKE);
        this.obIndoorPaint.setStrokeCap(Cap.ROUND);
        this.obIndoorPaint.setAntiAlias(true);
        this.obIndoorPaint.setStrokeWidth(8F);
        Point localPoint1 = new Point(300, 0);
        Point localPoint2 = new Point(900, 0);
        this.obSingleSetPointPaint = new Paint();
        this.obSingleSetPointPaint.setColor(0xFFFF0000);
        this.obSingleSetPointPaint.setStyle(Style.STROKE);
        this.obSingleSetPointPaint.setStrokeCap(Cap.ROUND);
        this.obSingleSetPointPaint.setAntiAlias(true);
        if(blnDrawSmall)
        {
            this.obSingleSetPointPaint.setStrokeWidth(18.0f);
            Log.e(TAG, "setStrokeWidth20");
        }
        else
        {
            this.obSingleSetPointPaint.setStrokeWidth(37.0F);
            Log.e(TAG, "setStrokeWidth37");
        }
        this.obSingleSetPointPaint.setShader(new LinearGradient(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y, localResources.getColor(R.color.feels_gradient_light), localResources.getColor(R.color.feels_gradient_dark), TileMode.CLAMP));
        this.obHeatViewThumb = BitmapFactory.decodeResource(localResources, R.drawable.redknob);
        this.obHeatActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.redknobactive);
        this.obHeatInActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.toggleoff);
        this.obHeatAwayThumb = BitmapFactory.decodeResource(localResources, R.drawable.away_red_knob);
        this.obCoolViewThumb = BitmapFactory.decodeResource(localResources, R.drawable.blueknob);
        this.obCoolActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.blueknobactive);
        this.obCoolInActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.toggleoff);
        this.obCoolAwayThumb = BitmapFactory.decodeResource(localResources, R.drawable.away_blue_knob);
        this.obBlueSingleSetPointActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.fl_blue_knob_pointer);
        this.obBlueSingleSetPointInActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.fl_blue_knob);
        this.obRedSingleSetPointActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.fl_red_knob_pointer);
        this.obRedSingleSetPointInActiveThumb = BitmapFactory.decodeResource(localResources, R.drawable.fl_red_knob);
    }

    private boolean shouldReturnToViewMode(Point paramPoint)
    {
//        float f1 = paramPoint.x - this.obTranslateX;
//        float f2 = paramPoint.y - this.obTranslateY;
//        boolean bool = (float)Math.sqrt(f1 * f1 + f2 * f2) < 100 + this.obArcRadius;
//        int i = 0;
//        if (bool)
//            i = 1;
//        return i;
        int iOutDis=100;
        if(blnDrawSmall)
        {
            iOutDis = 55;
        }


        boolean flag = false;
        float f = paramPoint.x - obTranslateX;
        float f1 = paramPoint.y - obTranslateY;
        if((float) Math.sqrt(f * f + f1 * f1) > (float)(obArcRadius + iOutDis))
            flag = true;
        return flag;
    }

    private Point tickPointForValue(int paramInt1, int paramInt2)
    {
        float iTicks=30.0f;
        if(obHorseShoeModel.blnTempC)
        {
            iTicks = 16.0f;
        }
        int i = (int)(90.0F + (180.0F + (45.0F + 270.0F * (paramInt1 / iTicks))));
        return new Point((int)((paramInt2 + this.obArcRadius) * Math.cos(Math.toRadians(i))), (int)((paramInt2 + this.obArcRadius) * Math.sin(Math.toRadians(i))));
    }

    private void updateFromOnTouch(MotionEvent paramMotionEvent)
    {


        if(ignoreTouch(paramMotionEvent.getX(), paramMotionEvent.getY()))
        {
            Log.e(TAG, "ignoreTouch");
            return;
        }
        else
        {
            setPressed(true);
            obTouchAngle = getTouchDegrees(paramMotionEvent.getX(), paramMotionEvent.getY());

            onProgressRefresh(getProgressForAngle(obTouchAngle));
            return;
        }

    }

    private void updateProgress(int paramInt)
    {
        int iTotalTemp=30;
        int iStartTemp=60;
        if(obHorseShoeModel.blnTempC)
        {
            iTotalTemp=16;
            iStartTemp=16;

        }


        if (paramInt == -1)
            return;
        if (paramInt > iTotalTemp)
            paramInt = iTotalTemp;
        if (this.obProgress < 0)
            paramInt = 0;
        this.obProgress = paramInt;


        if (obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_COOL_EDIT_MODE)
        {
            if(obHorseShoeModel.getCoolSP()!=iStartTemp + this.obProgress)
            {
                obHorseShoeModel.setCoolSP(iStartTemp + this.obProgress, true);
            }
        }
        else if (obHorseShoeModel.getUIMode() == LXHorseShoeModel.UI_MODE.UI_HEAT_EDIT_MODE)
        {
           if(obHorseShoeModel.getHeatSP()!=iStartTemp + this.obProgress)
           {
               obHorseShoeModel.setHeatSP(iStartTemp + this.obProgress, true);
           }
        }

        invalidate();
    }

    private float valuePerDegree()
    {
        if(obHorseShoeModel.blnTempC)
        {
            return (0.1111111F/30.0f)*16.0f;
        }
          else
        {
            return 0.1111111F;
        }

    }

    public void cancelBackToViewTimer()
    {
        if (this.obBackToViewTimer != null)
        {
            this.obBackToViewTimer.cancel();
            this.obBackToViewTimer.purge();
            this.obBackToViewTimer = null;
        }
    }

    public void createBackToViewTimer()
    {
        if (this.obBackToViewTimer == null)
        {
            this.obBackToViewTimer = new Timer();

        }
    }

    public LXHorseShoeModel getHorseShoeModel()
    {
        return this.obHorseShoeModel;
    }

    public void onCoolSPChanged(int paramInt, boolean paramBoolean)
    {
//        if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
//            if (paramInt <= 90)
//            {
//                this.obProgress = (paramInt - 60);
//                invalidate();
//            }
//        do
//            return;
//        while ((paramInt < 60) || (paramInt > 90));
//        invalidate();


        int iStartTemp=60;
        int iEndTemp=90;
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp=16;
            iEndTemp = 32;
        }

        if(obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
        {
            if(paramInt <= iEndTemp)
            {
                obProgress = paramInt - iStartTemp;
                invalidate();
            }
        }
        else
        if(paramInt >= iStartTemp && paramInt <= iEndTemp)
        {
            invalidate();
            return;
        }
    }

    protected void onDraw(Canvas paramCanvas)
    {
        Log.e(TAG, "onDraw");
        switch (this.obHorseShoeModel.getUIMode().ordinal())
        {

            case 0:
                cancelBackToViewTimer();
                drawViewMode(paramCanvas);
                break;
            case 1:
                drawHeatMode(paramCanvas);
                break;
            case 2:
                drawCoolMode(paramCanvas);
                break;
            case 3:
                drawSingleSetPointMode(paramCanvas);
                break;
            case 4:
                drawSelectMode(paramCanvas);
                break;
            case 5:
                cancelBackToViewTimer();
                drawAwayMode(paramCanvas);
                break;
            case 6:
                cancelBackToViewTimer();
                drawDisconnectedMode(paramCanvas);
                break;
            case 7:
                cancelBackToViewTimer();
                drawConnectedMode(paramCanvas);
                break;

        }


//        switch (this.obHorseShoeModel.getUIMode().ordinal())
//        {
//
//            case 1:
//                cancelBackToViewTimer();
//                drawViewMode(paramCanvas);
//                break;
//            case 2:
//                drawHeatMode(paramCanvas);
//                break;
//            case 3:
//                drawCoolMode(paramCanvas);
//                break;
//            case 4:
//                drawSingleSetPointMode(paramCanvas);
//                break;
//            case 5:
//                drawSelectMode(paramCanvas);
//                break;
//            case 6:
//                cancelBackToViewTimer();
//                drawAwayMode(paramCanvas);
//                break;
//            case 7:
//                cancelBackToViewTimer();
//                drawDisconnectedMode(paramCanvas);
//                break;
//            default:
//                break;
//        }
//        cancelBackToViewTimer();
//        drawConnectedMode(paramCanvas);
    }

    public void onFanModeChanged(LXFanMode paramLXFanMode, boolean paramBoolean)
    {
    }

    public void onHeatSPChanged(int paramInt, boolean paramBoolean)
    {
//        if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
//            if (paramInt >= 60)
//            {
//                this.obProgress = (paramInt - 60);
//                invalidate();
//            }
//        do
//            return;
//        while ((paramInt < 60) || (paramInt > 90));
//        invalidate();

        int iStartTemp=60;
        int iEndTemp=90;
        if(obHorseShoeModel.blnTempC)
        {
            iStartTemp=16;
            iEndTemp = 32;
        }

        if(obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
        {
            if(paramInt >= iStartTemp)
            {
                obProgress = paramInt - iStartTemp;
                invalidate();
            }
        } else
        if(paramInt >= iStartTemp && paramInt <= iEndTemp)
        {
            invalidate();
            return;
        }
    }

    public void onIndoorSPChanged(int paramInt)
    {
        Log.e(TAG, "onIndoorSPChanged:" + paramInt);
//        int iStartTemp=60;
//        int iEndTemp=90;
//        if(obHorseShoeModel.blnTempC)
//        {
//            iStartTemp=16;
//            iEndTemp = 32;
//        }
//        else
//        {
//            paramInt = Math.round(paramInt * 1.8f + 32);
//        }
//        if ((paramInt >= iStartTemp) && (paramInt <= iEndTemp))
            invalidate();


    }

    public void onIsSingleSetPointChanged(boolean paramBoolean)
    {
        invalidate();
    }

    public void onIsUsingSchedulesChanged(boolean paramBoolean)
    {
        invalidate();
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {


        int i = getDefaultSize(getSuggestedMinimumHeight(), paramInt2);
        int j = getDefaultSize(getSuggestedMinimumWidth(), paramInt1);


        int k = Math.min(j, i);

        this.obTranslateX = (int)(0.5F * j);
        this.obTranslateY = (int)(0.5F * i);

        int m = k - getPaddingLeft() - 18;

        Log.e(TAG, "m:" + m);

        if((m/dm.density)>500)
        {
            m=m-100;
        }

        this.obArcRadius = (m / 2);
        float f1 = i / 2 - m / 2;
        float f2 = j / 2 - m / 2;

        if(blnDrawSmall)
        {
            this.obArcRect.set(f2, f1+15, f2 + m, f1 + m+15);

            this.obTranslateY += 15;
            //this.obArcRect.set(f2, f1, f2 + m, f1 + m);
        }
        else
        {
            this.obArcRect.set(f2, f1, f2 + m, f1 + m);
        }

        //Log.e(TAG, "obArcRect:\n" + obArcRect.left + "," + obArcRect.top + "\n" + obArcRect.right+","+obArcRect.bottom);

        setTouchInSide();
        super.onMeasure(paramInt1, paramInt2);
    }

    public void onSingleSetPointSPChanged(int paramInt, boolean paramBoolean)
    {
//        if (this.obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
//            if ((paramInt <= 90) && (paramInt >= 60))
//            {
//                this.obProgress = (paramInt - 60);
//                invalidate();
//            }
//        do
//            return;
//        while ((paramInt < 60) || (paramInt > 90));
//        invalidate();


        if(obHorseShoeModel.getUIMode() != LXHorseShoeModel.UI_MODE.UI_VIEW_MODE)
        {
            if(paramInt <= 90 && paramInt >= 60)
            {
                obProgress = paramInt - 60;
                invalidate();
            }
        } else
        if(paramInt >= 60 && paramInt <= 90)
        {
            invalidate();
            return;
        }
    }

    public void onSystemModeChanged(LXSystemMode paramLXSystemMode, boolean paramBoolean)
    {
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        Log.e(TAG, "onTouchEvent:" + paramMotionEvent.getAction());

        updateOnTouch(paramMotionEvent);
        return true;
    }

    public void onUIModeChanged(LXHorseShoeModel.UI_MODE paramUI_MODE, boolean paramBoolean)
    {
//        UI_VIEW_MODE,
//                UI_HEAT_EDIT_MODE,
//                UI_COOL_EDIT_MODE,
//                UI_SINGLE_SET_POINT_EDIT_MODE,
//                UI_SELECT,
//                UI_AWAY,
//                UI_DISCONNECTED,
//                UI_CONNECTED,


        if (!paramBoolean)
        {
//            switch (paramUI_MODE.ordinal())
//            {
//                case 1:
//                    this.obProgress = this.obHorseShoeModel.getHeatSP();
//                    break;
//                case 2:
//                    this.obProgress = this.obHorseShoeModel.getCoolSP();
//                    break;
//                case 3:
//                    this.obProgress = this.obHorseShoeModel.getSingleSetPointSP();
//                    break;
//
//                default:
//                    break;
//            }

            switch (paramUI_MODE.ordinal())
            {
                case 1:
                    this.obProgress = this.obHorseShoeModel.getHeatSP();
                    break;
                case 2:
                    this.obProgress = this.obHorseShoeModel.getCoolSP();
                    break;
                case 3:
                    this.obProgress = this.obHorseShoeModel.getSingleSetPointSP();
                    break;

                default:
                    break;
            }
        }

        invalidate();



//        if (!paramBoolean)
//            switch (paramUI_MODE.ordinal())
//        {
//            case 1:
//            case 5:
//            case 6:
//            case 7:
//            default:
//            case 2:
//            case 3:
//            case 4:
//        }
//        while (true)
//        {
//            invalidate();
//            return;
//            this.obProgress = this.obHorseShoeModel.getHeatSP();
//            continue;
//            this.obProgress = this.obHorseShoeModel.getCoolSP();
//            continue;
//            this.obProgress = this.obHorseShoeModel.getSingleSetPointSP();
//        }
    }

    public void setHorseShoeModel(LXHorseShoeModel paramLXHorseShoeModel)
    {
        this.obHorseShoeModel = paramLXHorseShoeModel;
        this.obHorseShoeModel.setHeatSP(paramLXHorseShoeModel.getHeatSP(), false);
        this.obHorseShoeModel.setCoolSP(paramLXHorseShoeModel.getCoolSP(), false);
        this.obHorseShoeModel.setSingleSetPointSP(paramLXHorseShoeModel.getSingleSetPointSP(), false);
        this.obHorseShoeModel.setUIMode(paramLXHorseShoeModel.getUIMode(), false);
        this.obHorseShoeModel.setFanMode(paramLXHorseShoeModel.getFanMode(), false);
        this.obHorseShoeModel.setSystemMode(paramLXHorseShoeModel.getSystemMode(), false);
//        this.obHorseShoeModel.setIsUsingSchedule(paramLXHorseShoeModel.isUsingSchedule());
//        this.obHorseShoeModel.setSelectedSchedule(paramLXHorseShoeModel.getSelectedSchedule());
        this.obHorseShoeModel.setFeelsLike(paramLXHorseShoeModel.isFeelsLike());
        this.obHorseShoeModel.setAway(paramLXHorseShoeModel.getIsAway());
        this.obHorseShoeModel.setSingleSetPoint(paramLXHorseShoeModel.isSingleSetPoint());

        invalidate();
    }

    public void setTouchInSide()
    {
        if(blnDrawSmall)
        {
            this.obTouchIgnoreRadius = 60.0F;
        }
        else
        {
            this.obTouchIgnoreRadius = 200.0F;
        }
    }

    public void startBackToViewTimer()
    {
        cancelBackToViewTimer();
        createBackToViewTimer();
        TimerTask local1 = new TimerTask()
    {
        public void run()
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                public void run()
                {
                    if (!LXHorseShoe.this.obHorseShoeModel.getIsAway())
                    {
                        LXHorseShoe.this.obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);

                    }
                    else
                    {
                        LXHorseShoe.this.obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                    }
                }
            });
        }
    };
        this.obBackToViewTimer.schedule(local1, 5000L);
    }

    protected void updateOnTouch(MotionEvent paramMotionEvent)
    {





        Point localPoint = new Point((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());

        Log.e(TAG, "updateOnTouch :" + localPoint.x + "," + localPoint.y);

        switch (obHorseShoeModel.getUIMode().ordinal())
        {

            case 0://UI_VIEW_MODE
                if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                {

                    if (pointsAreClose(localPoint, obCoolKnobPos))
                    {
                        sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE.UI_COOL_EDIT_MODE, paramMotionEvent);
                        Log.e(TAG, "sendUIModeChangeFromTouch :UI_COOL_EDIT_MODE");

                    }
                    else if (pointsAreClose(localPoint, obHeatKnobPos))
                    {
                        sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE.UI_HEAT_EDIT_MODE, paramMotionEvent);
                        Log.e(TAG, "sendUIModeChangeFromTouch :UI_HEAT_EDIT_MODE");
                    }
                }
                break;

            case 1://                UI_HEAT_EDIT_MODE,1

                modifyTimer(paramMotionEvent);
                if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                {
                    if(pointsAreClose(localPoint, obHeatKnobPos))
                    {
                        Log.e(TAG, "pointsAreClose==obHeatKnobPos");

                        updateFromOnTouch(paramMotionEvent);
                        if(paramMotionEvent.getAction() == 0)
                        {
                            obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.PressHeatSP: ").append(obHorseShoeModel.getHeatSP()).toString());
                        }
                        if(paramMotionEvent.getAction() == 1)
                        {
                            obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.ReleaseHeatSP: ").append(obHorseShoeModel.getHeatSP()).toString());
                        }
                        invalidate();
                        return;
                    }
                    else if(pointsAreClose(localPoint, obCoolKnobPos))
                    {

                        Log.e(TAG, "pointsAreClose==obCoolKnobPos");
                        sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE.UI_COOL_EDIT_MODE, paramMotionEvent);
                        return;
                    }
                    else if(shouldReturnToViewMode(localPoint))
                    {
                        Log.e(TAG, "shouldReturnToViewMode");


                        if(obHorseShoeModel.getIsAway())
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                        else
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                        invalidate();
                        return;
                    }

                    Log.e(TAG, "updateFromOnTouch");
                    updateFromOnTouch(paramMotionEvent);
                    if(paramMotionEvent.getAction() == 0)
                        obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                    else
                        obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                    //invalidate();
                    return;
                }
                if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT)
                {
                    if(pointsAreClose(localPoint, obHeatKnobPos))
                    {
                        updateFromOnTouch(paramMotionEvent);
                        if(paramMotionEvent.getAction() == 0)
                        {
                            obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.PressHeatSP: ").append(obHorseShoeModel.getHeatSP()).toString());
                        }
                        if(paramMotionEvent.getAction() == 1)
                        {
                            obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.ReleaseHeatSP: ").append(obHorseShoeModel.getHeatSP()).toString());
                        }
                        invalidate();
                        return;
                    }
                    if(shouldReturnToViewMode(localPoint))
                    {
                        if(obHorseShoeModel.getIsAway())
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                        else
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                        invalidate();
                        return;
                    }
                    updateFromOnTouch(paramMotionEvent);
                    if(paramMotionEvent.getAction() == 0)
                        obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                    else
                        obHorseShoeModel.setHeatSP(obHorseShoeModel.getHeatSP(), true);
                    //invalidate();
                    return;
                }
            case 2://                UI_COOL_EDIT_MODE,2


                modifyTimer(paramMotionEvent);
                if(obHorseShoeModel.getSystemMode() == LXSystemMode.HEAT_AND_COOL)
                {
                    if(pointsAreClose(localPoint, obCoolKnobPos))
                    {

                        Log.e(TAG, "pointsAreClose(localPoint, obCoolKnobPos)");
                        updateFromOnTouch(paramMotionEvent);
                        if(paramMotionEvent.getAction() == 0)
                        {
                            obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.PressCoolSP: ").append(obHorseShoeModel.getCoolSP()).toString());
                        }
                        if(paramMotionEvent.getAction() == 1)
                        {
                            obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.ReleaseCoolSP: ").append(obHorseShoeModel.getCoolSP()).toString());
                        }
                        invalidate();
                        return;
                    }
                    else if(pointsAreClose(localPoint, obHeatKnobPos))
                    {
                        Log.e(TAG, "pointsAreClose(localPoint, obHeatKnobPos)");
                        sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE.UI_HEAT_EDIT_MODE, paramMotionEvent);
                        return;
                    }
                    else  if(shouldReturnToViewMode(localPoint))
                    {

                        Log.e(TAG, "shouldReturnToViewMode");
                        if(obHorseShoeModel.getIsAway())
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                        else
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                        invalidate();
                        return;
                    }
                    Log.e(TAG, "updateFromOnTouch");
                    updateFromOnTouch(paramMotionEvent);
                    if(paramMotionEvent.getAction() == 1)
                        obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                    else
                        obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), false);
                    //invalidate();
                    return;
                }
                if(obHorseShoeModel.getSystemMode() == LXSystemMode.COOL)
                {
                    if(pointsAreClose(localPoint, obCoolKnobPos))
                    {
                        updateFromOnTouch(paramMotionEvent);
                        if(paramMotionEvent.getAction() == 0)
                        {
                            obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.PressCoolSP: ").append(obHorseShoeModel.getCoolSP()).toString());
                        }
                        if(paramMotionEvent.getAction() == 1)
                        {
                            obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                            //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.ReleaseCoolSP: ").append(obHorseShoeModel.getCoolSP()).toString());
                        }
                        invalidate();
                        return;
                    }
                    if(shouldReturnToViewMode(localPoint))
                    {
                        if(obHorseShoeModel.getIsAway())
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                        else
                            obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                        invalidate();
                        return;
                    }
                    updateFromOnTouch(paramMotionEvent);
                    if(paramMotionEvent.getAction() == 1)
                        obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                    else
                        obHorseShoeModel.setCoolSP(obHorseShoeModel.getCoolSP(), true);
                    //invalidate();
                    return;
                }
                break;
            case 3:
                //                UI_SINGLE_SET_POINT_EDIT_MODE,3

                if(!obHorseShoeModel.isSingleSetPoint()
                        || obHorseShoeModel.getSystemMode() != LXSystemMode.HEAT_AND_COOL
                        || obHorseShoeModel.isUsingSchedule()
                        || obHorseShoeModel.getIsAway())
                    break; /* Loop/switch isn't completed */
                if(pointsAreClose(localPoint, obSingleSetPointKnobPos))
                {
                    sendUIModeChangeFromTouch(LXHorseShoeModel.UI_MODE.UI_SINGLE_SET_POINT_EDIT_MODE, paramMotionEvent);

                }
                break;
            case 4:
                //                UI_SELECT,4

                modifyTimer(paramMotionEvent);
                if(pointsAreClose(localPoint, obSingleSetPointKnobPos))
                {
                    updateFromOnTouch(paramMotionEvent);
                    if(paramMotionEvent.getAction() == 0)
                    {
                        //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.PressFeelSP: ").append(obHorseShoeModel.getSingleSetPointSP()).toString());
                        obHorseShoeModel.setSingleSetPointSP(obHorseShoeModel.getSingleSetPointSP(), false);
                    }
                    if(paramMotionEvent.getAction() == 1)
                    {
                        //LXAnalytics.hitEvent(LXMainActivity.getActivityRef().getApplicationContext(), LXMainActivity.getDEVICE_ID(), (new StringBuilder()).append("Action.KnobSP.ReleaseFeelSP: ").append(obHorseShoeModel.getSingleSetPointSP()).toString());
                        obHorseShoeModel.setSingleSetPointSP(obHorseShoeModel.getSingleSetPointSP(), true);
                    }
                    invalidate();
                    return;
                }
                if(shouldReturnToViewMode(localPoint))
                {
                    if(obHorseShoeModel.getIsAway())
                        obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                    else
                        obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                    invalidate();
                    return;
                }
                updateFromOnTouch(paramMotionEvent);
                if(paramMotionEvent.getAction() == 1)
                    obHorseShoeModel.setSingleSetPointSP(obHorseShoeModel.getSingleSetPointSP(), true);
                else
                    obHorseShoeModel.setSingleSetPointSP(obHorseShoeModel.getSingleSetPointSP(), false);
                invalidate();
                break;
            case 5:
                //                UI_AWAY,5

                if(shouldReturnToViewMode(localPoint))
                {
                    if(obHorseShoeModel.getIsAway())
                        obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_AWAY, true);
                    else
                        obHorseShoeModel.setUIMode(LXHorseShoeModel.UI_MODE.UI_VIEW_MODE, true);
                    invalidate();
                    return;
                }
            default:
                break;
        }

    }
}