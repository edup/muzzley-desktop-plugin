package com.example.muzzley;

import org.json.JSONObject;

import com.muzzley.sdk.appliance.MZActivity;
import com.muzzley.sdk.appliance.Participant;

public interface MZActivityListener {
	//public void participantWidgetReady();
	public void participantSignalReceived(Participant participant, JSONObject msg);
	public void onActivityCreated(MZActivity mzActivity);
}
