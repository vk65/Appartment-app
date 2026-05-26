package com.example.appartmentapp.di

import com.example.appartmentapp.data.repository.AuthRepositoryImpl
import com.example.appartmentapp.data.repository.DashboardRepositoryImpl
import com.example.appartmentapp.domain.repository.AuthRepository
import com.example.appartmentapp.domain.repository.DashboardRepository
import com.example.appartmentapp.domain.usecase.*

class AppContainer {
    private val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
    private val dashboardRepository: DashboardRepository by lazy { DashboardRepositoryImpl() }

    val loginUseCase: LoginUseCase by lazy { LoginUseCase(authRepository) }
    val signupUseCase: SignupUseCase by lazy { SignupUseCase(authRepository) }
    val forgotPasswordUseCase: ForgotPasswordUseCase by lazy { ForgotPasswordUseCase(authRepository) }
    val verifyOtpUseCase: VerifyOtpUseCase by lazy { VerifyOtpUseCase(authRepository) }
    val resetPasswordUseCase: ResetPasswordUseCase by lazy { ResetPasswordUseCase(authRepository) }
    val logoutUseCase: LogoutUseCase by lazy { LogoutUseCase(authRepository) }
    val getDashboardDataUseCase: GetDashboardDataUseCase by lazy { GetDashboardDataUseCase(dashboardRepository) }
}
