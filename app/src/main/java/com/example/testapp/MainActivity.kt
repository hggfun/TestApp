package com.example.testapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelPlannerApp() {
    var departure by remember { mutableStateOf("Москва") }
    var arrival by remember { mutableStateOf("Турция") }
    var isComplexRoute by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Main screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Departure-Arrival Card
        DepartureArrivalCard(
            departure = departure,
            arrival = arrival,
            onCardClick = { scope.launch { sheetState.show(); showBottomSheet = true } },
            onDepartureChange = { departure = it },
            onArrivalChange = { arrival = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Other content can go here
    }

    // Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Departure-Arrival Card inside the bottom sheet
                DepartureArrivalCard(
                    departure = departure,
                    arrival = arrival,
                    onCardClick = { },
                    onDepartureChange = { departure = it },
                    onArrivalChange = { arrival = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Complex route checkbox
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isComplexRoute,
                        onCheckedChange = { isComplexRoute = it }
                    )
                    Text(
                        text = "Сложный маршрут",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                if (isComplexRoute) {
                    Text(
                        text = "Сложный",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DepartureArrivalCard(
    departure: String,
    arrival: String,
    onCardClick: () -> Unit,
    onDepartureChange: (String) -> Unit,
    onArrivalChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Отправление",
                style = MaterialTheme.typography.titleSmall
            )
            TextField(
                value = departure,
                onValueChange = onDepartureChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text("Город отправления") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Прибытие",
                style = MaterialTheme.typography.titleSmall
            )
            TextField(
                value = arrival,
                onValueChange = onArrivalChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text("Город прибытия") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTravelPlannerApp() {
    TravelPlannerApp()
}


//////////////////////////////////////
//////////////////////////////////////

@Composable
fun OverflowingInnerCardsExample() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {

        // Outer Card
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 48.dp)
                .height(200.dp)
                .clipToBounds()    // <-- Important: Disable clipping here
        ) {
            Image(
                painter = painterResource(id = R.drawable.taxi_background),
                contentDescription = "Background Taxi",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Lazy row of inner cards (positioned visually inside/overlapping the main Card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)  // Adjusts vertical position to overlap main card
                .clipToBounds()    // Important to allow overflow
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleItems()) { item ->
                    InnerCard(item)
                }
            }
        }
    }
}

@Composable
fun InnerCard(label: String) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .size(width = 120.dp, height = 120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, color = Color.Black)
        }
    }
}

fun sampleItems() = List(10) { "Card ${it+1}" }

@Preview(showBackground = true)
@Composable
fun PreviewCards() {
    OverflowingInnerCardsExample()
}


///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////



@Composable
fun OverflowWithoutBoxExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clipToBounds() // crucial to allow overflow without clipping
    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 48.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clipToBounds()
        ) {
            Image(
                painter = painterResource(R.drawable.taxi_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Here we use negative offset Y to push LazyRow upward visually overlapping the main Card.
        LazyRow(
            modifier = Modifier
                .offset(y = (-200).dp) // negative offset to overlap upwards
                .fillMaxWidth()
                .clipToBounds(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleItems2()) {
                InnerCard2(label = it)
            }
        }
    }
}

@Composable
fun InnerCard2(label: String) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.size(120.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(),
            color = Color.Black
        )
    }
}

private fun sampleItems2() = List(10) { "Card ${it + 1}" }

@Preview(showBackground = true)
@Composable
fun PreviewCardsWithoutBox() {
    OverflowWithoutBoxExample()
}

@Composable
fun CardWithBackground() {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .paint(
                painter = painterResource(R.drawable.taxi_background),
                contentScale = ContentScale.FillWidth
            )
    ) {  }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardWithBackground() {
    CardWithBackground()
}


