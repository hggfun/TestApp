//package com.example.testapp
//
//
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//
//@Composable
//fun DepartureArrivalCard(
//    departureText: String,
//    arrivalText: String,
//    onDepartureTextChange: (String) -> Unit,
//    onArrivalTextChange: (String) -> Unit,
//    onDepartureMapClick: () -> Unit,
//    onArrivalMapClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    onDepartureFocused: () -> Unit = {},
//    onArrivalFocused: () -> Unit = {},
//    onDonePressed: () -> Unit = {}  // Added Callback
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Card(modifier = modifier.padding(8.dp)) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            OutlinedTextField(
//                value = departureText,
//                onValueChange = onDepartureTextChange,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .onFocusChanged {
//                        if (it.isFocused) onDepartureFocused()
//                    },
//                label = { Text("Departure") },
//                trailingIcon = {
//                    IconButton(onClick = onDepartureMapClick) {
//                        Icon(Icons.Default.Place, "Pick from map")
//                    }
//                },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        keyboardController?.hide()
//                        onDonePressed()
//                    }
//                )
//            )
//
//            OutlinedTextField(
//                value = arrivalText,
//                onValueChange = onArrivalTextChange,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .onFocusChanged {
//                        if (it.isFocused) onArrivalFocused()
//                    },
//                label = { Text("Arrival") },
//                trailingIcon = {
//                    IconButton(onClick = onArrivalMapClick) {
//                        Icon(Icons.Default.Place, "Pick from map")
//                    }
//                },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        keyboardController?.hide()
//                        onDonePressed()
//                    }
//                )
//            )
//        }
//    }
//}
//
//fun attemptNavigation() {
//    if (departureText.isNotBlank() && arrivalText.isNotBlank()) {
//        keyboardController?.hide()
//        onNavigateToTripDetails(departureText, arrivalText)
//    }
//}
//
