package com.xl.xl_base.adapter.image

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

class TopCircleCrop : BitmapTransformation() {

    companion object {
        private const val VERSION = 1
        private const val ID =
            "com.xl.xl_base.adapter.image.TopCircleCrop.$VERSION"
        private val ID_BYTES = ID.toByteArray(CHARSET)
    }

    override fun equals(other: Any?): Boolean {
        return other is TopCircleCrop
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        return messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val min = toTransform.width.coerceAtMost(toTransform.height)
        val bitmap = Bitmap.createBitmap(toTransform, 0, 0,min,min)
        return TransformationUtils.circleCrop(pool,bitmap,outWidth,outWidth)
    }
}