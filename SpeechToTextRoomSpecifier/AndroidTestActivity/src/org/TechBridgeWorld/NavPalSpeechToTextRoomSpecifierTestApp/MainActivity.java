package org.TechBridgeWorld.NavPalSpeechToTextRoomSpecifierTestApp;

import com.example.androidtestactivity.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private static String TAG = "MainTestActivity";

    private static String APP_NAME = "org.TechBridgeWorld.NavPalSpeechToTextRoomSpecifierTestApp";

    private static String VOICE_RECOGNITION_PACKAGE  = "org.TechBridgeWorld.NavPalSpeechToTextRoomSpecifier";
    private static String VOICE_RECOGNITION_ACTIVITY = "org.TechBridgeWorld.NavPalSpeechToTextRoomSpecifier.VoiceRecognitionActivity";
    
    private static final int VOICE_RECOGNITION_APP_CHECK_CODE = 100;

    Context context;
    PackageManager packageManager;

    TextView sourceTextView;
    TextView goalTextView;

    int _x1, _y1, _x2, _y2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	Log.d(TAG, "onCreate Entered.");

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	sourceTextView = (TextView) findViewById(R.id.textViewSourceXY);
	goalTextView = (TextView) findViewById(R.id.textViewDestXY);

	sourceTextView.setText("Undefined");
	goalTextView.setText("Undefined");

	Log.d(TAG, "onCreate Exiting.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }

    public void launch(View view)
    {
	Log.d(TAG, "launch Entered.");

	Log.d(TAG, "Launching activity '" + APP_NAME + "'.");

	// To test the voice recognition activity
	//Intent intent = pm.getLaunchIntentForPackage(VOICE_RECOGNITION_ACTIVITY);
	Intent intent = new Intent();
	intent.setClassName(VOICE_RECOGNITION_PACKAGE, VOICE_RECOGNITION_ACTIVITY);	
	startActivityForResult(intent, VOICE_RECOGNITION_APP_CHECK_CODE);

	Log.d(TAG, "launch Exited.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent idata)
    {
	super.onActivityResult(requestCode, resultCode, idata);

	Log.d(TAG, "onActivityResult Entered.");

	Log.d(TAG, "requestCode is " + requestCode + " and resultCode is " + resultCode);
	Log.d(TAG, "VOICE_RECOGNITION_APP_CHECK_CODE is " + VOICE_RECOGNITION_APP_CHECK_CODE + " and RESULT_OK == " + RESULT_OK);

	Log.d(TAG, "requestCode == VOICE_RECOGNITION_APP_CHECK_CODE: " + (requestCode == VOICE_RECOGNITION_APP_CHECK_CODE));
	Log.d(TAG, "resultCode == RESULT_OK: " + (resultCode == RESULT_OK));

	if (requestCode == VOICE_RECOGNITION_APP_CHECK_CODE)
	{
	    if (resultCode == RESULT_OK)
	    {
		Log.d(TAG, "Request code and result were OK, proceeding to extract data.");
		Bundle bundleExtras = idata.getExtras();

		if (bundleExtras == null)
		{
		    Log.d(TAG, "'bundleExtras' is NULL. No data to retrieve.");
		    return;
		}

		Log.d(TAG, "'bundleExtras' is defined. Attempting to retrieve data.");

		boolean canceled = bundleExtras.getBoolean("canceled");
		int[] points = bundleExtras.getIntArray("points");
		
		Log.d(TAG, "Data 'canceled' with value " + canceled + " extracted from intent.");
		Log.d(TAG, "Size of Points array received from returning activity: " + (points == null ? 0:points.length));

		if (points == null)
		{
		    Log.d(TAG, "Points array received from returning activity is null. Exiting since there is no data.");
		    return;
		}

		// Check if the array has 4 elements representing x1, y1, x2, y2
		if (points.length == 4)
		{
		    _x1 = points[0];
		    _y1 = points[1];
		    _x2 = points[2];
		    _y2 = points[3];
		}
		else
		{
		    _x1 = -1;
		    _y1 = -1;
		    _x2 = -1;
		    _y2 = -1;
		}

		sourceTextView.setText("(" + _x1 + ", " + _y1 + ")");
		goalTextView.setText("(" + _x2 + ", " + _y2 + ")");
	    }
	}

	Log.d(TAG, "onActivityResult Exited.");
    }
}
