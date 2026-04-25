package com.app.masterplan.presentation.ui.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterplan.R
import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.presentation.ui.common.AvatarNameSec
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.common.DownloadCard
import com.app.masterplan.presentation.ui.theme.GreenSoft
import com.app.masterplan.presentation.ui.theme.PurpleSoft
import com.app.masterplan.presentation.ui.theme.RedSoft
import com.app.masterplan.presentation.ui.theme.YellowSoft
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun AboutToCheckReport(
    report: Report,
    reportStatus: ReportStatus,
    employeeName: String? = null,
    employeeSurname: String? = null,
    employeePatronymic: String? = null,
    referenceTitle: String,
    downloadButtonTitle: String,
    onDownloadClick: () -> Unit,
    onChecking: (UUID) -> Unit,
    onToUpdate: (UUID) -> Unit,
    onCheck: (UUID) -> Unit,
){

    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = report.title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

            if (employeeName!=null && employeeSurname!=null){
                val initials = "${employeeSurname[0]}${employeeName[0]}"
                val nameWithInitials = "$employeeSurname ${employeeName[0]}.${employeePatronymic?.get(0)?:""}"
                AvatarNameSec(
                    initials, nameWithInitials,Modifier.padding(top = 10.dp)
                )
            }
        }

        report.description?.let {
            Text(
                text = stringResource(R.string.desc) ,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

            Text(
                text = report.description ,
                fontSize = 20.sp, fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
        }

        CardButton(referenceTitle){}

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.created_at),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
            Text(
                text = report.creationDate.format(outputFormatter),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )

        }
        report.editDate?.let {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.updated_at),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = report.editDate.format(outputFormatter),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )

            }
        }


        val statusColor = when(reportStatus){
            ReportStatus.NOT_CHECKED -> RedSoft
            ReportStatus.CHECKED -> GreenSoft
            ReportStatus.CHECKING -> YellowSoft
            ReportStatus.TO_UPDATE -> PurpleSoft
        }

        val statusTitle = when(reportStatus){
            ReportStatus.NOT_CHECKED -> stringResource(R.string.not_check)
            ReportStatus.CHECKED -> stringResource(R.string.checked)
            ReportStatus.CHECKING -> stringResource(R.string.checking)
            ReportStatus.TO_UPDATE -> stringResource(R.string.to_update)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "${stringResource(R.string.status)}: ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.End,
            )

            Text(
                text = statusTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                textAlign = TextAlign.Start,
            )

        }

        DownloadCard(downloadButtonTitle) {
            onDownloadClick()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            FloatingActionButton(
                modifier = Modifier.width(73.dp).height(56.dp),
                containerColor = YellowSoft,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { onChecking(report.id) },
            ) {
                Icon(Icons.Filled.Edit, "Floating checking button.")
            }

            FloatingActionButton(
                modifier = Modifier.width(73.dp).height(56.dp),
                containerColor = PurpleSoft,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { onToUpdate(report.id) },
            ) {
                Icon(Icons.Filled.Autorenew, "Floating toUpdate button.")
            }

            FloatingActionButton(
                modifier = Modifier.width(73.dp).height(56.dp),
                containerColor = GreenSoft,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { onCheck(report.id) },
            ) {
                Icon(Icons.Filled.Check, "Floating check button.")
            }

        }
    }

}