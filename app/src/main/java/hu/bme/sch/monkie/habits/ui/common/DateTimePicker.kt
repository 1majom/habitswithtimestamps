package hu.bme.sch.monkie.habits.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.sch.monkie.habits.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    dateTimeInstant: LocalDateTime?,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    showTempAndVisiPicker:Boolean,
    changeShowDatePicker: (Boolean) -> Unit,
    changeShowTimePicker: (Boolean) -> Unit,
    changeTempAndVisiPicker: (Boolean) -> Unit,

    onDateTimeSelected: (LocalDateTime,Double?,Double?) -> Unit,


    ) {
    val dateTime =
        LocalDateTime.ofInstant(Instant.now(), ZoneId.of("CET"))

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dateTimeInstant?.atZone(ZoneId.of("CET"))?.toInstant()?.toEpochMilli() ?: Instant.now().toEpochMilli(),
        initialDisplayMode = DisplayMode.Picker,
    )

    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = dateTime.hour,
        initialMinute = dateTime.minute
    )


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { changeShowDatePicker(false) },
            confirmButton = {
                TextButton(onClick = {
                    changeShowTimePicker(true)
                    changeShowDatePicker(false)
                    changeTempAndVisiPicker(false)
                }) {
                    Text(text = "Next")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    changeShowDatePicker(false)
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = true)
        }
    }

    if (showTimePicker) {
        DatePickerDialog(
            modifier = Modifier.padding(5.dp),
            onDismissRequest = {
                changeShowTimePicker(false)
                               },
            confirmButton = {
                TextButton(onClick = {
                    changeShowTimePicker(false)
                    changeShowDatePicker(false)
                    changeTempAndVisiPicker(true)

                }) {
                    Text(text = "Next")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    changeShowTimePicker(false)
                    changeTempAndVisiPicker(false)
                    changeShowDatePicker(true)


                }) {
                    Text(text = "Back")
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Choose time and date",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth()
                    .padding(12.dp))
                TimePicker(state = timePickerState, modifier = Modifier.padding(8.dp))
            }
        }
    }
    val lon = remember { mutableStateOf("") }
    val lat = remember { mutableStateOf("") }
    val isInputValid = remember { mutableStateOf(true) }

    if (showTempAndVisiPicker){
        AlertDialog(
            onDismissRequest = {
                changeTempAndVisiPicker(false)
            },
            title = { Text(text = "Enter two coordinates") },
            text = {
                Column {
                    OutlinedTextField(
                        value = lon.value,
                        onValueChange = {
                            lon.value = it
                            isInputValid.value = validateInput(it)
                        },
                        label = { Text("Latitude") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isInputValid.value) Color.Blue else Color.Red,
                            unfocusedBorderColor = if (isInputValid.value) Color.Gray else Color.Red
                        )
                    )
                    OutlinedTextField(
                        value = lat.value,
                        onValueChange = { lat.value = it },
                        label = { Text("Longitude") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val lon2 = lon.value.toDoubleOrNull()
                        val lat2 = lat.value.toDoubleOrNull()
                        changeTempAndVisiPicker(false)
                        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                            onDateTimeSelected(
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(selectedDateMillis),ZoneId.of("CET")).plusHours((timePickerState.hour-2).toLong()).plusMinutes(timePickerState.minute.toLong()),
                                lon2,
                                lat2
                            )
                        }
                        lon.value=""
                        lat.value=""
                    },
                    enabled = (lon.value.isNotEmpty() && lat.value.isNotEmpty()) || (lon.value.isEmpty() && lat.value.isEmpty())
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    changeTempAndVisiPicker(false)
                    changeShowTimePicker(true)
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}
fun validateInput(input: String): Boolean {
    return input.toDoubleOrNull() != null
}
@Preview
@Composable
fun DateTimePickerPreview() {
    AppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                DateTimePicker(
                    dateTimeInstant = LocalDateTime.now(),
                    onDateTimeSelected = { _,_,_  -> },
                    showDatePicker = false,
                    showTimePicker = false,
                    showTempAndVisiPicker = false,
                    changeShowTimePicker = {},
                    changeShowDatePicker = {},
                    changeTempAndVisiPicker = {}
                )
            }
        }
    }
}