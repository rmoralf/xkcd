package com.rmoralf.xkcd.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rmoralf.xkcd.presentation.comic.ComicScreen
import com.rmoralf.xkcd.presentation.ui.theme.XkcdTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XkcdTheme {
                ComicScreen()
            }
        }
    }
}