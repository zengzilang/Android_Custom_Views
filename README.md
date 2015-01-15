# Android_Custom_Views
Creating your custom View class
Declaring a custom View is much similar to declaring any other class in Java. While designing an custom class keep the following things in mind

It should provide some easy to use interface and should use the memory efficiently.
A Custom View should conform to Android standards
You can provide custom styleable attributes that can be configurable from Android XML layouts
Your View class should be compatible with multiple Android platforms
All the default View classes that are available in android extends View. The same way your custom View can directly extend the View class or you may extend one of the default View controls such as TextView. Now, override the constructor that takes a Context and an AttributeSet object as parameters.

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LovelyView extends LinearLayout {

	public LovelyView(Context context) {
		super(context);		
	}

	public LovelyView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	public LovelyView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);		
	}	
}

4. Define Custom Attributes
You can provide custom styleable attributes that can be configurable from Android XML layouts. These attributes will control its appearance and behavior of View. You can create a values/attr.xml file in your project. To enable this behavior in your custom view, you can define custom attributes for your view in a resource element in attr.xml file.

<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="LovelyView">
        <attr name="leftLabel" format="string" />
        <attr name="rightLabel" format="string" />
        <attr name="leftLabelStyle" format="reference" />
        <attr name="rightLabelStyle" format="reference" />
    </declare-styleable>

</resources>
In the above xml file, LovelyView is the name of the style. and leftLabel, rightLabel, leftLabelStyle and rightLabelStyleare the style attributes defines.

Once you define the custom attributes, you can use them in layout XML files just like built-in attributes. The only difference is that your custom attributes belong to a different namespace. Instead of belonging to the http://schemas.android.com/apk/res/android namespace, they belong to http://schemas.android.com/apk/res/(your package name)

Here’s how to use the attributes defined for LovelyView

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:custom="http://schemas.android.com/apk/res/com.example.customview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="10dp"
    android:orientation="vertical"
    tools:context=".MainActivity" >

    <com.example.customview.LovelyView
        android:id="@+id/lovelyView"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        custom:leftLabel=""
        custom:leftLabelStyle="@style/leftTextStyle"
        custom:rightLabel=""
        custom:rightLabelStyle="@style/rightTextStyle" />

    <com.example.customview.LovelyView
        android:id="@+id/lovelyView1"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        custom:leftLabel="Hello Mr:"
        custom:leftLabelStyle="@style/leftTextStyle"
        custom:rightLabel="Whats Up Buddy!"
        custom:rightLabelStyle="@style/rightTextStyle" />

</LinearLayout>
Notice that, In the above code snippet, we are using the fully qualified name of the custom view class. If your view class is an inner class, you must further qualify it with the name of the view’s outer class.

5. Apply Custom Attributes
In the above two steps, we have configured the View stylable attributes in the XML. When a view is created from an XML layout, all of the attributes in the XML tag are read from the resource bundle and passed into the view’s constructor as an AttributeSet. We can read the attributes by calling obtainStyledAttributes() method.

TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
		R.styleable.LovelyView, 0, 0);
try {
	// get the text and colors specified using the names in attrs.xml
	leftLabel = a.getString(R.styleable.LovelyView_leftLabel);
	rightLabel = a.getString(R.styleable.LovelyView_rightLabel);
	leftStyle = a.getResourceId(R.styleable.LovelyView_leftLabelStyle, android.R.style.TextAppearance_DeviceDefault);
	rightStyle = a.getResourceId(R.styleable.LovelyView_rightLabelStyle, android.R.style.TextAppearance_DeviceDefault);

} finally {
	a.recycle();
}

LayoutInflater.from(context).inflate(R.layout.key_value_layout, this);

//left text view
leftTextView = (TextView) this.findViewById(R.id.leftTextView);
leftTextView.setText(leftLabel);
leftTextView.setTextAppearance(context, leftStyle);

//right text view
rightTextView = (TextView) this.findViewById(R.id.rightTextView);
rightTextView.setText(rightLabel);
rightTextView.setTextAppearance(context, rightStyle);
Below is the complete View class code

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LovelyView extends LinearLayout {
	private String leftLabel = "";
	private String rightLabel = "";
	private TextView leftTextView;
	private TextView rightTextView;
	private int leftStyle ;
	private int rightStyle;

	public LovelyView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.key_value_layout, this);
	}

	public LovelyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
	}

	public LovelyView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
		initViews(context, attrs);
	}

	private void initViews(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.LovelyView, 0, 0);

		try {
			// get the text and colors specified using the names in attrs.xml
			leftLabel = a.getString(R.styleable.LovelyView_leftLabel);
			rightLabel = a.getString(R.styleable.LovelyView_rightLabel);
			leftStyle = a.getResourceId(R.styleable.LovelyView_leftLabelStyle, android.R.style.TextAppearance_DeviceDefault);
			rightStyle = a.getResourceId(R.styleable.LovelyView_rightLabelStyle, android.R.style.TextAppearance_DeviceDefault);

		} finally {
			a.recycle();
		}

		LayoutInflater.from(context).inflate(R.layout.key_value_layout, this);

		//left text view
		leftTextView = (TextView) this.findViewById(R.id.leftTextView);
		leftTextView.setText(leftLabel);
		leftTextView.setTextAppearance(context, leftStyle);

		//right text view
		rightTextView = (TextView) this.findViewById(R.id.rightTextView);
		rightTextView.setText(rightLabel);
		rightTextView.setTextAppearance(context, rightStyle);
	}

	public String getLeftLabel() {
		return leftLabel;
	}

	public void setLeftLabel(String leftLabel) {
		this.leftLabel = leftLabel;
		if(leftTextView!=null){
			leftTextView.setText(leftLabel);
		}
	}

	public String getRightLabel() {
		return rightLabel;
	}

	public void setRightLabel(String rightLabel) {
		this.rightLabel = rightLabel;
		if(rightTextView!=null){
			rightTextView.setText(rightLabel);
		}
	}
}

6. Download Complete Example
Here you can download complete eclipse project source code from GitHub.

Download Complete Source Code from GitHub


7. Output
Custom and Compound Views in Android
