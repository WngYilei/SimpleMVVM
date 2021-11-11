package com.xl.xl_base.tool.util

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.fragment.app.FragmentActivity


@Suppress("Unused")
object PermissionPageUtil {
    const val REQUEST_CODE = 987

    /**
     *  应用设置界面
     */
    fun toSettingActivity(activity: FragmentActivity) {
        try {
            val uri = Uri.parse("package:${activity.packageName}")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intent, REQUEST_CODE)
        } catch (e: Exception) {
            toNormalSettings(activity)
        }
    }


    /**
     * 通知中 推送渠道 界面
     * @param channelId 通知的渠道id
     */
    fun toSettingPush(activity: FragmentActivity, channelId: String) {
        try {
            val intent = Intent()
            when {
                Build.VERSION.SDK_INT > 26 -> {
                    intent.action = Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
                }
                Build.VERSION.SDK_INT > 21 -> {
                    intent.putExtra("app_package", activity.packageName)
                    intent.putExtra("app_uid", activity.applicationInfo.uid)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intent, REQUEST_CODE)
        } catch (e: Exception) {
            toSettingNotification(activity)
        }
    }

    /**
     * 通知设置界面
     */
    fun toSettingNotification(activity: FragmentActivity) {
        try {
            val intent = Intent()
            when {
                Build.VERSION.SDK_INT > 26 -> {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                }
                Build.VERSION.SDK_INT > 21 -> {
                    intent.putExtra("app_package", activity.packageName)
                    intent.putExtra("app_uid", activity.applicationInfo.uid)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intent, REQUEST_CODE)
        } catch (e: Exception) {
            toSettingActivity(activity)
        }
    }

    /**
     * 手机设置界面
     */
    private fun toNormalSettings(activity: FragmentActivity) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intent, REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}