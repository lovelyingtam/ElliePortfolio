package com.ellie.jetportfolio


class SampleActivityTest {
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>() // For resource access
//
//    // To make sure that your app navigates to the correct place,
//    // you need to reference a TestNavHostController instance to check
//    // the navigation route of the nav host when the app takes actions to navigate.
//    private lateinit var navController: TestNavHostController
//
//    @Before
//    fun setupCupcakeNavHost() {
//        // When Start screen is loaded
//        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(
//                ComposeNavigator()
//            )
//            CupcakeApp(navController = navController)
//        }
//    }
//
//    @Test
//    fun cupcakeNavHost_verifyStartDestination() {
//        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
//        val backText = composeTestRule.activity.getString(R.string.back_button)
//        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
//    }
//
//    @Test
//    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
//        navigateToFlavorScreen()
//        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickNextOnFlavorScreen_navigatesToPickupScreen() {
//        navigateToFlavorScreen()
//        composeTestRule.onNodeWithStringId(R.string.next).performClick()
//        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickBackOnFlavorScreen_navigatesToStartOrderScreen() {
//        navigateToFlavorScreen()
//        performNavigateUp()
//        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartOrderScreen() {
//        navigateToFlavorScreen()
//        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
//        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickNextOnPickupScreen_navigatesToSummaryScreen() {
//        navigateToPickupScreen()
//        composeTestRule.onNodeWithText(getFormattedDate(1)).performClick()
//        composeTestRule.onNodeWithStringId(R.string.next).performClick()
//        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickBackOnPickupScreen_navigatesToFlavorScreen() {
//        navigateToPickupScreen()
//        performNavigateUp()
//        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickCancelOnPickupScreen_navigatesToStartOrderScreen() {
//        navigateToPickupScreen()
//        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
//        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
//    }
//
//    @Test
//    fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartOrderScreen() {
//        navigateToSummaryScreen()
//        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
//        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
//    }
//
//    private fun navigateToFlavorScreen() {
//        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
//        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
//    }
//
//    private fun navigateToPickupScreen() {
//        navigateToFlavorScreen()
//        composeTestRule.onNodeWithStringId(R.string.next).performClick()
//    }
//
//    private fun navigateToSummaryScreen() {
//        navigateToPickupScreen()
//        composeTestRule.onNodeWithText(getFormattedDate(1)).performClick()
//        composeTestRule.onNodeWithStringId(R.string.next).performClick()
//    }
//
//    private fun performNavigateUp() {
//        val backText = composeTestRule.activity.getString(R.string.back_button)
//        composeTestRule.onNodeWithContentDescription(backText).performClick()
//    }
}