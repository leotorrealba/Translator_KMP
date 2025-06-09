import SwiftUI
import shared

struct ContentView: View {
    
    let appModule: AppModule
    @State private var showSplash = true

	var body: some View {
        
        ZStack {
            Color.background
                .ignoresSafeArea(edges: .all)
            if showSplash {
                SplashScreen()
                    .onAppear {
                        DispatchQueue.main
                            .asyncAfter(deadline: .now() + 2) {
                                withAnimation {
                                    showSplash = false
                                }
                            }
                    }
            } else {
                TranslateScreen(
                    historyDataSource: appModule.historyDataSource,
                    translateUseCase: appModule.translateUseCase,
                    parser: appModule.voiceParser
                )
            }
        }
	}
}
