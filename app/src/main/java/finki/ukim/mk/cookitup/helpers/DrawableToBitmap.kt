package finki.ukim.mk.cookitup.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import finki.ukim.mk.cookitup.R

fun drawableToBitmap(view: ImageView, context: Context): Bitmap {
    val drawable = view.drawable
    return if (drawable != null && (drawable is BitmapDrawable))
        drawable.toBitmap()
    else
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_image_not_supported_24)!!.toBitmap()
}