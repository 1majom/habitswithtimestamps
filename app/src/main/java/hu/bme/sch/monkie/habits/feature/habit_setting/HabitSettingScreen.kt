
package hu.bme.sch.monkie.habits.feature.habit_detailed

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import hu.bme.sch.monkie.habits.feature.habit_setting.HabitSettingViewModel
import hu.bme.sch.monkie.habits.ui.theme.AppTheme

@Composable
fun HabitSettingScreen(modifier: Modifier = Modifier, viewModel: HabitSettingViewModel = hiltViewModel()) {
    val currentState by viewModel.uiState.collectAsStateWithLifecycle()

    HabitSettingScreen(
        items = currentState.habits,
        modifier = modifier,
        sendDown = viewModel::sendDown,
        sendUp = viewModel::sendUp,
        deleteHabit = viewModel::deleteHabit,
        showDialog=currentState.showDialog,
        newNameFunc=viewModel::newName,
        clickin=viewModel::clickOn,
        changeBool=viewModel::changeBool
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitSettingScreen(
    items: List<LocalHabit>,
    modifier: Modifier = Modifier,
    sendUp:(uid:Int) -> Unit,
    sendDown:(uid:Int) -> Unit,
    deleteHabit:(uid:Int) -> Unit,
    showDialog:Boolean,
    newNameFunc:(String) -> Unit,
    clickin:(Boolean, Int)->Unit,
    changeBool:(Boolean) -> Unit
    //choosenId:Int
) {
    TextInputDialog(showDialog, newNameFunc, changeBool)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
            )
        }

    ) {
        LazyColumn(Modifier.padding(it)) {
            items(items,key= {it.uid } ) {
                ListItem(headlineContent = { Text(text = it.name) }, trailingContent = {
                    Row{
                        IconButton(onClick =  { clickin(true, it.uid) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick =  { deleteHabit(it.uid) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete")
                        }
                        IconButton(onClick =  { sendUp(it.uid) }) {
                            Icon(Icons.Filled.ArrowUpward, contentDescription = "Up")
                        }
                        IconButton(onClick =  { sendDown(it.uid) }) {
                            Icon(Icons.Filled.ArrowDownward, contentDescription = "Down")
                        }

                    }

                })
            }
        }
    }
}

@Composable
fun TextInputDialog(
    showDialog: Boolean,
    onOkClicked: (String) -> Unit,
    changeBool:(Boolean) -> Unit
) {
    val text = remember { mutableStateOf("") }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { changeBool(false) },
            title = { Text("Enter text") },
            text = {
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text("Text") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onOkClicked(text.value)
                        changeBool( false)
                        text.value=""
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { changeBool(false) }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        HabitSettingScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AppTheme {
        HabitSettingScreen()
    }
}