package com.vsahin.volkytv.ui.common.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vsahin.volkytv.data.model.Entry

@Composable
fun EntryPoster(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    entry: Entry,
    onClick: ((String) -> Unit)?
) {
    Card(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.clickable(onClick = { onClick?.invoke(entry.id) }),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                painter = rememberImagePainter(
                    data = entry.images[0].url,
                ),
                contentScale = contentScale,
                contentDescription = "My content description",
            )

            Text(text = entry.title, modifier = Modifier.padding(4.dp))
        }
    }
}