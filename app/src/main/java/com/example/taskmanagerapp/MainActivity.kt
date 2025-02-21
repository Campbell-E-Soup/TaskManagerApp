package com.example.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanagerapp.model.Task
import com.example.taskmanagerapp.ui.theme.TaskManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val mod = Modifier //new mod to avoid innerpadding being added to everthing
    val taskList = remember { mutableStateListOf<Task>(Task("eat")) } //to do list
    var text by remember { mutableStateOf("") }
    Surface( //bckgrd
        modifier = modifier,
        color = colorResource(R.color.background),

        ) {
        Column(
            modifier = mod.padding(16.dp).fillMaxSize(),
        ) {
            Row(mod.fillMaxWidth()) { //textfield and button
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter Task") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(R.color.backgroundUI),
                        unfocusedLabelColor = colorResource(R.color.primary)
                    ),
                    modifier = mod
                        .weight(1f)
                        .padding(end = 4.dp)
                )
                Button(
                    onClick = {
                      taskList.add(Task(text))
                      text = ""
                    },
                    modifier = mod
                        .width(100.dp)
                        .height(56.dp)
                        .align(Alignment.CenterVertically),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary))
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.primaryOn)
                    )
                }
            }
            RenderList(taskList)
        }
    }

}
@Composable
fun RenderList(list: List<Task>, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(top = 16.dp),
        color = colorResource(R.color.backgroundUI),
        shape = RoundedCornerShape(8.dp)
        ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(list) { index, task ->
                //decorations for different task states
                var checked by remember { mutableStateOf(task.GetCompleted()) }
                val textColor = if (checked) colorResource(R.color.backgroundTextDisabled) else colorResource(R.color.backgroundText)
                val textStyle = if (checked) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                Row(modifier) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            task.Check()
                            checked = task.GetCompleted()
                        },
                        modifier = modifier.align(Alignment.CenterVertically),
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.primary),
                            checkmarkColor = colorResource(R.color.primaryOn)
                        )
                    )

                    Text(
                        text = task.GetDescription(),
                        color = textColor,
                        style = textStyle,
                        modifier = Modifier.padding(start = 2.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TaskManagerAppTheme {
        App()
    }
}