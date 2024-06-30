package expo.modules.bashgmuhce

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class CardService : HostApduService() {

    companion object {
        private const val SELECT_APDU_HEADER = "00A40400"
        private const val UNKNOWN_CMD_SW = "8888"
    }

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle): ByteArray {
        val data = RNStorage.getValue(applicationContext, RNStorage.HCE_ID)
        val accountBytes = data.toByteArray()
        return accountBytes
//        val selectedApdu = getSelectedApdu()
//        return if (Arrays.equals(selectedApdu, commandApdu)) {
//            val data = RNStorage.getValue(applicationContext, RNStorage.HCE_ID)
//            val accountBytes = data.toByteArray()
//            accountBytes
//        } else {
//            UNKNOWN_CMD_SW.toByteArray()
//        }
    }

    override fun onDeactivated(reason: Int) {}

    @Throws(IllegalArgumentException::class)
    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        require(len % 2 == 0) { "Hex string must have even number of characters" }
        val data = ByteArray(len / 2)
        for (i in s.indices step 2) {
            data[i / 2] = (Character.digit(s[i], 16) shl 4 + Character.digit(s[i + 1], 16)).toByte()
        }
        return data
    }

    fun byteArrayToHexString(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    private fun buildSelectApdu(aid: String): ByteArray {
        return hexStringToByteArray("$SELECT_APDU_HEADER${String.format("%02X", aid.length / 2)}$aid")
    }

    private fun getAIDValue(): String {
        return RNStorage.getValue(applicationContext, RNStorage.HCE_AID)
    }

    private fun getSelectedApdu(): ByteArray {
        return buildSelectApdu(getAIDValue())
    }
}