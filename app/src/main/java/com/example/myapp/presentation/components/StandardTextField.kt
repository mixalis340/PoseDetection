package com.example.myapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun StandardTextField(
    text: String = "",
    hint: String = "",
    onValueChange: (String) -> Unit,
    error: String = "",
    singleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
){
    val isPasswordToggleDisplayed by remember {
        mutableStateOf(keyboardType == KeyboardType.Password)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint,
                style = MaterialTheme.typography.body1
            )
        },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        visualTransformation = if(isPasswordToggleDisplayed && isPasswordVisible){
                                                                PasswordVisualTransformation()
                                                            } else {
                                                                   VisualTransformation.None
                                                                   },
        trailingIcon = {
            if(isPasswordToggleDisplayed) {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        tint = Color.White,
                    contentDescription = ""
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),


    )

}
