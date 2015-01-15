package com.cloud4family.activities.customview;

import com.cloud4family.activities.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LovelyView extends LinearLayout{
	private String leftLabel = "";
	private String rightLabel = "";
	private TextView leftTextView, rightTextView;
	private int leftStyle, rightStyle;
	private Context mContext;

	public LovelyView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.key_value_layout, this);
	}

	public LovelyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initViews(context, attrs);
	}

	public LovelyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initViews(context, attrs);
	}

	private void initViews(Context context, AttributeSet attrs) {
		TypedArray typeArr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView, 0, 0);
		
		try {
			// get the text and colors specified using the names in attrs.xml
			leftLabel = typeArr.getString(R.styleable.LovelyView_leftLabel);
			rightLabel = typeArr.getString(R.styleable.LovelyView_rightLabel);
			
			leftStyle = typeArr.getResourceId(R.styleable.LovelyView_leftLabelStyle, android.R.style.TextAppearance_DeviceDefault);
			rightStyle = typeArr.getResourceId(R.styleable.LovelyView_rightLabelStyle, android.R.style.TextAppearance_DeviceDefault);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			typeArr.recycle();
		}
		
		LayoutInflater.from(context).inflate(R.layout.key_value_layout, this);
		
		//Left Text View
		leftTextView = (TextView) this.findViewById(R.id.leftTextView);
		leftTextView.setText(leftLabel);
		leftTextView.setTextAppearance(context, leftStyle);
		
		//Right Text View
		rightTextView = (TextView) this.findViewById(R.id.rightTextView);
		rightTextView.setText(rightLabel);
		rightTextView.setTextAppearance(context, rightStyle);
	}

	public String getLeftLabel() {
		return leftLabel;
	}

	public void setLeftLabel(String leftLabel) {
		this.leftLabel = leftLabel;
		if(leftTextView != null) {
			leftTextView.setText(leftLabel);
		}
	}

	public String getRightLabel() {
		return rightLabel;
	}

	public void setRightLabel(String rightLabel) {
		this.rightLabel = rightLabel;
		if(rightTextView != null) {
			rightTextView.setText(rightLabel);
		}
	}
}