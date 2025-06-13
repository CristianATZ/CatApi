package com.devtorres.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun AsyncImageLoader(
    modifier: Modifier = Modifier,
    url: String = "",              // p. ej. "exercises" o "supplements"
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRatio: Float = 1f
) {
    var state by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is AsyncImagePainter.State.Error -> {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }

            else -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .size(Size.ORIGINAL)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = contentScale,
                    onState = { painterState -> state = painterState },
                    modifier = Modifier
                        .matchParentSize()
                        .then(
                            if (state is AsyncImagePainter.State.Loading)
                                Modifier.aspectRatio(placeholderRatio)
                            else Modifier
                        )
                )

                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}