package com.comst.presentation.main.group

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.presentation.component.PDScreenHeader
import com.comst.presentation.main.group.create.CreateGroupActivity
import com.comst.domain.model.groupRoom.GroupRoomCardModel
import com.comst.presentation.ui.theme.PlanDingTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GroupScreen(
    viewModel: GroupViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {permissions ->
        if (permissions.all { it.value }) {
            context.startActivity(
                Intent(context, CreateGroupActivity::class.java)
            )
        } else {
            Toast.makeText(context, "필요한 권한이 부여되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect){
            is GroupSideEffect.Toast -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_SHORT
            ).show()

            GroupSideEffect.NavigateToCreateGroupActivity -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                    ))
                } else {
                    permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                }
            }

            is GroupSideEffect.NavigateToGroupDetailActivity -> {
                context.startActivity(
                    Intent(
                        context, CreateGroupActivity::class.java
                    ).apply {
                        putExtra("", sideEffect.id)
                    }
                )
            }
        }
    }

    GroupScreen(
        groupRoomCardModels = state.groupCardModels,
        onUIAction = viewModel::onUIAction,
    )
}


@Composable
private fun GroupScreen(
    groupRoomCardModels:List<GroupRoomCardModel>,
    onUIAction:(GroupUIAction) -> Unit
){
    Surface {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PDScreenHeader(text = "나의 그룹")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        count = groupRoomCardModels.size,
                        key = { index -> groupRoomCardModels[index].groupId }
                    ){ index ->
                        groupRoomCardModels[index].let {
                            GroupCard(
                                groupName = it.groupName,
                                groupDescription = it.groupDescription,
                                groupImageUrl = it.groupImageUrl
                            )
                        }
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(60.dp),
                onClick = { onUIAction(GroupUIAction.GroupCreate) },
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }

        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupScreenPreview(){
    PlanDingTheme {
        GroupScreen()
    }
}