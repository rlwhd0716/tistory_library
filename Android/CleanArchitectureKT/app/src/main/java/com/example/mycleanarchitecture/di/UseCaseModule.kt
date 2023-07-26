package kr.or.koelsa.elsm.di

import kr.or.koelsa.elsm.domain.usecase.*
import kr.or.koelsa.elsm.domain.usecaseImpl.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::UserUseCaseImpl) { bind<UserUseCase>() }
    singleOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
    singleOf(::AccidentUseCaseImpl) { bind<AccidentUseCase>() }
    singleOf(::ReportUseCaseImpl) { bind<ReportUseCase>() }
    singleOf(::LocationUseCaseImpl) { bind<LocationUseCase>() }
    singleOf(::MobilizeUseCaseImpl) { bind<MobilizeUseCase>() }
    singleOf(::NaverUseCaseImpl) { bind<NaverUseCase>() }
    singleOf(::CodeUseCaseImpl) { bind<CodeUseCase>() }
    singleOf(::ElevatorUseCaseImpl) { bind<ElevatorUseCase>() }
    singleOf(::CorpLinkUseCaseImpl) { bind<CorpLinkUseCase>() }
    singleOf(::MessageQueueUseCaseImpl) { bind<MessageQueueUseCase>() }
    singleOf(::PushUseCaseImpl) { bind<PushUseCase>() }

}