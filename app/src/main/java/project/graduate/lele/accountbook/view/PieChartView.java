package project.graduate.lele.accountbook.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import java.text.DecimalFormat;
import java.util.List;
import project.graduate.lele.accountbook.bean.TotalPayBean;
import project.graduate.lele.accountbook.utils.ScreenUtils;

/**
 *
 */
public class PieChartView extends View {

    private int screenW, screenH;

    /**
     * 画笔去 画 文字 扇形 线
     */
    private Paint textPaint, piePaint, linePaint, midPaint;

    private Context context;

    /**
     * 扇形的 圆心 坐标 半径
     */
    private int pieCenterX, pieCenterY, pieRadius;

    /**
     * 矩形
     */
    private RectF pieOval, rectF;

    private float Margin_10;

    /*
     * 传进来的扇形参数
     */
    private List<TotalPayBean> mPieItems;

    private double totalValue;

    /**
     * 动画
     */
    private ChartAnimator mAnimator;

    private Path path = new Path();

    public String midString = "总支出";

    public PieChartView(Context context) {
        super(context);

        init(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        this.context = context;

        if (android.os.Build.VERSION.SDK_INT < 11) {
            mAnimator = new ChartAnimator();
        } else {
            mAnimator = new ChartAnimator(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    postInvalidate();
                }

            });
        }

        // 获取屏幕的 宽高
        screenW = ScreenUtils.getScreenW(context);
        screenH = ScreenUtils.getScreenH(context);
        // 计算圆心
        pieCenterX = screenW / 3;
        pieCenterY = screenH / 5;
        // 计算半径（扇形）
        pieRadius = screenW / 5;
        Margin_10 = ScreenUtils.dp2px(context, 10);
        pieOval = new RectF();
        pieOval.left = pieCenterX - pieRadius;
        pieOval.top = pieCenterY - pieRadius;
        pieOval.right = pieCenterX + pieRadius;
        pieOval.bottom = pieCenterY + pieRadius;
        // 圆角矩形背景
        rectF = new RectF();

        // The paint to draw text.
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(ScreenUtils.dp2px(context, 13f));

        // The paint to draw circle.
        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL);

        // The paint to draw line to show the concrete text
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(ScreenUtils.dp2px(context, 0.5f));

        midPaint = new Paint();

    }

    // The degree position of the last item arc's center.
    private float lastDegree = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        piePaint.setColor(Color.parseColor("#f1f1f1"));
        // 画最底部园
        canvas.drawCircle(pieCenterX, pieCenterY, pieRadius + 15, piePaint);
        // 起始的角度
        float start = 0;

        if (mPieItems != null && mPieItems.size() > 0) {
            // 获取每项对于的文字
            String TypeText = mPieItems.get(0).getName();
            // 获取文字的高度
            FontMetrics fm = textPaint.getFontMetrics();
            float TypeTextLen = (float) Math.ceil(fm.descent - fm.ascent);
            // 计算百分比文字在Y轴最上部的起始绘制坐标
            float txtY = pieCenterY
                    - (mPieItems.size() * TypeTextLen + (mPieItems.size() - 1)
                    * Margin_10) / 2 + TypeTextLen / 2;

            // 遍历
            for (int i = 0; i < mPieItems.size(); i++) {
                // 设置画笔颜色
                piePaint.setColor(Color.parseColor(mPieItems.get(i).getColor()));
                textPaint.setColor(Color.parseColor("#333333"));
                linePaint.setColor(Color.parseColor(mPieItems.get(i).getColor()));
                textPaint.setAlpha((int) (255 * mAnimator.getPhaseX()));

                float sweep = (float) ((mPieItems.get(i).getTotal())
                        / totalValue * 360);
                // 画 扇形
                canvas.drawArc(pieOval, start * mAnimator.getPhaseX(), sweep
                        * mAnimator.getPhaseY(), true, piePaint);

                // 获取每项对于的文字
                String itemTypeText = mPieItems.get(i).getName() + ":";
                // 获取百分比
                String percent = formatFloat(mPieItems.get(i).getPercent() * 100);
                String itemPercentText = (percent) + "%";

                float left = pieCenterX + pieRadius + 3 * Margin_10;
                float top = txtY + i * (TypeTextLen + Margin_10) - (float) 1.2
                        * Margin_10 + (float) 0.2 * Margin_10;
                float right = pieCenterX + pieRadius + 3 * Margin_10
                        + (float) 1.2 * Margin_10;
                float bottom = txtY + i * (TypeTextLen + Margin_10)
                        + (float) 0.2 * Margin_10;
                // // 绘制圆角矩形
                rectF.left = left;
                rectF.top = top;
                rectF.right = right;
                rectF.bottom = bottom;
                //piePaint.setAlpha((int) (255 * mAnimator.getPhaseX()));
                canvas.drawRoundRect(rectF, 10, 10, piePaint);
                // 绘制类型比例文字
                canvas.drawText(itemTypeText + "   " + itemPercentText, left
                        + 2 * Margin_10, txtY + i
                        * (TypeTextLen + Margin_10), textPaint);

                linePaint.setAlpha((int) (255 * mAnimator.getPhaseX()));
                canvas.drawPath(path, linePaint);

                lastDegree = start + sweep / 2;
                start += sweep;
            }
        }
        // 画内嵌圆
        piePaint.setColor(Color.parseColor("#4c7F7F7F"));
        canvas.drawCircle(pieCenterX, pieCenterY, pieRadius / 3 * 2,
                piePaint);
        // // 画内嵌圆
        // piePaint.setColor(Color.parseColor("#ffffff"));
        // canvas.drawCircle(pieCenterX, pieCenterY, pieRadius / 3 * 2 - 15,
        // piePaint);
        piePaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(pieCenterX, pieCenterY, pieRadius / 3 * 2 - Margin_10 / 2,
                piePaint);

        midPaint.setColor(Color.parseColor("#333333"));
        midPaint.setTextSize(ScreenUtils.dp2px(context, 13f));
        // 画中间文字
        canvas.drawText(
                midString,
                pieCenterX
                        - midPaint.measureText(midString.substring(0,
                        midString.length() / 2)), pieCenterY + 10,
                midPaint);

    }

    public List<TotalPayBean> getPieItems() {
        return mPieItems;
    }

    public void setPieItems(List<TotalPayBean> pieItems) {
        this.mPieItems = pieItems;
        if (pieItems.size() > 0) {
            totalValue = pieItems.get(0).getTotalPay();
        }
        invalidate();
    }


    public void AnimalY(int durationMillis, TimeInterpolator interpolator) {
        mAnimator.animateY(durationMillis, interpolator);
    }

    public void AnimalX(int durationMillis, TimeInterpolator interpolator) {
        mAnimator.animateX(durationMillis, interpolator);
    }

    public void AnimalXY(int durationMillisX, int durationMillisY,
                         TimeInterpolator interpolator) {
        mAnimator.animateXY(durationMillisX, durationMillisY, interpolator);
    }

    public String formatFloat(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

}
