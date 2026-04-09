package com.app.masterplan.presentation.ui.common

sealed class MasterPlanState<out R>  {

    data class Success<out R>(val result: R): MasterPlanState<R>()

    data object Waiting : MasterPlanState<Nothing>()

    data class Failure(val exception: Exception) :MasterPlanState<Nothing>()

    data object Loading : MasterPlanState<Nothing>()

}