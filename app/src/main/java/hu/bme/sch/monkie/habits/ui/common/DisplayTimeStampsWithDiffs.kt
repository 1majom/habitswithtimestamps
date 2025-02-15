package hu.bme.sch.monkie.habits.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter

@Composable
fun DisplayDateTimeStampWithDiffs(
    item: DateTimeStampWithDiffs,
    deleteDate: ((uid: Int) -> Unit)? =null,
    onDateSelectedBeforeDatePicker:((Int) -> Unit)? =null,
    changeShowDatePicker:((Boolean) -> Unit)? =null,) {

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    var text=""
    if (deleteDate==null||onDateSelectedBeforeDatePicker==null||changeShowDatePicker==null) {
        text="${item.timestamp.date.format(dateFormat)}, Temp.: ${item.timestamp.temperature}, " +
                "Visi.: ${item.timestamp.visibility}"
    }
    else{
        text= item.timestamp.date.format(dateFormat)
    }

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        headlineContent = {
            Text(
                text = text
            )
        },
        trailingContent = {
            if (onDateSelectedBeforeDatePicker != null) {
                if (changeShowDatePicker != null) {
                    if (deleteDate != null) {
                        Row {
                            Button(onClick = {
                                onDateSelectedBeforeDatePicker(item.timestamp.uid)
//                                changeShowDatePicker(true)
                            }) {
                                Text(text = "Edit")
                            }
                            Button(onClick = {
                                deleteDate(item.timestamp.uid)

                            }) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            }
        }
    )
    if (item.diffInHours != null) {
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            headlineContent = {
                Text(
                    text = "${item.diffInHours}:${item.diffInMinutes}",
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)
                )
            }
        )
    }
}