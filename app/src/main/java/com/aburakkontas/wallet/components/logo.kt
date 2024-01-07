package com.aburakkontas.wallet.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.aburakkontas.wallet.R

@Composable
fun Logo(modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.Crop) {
    Image(
        painter = painterResource(id = R.drawable.logo_colored),
        contentDescription = "Logo",
        modifier = modifier,
        contentScale = contentScale
    )
}