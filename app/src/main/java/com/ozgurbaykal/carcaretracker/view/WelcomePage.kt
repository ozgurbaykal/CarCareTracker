package com.ozgurbaykal.carcaretracker.view

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.ui.theme.MainGray
import com.ozgurbaykal.carcaretracker.view.ui.theme.CarCareTrackerTheme
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

class WelcomePage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCareTrackerTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TutorialLayout()
                }
            }
        }
    }


    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun requestNotificationPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
        WelcomePage.NOTIFICATION_PERMISSION_REQUEST_CODE
    )
}

@Composable
fun TutorialLayout() {
    val title1String = stringResource(R.string.tutorial_page_1_title)
    val paragraph1String = stringResource(R.string.tutorial_page_1_paragraph)
    val title2String = stringResource(R.string.tutorial_page_2_title)
    val paragraph2String = stringResource(R.string.tutorial_page_2_paragraph)

    val buttonText1 = stringResource(R.string.next)
    val buttonText2 = stringResource(R.string.continue_and_open_notification)


    var currentPage by remember { mutableIntStateOf(0) }
    val title by remember { derivedStateOf { if (currentPage == 0) title1String else title2String } }
    val paragraph by remember { derivedStateOf { if (currentPage == 0) paragraph1String else paragraph2String } }

    var buttonText by remember {
        mutableStateOf(buttonText1)
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(MainBlue),
        contentAlignment = Alignment.Center

    ) {
        when (currentPage) {
            0 -> {
                // İlk sayfanın metinleri ve görselleri
                Image(
                    painter = painterResource(id = R.drawable.activity_tracker),
                    contentDescription = "activity tracker"
                )
            }

            1 -> {
                // İkinci sayfanın metinleri ve görselleri
                Image(
                    painter = painterResource(id = R.drawable.ringing),
                    contentDescription = "activity tracker"
                )
            }
        }
    }

    val activity = LocalContext.current as Activity


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(270.dp),
                fontSize = 28.sp,
                color = MainBlue,
                textAlign = TextAlign.Center,
            )

            Text(
                text = paragraph,
                modifier = Modifier
                    .padding(top = 45.dp, start = 10.dp, end = 10.dp),
                color = LightGray,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            PageIndicator(currentPage)
        }
        Box(
            modifier = Modifier
                .padding(bottom = 25.dp, end = 20.dp)
                .align(Alignment.BottomEnd)
        ) {
            Button(
                onClick = {
                    if (currentPage == 0) {
                        currentPage = (currentPage + 1) % 2
                        if (currentPage == 1) {
                            buttonText = buttonText2
                        }
                    } else if (currentPage == 1) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestNotificationPermission(activity)
                        }
                        activity.finish()
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = MainBlue),
                shape = RoundedCornerShape(4.dp)

            ) {
                Text(text = buttonText,
                fontSize = 15.sp,
                color = Color.White)
            }
        }
    }


}

@Composable
fun PageIndicator(currentPageIndex: Int) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        for (i in 0 until 2) { // İki sayfa için iki nokta
            Indicator(isSelected = i == currentPageIndex)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(12.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.5f))
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CarCareTrackerTheme {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TutorialLayout()
        }
    }
}

