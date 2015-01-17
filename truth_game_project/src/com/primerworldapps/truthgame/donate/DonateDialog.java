package com.primerworldapps.truthgame.donate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.primerworldapps.truthgame.R;

public class DonateDialog {

	public static void show(Activity activity) {
		new AlertDialog.Builder(activity)
				.setTitle(activity.getString(R.string.dialog_donate_title))
				.setMessage(
						activity.getString(R.string.dialog_donate_text) + activity.getString(R.string.dialog_donate_card) + activity.getString(R.string.dialog_donate_name)
								+ activity.getString(R.string.dialog_donate_thanks)).setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				}).setIcon(R.drawable.ic_donate).show();
	}
}
