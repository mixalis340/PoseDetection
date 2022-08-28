package com.example.myapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.presentation.ui.theme.SpaceMedium
import com.example.myapp.presentation.ui.theme.SpaceSmall
import com.example.myapp.presentation.ui.theme.TextGray

@Composable
fun SettingsChoice(
    //modifier: Modifier,
    checked: Boolean,
    onCheckedClick: (Boolean) -> Unit ={}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.show_likelihood), fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(text = stringResource(id = R.string.info_likelihood), fontSize = 13.sp)
        }
        Checkbox(checked , onCheckedChange = onCheckedClick , modifier = Modifier.align(Alignment.CenterEnd))
    }
    Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)
}