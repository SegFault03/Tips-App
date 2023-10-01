package com.example.tips_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tips_app.data.TipsRepository
import com.example.tips_app.model.Tip
import com.example.tips_app.ui.theme.TipsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipsAppTheme {
                TipsApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsApp(modifier: Modifier = Modifier){
    val tipList: List<Tip> = TipsRepository().loadTips()
    Scaffold(
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.padding_large)),
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth())
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            items(tipList){ tip ->
                TipItem(
                    tip = tip,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun TipItem(
    tip: Tip,
    modifier: Modifier = Modifier
){
    var expandedState by remember{
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if(expandedState) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary,
        label = "colorAnim"
    )
    Card(
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(color)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    bottom = 0.dp
                )
                .wrapContentHeight()
                .animateContentSize(
                    spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioNoBouncy
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = tip.tipSnRes),
                style = MaterialTheme.typography.labelSmall
            )
            TipItemHeader(
                tipNameRes = tip.tipNameRes,
                expandedState = expandedState,
                onClick = {
                          expandedState = !expandedState
                },
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = tip.tipImageRes),
                contentDescription = stringResource(id = tip.tipNameRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dp)
                    .padding(top = 0.dp)
                    .offset(y = (-12).dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            if(expandedState){
                TipItemDescription(
                    tipDesc = tip.tipDescRes,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TipItemHeader(
    @StringRes tipNameRes: Int,
    onClick: ()->Unit,
    expandedState: Boolean,
    modifier: Modifier = Modifier,
){
    val imageVector = if(expandedState) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    Row(
        modifier = modifier
            .padding(top = 0.dp)
            .offset(y = (-12).dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = tipNameRes),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .weight(0.8f)
                .wrapContentHeight()
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .weight(0.2f)
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = 0.dp
                )
                .offset(x = (6).dp)
        ){
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(id = R.string.dropdown_arrow),
            )
        }
    }
}

@Composable
fun TipItemDescription(
    @StringRes tipDesc: Int,
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .wrapContentHeight(),
        text = stringResource(id = tipDesc),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start
    )
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier){
    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center,
        color = Color.Black,
        modifier = modifier.background(Color(0xD9FFFFFF)),
    )
}

@Preview(
    name = "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED
)
@Composable
fun DefaultPreviewDarkTheme(){
    TipsAppTheme {
        TipsApp(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun DefaultPreview(){
    TipsAppTheme {
        TipsApp(modifier = Modifier.fillMaxSize())
    }
}