///////////////////////////////////////////////////////
///////////////////////////////////////////////////////


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCardWithBottomSheet(wordsList: List<String>) {
    var currentWordIndex by remember { mutableStateOf(0) }
    var visibleLettersCount by remember { mutableStateOf(0) }
    var isAppearing by remember { mutableStateOf(true) }
    val currentWord = wordsList[currentWordIndex]

    // Modal Bottom Sheet State
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    // Animation LaunchEffect
    LaunchedEffect(currentWordIndex, visibleLettersCount, isAppearing) {
        delay(100L)

        if (isAppearing) {
            if (visibleLettersCount < currentWord.length) {
                visibleLettersCount++
            } else {
                delay(1000L)
                isAppearing = false
            }
        } else {
            if (visibleLettersCount > 0) {
                visibleLettersCount--
            } else {
                currentWordIndex = (currentWordIndex + 1) % wordsList.size
                isAppearing = true
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = { showSheet = true } // show the sheet on card clicked
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentWord.take(visibleLettersCount),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Arrival",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Modal Bottom Sheet
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            // Bottom sheet content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Here is your bottom sheet!", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showSheet = false }
                ) {
                    Text("Dismiss")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun LocationCard(
    departure: String,
    arrival: String,
    onCardClick: () -> Unit,
    onDepartureChange: (String) -> Unit,
    onArrivalChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Отправление",
                style = MaterialTheme.typography.titleSmall
            )
            TextField(
                value = departure,
                onValueChange = onDepartureChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text("Город отправления") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Прибытие",
                style = MaterialTheme.typography.titleSmall
            )
            TextField(
                value = arrival,
                onValueChange = onArrivalChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text("Город прибытия") }
            )
        }
    }
}

@Composable
fun BottomSearch() {

}
//last to have cool Departure-arrival
@Preview(showBackground = true)
@Composable
fun AnimatedCardScreen() {
    AnimatedCardWithBottomSheet(
        wordsList = listOf("Departure", "Destination", "Travel", "Adventure")
    )
}


/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////

@Composable
fun DepartureArrivalCard(
    departureText: String,
    arrivalText: String,
    onDepartureTextChange: (String) -> Unit,
    onArrivalTextChange: (String) -> Unit,
    onDepartureMapClick: () -> Unit,
    onArrivalMapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationInputField(
                hint = "Departure",
                text = departureText,
                onTextChange = onDepartureTextChange,
                onMapClick = onDepartureMapClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            LocationInputField(
                hint = "Arrival",
                text = arrivalText,
                onTextChange = onArrivalTextChange,
                onMapClick = onArrivalMapClick,
                autofocus = true
            )
        }
    }
}

@Composable
fun RecentAddressCard(
    recentAddresses: List<String>,
    onAddressClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (recentAddresses.isEmpty()) return

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            recentAddresses.take(6).forEachIndexed { index, address ->
                if (index > 0) {
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                }
                Text(
                    text = address,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAddressClick(address) }
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun LocationInputField(
    hint: String,
    text: String,
    onTextChange: (String) -> Unit,
    onMapClick: () -> Unit,
    autofocus: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text(hint, color = Color.Gray.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                )
            )

            Text(
                text = "on map",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(end = 12.dp)
                    .clickable { onMapClick() }
            )
        }
    }

    LaunchedEffect(autofocus) {
        if (autofocus) {
            focusRequester.requestFocus()
            delay(200L) // Small delay to ensure keyboard opens reliably
            keyboardController?.show()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCardWithBottomSheet2(wordsList: List<String>) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    var departureText by remember { mutableStateOf("") }
    var arrivalText by remember { mutableStateOf("") }

    // Your existing animated card placed here...
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .padding(8.dp),
            onClick = { showSheet = true }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Departure → Arrival", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxHeight(0.8f)
        ) {
            DepartureArrivalCard(
                departureText = departureText,
                arrivalText = arrivalText,
                onDepartureTextChange = { departureText = it },
                onArrivalTextChange = { arrivalText = it },
                onDepartureMapClick = {
                    // TODO: later implement choosing departure location from map
                },
                onArrivalMapClick = {
                    // TODO: later implement choosing arrival location from map
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedCardScreen2() {
    AnimatedCardWithBottomSheet2(
        wordsList = listOf("Departure", "Destination", "Travel", "Adventure")
    )
}