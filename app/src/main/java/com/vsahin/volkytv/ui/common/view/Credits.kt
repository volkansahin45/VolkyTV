package com.vsahin.volkytv.ui.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vsahin.volkytv.data.model.Credit
import com.vsahin.volkytv.ui.common.RandomColor

@Composable
fun Credits(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    credits: List<Credit>
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement
    ) {
        items(credits) {
            Column(
                modifier = Modifier.width(75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            color = Color.RandomColor
                        ),
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "My content description",
                        tint = Color.White
                    )
                }
                Text(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, text = it.name)
                Text(textAlign = TextAlign.Center, text = it.role)
            }
        }
    }
}