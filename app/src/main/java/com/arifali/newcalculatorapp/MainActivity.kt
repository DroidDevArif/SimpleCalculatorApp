package com.arifali.newcalculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arifali.newcalculatorapp.ui.theme.NewCalculatorAppTheme
import kotlinx.coroutines.delay
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewCalculatorAppTheme {

                SplashScreen()
                    //CalculatorApp()
                }
            }
        }
    }
@Composable
fun SplashScreen() {
    var isSplashVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { 
        delay(2000) // 2 seconds delay
        isSplashVisible = false
    }

    if (isSplashVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //Text(text = "Welcome to a new calculator app" )
            Image(
                painter = painterResource(id = R.drawable.maths),
                contentDescription = null
            )
        }
    } else {
        CalculatorApp()
    }
}


@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .background(Color(0xFF009688)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        // Display
        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .background(Color(0xFF009688))
           // horizontalAlignment = Alignment.End
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF009688))
            ) {
                Text(
                    text = input,
                    fontSize = 36.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.End
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF009688))
            ) {
                Text(
                    text = result,
                    fontSize = 46.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.End
                )
            }
        }

        // Buttons
        val buttons = listOf(
            listOf("C","%","⌫","/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("00", "0", ".", "=")
        )

        Column(modifier = Modifier.padding(18.dp)){
            buttons.forEach { row ->
                Row(modifier = Modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth()
                    , horizontalArrangement = Arrangement.SpaceBetween)
                    {
                    row.forEach { button ->
                        CalculatorButton(button) { onButtonClick(button, input, { input = it }, { result = it }) }
                    }
                }
            }
        }
    }
}

//@Composable
//fun CalculatorButton(text: String, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            //.weight(1f)
//            .padding(0.dp)
//            .height(50.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF4A4A4A))
//    )
//    {
//        Column(
//
//            modifier = Modifier.padding(6.dp),
//        )
//        {
 //   ElevatedButton()
        

@Composable
fun CalculatorButton(text: String,onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
        Text(text, fontSize = 25.sp, color = Color.Black)
    }
}
//            Button(
//                onClick = onClick,
//                //colors = ButtonDefaults.buttonColors(containerColor = Color.),
//
//                modifier = Modifier.padding(3.dp),
//                border = BorderStroke(1.dp, Color.Blue),
//                shape = RoundedCornerShape(25)
//            ) {
//                Text(text, fontSize = 15.sp, color = Color.White)
//            }



//}

private fun onButtonClick(button: String, input: String, updateInput: (String) -> Unit, updateResult: (String) -> Unit) {
    when (button) {
        "C" -> {
            updateInput("")
            updateResult("")
        }
        "=" -> {
            val result = calculateResult(input)
            updateResult(result)
        }
        "⌫" ->{
          updateInput(input)
        }
        else -> updateInput(input + button)
    }
}

private fun calculateResult(input: String): String {
    return try {
        if (input.isEmpty()) return ""
        val exp = ExpressionBuilder(input).build()
        exp.evaluate().toString()
    } catch (e: Exception) {
        "Error"
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCalculator() {
    NewCalculatorAppTheme{
        CalculatorApp()
    }

}