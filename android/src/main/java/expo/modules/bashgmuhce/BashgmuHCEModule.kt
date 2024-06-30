package expo.modules.bashgmuhce

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.util.Log

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class BashgmuHCEModule : Module() {

  private val reactContext: Context
    get() = appContext.reactContext ?: throw Exception("ReactContextLost")
  override fun definition() = ModuleDefinition {
    Name("BashgmuHCE")

    // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
    Function("hello") {
      "Hello world! 123 👋"
    }

    Function("supportNFC") {
      return@Function "supportNFC"
    }

    View(BashgmuHCEView::class) {
      // Defines a setter for the `name` prop.
      Prop("name") { view: BashgmuHCEView, prop: String ->
        println(prop)
      }
    }
  }

  private fun supportNFC(): Unit{
    val manager = this.reactContext.getSystemService(Context.NFC_SERVICE) as NfcManager
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

}
