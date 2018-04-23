package cordova.plugin.bluetoothscanner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.uk.tsl.rfid.asciiprotocol.AsciiCommander;
/**
 * This class echoes a string called from JavaScript.
 */
public class BluetoothScanner extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
			 final CordovaPlugin that = this;
			 AsciiCommander commander = new AsciiCommander(that.cordova.getActivity().getBaseContext());
			 
			// Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), CaptureActivity.class);
			callbackContext.success(message + " connnected device name : " + commander.getConnectedDeviceName());
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
