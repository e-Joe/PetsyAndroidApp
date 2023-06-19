package com.bytecode.petsy.presentation.ui.commonui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bytecode.petsy.R
import com.bytecode.petsy.presentation.ui.theme.button_text

/**
 * Creates a Composable function that displays a row with two text buttons for "About Us" and "Privacy Policy".
 *
 * @author Ilija Vucetic
 */
@Composable
fun AboutUsAndPrivacyView() {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")) }

    Row {
        TextButton(contentPadding = PaddingValues(0.dp), onClick = {
            context.startActivity(intent)
        }) {
            Text(
                text = stringResource(R.string.common_about_us),
                style = button_text
            )
        }

        Spacer(modifier = Modifier.width(40.dp))

        TextButton(contentPadding = PaddingValues(0.dp), onClick = {
            context.startActivity(intent)
        }) {
            Text(
                text = stringResource(R.string.common_privacy_policy),
                style = button_text
            )
        }
    }
}