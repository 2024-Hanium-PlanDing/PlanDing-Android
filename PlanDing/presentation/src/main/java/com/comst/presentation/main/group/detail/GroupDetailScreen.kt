package com.comst.presentation.main.group.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.comst.presentation.R
import com.comst.presentation.ui.theme.PlanDingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailScreen(
    groupId : Long,
    viewModel: GroupDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(groupId) {
        viewModel.initialize(groupId)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "그룹명")
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            CollapsingContent(viewModel)
            TabLayout()
            ViewPagerContent()
        }
    }
}

@Composable
fun CollapsingContent(viewModel: GroupDetailViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(""),
                    contentDescription = null,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )

                Column {
                    if (true) {
                        Text(
                            text = "프로필 수정",
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.LightGray)
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            fontSize = 14.sp
                        )

                    } else {
                        Text(
                            text = "그룹 탈퇴",
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.LightGray)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .clickable {
                                },
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Text(
                text = "그룹 설명",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = "그룹 생성일",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                fontSize = 12.sp,
                color = Color.Gray
            )

            IconButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_keyboard_arrow_down_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun TabLayout() {
    TabRow(
        selectedTabIndex = 0,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp)
            .background(Color.White)
    ) {
        Tab(selected = true, onClick = { /*TODO*/ }) {
            Text(text = "그룹 일정")
        }
        Tab(selected = false, onClick = { /*TODO*/ }) {
            Text(text = "그룹원")
        }
    }
}

@Composable
fun ViewPagerContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

    }
}
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupDetailPreview() {
    PlanDingTheme {
        GroupDetailScreen(groupId = 6318, viewModel = hiltViewModel())
    }
}