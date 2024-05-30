package com.ulises.components.screens

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.ulises.theme.CountdownAppTheme

@Composable
fun DefaultErrorScreen(
    @DrawableRes imageRes: Int? = null,
    @DrawableRes iconRes: Int? = null,
    text: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        imageRes?.also {
            Image(
                painter = painterResource(id = it),
                contentDescription = "",
                modifier = Modifier.size(150.dp, 150.dp)
            )
        }
        iconRes?.also {
            Icon(
                painter = painterResource(id = it),
                contentDescription = "",
                modifier = Modifier.size(150.dp, 150.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = text,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevDefaultErrorScreen() {
    CountdownAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DefaultErrorScreen(
                imageRes = com.ulises.common.resources.R.drawable.ic_error,
                text = "Error doing something that is why this error is visible"
            )
        }
    }
}