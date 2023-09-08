package app.momsline.rnyandexads

import android.content.Context
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.annotation.SuppressLint;

/**
 * Partially working like an Application class by holding the appContext which makes it accessible inside this module.
 */
@SuppressLint("StaticFieldLeak")
object Common {
    /**
     * App appContext
     */
    @Volatile
    lateinit var appContext: Context

    var isStoreVersion: Boolean = false

    fun setContext(context: Context) {
        appContext = context
    }
}

class AdsContentProvider : ContentProvider() {

    companion object {
        private val TAG = AdsContentProvider::class.java.simpleName
    }

    override fun onCreate(): Boolean {
        context?.let {
            Common.setContext(it)
            return true
        }
        // Logger.e(TAG, "Context injection to common failed. Context is null! Check ContextProvider registration in the Manifest!")
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}