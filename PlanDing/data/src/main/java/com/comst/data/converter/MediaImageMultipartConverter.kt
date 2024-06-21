package com.comst.data.converter

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.comst.domain.model.file.MediaImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MediaImageMultipartConverter @Inject constructor(
    private val context: Context
) {

    fun toMultipartBodyPart(mediaImage: MediaImage, partName: String): MultipartBody.Part? {
        val uri = Uri.parse(mediaImage.uri)
        val file = getFileFromContentUri(context, uri)
        return file?.let {
            val requestFile = it.asRequestBody(mediaImage.mimeType.toMediaTypeOrNull())
            MultipartBody.Part.createFormData(partName, it.name, requestFile)
        }
    }

    private fun getFileFromContentUri(context: Context, contentUri: Uri): File? {
        var filePath: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath?.let { File(it) }
    }
}