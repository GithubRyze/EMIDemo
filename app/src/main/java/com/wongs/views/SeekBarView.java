package com.wongs.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.wongs.emidemo.R;
import com.wongs.utils.Constant;
import com.wongs.utils.LogUtil;

/**
 * Created by ryze.liu on 7/26/2016.
 */
public class SeekBarView extends View {
    private static final String TAG = "SeekBarView";
    private Drawable heatThumb;
    private Drawable coolThumb;
    private Paint heatPaint;
    private Paint coolPaint;
    private int barWidth = 6;
    private int scaleWidth = 1;
    private RectF mArcRect = new RectF();
    private Paint normalPaint;
    private Paint scalePaint;
    private Paint tempScalePaint;
    private int progressWidth = 10;
    private int angleOffSet = -90;
    private int mArcRadius;
    private int mStartAngle;
    private float mHeatSweep;
    private float mClooSweep;
    private int mMax = 40;
    private int angle = 270;
    private int heatThumbPosX;
    private int heatThumbPosY;
    private int heatBarPosX;
    private int heatBarPosY;
    private int coolThumbPosX;
    private int coolThumbPosY;
    private int coolBarPosX;
    private int coolBarPosY;
    private int centerX;
    private int centerY;
    private DisplayMetrics density;
    private int heatValue = Constant.mSensorData.getHeatTemp();
    private int coolValue = Constant.mSensorData.getCoolTemp();
    private int currentTemp = 27;
    private Point tempStart = new Point();
    private Point tempEnd = new Point();
    private static int MODE;
    private Point heatPoint = new Point();
    private Point coolPoint = new Point();
    private Point touchPoint = new Point();
    private static final int VIEW_MODE = 1;
    private static final int HEAT_MODE = 2;
    private static final int COOL_MODE= 3;
    public SeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs,defStyleAttr);
    }

    public SeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs,0);
    }

    public SeekBarView(Context context) {
        super(context);
        initView(context, null, 0);
    }


    void initView(Context context, AttributeSet attrs, int defStyleAttr){
        LogUtil.d(TAG,"Initialising SeekBarView");
        final Resources resources = context.getResources();
        density = resources.getDisplayMetrics();
        // Defaults, may need to link this into theme settings
        int heatColor = resources.getColor(R.color.heat_color);
        int coolColor = resources.getColor(R.color.cool_color);
        int normalColor = resources.getColor(R.color.main_color);
        int thumbHalfHeight = 0;
        int thumbHalfWidth = 0;
        heatThumb = resources.getDrawable(R.drawable.fl_red_knob);
        coolThumb = resources.getDrawable(R.drawable.fl_blue_knob);

        thumbHalfWidth = heatThumb.getIntrinsicWidth()/8;
        thumbHalfHeight = heatThumb.getIntrinsicHeight()/8;
        heatThumb.setBounds(-thumbHalfWidth,-thumbHalfHeight,thumbHalfWidth,thumbHalfHeight);

        thumbHalfWidth = coolThumb.getIntrinsicWidth()/8;
        thumbHalfHeight = coolThumb.getIntrinsicHeight()/8;
        coolThumb.setBounds(-thumbHalfWidth,-thumbHalfHeight,thumbHalfWidth,thumbHalfHeight);

        mStartAngle = 45;
        angle = 270;
        progressWidth = (int) (progressWidth*density.density);
        barWidth = (int) (barWidth*density.density);
        scaleWidth =(int) (scaleWidth*density.density);
        heatPaint = new Paint();
        heatPaint.setColor(heatColor);
        heatPaint.setAntiAlias(true);
        heatPaint.setStyle(Paint.Style.STROKE);
        heatPaint.setStrokeWidth(barWidth);
        heatPaint.setStrokeJoin(Paint.Join.ROUND);
        heatPaint.setStrokeCap(Paint.Cap.ROUND);

        coolPaint = new Paint();
        coolPaint.setColor(coolColor);
        coolPaint.setAntiAlias(true);
        coolPaint.setStyle(Paint.Style.STROKE);
        coolPaint.setStrokeWidth(barWidth);
      //  coolPaint.setStrokeJoin(Paint.Join.ROUND);
        coolPaint.setStrokeCap(Paint.Cap.ROUND);

        normalPaint = new Paint();
        normalPaint.setColor(normalColor);
        normalPaint.setAntiAlias(true);
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setStrokeWidth(barWidth);
     //   normalPaint.setStrokeJoin(Paint.Join.ROUND);
        normalPaint.setStrokeCap(Paint.Cap.ROUND);

        scalePaint = new Paint();
        scalePaint.setColor(normalColor);
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(scaleWidth);

        tempScalePaint = new Paint();
        tempScalePaint.setColor(normalColor);
        tempScalePaint.setAntiAlias(true);
      //  tempScalePaint.setStrokeJoin(Paint.Join.ROUND);
        tempScalePaint.setStrokeCap(Paint.Cap.ROUND);
        tempScalePaint.setStrokeWidth(6f);

        mHeatSweep = (float)heatValue / mMax * 270;
        mClooSweep = 0-(float)(40-coolValue) / mMax * 270;
        MODE = VIEW_MODE;
    }

  /*  public void setTouchInSide(boolean isEnabled) {
        int thumbHalfheight = (int) mThumb.getIntrinsicHeight() / 2;
        int thumbHalfWidth = (int) mThumb.getIntrinsicWidth() / 2;
        mTouchInside = isEnabled;
        if (mTouchInside) {
            mTouchIgnoreRadius = (float) mArcRadius / 4;
        } else {
            // Don't use the exact radius makes interaction too tricky
            mTouchIgnoreRadius = mArcRadius
                    - Math.min(thumbHalfWidth, thumbHalfheight);
        }
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);
        final int min = Math.min(width, height);
        float top = 0;
        float left = 0;
        int arcDiameter = 0;

        centerX = (int) (width * 0.5f);
        centerY = (int) (height * 0.5f);

        //	LogUtil.d(TAG,"mTranslateX:"+mTranslateX);
        //	LogUtil.d(TAG,"mTranslateY:"+mTranslateY);

        arcDiameter = min - getPaddingLeft()-barWidth;
        mArcRadius = arcDiameter / 2;
        top = height / 2 - (arcDiameter / 2);
        left = width / 2 - (arcDiameter / 2);
        mArcRect.set(left, top, left + arcDiameter, top + arcDiameter);
        //final int arcStart = mStartAngle + mAngleOffset + mRotation;
        int arcStart = (int)mHeatSweep + mStartAngle+ 180 + 90 ;
        heatThumbPosX = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart)));
        heatThumbPosY = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart)));
        int arcStart_2 = (int)mClooSweep+mStartAngle+angle+180+90;
        coolThumbPosX = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart_2)));
        coolThumbPosY = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart_2)));
        setCurrentTemp(Constant.mSensorData.getCurrentTemp());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        heatBarPosX = centerX-heatThumbPosX;
        heatBarPosY = centerY-heatThumbPosY;

        coolBarPosX = centerX-coolThumbPosX;
        coolBarPosY = centerY-coolThumbPosY;
        heatPoint.set(heatBarPosX, heatBarPosY);
        coolPoint.set(coolBarPosX, coolBarPosY);
        switch (MODE){

            case VIEW_MODE:
                drawNormalView(canvas);
                LogUtil.d(TAG,"VIEW_MODE");
                break;
            case HEAT_MODE:
                drawNormalView(canvas);
                LogUtil.d(TAG,"HEAT_MODE");
                break;
            case COOL_MODE:
                drawNormalView(canvas);
                LogUtil.d(TAG,"COOL_MODE");
                break;
        }

    }

    private void drawNormalView(Canvas canvas){


        final int arcStart = mStartAngle + angleOffSet + 180;

        canvas.drawArc(mArcRect, arcStart, angle, false, normalPaint);
        canvas.drawArc(mArcRect, arcStart, mHeatSweep, false, heatPaint);
        canvas.drawArc(mArcRect, arcStart + angle, mClooSweep, false, coolPaint);
        canvas.drawLine(tempStart.x,tempStart.y,tempEnd.x,tempEnd.y,tempScalePaint);
        canvas.translate(heatBarPosX, heatBarPosY);
        heatThumb.draw(canvas);
        canvas.translate(coolBarPosX - heatBarPosX, coolBarPosY - heatBarPosY);
        coolThumb.draw(canvas);

    }


     protected void drawScale(Canvas canvas){
        int x,y;
        float arcStart;
        Point  start = null,end = null;
        arcStart = mStartAngle+ 180f + 90f;
        for (int i =0 ;i < 40;i++){
                if (i%5 == 0) {
                    LogUtil.e(TAG, "drawScale i:" + i);
                    LogUtil.e(TAG, "drawScale arcStart:" + arcStart);
                    x = (int) ((mArcRadius + 60) * Math.cos(Math.toRadians(arcStart)));
                    y = (int) ((mArcRadius + 60) * Math.sin(Math.toRadians(arcStart)));
                    start = new Point(centerX - x, centerY - y);
                    end = new Point(centerX + x, centerY + y);
                    canvas.drawLine(start.x, start.y, end.x, end.y, scalePaint);
                }
                arcStart += 270.0f/40;
        }
     }

  /*  private void drawTicks(Canvas paramCanvas) {


        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.background_arc_off_white));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        int iStart = 37;

        int iLGapEnd = 72;
        int iSGapEnd = 60;
        paint.setStrokeWidth(2.0F);
        int i = 0;

        int iTotalTicks = 31;
        int iMods = 5;

        while (i < iTotalTicks) {
            Point point;
            Point point1;
            if (i % iMods == 0)//long tick
            {
                point = tickPointForValue(i, iStart);
                point1 = tickPointForValue(i, iLGapEnd);
            } else //short tick
            {
                point = tickPointForValue(i, iStart);
                point1 = tickPointForValue(i, iSGapEnd);
            }
            paramCanvas.drawLine(centerX - point.x, centerY - point.y, centerX - point1.x, centerY - point1.y, paint);
            i++;
        }
    }

    private Point tickPointForValue(int paramInt1, int paramInt2)
    {
        float iTicks=30.0f;
        int i = (int)(90.0F + (180.0F + (45.0F + 270.0F * (paramInt1 / iTicks))));
        return new Point((int)((paramInt2 + this.mArcRadius) * Math.cos(Math.toRadians(i))), (int)((paramInt2 + this.mArcRadius) * Math.sin(Math.toRadians(i))));
    }*/

    protected void updateHeatPos(){
        float arcStart = mHeatSweep + mStartAngle+ 180 + 90 ;
        heatThumbPosX = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart)));
        heatThumbPosY = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart)));

    }

    protected void updateCoolPos(){
        float arcStart_2 = mClooSweep+mStartAngle+angle+180+90;
        coolThumbPosX = (int) (mArcRadius * Math.cos(Math.toRadians(arcStart_2)));
        coolThumbPosY = (int) (mArcRadius * Math.sin(Math.toRadians(arcStart_2)));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchPoint.set((int) event.getX(), (int) event.getY());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pointsAreClose(touchPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                if (MODE == HEAT_MODE){
                    updateHeatView(event);
                }else if (MODE == COOL_MODE){
                    updateCoolView(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                callBack.isMoving(false);
                break;


        }
        return true;
    }

    private void pointsAreClose(Point touchPoint)
    {
        if ((touchPoint.x < heatPoint.x+50)&&(touchPoint.x > heatPoint.x-50)&&(touchPoint.y < heatPoint.y+50)&&(touchPoint.y > heatPoint.y-50)){
            MODE = HEAT_MODE;
            LogUtil.e(TAG,"current mode == HEAT_MODE");
        }

        if ((touchPoint.x < coolPoint.x+50)&&(touchPoint.x > coolPoint.x-50)&&(touchPoint.y < coolPoint.y+50)&&(touchPoint.y > coolPoint.y-50)) {
            MODE = COOL_MODE;
            LogUtil.e(TAG, "current mode == COOL_MODE");
        }


    }
    private double mTouchHeatAngle;
    private double mTouchCoolAngle;
    private double getTouchDegrees(float xPos, float yPos) {
        float x = xPos - centerX;
        float y = yPos - centerY;
        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2)
                - Math.toRadians(180));
        if (angle < 0) {
            angle = 360 + angle;
        }
        angle -= 45;
        return angle;
    }

    protected void updateHeatView(MotionEvent event){

        mTouchHeatAngle = getTouchDegrees(event.getX(), event.getY());
        heatValue = getProgressFromAngle(mTouchHeatAngle);
        if (heatValue < 0){
            heatValue = 0;
        }else if (heatValue >38 ){
            heatValue = 38;
        }
        mHeatSweep = (float)heatValue / mMax * 270f;
        if ((270f+mClooSweep-mHeatSweep) < 2F/mMax * 270F){
            mClooSweep +=  1F/mMax * 270F;
            if (mClooSweep >= 0){
                mHeatSweep = 270f-2F/mMax * 270F;
                mClooSweep = 0;
            }
            coolValue = (int) (mClooSweep* mMax / 270F);
            if (callBack!=null){
                callBack.coolCallBack(40+coolValue);
            }
            updateCoolPos();
        }
        if (callBack!=null){
            callBack.heatCallBack(heatValue);
        }
        updateHeatPos();
        invalidate();
    }

    protected void updateCoolView(MotionEvent event){
        mTouchCoolAngle =getTouchDegrees(event.getX(),event.getY());
        coolValue =40-getProgressFromAngle(mTouchCoolAngle);
        if (coolValue < 0){
            coolValue = 0;
        }else if (coolValue >38 ){
            coolValue = 38;
        }
        mClooSweep = 0-(float)coolValue / mMax * 270f;
        if ((270f+mClooSweep-mHeatSweep) < 2F/mMax * 270F){
            mHeatSweep -=  1F/mMax * 270F;
            if (mHeatSweep <= 0){
                mClooSweep = 0-(270f-2F/mMax * 270F);
                mHeatSweep = 0;
            }
            heatValue = (int) (mHeatSweep* mMax / 270F);
            if (callBack!=null){
                callBack.heatCallBack(heatValue);
            }
            updateHeatPos();
        }
        if (callBack != null){
            callBack.coolCallBack(40-coolValue);
        }
        updateCoolPos();
        invalidate();
    }

    protected int getProgressFromAngle(double angle){
        int progress = (int) Math.round(angle* 40/270);
        return progress;
    }

    public interface OnSeekBarCallBack{

        public void heatCallBack(int HeatValue);

        public void coolCallBack(int CoolValue);

        public void isMoving(boolean up);
    }

    private OnSeekBarCallBack callBack;

    public void setOnSeekBarCallBack(OnSeekBarCallBack c){
        this.callBack = c;
    }


   public void setHeatValue(int value){
       heatValue = value;
       mHeatSweep = (float)heatValue / mMax * 270;
       updateHeatPos();
       invalidate();
   }
    public void setCoolValue(int value){
        coolValue = value;
        mClooSweep = 0- (float)coolValue / mMax * 270;
        updateCoolPos();
        invalidate();
    }

    public void setCurrentTemp(int value){
        currentTemp = value;
        float tempArc = (float)currentTemp / mMax * 270;
        float tempPos = tempArc + mStartAngle+ 180 + 90 ;
        tempStart.set(centerX-(int)((mArcRadius+40)* Math.cos(Math.toRadians(tempPos))),
                centerY-(int) ((mArcRadius+40) * Math.sin(Math.toRadians(tempPos))));
        tempEnd.set(centerX - (int) ((mArcRadius - 40) * Math.cos(Math.toRadians(tempPos))),
                centerY - (int) ((mArcRadius - 40) * Math.sin(Math.toRadians(tempPos))));
        invalidate();
    }


}
