package com.example.colorgame.ui.functions

import androidx.compose.ui.graphics.Color

fun getColor(color: Int): Color = when (color) {
    1 -> Color.Yellow
    2 -> Color.White
    3 -> Color(0xFF6035Ff)
    4 -> Color.Blue
    5 -> Color.Red
    else -> Color.Green
}

fun checkWin(bets: MutableList<BetNode>,winningColor: Int) : Int {
    var totalWin = 0
    for (i in bets) {
        if (i.color == winningColor) {
            totalWin += (i.stake * 2)
        }
    }
    return totalWin
}