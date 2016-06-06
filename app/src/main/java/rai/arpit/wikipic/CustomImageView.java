package rai.arpit.wikipic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Rai_Sahab on 6/4/2016.
 */
public class CustomImageView extends ImageView{

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //height hard coded=350
        setMeasuredDimension(getMeasuredWidth(),350);
    }
}
