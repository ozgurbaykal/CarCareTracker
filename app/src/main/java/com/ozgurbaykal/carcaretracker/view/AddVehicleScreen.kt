package com.ozgurbaykal.carcaretracker.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.ui.theme.MainGray
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddVehicleScreen : ComponentActivity() {

    val icons = listOf(
        R.drawable.hatchback,
        R.drawable.sedan,
        R.drawable.suv,
        R.drawable.cabrio,
        R.drawable.swagon,
        R.drawable.jeep,
        R.drawable.minivan,
        R.drawable.pickup,
        R.drawable.trailer,
        R.drawable.truck,
        R.drawable.van
    )


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent() {
            CarCareTrackerTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(id = R.string.add_vehicle),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MainGray,
                                titleContentColor = MainBlue,
                            ),
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back),
                                        tint = MainBlue
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = stringResource(id = R.string.add_vehicle),
                                        tint = MainBlue
                                    )
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    AddVehicleObjects(innerPadding)
                }
            }
        }
    }


    @Composable
    fun AddVehicleObjects(innerPadding: PaddingValues) {
        var plate by remember { mutableStateOf("") }
        var vehicleBrand by remember { mutableStateOf("") }
        var vehicleModel by remember { mutableStateOf("") }
        var odometerNumber by remember { mutableStateOf("") }
        val radioOptions = listOf("KM", "Mil")
        var selectedOption by remember { mutableStateOf(radioOptions[0]) }
        var vehicleNote by remember { mutableStateOf("") }

        val serviceDate = remember { mutableStateOf("") }
        val insuranceDate = remember { mutableStateOf("") }
        var selectedIcon by remember { mutableStateOf<Int?>(null) }
        var selectedColor by remember { mutableStateOf<Color?>(null) }

        val context = LocalContext.current
        val colors = listOf(
            ContextCompat.getColor(context, R.color.black),
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.silver),
            ContextCompat.getColor(context, R.color.smoke_grey),
            ContextCompat.getColor(context, R.color.chocolate_brown),
            ContextCompat.getColor(context, R.color.light_red),
            ContextCompat.getColor(context, R.color.dark_red),
            ContextCompat.getColor(context, R.color.metallic_red),
            ContextCompat.getColor(context, R.color.candy_apple_red),
            ContextCompat.getColor(context, R.color.dark_orange),
            ContextCompat.getColor(context, R.color.tangerine_orange),
            ContextCompat.getColor(context, R.color.gold),
            ContextCompat.getColor(context, R.color.sunshine_yellow),
            ContextCompat.getColor(context, R.color.light_green),
            ContextCompat.getColor(context, R.color.dark_green),
            ContextCompat.getColor(context, R.color.metallic_green),
            ContextCompat.getColor(context, R.color.light_blue),
            ContextCompat.getColor(context, R.color.dark_blue),
            ContextCompat.getColor(context, R.color.metallic_blue),
            ContextCompat.getColor(context, R.color.midnight_blue),
            ContextCompat.getColor(context, R.color.light_purple),
            ContextCompat.getColor(context, R.color.dark_purple),

            ).map { Color(it) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            CustomTextField(
                plate,
                label = stringResource(id = R.string.plate),
                textFieldIcon = R.drawable.license_plate,
                textFieldIconDescription = stringResource(id = R.string.plate),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                maxLength = 15
            ) { plate = it }

            Row {

                CustomTextField(
                    vehicleBrand,
                    label = stringResource(id = R.string.brand),
                    textFieldIcon = R.drawable.cubes,
                    textFieldIconDescription = stringResource(id = R.string.brand),
                    maxLength = 25,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                ) { vehicleBrand = it }

                Spacer(Modifier.width(8.dp))

                CustomTextField(
                    vehicleModel,
                    label = stringResource(id = R.string.model),
                    textFieldIcon = R.drawable.cubes,
                    textFieldIconDescription = stringResource(id = R.string.model),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    maxLength = 25
                ) { vehicleModel = it }

            }



            Row {
                CustomTextField(
                    odometerNumber,
                    label = stringResource(id = R.string.odometer_info, selectedOption),
                    textFieldIcon = R.drawable.odometer,
                    textFieldIconDescription = stringResource(id = R.string.odometer_info),
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    maxLength = 7
                ) { odometerNumber = it }


                radioOptions.forEach { unitType ->
                    Column(modifier = Modifier.padding(end = 10.dp)) {
                        RadioButton(
                            selected = (unitType == selectedOption),
                            onClick = { selectedOption = unitType },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MainBlue,
                                unselectedColor = LightGray
                            )
                        )
                        Text(
                            text = unitType,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                    }
                }
            }

            Column {
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    CustomDatePicker(
                        LocalContext.current,
                        Modifier.weight(1f),
                        stringResource(id = R.string.date_picker_insurance)
                    ) {
                        serviceDate.value = it.toString()
                    }
                    Spacer(Modifier.width(8.dp)) // DatePicker'lar arasında boşluk
                    CustomDatePicker(
                        LocalContext.current,
                        Modifier.weight(1f),
                        stringResource(id = R.string.date_picker_service)
                    ) {
                        insuranceDate.value = it.toString()
                    }
                }
            }

            CustomTextField(
                vehicleNote,
                label = stringResource(id = R.string.note),
                textFieldIcon = R.drawable.spare_parts,
                textFieldIconDescription = stringResource(id = R.string.note),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .padding(10.dp),
                imeAction = ImeAction.None,
                maxLength = 200,
                singleLine = false
            ) { vehicleNote = it }


            Text(
                stringResource(id = R.string.select_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(8),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(colors) { colorRes ->
                    ColorItem(
                        colorRes = colorRes,
                        isSelected = selectedColor == colorRes,
                        onClick = { selectedColor = colorRes },
                    )
                }
            }


            Text(
                stringResource(id = R.string.select_vehicle_type),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                items(icons) { iconRes ->
                    IconItem(
                        iconRes = iconRes,
                        selectedColor = selectedColor ?: Color.Black,
                        isSelected = selectedIcon == iconRes,
                        onClick = { selectedIcon = iconRes }
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

        }
    }


    private val customTextSelectionColors = TextSelectionColors(
        handleColor = MainBlue,
        backgroundColor = MainBlue.copy(alpha = 0.4f)
    )

    @Composable
    fun IconItem(iconRes: Int, isSelected: Boolean, selectedColor: Color, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .size(64.dp)
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = if (isSelected) Color.Black else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = selectedColor)
        }
    }

    @Composable
    fun ColorItem(colorRes: Color, isSelected: Boolean, onClick: () -> Unit) {


        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            ){

            OutlinedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp,
                ),
                colors = CardDefaults.cardColors(
                    containerColor = colorRes,
                ),
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        onClick()
                    }
            ) {
            }

            Spacer(modifier = Modifier.height(3.dp))

            if (isSelected) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp,
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(R.color.main_blue),
                    ),
                    modifier = Modifier
                        .size(5.dp)
                        .clickable { onClick() }
                ) {
                }
            }
        }

    }

    @Composable
    fun CustomTextField(
        stringValue: String,
        label: String,
        textFieldIcon: Int,
        textFieldIconDescription: String,
        modifier: Modifier = Modifier,
        capitalization: KeyboardCapitalization = KeyboardCapitalization.Characters,
        imeAction: ImeAction = ImeAction.Next,
        keyboardType: KeyboardType = KeyboardType.Text,
                maxLength: Int,
        singleLine: Boolean = true,
        onStringValueChanged: (String) -> Unit,


        ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(

                value = stringValue,
                onValueChange = { if (it.length <= maxLength) onStringValueChanged(it) },
                label = { Text(label) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = textFieldIcon),
                        contentDescription = textFieldIconDescription,
                        tint = MainBlue,
                        modifier = Modifier.size(25.dp)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = capitalization,
                    imeAction = imeAction,
                    keyboardType = keyboardType
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MainBlue,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MainBlue,
                    unfocusedIndicatorColor = MainBlue,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MainBlue,
                ),
                singleLine = singleLine,
                modifier = modifier
            )
        }
    }


    @Composable
    fun CustomDatePicker(
        context: Context,
        modifier: Modifier,
        label: String,
        onDateSelected: (Long) -> Unit
    ) {
        val currentCalendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateText = remember { mutableStateOf(dateFormatter.format(currentCalendar.time)) }

        TextField(
            value = dateText.value,
            onValueChange = { /* Read-only, no action needed */ },
            enabled = false,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange, // Tarih ikonu
                    contentDescription = "Date",
                    tint = MainBlue
                )
            },
            modifier = modifier
                .clickable {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        R.style.CustomDatePickerDialog,
                        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                            val selectedCalendar = Calendar.getInstance().apply {
                                set(selectedYear, selectedMonth, selectedDayOfMonth)
                            }
                            dateText.value = dateFormatter.format(selectedCalendar.time)
                            onDateSelected(selectedCalendar.timeInMillis)
                        },
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH),
                        currentCalendar.get(Calendar.DAY_OF_MONTH)
                    )

                    with(datePickerDialog.datePicker) {
                        minDate =
                            currentCalendar.timeInMillis // Kullanıcı sadece bugün veya gelecekteki tarihleri seçebilir
                    }
                    datePickerDialog.show()
                },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MainBlue,
                disabledTextColor = MainBlue,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MainBlue
            ),
            singleLine = true
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        var plate by remember { mutableStateOf("") }

        CarCareTrackerTheme {


            Scaffold(

                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                stringResource(id = R.string.add_vehicle),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MainGray,
                            titleContentColor = MainBlue,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back),
                                    tint = MainBlue
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = stringResource(id = R.string.add_vehicle),
                                    tint = MainBlue
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->

                AddVehicleObjects(innerPadding)

            }

        }
    }
}
