package com.example.praktikamphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.praktikamphibians.R
import com.example.praktikamphibians.model.Amphibian
import com.example.praktikamphibians.ui.theme.PraktikAmphibiansTheme

@Composable
fun HomeScreen(
    amphibiUiState: AmphibiansUiState,
    retryAction: ()->Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when(amphibiUiState){
        is AmphibiansUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is AmphibiansUiState.Success -> AmphibiansListScreen(amphibians= amphibiUiState.amphibians, modifier = modifier.padding(
            start = dimensionResource(R.dimen.padding_medium),
            top = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_medium)
        ),contentPadding = contentPadding
        )
        else -> ErrorScreen(retryAction, modifier)
    }


}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction){
            Text(stringResource(R.string.retry))
        }

    }
}


@Composable
fun AmphibiCard(amphibian: Amphibian, modifier: Modifier = Modifier){
    Card(modifier = modifier, shape = RoundedCornerShape(8.dp)){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = stringResource(R.string.amphibian_title, amphibian.name, amphibian.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current).data(amphibian.imgSrc).crossfade(true).build(),
                contentDescription = stringResource(id = R.string.amphibi_photo),
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),

            )
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )

        }
    }
}

@Composable
fun AmphibiansListScreen(
    amphibians: List<Amphibian>,
    modifier: Modifier =  Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        items(
            items = amphibians,
            key = { amphibian ->
                amphibian.name
            }
        ){ amphibian->
            AmphibiCard(amphibian = amphibian, modifier = Modifier.fillMaxSize())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview(){
    PraktikAmphibiansTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview(){
    PraktikAmphibiansTheme {
ErrorScreen({}, Modifier.fillMaxSize())
    }
    }


@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview(){
    PraktikAmphibiansTheme {
        val mockData = List(10){
            Amphibian(
                "Lorem Ipsum - $it",
                "$it",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                        " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                        " minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                        " ex ea commodo consequat.",
                imgSrc = ""
            )
        }
        AmphibiansListScreen(mockData, Modifier.fillMaxSize())
    }
    }