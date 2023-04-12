package com.example.tipcalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.tipcalculator.ui.theme.TipCalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val originalAmount = remember {mutableStateOf("") }
    val calculation: MutableState<TipPercent> = remember{ mutableStateOf(TipPercent.TenP) }
    val tip: MutableState<String> = remember{ mutableStateOf("") }
    val total: MutableState<String> = remember{ mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        Text(text = "Tip Calculator",
            fontSize = 30.sp)
        Text("Enter your check amount:",
            modifier = Modifier.padding(20.dp))

    }
    Row(
        modifier = Modifier
            .padding(top = 120.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text("$",
            fontSize = 40.sp)
        OutlinedTextField(value = originalAmount.value,
            onValueChange = {newVal ->
                originalAmount.value = newVal}, Modifier.background(LightGray))
    }

    Text("Select your tip amount: ", modifier = Modifier.padding(top = 200.dp),
        textAlign = TextAlign.Center)


    Row(
        modifier = Modifier.padding(top = 250.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(
            onClick = {
                calculation.value = TipPercent.TenP
            }) {
            Text("10%")
        }
        Button(
            onClick = {
            calculation.value = TipPercent.TwentyP
        }) {
            Text("20%")

        }
        Button(
            onClick = {
            calculation.value = TipPercent.ThirtyP
        }) {
            Text("30%")

        }
    }

    Row(
        modifier = Modifier.padding(top = 350.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = {
            try{
                val result = tipCalculations(originalAmount.value, calculation.value)
                val totalAmount = totalCalculations(result, originalAmount.value)
                tip.value = result.toString()
                total.value = totalAmount.toString()
            } catch(e: NumberFormatException){
                Log.e(MainActivity::class.java.simpleName, "Error calculating tip or total", e)
                tip.value = "ERROR"
                total.value = "ERROR"
            }
        }) {
            Text("Calculate")

        }
    }

    Column(
        modifier = Modifier.padding(top = 450.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Tip: $ ${tip.value}",
            fontSize = 20.sp,
            modifier = Modifier.padding(15.dp))
        Text("Total: $ ${total.value}",
            fontSize = 20.sp,
            modifier = Modifier.padding(15.dp))
    }
}


fun totalCalculations(result: Double, originalAm: String): Any {

    return result + originalAm.toDouble()
}

fun tipCalculations(originalAm: String, calculation: TipPercent): Double {

    val amount: Double = originalAm.toDouble()
    return when(calculation){

        TipPercent.TenP -> {
            amount * .10
        }
        TipPercent.TwentyP -> {
            amount * .20
        }
        TipPercent.ThirtyP -> {
            amount * .30
        }
    }
}

enum class TipPercent{

    TenP, TwentyP, ThirtyP
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        MainScreen()
    }
}