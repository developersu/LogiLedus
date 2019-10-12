package logiled.USB;

import logiled.MessagesConsumer;
import org.usb4java.*;

public class UsbConnect {
    private final int DEFAULT_INTERFACE = 1;

    private Context contextKbrd;
    private DeviceHandle handlerKbrd;

    private boolean connected = false;
    
    public UsbConnect(){
        MessagesConsumer mc = MessagesConsumer.getInstance();

        int result;

        // Creating Context required by libusb. Optional. TODO: Consider removing.
        contextKbrd = new Context();
        result = LibUsb.init(contextKbrd);
        if (result != LibUsb.SUCCESS) {
            mc.inform("'libusb' initialization failed: "+result);
            close();
            return;
        }

        // Searching for NS in devices: obtain list of all devices
        DeviceList deviceList = new DeviceList();
        result = LibUsb.getDeviceList(contextKbrd, deviceList);
        if (result < 0) {
            mc.inform("Getting device list failed: "+result);
            close();
            return;
        }
        // Searching for keyboard in devices
        DeviceDescriptor descriptor;
        Device deviceKbrd = null;
        for (Device device: deviceList){
            descriptor = new DeviceDescriptor();                // mmm.. leave it as is.
            result = LibUsb.getDeviceDescriptor(device, descriptor);
            if (result != LibUsb.SUCCESS){
                mc.inform("Read file descriptors for USB devices failed: "+result);
                LibUsb.freeDeviceList(deviceList, true);
                close();
                return;
            }
            if ((descriptor.idVendor() == (short) 0x046D) && (descriptor.idProduct() == (short) 0xC33C)){
                deviceKbrd = device;
                break;
            }
        }
        if (deviceKbrd == null){
            mc.inform("Keyboard is not found in connected devices.");
            close();
            return;
        }
        // Handle keyboard device
        handlerKbrd = new DeviceHandle();
        result = LibUsb.open(deviceKbrd, handlerKbrd);
        if (result != LibUsb.SUCCESS) {
            mc.inform("Open keyboard failed : "+UsbErrorCodes.getErrCode(result)+(result == LibUsb.ERROR_ACCESS?" (not enough rights)":""));
            // Let's make a bit dirty workaround since such shit happened
            LibUsb.exit(contextKbrd);
            return;         // And close
        }

        LibUsb.freeDeviceList(deviceList, true);

        // Check if this device uses kernel driver and detach if possible:
        result = LibUsb.kernelDriverActive(handlerKbrd, DEFAULT_INTERFACE);
        if (result == 1) {      // used by kernel
            if (LibUsb.detachKernelDriver(handlerKbrd, DEFAULT_INTERFACE) != LibUsb.SUCCESS) {
                mc.inform("Detach kernel failed: " + UsbErrorCodes.getErrCode(result));
                close();
                return;
            }
        }
        else if (result != LibUsb.SUCCESS)
            mc.inform("Can't proceed with libusb driver : "+UsbErrorCodes.getErrCode(result));

        // Claim interface
        result = LibUsb.claimInterface(handlerKbrd, DEFAULT_INTERFACE);
        if (result != LibUsb.SUCCESS) {
            mc.inform("Claim interface failure: "+UsbErrorCodes.getErrCode(result));
            close();
            return;
        }
        else
            mc.inform("Interface claimed");

        this.connected = true;
    }

    /**
     * Get USB status
     * @return status of connection
     */
    public boolean isConnected() { return connected; }
    /**
     * Getter for handler
     * @return DeviceHandle of Keyboard
     */
    public DeviceHandle getHandlerKbrd(){ return handlerKbrd; }
    /**
     * Correct exit
     * */
    public void close(){
        // Close handler in the end
        if (handlerKbrd != null) {
            // Try to release interface
            LibUsb.releaseInterface(handlerKbrd, DEFAULT_INTERFACE);
            LibUsb.close(handlerKbrd);
        }
        // Close context in the end
        if (contextKbrd != null)
            LibUsb.exit(contextKbrd);
    }
}
