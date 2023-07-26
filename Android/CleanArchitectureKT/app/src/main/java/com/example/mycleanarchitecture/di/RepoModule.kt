package kr.or.koelsa.elsm.di

import kr.or.koelsa.elsm.data.repositoryImpl.*
import kr.or.koelsa.elsm.domain.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repoModule = module {
    singleOf(::AccidentRepoImpl) { bind<AccidentRepo>() }
    singleOf(::UserRepoImpl) { bind<UserRepo>() }
    singleOf(::LoginRepoImpl) { bind<LoginRepo>() }
    singleOf(::ReportRepoImpl) { bind<ReportRepo>() }
    singleOf(::LocationRepoImpl) { bind<LocationRepo>() }
    singleOf(::MobilizeRepoImpl) { bind<MobilizeRepo>() }
    singleOf(::NaverRepoImpl) { bind<NaverRepo>() }
    singleOf(::CodeRepoImpl) { bind<CodeRepo>() }
    singleOf(::ElevatorRepoImpl) { bind<ElevatorRepo>() }
    singleOf(::CorpLinkRepoImpl) { bind<CorpLinkRepo>() }
    singleOf(::MessageQueueRepoImpl) { bind<MessageQueueRepo>() }
    singleOf(::PushRepoImpl) { bind<PushRepo>() }

}