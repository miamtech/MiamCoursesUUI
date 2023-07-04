package tech.miam.coursesUDemoApp

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import tech.miam.coursesUDemoApp.settings.GetUserUseCase
import tech.miam.coursesUDemoApp.store.StoreUseCase

class MainViewModel(
    val getUserUseCase: GetUserUseCase,
    val storeUseCase: StoreUseCase
) : ViewModel(), KoinComponent {}