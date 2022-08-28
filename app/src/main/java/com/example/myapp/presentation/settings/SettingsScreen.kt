package com.example.myapp.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.components.SettingsChoice
import com.example.myapp.presentation.components.StandardToolbar

@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolbar(
            showBackArrow = true,
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SettingsChoice(
                    checked = state.isChecked,
                    onCheckedClick =  {
                    if(!state.isChecked)
                        viewModel.onEvent(SettingsEvent.Checked)

                    if(state.isChecked)
                        viewModel.onEvent(SettingsEvent.UnChecked)
                })
            }
        }

    }

}