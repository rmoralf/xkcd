package com.rmoralf.xkcd.presentation.comic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rmoralf.xkcd.core.utils.Utils.Companion.formatDate
import com.rmoralf.xkcd.core.utils.Utils.Companion.openUrl
import com.rmoralf.xkcd.domain.model.Comic
import com.rmoralf.xkcd.domain.model.samples.SampleComicProvider

@Preview
@Composable
fun ComicDetails(
    @PreviewParameter(SampleComicProvider::class, 1)
    comic: Comic,
) {
    val context = LocalContext.current
    val localUriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth() // first, set the max size
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = comic.title,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "#${comic.num} - " +
                    formatDate("${comic.day}-${comic.month}-${comic.year}"),
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(comic.img)
                .crossfade(true)
                .build(),
            contentDescription = comic.alt,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .then(if (comic.link.isNotBlank()) Modifier.clickable {
                    openUrl(
                        uriHandler = localUriHandler,
                        url = comic.link
                    )
                } else Modifier)
        )

        Text(
            text = comic.alt,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

    }
}