package com.softsandr.placesearch.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Parcelable
import java.util.*

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
object NavigatingUtil {

    @JvmStatic
    fun openExternalBrowser(ctx: Context, url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE)
        ctx.startActivity(browserIntent)

        val possibleTargets = ArrayList<Intent>()
        val uri = Uri.parse(url)
        val sampleIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val activitiesForIntent = ctx.packageManager.queryIntentActivities(sampleIntent, PackageManager.MATCH_DEFAULT_ONLY)
        for (activity in activitiesForIntent) {
            val packageName = activity.activityInfo.packageName
            if (packageName == ctx.getPackageName()) {
                continue
            }
            val intent = Intent(Intent.ACTION_VIEW, uri)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setPackage(packageName)
            possibleTargets.add(intent)
        }

        if (possibleTargets.size > 0) {
            val chooser = Intent.createChooser(possibleTargets.removeAt(0), "Open with...")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Intent.EXTRA_INITIAL_INTENTS, possibleTargets.toTypedArray<Parcelable>())
            ctx.startActivity(chooser)
        }
    }
}