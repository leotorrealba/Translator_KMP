//
//  LanguageDropdown.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 07-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropdown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectedLanguage: (UiLanguage) -> Void
    
    var body: some View {
        
        Menu {
            VStack {
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode){ language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                        selectedLanguage(language)
                        }
                    )
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                Text(language.language.langName)
                    .foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(.lightBlue)
            }
        }
        
    }
}

#Preview {
    LanguageDropdown(
        language: UiLanguage(language: .english, imageName: "english"),
        isOpen: true,
        selectedLanguage: { language in
            
        }
    )
}
