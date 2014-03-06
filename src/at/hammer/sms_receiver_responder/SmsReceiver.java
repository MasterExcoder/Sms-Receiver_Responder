package at.hammer.sms_receiver_responder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];

		String msg = "";
		int smsPieces = messages.length;

		for (int n = 0; n < smsPieces; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
			// grab all pieces of the intercepted sms
			msg += "\n" + (n + 1) + " -of- " + smsPieces + "\n" + "Sender:\t"
					+ smsMessage[n].getOriginatingAddress() + "\n"
					+ "Body: \n " + smsMessage[n].getMessageBody();
		}

		// show first part of intercepted (current) message
		Toast toast = Toast.makeText(context, "FANCY >>> Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		toast.show();
		MainActivity.textview_smsText.setText(msg);
	}

}
