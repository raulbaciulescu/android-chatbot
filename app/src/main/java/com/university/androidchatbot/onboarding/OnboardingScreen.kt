package com.university.androidchatbot.onboarding

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//private fun OnboardingScreen(
//    pages: List<TopUpIllustration>,
//    pagerState: PagerState,
//    onFinishOnboarding: () -> Unit
//) = Column(
//    modifier = Modifier
//        .fillMaxSize(),
//    verticalArrangement = Arrangement.Center,
//    horizontalAlignment = Alignment.CenterHorizontally
//) {
//    HorizontalPager(pageCount = pages.size, state = pagerState) { crtPage ->
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                modifier = Modifier.fillMaxWidth(.7f),
//                painter = painterResource(pages[crtPage]),
//                contentDescription = null
//            )
//            Text(
//                text = stringResource(getTitle(crtPage)),
//                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
//                color = MaterialTheme.colorScheme.textPrimary
//            )
//            VerticalSpace(size = 12.dp)
//            Text(
//                modifier = Modifier.fillMaxWidth(0.8f),
//                text = stringResource(getDescription(crtPage)),
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.textPrimary,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//    VerticalSpace(size = 32.dp)
//    DotsIndicator(totalDots = pages.size, selectedIndex = pagerState.currentPage)
//    VerticalSpace(size = 24.dp)
//    if (pagerState.currentPage == pages.lastIndex) {
//        MyFilledButton(
//            modifier = Modifier
//                .fillMaxWidth(.5f)
//                .height(48.dp),
//            text = "Get started",
//            onClick = onFinishOnboarding
//        )
//    } else {
//        VerticalSpace(size = 48.dp)
//    }
//}