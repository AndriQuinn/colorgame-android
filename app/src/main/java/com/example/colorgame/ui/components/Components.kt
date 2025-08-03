package com.example.colorgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    color: Color,
    isColorBetBox: Boolean = false,
    id: Int = 0,
    placeBet: (Int) -> Unit = {}

) {

    val thisId = id

    Button (
        onClick = {placeBet(thisId)},
        colors = buttonColors(
            containerColor = color,
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .border(2.dp, Color.Black)
            .fillMaxSize(),
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            if (isColorBetBox) {
                Text(
                    text = "Bet",
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
fun BetField(
    modifier: Modifier = Modifier,
    betAmount: String,
    onValueChanged: (String) -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Bet: ")
        TextField(
            value = betAmount,
            onValueChange = {onValueChanged(it)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Transparent,
                focusedContainerColor = Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),

            singleLine = true,
            label = { Text("Bet amount") }
        )
    }

}