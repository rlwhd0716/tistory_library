package kr.or.koelsa.elsm.di

import kr.or.koelsa.elsm.view.account.AccountViewModel
import kr.or.koelsa.elsm.view.account.AccountCreateViewModel
import kr.or.koelsa.elsm.view.area.AreaManageViewModel
import kr.or.koelsa.elsm.view.elevator.ElevatorManageViewModel
import kr.or.koelsa.elsm.view.elevator.ElevatorRegisterViewModel
import kr.or.koelsa.elsm.view.intro.IntroViewModel
import kr.or.koelsa.elsm.view.login.LoginViewModel
import kr.or.koelsa.elsm.view.main.MainViewModel
import kr.or.koelsa.elsm.view.report.ReportViewModel
import kr.or.koelsa.elsm.view.spread.SpreadDetailViewModel
import kr.or.koelsa.elsm.view.spread.SpreadViewModel
import kr.or.koelsa.elsm.view.trouble.TroubleReportViewModel
import kr.or.koelsa.elsm.view.user.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::IntroViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::AccountCreateViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SpreadDetailViewModel)
    viewModelOf(::SpreadViewModel)
    viewModelOf(::SettingViewModel)
    viewModelOf(::ElevatorManageViewModel)
    viewModelOf(::ElevatorRegisterViewModel)
    viewModelOf(::AreaManageViewModel)
    viewModelOf(::ReportViewModel)
    viewModelOf(::TroubleReportViewModel)
}