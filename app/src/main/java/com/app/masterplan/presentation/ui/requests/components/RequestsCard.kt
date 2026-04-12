package com.app.masterplan.presentation.ui.requests.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.masterplan.R
import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestCardViewModel
import com.app.masterplan.presentation.ui.theme.Gray
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.time.format.DateTimeFormatter

@Composable
fun RequestsCard(viewModel: RequestCardViewModel, createAnswerButtonOnClick: () -> Unit){

    val selectedRequest = viewModel.selectedRequest.collectAsState()

    val selectedRequestStatus = viewModel.selectedRequestStatus.collectAsState()

    val senderInfo = viewModel.senderInfo.collectAsState()

    val showGetInWorkButton = viewModel.showGetInWorkButton.collectAsState()

    val showCreateAnswerButton = viewModel.showCreateAnswerButton.collectAsState()

    val showAnswerInfo = viewModel.showAnswerInfo.collectAsState()

    val answerInfo = viewModel.answerInfo.collectAsState()

    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")

    Column(
        modifier = Modifier.height(700.dp).padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        selectedRequest.value?.let { request ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = request.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )


                val text = when(senderInfo.value){
                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                    MasterPlanState.Loading -> stringResource(R.string.loading)
                    is MasterPlanState.Success-> (senderInfo.value as MasterPlanState.Success<String>).result
                    MasterPlanState.Waiting -> ""
                }
                Text(
                    text = text,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.End,)
            }


            Text(
                text = selectedRequest.value?.creationDate?.format(outputFormatter) ?:"",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End
            )

            val statusColor = when(selectedRequestStatus.value){
                AdminRequestStatus.NOT_STARTED -> RedSoft
                AdminRequestStatus.IN_PROGRESS -> YellowSoft
                AdminRequestStatus.COMPLETED -> GreenSoft
                AdminRequestStatus.INVALID -> Gray
            }

            val statusTitle = when(selectedRequestStatus.value){
                AdminRequestStatus.NOT_STARTED -> stringResource(R.string.not_started)
                AdminRequestStatus.IN_PROGRESS -> stringResource(R.string.inProgress)
                AdminRequestStatus.COMPLETED -> stringResource(R.string.completed)
                AdminRequestStatus.INVALID -> stringResource(R.string.invalid)
            }
            Text(
                text = statusTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                textAlign = TextAlign.End
            )

            Text(
                text = stringResource(R.string.desc) ,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

            Text(
                text = request.description ,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

            Spacer(Modifier.width(10.dp))

            when {
                showGetInWorkButton.value == true -> {
                    CardButton(stringResource(R.string.get_in_work) ){
                        viewModel.getInWork()
                    }

                }
                showCreateAnswerButton.value == true -> CardButton(stringResource(R.string.create_answer) ){
                    createAnswerButtonOnClick()
                    viewModel.closeRequestTab()
                }
                showAnswerInfo.value == true -> {
                    Text(
                        text = stringResource(R.string.respond) ,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                    )
                    val text = when (answerInfo.value){
                        is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                        MasterPlanState.Loading -> stringResource(R.string.loading)
                        is MasterPlanState.Success -> (answerInfo.value as MasterPlanState.Success<AdminAnswer>).result.description
                        MasterPlanState.Waiting -> ""
                    }
                    Text(
                        text = text ,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}