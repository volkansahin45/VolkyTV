package com.vsahin.volkytv.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vsahin.volkytv.data.model.Entry

@Composable
fun HeaderListItem(
    modifier: Modifier = Modifier,
    entry: Entry,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clickable(onClick = {
                    onClick(entry.id)
                })
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                painter = rememberImagePainter(
                    data = entry.images[0].url,
                ),
                contentDescription = "My content description",
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = Icons.Filled.PlayArrow,
                tint = Color.White,
                contentDescription = "share",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color(0xAA000000), shape = RoundedCornerShape(8.dp))
            )
        }
    }
}