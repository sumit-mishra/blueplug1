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
import com.uk.tsl.rfid.asciiprotocol.commands.BatteryStatusCommand;
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
		try{
			if (message != null && message.length() > 0) {
			 
			 final CordovaPlugin that = this;
			 final Context context = that.cordova.getActivity().getBaseContext();
			 AsciiCommander commander = new AsciiCommander(that.cordova.getActivity().getBaseContext());
			 
			// Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), CaptureActivity.class);
			
			BatteryStatusCommand batteryStatusCommand = new BatteryStatusCommand();
				
			
			BluetoothManager  bluetoothManagerObj = (BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
                 BluetoothAdapter bluetoothAdapterObj = null;
                 
                 //bluetoothAdapterObj = bluetoothManagerObj.getDefaultAdapter();
                 
                 if(bluetoothAdapterObj==null){
                    bluetoothAdapterObj =  bluetoothManagerObj.getAdapter();
                 }
                 
                 Boolean status = bluetoothAdapterObj.startDiscovery();
                 
                 Set<BluetoothDevice> listOfBondedDevices = bluetoothAdapterObj.getBondedDevices();
				 String deviceDetails = "";
				 BluetoothDevice dev = null;
				 for(BluetoothDevice device : listOfBondedDevices){
					 dev = device;
					 String address = device.getAddress();
					 BluetoothDevice bluetoothDeviceObj = bluetoothAdapterObj.getRemoteDevice(address);
					 String addressValue = bluetoothDeviceObj.getAddress();
					 int state = bluetoothDeviceObj.getBondState();
					 String name = bluetoothDeviceObj.getName();
					 int type = bluetoothDeviceObj.getType();
					 
					 deviceDetails = deviceDetails+" - Address - " + address + " - State - " + state + " - name - " + name + " - type - " + type;
				 }
				 
				 BluetoothReaderService buletoothReader = new BluetoothReaderService(new Handler());
				 commander.connect(dev);
				 boolean con = buletoothReader.connect(dev, true);
				 //commander.executeCommand(batteryStatusCommand);
				 
				 callbackContext.success("Connection Success - " +commander.hasConnectedSuccessfully() + " - Connected - " +commander.isConnected() + "- Device Responsive - "+	commander.isResponsive() + " Device - "+commander.getConnectedDeviceName() );
				 
				 /*callbackContext.success(message + " connnected device name : " + commander.getConnectedDeviceName()+ "- Device Reader - "+ new BluetoothReaderService(new Handler()) + " Device  - "+ deviceDetails + " device connection - " + con + " - Battery Level - "+batteryStatusCommand.getBatteryLevel()+ " - Battery Charging state - "+batteryStatusCommand.getChargeStatus() );*/
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
		
		}catch(Exception e){
			callbackContext.error(e.getMessage());
		}
        
    }
}
