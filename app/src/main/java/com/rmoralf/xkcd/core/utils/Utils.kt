package com.rmoralf.xkcd.core.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.ui.platform.UriHandler
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.request.ImageRequest
import com.rmoralf.xkcd.core.utils.Constants.API_ENDPOINT
import com.rmoralf.xkcd.core.utils.Constants.EXPLANATION_URL
import com.rmoralf.xkcd.core.utils.Constants.TAG
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        fun printError(message: String?) {
            Log.d(TAG, message ?: "")
        }

        fun openUrl(uriHandler: UriHandler, url: String) {
            uriHandler.openUri(url)
        }

        fun openExplanation(uriHandler: UriHandler, comicId: Int) {
            openUrl(uriHandler, EXPLANATION_URL + comicId)
        }

        fun showErrorToast(
            context: Context,
            @StringRes stringRes: Int,
            formatArgs: Int? = null
        ) {
            Toast.makeText(
                context,
                context.getString(stringRes, formatArgs),
                Toast.LENGTH_SHORT
            ).show()
        }

        fun formatDate(date: String): String {
            val parser = SimpleDateFormat("dd-MM-yyyy")
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return parser.parse(date)?.let { formatter.format(it) } ?: date
        }

        fun shareImage(context: Context, url: String, comicId: Int) {
            val loader = ImageLoader(context)
            val req = ImageRequest.Builder(context)
                .data(url)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.putExtra(Intent.EXTRA_STREAM, getImageUri(context, bitmap))
                    intent.putExtra(Intent.EXTRA_TEXT, getXkcdLink(comicId))
                    context.startActivity(Intent.createChooser(intent, "Share Image"))

                }
                .build()

            loader.enqueue(req)
        }

        private fun getXkcdLink(comicId: Int): String {
            return API_ENDPOINT + comicId
        }

        private fun getImageUri(context: Context, bitmap: Bitmap): Uri? {

            val cachePath = File(context.externalCacheDir, "xkcd_images/")
            cachePath.mkdirs()

            val file = File(cachePath, "xkcd_share.png")
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider", file
            )
        }
    }


}