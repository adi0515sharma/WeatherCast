package com.kft.learnkmp.android.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.kft.learnkmp.Utils.Location
import com.kft.learnkmp.android.R
import com.kft.learnkmp.model.Database.SaveList.SavedListDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    searchScreenTitle : String,
    currentSearch : TextFieldValue,
    setCurrentSearch : (TextFieldValue) -> Unit,
    getSearchResult : List<SavedListDTO>,
    previousHistory: List<SavedListDTO> ,
    navigateTo : (String, Boolean) -> Unit,
    deleteItem : (SavedListDTO) -> Unit
){




    Column (modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(3.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Gray)
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = currentSearch,
                onValueChange = { newTextState -> setCurrentSearch(newTextState) },
                placeholder = { Text("Enter text") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Gray
                )
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navigateTo(currentSearch.text, false)
                    },
                colorFilter = ColorFilter.tint(Color.Gray)
            )

        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))


        Text(
            text = searchScreenTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()){
            if(searchScreenTitle == "Previous Search History"){
                items(previousHistory.indices.toList().reversed()){
                    SearchItem(
                        previousHistory[it],
                        Modifier.clickable {
                            val location = Gson().toJson(
                                Location(
                                    previousHistory[it].lat,
                                    previousHistory[it].lon
                                )
                            )
                            navigateTo(location, true)
                        },
                        {
                            deleteItem(previousHistory[it])
                        }
                    )
                }
            }
            else{
                items(getSearchResult.indices.toList()){
                    SearchItem(
                        getSearchResult[it],
                        Modifier.clickable {
                            val location = Gson().toJson(Location(getSearchResult[it].lat, getSearchResult[it].lon))
                            navigateTo(location, true)
                        },
                        {  },
                        false
                    )
                }
            }

        }
    }
}

@Composable
fun SearchItem(
    savedListDTO: SavedListDTO,
    modifier: Modifier,
    deleteItemListener : ()->Unit,
    isPreviousSearch : Boolean = true) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)){

        Column (modifier = Modifier.weight(1f)){
            Text(text = savedListDTO.name.toString())
            Spacer(modifier = Modifier.width(1.dp))
            Row{
                Text(text = if(savedListDTO.state!=null) savedListDTO.state +"," else "")
                Text(text = savedListDTO.country)
            }
        }
        Spacer(modifier = Modifier.width(3.dp))
        Image(
            painter = painterResource(id = if(!isPreviousSearch) R.drawable.baseline_search_24 else R.drawable.baseline_replay_24),
            contentDescription = "search icon",
            modifier = Modifier.size(30.dp),
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Spacer(modifier = Modifier.width(3.dp))

        if(isPreviousSearch){
            Image(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "delete icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { deleteItemListener() },
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }
    }
}