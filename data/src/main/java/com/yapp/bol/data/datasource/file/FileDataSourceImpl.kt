package com.yapp.bol.data.datasource.file

import com.yapp.bol.data.remote.FileApi
import com.yapp.bol.data.remote.TermsApi
import com.yapp.bol.domain.handle.BaseRepository
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : BaseRepository(), FileDataSource {
}
