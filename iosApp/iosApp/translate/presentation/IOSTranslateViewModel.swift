//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 07-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen {
    @MainActor class IOSTranslateViewModel: ObservableObject {
        private var historyDataSource: HistoryDataSource
        private var translateUseCase: TranslateUseCase
        
        private let viewmodel: TranslateViewModel
        
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating: false,
            fromLanguage: UiLanguage(language: .english, imageName: "english"),
            toLanguage: UiLanguage(language: .danish, imageName: "danish"),
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            history: []
        )
        
        private var handle: DisposableHandle?
        
        init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase) {
            self.historyDataSource = historyDataSource
            self.translateUseCase = translateUseCase
            self.viewmodel = TranslateViewModel(translate: translateUseCase, historyDataSource: historyDataSource, coroutineScope: nil)
        }
        
        func onEvent(event: TranslateEvent){
            self.viewmodel.onEvent(event: event)
        }
        
        func startObserving() {
            handle = viewmodel.state.subscribe(onCollect: { state in
                if let state = state{
                    self.state = state
                }
            })
        }
        
        func dispose(){
            handle?.dispose()
        }
    }
    
}
