package com.rmoralf.xkcd.presentation.comic.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.rmoralf.xkcd.R
import com.rmoralf.xkcd.core.utils.Utils.Companion.showErrorToast
import com.rmoralf.xkcd.presentation.comic.ComicViewModel

@Composable
fun ComicSearchDialog(
    viewModel: ComicViewModel = hiltViewModel()
) {
    var number by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    val context = LocalContext.current

    if (viewModel.openDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.openDialogState.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.search))
            },
            text = {
                TextField(
                    value = number,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { number = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search_comic_number)
                        )
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                )
                DisposableEffect(Unit) {
                    focusRequester.requestFocus()
                    onDispose { }
                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false

                        number.toIntOrNull()?.let {
                            if (viewModel.isComicNumberValid(it)) {
                                viewModel.findComic(it)
                            } else {
                                showErrorToast(
                                    context = context,
                                    R.string.search_error_max_comic,
                                    viewModel.latestComicId
                                )

                            }
                        } ?: run {
                            showErrorToast(
                                context = context,
                                stringRes = R.string.search_error_bad_input
                            )
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_search),
                        color = Color.Black
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_cancel),
                        color = Color.Black
                    )
                }
            }
        )
    }
}