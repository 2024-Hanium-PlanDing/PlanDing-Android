package com.comst.domain.repository

import com.comst.domain.model.file.MediaImage

interface FileRepository {
    suspend fun getImageList():List<MediaImage>
}