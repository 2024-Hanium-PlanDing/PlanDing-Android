package com.comst.domain.usecase.file

import com.comst.domain.model.file.MediaImage

interface GetImageListUseCase {
    suspend operator fun invoke():List<MediaImage>
}