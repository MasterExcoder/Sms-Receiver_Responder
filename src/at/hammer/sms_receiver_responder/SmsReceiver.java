package at.hammer.sms_receiver_responder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	
	public SmsReceiver() {
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];

		String msg = "";
		int smsPieces = messages.length;

		for (int i = 0; i < smsPieces; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			// grab all pieces of the intercepted sms
			msg += "\n" + (i + 1) + " -of- " + smsPieces + "\n" + "Sender:\t"
					+ smsMessage[i].getOriginatingAddress() + "\n"
					+ "Body: \n " + smsMessage[i].getMessageBody();
		}

		// show first part of intercepted (current) message
		Log.d("SMS", "Received SMS: " + msg);
		Toast toast = Toast.makeText(context, "Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		toast.show();
		//check if textview reference is null
		if(MainActivity.textview_smsText != null) {
			MainActivity.textview_smsText.setText(smsMessage[0].getMessageBody());
		} else {
			Log.d("SmsReceiver", "ERROR");
		}
	}

}
