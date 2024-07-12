package com.comst.presentation.main.group.create

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.comst.presentation.common.base.BaseScreen
import com.comst.presentation.main.group.create.CreateGroupContract.CreateGroupIntent
import com.comst.presentation.main.group.create.CreateGroupContract.CreateGroupSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectScreen(
    viewModel: CreateGroupViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val context = LocalContext.current

    val handleEffect: (CreateGroupSideEffect) -> Unit = { effect ->
        when (effect) {
            is CreateGroupSideEffect.SuccessGroupCreation -> {
                // Handle Success Group Creation if needed
            }
        }
    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        Surface {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "새 그룹",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "뒤로가기"
                                )
                            }
                        },
                        actions = {
                            TextButton(onClick = onNextClick) {
                                Text(text = "다음", color = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                },
                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.selectedImage != null) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = rememberAsyncImagePainter(
                                        model = uiState.selectedImage!!.uri
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null
                                )
                            } else {
                                Text(
                                    text = "선택된 이미지가 없습니다.",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Divider(modifier = Modifier.height(1.dp))
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.White),
                            columns = GridCells.Adaptive(110.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            items(
                                count = uiState.images.size,
                                key = { index -> uiState.images[index].uri }
                            ) { index ->
                                val image = uiState.images[index]
                                Box(
                                    modifier = Modifier.clickable {
                                        viewModel.setIntent(CreateGroupIntent.SelectGroupImage(image))
                                    }
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f),
                                        painter = rememberAsyncImagePainter(
                                            model = image.uri,
                                            contentScale = ContentScale.Crop
                                        ),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                    if (uiState.selectedImage?.uri == image.uri) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(start = 4.dp, top = 4.dp)
                                                .clip(CircleShape)
                                                .background(color = Color.White),
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}