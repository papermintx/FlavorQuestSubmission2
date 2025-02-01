package com.dicoding.flavorquest.ui.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.flavorquest.R

@Composable
fun LoadingDialog(
    message: String = stringResource(id = R.string.loading),
    isVisible: Boolean
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = {},
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(text = message)
            },
            text = {
               Column(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center
               ) {
                   Card(
                       modifier = Modifier.padding(16.dp),
                       colors = CardDefaults.cardColors(
                           containerColor = MaterialTheme.colorScheme.surface,
                       )
                   ) {
                       CircularProgressIndicator(
                            modifier = Modifier.size(60.dp),
                            color = MaterialTheme.colorScheme.primary
                       )
                   }
               }
            },
            confirmButton = {}
        )
    }
}
