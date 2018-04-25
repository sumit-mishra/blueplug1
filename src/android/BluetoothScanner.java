package cordova.plugin.bluetoothscanner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.content.Intent;
import android.content.Context;
import android.content.ContextWrapper;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothDevice; 
import android.os.Handler;
import android.bluetooth.BluetoothAdapter; 

import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.uk.tsl.rfid.asciiprotocol.AsciiCommander;
import com.uk.tsl.rfid.asciiprotocol.BluetoothReaderService;
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
			 final Context context = that.cordova.getActivity().getBaseContext();
			 AsciiCommander commander = new AsciiCommander(that.cordova.getActivity().getBaseContext());
			 
			// Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), CaptureActivity.class);
			
			
			BluetoothManager  bluetoothManagerObj = (BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
                 BluetoothAdapter bluetoothAdapterObj = null;
                 
                 //bluetoothAdapterObj = bluetoothManagerObj.getDefaultAdapter();
                 
                 if(bluetoothAdapterObj==null){
                    bluetoothAdapterObj =  bluetoothManagerObj.getAdapter();
                 }
                 
                 Boolean status = bluetoothAdapterObj.startDiscovery();
                 
                 Set<BluetoothDevice> listOfBondedDevices = bluetoothAdapterObj.getBondedDevices();
				 String deviceDetails = "";
				 for(BluetoothDevice device : listOfBondedDevices){
					 String address = device.getAddress();
					 BluetoothDevice bluetoothDeviceObj = bluetoothAdapterObj.getRemoteDevice(address);
					 String addressValue = bluetoothDeviceObj.getAddress();
					 int state = bluetoothDeviceObj.getBondState();
					 String name = bluetoothDeviceObj.getName();
					 int type = bluetoothDeviceObj.getType();
					 "Device Detials - "+ deviceDetails+" - Address - " + address + " - State - " + state + " - name - " + name + " - type - " + type;
				 }
				 
				 callbackContext.success(message + " connnected device name : " + commander.getConnectedDeviceName()+ "- Device Reader - "+ new BluetoothReaderService(new Handler()) + " Device  - "+deviceDetails);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
