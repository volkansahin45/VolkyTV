package com.vsahin.volkytv.ui.common.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vsahin.volkytv.R.string

@Preview
@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    error: String = stringResource(id = string.there_is_an_error),
    onRefresh: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)

        onRefresh?.let {
            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = onRefresh
            ) {
                Text(text = stringResource(id = string.refresh))
            }
        }
    }
}