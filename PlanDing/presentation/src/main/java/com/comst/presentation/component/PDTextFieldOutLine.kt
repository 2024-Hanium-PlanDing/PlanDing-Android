package com.comst.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.comst.presentation.ui.theme.BackgroundColor1

@Composable
fun PDTextFieldOutLine(
    modifier: Modifier,
    label: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BackgroundColor1,
            unfocusedContainerColor = BackgroundColor1,
        ),
        label = { Text(text = label) },
        shape = RoundedCornerShape(8.dp),
        visualTransformation = visualTransformation
    )
}