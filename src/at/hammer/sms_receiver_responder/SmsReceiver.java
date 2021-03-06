package at.hammer.sms_receiver_responder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
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
		String number = "";
		
		int smsPieces = messages.length;

		for (int i = 0; i < smsPieces; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			// grab all pieces of the intercepted sms
			msg += "\n" + (i + 1) + " -of- " + smsPieces + "\n" + "Sender:\t"
					+ smsMessage[i].getOriginatingAddress() + "\n"
					+ "Body: \n " + smsMessage[i].getMessageBody();
		}

		msg = smsMessage[0].getMessageBody();
		number = smsMessage[0].getOriginatingAddress();
		
		// show first part of intercepted (current) message
		Log.d("SMS", "Received SMS: " + msg);
		Toast toast = Toast.makeText(context, "Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		toast.show();
		
		//check if textview reference is null and add received Message
		if(MainActivity.textview_smsText != null) {
			MainActivity.textview_smsText.setText(MainActivity.textview_smsText.getText() + "\n" + number + ": " + msg);
		} else {
			Log.d("SmsReceiver", "ERROR");
		}
		
		//TextToSpeech the message
		MainActivity.tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
		
		sendReplyMessage(number, "This is an automatic reply message!");
		Log.d("reply message", "reply message sent");
	}
	
	public void sendReplyMessage(String number, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number, null, message, null, null);
	}

}
