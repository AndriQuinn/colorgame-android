package com.example.colorgame.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.colorgame.R
import com.example.colorgame.ui.components.BetField
import com.example.colorgame.ui.components.ColorBox
import com.example.colorgame.ui.functions.BetNode
import com.example.colorgame.ui.functions.checkWin
import com.example.colorgame.ui.functions.getColor
import com.example.colorgame.ui.theme.ColorGameTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var box1 by remember { mutableIntStateOf(1) }
    var box2 by remember { mutableIntStateOf(1) }
    var box3 by remember { mutableIntStateOf(1) }
    var totalMoney by remember { mutableStateOf("1000") }
    var betAmount by remember {mutableStateOf("0")}
    val bets = remember { mutableStateListOf<BetNode>() }
    var winAmount by remember {mutableIntStateOf(0)}
    var indicator by remember {mutableStateOf(false)}
    var indicatorValue by remember {mutableIntStateOf(0)}

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.systemBarsPadding()
    ) {
        InfoBar(
            coinAmount = totalMoney,
            modifier = Modifier.weight(0.8f),
            amount = indicatorValue,
            isShow = indicator,
            toOff = {value -> indicator = value}
        )
        MainContent(
            box1 = box1,
            box2 = box2,
            box3 = box3,
            betAmount = betAmount,
            onValueChanged = {
                newValue ->
                    betAmount = newValue
            },
            playButton = {
                box1 = Random.nextInt(1,7)
                box2 = Random.nextInt(1,7)
                box3 = Random.nextInt(1,7)
                winAmount += checkWin(bets,box1)
                winAmount += checkWin(bets,box2)
                winAmount += checkWin(bets,box3)
                totalMoney =  (totalMoney.toInt() + winAmount).toString()
                indicatorValue = winAmount
                indicator = true
                winAmount = 0
                bets.clear()
            },
            betList = bets,
            Modifier.weight(5f))
        BetColor(
            placeBet = if ((betAmount.toIntOrNull() ?: 0) > 0) {
                id ->
                    totalMoney = (totalMoney.toInt() - (betAmount.toInt())).toString()
                    bets.add(BetNode(id, betAmount.toInt()))
                    indicatorValue = betAmount.toInt() - (betAmount.toInt()*2)
                    indicator = true
            } else {id -> id},
            modifier = Modifier.weight(4.2f))
    }
}

@Composable
fun InfoBar(
    coinAmount: String,
    amount: Int,
    modifier: Modifier = Modifier,
    isShow: Boolean,
    toOff: (Boolean) -> Unit
    ) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.coin),
                contentDescription = stringResource(R.string.coin_icon_txt),
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(5.dp))
            Text(coinAmount)
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            if (isShow) {
                MoneyIndicator(
                    show = isShow,
                    isAdd = if (amount > 0) {
                        true
                    } else {false},
                    amount = amount,
                    turnOff = { toOff(it) }
                )
            } else {}
        }
    }
}

@Composable
fun MoneyIndicator(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    isAdd: Boolean = false,
    amount: Int,
    turnOff: (Boolean) -> Unit
) {
    var isShow = show
    val transitionPadding by animateDpAsState(
        targetValue = if (isShow) 0.dp else 15.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        delay(1000L)
        isShow = false
        turnOff(false)
    }

    AnimatedVisibility(visible = isShow) {
        if (isAdd) {
            Text(
                text = "+$amount",
                color = Color.Green,
                modifier = Modifier.padding(top = transitionPadding)
            )
        } else {
            Text(
                text = "$amount",
                color = Color.Red,
                modifier = Modifier.padding(top = transitionPadding)
            )
        }
    }
}

@Composable
fun MainContent(
    box1: Int,
    box2: Int,
    box3: Int,
    betAmount: String,
    onValueChanged: (String) -> Unit,
    playButton: () -> Unit,
    betList: MutableList<BetNode>,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .padding(50.dp)
                .weight(2f)
                .fillMaxSize()
        ) {
            ColorBox(
                color = getColor(box1),
                modifier = Modifier.weight(1f),
                placeBet = {}
            )
            ColorBox(
                color = getColor(box2),
                modifier = Modifier.weight(1f),
                placeBet = {}
            )
            ColorBox(
                color = getColor(box3),
                modifier = Modifier.weight(1f),
                placeBet = {}
            )
        }
        Row (
            modifier = Modifier
                .padding(
                    vertical = 40.dp,
                    horizontal = 30.dp
                )
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Bets: ")
            Row {
                for (bet in betList) {
                    ColorBox(color = getColor(bet.color),modifier = Modifier.size(20.dp))
                }

            }
        }
        Row (
            modifier = Modifier
                .padding(
                    top = 40.dp,
                    bottom = 20.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .weight(1f)
                .fillMaxSize()
        ) {
            BetField(
                betAmount = betAmount,
                onValueChanged = {onValueChanged(it)},
                modifier = Modifier.weight(2f)
            )
            Button (
                onClick = {playButton()},
                colors = buttonColors(
                    containerColor = Color.Black,
                    
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.play_txt),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BetColor(
    placeBet: (Int) -> Unit,
    modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .background(Color.Green)
            .fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ColorBox(
                color = Color.Yellow,
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 1,
                placeBet = placeBet
            )
            ColorBox(
                color = Color.White,
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 2,
                placeBet = placeBet
            )
            ColorBox(
                color = Color(0xFF6035FF),
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 3,
                placeBet = placeBet
            )
        }
        Row(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ColorBox(
                color = Color.Blue,
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 4,
                placeBet = placeBet
            )
            ColorBox(
                color = Color.Red,
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 5,placeBet = placeBet

            )
            ColorBox(
                color = Color.Green,
                modifier = Modifier.weight(1f),
                isColorBetBox = true,
                id = 6,
                placeBet = placeBet
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "HomeScreen preview"
)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    ColorGameTheme {
        HomeScreen()
    }
}

