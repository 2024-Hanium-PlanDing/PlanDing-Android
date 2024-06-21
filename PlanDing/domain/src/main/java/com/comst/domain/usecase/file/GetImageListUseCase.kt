package com.comst.domain.usecase.file

import com.comst.domain.model.file.MediaImage
import com.comst.domain.repository.FileRepository
import javax.inject.Inject

class GetImageListUseCase @Inject constructor(
    private val fileRepository: FileRepository
){
    suspend operator fun invoke():Result<List<MediaImage>> = kotlin.runCatching {
        fileRepository.getImageList()
    }
}