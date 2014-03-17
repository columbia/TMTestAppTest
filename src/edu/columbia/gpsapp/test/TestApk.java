package edu.columbia.gpsapp.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

@SuppressWarnings("unchecked")
public class TestApk extends ActivityInstrumentationTestCase2 {
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "edu.columbia.gpsapp.MainActivity";
	private static Class launcherActivityClass;
	static {
		try {
			launcherActivityClass = Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public TestApk() throws ClassNotFoundException {
		super(launcherActivityClass);
	}

	private Solo solo;

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testDisplayBlackBox() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException
	{
		try {
			Socket client = new Socket("localhost", 50000);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
	        BufferedReader in = new BufferedReader(new InputStreamReader(inFromServer));

			out.writeUTF("runover TMIMSIService\n");
			//while (in.readLine() != null) ;
			out.writeUTF("disc\n");
			//while (in.readLine() != null) ;
			client.close();

		} catch (IOException e) {

		}
		solo.waitForDialogToOpen(3000);
		solo.clickOnButton(1);
		solo.clickOnButton(0);
		solo.clickOnButton(0);
	}
	
}
