package expo.modules.bashgmuhce

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ___BashgmuHCEModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener, Module() {
    override fun definition() = ModuleDefinition {
        Name("BashgmuHCE")

        Function("supportNFC") { map: WritableMap ->
            return@Function map
        }

        Function("listenNFCStatus") { map: WritableMap ->
            return@Function map
        }

        Function("hello") {
            "Hello world! 123 👋"
        }

        Function("setCardContent") { value: String ->
            return@String value
        }

        Function("setAidFilter") { value: String ->
            return@String value
        }

        View(BashgmuHCEView::class) {
            // Defines a setter for the `name` prop.
            Prop("name") { view: BashgmuHCEView, prop: String ->
                println(prop)
            }
        }
    }

    private val reactContext: ReactApplicationContext = reactContext

    init {
        reactContext.addLifecycleEventListener(this)
        val filter = IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)
        reactContext.registerReceiver(mReceiver, filter)
    }
    override fun getName() = "BashgmuHCE";

    private fun supportNFC(): WritableMap {
        val manager = this.reactContext.getSystemService(this.reactContext.NFC_SERVICE) as NfcManager
        val adapter = manager.defaultAdapter
        val map = Arguments.createMap()
        if (adapter != null) {
            map.putBoolean("supported", true)
            map.putBoolean("enabled", adapter.isEnabled)
        } else {
            map.putBoolean("supported", false)
            map.putBoolean("enabled", false)
        }
        return map
    }

    fun sendEvent(reactContext: ReactContext, eventName: String, payload: WritableMap?) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, payload)
    }
    @ReactMethod
    fun setCardContent(value: String) {
        RNStorage.setValue(this.reactContext, value, RNStorage.HCE_ID)
    }
    @ReactMethod
    fun setAidFilter(value: String) {
        RNStorage.setValue(this.reactContext, value, RNStorage.HCE_AID)
    }

    override fun getConstants(): Map<String, Any> {
        val constants = mutableMapOf<String, Any>()
        constants["supportNFC"] = supportNFC()
        return constants
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == NfcAdapter.ACTION_ADAPTER_STATE_CHANGED) {
                val state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, NfcAdapter.STATE_OFF)
                val payload = Arguments.createMap()
                when (state) {
                    NfcAdapter.STATE_OFF -> sendStatusToReactContext(payload, false)
                    NfcAdapter.STATE_ON -> sendStatusToReactContext(payload, true)
                    NfcAdapter.STATE_TURNING_OFF, NfcAdapter.STATE_TURNING_ON -> {}
                }
            }
        }
    }

    private fun sendStatusToReactContext(map: WritableMap, status: Boolean) {
        map.putBoolean("status", status)
        sendEvent(reactContext, "listenNFCStatus", map)
    }

    override fun onHostResume() {}

    override fun onHostPause() {}

    override fun onHostDestroy() {
        try {
            reactContext.unregisterReceiver(mReceiver)
        } catch (ignore: Exception) {
            // ignore if already unregistered
        }
    }
}