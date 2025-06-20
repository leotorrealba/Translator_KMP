//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 07-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: TranslateUseCase
    @ObservedObject var viewModel: IOSTranslateViewModel
    private let parser: any VoiceToTextParser
    
    init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase, parser: VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.parser = parser
        self.viewModel = IOSTranslateViewModel(historyDataSource: historyDataSource, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropdown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectedLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    )
                    Spacer()
                    SwapLanguageButton(
                        onClick: {
                            viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                        }
                    )
                    Spacer()
                    LanguageDropdown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectedLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromText: Binding(get: { viewModel.state.fromText }, set: { value in
                        viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                    }),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: {
                        viewModel.onEvent(event: $0)
                    })
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.history.isEmpty {
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                }
                
                ForEach(viewModel.state.history, id: \.self.id){ item in
                        TranslateHistoryItem(
                            item: item) {
                                viewModel.onEvent(event: .SelectHistoryItem(item: item))
                            }
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.background)
                }
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack {
                Spacer()
                NavigationLink(destination:
                                VoiceToTextScreen(
                                    onResult: { spokenText in
                                        viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: spokenText))
                                    },
                                    parser: parser,
                                    languageCode: viewModel.state.fromLanguage.language.langCode)
                ){
                    ZStack {
                        Circle()
                            .foregroundColor(.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundColor(.onPrimary)
                            .accessibilityIdentifier("Record audio")
                    }
                    .frame(maxWidth: 100, maxHeight: 100)
                }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
