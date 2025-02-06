package com.dicoding.favorite.ui.detail

//
//@HiltViewModel
//class DetailMealViewModel @Inject constructor(
//    private val getMealDetailUseCase: GetMealDetailUseCase,
//    private val insertFavoriteMealUseCase: InsertFavoriteMealUseCase
//): ViewModel(), IDetailMealViewModel {
//
//    private val _state = MutableStateFlow<StateUi<MealDetail>>(StateUi())
//    override val state: StateFlow<StateUi<MealDetail>> = _state.asStateFlow()
//
//
//    override fun onEvent(event: DetailEvent) {
//        when(event) {
//            is DetailEvent.GetDetail -> getDetailMeal(event.id)
//            DetailEvent.ResetError -> _state.update {
//                it.copy(
//                    error = Pair(false, "")
//                )
//            }
//            is DetailEvent.SetFavorite -> {
//                val meal = event.meal.toMeal()
//                insertFavoriteMeal(meal, context = event.context)
//            }
//        }
//    }
//
//    private fun insertFavoriteMeal(meal: Meal, context: Context) {
//        viewModelScope.launch {
//            try {
//                insertFavoriteMealUseCase(meal)
//                Toast.makeText(context, "Added to favorite successfully", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                _state.update {
//                    it.copy(
//                        error = Pair(true, e.message ?: "An error occurred")
//                    )
//                }
//            }
//        }
//    }
//
//
//    private fun getDetailMeal(id: String) {
//        viewModelScope.launch {
//            try {
//                getMealDetailUseCase(id).collect{ state ->
//                    when(state){
//                        State.Empty -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                        is State.Error -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    error = Pair(true, state.message)
//                                )
//                            }
//                        }
//                        State.Loading -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = true,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                        is State.Success -> {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    data = state.data,
//                                    error = Pair(false, "")
//                                )
//                            }
//                        }
//                    }
//
//                }
//
//            } catch (e: Exception){
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        error = Pair(true, e.message ?: "An error occurred")
//                    )
//                }
//            }
//
//        }
//    }
//}