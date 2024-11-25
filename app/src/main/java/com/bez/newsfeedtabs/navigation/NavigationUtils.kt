package com.bez.newsfeedtabs.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object NavigationUtils {

    fun openWebLink(context: Context, url: String) {
        try {

            // Validate URL scheme
            val validUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
                url
            } else {
                "http://$url"
            }

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(validUrl)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

}
