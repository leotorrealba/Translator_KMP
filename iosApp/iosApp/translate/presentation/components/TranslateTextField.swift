//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 07-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let isTranslating: Bool
    let fromLanguage: UiLanguage
    let toLanguage: UiLanguage
    let onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: onTranslateEvent)
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
        } else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: onTranslateEvent)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

#Preview {
    TranslateTextField(
        fromText: Binding(get: { " test" }, set: { value in }),
        toText: "TEST",
        isTranslating: true,
        fromLanguage: UiLanguage(language: .english, imageName: "english"),
        toLanguage: UiLanguage(language: .danish, imageName: "danish"),
        onTranslateEvent: { event in }
    )
}

private extension TranslateTextField {
    
    struct TransparentTextEditor: UIViewRepresentable {
        @Binding var text: String
        
        func makeUIView(context: Context) -> UITextView {
            let textView = UITextView()
            textView.backgroundColor = .clear
            textView.textColor = UIColor.label
            textView.font = UIFont.systemFont(ofSize: 16)
            textView.delegate = context.coordinator
            return textView
        }
        
        func updateUIView(_ uiView: UITextView, context: Context) {
            uiView.text = text
            uiView.backgroundColor = .clear // Ensure it stays clear
        }
        
        func makeCoordinator() -> Coordinator {
            Coordinator(self)
        }
        
        class Coordinator: NSObject, UITextViewDelegate {
            let parent: TransparentTextEditor
            
            init(_ parent: TransparentTextEditor) {
                self.parent = parent
            }
            
            func textViewDidChange(_ textView: UITextView) {
                parent.text = textView.text
            }
        }
    }
    
    struct IdleTextField: View {
        @Binding var fromText: String
        let isTranslating: Bool
        let onTranslateEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TransparentTextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Color.onSurface)
                .overlay(alignment: .bottomTrailing){
                    ProgressButton(
                        text: "Translate",
                        isLoading: isTranslating,
                        onClick: {
                            onTranslateEvent(TranslateEvent.Translate())
                        })
                    .padding(.trailing)
                    .padding(.bottom)
                }
        }
    }
    
    struct TranslatedTextField: View {
        let fromText: String
        let toText: String
        let fromLanguage: UiLanguage
        let toLanguage: UiLanguage
        let onTranslateEvent: (TranslateEvent) -> Void
        
        private let tts = TextToSpeech()
        
        var body: some View {
            VStack(alignment: .leading){
                LanguageDisplay(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(Color.secondary)
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            fromText,
                            forPasteboardType: UTType.plainText.identifier)
                    }){
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    Button(action: {
                        onTranslateEvent(TranslateEvent.CloseTranslation())
                    }){
                        Image(systemName: "xmark")
                            .foregroundColor(.lightBlue)
                    }
                }
                Divider()
                    .padding()
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(.onSurface)
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            toText,
                            forPasteboardType: UTType.plainText.identifier)
                    }){
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    Button(action: {
                        tts.speak(
                            text: toText,
                            language: toLanguage.language.langCode
                        )
                    }){
                        Image(systemName: "speaker.wave.2")
                            .foregroundColor(.lightBlue)
                    }
                }
            }
        }
    }
}
