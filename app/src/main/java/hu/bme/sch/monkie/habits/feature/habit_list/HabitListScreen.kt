
package hu.bme.sch.monkie.habits.feature.habit_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import hu.bme.sch.monkie.habits.data.local.database.LocalHabit
import hu.bme.sch.monkie.habits.feature.habit.HabitListViewModel
import hu.bme.sch.monkie.habits.ui.common.ClickableSwipeListItem
import hu.bme.sch.monkie.habits.ui.theme.AppTheme

@Composable
fun HabitListScreen(modifier: Modifier = Modifier, viewModel: HabitListViewModel = hiltViewModel(), navigate: (String) -> Unit) {
    val currentState by viewModel.uiState.collectAsStateWithLifecycle()
    HabitListScreen(
        modifier = modifier,
        onClick = viewModel::addNew,
        onSwipeLeft= viewModel::deleteLast,
        addNewHabit = viewModel::addNewHabit,
        items = currentState.habits,
        navigate=navigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HabitListScreen(
    items: List<LocalHabit>,
    modifier: Modifier = Modifier,
    onClick:(uid:Int) -> Unit,
    addNewHabit:(new:String)->Unit,
    onSwipeLeft:(uid:Int) -> Unit,
    navigate:(String) -> Unit,

    ) {
    val showDialog = remember { mutableStateOf(false) }

    getPermission()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habits") },
                actions = {
                    IconButton(onClick = { throw RuntimeException("Test Crash") // Force a crash
                         }) {
                        Icon(Icons.Filled.BrokenImage, contentDescription = "crash")
                    }
                    IconButton(onClick = { navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick =  { showDialog.value = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                    if (showDialog.value) {
                        TextInputDialog(showDialog, addNewHabit)
                    }


                }
            )
        }
    ) {
        LazyColumn(Modifier.padding(it)) {
            items(items) {
                ClickableSwipeListItem(
                    onClick = { onClick(it.uid) },
                    onLongClick = { navigate("habit/"+it.uid) },
                    onSwipedToStart = {onSwipeLeft(it.uid)},
                    headlineContent = { Text(text = it.name) })
            }
        }
    }
}
@Composable
fun TextInputDialog(
    showDialog: MutableState<Boolean>,
    onOkClicked: (String) -> Unit,
) {
    val text = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Enter habit name") },
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
                        showDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun getPermission() {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value && !locationPermissionsState.allPermissionsGranted) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Permission Required") },
            text = {
                Column {
                    if (locationPermissionsState.permissions.all { !it.status.isGranted }) {
                        Text(
                            text = "Ahhoz, hogy a térkép megtalálja az aktuális pozícióját engedélyeznie kell a helyhozzáférést. " +
                                    "Ha a gomb megnyomása nem hozza be a menüt, akkor az alkalmazásinfó oldalon be lehet állítani az engedélyt",
                            textAlign = TextAlign.Justify
                        )
                    }
                    if (locationPermissionsState.permissions.find { it.permission == android.Manifest.permission.ACCESS_FINE_LOCATION }?.status?.isGranted == false) {
                        Text(
                            text = "Csak a hozzávetőleges adatokhoz fér hozzá az alkalmazás, így csak egy nagyobb terület behatárolására képes. " +
                                    "Ha pontos helyadatot szeretne, akkor engedélyeznie kell azt. " +
                                    "Ha a gomb megnyomása nem hozza be a menüt, akkor az alkalmazásinfó oldalon be lehet állítani az engedélyt"
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        locationPermissionsState.launchMultiplePermissionRequest()
                        showDialog.value = false
                    }
                ) {
                    Text(text = "Engedély megadása")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "Mégse")
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
        HabitListScreen(
            Modifier.padding(16.dp),
        ) { }
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    AppTheme {
        HabitListScreen(
            Modifier.padding(16.dp),
        ) { }
    }
}
